import javassist.ClassPool;
import javassist.NotFoundException;


public class DataContainer 
{
	private static DataContainer instance = null;
	
	private static MultiMap container = new MultiMap();
	
	private ClassPool pool;
	
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
	
	public static void addFieldWriteTrap(String key, String value) 
	{
		container.add(key, value); 
	}
	
	
	public MultiMap getFieldWriteTraps() 
	{
		return container;
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
	
	public String getPackageName(String className) 
	{
		
		try 
		{
			return pool.get(className).getPackageName();
		} 
		catch (NotFoundException e) 
		{
			e.printStackTrace();
		}
		
		return "noPackageName";
	}
	
}
