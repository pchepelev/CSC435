package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class VariableAssignmentStatement extends Statement {
	
	public Identifier theId;
	public Expression theExp;
	
	public VariableAssignmentStatement (Identifier id, Expression e) {
		theId = id;
		theExp = e;
	}
	
	public String getID(){
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