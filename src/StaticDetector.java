import java.io.File;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;


public class StaticDetector 
{
	private String absolutPathToBinaryDirectory;
	private ClassPool pool;
	
	public StaticDetector (String absolutPathToBinaryDirectory) throws Throwable
	{
		this.absolutPathToBinaryDirectory = absolutPathToBinaryDirectory;
		pool = ClassPool.getDefault();	
		pool.insertClassPath(absolutPathToBinaryDirectory);
	}

	public void run() throws NotFoundException, CannotCompileException 
	{
		System.out.println("run StaticDetector..");
		
		iterateClasses (new File (absolutPathToBinaryDirectory));
		MultiMap result = StaticDataContainer.getInstance().getMultiMap();
		
		System.out.println("RESULT..");
		result.printPolymorphicFields();
		result.printDump();
	}
	
	
	private void iterateClasses(File file) throws NotFoundException, CannotCompileException 
	{
		if (file.getName().endsWith(".class") && file.isFile())
		{
			String packageName = getPackageName (file.getAbsolutePath());
			
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
	
	private String getPackageName (String absoluteFilePath)
	{
		int posOfLastPathSep = absoluteFilePath.lastIndexOf(System.getProperty("file.separator"));
		int posOfBinPathEnd = absoluteFilePath.indexOf("bin") + 3;
		
		String packageName;
		if (posOfBinPathEnd != posOfLastPathSep)
		{
			packageName = absoluteFilePath.substring(posOfBinPathEnd + 1, posOfLastPathSep);
			packageName = packageName.replace(System.getProperty("file.separator").charAt(0), '.') + ".";
		}
		else
			packageName = "";

		return packageName;
	}
	
	private String getNameWithoutExtension (String fileName)
	{
		int index = fileName.lastIndexOf('.');
		return fileName.substring(0, index);
	}

}
