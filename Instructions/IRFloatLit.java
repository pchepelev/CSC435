package Instructions;

import Temps.*;
import java.text.DecimalFormat;

public class IRFloatLit extends Instruction {
	
	Double value;
	Temp tn;
	
	public IRFloatLit (Temp t, Double i) {
		tn = t;
		value = i;
		theType = tn.type;
	}
	
	@Override
	public void print() {
		tn.print();
		System.out.print(" := ");
		System.out.print(value);
		System.out.print(";");
	}
	
	public int printJVM (int l) {
		DecimalFormat numberFormat = new DecimalFormat("#.000000");
		printJVMC(this);
		System.out.println("	ldc "+numberFormat.format(value));
		System.out.println("	fstore "+tn.number);
		return l;
	}
	
}