package Temps;

import Types.*;
import AST.*;

public class TempFactory {
	
	public Temp [] temps;
	public int nextTemp;
	
	public TempFactory () {
		nextTemp=0;
		temps = new Temp[65536];
	}
	
	public Temp addTemp(Type type, Identifier name, ClassType ct){
		Temp t;
		if (nextTemp+1 < temps.length) {
			t = new Temp(nextTemp,type,name,ct);
			temps[nextTemp] = t;
			nextTemp++;
		} else {
			System.out.println("ERROR: Code too large");
			System.exit(0);
			t = new Temp(nextTemp,type,name,ct);
		}
		return t;
	}
	
	public Temp getTemp(int number) {
		return temps[number-1];
	}
	
	public int[] getInts () {
		int[] intArray = new int[nextTemp];
		for (int i = 0; i < nextTemp; i++) {
			intArray[i] = temps[i].number;
		}
		return intArray;
	}
	
	public Identifier[] getNames () {
		Identifier[] nameArray = new Identifier[nextTemp];
		for (int i = 0; i < nextTemp; i++) {
			nameArray[i] = temps[i].name;
		}
		return nameArray;
	}
	
	public Type[] getTypes () {
		Type[] typeArray = new Type[nextTemp];
		for (int i = 0; i < nextTemp; i++) {
			typeArray[i] = temps[i].type;
		}
		return typeArray;
	}
	
	public ClassType[] getClasses () {
		ClassType[] classes = new ClassType[nextTemp];
		for (int i = 0; i < nextTemp; i++) {
			classes[i] = temps[i].tempClass;
		}
		return classes;
	}
	
}