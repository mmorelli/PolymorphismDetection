
public class DataContainer 
{
	private static DataContainer instance = null;
	
	private static MultiMap container = new MultiMap();
	
	private DataContainer() 
	{
		container = new MultiMap();
	}

	public static DataContainer getInstance() 
	{
	    if (instance == null)
	        instance = new DataContainer();

		return instance;
	}
	
	public static void addFieldWriteTrap(String classType, String fieldName, String fieldValue) 
	{
		container.add(classType, fieldName, fieldValue); 
	}
	
	
	public MultiMap getFieldWriteTraps() 
	{
		return container;
	}
	
}
