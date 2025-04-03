package Instructions;

import java.util.*;
import Types.*;
import Temps.*;
import AST.*;

public class IRFunction {
	
	public List<Instruction> instrList;
	public TempFactory tempFact;
	public CompoundType retType;
	public String funcName;
	public ParameterList pList;
	public int iSize() {
		return instrList.size();
	}
	public int tSize() {
		return tempFact.nextTemp;
	}
	
	
}