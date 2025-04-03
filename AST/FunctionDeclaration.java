package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class FunctionDeclaration {
	
	public CompoundType ctype;
	public Identifier ident;
	public ParameterList paraL;
	
	public FunctionDeclaration (CompoundType c, Identifier i, ParameterList pl) {
		ctype = c;
		ident = i;
		paraL = pl;
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