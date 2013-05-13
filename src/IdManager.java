public class IdManager 
{
	private static IdManager instance = null;
	
	private static int Id = 0;
	
	public static IdManager getInstance() 
	{
	    if (instance == null)
	        instance = new IdManager();

		return instance;
	}
	
	public static int getNextId ()
	{
		return Id++;
	}
}
