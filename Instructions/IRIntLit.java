package Instructions;

import Temps.*;

public class IRIntLit extends Instruction {
	
	int value;
	Temp tn;
	
	public IRIntLit (Temp t, int i) {
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
		System.out.println("	istore "+tn.number);
		return n;
	}
	
}