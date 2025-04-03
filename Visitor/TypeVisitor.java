package Visitor;

import AST.*;
import Types.*;
import Environment.*;
import SemanticException.*;
import java.util.Iterator;
import java.util.ArrayList;

public class TypeVisitor {
	
	FunctionEnvironment fe;
	VariableEnvironment ve;
	String currentFunction;
	Type currentReturnType;
	int currentLine;
	
	public TypeVisitor () {
		fe = new FunctionEnvironment();
		ve = new VariableEnvironment();
	}
	
	boolean sameTypes(Type one, Type two) {
		if(one.typeOf.equals(two.typeOf)) {
			return true;
		} else {
			return false;
		}
	}
	
	void comparePlEl(ParameterList pl, ExpressionList el) throws SemanticException {
		for (int i = 0; i < pl.size(); i++) {
			Type plType = pl.get(i).theCT.accept(this);
			Type elType = el.get(i).accept(this);
			if (!sameTypes(plType,elType)){
				throw new SemanticException("Error:" + currentLine + ":Function call parameter " + (i+1) + " doesn't match expression " + (i+1));
			}
		}
	}
	
	public FunctionEnvironment visit (Program P) throws SemanticException {
		
		Iterator<Function> itr1 = P.functionList.iterator();
		while (itr1.hasNext()) {
			Function f = itr1.next();
			String fid = f.getID();
			f.funcDecl.ident.accept(this);
			if (fe.functionExists(fid)) {
				throw new SemanticException("Error:" + currentLine + ":Function "+fid+" already exists");
			}
			fe.addFunction(fid,f);
		}
		
		if (P.functionList.size() == 0) {
			throw new SemanticException("Error:" + currentLine + ":No functions found");
		}
		
		if (!P.mainExists()) {
			throw new SemanticException("Error:" + currentLine + ":Main function not found");
		}
		
		Iterator<Function> itr2 = P.functionList.iterator();
		while (itr2.hasNext()) {
			itr2.next().accept(this);
		}
		
		return fe;
		
	}
	
	public void visit (Function F) throws SemanticException {
		String fid = F.getID();
		ve.beginScope();
		currentReturnType = F.funcDecl.accept(this);
		currentFunction = fid;
		F.funcBody.accept(this);
	}
	
	public Type visit (FunctionDeclaration FD) throws SemanticException {
		ve.print();
		FD.paraL.accept(this);
		return FD.ctype.accept(this);
	}
	
	public void visit (FunctionBody FB) throws SemanticException {
		VarDeclarList vdl = FB.theVDL;
		StatementList sl = FB.theSL;
		vdl.accept(this);
		sl.accept(this);
	}
	
	public Type visit (CompoundType CT) throws SemanticException {
		Type t = CT.theType.accept(this);
		currentLine = t.lineNumber;
		if(t instanceof ArrayType){
			ArrayType  atype = (ArrayType)t;
			Type ident = atype.theType;
			if (sameTypes(ident, new VoidType())) {
				throw new SemanticException("Error:" + currentLine + ":Array type cannot be void");
			}
		}
		return t;
	}
	
	public Type visit (Identifier ID) throws SemanticException {
		currentLine = ID.lineNumber;
		return ve.lookup(ID.theId);
	}
	
	public void visit (ParameterList  Par) throws SemanticException {
		Iterator<Parameter> itr = Par.paramList.iterator();
		while(itr.hasNext()){
			itr.next().accept(this);
		}
	}
	
	public void visit (Parameter Par) throws SemanticException {
		Par.theId.accept(this);
		if (ve.variableExists(Par.getID())) {
			throw new SemanticException("Error:" + currentLine + ":Parameter " + Par.getID() + " already exists");
		}
		if (Par.theCT.accept(this) instanceof VoidType) {
			throw new SemanticException("Error:" + currentLine + ":" + Par.getID() + " cannot be of type void");
		}
		ve.addVar(Par.getID(), Par.theCT.accept(this));
	}
	
	public void visit (VarDeclarList VDL) throws SemanticException {
		Iterator<VarDeclar> itr = VDL.varDeclList.iterator();
		while(itr.hasNext()) {
			itr.next().accept(this);
		}
	}
	
