package staticDetection;

import java.util.HashMap;
import java.util.Set;

import controller.MultiMap;

public class StaticDataContainer
{
	private static StaticDataContainer instance = null;
	
	private static MultiMap keyValueContainer;
	private static HashMap <String, String> keys;
	private static HashMap <String, String> values;
	
	private StaticDataContainer() 
	{
		keyValueContainer = new MultiMap();
		keys = new HashMap <String, String>();
		values = new HashMap <String, String>();
	}

	public static StaticDataContainer getInstance() 
	{
	    if (instance == null)
	        instance = new StaticDataContainer();

		return instance;
	}

	public void addFieldKey(String id, String keyString) 
	{
		keys.put(id, keyString);
	}

	public void addFieldValue(String id, String valueString) 
	{
		values.put(id, valueString);
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
		Set<String> set = keys.keySet();
		for (String key : set)
		{
			if (values.containsKey(key))
			{
				String theKey = keys.get(key);
				String value = values.get(key);
				
				keyValueContainer.add(theKey, value);
			}
		}
		
	}
	
	
}
