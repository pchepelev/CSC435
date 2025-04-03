package Instructions;

import Labels.*;

public class IRGoto extends Instruction {
	
	Label dest;
	
	public IRGoto (Label d) {
		dest = d;
	}
	
	public void print() {
		System.out.print("GOTO ");
		dest.print();
		System.out.print(";");
	}
	
	public int printJVM(int n) {
		printJVMC(this);
		System.out.print("	goto ");
		dest.print();
		System.out.println();
		return n;
	}
	
}