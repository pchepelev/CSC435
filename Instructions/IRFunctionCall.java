package Instructions;

import java.util.*;
import Temps.*;
import AST.*;
import Types.*;
import Environment.*;

public class IRFunctionCall extends Instruction {
	
	Temp result;
	String name;
	List<Temp> temps;
	String progName;
	String tt;
	
	public IRFunctionCall (String fname,List<Temp> list, FunctionEnvironment fe, String p) {
		tt="";
		name = fname;
		temps = list;
		theType = getFuncType(fe,name);
		progName = p;
	}
	
	public IRFunctionCall (Temp out,String fname,List<Temp> list, FunctionEnvironment fe, String p) {
		tt="";
		result = out;
		name = fname;
		temps = list;
		theType = getFuncType(fe,name);
		progName = p;
	}
	
	Type getFuncType(FunctionEnvironment fe,String name) {
		Function f = fe.lookup(name);
		if (f.funcDecl.ctype.theType.IRType.equals("A")) {
			ArrayType at = (ArrayType)f.funcDecl.ctype.theType;
			tt="[";
			return at.theType;
		}
		return f.funcDecl.ctype.theType;
	}
	
	public void print() {
		if (result == null){
			System.out.print("CALL "+name+"(");
			for (int i = 0; i < temps.size(); i++) {
				temps.get(i).print();
				if (i < temps.size()-1) {
					System.out.print(" ");
				}
			}
			System.out.print(");");
		} else {
			result.print();
			System.out.print(" := CALL "+name+"(");
			for (int i = 0; i < temps.size(); i++) {
				temps.get(i).print();
				if (i < temps.size()-1) {
					System.out.print(" ");
				}
			}
			System.out.print(");");
		}
	}
	
	public int printJVM(int n) {
		printJVMC(this);
		String t;
		Type ty;
		for (int i = 0; i < this.temps.size(); i++) {
			t = OCType(this.temps.get(i));
			System.out.println("	"+t+"load "+this.temps.get(i).number);
		}
		System.out.print("	invokestatic "+progName+"/"+name+"(");
		for (int i = 0; i < this.temps.size(); i++) {
			ty = this.temps.get(i).type;
			if (ty.IRType.equals("A")) {
				ArrayType at = (ArrayType)ty;
				System.out.print("["+at.theType.IRType);
			}else {
				System.out.print(ty.IRType);
			}
		}
		System.out.println(")"+tt+theType.IRType);
		if (result != null){
			t = OCType(result);
			System.out.println("	"+t+"store "+result.number);
		}
		return n;
	}
	
}