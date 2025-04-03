package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class IntegerLiteral extends Expression {
	
	public Integer theInt;
	
	public IntegerLiteral (Integer i, int ln) {
		lineNumber = ln;
		theInt = i;
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