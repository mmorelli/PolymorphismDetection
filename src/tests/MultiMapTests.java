package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.*;

import controller.MultiMap;
import dynamicDetection.ClassFieldMap;

public class MultiMapTests 
{
	private MultiMap map;
	
	@Before
	public void setUp()
	{
		map = new MultiMap();
	}
	
	@Test
	public void addTest1()
	{
		map.add("A", "a");
		map.add("A", "a");
	
		assertTrue(map.getValueCountOfKey("A") == 1);
		assertTrue(map.getValueCountOfKey("B") == 0);
		
		map.add("B", "a");
		
		assertTrue(map.getValueCountOfKey("B") == 1);
	}
	
	@Test
	public void addTest2()
	{
		map.add("A", "a");
		map.add("A", "a");
		map.add("B", "a");
		map.add("B", "b");
		map.add("B", "c");
		map.add("B", "d");
		map.add("C", "a");
		
		assertTrue(map.getValueCountOfKey("A") == 1);
		assertTrue(map.getValueCountOfKey("B") == 4);
		assertTrue(map.getValueCountOfKey("C") == 1);
		assertTrue(map.getValueCountOfKey("D") == 0);
	}
	
	@Test
	public void getDuplicatedValuesTest1()
	{
		map.add("A", "a");
		map.add("B", "a");
		
		ArrayList<ClassFieldMap> dublicates = map.getDuplicates();
		
	    assertTrue (dublicates.size() == 1);
	    assertTrue (dublicates.get(0).getClassName().equals("B"));
	    assertTrue (dublicates.get(0).getFields().size() == 1);
	    assertTrue (dublicates.get(0).getFields().get(0).equals("a"));
	}
	
	@Test
	public void getDuplicatedValuesTest2()
	{
		map.add("B", "a");
		map.add("B", "a");
		
		ArrayList<ClassFieldMap> dublicates = map.getDuplicates();
		
	    assertTrue (dublicates.size() == 0);
	    assertFalse (dublicates.contains("B"));
	    assertFalse (dublicates.contains("C"));
	}
	
	@Test
	public void getDuplicatedValuesTest3()
	{
		map.add("A", "a");
		map.add("B", "a");
		map.add("C", "a");
		map.add("D", "d");
		
		ArrayList<ClassFieldMap> dublicates = map.getDuplicates();
		
	    assertTrue (dublicates.size() == 2);
	    assertTrue (dublicates.get(0).getClassName().equals("B"));
	    assertTrue (dublicates.get(0).getFields().size() == 1);
	    assertTrue (dublicates.get(0).getFields().get(0).equals("a"));
	    assertTrue (dublicates.get(1).getClassName().equals("C"));
	    assertTrue (dublicates.get(1).getFields().size() == 1);
	    assertTrue (dublicates.get(1).getFields().get(0).equals("a"));
	}
	
	@Test
	public void getDuplicatedValuesTest4()
	{
		map.add("A", "a");
		map.add("B", "b");
		map.add("C", "a");
		map.add("D", "b");
		
		ArrayList<ClassFieldMap> dublicates = map.getDuplicates();
		
	    assertTrue (dublicates.size() == 2);
	    assertTrue (dublicates.get(0).getClassName().equals("B"));
	    assertTrue (dublicates.get(0).getFields().size() == 1);
	    assertTrue (dublicates.get(0).getFields().get(0).equals("b"));
	    assertTrue (dublicates.get(1).getClassName().equals("C"));
	    assertTrue (dublicates.get(1).getFields().size() == 1);
	    assertTrue (dublicates.get(1).getFields().get(0).equals("a"));
	}
	
	@Test
	public void getDuplicatedValuesTest5()
	{
		map.add("A", "a");
		map.add("A", "a");
		map.add("C", "c");
		map.add("D", "d");
		
		ArrayList<ClassFieldMap> dublicates = map.getDuplicates();
		
	    assertTrue (dublicates.size() == 0);
	}
}
