package Instructions;

import Temps.*;
import Labels.*;

public class IRIfGoto extends Instruction {
	
	Temp cond;
	Label dest;
	
	public IRIfGoto (Temp t, Label d) {
		cond = t;
		dest = d;
		theType = cond.type;
	}
	
	public void print() {
		System.out.print("IF ");
		cond.print();
		System.out.print(" GOTO ");
		dest.print();
		System.out.print(";");
	}
	
	public int printJVM(int n) {
		printJVMC(this);
		System.out.println("	iload "+cond.number);
		System.out.print("	ifne ");
		dest.print();
		System.out.println();
		return n;
	}
	
}