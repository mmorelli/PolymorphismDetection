package staticDetection;

import java.io.File;

import controller.Detector;
import controller.MultiMap;

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

		try 
		{
			pool.appendClassPath(new File("src\\libs\\play-1.2.2.jar").getAbsolutePath());
			pool.appendClassPath(new File("src\\libs\\junit-4.4.jar").getAbsolutePath());
			pool.appendClassPath(new File("src\\libs\\netty-3.2.3.jar").getAbsolutePath());
		} 
		catch (NotFoundException e) 
		{
			System.out.println("### FAILED LOADING NEEDED LIBS ###");
			e.printStackTrace();
		}
		
		iterateClasses (new File (absolutPathToBinaryDirectory));
	}
	
	
	private void iterateClasses(File file) throws CannotCompileException, NotFoundException 
	{
		if (file.getName().endsWith(".class") && file.isFile())
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
