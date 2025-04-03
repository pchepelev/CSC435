package Visitor;

import java.util.*;
import java.io.*;
import AST.*;
import Types.*;
import Temps.*;
import Instructions.*;
import Environment.*;
import Labels.*;

public class IRVisitor {
	
	String progName;
	String outPath;
	FunctionCall curFnc;
	TempFactory factory;
	TempEnvironment tmpEnv;
	FunctionEnvironment fe;
	List<Instruction> instructions;
	boolean voidFuncHasReturn;
	int numLabels;
	public IRProgram irProg;
	int curFunc;
	public int numGenLabels;
	
	public void boilerplate() {
		System.out.println(".method public static main([Ljava/lang/String;)V");
		System.out.println("	.limit locals 1");
		System.out.println("	.limit stack 4");
		System.out.println("	invokestatic "+progName+"/__main()V");
		System.out.println("	return");
		System.out.println(".end method");
		System.out.println();
		System.out.println(".method public <init>()V");
		System.out.println("	aload_0");
		System.out.println("	invokenonvirtual java/lang/Object/<init>()V");
		System.out.println("	return");
		System.out.println(".end method");
		
	}
	
	public void print() {
		System.out.println(".source "+progName+".ir");
		System.out.println(".class public "+progName);
		System.out.println(".super java/lang/Object\n");
		numGenLabels = 0;
		String aFName;
		IRFunction f;
		ParameterList pl;
		for (int i = 0; i < irProg.getSize(); i++) {
			f = irProg.get(i);
			aFName = f.funcName;
			pl = f.pList;
			if (aFName.equals("main")) {
				System.out.println(".method public static __main()V");
			} else {
				System.out.print(".method public static "+aFName+"(");
				for (int x = 0; x < pl.size();x++) {
					Type ty = pl.get(x).theCT.theType;
					String thisPT = ty.IRType;
					if (ty.IRType.equals("A")){
						ArrayType at = (ArrayType)ty;
						thisPT = "["+at.theType.IRType;
					}
					System.out.print(thisPT);
				}
				Type ty = f.retType.theType;
				String thisT = ty.IRType;
				if (ty.IRType.equals("A")) {
					ArrayType at = (ArrayType)ty;
					thisT = "["+at.theType.IRType;
				}
				System.out.println(")"+thisT);
			}
			System.out.println("	.limit locals "+f.tSize());
			System.out.println("	.limit stack 22");
			
			for (int j = 0; j < f.iSize(); j++) {
				numGenLabels = f.instrList.get(j).printJVM(numGenLabels);
			}
			
			System.out.println(".end method");
			System.out.println();
		}
		boilerplate();
		
	}
	
	public IRVisitor(String filename, FunctionEnvironment oldFE) throws IOException {
		fe = oldFE;
		String period[] =  filename.split("\\.");
		String pathMinusExt = "";
		for (int i = 0; i < period.length-1; i++) {
			pathMinusExt += period[i];
			if (i < period.length-2) {
				pathMinusExt += ".";
			}
		}
		outPath = pathMinusExt+".ir";
		
		String slash[] = pathMinusExt.split("/");
		progName = slash[slash.length-1];
		
		//PrintWriter printWriter = new PrintWriter (outPath);
		
	}
	
	/*---------------------PROGRAM STRUCTURE-----------------------*/
	
	//Overall Structure
	public Temp visit (Program P) {
		irProg = new IRProgram(progName);
		//System.out.println("PROG " + progName);
		Iterator<Function> itr = P.functionList.iterator();
		while (itr.hasNext()) {
			itr.next().accept(this);
		}
		return null;
	}
	
