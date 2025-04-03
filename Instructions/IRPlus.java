package Instructions;

import Temps.*;

public class IRPlus extends Instruction {
	
	Temp result;
	Temp t1;
	Temp t2;
	String ot;
	
	public IRPlus (Temp tn, Temp tx, Temp ty, String type) {
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
		System.out.print(" "+ot+"+ ");
		t2.print();
		System.out.print(";");
	}
	public int printJVM(int n) {
		printJVMC(this);
		String t = OCType(t1);
		
		if (t1.type.typeOf.equals("string")) {
			System.out.println("	new java/lang/StringBuffer");
			System.out.println("	dup");
			System.out.println("	invokenonvirtual java/lang/StringBuffer/<init>()V");
			System.out.println("	aload "+t1.number);
			System.out.println("	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;");
			System.out.println("	aload "+t2.number);
			System.out.println("	invokevirtual java/lang/StringBuffer/append(Ljava/lang/String;)Ljava/lang/StringBuffer;");
			System.out.println("	invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;");
			System.out.println("	astore "+result.number);
			return n;
		}
		
		System.out.println("	"+t+"load "+t1.number);
		System.out.println("	"+t+"load "+t2.number);
		System.out.println("	"+t+"add");
		if (t1.type.typeOf.equals("char")) {
			System.out.println("	i2c");
		}
		System.out.println("	"+t+"store "+result.number);
		return n;
	}
}