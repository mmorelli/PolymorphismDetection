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
	public void test1Package1() throws Throwable
	{
		StaticDetector detector = new StaticDetector(new File("src" + d + "testFiles"+ d + "testPackage1").getAbsolutePath());
		detector.run();
		MultiMap map = detector.getResult();
		
		map.printDump();
		map.printPolymorphicFields();
		
		HashMap<String, ArrayList<String>> hm = map.getMultiMap();

		// Ignore Primitve Datatypes
		//
		assertFalse(hm.containsKey("MainClass:int:aInt3"));
		assertFalse(hm.containsKey("MainClass:boolean:aBool1"));
		assertFalse(hm.containsKey("MainClass:char:aChar1"));
		assertFalse(hm.containsKey("MainClass:long:aLong1"));
		
		assertTrue(hm.containsKey("MainClass:java.lang.String:aString1"));
		assertTrue(hm.get("MainClass:java.lang.String:aString1").size() == 1);
		assertTrue(hm.get("MainClass:java.lang.String:aString1").contains("java.lang.String"));
		
		assertTrue(hm.containsKey("MainClass:java.lang.String:aString4"));
		assertTrue(hm.get("MainClass:java.lang.String:aString4").size() == 1);
		assertTrue(hm.get("MainClass:java.lang.String:aString4").contains("java.lang.String"));
		
		assertTrue(hm.containsKey("MainClass:Interface:I"));
		assertTrue(hm.get("MainClass:Interface:I").size() == 2);
		assertTrue(hm.get("MainClass:Interface:I").contains("A"));
		assertTrue(hm.get("MainClass:Interface:I").contains("B"));
	}
	

}
