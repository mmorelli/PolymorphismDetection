package controller;

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
}
