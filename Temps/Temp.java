package Temps;

import Types.*;
import AST.*;

public class Temp {
	
	public int number;
	public Identifier name;
	public Type type;
	ClassType tempClass;
	
	public Temp (int i, Type t, Identifier n, ClassType ct) {		
		name = n;
		number = i;
		type = t;
		tempClass = ct;
	}
	
	public void print () {
		System.out.print("T");
		System.out.print(number);
	}
	
}