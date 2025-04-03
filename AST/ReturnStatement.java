package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class ReturnStatement extends Statement {
	
	public Expression theExpr;
	
	public ReturnStatement(int line) {
		theExpr = null;
		lineNumber = line;
	}
	
	public ReturnStatement(Expression e, int line) {
		theExpr = e;
		lineNumber = line;
	}
	
	public void accept (PrintVisitor v) {
		v.visit(this);
	}
	
	public void accept (TypeVisitor v) throws SemanticException {
		v.visit(this);
	}
	
	public Temp accept (IRVisitor v) {
		return v.visit(this);
	}
	
}