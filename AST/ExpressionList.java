package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import java.util.ArrayList;
import java.util.Iterator;
import Temps.*;

public class ExpressionList {
	
	public ArrayList<Expression> theExpList;
	
	public ExpressionList () {
		theExpList = new ArrayList<Expression>();
	}
	
	public void addExp (Expression e) {
		theExpList.add(e);
	}
	
	public int size (){
		return theExpList.size();
	}
	
	public Expression get (int i) {
		return theExpList.get(i);
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