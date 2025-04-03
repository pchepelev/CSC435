package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class Block extends Statement {
	
	public StatementList blockList;
	
	public Block () {
		blockList = new StatementList();
	}
	
	public void blockAddStmt (Statement s) {
		blockList.addStmt(s);
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