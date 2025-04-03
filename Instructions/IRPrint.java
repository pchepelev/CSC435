package Instructions;

import Temps.*;

public class IRPrint extends Instruction {
	
	Temp a;
	
	public IRPrint (Temp tn) {
		a = tn;
		theType = a.type;
	}
	
	public void print () {
		String op = a.type.IRType;
		System.out.print("PRINT"+op+" ");
		a.print();
		System.out.print(";");
	}
	
	public int printJVM(int n) {
		printJVMC(this);
		String t = OCType(a);
		System.out.println("	getstatic java/lang/System/out Ljava/io/PrintStream;");
		System.out.println("	"+t+"load "+a.number);
		if (!theType.IRType.equals("U")) {
			System.out.println("	invokevirtual java/io/PrintStream/print("+theType.IRType+")V");
		} else {
			System.out.println("	invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V");
		}
		return n;
	}
	
}