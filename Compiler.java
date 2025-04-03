import org.antlr.runtime.*;
import java.io.*;
import AST.*;
import Visitor.*;
import Environment.*;

public class Compiler {

	public static void main (String[] args) throws Exception {
		
		String filename = args[0];
		ANTLRInputStream input;

		if (args.length == 0 ){
			System.out.println("Usage: Test filename.ul");
			return;
		}else{			
			input = new ANTLRInputStream(new FileInputStream(filename));
		}
		
		ulLexer lexer = new ulLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ulParser parser = new ulParser(tokens);
		
		try {
			Program theProgram = parser.program();
			TypeVisitor typeCheck = new TypeVisitor();
			FunctionEnvironment oldFE = theProgram.accept(typeCheck);
			IRVisitor irVisit = new IRVisitor(filename,oldFE);
			theProgram.accept(irVisit);
			
			irVisit.print();
			
			
		}
		catch (RecognitionException e )	{
			
		}
		catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
}