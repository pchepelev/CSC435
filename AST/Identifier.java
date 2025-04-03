package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class Identifier extends ASTNode {
	
	public String theId;
	
	public Identifier (String s, int ln) {
		theId = s;
		lineNumber = ln;
	}
	
	public void accept (PrintVisitor v) {
		v.visit(this);
	}
	
	public Type accept (TypeVisitor v) throws SemanticException {
		return v.visit(this);
	}
	
	public String accept (IRVisitor v) {
		return v.visit(this);
	}
	
}