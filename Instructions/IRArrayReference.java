package Instructions;

import Temps.*;

public class IRArrayReference extends Instruction {
	
	Temp tn;
	Temp tx;
	Temp ty;
	
	public IRArrayReference (Temp t1, Temp t2, Temp t3) {
		tn = t1;
		tx = t2;
		ty = t3;
		theType = tn.type;
	}
	
	@Override
	public void print() {
		tn.print();
		System.out.print(" := ");
		tx.print();
		System.out.print("[");
		ty.print();
		System.out.print("]");
		System.out.print(";");
	}
	
	public int printJVM(int l) {
		printJVMC(this);
		String t = OCType(tn);
		System.out.println("	aload "+tx.number);
		System.out.println("	iload "+ty.number);
		System.out.println("	"+t+"aload");
		System.out.println("	"+t+"store "+tn.number);
		return l;
	}
	
}