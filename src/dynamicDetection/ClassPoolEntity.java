package dynamicDetection;

public class ClassPoolEntity 
{
	private String className;
	private boolean isLoaded;
	
	public ClassPoolEntity (String className)
	{
		this.className = className;
		this.isLoaded = false;
	}
	
	public ClassPoolEntity (String className, boolean loaded)
	{
		this.className = className;
		this.isLoaded = loaded;
	}
	
	public void setClassName (String className)
	{
		this.className = className;
	}
	
	public void setIsLoaded (boolean loaded)
	{
		this.isLoaded = loaded;
	}
	
	public String getClassName ()
	{
		return this.className;
	}
	
	public boolean getIsLoaded ()
	{
		return this.isLoaded;
	}
}
