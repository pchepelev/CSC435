package Instructions;

import Temps.*;

public class IRStringLit extends Instruction {
	
	String value;
	Temp tn;
	
	public IRStringLit (Temp t, String i) {
		tn = t;
		value = i;
		theType = tn.type;
	}
	
	@Override
	public void print() {
		tn.print();
		System.out.print(" := ");
		System.out.print(value);
		System.out.print(";");
	}
	
	public int printJVM(int n) {
		printJVMC(this);
		System.out.println("	ldc "+value);
		System.out.println("	astore "+tn.number);
		return n;
	}
	
}