	public Temp visit (Function F) {
		curFunc = irProg.getSize();
		
		IRFunction thisIRFunc = new IRFunction();
		irProg.addFunction(thisIRFunc);
		
		
		
		factory = new TempFactory();
		instructions = new ArrayList<Instruction>();
		tmpEnv = new TempEnvironment();
		numLabels = 0;
		voidFuncHasReturn = false;
		
		F.funcDecl.accept(this);
		//System.out.println("{");
		F.funcBody.accept(this);
		
		thisIRFunc.instrList = instructions;
		thisIRFunc.tempFact = factory;
		
		int[] ints = factory.getInts();
		Identifier[] names = factory.getNames();
		Type[] types = factory.getTypes();
		ClassType[] classes = factory.getClasses();
		/*
		for (int i = 0; i < factory.nextTemp; i++) {
			switch (classes[i]) {
				case LOCAL:
					System.out.print("\tTEMP ");
					System.out.print(ints[i]);
					System.out.print(":");
					types[i].accept(this);
					System.out.print("\t[L(\"");
					names[i].accept(this);
					System.out.println("\")];");
					break;
				case PARAMETER:
					System.out.print("\tTEMP ");
					System.out.print(ints[i]);
					System.out.print(":");
					types[i].accept(this);
					System.out.print("\t[P(\"");
					names[i].accept(this);
					System.out.println("\")];");
					break;
				case INTERMEDIATE:
					System.out.print("\tTEMP ");
					System.out.print(ints[i]);
					System.out.print(":");
					types[i].accept(this);
					System.out.println(";");
					break;
			}
		}
		*/
		if ((F.getType() instanceof VoidType) && !voidFuncHasReturn) {
			instructions.add(new IRReturn());
		}
		/*
		for (int i = 0; i < instructions.size(); i++) {
			if (!(instructions.get(i) instanceof IRLabel)) {
				System.out.print("\t\t");
			}
			instructions.get(i).print();
			System.out.println();
		}
		
		*/
		
		//System.out.println("}");
		return null;
	}
	
	//Function Signature
	public Temp visit (FunctionDeclaration FD) {
		//System.out.print("FUNC ");
		irProg.get(curFunc).funcName = FD.ident.accept(this);
		//System.out.print(" (");
		FD.paraL.accept(this);
		//System.out.print(")");
		FD.ctype.accept(this);
		//System.out.println();
		
		irProg.get(curFunc).retType = FD.ctype;
		irProg.get(curFunc).pList = FD.paraL;
		
		return null;
	}

	public Temp visit (ParameterList PL) {
		Iterator<Parameter> itr = PL.paramList.iterator();
		while(itr.hasNext()){
			itr.next().accept(this);
		}
		return null;
	}
	
	//Function Body
	public Temp visit (FunctionBody FB) {
		FB.theVDL.accept(this);
		FB.theSL.accept(this);
		return null;
	}
	
	public Temp visit (VarDeclarList VDL) {
		Iterator<VarDeclar> itr = VDL.varDeclList.iterator();
		while(itr.hasNext()) {
			itr.next().accept(this);
		}
		return null;
	}
	
	public Temp visit (StatementList SL) {
		Iterator<Statement> itr = SL.stmtList.iterator();
		while(itr.hasNext()) {
			itr.next().accept(this);
		}
		return null;
	}
	
	//Block
	public Temp visit (Block B) {
		B.blockList.accept(this);
		return null;
	}
	
	/*---------------------PROGRAM STRUCTURE-----------------------*/
	

	

	

	/*---------------------PROGRAM COMPONENTS---------------------*/
	
	public Temp visit (CompoundType CT) {
		CT.theType.accept(this);
		return null;
	}
	
	public String visit (Identifier ID) {
		//System.out.print(ID.theId);
		return ID.theId;
	}
	
	public Temp visit (Parameter Par) {
		
		Par.theCT.accept(this);
		Type t = Par.theCT.theType;
		Identifier i = Par.theId;
		//System.out.println("Identifier: ["+i.theId + "] type:[" + t.IRType+"]");
		Temp aTemp = factory.addTemp(t, i, ClassType.PARAMETER);
		tmpEnv.addTemp(i.theId,aTemp);
		if (t.IRType.equals("A")) {
			//instructions.add(new IRNewArray(aTemp,t));
		}
		return null;
		
	}
	
	public Temp visit (VarDeclar VD) {
		
		Type t = VD.theCT.theType;
		Identifier i = VD.theId;
		Temp aTemp = factory.addTemp(t, i, ClassType.LOCAL);
		tmpEnv.addTemp(i.theId,aTemp);
		if (t.IRType.equals("A")) {
			instructions.add(new IRNewArray(aTemp,t));
		}
		return null;
		
	}
	
	/*---------------------PROGRAM COMPONENTS---------------------*/

	

	
	/*--------------------------STATEMENTS-----------------------*/
	
	public Temp visit (Statement S) {
		return null;
	}

	//Loops
	public Temp visit (IfStatement IS) {
		Temp cond = IS.theExp.accept(this);
		Temp invCond = factory.addTemp(new BooleanType(), null, ClassType.INTERMEDIATE);
		instructions.add(new IRNegation(invCond, cond));
		
		Label skip = new Label(numLabels++);
		
		instructions.add(new IRIfGoto(invCond, skip));
		IS.block1.accept(this);
		instructions.add(new IRLabel(skip));
		return null;
	}
	