	public void visit (VarDeclar VD) throws SemanticException {
		Type t = VD.theCT.accept(this);
		VD.theId.accept(this);
		if (ve.variableExists(VD.getID())) {
			throw new SemanticException("Error:" + currentLine + ":Variable " + VD.getID() + " already exists");
		}
		if (VD.getID().equals(currentFunction)) {
			throw new SemanticException("Error:" + currentLine + ":Variable " + VD.getID() + " is same is function name");
		}
		if (t instanceof VoidType) {
			throw new SemanticException("Error:" + currentLine + ":Variable " + VD.getID() + " cannot be of type void");
		}
		ve.addVar(VD.getID(), t);
		
	}
	
	public void visit (StatementList SL) throws SemanticException {
		Iterator<Statement> itr = SL.stmtList.iterator();
		while (itr.hasNext()) {
			itr.next().accept(this);
		}
	}
	
	public void visit (Statement S) throws SemanticException {
		S.accept(this);
	}
	public void visit (EmptyStatement EmS) throws SemanticException {
	}
	public void visit (ExpressionStatement ExS) throws SemanticException {
		ExS.theExp.accept(this);
	}
	public void visit (PrintStatement PS) throws SemanticException {
		Type t = PS.theExpr.accept(this);
		
		if (t instanceof ArrayType) {
			throw new SemanticException("Error:" + currentLine + ":Cannot print array");
		}
		if(sameTypes(t,new VoidType())) {
			throw new SemanticException("Error:" + currentLine + ":Cannot print void type");
		}
	}
	public void visit (PrintLineStatement PLS) throws SemanticException {
		Type t = PLS.theExpr.accept(this);
		if (t instanceof ArrayType) {
			throw new SemanticException("Error:" + currentLine + ":Cannot println array");
		}
		if(sameTypes(t,new VoidType())) {
			throw new SemanticException("Error:" + currentLine + ":Cannot println void type");
		}
	}
	public void visit (ReturnStatement RS) throws SemanticException {
		
		currentLine = RS.lineNumber;
		if (sameTypes(new VoidType(),currentReturnType)) {
			if (RS.theExpr != null) {
				RS.theExpr.accept(this);
				throw new SemanticException("Error:" + currentLine + ":Function return is void, return statement is not");
			}
		} else {
			if (RS.theExpr == null) {
				throw new SemanticException("Error:" + currentLine + ":Function return is not void, return statement is void");
			}
			Type e = RS.theExpr.accept(this);
			if (!sameTypes(e,currentReturnType)) {
				throw new SemanticException("Error:" + currentLine + ":Function return type and return statement type are not the same");
			}
		}
		
	}
	
	public void visit (VariableAssignmentStatement VAS) throws SemanticException {
		if (!ve.variableExists(VAS.getID())) {
			throw new SemanticException("Error:" + currentLine + ":Variable " + VAS.getID() + " not declared");
		}
		Type a = VAS.theId.accept(this);
		Type b = VAS.theExp.accept(this);
		if (!sameTypes(a,b)) {
			throw new SemanticException("Error:" + currentLine + ":Assigned wrong type to " + VAS.getID() + ". Should be " + a.typeOf);
		}
	}
	
	public void visit (ArrayAssignmentStatement AAS) throws SemanticException {
		if (!ve.variableExists(AAS.getID())) {
			throw new SemanticException("Error:" + currentLine + ":Array " + AAS.getID() + " not declared");
		}
		Type array = AAS.theId.accept(this);
		ArrayType  atype = (ArrayType)array;
		Type ident = atype.theType;
		Type index = AAS.exp1.accept(this);
		Type assgn = AAS.exp2.accept(this);
		if (!sameTypes(index,new IntegerType())){
			throw new SemanticException("Error:" + currentLine + ":Array index not integer");
		}
		if (!sameTypes(ident,assgn)) {
			throw new SemanticException("Error:" + currentLine + ":Assigned wrong type to " + AAS.getID() + ". Should be " + ident.typeOf);
		}
	}
	public void visit (WhileStatement WS) throws SemanticException {
	
		Type condition = WS.theExp.accept(this);
		if (!sameTypes(condition,new BooleanType())){
			throw new SemanticException("Error:" + currentLine + ":While statement condition not of type boolean");
		}
		WS.theBlock.accept(this);
	}
	public void visit (IfStatement IS) throws SemanticException {
		Type condition = IS.theExp.accept(this);
		if (!sameTypes(condition,new BooleanType())){
			throw new SemanticException("Error:" + currentLine + ":If statement condition not of type boolean");
		}
		IS.block1.accept(this);
	}
	public void visit (IfElseStatement IES) throws SemanticException {
		Type condition = IES.theExp.accept(this);
		if (!sameTypes(condition,new BooleanType())){
			throw new SemanticException("Error:" + currentLine + ":If-else statement condition not of type boolean");
		}
		IES.block1.accept(this);
		IES.block2.accept(this);
	}
	
