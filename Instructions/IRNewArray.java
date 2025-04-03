package Instructions;

import Temps.*;
import Types.*;

public class IRNewArray extends Instruction {
	
	Temp result;
	String type;
	int len;
	
	public IRNewArray (Temp tn, Type t) {
		result = tn;
		ArrayType at = (ArrayType)t;
		len = at.getInt();
		type = at.theType.IRType;
		if (type.equals("A")) {
			System.out.println("ERROR: cannot have arrays of arrays");
			System.exit(0);
		}
	}
	
	public void print () {
		result.print();
		System.out.print(" := NEWARRAY "+type+" ");
		System.out.print(len);
		System.out.print(";");
	}
	
	public int printJVM(int n) {
		printJVMC(this);
		System.out.println("	ldc "+len);
		ArrayType a = (ArrayType)result.type;
		String s = a.theType.typeOf;
		switch(s) {
			case "string":
				System.out.println("	anewarray java/lang/String");
				break;
			default:
				System.out.println("	newarray "+s);
				break;
		}
		System.out.println("	astore "+result.number);
		return n;
	}
	
}