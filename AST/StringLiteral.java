package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class StringLiteral extends Expression {
	
	public String theStr;
	
	public StringLiteral (String s, int ln) {
		lineNumber = ln;
		theStr = s;
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