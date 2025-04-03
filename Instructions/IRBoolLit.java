package Instructions;

import Temps.*;

public class IRBoolLit extends Instruction {
	
	boolean value;
	Temp tn;
	
	public IRBoolLit (Temp t, boolean i) {
		tn = t;
		value = i;
		theType = tn.type;
	}
	
	@Override
	public void print() {
		tn.print();
		System.out.print(" := ");
		if (value) {
			System.out.print("TRUE");
		} else {
			System.out.print("FALSE");
		}
		System.out.print(";");
	}
	
	public int printJVM(int l) {
		printJVMC(this);
		if (value) {
			System.out.println("	ldc 0");
		} else {
			System.out.println("	ldc 1");
		}
		System.out.println("	istore "+tn.number);
		return l;
	}
}