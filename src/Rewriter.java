import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

public class Rewriter extends ExprEditor 
{
	private final int PUBLIC = 1;
	
	public void edit (FieldAccess f)
	{
		try 
		{
			if (!f.isStatic())
				f.getField().setModifiers(PUBLIC);
		} 
		catch (NotFoundException e) 
		{
			e.printStackTrace();
		}
	}

}


