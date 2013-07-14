package dynamicDetection;
import javassist.tools.reflect.Metaobject;


public class MetaClass extends Metaobject
{
	public MetaClass (Object self, Object[] args)
	{
		super(self, args);
	}

    public Object trapMethodcall(int identifier, Object[] args)	throws Throwable 
    {
        return super.trapMethodcall(identifier, args);
    }
    
    public Object trapFieldRead(String name) 
    {
        return super.trapFieldRead(name);
    }

    public void trapFieldWrite(String name, Object value) 
    {	
    	if (value != null)
    	{
    		String type = DynamicDataContainer.getInstance().getFieldType(getClassMetaobject().getName(), name);

    		// HEURISTICS: Ignore primitive Datatypes
    		//
    		if (!DynamicDataContainer.getInstance().isFieldTypePrimitve(getClassMetaobject().getName(), name))
    			DynamicDataContainer.getInstance().addFieldWriteTrap(getClassMetaobject().getName() + ":" 
																+ type + ":" + name  , value.getClass().getName());
   
    	}
    	else 
    		System.out.println("MetaClass::field ("+ name + ") has value: null");
    	
        super.trapFieldWrite(name, value);
    }
}
