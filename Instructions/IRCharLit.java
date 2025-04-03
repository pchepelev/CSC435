package Instructions;

import Temps.*;

public class IRCharLit extends Instruction {
	
	char value;
	Temp tn;
	
	public IRCharLit (Temp t, char i) {
		tn = t;
		value = i;
		theType = tn.type;
	}
	
	@Override
	public void print() {
		tn.print();
		System.out.print(" := '");
		System.out.print(value);
		System.out.print("';");
	}
	
	public int printJVM (int l) {
		printJVMC(this);
		System.out.println("	ldc "+(int)value);
		System.out.println("	istore "+tn.number);
		return l;
	}
	
}