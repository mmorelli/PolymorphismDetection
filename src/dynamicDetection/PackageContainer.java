package dynamicDetection;
import java.util.ArrayList;


public class PackageContainer 
{
	private ArrayList<PackageAndClassPair> map;
	
	public PackageContainer ()
	{
		map = new ArrayList<PackageAndClassPair>();
	}
	
	public void add (String absoluteFilePath) 
	{
		int posOfLastPathSep = absoluteFilePath.lastIndexOf(System.getProperty("file.separator"));
		String fullClassName = absoluteFilePath.substring(posOfLastPathSep + 1, absoluteFilePath.length());

		int posOfBinPathEnd = absoluteFilePath.indexOf("bin") + 3;
		
		String packageString;
		if (posOfBinPathEnd != posOfLastPathSep)
			packageString = absoluteFilePath.substring(posOfBinPathEnd + 1, posOfLastPathSep);
		else
			packageString = "defaultPackage";
		
		String pathDelimiter = System.getProperty("file.separator");
		String packageName = packageString.replace(pathDelimiter.charAt(0), '.');
		
		map.add(new PackageAndClassPair (fullClassName, packageName)); 
	}

	public String getPackageNameOfClass (String className) 
	{
		for (PackageAndClassPair each : map)
		{
			if (each.getClassName().equals(className))
				return each.getPackageName();
		}

		return null;
	}
	
	private class PackageAndClassPair
	{	
		private String className;	
		private String packageName;
	
		public PackageAndClassPair (String className, String packageName)
		{
			this.className = className;
			this.packageName = packageName;
		}

		public Object getClassName() 
		{
			return className;
		}
		
		public String getPackageName() 
		{
			return packageName;
		}
	}
}
