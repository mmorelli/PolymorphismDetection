package controller;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
	
	public int getValueCountOfKey (String key)
	{
		if (multiMap.containsKey(key))
			return multiMap.get(key).size();
		else
			return 0;
	}
	
	// TODO finish it!
//	public boolean eliminatePrimitiveDataTypes () 
//	{
//		Set<String> set = multiMap.keySet();
//		for (String key : set)
//		{
//			if (isPrimitveDataType (key))
//				set.
//		}
//		
//		return (keyString.contains("int") ||
//				keyString.contains("char") ||
//				keyString.contains("long") ||
//				keyString.contains("Long"));
//	}
	
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
	

	// Returns over all ClassNames (Key) the fileNames (values) which are duplicated
	//
	public HashMap<String/*ClassName*/, String/*FieldName*/> getDuplicatedFieldNames() 
	{
		HashMap<String, String> dublicates = new HashMap<String, String> ();
		
		Set<String> set = multiMap.keySet();
		for (String key : set)
		{
			ArrayList<String> values = multiMap.get(key);
			for (String value : values)
			{
//				System.out.println(key +  " " + value);
				
				if (containsValueMoreThanOnce(value))
					dublicates.put(key, value);
			}
		}
		
		return dublicates; 
	}

	private boolean containsValueMoreThanOnce(String value) 
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


