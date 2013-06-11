package dynamicDetection;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
			collectClassNames (new File(absolutPathToBinaryDirectory));
			
			appendLibrariesToPool (pool);

			appendPaths();
			
			renameDublicatedFieldNames();
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
			
			String absoluteDirPath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(System.getProperty("file.separator")));
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
		dublicateFieldNames = fieldNames.getDuplicatedFieldNames ();
		
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
		
		// If parentclass is not inside "classNames" it should be loaded
		//
		boolean found = false;
		for (ClassPoolEntity entity : classNames)
			if (entity.getClassName().equals(parentClassName))
				found = true;
		
		if (!found)
			return true;
		
		// If parentclass is inside "classNames" and loaded, load class 
		// 
		for (ClassPoolEntity entity : classNames)
			if (entity.getClassName().equals(parentClassName) && entity.getIsLoaded())
					return true;
		
		return false;
	}
	
	private void runMain(String mainClass, String[] args) throws Throwable
	{
//		try
//		{
			classLoader.run(mainClass, args);
			
//		}
//		catch (Throwable e) 
//		{
//			System.out.println("### ABORTED SIMULATION-RUN! (in dynamicDetector.runMain) ###");
//		}
	}
		
	public MultiMap getResult() 
	{
		return DynamicDataContainer.getInstance().getFieldWriteTraps();
	}
	
}
