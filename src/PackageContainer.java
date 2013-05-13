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
		int posOfLastPathSep = absoluteFilePath.lastIndexOf("\\");
		String fullClassName = absoluteFilePath.substring(posOfLastPathSep + 1, absoluteFilePath.length());
		
		
		// TODO refac and not use "bin"
//		int posOfBinPathEnd = absoluteFilePath.indexOf("bin") + 4;
		int posOfBinPathEnd = absoluteFilePath.indexOf("bin") + 3;
		String packageString = absoluteFilePath.substring(posOfBinPathEnd, posOfLastPathSep);
		
		String packageName = packageString.replace('\\', '.');
		
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
