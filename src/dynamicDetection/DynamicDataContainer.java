package dynamicDetection;
import controller.MultiMap;
import javassist.ClassPool;
import javassist.NotFoundException;


public class DynamicDataContainer
{
	private static DynamicDataContainer instance = null;
	private static MultiMap keyValueContainer;
	
	private ClassPool pool;
	
	private DynamicDataContainer() 
	{
		keyValueContainer = new MultiMap();
	}

	public static DynamicDataContainer getInstance() 
	{
	    if (instance == null)
	        instance = new DynamicDataContainer();

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
}
