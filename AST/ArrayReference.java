package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class ArrayReference extends Expression {
	
	public Identifier theId;
	public Expression theExp;
	
	public ArrayReference (Identifier i, Expression e) {
		theId = i;
		theExp = e;
	}
	
	public String getID(){
		return theId.theId;
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