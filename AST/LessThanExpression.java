package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class LessThanExpression extends Expression {
	
	public Expression theExp1;
	public Expression theExp2;
	
	public LessThanExpression(Expression e1, Expression e2) {
		theExp1 = e1;
		theExp2 = e2;
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