package AST;

import Visitor.*;
import Types.*;
import java.util.ArrayList;
import java.util.Iterator;
import SemanticException.*;
import Temps.*;
import Environment.*;

public class Program {
	
	public ArrayList<Function> functionList;
	
	public Program () {
		functionList = new ArrayList<Function>();
	}
	
	public void addFunction (Function f) {
		functionList.add(f);
	}
	
	public boolean mainExists() {
		Iterator<Function> itr = functionList.iterator();
		while (itr.hasNext()) {
			Function f = itr.next();
			if (f.getID().equals("main") && f.getNumParam() == 0 && f.getType() instanceof VoidType) {
				return true;
			}
		}
		return false;
	}
	
	public void accept (PrintVisitor v) {
		v.visit(this);
	}
	
	public FunctionEnvironment accept (TypeVisitor v) throws SemanticException {
		return v.visit(this);
	}
	
	public Temp accept (IRVisitor v) {
		return v.visit(this);
	}
}