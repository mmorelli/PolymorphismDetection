import java.io.File;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;


public class StaticDetector 
{
	private String absolutPathToBinaryDirectory;
	private ClassPool pool;
	private String curPackageName;
	
	
	public StaticDetector (String absolutPathToBinaryDirectory) throws Throwable
	{
		this.absolutPathToBinaryDirectory = absolutPathToBinaryDirectory;
		pool = ClassPool.getDefault();	
		pool.insertClassPath(absolutPathToBinaryDirectory);
		curPackageName = "";
	}

	public void run() throws NotFoundException, CannotCompileException 
	{
		System.out.println("run StaticDetector..");
		
		iterateClasses (new File (absolutPathToBinaryDirectory));
		MultiMap result = StaticDataContainer.getInstance().getMultiMap();
		
		System.out.println("RESULT..");
		result.printOut();
		result.printAll();
	}
	
	
	private void iterateClasses(File file) throws NotFoundException, CannotCompileException 
	{
		if (file.getName().endsWith(".class") && file.isFile())
		{
			CtClass ctClass = pool.get(curPackageName + getNameWithoutExtension(file.getName()));
			ctClass.instrument (new StaticFieldReader(curPackageName));
		}
	    
	    if (file.isDirectory())
	    {
	    	if (!file.getName().equals("bin"))
	    		curPackageName += file.getName() + ".";
	    	
		    File[] children = file.listFiles();
		    for (File child : children) 
		    {
		    	iterateClasses(child);
		    }
	    }

	}
	
	private static String getNameWithoutExtension (String fileName)
	{
		int index = fileName.lastIndexOf('.');
		return fileName.substring(0, index);
	}

}
