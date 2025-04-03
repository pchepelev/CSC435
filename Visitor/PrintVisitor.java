package Visitor;

import AST.*;
import Types.*;
import java.util.Iterator;

public class PrintVisitor {
	
	int indentLevel;
	
	public PrintVisitor() {
		indentLevel = 0;
	}
	
	public void printIndent(int times) {
		for (int i = 0; i < times; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(" ");
			}
		}
	}
	
	public void visit (Program P) {
		for (int i = 0; i < P.functionList.size(); i++) {
			Function f = P.functionList.get(i);
			f.accept(this);
			if (i+1 < P.functionList.size()) {
				System.out.println();
			}
		}
	}
	
	public void visit (Function F) {
		FunctionDeclaration fd = F.funcDecl;
		FunctionBody fb = F.funcBody;
		fd.accept(this);
		fb.accept(this);
	}
	
	public void visit (FunctionDeclaration FD) {
		CompoundType com = FD.ctype;
		Identifier ide = FD.ident;
		ParameterList parL = FD.paraL;
		com.accept(this);
		System.out.print(" ");
		ide.accept(this);
		System.out.print(" ");
		parL.accept(this);
	}

	public void visit (FunctionBody FB) {
		VarDeclarList oneVDL = FB.theVDL;
		StatementList oneSL = FB.theSL;
		System.out.println();
		System.out.println("{");
		oneVDL.accept(this);
		oneSL.accept(this);
		System.out.println("}");
	}

	public void visit (CompoundType CT) {
		Type aType = CT.theType;
		aType.accept(this);
	}
	
	public void visit (Identifier ID) {
		System.out.print(ID.theId);
	}
	
	public void visit (ParameterList PL) {
		System.out.print("(");
		for (int i = 0; i < PL.paramList.size(); i++) {
			Parameter p = PL.paramList.get(i);
			p.accept(this);
			if ((i+1) < PL.paramList.size()) {
				System.out.print(", ");
			}
		}
		System.out.print(")");
	}
	
	public void visit (Parameter Par) {
		CompoundType ct = Par.theCT;
		Identifier id = Par.theId;
		ct.accept(this);
		System.out.print(" ");
		id.accept(this);
	}
	
	public void visit (VarDeclarList VDL) {
		indentLevel++;
		for (int i = 0; i < VDL.varDeclList.size(); i++) {
			VarDeclar vd = VDL.varDeclList.get(i);
			vd.accept(this);
		}
		if(VDL.varDeclList.size() > 0) {
			System.out.println();
		}
		indentLevel--;
	}
	
	public void visit (VarDeclar VD) {
		printIndent(indentLevel);
		CompoundType ct = VD.theCT;
		Identifier id = VD.theId;
		ct.accept(this);
		System.out.print(" ");
		id.accept(this);
		System.out.println(";");
	}
	
	public void visit (Block B) {
		printIndent(indentLevel);
		System.out.println("{");
		StatementList aStmtL = B.blockList;
		aStmtL.accept(this);
		printIndent(indentLevel);
		System.out.println("}");
	}
	
	
	
	public void visit (StatementList SL) {
		indentLevel++;
		for (int i = 0; i < SL.stmtList.size(); i++) {
			printIndent(indentLevel);
			Statement s = SL.stmtList.get(i);
			s.accept(this);
		}
		indentLevel--;
	}
	
	public void visit (Statement S) {
		S.accept(this);
	}
	
	public void visit (EmptyStatement EmS) {
		System.out.println(";");
	}
	
	public void visit (ExpressionStatement ExS) {
		Expression aExp = ExS.theExp;
		aExp.accept(this);
		System.out.println(";");
	}
	
	public void visit (PrintStatement PS) {
		System.out.print("print ");
		Expression aExp = PS.theExpr;
		aExp.accept(this);
		System.out.println(";");
	}
	
	public void visit (PrintLineStatement PLS) {
		System.out.print("println ");
		Expression aExp = PLS.theExpr;
		aExp.accept(this);
		System.out.println(";");
	}
	
	public void visit (ReturnStatement RS) {
		System.out.print("return");
		if (RS.theExpr != null) {
			System.out.print(" ");
			Expression anExp = RS.theExpr;
			anExp.accept(this);
		}
		System.out.println(";");
	}
	
	public void visit (VariableAssignmentStatement VAS) {
		Identifier anId = VAS.theId;
		Expression anExp = VAS.theExp;
		anId.accept(this);
		System.out.print("=");
		anExp.accept(this);
		System.out.println(";");
	}
	
	public void visit (ArrayAssignmentStatement AAS) {
		Identifier anId = AAS.theId;
		Expression expr1 = AAS.exp1;
		Expression expr2 = AAS.exp2;
		anId.accept(this);
		System.out.print("[");
		expr1.accept(this);
		System.out.print("]=");
		expr2.accept(this);
		System.out.println(";");
	}
	
	public void visit (WhileStatement WS) {
		Expression anExp = WS.theExp;
		Block aBlock = WS.theBlock;
		System.out.print("while (");
		anExp.accept(this);
		System.out.println(")");
		aBlock.accept(this);
	}
	
	public void visit (IfStatement IS) {
		Expression anExp = IS.theExp;
		Block aBlock = IS.block1;
		System.out.print("if (");
		anExp.accept(this);
		System.out.println(") ");
		aBlock.accept(this);
		
	}
	
	public void visit (IfElseStatement IES) {
		Expression anExp = IES.theExp;
		Block block1 = IES.block1;
		Block block2 = IES.block2;
		System.out.print("if (");
		anExp.accept(this);
		System.out.println(")");
		block1.accept(this);
		printIndent(indentLevel);
		System.out.println("else");
		block2.accept(this);
	}
	
	public void visit (Expression E) {
		E.accept(this);
	}
	
	public void visit (EqualsExpression EE) {
		Expression anExp1 = EE.theExp1;
		Expression anExp2 = EE.theExp2;
		anExp1.accept(this);
		System.out.print("==");
		anExp2.accept(this);
	}
	
	public void visit (LessThanExpression LTE) {
		Expression anExp1 = LTE.theExp1;
		Expression anExp2 = LTE.theExp2;
		anExp1.accept(this);
		System.out.print("<");
		anExp2.accept(this);
	}
	
	public void visit (AddExpression AE) {
		Expression anExp1 = AE.theExp1;
		Expression anExp2 = AE.theExp2;
		anExp1.accept(this);
		System.out.print("AE");
		anExp2.accept(this);
	}
	
	public void visit (PlusExpression PE) {
		Expression anExp1 = PE.theExp1;
		Expression anExp2 = PE.theExp2;
		anExp1.accept(this);
		System.out.print("+");
		anExp2.accept(this);
	}
	
	public void visit (MinusExpression MiE) {
		Expression anExp1 = MiE.theExp1;
		Expression anExp2 = MiE.theExp2;
		anExp1.accept(this);
		System.out.print("-");
		anExp2.accept(this);
	}
	
	public void visit (MultiplyExpression MuE) {
		Expression anExp1 = MuE.theExp1;
		Expression anExp2 = MuE.theExp2;
		anExp1.accept(this);
		System.out.print("*");
		anExp2.accept(this);
	}
	
	public void visit (IdentifierValue IV) {
		Identifier anId = IV.theId;
		anId.accept(this);
	}
	
	public void visit (ArrayReference AR) {
		Identifier anId = AR.theId;
		Expression anExp = AR.theExp;
		anId.accept(this);
		System.out.print("[");
		anExp.accept(this);
		System.out.print("]");
	}
	
	public void visit (FunctionCall FC) {
		Identifier anId = FC.theId;
		ExpressionList anExpList = FC.theExpList;
		anId.accept(this);
		System.out.print("(");
		anExpList.accept(this);
		System.out.print(")");
	}
	
	public void visit (ExpressionList EL) {
		Iterator<Expression> itr = EL.theExpList.iterator();
		while (itr.hasNext()) {
			itr.next().accept(this);
			if (itr.hasNext()){
				System.out.print(",");
			}
		}
	}
	
	public void visit (ParenthExpression ParEx) {
		Expression anExp = ParEx.theExp;
		System.out.print("(");
		anExp.accept(this);
		System.out.print(")");
	}
	
	public void visit (IntegerLiteral IL) {
		System.out.print(IL.theInt);
	}
	
	public void visit (FloatLiteral FL) {
		System.out.print(FL.theFloat);
	}
	
	public void visit (CharacterLiteral CL) {
		System.out.print("'");
		System.out.print(CL.theChar);
		System.out.print("'");
	}
	
	public void visit (StringLiteral SL) {
		System.out.print(SL.theStr);
	}
	
	public void visit (BooleanLiteral BL) {
		System.out.print(BL.theBool);
	}
	
	public void visit (ArrayType AT) {
		Type aType = AT.theType;
		IntegerLiteral anInt = AT.theIntLit;
		aType.accept(this);
		System.out.print("[");
		anInt.accept(this);
		System.out.print("]");
	}
	
	public void visit (BooleanType BT) {
		System.out.print(BT.typeOf);
	}
	
	public void visit (CharType CT) {
		System.out.print(CT.typeOf);
	}
	
	public void visit (FloatType FT) {
		System.out.print(FT.typeOf);
	}
	
	public void visit (IntegerType IT) {
		System.out.print(IT.typeOf);
	}
	
	public void visit (StringType ST) {
		System.out.print(ST.typeOf);
	}
	
	public void visit (Type T) {
		T.accept(this);
	}
	
	public void visit (VoidType VT) {
		System.out.print(VT.typeOf);
	}
	
	
}