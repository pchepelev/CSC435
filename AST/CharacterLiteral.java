package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class CharacterLiteral extends Expression {
	
	public char theChar;
	
	public CharacterLiteral (char c, int ln) {
		lineNumber = ln;
		theChar = c;
	}
	
	public void accept (PrintVisitor v) {
		v.visit(this);
	}
	
	public Type accept (TypeVisitor v) throws SemanticException {
		return v.visit(this);
	}
	
	public Temp accept (IRVisitor v) {
		return v.visit(this);
	}
	
}