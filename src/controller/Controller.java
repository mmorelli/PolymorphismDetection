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
		
	//	private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\MooseJEE-ArgoUML_032-IWRE2012\\MooseJEE-ArgoUML_032-IWRE2012\\src\\ArgoUML_032\\src\\argouml-app\\src\\org";
	//	private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\lectures-p2-examples\\p2-TicTacToe\\p2-TicTacToe-v9\\bin";
	
		// Problem sets from P2 (Andrei)
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\P2\\problemset03-sample\\bin";
		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\P2\\problemset05-sample\\bin";
		
		// ESE-Projects
		//
//		private final static String absolutPathToBinaryDirectory = "D:\\Bachelorarbeit\\source-files\\ESE projects\\ese2011-team2-master\\eclipse\\classes";
		
		
	public static void main(String[] args) 
	{	
		try 
		{
			StaticDetector sd = new StaticDetector (absolutPathToBinaryDirectory);
			sd.run ();
			
			MultiMap staticResult = sd.getResult ();
			
			System.out.println("***STATIC RESULT***");
			staticResult.printDump();
			staticResult.printPolymorphicFields();
			
			
			
			DynamicDetector dc = new DynamicDetector (absolutPathToBinaryDirectory);

			dc.run ("battleship.game.GameDriver", args);
//			dc.run ("battleship.game.GameDriver", args);
//			dc.run ("turtle.TurtleRenderer", args);
//			dc.run ("snakes.Game", args);
			
			
			MultiMap dynamicResult = dc.getResult ();
			
			System.out.println("***DYNAMIC RESULT***");
			dynamicResult.printDump();
			dynamicResult.printPolymorphicFields();
	
		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
		}
		
	}

}
