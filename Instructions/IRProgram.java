package Instructions;

import java.util.*;
import Types.*;
import AST.*;

public class IRProgram {
	
	public String progName;
	public ArrayList<IRFunction> functionList;
	
	public IRProgram (String name) {
		progName = name;
		functionList = new ArrayList();
	}
	
	public void addFunction (IRFunction f) {
		functionList.add(f);
	}
	
	public int getSize() {
		return functionList.size();
	}
	
	public IRFunction get (int i) {
		return functionList.get(i);
	}
	
}