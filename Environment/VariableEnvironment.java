package Environment;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;
import AST.*;
import Types.*;

public class VariableEnvironment {
	
	public HashMap<String, Type> vars; 
	
	public VariableEnvironment () {
		vars = new HashMap<String, Type>();
	}
	
	public void addVar(String id, Type ty) {
		vars.put(id, ty);
	}
	
	public void beginScope() {
		vars.clear();
	}
	
	public Type lookup(String id) {
		return vars.get(id);
	}
	
	public boolean variableExists (String id) {
		return vars.containsKey(id);
	}
	
	public void print() {
        Set<Entry<String,Type>> hashSet = vars.entrySet();
        for(Entry entry:hashSet ) {
			Type t = (Type)entry.getValue();
            System.out.println("Key="+entry.getKey()+", Value="+t.typeOf);
        }
	}
	
}