	public Type visit (Expression E) throws SemanticException {
		return E.accept(this);
	}
	
	public Type visit (EqualsExpression EE) throws SemanticException {
		Type t1 = EE.theExp1.accept(this);
		Type t2 = EE.theExp2.accept(this);
		if (sameTypes(t1,t2)) {
			if (t1.typeOf.equals("void")) {
				throw new SemanticException("Error:" + currentLine + ":Void type not supported for equals expression");
			}
			return new BooleanType(t1.lineNumber);
		} else {
			throw new SemanticException("Error:" + currentLine + ":Mismatched types for equals expression");
		}
		
	}
	public Type visit (LessThanExpression LTE) throws SemanticException {
		Type t1 = LTE.theExp1.accept(this);
		Type t2 = LTE.theExp2.accept(this);
		if (sameTypes(t1,t2)) {
			if(t1 instanceof ArrayType) {
				throw new SemanticException("Error:" + currentLine + ":Array type not supported for less than expression");
			}
			if (t1.typeOf.equals("void")) {
				throw new SemanticException("Error:" + currentLine + ":Void type not supported for less than expression");
			}
			if (t1.typeOf.equals("boolean")) {
				throw new SemanticException("Error:" + currentLine + ":Boolean type not supported for less than expression");
			}
			return new BooleanType(t1.lineNumber);
		} else {
			throw new SemanticException("Error:" + currentLine + ":Mismatched types for less than expression");
		}
	}
	public Type visit (AddExpression AE) throws SemanticException {
		Type t1 = AE.theExp1.accept(this);
		Type t2 = AE.theExp2.accept(this);
		if (sameTypes(t1,t2)) {
			if(t1 instanceof ArrayType) {
				throw new SemanticException("Error:" + currentLine + ":Array type not supported for add expression");
			}
			if (t1.typeOf.equals("void")) {
				throw new SemanticException("Error:" + currentLine + ":Void type not supported for add expression");
			}
			if (t1.typeOf.equals("boolean")) {
				throw new SemanticException("Error:" + currentLine + ":Boolean type not supported for add expression");
			}
			return t1;
		} else {
			throw new SemanticException("Error:" + currentLine + ":Mismatched types for add expression");
		}
	}
	public Type visit (PlusExpression PE) throws SemanticException {
		Type t1 = PE.theExp1.accept(this);
		Type t2 = PE.theExp2.accept(this);
		if (sameTypes(t1,t2)) {
			if(t1 instanceof ArrayType) {
				throw new SemanticException("Error:" + currentLine + ":Array type not supported for plus expression");
			}
			if (t1.typeOf.equals("void")) {
				throw new SemanticException("Error:" + currentLine + ":Void type not supported for plus expression");
			}
			if (t1.typeOf.equals("boolean")) {
				throw new SemanticException("Error:" + currentLine + ":Boolean type not supported for plus expression");
			}
			return t1;
		} else {
			throw new SemanticException("Error:" + currentLine + ":Mismatched types for plus expression");
		}
	}
	public Type visit (MinusExpression MiE) throws SemanticException {
		Type t1 = MiE.theExp1.accept(this);
		Type t2 = MiE.theExp2.accept(this);
		if (sameTypes(t1,t2)) {
			if(t1 instanceof ArrayType) {
				throw new SemanticException("Error:" + currentLine + ":Array type not supported for minus expression");
			}
			if (t1.typeOf.equals("void")) {
				throw new SemanticException("Error:" + currentLine + ":Void type not supported for minus expression");
			}
			if (t1.typeOf.equals("boolean")) {
				throw new SemanticException("Error:" + currentLine + ":Boolean type not supported for minus expression");
			}
			if (t1.typeOf.equals("string")) {
				throw new SemanticException("Error:" + currentLine + ":String type not supported for minus expression");
			}
			return t1;
		} else {
			throw new SemanticException("Error:" + currentLine + ":Mismatched types for minus expression");
		}
	}
	public Type visit (MultiplyExpression MuE) throws SemanticException {
		Type t1 = MuE.theExp1.accept(this);
		Type t2 = MuE.theExp2.accept(this);
		if (sameTypes(t1,t2)) {
			if(t1 instanceof ArrayType) {
				throw new SemanticException("Error:" + currentLine + ":Array type not supported for multiply expression");
			}
			if (t1.typeOf.equals("void")) {
				throw new SemanticException("Error:" + currentLine + ":Void type not supported for multiply expression");
			}
			if (t1.typeOf.equals("boolean")) {
				throw new SemanticException("Error:" + currentLine + ":Boolean type not supported for multiply expression");
			}
			if (t1.typeOf.equals("string")) {
				throw new SemanticException("Error:" + currentLine + ":String type not supported for multiply expression");
			}
			if (t1.typeOf.equals("char")) {
				throw new SemanticException("Error:" + currentLine + ":Char type not supported for multiply expression");
			}
			return t1;
		} else {
			throw new SemanticException("Error:" + currentLine + ":Mismatched types for multiply expression");
		}
	}
	
