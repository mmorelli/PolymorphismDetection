package tests;

import static org.junit.Assert.*;

import org.junit.*;

import dynamicDetection.IdManager;

public class IdManagerTests 
{
	@Test
	public void getNextId1()
	{
		assertTrue (IdManager.getNextId() == 0);
		assertTrue (IdManager.getNextId() == 1);
		assertTrue (IdManager.getNextId() == 2);
		assertTrue (IdManager.getNextId() == 3);
		assertTrue (IdManager.getNextId() == 4);
		assertTrue (IdManager.getNextId() == 5);
		assertTrue (IdManager.getNextId() == 6);
		assertTrue (IdManager.getNextId() == 7);
	}
}
