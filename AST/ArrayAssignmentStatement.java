package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class ArrayAssignmentStatement extends Statement {
	
	public Identifier theId;
	public Expression exp1;
	public Expression exp2;
	
	public ArrayAssignmentStatement(Identifier id, Expression e1, Expression e2) {
		theId = id;
		exp1 = e1;
		exp2 = e2;
	}
	
	public String getID() { 
		return theId.theId;
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