package dynamicDetection;

import java.io.File;
import java.util.ArrayList;

import controller.Detector;
import controller.MultiMap;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import javassist.tools.reflect.Loader;


public class DynamicDetector extends Detector
{
	private int DEBUG_COUNTER_MADE_REFLECTIVE = 0;
	
	private ClassPool pool; 
	private Loader classLoader; 
	
	private ArrayList<String> paths;
	private ArrayList<ClassPoolEntity> classNames;
	
	// C'tor
	//
	public DynamicDetector (String absolutPathToBinaryDirectory) throws NotFoundException, CannotCompileException  
	{
		super (absolutPathToBinaryDirectory);
		
		pool = ClassPool.getDefault();	
		classLoader = new Loader();
		pool.insertClassPath(absolutPathToBinaryDirectory);
		classLoader.setClassPool(pool);
		classLoader.delegateLoadingOf("dynamicDetection.DynamicDataContainer");
		
		paths = new ArrayList<String>();
		classNames = new ArrayList<ClassPoolEntity>();
	}
	
	public void run(String mainName, String[] args) 
	{
		try 
		{
			collectClassNames (new File(absolutePathToRootDirectory));
			
			appendLibrariesToPool (pool);

			appendPaths();
			
			detectDublicatedFieldNames();
			setFieldModifiersToPublic ();
			
			loadInterfaces();
			makeReflective();
			
			copyPoolToDataContainer ();
			
			runMain (mainName, args);
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
	
	private void collectClassNames(File file) 
	{
		if (file.getName().endsWith(".class") && file.isFile())
		{
			String packageName = getPackageNameFromPath (file.getAbsolutePath());
			classNames.add(new ClassPoolEntity (packageName + file.getName()));
			
			String absoluteDirPath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(pathDelimiter));
			addPath (absoluteDirPath);
		}
		else if (file.getName().endsWith(".jar") && file.isFile())
		{
			addPath (file.getAbsolutePath());
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
	
	private void addPath(String absoluteDirPath) 
	{
		if (!paths.contains(absoluteDirPath))
			paths.add(absoluteDirPath);
	}
	
	private void appendPaths() throws NotFoundException 
	{
		for (String path : paths)
			pool.appendPathList(path);
	}
	
	private void detectDublicatedFieldNames() throws Throwable 
	{
		MultiMap fieldNames = new MultiMap();
		for (ClassPoolEntity entity : this.classNames)
		{
			CtClass cc = pool.get(getNameWithoutExtension(entity.getClassName()));
			cc.instrument (new FieldNameCollector(cc.getName(), fieldNames));
		}
		
		ArrayList<ClassFieldMap> duplicatedFieldNames = fieldNames.getDuplicates ();
		if (!duplicatedFieldNames.isEmpty())
			System.out.println ("***WARNING*** Duplicated field names detected!");
		
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
		DynamicDataContainer.getInstance().setPool (this.pool);
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
				
				System.out.println ("loaded interface: " + ctClass.getName());
			}
		}
	}

	private void makeReflective() throws Throwable 
	{
		for (ClassPoolEntity entity : classNames)
			makeClassesReflectiv();
		
		System.out.println ("****madereflective: " + DEBUG_COUNTER_MADE_REFLECTIVE + " files");
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
	
	// Makes all Classes in "classNames" reflective in correct order (parentclasses first!)
	//
	private void makeClassesReflectiv() throws Throwable 
	{
		while (!allClassesLoaded())
		{
			for (ClassPoolEntity entity : classNames)
			{
				CtClass ctClass = pool.get(getNameWithoutExtension(entity.getClassName()));
				
				if (!entity.getIsLoaded())
				{
					if (parentClassIsLoaded (ctClass))
					{
						classLoader.makeReflective((ctClass.getName()), 
								"dynamicDetection.MetaClass", 
								"javassist.tools.reflect.ClassMetaobject");
						entity.setIsLoaded(true);
						
						System.out.println ("madereflective: " + entity.getClassName());
						
						DEBUG_COUNTER_MADE_REFLECTIVE++;
					}
				}
			}
		}
	}
		
	private boolean parentClassIsLoaded(CtClass ctClass) throws NotFoundException 
	{
		String parentClassName = ctClass.getSuperclass().getName() + ".class";
		
		// If parent class is not inside "classNames" it should be loaded
		//
		boolean found = false;
		for (ClassPoolEntity entity : classNames)
			if (entity.getClassName().equals(parentClassName))
				found = true;
		
		if (!found)
			return true;
		
		// If parent class is inside "classNames" and loaded, load class 
		// 
		for (ClassPoolEntity entity : classNames)
			if (entity.getClassName().equals(parentClassName) && entity.getIsLoaded())
					return true;
		
		return false;
	}
	
	private void runMain(String mainClass, String[] args) throws Throwable
	{
		classLoader.run(mainClass, args);
	}
		
	public MultiMap getResult() 
	{
		return DynamicDataContainer.getInstance().getFieldWriteTraps();
	}
	
}
