package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class BooleanLiteral extends Expression {
	
	public boolean theBool;
	
	public BooleanLiteral (String s, int ln) {
		lineNumber = ln;
		if (s.equals("true")) {
			theBool = true;
		} else {
			theBool = false;
		}
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