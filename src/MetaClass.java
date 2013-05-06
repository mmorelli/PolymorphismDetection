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
        System.out.println("field write: " + name + " " + value.getClass().getName() + " " + getClassMetaobject().getName());
        
        DataContainer.getInstance().addFieldWriteTrap(getClassMetaobject().getName(), name, value.getClass().getName());
        
        super.trapFieldWrite(name, value);
    }

}
