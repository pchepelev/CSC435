package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class FloatLiteral extends Expression {
	
	public Double theFloat;
	
	public FloatLiteral (Double d, int ln) {
		lineNumber = ln;
		theFloat = d;
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