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
			
			String id = f.getEnclosingClass().getName() + "AtLine:" + f.getLineNumber();
			
			if (f.isWriter())
			{	
				String keyString = f.getClassName() + ":" + f.getField().getType().getName() + ":" + f.getField().getName(); 
				StaticDataContainer.getInstance().addFieldKey(id, keyString);
				
				if (f.getField().getType().isInterface())			// (was a HEURISTIC: "Interfaces are assumed to be polymorphic")
					InterfaceFieldContainer.getInstance().addField (keyString);
			}
			
			if (f.isReader())
			{
				String valueString = f.getField().getType().getName();
				StaticDataContainer.getInstance().addFieldValue(id, valueString);
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
		try 
		{
			String id = c.getEnclosingClass().getName() + "AtLine:" + c.getLineNumber();
			String keyString = c.getType().getName(); 
			
			StaticDataContainer.getInstance().addCastAtLine(id, keyString);
		} 
		catch (NotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
