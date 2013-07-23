package controller;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import dynamicDetection.ClassFieldMap;

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
	
	public int getValueCountOfKey (String key)
	{
		if (multiMap.containsKey(key))
			return multiMap.get(key).size();
		else
			return 0;
	}
	
	public void printPolymorphicFields ()
	{
		System.out.println("***Polymorphic Fields:***");
		
		Set<String> set = multiMap.keySet();
		for (String key : set)
		{
			if (getValueCountOfKey (key) > 1)
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
	
	public void writeDumpToFile(String filename) 
	{
		try 
		{
			PrintWriter writer = new PrintWriter (filename, "UTF-8");
			
			writer.println("***Dump:***");
			
			Set<String> set = multiMap.keySet();
			for (String key : set)
			{
				writer.println("KEY: " + key);
				ArrayList<String> values = multiMap.get(key);
				for (String value : values)
				{
					writer.println(" VALUE(S): " + value);
				}
			}
			
			writer.println("***Dump end***");
			
			writer.println("***Polymorphic Fields:***");
			
			set = multiMap.keySet();
			for (String key : set)
			{
				if (getValueCountOfKey (key) > 1)
					writer.println(key);
			}
			
			writer.println("***Polymorphic Fields end***");
			
			writer.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("FileNotFound!");
		} 
		catch (UnsupportedEncodingException e) 
		{
			System.out.println("UnsupportedEncoding!");
		}
	}
	
	public ArrayList<ClassFieldMap> getDuplicates() 
	{
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<ClassFieldMap> dublicates = new ArrayList<ClassFieldMap>();
		
		Set<String> set = multiMap.keySet();
		for (String className : set)
		{
			ArrayList<String> values = multiMap.get(className);
			for (String valueOfMultiMap : values)
			{
				if (names.contains(valueOfMultiMap))
					dublicates.add(new ClassFieldMap(className, valueOfMultiMap));
				else	
					names.add(valueOfMultiMap);
			}
		}
	
		return dublicates;
	}

	// For unit tests only
	//
	public HashMap<String, ArrayList<String>> getMultiMap ()
	{
		return multiMap;
	}
}