	public Temp visit (IfElseStatement IES) {
		Temp cond = IES.theExp.accept(this);
		Temp invCond = factory.addTemp(new BooleanType(), null, ClassType.INTERMEDIATE);
		instructions.add(new IRNegation(invCond, cond));
		
		Label skip1 = new Label(numLabels++);
		
		instructions.add(new IRIfGoto(invCond, skip1));
		IES.block1.accept(this);
		
		Label skip2 = new Label(numLabels++);
		
		instructions.add(new IRGoto(skip2));
		instructions.add(new IRLabel(skip1));
		IES.block2.accept(this);
		instructions.add(new IRLabel(skip2));
		return null;
	}
	
	public Temp visit (WhileStatement WS) {
		Label top = new Label(numLabels++);
		instructions.add(new IRLabel(top));
		
		Temp cond = WS.theExp.accept(this);
		Temp invCond = factory.addTemp(new BooleanType(), null, ClassType.INTERMEDIATE);
		instructions.add(new IRNegation(invCond, cond));
		
		Label skip = new Label(numLabels++);
		instructions.add(new IRIfGoto(invCond,skip));
		WS.theBlock.accept(this);
		instructions.add(new IRGoto(top));
		instructions.add(new IRLabel(skip));
		return null;
	}
	
	//Assignments
	public Temp visit (VariableAssignmentStatement VAS) {
		String id = VAS.getID();
		Temp tn = tmpEnv.lookup(id);
		Temp tx = VAS.theExp.accept(this);
		instructions.add(new IRVariableAssignment(tn,tx));
		return tn;
	}
	
	public Temp visit (ArrayAssignmentStatement AAS) {
		String id = AAS.getID();
		Temp array = tmpEnv.lookup(id);
		
		Temp index = AAS.exp1.accept(this);
		Temp tx = AAS.exp2.accept(this);
		
		instructions.add(new IRArrayAssignment(array,index,tx));
		return array;
	}
	
	//Print Statements
	public Temp visit (PrintStatement PS) {
		Temp e = PS.theExpr.accept(this);
		instructions.add(new IRPrint(e));
		return e;
	}
	
	public Temp visit (PrintLineStatement PLS) {
		Temp e = PLS.theExpr.accept(this);
		instructions.add(new IRPrintln(e));
		return e;
	}
	
	//Misc Statements
	public Temp visit (ExpressionStatement ExS) {
		ExS.theExp.accept(this);
		return null;
	}

	public Temp visit (ReturnStatement RS) {
		if (RS.theExpr != null) {
			Temp ret = RS.theExpr.accept(this);
			instructions.add(new IRReturn(ret));
		} else {
			instructions.add(new IRReturn());
			voidFuncHasReturn = true;
		}
		return null;
	}
	
	public Temp visit (EmptyStatement EmS) {
		//instructions.add(new IREmpty());
		return null;
	}
	
	/*------------------------STATEMENTS-----------------------*/
	
	
	
	/*-----------------------EXPRESSIONS-----------------------*/
	
	public Temp visit (Expression E) {
		return null;
	}
	
	//ParenthExpression
	public Temp visit (ParenthExpression ParEx) {
		Temp t1 = ParEx.theExp.accept(this);
		Temp out = factory.addTemp(t1.type, null, ClassType.INTERMEDIATE);
		instructions.add(new IRParenthExpression(out,t1));
		return out;
	}
	
	//Operators
	public Temp visit (EqualsExpression EE) {
		Temp t1 = EE.theExp1.accept(this);
		Temp t2 = EE.theExp2.accept(this);
		Temp out = factory.addTemp(new BooleanType(), null, ClassType.INTERMEDIATE);
		instructions.add(new IREquals(out,t1,t2,t1.type.IRType));
		return out;
	}
	
	public Temp visit (LessThanExpression LTE) {
		Temp t1 = LTE.theExp1.accept(this);
		Temp t2 = LTE.theExp2.accept(this);
		Temp out = factory.addTemp(new BooleanType(), null, ClassType.INTERMEDIATE);
		instructions.add(new IRLessThan(out,t1,t2,t1.type.IRType));
		return out;
	}
	
	public Temp visit (AddExpression AE) {
		return null;
	}
	
	public Temp visit (PlusExpression PE) {
		Temp t1 = PE.theExp1.accept(this);
		Temp t2 = PE.theExp2.accept(this);
		Temp out = factory.addTemp(t1.type, null, ClassType.INTERMEDIATE);
		instructions.add(new IRPlus(out,t1,t2,t1.type.IRType));
		return out;
	}
	
