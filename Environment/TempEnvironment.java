package Environment;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;
import Temps.*;


public class TempEnvironment {
	
	public HashMap<String, Temp> temps; 
	
	public TempEnvironment () {
		temps = new HashMap<String, Temp>();
	}
	
	public void addTemp(String id, Temp ty) {
		temps.put(id, ty);
	}
	
	public void beginScope() {
		temps.clear();
	}
	
	public Temp lookup(String id) {
		return temps.get(id);
	}
	
	public boolean tempExists (String id) {
		return temps.containsKey(id);
	}
	
	public void print() {
        Set<Entry<String,Temp>> hashSet = temps.entrySet();
        for(Entry entry:hashSet ) {
			Temp t = (Temp)entry.getValue();
            System.out.println("Key="+entry.getKey()+", Value=");
        }
	}
	
}