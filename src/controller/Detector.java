package controller;

import java.io.File;

import javassist.ClassPool;
import javassist.NotFoundException;

public class Detector 
{
	protected String absolutPathToBinaryDirectory;
	private String sourceRootDirectoryName;
	
	public Detector (String absolutPathToBinaryDirectory)
	{
		this.absolutPathToBinaryDirectory = absolutPathToBinaryDirectory;
		this.sourceRootDirectoryName = extractSourceDirName ();
	}
	
	private String extractSourceDirName() 
	{
		int lastPathDelimiterIdx = absolutPathToBinaryDirectory.lastIndexOf(System.getProperty("file.separator").charAt(0));
		String dirName = absolutPathToBinaryDirectory.substring(lastPathDelimiterIdx + 1); 
		
		return dirName;
	}

	protected String getPackageNameFromPath (String absoluteFilePath)
	{
		int posOfLastPathSep = absoluteFilePath.lastIndexOf(System.getProperty("file.separator"));
		int posOfBinPathEnd = absoluteFilePath.indexOf(sourceRootDirectoryName) + sourceRootDirectoryName.length();
		
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
	
	protected String getNameWithoutExtension (String fileName)
	{
		int index = fileName.lastIndexOf('.');
		return fileName.substring(0, index);
	}
	
	protected void appendLibrariesToPool (ClassPool pool) 
	{
		try 
		{
			pool.appendClassPath(new File("src\\libs\\play-1.2.2.jar").getAbsolutePath());
			pool.appendClassPath(new File("src\\libs\\junit-4.4.jar").getAbsolutePath());
			pool.appendClassPath(new File("src\\libs\\netty-3.2.3.jar").getAbsolutePath());
			
			// TODO noch in richtiges dir
			//
			pool.appendClassPath(new File("D:\\Bachelorarbeit\\Libs\\javax.jar").getAbsolutePath());
			pool.appendClassPath(new File("D:\\Bachelorarbeit\\Libs\\apache-commons.jar").getAbsolutePath());
			pool.appendClassPath(new File("D:\\Bachelorarbeit\\Libs\\jdom-1.1.jar").getAbsolutePath());
		} 
		catch (NotFoundException e) 
		{
			System.out.println("### FAILED LOADING NEEDED LIBS ###");
			e.printStackTrace();
		}
		
	}
}
