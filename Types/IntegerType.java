package Types;

import Visitor.*;
import SemanticException.*;
import Temps.*;

public class IntegerType extends Type {
	
	public IntegerType  (int ln) {
		lineNumber = ln;
		typeOf = "int";
		IRType = "I";
	}
	
	public IntegerType  () {
		lineNumber = -1;
		typeOf = "int";
		IRType = "I";
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