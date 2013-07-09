package dynamicDetection;
import controller.MultiMap;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

public class FieldNameCollector extends ExprEditor 
{	
	private String className;
	private MultiMap collector;
	
	public FieldNameCollector (String className, MultiMap collector)
	{
		this.className = className;
		this.collector = collector;
	}

	public void edit (FieldAccess f)
	{
		try 
		{
			String fieldName = f.getField().getName();
			String className = f.getField().getDeclaringClass().getName();
			
			if (this.className.equals(className) && !f.isStatic() && !fieldName.contains("$"))
				collector.add(className, fieldName);
		} 
		catch (NotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
