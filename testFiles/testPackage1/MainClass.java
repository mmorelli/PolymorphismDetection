import java.util.ArrayList;


public class MainClass 
{
	// Primitve Datatypes
	//
	private int aInt1;
	private int aInt2;
	private int aInt3;
	
	private boolean aBool1;
	private boolean aBool2;
	private boolean aBool3;
	
	private char aChar1;
	private char aChar2;
	private char aChar3;
	
	private long aLong1;
	private long aLong2;
	private long aLong3;
	
	// Non-primitive Datatypes
	//
	private String aString1;
	private String aString2;
	private String aString3;
	
	private String aString4;
	private String aString5;

	private A classA;
	private A classA2;
	private A classA3;
	private B classB;
	
	private Interface I;
	
	private C classC;
	
	// static fields are not detected by the static and dynamic detector
	//
	private static C staticClassC;

	
	public MainClass()
	{

	}
	
	public static void main(String[] args) 
	{
		MainClass test = new MainClass();
		
		// Primitve Datatypes (fieldaccesses will be ignored (Heuristics))
		//
		test.aInt1 = 3;
		test.aInt2 = 2;
		test.aInt3 = 4;
		
		test.aInt1 = test.aInt2;
		test.aInt1 = test.aInt3;
		
		test.aBool1 = false;
		test.aBool2 = false;
		test.aBool3 = true;
		
		test.aBool1 = test.aBool2;
		test.aBool1 = test.aBool3;
		
		test.aChar1 = 'A';
		test.aChar2 = 'c';
		test.aChar3 = 'g';
		
		test.aChar1 = test.aChar2;
		test.aChar1 = test.aChar3;
		
		test.aLong1 = 324243444;
		test.aLong2 = 3334653;
		test.aLong3 = 43334;
		
		test.aLong1 = test.aLong2;
		test.aLong1 = test.aLong3;
		
		// Non-primitive Datatypes
		// Non-polymorphic
		//
		test.aString4 = "test";
		test.aString5 = "thisIsATest";
		
		test.aString4 = test.aString5;
		
		// Non-primitive Datatypes
		// Polymorphic
		//
		test.aString1 = "atest";
		test.aString2 = "anotherTest";
		test.aString3 = "theTest";
		
		test.aString1 = test.aString2;
		test.aString1 = test.aString3;
		
		test.classA = new A();
		test.classB = new B();
		
		test.I = (Interface) test.classA; // only correct interpreted by the static detector
		test.I = (Interface) test.classB; // static returns type Interface
										  // dynamic returns type B (catching casting not possible with metaobject trap)	
		
		// Test return types
		//
		test.classA = test.classA.aMethodWhichReturnsTypeB();	
		test.classA = test.classB.aMethodWhichReturnsTypeA();
			
		test.classA2 = test.classB.aMethodWhichReturnsTypeA();
		
		// Test if field accesses in other classes are caught
		//
		test.classC = new C();
		test.classC.run ();
		
		// Test static fields
		// Ignored by the static and dynamic detector
		//
		test.staticClassC = new C();
	}
}
