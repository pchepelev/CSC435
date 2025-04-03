package Instructions;

import Temps.*;

public class IRReturn extends Instruction {
	
	Temp a;
	
	public IRReturn () {
		//nothing?
	}
	
	public IRReturn (Temp retval) {
		a = retval;
		theType = a.type;
	}
	
	public void print() {
		System.out.print("RETURN");
		if (a != null) {
			System.out.print(" ");
			a.print();
		}
		System.out.print(";");
	}
	
	public int printJVM(int n){
		printJVMC(this);
		if (a == null) {
			System.out.println("	return");
		} else {
			String t = OCType(a);
			System.out.println("	"+t+"load "+a.number);
			System.out.println("	"+t+"return");
		}
		return n;
	}
	
}