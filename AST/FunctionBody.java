package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class FunctionBody {
	
	public VarDeclarList theVDL;
	public StatementList theSL;
	
	public FunctionBody (VarDeclarList vdl, StatementList sl) {
		theVDL = vdl;
		theSL = sl;
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