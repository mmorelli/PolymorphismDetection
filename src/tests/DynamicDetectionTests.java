package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.*;

import controller.MultiMap;
import dynamicDetection.DynamicDetector;

public class DynamicDetectionTests 
{
	private char d = System.getProperty("file.separator").charAt(0);
	
	@Test
	public void test1Package1() throws Throwable
	{
		DynamicDetector detector = new DynamicDetector(new File("testFiles"+ d + "testPackage1").getAbsolutePath());
		detector.run("MainClass", null);
		MultiMap map = detector.getResult();
		
		map.printDump();
		map.printPolymorphicFields();
		
		HashMap<String, ArrayList<String>> hm = map.getMultiMap();

		assertTrue(hm.containsKey("MainClass:java.lang.String:aString1"));
		assertTrue(hm.get("MainClass:java.lang.String:aString1").size() == 1);
		assertTrue(hm.get("MainClass:java.lang.String:aString1").contains("java.lang.String"));
		
		assertTrue(hm.containsKey("MainClass:java.lang.String:aString2"));
		assertTrue(hm.get("MainClass:java.lang.String:aString2").size() == 1);
		assertTrue(hm.get("MainClass:java.lang.String:aString2").contains("java.lang.String"));
		
		assertTrue(hm.containsKey("MainClass:java.lang.String:aString3"));
		assertTrue(hm.get("MainClass:java.lang.String:aString3").size() == 1);
		assertTrue(hm.get("MainClass:java.lang.String:aString3").contains("java.lang.String"));
		
		assertTrue(hm.containsKey("MainClass:java.lang.String:aString4"));
		assertTrue(hm.get("MainClass:java.lang.String:aString4").size() == 1);
		assertTrue(hm.get("MainClass:java.lang.String:aString4").contains("java.lang.String"));
		
		assertTrue(hm.containsKey("MainClass:java.lang.String:aString5"));
		assertTrue(hm.get("MainClass:java.lang.String:aString5").size() == 1);
		assertTrue(hm.get("MainClass:java.lang.String:aString5").contains("java.lang.String"));
		
		assertTrue(hm.containsKey("MainClass:A:classA"));
		assertTrue(hm.get("MainClass:A:classA").size() == 1);
		assertTrue(hm.get("MainClass:A:classA").contains("A"));
		
		assertTrue(hm.containsKey("MainClass:B:classB"));
		assertTrue(hm.get("MainClass:B:classB").size() == 1);
		assertTrue(hm.get("MainClass:B:classB").contains("B"));
		
		assertTrue(hm.containsKey("MainClass:Interface:I"));
		assertTrue(hm.get("MainClass:Interface:I").size() == 2);
		assertTrue(hm.get("MainClass:Interface:I").contains("A"));
		assertTrue(hm.get("MainClass:Interface:I").contains("B"));
	}
		
}
