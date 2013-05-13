import javassist.ClassPool;
import javassist.NotFoundException;


public class DataContainer 
{
	private static DataContainer instance = null;
	
	private static MultiMap keyValueContainer;
	private static PackageContainer packageNamesOfClasses;
	
	private ClassPool pool;
	
	private DataContainer() 
	{
		keyValueContainer = new MultiMap();
		packageNamesOfClasses = new PackageContainer();
	}

	public static DataContainer getInstance() 
	{
	    if (instance == null)
	        instance = new DataContainer();

		return instance;
	}
	
	public static void addFieldWriteTrap(String key, String value) 
	{
		keyValueContainer.add(key, value); 
	}
	
	
	public MultiMap getFieldWriteTraps() 
	{
		return keyValueContainer;
	}

	public void setPool(ClassPool pool) 
	{
		this.pool = pool;	
	}

	public String getFieldType(String className, String fieldname) 
	{
		try 
		{
			return pool.get(className).getField(fieldname).getType().getName();		
		} 
		catch (NotFoundException e) 
		{
			e.printStackTrace();
		}
		
		return "noFieldType";
	}
	
	public void addPackageNameOfClass(String absoluteFilePath) 
	{
		packageNamesOfClasses.add (absoluteFilePath);
	}
	
	public String getPackageNameOfClass(String className) 
	{
		String packageName = packageNamesOfClasses.getPackageNameOfClass (className);
		
		if (packageName == null)
			return "defaultPackage";
				
		return packageName;
	}	
}
