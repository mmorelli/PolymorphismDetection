package staticDetection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import controller.MultiMap;

public class StaticDataContainer
{
	private static StaticDataContainer instance = null;
	
	private static MultiMap keyValueContainer;
	private static HashMap <String, String> writeAccesses;
	private static HashMap <String, String> readAccesses;
	private static HashMap <String, String> returnValues;
	private static ArrayList<String> casts;
	
	private StaticDataContainer() 
	{
		keyValueContainer = new MultiMap();
		writeAccesses	  = new HashMap <String, String>();
		readAccesses 	  = new HashMap <String, String>();
		returnValues 	  = new HashMap <String, String>();
		casts 			  = new ArrayList<String>();
	}

	public static StaticDataContainer getInstance() 
	{
	    if (instance == null)
	        instance = new StaticDataContainer();

		return instance;
	}
	
	public void addCastAtLine(String id) 
	{
		if (!casts.contains(id))
			casts.add(id);
	}
	
	public void addReturnType(String id, String keyString) 
	{
		returnValues.put(id, keyString);
	}

	public void addFieldKey(String id, String keyString) 
	{
		writeAccesses.put(id, keyString);
	}

	public void addFieldValue(String id, String valueString) 
	{
		// richtiger type (valuestring) holen [todo]
		
		readAccesses.put(id, valueString);
	}

	public MultiMap getMultiMap() 
	{	
		mergeKeyAndValues ();
		
		return keyValueContainer;
	}

	// Merges the two hashmaps "keys" and "values" (if they have both the same Id they are merged)
	// and saves the result to the member keyValueContainer
	//
	private void mergeKeyAndValues() 
	{
		Set<String> set = writeAccesses.keySet();
		for (String key : set)
		{
			if (returnValues.containsKey(key) && !casts.contains(key))
			{
				String theKey = writeAccesses.get(key);
				String value = returnValues.get(key);
				
				keyValueContainer.add(theKey, value);	
			}
			
			else if (readAccesses.containsKey(key))
			{
				String theKey = writeAccesses.get(key);
				String value = readAccesses.get(key);
				
				keyValueContainer.add(theKey, value);
			}
		}
	}


	
	
//	// Merges the two hashmaps "keys" and "values" (if they have both the same Id they are merged)
//	// and saves the result to the member keyValueContainer
//	//
//	private void mergeKeyAndValues() 
//	{
//		Set<String> set = writeAccesses.keySet();
//		for (String key : set)
//		{
//			if (readAccesses.containsKey(key))
//			{
//				String theKey = writeAccesses.get(key);
//				String value = readAccesses.get(key);
//				
//				keyValueContainer.add(theKey, value);
//			}
//		}
//	}
}
