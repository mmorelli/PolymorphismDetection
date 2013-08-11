package controller;
import staticDetection.InterfaceFieldContainer;
import staticDetection.StaticDetector;
import dynamicDetection.DynamicDetector;

/** TODO: 
*1) Download External Projects from: https://github.com/mmorelli/External-Projects
*2) Set absolute path to this directory below
 **/

public class Controller 
{
		// Snakes and Ladders
		//
//		private final static String mainName = "snakes.Game";
//		private final static String absolutPathToBinaryDirectory = "TODO here: set absolute path to ..External-Projects\\p2-SnakesAndLadders\\bin";
	
		// Apache Commons Projects
		// 
		private final static String mainName = "MainClass";
		private final static String absolutPathToBinaryDirectory = "TODO here: set absolute path to ..External-Projects\\commons-jxpath-1.3-src\\bin";
//		private final static String absolutPathToBinaryDirectory = "TODO here: set absolute path to ..External-Projects\\commons-collections-3.2.1-src\\bin";
		
//		private final static String absolutPathToBinaryDirectory = "TODO here: set absolute path to ..External-Projects\\commons-codec-1.8-src\\bin";
//		private final static String absolutPathToBinaryDirectory = "TODO here: set absolute path to ..External-Projects\\commons-pool-1.6-src\\bin";
//		private final static String absolutPathToBinaryDirectory = "TODO here: set absolute path to ..External-Projects\\commons-cli-1.2-src\\bin";
//		private final static String absolutPathToBinaryDirectory = "TODO here: set absolute path to ..External-Projects\\commons-daemon-1.0.15-src\\bin";
		
		
	/**
	* To run the application "mainName" and "absolutPathToBinaryDirectory" are needed from above.
	*/	
	public static void main(String[] args) 
	{	
		try 
		{
			StaticDetector sd = new StaticDetector (absolutPathToBinaryDirectory);
			sd.run ();
			
			MultiMap staticResult = sd.getResult ();
			
			System.out.println("***STATIC RESULT***");
			staticResult.writeDumpToFile("staticResult.txt");
			
			System.out.println("HEURISTC: Field-writer with Interface-type detected: " + InterfaceFieldContainer.getInstance().size());
			InterfaceFieldContainer.getInstance().writeDumpToFile("staticInterfaceFields.txt");
			
			DynamicDetector dc = new DynamicDetector (absolutPathToBinaryDirectory);

			dc.run (mainName, args);
			
			MultiMap dynamicResult = dc.getResult ();
			
			System.out.println("***DYNAMIC RESULT***");
			dynamicResult.writeDumpToFile("dynamicResult.txt");
			
			System.out.println ("FINISHED!");
		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
		}
		
	}

}
