
public class ClassA 
{	
	protected Object field1;
	private Object field2;
	Object field3;
	
	public ClassA ()
	{
		field1 = new Object();
		field2 = new Object();
		field3 = new Object();
	}
	
	public static void main(String[] args) 
	{
		ClassA A = new ClassA();
		ClassB B = new ClassB();
	}
}
