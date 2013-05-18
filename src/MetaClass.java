import javassist.tools.reflect.Metaobject;


public class MetaClass extends Metaobject
{
	public MetaClass (Object self, Object[] args)
	{
		super(self, args);
	}


    public Object trapMethodcall(int identifier, Object[] args)	throws Throwable 
    {
//        System.out.println("trapMethodCall: " + getMethodName(identifier) + "() in "
//                				+ getClassMetaobject().getName());
        
//        DataContainer.getInstance().addTrap("methodcall **data");
        
        return super.trapMethodcall(identifier, args);
    }
    
    public Object trapFieldRead(String name) 
    {
//        System.out.println("field read: " + name + " " + getClassMetaobject().getName());
        
//        DataContainer.getInstance().addTrap("trapFieldRead **data");
        
        return super.trapFieldRead(name);
    }


    public void trapFieldWrite(String name, Object value) 
    {
    	if (value != null)
    	{	
    		String type = DynamicDataContainer.getInstance().getFieldType(getClassMetaobject().getName(), name);
    		String packageName = DynamicDataContainer.getInstance().getPackageNameOfClass(getClassMetaobject().getName() + ".class");

//    		System.out.println(packageName + "." + getClassMetaobject().getName() + ":" + packageName + "." 
//    									+ type + "." + name + "  " + packageName + "." + value.getClass().getName());
    		    
    		
//    		DataContainer.getInstance().addFieldWriteTrap(packageName + "." + getClassMetaobject().getName() + ":" + packageName + "." 
//    									+ type + "." + name  , packageName + "." + value.getClass().getName());
    		
    		DynamicDataContainer.getInstance().addFieldWriteTrap(getClassMetaobject().getName() + ":" 
					+ type + "." + name  , value.getClass().getName());
    		
    	}
        super.trapFieldWrite(name, value);
    }
    


}
