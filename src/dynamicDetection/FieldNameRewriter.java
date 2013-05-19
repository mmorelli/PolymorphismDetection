package dynamicDetection;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

public class FieldNameRewriter extends ExprEditor 
{
	private String originFieldName;
	private String suffix;
	
	public FieldNameRewriter (String originFieldName)
	{
		this.originFieldName = originFieldName;
		this.suffix = "_" + IdManager.getInstance().getNextId();
	}
	
	public void edit (FieldAccess f)
	{
		try 
		{
			if (f.getFileName().equals(originFieldName))
			{
				f.getField().setName(originFieldName + suffix);
			}
		} 
		catch (NotFoundException e) 
		{
			e.printStackTrace();
		}
	}

}