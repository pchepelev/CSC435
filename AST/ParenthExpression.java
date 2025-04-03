package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class ParenthExpression extends Expression {
	
	public Expression theExp;
	
	public ParenthExpression (Expression e) {
		theExp = e;
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