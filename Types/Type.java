package Types;

import Visitor.*;
import AST.*;
import SemanticException.*;
import Temps.*;

public class Type extends ASTNode {
	
	public String typeOf;
	public String IRType;
	
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