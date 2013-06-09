package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class MultiMap 
{
	private HashMap<String, ArrayList<String>> multiMap;
	
	public MultiMap ()
	{
		multiMap = new HashMap<String, ArrayList<String>>(); 
	}
	
	public void add (String key, String value)
	{
		if (!multiMap.containsKey(key))
		{
			ArrayList<String> newList = new ArrayList<String>();
			newList.add(value);
			multiMap.put(key, newList);	
		}
		else
		{
			if (!multiMap.get(key).contains(value))
				multiMap.get(key).add(value);
		}
	}
	
	private int getValueCount (String key)
	{
		return multiMap.get(key).size();		
	}
	
	public void printPolymorphicFields ()
	{
		System.out.println("***Polymorphic Fields:***");
		
		Set<String> set = multiMap.keySet();
		for (String key : set)
		{
			if (getValueCount (key) > 1)
				System.out.println(key);
		}
		
		System.out.println("***Polymorphic Fields end***");
	}

	public void printDump() 
	{
		System.out.println("***Dump:***");
		
		Set<String> set = multiMap.keySet();
		for (String key : set)
		{
			System.out.println("KEY: " + key);
			ArrayList<String> values = multiMap.get(key);
			for (String value : values)
			{
				System.out.println(" VALUE(S): " + value);
			}
		}
		
		System.out.println("***Dump end***");
		
	}

	// This method returns a Multimap with the fieldnames wich are twice or more seen
	//
	public HashMap<String, String> getDublicates() 
	{
		HashMap<String, String> dublicates = new HashMap<String, String> ();
		
		Set<String> set = multiMap.keySet();
		for (String key : set)
		{
			ArrayList<String> values = multiMap.get(key);
			for (String value : values)
			{
				System.out.println(key +  " " + value);
				
				if (containsValueMoreThanOneTime(value))
					dublicates.put(key, value);
			}
		}
		
		return dublicates; 
	}

	private boolean containsValueMoreThanOneTime(String value) 
	{
		int valueCount = 0;
		
		Set<String> set = multiMap.keySet();
		for (String key : set)
		{
			ArrayList<String> values = multiMap.get(key);
			for (String valueOfMultiMap : values)
			{
				if (valueOfMultiMap.equals(value))
				{
					valueCount++;
					
					if (valueCount > 1)
						return true;
				}
			}
		}
		
		return false;
	}

}


