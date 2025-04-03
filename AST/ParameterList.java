package AST;

import Visitor.*;
import Types.*;
import SemanticException.*;
import java.util.ArrayList;
import java.util.Iterator;
import Temps.*;

public class ParameterList {
	
	public ArrayList<Parameter> paramList;
	
	public ParameterList () {
		paramList = new ArrayList<Parameter>();
	}
	
	public void addParam (Parameter p) {
		paramList.add(p);
	}
	
	public boolean paramExists(Parameter p){
		Iterator<Parameter> itr = paramList.iterator();
		while(itr.hasNext()){
			if (itr.next().getID().equals(p.getID())){
				return true;
			}
		}
		return false;
	}
	
	public int size (){
		return paramList.size();
	}
	
	public Parameter get (int i) {
		return paramList.get(i);
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