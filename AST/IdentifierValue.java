package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class IdentifierValue extends Expression {
	
	public Identifier theId;
	
	public IdentifierValue (Identifier i) {
		theId = i;
	}
	
	public String getID() {
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