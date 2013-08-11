package staticDetection;

import java.util.HashMap;
import java.util.Set;
import controller.MultiMap;

public class StaticDataContainer
{
	private static StaticDataContainer instance = null;
	
	private static MultiMap keyValueContainer;
	private static HashMap <String, String> writeAccesses;
	private static HashMap <String, String> readAccesses;
	private static HashMap <String, String> returnTypes;
	private static HashMap <String, String> casts;
	
	private StaticDataContainer() 
	{
		keyValueContainer = new MultiMap();
		writeAccesses	  = new HashMap <String, String>();
		readAccesses 	  = new HashMap <String, String>();
		returnTypes 	  = new HashMap <String, String>();
		casts 			  = new HashMap <String, String>();
	}

	public static StaticDataContainer getInstance() 
	{
	    if (instance == null)
	        instance = new StaticDataContainer();

		return instance;
	}
	
	public void addCastAtLine(String id, String keyString) 
	{	
		casts.put(id, keyString);
	}
	
	public void addReturnType(String id, String keyString) 
	{
		returnTypes.put(id, keyString);
	}

	public void addFieldKey(String id, String keyString) 
	{
		writeAccesses.put(id, keyString);
	}

	public void addFieldValue(String id, String valueString) 
	{
		readAccesses.put(id, valueString);
	}

	public MultiMap getMultiMap() 
	{	
		mergeKeyAndValues ();
		
		return keyValueContainer;
	}

	private void mergeKeyAndValues() 
	{
		Set<String> set = writeAccesses.keySet();
		for (String key : set)
		{
			String theKey = writeAccesses.get(key);
			
			if (readAccesses.containsKey(key) && casts.containsKey(key))
			{
				String value = casts.get(key);
				keyValueContainer.add(theKey, value);	
			}
			
			else if (readAccesses.containsKey(key) && returnTypes.containsKey(key) 
					&& !casts.containsKey(key))
			{
				String value = returnTypes.get(key);
				keyValueContainer.add(theKey, value);	
			}
			
			else if (readAccesses.containsKey(key))
			{
				String value = readAccesses.get(key);
				keyValueContainer.add(theKey, value);
			}
		}
	}
}
