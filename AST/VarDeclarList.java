package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import java.util.ArrayList;
import Temps.*;

public class VarDeclarList {
	
	public ArrayList<VarDeclar> varDeclList;
	
	public VarDeclarList () {
		varDeclList = new ArrayList<VarDeclar>();
	}
	
	public void addVarDecl (VarDeclar vd) {
		varDeclList.add(vd);
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