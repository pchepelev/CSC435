package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class IfElseStatement extends IfStatement {
	
	public Block block2;
	
	public IfElseStatement (Expression e, Block b1, Block b2) {
		super(e, b1);
		block2 = b2;
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