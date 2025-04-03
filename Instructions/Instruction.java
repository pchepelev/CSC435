package Instructions;

import Temps.*;
import Types.*;

public class Instruction {
	public String inst;
	public Type theType;
	
	public void print() {
		System.out.print(" temporary! lol ");
	}
	
	public void printJVMC(Instruction i) {
		System.out.print(";				");
		i.print();
		System.out.println();
	}
	
	public int printJVM(int l){
		System.out.println("============================temporary! lol ");
		return l;
	}
	
	String OCType(Temp t) {
		String type = t.type.typeOf;
		switch(type) {
			case "boolean":
				return "i";
			case "char":
				return "i";
			case "float":
				return "f";
			case "int":
				return "i";
			case "string":
				return "a";
			default:
				return "a";
		}
	}
}