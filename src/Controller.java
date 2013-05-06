import java.io.File;

import javassist.ClassPool;
import javassist.NotFoundException;
import javassist.tools.reflect.Loader;


public class Controller 
{
//	private final String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\Javassist Projects\\Source\\bin";
	private final String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-SnakesAndLadders - public\\bin";
	
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
		classLoader.delegateLoadingOf("DataContainer");
		
		// todo search all jars and append them to pathlist
		pool.appendPathList("D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-SnakesAndLadders - public\\bin\\jexample-4.5-391.jar");
		pool.appendPathList("D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-SnakesAndLadders - public\\bin\\junit-4.4.jar");
		
		// todo load all interfaces directly
		classLoader.loadClass("ISquare");
	}
	
	public static void main(String[] args) 
	{
		Controller controller;
		
		try 
		{
			controller = new Controller ();
			
			// todo eliminate all fieldname-dublications -> renaming
			// todo set all field-modifiers to public
			
			controller.makeClassesReflectiv ();
			controller.runMain ("Game", args);

			
			MultiMap result = DataContainer.getInstance().getFieldWriteTraps();
			result.printOut();

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
		classLoader.makeReflective(getNameWithoutExtension("Square.class"), 
				"MetaClass", 
				"javassist.tools.reflect.ClassMetaobject");
		
		classLoader.makeReflective(getNameWithoutExtension("Ladder.class"), 
				"MetaClass", 
				"javassist.tools.reflect.ClassMetaobject");
		
		classLoader.makeReflective(getNameWithoutExtension("Snake.class"), 
				"MetaClass", 
				"javassist.tools.reflect.ClassMetaobject");
		
		classLoader.makeReflective(getNameWithoutExtension("FirstSquare.class"), 
				"MetaClass", 
				"javassist.tools.reflect.ClassMetaobject");
		
		classLoader.makeReflective(getNameWithoutExtension("Die.class"), 
				"MetaClass", 
				"javassist.tools.reflect.ClassMetaobject");
		classLoader.makeReflective(getNameWithoutExtension("DieTest.class"), 
				"MetaClass", 
				"javassist.tools.reflect.ClassMetaobject");
		
		classLoader.makeReflective(getNameWithoutExtension("Game.class"), 
				"MetaClass", 
				"javassist.tools.reflect.ClassMetaobject");
		
		classLoader.makeReflective(getNameWithoutExtension("LastSquare.class"), 
				"MetaClass", 
				"javassist.tools.reflect.ClassMetaobject");
		
		classLoader.makeReflective(getNameWithoutExtension("Player.class"), 
				"MetaClass", 
				"javassist.tools.reflect.ClassMetaobject");
		
		classLoader.makeReflective(getNameWithoutExtension("SimpleGameTest.class"), 
				"MetaClass", 
				"javassist.tools.reflect.ClassMetaobject");
		

		
		// todo recursive call to make all classes reflective (order of loading is important!)
		
//		File dir = new File(absolutPathToBinaryDirectory);
//		for (File child : dir.listFiles()) 
//		{
//			String fileName = child.getName();
//			
//			if (fileName.endsWith(".class"))
//			{
//				if (child.exists() && !fileName.equals("ISquare.class"))
//				{
//					
//					System.out.println ("loaded... " + fileName + ":");	
////					classLoader.loadClass(getNameWithoutExtension(fileName));
//						
//					classLoader.makeReflective(getNameWithoutExtension(fileName), 
//										"MetaClass", 
//										"javassist.tools.reflect.ClassMetaobject");
//				}
//			}
//
//					
//		}
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
