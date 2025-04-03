package Instructions;

import Temps.*;

public class IRArrayAssignment extends Instruction {
	
	Temp array;
	Temp index;
	Temp calc;
	
	public IRArrayAssignment (Temp tn, Temp tx, Temp ty) {
		array = tn;
		index = tx;
		calc = ty;
		theType = calc.type;
	}
	
	public void print () {
		array.print();
		System.out.print("[");
		index.print();
		System.out.print("] := ");
		calc.print();
		System.out.print(";");
	}
	
	public int printJVM(int l) {
		printJVMC(this);
		String t = OCType(calc);
		System.out.println("	aload "+array.number);
		System.out.println("	iload "+index.number);
		System.out.println("	"+t+"load "+calc.number);
		System.out.println("	"+t+"astore");
		return l;
	}
	
}