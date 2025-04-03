package Types;

import Visitor.*;
import SemanticException.*;
import Temps.*;

public class FloatType extends Type {
	
	public FloatType (int ln) {
		lineNumber = ln;
		typeOf = "float";
		IRType = "F";
	}
	
	public FloatType () {
		lineNumber = -1;
		typeOf = "float";
		IRType = "F";
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