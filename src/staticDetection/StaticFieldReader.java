package staticDetection;
import javassist.NotFoundException;
import javassist.expr.Cast;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

public class StaticFieldReader extends ExprEditor 
{
	public void edit (FieldAccess f)
	{
		try 
		{	
			if (f.isStatic() || f.getField().getType().isPrimitive()) // HEURISTICS: Ignore primitive Datatypes
				return;
			
//			String id = f.getClassName() + "AtLine:" + f.getLineNumber();
			String id = f.getEnclosingClass().getName() + "AtLine:" + f.getLineNumber();
			
			if (f.isWriter())
			{
				String keyString = f.getClassName() + ":" + f.getField().getType().getName() + ":" + f.getField().getName(); 
				StaticDataContainer.getInstance().addFieldKey(id, keyString);
				
//				System.out.println ("writer: "+ id + "-"+ keyString);
			}
			
			if (f.isReader())
			{
				String valueString = f.getField().getType().getName(); //+ ":" + f.getField().getName();
				StaticDataContainer.getInstance().addFieldValue(id, valueString);
				
//				System.out.println ("reader: "+ id + "-"+valueString);
//				System.out.println ("value " + f.getField().getType().getName() + " " + f.getField().getName() + " " + line);
			}
		} 
		catch (NotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void edit (MethodCall c)
	{
		try 
		{
			String id = c.getEnclosingClass().getName() + "AtLine:" + c.getLineNumber();
			String valueString = c.getMethod().getReturnType().getName();
			
			if (!c.getMethod().getReturnType().isPrimitive())
				StaticDataContainer.getInstance().addReturnType(id, valueString);
		} 
		catch (NotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void edit (Cast c)
	{
		String id = c.getEnclosingClass().getName() + "AtLine:" + c.getLineNumber();
		
		StaticDataContainer.getInstance().addCastAtLine(id);
	}
}
