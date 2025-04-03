package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class CompoundType {
	
	public Type theType;
	
	public CompoundType (Type t) {
		theType = t;
	}
	
	public Type getType() {
		return theType;
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