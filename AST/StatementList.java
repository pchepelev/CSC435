package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import java.util.ArrayList;
import Temps.*;

public class StatementList {
	
	public ArrayList<Statement> stmtList;
	
	public StatementList () {
		stmtList = new ArrayList<Statement>();
	}
	
	public void addStmt (Statement s) {
		stmtList.add(s);
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