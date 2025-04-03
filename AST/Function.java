package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class Function {
	
	public FunctionDeclaration funcDecl;
	public FunctionBody funcBody;
	
	public String getID(){
		return funcDecl.ident.theId;
	}
	
	public int getNumParam() {
		return funcDecl.paraL.paramList.size();
	}
	
	public Type getType() {
		return funcDecl.ctype.getType();
	}

	public void accept (PrintVisitor v) {
		v.visit(this);
	}
	
	public void accept (TypeVisitor v) throws SemanticException {
		v.visit(this);
	}
	
	public Temp accept (IRVisitor v) {
		return v.visit(this);
	}

}