import javassist.CodeConverter;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;


public class StaticFieldReader extends ExprEditor 
{
	private String packageName;
	
	public StaticFieldReader(String packageName) 
	{
		this.packageName = packageName;
	}

	public void edit (FieldAccess f)
	{
		try 
		{
			if (f.isStatic())
				return;
			
			String id = f.getClassName() + "AtLine:" + f.getLineNumber();
			
			if (f.isWriter())
			{
				String keyString = f.getClassName() + ":" + f.getField().getType().getName() + ":" + f.getField().getName(); 
				StaticDataContainer.getInstance().addFieldKey(id, keyString);
				
				System.out.println ("writer: "+ id + "-"+ keyString);
			}
			
			if (f.isReader())
			{
				String valueString = f.getField().getType().getName() + ":" + f.getField().getName(); 
				StaticDataContainer.getInstance().addFieldValue(id, valueString);
				
				System.out.println ("reader: "+ id + "-"+valueString);
//				System.out.println ("value " + f.getField().getType().getName() + " " + f.getField().getName() + " " + line);
			}
			
		} 
		catch (NotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
