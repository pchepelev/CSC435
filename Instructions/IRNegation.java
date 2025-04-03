package Instructions;

import Temps.*;

public class IRNegation extends Instruction {
	
	Temp result;
	Temp t1;
	
	public IRNegation (Temp tn, Temp tx) {
		result = tn;
		t1 = tx;
	}
	
	public void print () {
		result.print();
		System.out.print(" := Z! ");
		t1.print();
		System.out.print(";");
	}
	
	public int printJVM(int n) {
		printJVMC(this);
		System.out.println("	iload "+t1.number);
		System.out.println("	ldc 1");
		System.out.println("	ixor");
		System.out.println("	istore "+result.number);
		return n;
	}
	
}