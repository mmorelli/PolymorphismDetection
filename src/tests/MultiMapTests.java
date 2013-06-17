package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Set;

import org.junit.*;

import controller.MultiMap;

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
		
		HashMap<String, String> dublicates = map.getDuplicatedFieldNames();
		Set<String> keys = dublicates.keySet();
		
	    assertTrue (keys.size() == 2);
	    assertTrue (keys.contains("A"));
	    assertTrue (keys.contains("B"));
	    assertFalse (keys.contains("C"));
	}
	
	@Test
	public void getDuplicatedValuesTest2()
	{
		map.add("B", "a");
		map.add("B", "a");
		
		HashMap<String, String> dublicates = map.getDuplicatedFieldNames();
		Set<String> keys = dublicates.keySet();
		
	    assertTrue (keys.size() == 0);
	    assertFalse (keys.contains("B"));
	    assertFalse (keys.contains("C"));
	}
	
	@Test
	public void getDuplicatedValuesTest3()
	{
		map.add("A", "a");
		map.add("B", "a");
		map.add("C", "a");
		map.add("D", "d");
		
		HashMap<String, String> dublicates = map.getDuplicatedFieldNames();
		Set<String> keys = dublicates.keySet();
		
	    assertTrue (keys.size() == 3);
	    assertTrue (keys.contains("A"));
	    assertTrue (keys.contains("B"));
	    assertTrue (keys.contains("C"));
	    assertFalse (keys.contains("D"));
	}
	
	@Test
	public void getDuplicatedValuesTest4()
	{
		map.add("A", "a");
		map.add("B", "b");
		map.add("C", "a");
		map.add("D", "d");
		
		HashMap<String, String> dublicates = map.getDuplicatedFieldNames();
		Set<String> keys = dublicates.keySet();
		
	    assertTrue (keys.size() == 2);
	    assertTrue (keys.contains("A"));
	    assertFalse (keys.contains("B"));
	    assertTrue (keys.contains("C"));
	    assertFalse (keys.contains("D"));
	}
}
