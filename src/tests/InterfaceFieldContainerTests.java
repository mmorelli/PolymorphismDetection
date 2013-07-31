package tests;

import static org.junit.Assert.*;

import org.junit.*;

import staticDetection.InterfaceFieldContainer;

import dynamicDetection.ClassFieldMap;

public class InterfaceFieldContainerTests 
{
	@Test
	public void addFieldTests()
	{
		InterfaceFieldContainer.getInstance().clear();
		
		InterfaceFieldContainer.getInstance().addField("A");
		assertTrue (InterfaceFieldContainer.getInstance().size() == 1);
		
		InterfaceFieldContainer.getInstance().addField("A");
		assertTrue (InterfaceFieldContainer.getInstance().size() == 1);
		
		InterfaceFieldContainer.getInstance().addField("B");
		assertTrue (InterfaceFieldContainer.getInstance().size() == 2);
		
		InterfaceFieldContainer.getInstance().addField("A");
		assertTrue (InterfaceFieldContainer.getInstance().size() == 2);
		
		InterfaceFieldContainer.getInstance().addField("C");
		assertTrue (InterfaceFieldContainer.getInstance().size() == 3);
		
		InterfaceFieldContainer.getInstance().addField("B");
		assertTrue (InterfaceFieldContainer.getInstance().size() == 3);
	}
}
