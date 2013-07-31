package staticDetection;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * The InterfaceFieldContainer is an instrument to evaluate the HEURISTIC: "Fields with type Interface are polymorphic"
 * Returns the size of detected fields-writer-accesses with type INTERFACE
 */

public class InterfaceFieldContainer
{
	private static InterfaceFieldContainer instance = null;
	private static ArrayList<String> container;

	private InterfaceFieldContainer() 
	{
		container = new ArrayList<String>();
	}

	public static InterfaceFieldContainer getInstance() 
	{
	    if (instance == null)
	        instance = new InterfaceFieldContainer();

		return instance;
	}

	public void addField(String keyString) 
	{
		if (!container.contains(keyString))
			container.add(keyString);
	}
	
	public int size ()
	{
		return container.size();
	}

	public void writeDumpToFile(String fileName) 
	{
		try 
		{
			PrintWriter writer = new PrintWriter (fileName, "UTF-8");
			
			writer.println("***Detected interface field accesses: " + size () + " ***");
			
			for (String each : container)
				writer.println(each);
			
			writer.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void clear ()
	{
		instance = null;
	}
}
