package dynamicDetection;

import java.util.ArrayList;

public class ClassFieldMap 
{
	private String className;
	private ArrayList<String> fieldNames;
	
	public ClassFieldMap(String className, String fieldName) 
	{
		this.className = className;
		this.fieldNames = new ArrayList<String>();
		this.fieldNames.add(fieldName);
	}
	
	public void addField (String fieldName) 
	{
		if (!fieldNames.contains(fieldName))
			this.fieldNames.add(fieldName);
	}
	
	public String getClassName() 
	{
		return className;
	}
	
	public void setClassName(String className) 
	{
		this.className = className;
	}
	
	public ArrayList<String> getFields() 
	{
		return fieldNames;
	}
}
