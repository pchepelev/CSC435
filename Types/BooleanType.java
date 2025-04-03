package Types;

import Visitor.*;
import SemanticException.*;
import Temps.*;

public class BooleanType extends Type {
	
	public BooleanType (int ln) {
		lineNumber = ln;
		typeOf = "boolean";
		IRType = "Z";
	}

	public BooleanType () {
		lineNumber = -1;
		typeOf = "boolean";
		IRType = "Z";
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