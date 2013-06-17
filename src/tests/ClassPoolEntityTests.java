package tests;

import static org.junit.Assert.*;

import org.junit.*;

import dynamicDetection.ClassPoolEntity;

public class ClassPoolEntityTests 
{
	@Test
	public void setterAndGetterTests1()
	{
		// Constructor Nr.1
		//
		ClassPoolEntity cpe = new ClassPoolEntity("classA");
		assertTrue(cpe.getClassName().equals("classA"));
		assertFalse(cpe.getIsLoaded());
		
		cpe.setClassName("newClassA");
		assertTrue(cpe.getClassName().equals("newClassA"));
		assertFalse(cpe.getIsLoaded());
		
		cpe.setIsLoaded(true);
		assertTrue(cpe.getClassName().equals("newClassA"));
		assertTrue(cpe.getIsLoaded());
	}
	
	@Test
	public void setterAndGetterTests2()
	{
		// Constructor Nr.2
		//
		ClassPoolEntity cpe = new ClassPoolEntity("classA", true);
		assertTrue(cpe.getClassName().equals("classA"));
		assertTrue(cpe.getIsLoaded());
		
		cpe.setClassName("newClassA");
		assertTrue(cpe.getClassName().equals("newClassA"));
		assertTrue(cpe.getIsLoaded());
		
		cpe.setIsLoaded(false);
		assertTrue(cpe.getClassName().equals("newClassA"));
		assertFalse(cpe.getIsLoaded());
	}
}
