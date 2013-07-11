package tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import dynamicDetection.ModifierRewriter;

public class ModifierRewriterTests 
{
	private final int PUBLIC = 1;
	private ClassPool pool; 
	private char d = System.getProperty("file.separator").charAt(0);
	
	@Test
	public void setModifiersToPublicTest() throws NotFoundException, CannotCompileException, IOException
	{
		pool = ClassPool.getDefault(); 
		pool.appendPathList(new File("testFiles"+ d + "testRewriter").getAbsolutePath());
		
		CtClass originClass = pool.get("ClassWithNonPublicFields");
		CtField [] fields = originClass.getFields();
		
		for (int i = 0; i < fields.length; i++)
			assertTrue (fields[i].getModifiers() != PUBLIC);
		
		originClass.instrument (new ModifierRewriter());
		
		originClass.replaceClassName("ClassWithNonPublicFields", "ClassWithPublicFields");
		originClass.writeFile(("testFiles"+ d + "testRewriter"));
		
		CtClass rewrittenClass = pool.get("ClassWithPublicFields");
		fields = rewrittenClass.getFields();
		
		for (int i = 0; i < fields.length; i++)
			assertTrue (fields[i].getModifiers() == PUBLIC);
	}
}
