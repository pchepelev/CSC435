package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class WhileStatement extends Statement {
	
	public Expression theExp;
	public Block theBlock;
	
	public WhileStatement (Expression e, Block b) {
		theExp = e;
		theBlock = b;
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