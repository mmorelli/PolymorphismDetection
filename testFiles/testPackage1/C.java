
public class C {

	private C classC;
	private D classD;
	
	public void run() 
	{
		classC = new C();	// not detected by the static detection, since new C() is not a field
		classC = new D();
		
	}

}
