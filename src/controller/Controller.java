package controller;
import staticDetection.InterfaceFieldContainer;
import staticDetection.StaticDetector;
import dynamicDetection.DynamicDetector;


public class Controller 
{
	
		// Snakes and Ladders
		//
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-SnakesAndLadders\\bin";
	
		// Apache Commons Projects
		// 
		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\Apache\\commons-jxpath-1.3-src\\bin";
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\Apache\\commons-collections-3.2.1-src\\bin";
		
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\Apache\\commons-codec-1.8-src\\bin";
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\Apache\\commons-pool-1.6-src\\bin";
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\Apache\\commons-cli-1.2-src\\bin";
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\Apache\\commons-daemon-1.0.15-src\\bin";
		
		
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

//			dc.run ("snakes.Game", args);
			dc.run ("MainClass", args);
			
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
