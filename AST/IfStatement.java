package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class IfStatement extends Statement {
	
	public Expression theExp;
	public Block block1;
	
	public IfStatement (Expression e, Block b) {
		theExp = e;
		block1 = b;
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