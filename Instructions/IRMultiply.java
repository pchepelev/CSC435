package Instructions;

import Temps.*;

public class IRMultiply extends Instruction {
	
	Temp result;
	Temp t1;
	Temp t2;
	String ot;
	
	public IRMultiply (Temp tn, Temp tx, Temp ty, String type) {
		result = tn;
		t1 = tx;
		t2 = ty;
		ot = type;
		theType = tn.type;
	}
	
	public void print () {
		result.print();
		System.out.print(" := ");
		t1.print();
		System.out.print(" "+ot+"* ");
		t2.print();
		System.out.print(";");
	}
	
	public int printJVM(int n) {
		printJVMC(this);
		String t = OCType(result);
		System.out.println("	"+t+"load "+t1.number);
		System.out.println("	"+t+"load "+t2.number);
		System.out.println("	"+t+"mul");
		System.out.println("	"+t+"store "+result.number);
		return n;
	}
	
}