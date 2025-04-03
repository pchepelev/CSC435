package Instructions;

import Labels.*;

public class IRLabel extends Instruction {
	
	Label l;
	
	public IRLabel (Label dest) {
		l = dest;
	}
	
	public void print() {
		l.print();
		System.out.print(":;");
	}
	
	public int printJVM(int n){
		System.out.print(";		");
		this.print();
		System.out.println();
		l.print();
		System.out.println(":");
		return n;
	}
	
}