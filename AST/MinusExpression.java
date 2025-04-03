package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class MinusExpression extends AddExpression {
	
	public MinusExpression(Expression e1, Expression e2) {
		super(e1,e2);
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