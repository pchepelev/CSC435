package Instructions;

import Temps.*;

public class IRParenthExpression extends Instruction {
	
	Temp result;
	Temp t1;
	
	public IRParenthExpression (Temp tn, Temp tx) {
		result = tn;
		t1 = tx;
		theType = t1.type;
	}
	
	public void print () {
		result.print();
		System.out.print(" := ");
		t1.print();
		System.out.print(";");
	}
	
	public int printJVM(int n) {
		printJVMC(this);
		String t = OCType(result);
		System.out.println("	"+t+"load " + t1.number);
		System.out.println("	"+t+"store " + result.number);
		return n;
	}
	
}