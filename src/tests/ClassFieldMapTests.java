package tests;

import static org.junit.Assert.*;
import org.junit.*;
import dynamicDetection.ClassFieldMap;

public class ClassFieldMapTests 
{	
	@Test
	public void setterAndGetterTests1()
	{
		ClassFieldMap map = new ClassFieldMap("classA", "fieldA");
		
		assertTrue (map.getClassName().equals("classA"));
		assertTrue (map.getFields().size() == 1);
		assertTrue (map.getFields().contains("fieldA"));
	}
	
	@Test
	public void setterAndGetterTests2()
	{
		ClassFieldMap map = new ClassFieldMap("classA", "fieldA");
		map.addField("fieldB");
		map.setClassName("classB");
		
		assertTrue (map.getClassName().equals("classB"));
		assertTrue (map.getFields().size() == 2);
		assertTrue (map.getFields().contains("fieldA"));
		assertTrue (map.getFields().contains("fieldB"));
	}
	
	@Test
	public void setterAndGetterTests3()
	{
		ClassFieldMap map = new ClassFieldMap("classA", "fieldA");
		map.addField("fieldB");
		map.addField("fieldB");
		map.addField("fieldB");
		map.addField("fieldB");
		map.addField("fieldC");
		
		assertTrue (map.getClassName().equals("classA"));
		assertTrue (map.getFields().size() == 3);
		assertTrue (map.getFields().contains("fieldA"));
		assertTrue (map.getFields().contains("fieldB"));
		assertTrue (map.getFields().contains("fieldC"));
	}
	
	
	
}
