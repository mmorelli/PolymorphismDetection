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
	public void staticDetectionTest1() throws Throwable
	{
		StaticDetector detector = new StaticDetector(new File("bin" + d + "testFiles"+ d + "staticTestFiles").getAbsolutePath());
		detector.run();
		MultiMap map = detector.getResult();
		
		HashMap<String, ArrayList<String>> hm = map.getMultiMap();
		assertTrue(hm.size() == 1);
		assertTrue(hm.containsKey("StaticTest1:java.lang.String:aString1"));
		assertTrue(hm.get("StaticTest1:java.lang.String:aString1").size() == 2);
		assertTrue(hm.get("StaticTest1:java.lang.String:aString1").get(0).equals("java.lang.String:aString2"));
		assertTrue(hm.get("StaticTest1:java.lang.String:aString1").get(1).equals("java.lang.String:aString3"));
		assertFalse(hm.containsKey("java.lang.String:aString2"));
		
//		map.printDump();
//		map.printPolymorphicFields();
	}
}
