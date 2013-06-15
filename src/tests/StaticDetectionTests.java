package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.*;

import controller.MultiMap;

import staticDetection.StaticDetector;

public class StaticDetectionTests 
{
	private char d = System.getProperty("file.separator").charAt(0);
	
	@Test
	public void ignorePrimitveDatatypes() throws Throwable
	{
		StaticDetector detector = new StaticDetector(new File("src" + d + "testFiles"+ d + "staticTestFiles").getAbsolutePath());
		detector.run();
		MultiMap map = detector.getResult();
		
//		map.printDump();
//		map.printPolymorphicFields();
		
		HashMap<String, ArrayList<String>> hm = map.getMultiMap();

		// Ignore Primitve Datatypes
		//
		assertFalse(hm.containsKey("StaticTest1:int:aInt3"));
		assertFalse(hm.containsKey("StaticTest1:boolean:aBool1"));
		assertFalse(hm.containsKey("StaticTest1:char:aChar1"));
		assertFalse(hm.containsKey("StaticTest1:long:aLong1"));
	}
	
	@Test
	public void nonPrimitiveDatatypes() throws Throwable
	{
		StaticDetector detector = new StaticDetector(new File("src" + d + "testFiles"+ d + "staticTestFiles").getAbsolutePath());
		detector.run();
		MultiMap map = detector.getResult();
		
		HashMap<String, ArrayList<String>> hm = map.getMultiMap();
		
		// Non-primitive Datatypes
		// Non-polymorphic
		//
		assertTrue(hm.containsKey("StaticTest1:java.lang.String:aString4"));
		assertTrue(hm.get("StaticTest1:java.lang.String:aString4").size() == 1);
		assertTrue(hm.get("StaticTest1:java.lang.String:aString4").contains("java.lang.String:aString5"));
		
		// Non-primitive Datatypes
		// Polymorphic
		//
		assertTrue(hm.containsKey("StaticTest1:java.lang.String:aString1"));
		assertTrue(hm.get("StaticTest1:java.lang.String:aString1").size() == 2);
		assertTrue(hm.get("StaticTest1:java.lang.String:aString1").contains("java.lang.String:aString2"));
		assertTrue(hm.get("StaticTest1:java.lang.String:aString1").contains("java.lang.String:aString3"));
		
		assertTrue(hm.containsKey("StaticTest1:Interface:I"));
		assertTrue(hm.get("StaticTest1:Interface:I").size() == 2);
		assertTrue(hm.get("StaticTest1:Interface:I").contains("A:classA"));
		assertTrue(hm.get("StaticTest1:Interface:I").contains("B:classB"));
			
	}
}