	public Type visit (IdentifierValue IV) throws SemanticException {
		IV.theId.accept(this);
		if (!ve.variableExists(IV.getID())) {
			throw new SemanticException("Error:" + currentLine + ":Variable " + IV.getID() + " not declared");
		}
		return IV.theId.accept(this);
	}
	
	public Type visit (ArrayReference AR) throws SemanticException {
		AR.theId.accept(this);
		if (!ve.variableExists(AR.getID())) {
			throw new SemanticException("Error:" + currentLine + ":Array " + AR.getID() + " not declared");
		}
		if (!sameTypes(new IntegerType(),AR.theExp.accept(this))) {
			throw new SemanticException("Error:" + currentLine + ":Array reference of " + AR.getID() + " does not have integer type index");
		}
		Type array = AR.theId.accept(this);
		ArrayType  atype = (ArrayType)array;
		return atype.theType;
	}
	
	public void visit (Block B) throws SemanticException {
		StatementList bl = B.blockList;
		bl.accept(this);
	}
	
	public Type visit (FunctionCall FC) throws SemanticException {
		FC.theId.accept(this);
		if (!fe.functionExists(FC.getID())) {
			throw new SemanticException("Error:" + currentLine + ":Calling function " + FC.getID() + " but it hasn't been defined");
		}
		ExpressionList el = FC.theExpList;
		ParameterList pl = fe.lookup(FC.getID()).funcDecl.paraL;
		if (el.size() != pl.size()) {
			throw new SemanticException("Error:" + currentLine + ":Calling function " + FC.getID() + " but expression list doesn't have the required number of parameters");
		}
		if (el.size() > 0) {
			comparePlEl(pl,el);
		}
		return fe.lookup(FC.getID()).getType();
	}
	
	public Type visit (ExpressionList EL) throws SemanticException {
		return new IntegerType(-1);
	}
	
	public Type visit (ParenthExpression ParEx) throws SemanticException {
		return ParEx.theExp.accept(this);
	}
	
	
	public Type visit (IntegerLiteral IL) throws SemanticException {
		currentLine = IL.lineNumber;
		return new IntegerType(IL.lineNumber);
	}
	public Type visit (FloatLiteral FL) throws SemanticException {
		currentLine = FL.lineNumber;
		return new FloatType(FL.lineNumber);
	}
	public Type visit (CharacterLiteral CL) throws SemanticException {
		currentLine = CL.lineNumber;
		return new CharType(CL.lineNumber);
	}
	public Type visit (StringLiteral SL) throws SemanticException {
		currentLine = SL.lineNumber;
		return new StringType(SL.lineNumber);
	}
	public Type visit (BooleanLiteral BL) throws SemanticException {
		currentLine = BL.lineNumber;
		return new BooleanType(BL.lineNumber);
	}
	
	
	public Type visit (ArrayType AT) throws SemanticException {
		return AT;
	}
	public Type visit (BooleanType BT) throws SemanticException {
		return BT;
	}
	public Type visit (CharType CT) throws SemanticException {
		return CT;
	}
	public Type visit (FloatType FT) throws SemanticException {
		return FT;
	}
	public Type visit (IntegerType IT) throws SemanticException {
		return IT;
	}
	public Type visit (StringType ST) throws SemanticException {
		return ST;
	}
	public Type visit (Type T) throws SemanticException {
		return T;
	}
	public Type visit (VoidType VT) throws SemanticException {
		return VT;
	}
	
	
}