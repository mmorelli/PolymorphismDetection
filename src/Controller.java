import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
		
	}
	
	public static void main(String[] args) 
	{
		Controller controller;
		
		try 
		{
			controller = new Controller ();
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
//		System.out.println ("** makeReflective... ");
		
		File dir = new File(absolutPathToBinaryDirectory);
		for (File child : dir.listFiles()) 
		{
			String fileName = child.getName();
			
			if (fileName.endsWith(".class"))
			{
				if (child.exists())
				{
					
					System.out.println ("loaded... " + fileName + ":");	
					classLoader.loadClass(getNameWithoutExtension(fileName));
						
//					classLoader.makeReflective(getNameWithoutExtension(fileName), 
//										"MetaClass", 
//										"javassist.tools.reflect.ClassMetaobject");
				}
			}
			else	if (fileName.endsWith(".jar"))
					{
						File f = new File ("D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-SnakesAndLadders - public\\bin\\jexample-4.5-391.jar");
						JarFile jarFile = new JarFile(f);
			            Enumeration e = jarFile.entries();

			            URL[] urls = { new URL("jar:file:" + "D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-SnakesAndLadders - public\\bin\\jexample-4.5-391.jar"+"!/") };
			            URLClassLoader URLclassLoader = URLClassLoader.newInstance(urls);

			            while (e.hasMoreElements()) 
			            {
			                JarEntry je = (JarEntry) e.nextElement();
			                if(je.isDirectory() || !je.getName().endsWith(".class"))
			                {
			                    continue;
			                }
			
			                String className = je.getName().substring(0,je.getName().length()-6);
			                className = className.replace('/', '.');
			                URLclassLoader.loadClass(className);
			         
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
