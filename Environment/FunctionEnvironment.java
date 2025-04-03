package Environment;

import java.util.HashMap;
import AST.*;


public class FunctionEnvironment {
	
	HashMap<String, Function> functions; 
	
	public FunctionEnvironment () {
		functions = new HashMap<String, Function>();
	}
	
	public void addFunction(String s, Function f) {
		functions.put(s, f);
	}
	
	public Function lookup(String id) {
		return functions.get(id);
	}
	
	public boolean functionExists (String id) {
		return functions.containsKey(id);
	}
}