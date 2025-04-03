package AST;

import java.util.*;
import Visitor.*;
import Types.*;
import SemanticException.*;
import Temps.*;

public class FunctionCall extends Expression {
	
	public Identifier theId;
	public ExpressionList theExpList;
	public List<Temp> temps;
	
	public FunctionCall (Identifier id, ExpressionList el) {
		theId = id;
		theExpList = el;
	}
	
	public String getID() {
		return theId.theId;
	}
	
	public void clearList() {
		temps = new ArrayList<Temp>();
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