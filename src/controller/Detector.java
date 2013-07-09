package controller;

import java.io.File;
import javassist.ClassPool;
import javassist.NotFoundException;

public class Detector 
{
	protected final char pathDelimiter = System.getProperty("file.separator").charAt(0);
	
	protected String absolutePathToRootDirectory;
	private String rootDirectoryName;
	
	public Detector (String absolutePathToRootDirectory)
	{
		this.absolutePathToRootDirectory = absolutePathToRootDirectory;
		this.rootDirectoryName = extractSourceDirName ();
	}
	
	private String extractSourceDirName() 
	{
		int lastPathDelimiterIdx = absolutePathToRootDirectory.lastIndexOf(pathDelimiter);
		String rootDirName = absolutePathToRootDirectory.substring(lastPathDelimiterIdx + 1); 
		
		return rootDirName;
	}

	protected String getPackageNameFromPath (String absoluteFilePath)
	{
		int lastPathDelimiterIdx = absoluteFilePath.lastIndexOf(pathDelimiter);
		int posRootDirPathEnd = absoluteFilePath.indexOf(rootDirectoryName) + rootDirectoryName.length();
		
		String packageName;
		if (posRootDirPathEnd != lastPathDelimiterIdx)
		{
			packageName = absoluteFilePath.substring(posRootDirPathEnd + 1, lastPathDelimiterIdx);
			packageName = packageName.replace(pathDelimiter, '.') + ".";
		}
		else
			packageName = "";

		return packageName;
	}
	
	protected String getNameWithoutExtension (String fileName)
	{
		int index = fileName.lastIndexOf('.');
		return fileName.substring(0, index);
	}
	
	protected void appendLibrariesToPool (ClassPool pool) 
	{
		try 
		{
			File libsDirectory = new File("libs");
			if (libsDirectory.isDirectory())
			{
			    File[] children = libsDirectory.listFiles();
			    for (File child : children) 
			    {
			    	pool.appendClassPath(child.getAbsolutePath());
			    }
		    }
		} 
		catch (NotFoundException e) 
		{
			System.out.println("### FAILED LOADING LIBS FROM DIR: src\\libs ###");
			e.printStackTrace();
		}
		
	}
}
