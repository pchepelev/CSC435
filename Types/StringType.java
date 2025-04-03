package Types;

import Visitor.*;
import SemanticException.*;
import Temps.*;

public class StringType extends Type {
	
	public StringType  (int ln) {
		lineNumber = ln;
		typeOf = "string";
		IRType = "U";
	}
	
	public StringType  () {
		lineNumber = -1;
		typeOf = "string";
		IRType = "U";
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