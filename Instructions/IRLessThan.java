package Instructions;

import Temps.*;

public class IRLessThan extends Instruction {
	
	Temp result;
	Temp t1;
	Temp t2;
	String ot;
	
	public IRLessThan (Temp tn, Temp tx, Temp ty, String type) {
		result = tn;
		t1 = tx;
		t2 = ty;
		ot = type;
		theType = t1.type;
	}
	
	public void print () {
		result.print();
		System.out.print(" := ");
		t1.print();
		System.out.print(" "+ot+"< ");
		t2.print();
		System.out.print(";");
	}
	
	public int printJVM(int l) {
		printJVMC(this);
		String t = OCType(t1);
		String s = t1.type.typeOf;
		System.out.println("	"+t+"load "+t1.number);
		System.out.println("	"+t+"load "+t2.number);
		
		switch(s) {
			case "string":
				System.out.println("	invokevirtual java/lang/String/compareTo(Ljava/lang/String;)I");
				break;
			case "float":
				System.out.println("	fcmpg");
				break;
			default:
				System.out.println("	"+t+"sub");
				break;
		}
		
		System.out.println("	iflt L_"+(l));
		System.out.println("	ldc 0");
		System.out.println("	goto L_"+(l+1));
		System.out.println("L_"+(l)+":");
		System.out.println("	ldc 1");
		System.out.println("L_"+(l+1)+":");
		System.out.println("	istore "+result.number);
		return l+2;
	}
	
}