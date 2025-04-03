package Instructions;

public class IRAssignTemp extends Instruction {
	
	Temp result;
	Temp t1;
	
	public IRAssignTemp () {
		inst = "IRAssignTemp"
	}
	
	public IRAssignTemp (Temp tn, Temp tx) {
		result = tn;
		t1 = tx;
		theType = result.type;
	}
	
	public void print () {
		result.print();
		System.out.print(" := ");
		t1.print;
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
	
}