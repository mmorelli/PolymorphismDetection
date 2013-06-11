package staticDetection;

import java.io.File;

import controller.Detector;
import controller.MultiMap;
import dynamicDetection.DynamicDataContainer;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;


public class StaticDetector extends Detector 
{
	private ClassPool pool;
	
	public StaticDetector (String absolutPathToBinaryDirectory) throws Throwable
	{
		super (absolutPathToBinaryDirectory);
		
		pool = ClassPool.getDefault();	
		pool.insertClassPath(absolutPathToBinaryDirectory);
	}

	public void run() throws CannotCompileException, NotFoundException 
	{
		System.out.println("run StaticDetector..");

		appendLibrariesToPool (pool);
		
		iterateClasses (new File (absolutPathToBinaryDirectory));
	}
	
	private void iterateClasses(File file) throws CannotCompileException, NotFoundException 
	{
		if (file.getName().endsWith(".class") && file.isFile() /*&& ! file.getName().contains("Test")*/) // ignore Unittest class-files!
		{
			String packageName = getPackageNameFromPath (file.getAbsolutePath());
			CtClass ctClass = pool.get(packageName + getNameWithoutExtension(file.getName()));
			ctClass.instrument (new StaticFieldReader());
		}
	    
	    if (file.isDirectory())
	    {
		    File[] children = file.listFiles();
		    for (File child : children) 
		    {
		    	iterateClasses(child);
		    }
	    }
	}

	public MultiMap getResult() 
	{
		return StaticDataContainer.getInstance().getMultiMap();
	}

}
