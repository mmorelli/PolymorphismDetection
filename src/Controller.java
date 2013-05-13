import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import javassist.tools.reflect.Loader;


public class Controller 
{
//	private final String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\Javassist Projects\\Source\\bin";
//	private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-SnakesAndLadders - public\\bin";
//	private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-SnakesAndLadders - public - for package testing\\bin";
	private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-SnakesAndLadders - private - no ps\\bin";
//	private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-SnakesAndLadders - private - no ps - testingDublicates\\bin";

	
//	private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\MooseJEE-ArgoUML_032-IWRE2012\\MooseJEE-ArgoUML_032-IWRE2012\\src\\ArgoUML_032\\src\\argouml-app\\src\\org";
	
	
	private ClassPool pool; 
	private Loader classLoader; 
	
	private  ArrayList<String> paths = new ArrayList<String>();
	private  ArrayList<ClassPoolEntity> classNames = new ArrayList<ClassPoolEntity>();
	
	// C'tor
	//
	public Controller () throws Throwable
	{
		pool = ClassPool.getDefault();	
		classLoader = new Loader();
		pool.insertClassPath(absolutPathToBinaryDirectory);
		classLoader.setClassPool(pool);
		classLoader.delegateLoadingOf("DataContainer");
	}
	
	public static void main(String[] args) 
	{
		Controller controller;
		
		try 
		{
			controller = new Controller ();
			
			// TODO delete package includes in code
			
			controller.collectClassNames (new File(absolutPathToBinaryDirectory));
			
			controller.appendLibPaths();
			
			controller.renameDublicatedFieldNames();
			controller.setFieldModifiersToPublic ();
			
			controller.loadInterfaces();
			controller.makeReflective();
			
			controller.copyPoolToDataContainer ();
			
			controller.runMain ("Game", args);

			
			MultiMap result = DataContainer.getInstance().getFieldWriteTraps();
			result.printOut();
//			result.printAll();

		}
		catch (NotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
		}
		
		
	}
	
	private void renameDublicatedFieldNames() throws Throwable 
	{
		MultiMap fieldNames = new MultiMap();
		for (ClassPoolEntity entity : this.classNames)
		{
			CtClass cc = pool.get(getNameWithoutExtension(entity.getClassName()));	
			cc.instrument (new FieldNameCollector(cc.getName(), fieldNames));	
		}
		
		HashMap<String, String> dublicates = getDublicatedFieldNames (fieldNames);
		
		renameDublicates (dublicates);
	}
	
	private HashMap<String, String> getDublicatedFieldNames(MultiMap fieldNames) 
	{
		HashMap<String, String> dublicateFieldNames = new HashMap<String, String>();
		dublicateFieldNames = fieldNames.getDublicates ();
		return dublicateFieldNames;
	}
	
	private void renameDublicates(HashMap<String, String> dublicates) throws Throwable 
	{
		Set<String> set = dublicates.keySet();
		for (String key : set)
		{
			CtClass cc = pool.get(key);	
			cc.instrument (new FieldNameRewriter(dublicates.get(key)));
		}
	}

	private void setFieldModifiersToPublic() throws Throwable 
	{
		for (ClassPoolEntity entity : this.classNames)
		{
			CtClass cc = pool.get(getNameWithoutExtension(entity.getClassName()));
			cc.instrument (new ModifierRewriter());
		}
	}

	private void copyPoolToDataContainer() 
	{
		DataContainer.getInstance().setPool (this.pool);
	}

	private void runMain(String mainClass, String[] args) throws Throwable 
	{
		classLoader.run(mainClass, args);
	}
	
	private void collectClassNames(File file) 
	{
		if (file.getName().endsWith(".class") && file.isFile())
		{
			System.out.println(file.getName());
			classNames.add(new ClassPoolEntity (file.getName()));
			
			String absoluteFilePath = file.getAbsolutePath();
			String absoluteDirPath = absoluteFilePath.substring(0, absoluteFilePath.lastIndexOf("\\"));
			
			if (!paths.contains(absoluteDirPath))
				paths.add(absoluteDirPath);
			
			DataContainer.getInstance().addPackageNameOfClass (absoluteFilePath);
		}
		else if (file.getName().endsWith(".jar") && file.isFile())
		{
			if (!paths.contains(file.getAbsolutePath()))
				paths.add(file.getAbsolutePath());
		}
	    
	    if (file.isDirectory())
	    {
		    File[] children = file.listFiles();
		    for (File child : children) 
		    {
		    	collectClassNames(child);
		    }
	    }

	}
	
	private void appendLibPaths() throws NotFoundException 
	{
		for (String path : paths)
			pool.appendPathList(path);
	}
	
	private void loadInterfaces() throws Throwable 
	{
		for (ClassPoolEntity entity : classNames)
		{
			CtClass ctClass = pool.get(getNameWithoutExtension(entity.getClassName()));
			
			if (ctClass.isInterface())
			{
				classLoader.loadClass((ctClass.getName()));
				entity.setIsLoaded(true);
			}
		}
		
	}

	private void makeReflective() throws Throwable 
	{
		for (ClassPoolEntity entity : classNames)
			makeClassesReflectiv();
	}

	
	private boolean allClassesLoaded() throws Throwable 
	{
		for (ClassPoolEntity entity : classNames)
		{
			if (!entity.getIsLoaded())
				return false;
		}
		
		return true;
	}
	
	private void makeClassesReflectiv() throws Throwable 
	{
		while (!allClassesLoaded())
		{
			for (ClassPoolEntity entity : classNames)
			{
				CtClass ctClass = pool.get(getNameWithoutExtension(entity.getClassName()));
				
				if (!entity.getIsLoaded())
				{
					if (ctClass.getSuperclass().getName().equals( "java.lang.Object"))
					{
						classLoader.makeReflective((ctClass.getName()), 
								"MetaClass", 
								"javassist.tools.reflect.ClassMetaobject");
						entity.setIsLoaded(true);
					}
					else if (getEntityByName(ctClass.getSuperclass().getName() + ".class").getIsLoaded()) 
					{
						String name = ctClass.getSuperclass().getName() + ".class";
						
						classLoader.makeReflective((ctClass.getName()), 
								"MetaClass", 
								"javassist.tools.reflect.ClassMetaobject");
						entity.setIsLoaded(true);
					}
				}
			}
		}
	}
		
	private ClassPoolEntity getEntityByName(String className) throws Throwable 
	{
		for (ClassPoolEntity entity : classNames)
		{
			if (entity.getClassName().equals(className))
				return entity;
		}
		
		return null;
	}	
		
	private static String getExtension (String fileName)
	{
		int index = fileName.lastIndexOf('.');
		return fileName.substring(index+1);
	}
	
	private static String getNameWithoutExtension (String fileName)
	{
		int index = fileName.lastIndexOf('.');
		return fileName.substring(0, index);
	}
}
