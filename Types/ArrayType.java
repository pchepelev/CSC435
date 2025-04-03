package Types;

import Visitor.*;
import AST.*;
import SemanticException.*;
import Temps.*;

public class ArrayType extends Type {
	
	public Type theType;
	public IntegerLiteral theIntLit;
	
	public ArrayType (Type t, IntegerLiteral i) {
		theType = t;
		theIntLit = i;
		typeOf = setType();
		IRType = "A";
		this.lineNumber = t.lineNumber;
	}
	
	public String setType() {
		String s = theType.typeOf + "[" + theIntLit.theInt + "]";
		return s;
	}
	
	public int getInt(){
		return theIntLit.theInt;
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