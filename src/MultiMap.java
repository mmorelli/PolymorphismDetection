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
	
	public void add (String fieldType, String fieldname, String fieldValueType)
	{
		String key = fieldType + "-" + fieldname;
		
		if (!multiMap.containsKey(key))
		{
			ArrayList<String> newList = new ArrayList<String>();
			newList.add(fieldValueType);
			multiMap.put(key, newList);	
		}
		else
		{
			if (!multiMap.get(key).contains(fieldValueType))
				multiMap.get(key).add(fieldValueType);
		}
	}
	
	private int getValueCount (String key)
	{
		return multiMap.get(key).size();		
	}
	
	public void printOut ()
	{
		Set<String> set = multiMap.keySet();
		for (String key : set)
		{
			if (getValueCount (key) > 1)
				System.out.println("Field: " + extractFieldName(key) 
								+ " (Type:" + extractFieldType(key) + ") is polymorphic");
		}
	}
	
	private String extractFieldName(String key) 
	{
		int index = key.lastIndexOf('-');
		return key.substring(index + 1);
	}
	
	private String extractFieldType(String key) 
	{
		int index = key.lastIndexOf('-');
		return key.substring(0, index);
	}

	public String toString ()
	{
		return null;
	}
}


