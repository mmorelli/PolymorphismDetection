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
	
	public void printOut ()
	{
		Set<String> set = multiMap.keySet();
		for (String key : set)
		{
			if (getValueCount (key) > 1)
				System.out.println(key +  " is polymorphic");
		}
	}

	public String toString ()
	{
		return null;
	}
}


