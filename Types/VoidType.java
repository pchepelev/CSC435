package Types;

import Visitor.*;
import SemanticException.*;
import Temps.*;

public class VoidType extends Type {
	
	public VoidType  (int ln) {
		lineNumber = ln;
		typeOf = "void";
		IRType = "V";
	}
	
	public VoidType  () {
		lineNumber = -1;
		typeOf = "void";
		IRType = "V";
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