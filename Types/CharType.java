package Types;

import Visitor.*;
import SemanticException.*;
import Temps.*;

public class CharType extends Type {
	
	public CharType (int ln) {
		lineNumber = ln;
		typeOf = "char";
		IRType = "C";
	}
	
	public CharType () {
		lineNumber = -1;
		typeOf = "char";
		IRType = "C";
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