package controller;
import staticDetection.StaticDetector;
import dynamicDetection.DynamicDetector;


public class Controller 
{
	//	private final String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\Javassist Projects\\Source\\bin";
	//	private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-SnakesAndLadders - public\\bin";
	//	private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-SnakesAndLadders - public - for package testing\\bin";
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-SnakesAndLadders\\bin";
	//	private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-SnakesAndLadders - private - no ps\\bin";
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\ESE projects\\ese2011-team2-master\\eclipse\\master";
	//	private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-SnakesAndLadders - private - no ps - testingDublicates\\bin";
		
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\MooseJEE-ArgoUML_032-IWRE2012\\MooseJEE-ArgoUML_032-IWRE2012\\src\\ArgoUML_032\\src\\argouml-app\\src\\org";
	//	private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-TicTacToe\\p2-TicTacToe-v9\\bin";
	
		// Problem sets from P2 (Andrei)
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\P2\\problemset03-sample\\bin";
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\P2\\problemset05-sample\\bin";
		
		// ESE-Projects
		//
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\ESE projects\\ese2011-team2-master\\eclipse\\classes";
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\ESE projects\\ese2010-team3-master\\qa\\eclipse\\classes";
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\ESE projects\\ese2011-team6-master\\eclipse\\classes";
		
		// Apache JXPath
		// 
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\Apache\\commons-jxpath-1.3-src\\bin";
		
		// Apache Commons
		// 
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\Apache\\commons-jxpath-1.3-src\\bin";
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\Apache\\commons-lang3-3.1-src\\bin";
		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\Apache\\commons-collections-3.2.1-src\\bin";
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\Apache\\commons-codec-1.8-src\\bin";
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\Apache\\commons-io-2.4-src\\bin";
		
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\Apache\\commons-pool-1.6-src\\bin";
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\Apache\\commons-primitives21.0\\bin";
		
		
	public static void main(String[] args) 
	{	
		try 
		{
			StaticDetector sd = new StaticDetector (absolutPathToBinaryDirectory);
			sd.run ();
			
			MultiMap staticResult = sd.getResult ();
			
			System.out.println("***STATIC RESULT***");
//			staticResult.printDump();
//			staticResult.printPolymorphicFields();
			staticResult.writeDumpToFile("staticResult.txt");
			
			
			DynamicDetector dc = new DynamicDetector (absolutPathToBinaryDirectory);

//			dc.run ("org.argouml.application.Main", args);;
//			dc.run ("play.server.Server", args);
//			dc.run ("battleship.game.GameDriver", args);
//			dc.run ("turtle.TurtleRenderer", args);
//			dc.run ("snakes.Game", args);
			dc.run ("MainClass", args);
			
			MultiMap dynamicResult = dc.getResult ();
			
			System.out.println("***DYNAMIC RESULT***");
//			dynamicResult.printDump();
//			dynamicResult.printPolymorphicFields();
			dynamicResult.writeDumpToFile("dynamicResult.txt");
		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
		}
		
	}

}
