package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class Statement extends ASTNode {
	
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