	public Temp visit (MinusExpression MiE) {
		Temp t1 = MiE.theExp1.accept(this);
		Temp t2 = MiE.theExp2.accept(this);
		Temp out = factory.addTemp(t1.type, null, ClassType.INTERMEDIATE);
		instructions.add(new IRMinus(out,t1,t2,t1.type.IRType));
		return out;
	}
	
	public Temp visit (MultiplyExpression MuE) {
		Temp t1 = MuE.theExp1.accept(this);
		Temp t2 = MuE.theExp2.accept(this);
		Temp out = factory.addTemp(t1.type, null, ClassType.INTERMEDIATE);
		instructions.add(new IRMultiply(out,t1,t2,t1.type.IRType));
		return out;
	}
	
	//References
	public Temp visit (IdentifierValue IV) {
		String id = IV.getID();
		Temp tn = tmpEnv.lookup(id);
		return tn;
	}
	
	public Temp visit (ArrayReference AR) {
		String id = AR.getID();
		Temp array = tmpEnv.lookup(id);
		Temp index = AR.theExp.accept(this);
		Type type = array.type;
		ArrayType anArray = (ArrayType)type;
		type = anArray.theType;
		Temp out = factory.addTemp(type, AR.theId, ClassType.INTERMEDIATE);
		instructions.add(new IRArrayReference(out,array,index));
		return out;
	}
	
	//Function Calls
	public Temp visit (FunctionCall FC) {
		curFnc = FC;
		curFnc.clearList();
		
		String fname = curFnc.getID();
		Function f = fe.lookup(fname);
		Type retType = f.getType();
		
		FC.theExpList.accept(this);
		
		if (retType instanceof VoidType) {
			instructions.add(new IRFunctionCall(fname,curFnc.temps,fe,progName));
			return null;
		} else {
			Temp out = factory.addTemp(retType, null, ClassType.INTERMEDIATE);
			instructions.add(new IRFunctionCall(out,fname,curFnc.temps,fe,progName));
			return out;
		}
	}
	
	public Temp visit (ExpressionList EL) {
		Iterator<Expression> itr = EL.theExpList.iterator();
		while(itr.hasNext()) {
			curFnc.temps.add(itr.next().accept(this));
		}
		return null;
	}
	
	/*-----------------------EXPRESSIONS-----------------------*/
	
	
	
	/*------------------------LITERALS-----------------------*/
	
	public Temp visit (IntegerLiteral IL) {
		int val = IL.theInt;
		Temp tn = factory.addTemp(new IntegerType(), null, ClassType.INTERMEDIATE);
		instructions.add(new IRIntLit(tn,val));
		return tn;
	}
	
	public Temp visit (FloatLiteral FL) {
		Double val = FL.theFloat;
		Temp tn = factory.addTemp(new FloatType(), null, ClassType.INTERMEDIATE);
		instructions.add(new IRFloatLit(tn,val));
		return tn;
	}
	
	public Temp visit (CharacterLiteral CL) {
		char val = CL.theChar;
		Temp tn = factory.addTemp(new CharType(), null, ClassType.INTERMEDIATE);
		instructions.add(new IRCharLit(tn,val));
		return tn;
	}
	
	public Temp visit (StringLiteral SL) {
		String val = SL.theStr;
		Temp tn = factory.addTemp(new StringType(), null, ClassType.INTERMEDIATE);
		instructions.add(new IRStringLit(tn,val));
		return tn;
	}
	
	public Temp visit (BooleanLiteral BL) {
		boolean val = BL.theBool;
		Temp tn = factory.addTemp(new BooleanType(), null, ClassType.INTERMEDIATE);
		instructions.add(new IRBoolLit(tn,val));
		return tn;
	}
	
	/*------------------------LITERALS-----------------------*/
	
	
	
	/*--------------------------TYPES-----------------------*/
	
	public Temp visit (Type T) {
		//System.out.print(T.IRType);
		return null;
	}
	
	public Temp visit (ArrayType AT) {
		//System.out.print(AT.IRType);
		AT.theType.accept(this);
		return null;
	}
	
	public Temp visit (BooleanType BT) {
		//System.out.print(BT.IRType);
		return null;
	}
	
	public Temp visit (CharType CT) {
		//System.out.print(CT.IRType);
		return null;
	}
	
	public Temp visit (FloatType FT) {
		//System.out.print(FT.IRType);
		return null;
	}
	
	public Temp visit (IntegerType IT) {
		//System.out.print(IT.IRType);
		return null;
	}
	
	public Temp visit (StringType ST) {
		//System.out.print(ST.IRType);
		return null;
	}
	
	public Temp visit (VoidType VT) {
		//System.out.print(VT.IRType);
		return null;
	}
	
	/*--------------------------TYPES-----------------------*/
}