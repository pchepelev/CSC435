package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class Parameter {
	
	public CompoundType theCT;
	public Identifier theId;
	
	public Parameter(CompoundType ct, Identifier id) {
		theCT = ct;
		theId = id;
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