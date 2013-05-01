import java.util.ArrayList;


public class DataContainer 
{
	private static DataContainer instance = null;
	
	private static ArrayList<String> list; 
	
	private DataContainer() 
	{
		list = new ArrayList<String>();
	}

	public static DataContainer getInstance() 
	{
	    if (instance == null)
	    {
	        instance = new DataContainer();
	        System.out.println("singleton created");
	    }
	
		return instance;
	}
	
	public static void addTrap(String trapName) 
	{
		list.add (trapName);
		 System.out.println("ADDED" + trapName);
	}
	
	public ArrayList<String> getTraps() 
	{
		return list;
	}
	
}
