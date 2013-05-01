import java.io.File;
import java.util.ArrayList;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;
import javassist.tools.reflect.Loader;


public class Controller 
{
	private final String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\Javassist Projects\\Source\\bin";
	private ClassPool pool; 
	private Loader classLoader; 
	
	// C'tor
	//
	public Controller () throws Throwable
	{
		pool = ClassPool.getDefault();	
		classLoader = new Loader();
		pool.insertClassPath(absolutPathToBinaryDirectory);
		classLoader.setClassPool(pool);
	}
	
	public static void main(String[] args) 
	{
		Controller controller;
		
		try 
		{
			controller = new Controller ();
			controller.makeClassesReflectiv ();
			controller.runMain ("TestMain", args);
			// after this runMain () the instance of the singleton is null!!
			
			// This list is empty because the following "DynamicDataContainer.getInstance().getTraps()" creates a new Instance of the Singleton!
			ArrayList<String> list = DataContainer.getInstance().getTraps();
			for (String member : list)
			{
				System.out.println ("listoutput: " + member);
			}
			
		}
		catch (NotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
		}
		
		
	}
	
	private void runMain(String mainClass, String[] args) throws Throwable 
	{
		classLoader.run(mainClass, args);
	}

	private void makeClassesReflectiv() throws Throwable 
	{
		System.out.println ("** makeReflective... ");
		
		File dir = new File(absolutPathToBinaryDirectory);
		for (File child : dir.listFiles()) 
		{
			String fileName = child.getName();
			
			System.out.println ("class " + fileName + ":");
			
			if (fileName.endsWith(".class"))
			{
				if (child.exists())
				{
					classLoader.makeReflective(getNameWithoutExtension(fileName), 
										"MetaClass", 
										"javassist.tools.reflect.ClassMetaobject");
				}
			}
		}
	}

	private static String getExtension (String fileName)
	{
		int index = fileName.lastIndexOf('.');
		return fileName.substring(index+1);
	}
	
	private static String getNameWithoutExtension (String fileName)
	{
		int index = fileName.lastIndexOf('.');
		return fileName.substring(0, index);
	}

}
