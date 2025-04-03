grammar ul;

@header {
	
	import AST.*;
	import Types.*;
	
}

@members {
	protected void mismatch (IntStream input, int ttype, BitSet follow) throws RecognitionException {
		throw new MismatchedTokenException(ttype, input);
	}
	public void recoverFromMismatchedSet (IntStream input, RecognitionException e, BitSet follow) throws RecognitionException {
			reportError(e);
			throw e;
	}
}

@rulecatch {
	catch (RecognitionException ex) {
			reportError(ex);
			throw ex;
	}
}




/* ============= Grammar =========== */

program returns [Program P]
	@init
	{
		P = new Program();
	}
	:
		(
			f = function
				{
					P.addFunction(f);
				}
		)+ EOF
	;

function returns [Function F]
	@init
	{
		F = new Function();
	}
	:
		fd = functionDecl
			{
				F.funcDecl = fd;
			}
		fb = functionBody
			{
				F.funcBody = fb;
			}
	;

functionDecl returns [FunctionDeclaration FD]
	@after
	{
		FD = new FunctionDeclaration(ct, id, pa);
	}
	:
		ct = compoundType id = identifier '(' pa = param ')'
	;

param returns [ParameterList PL]
	@init
	{
		PL = new ParameterList();
	}
	:
		(ct = compoundType id = identifier
			{
				Parameter P = new Parameter(ct, id);
				PL.addParam(P);
			}
			(
				mp = moreParams
					{
						Parameter P = mp;
						PL.addParam(P);
					}
			)*)?
	;

moreParams returns [Parameter P]
	:
		',' ct = compoundType id = identifier
			{
				P = new Parameter (ct, id);
			}
	;

functionBody returns [FunctionBody FB]
	@init
	{
		VarDeclarList VDList = new VarDeclarList();
		StatementList SList = new StatementList();
	}
	@after
	{
		FB = new FunctionBody(VDList, SList);		
	}
	:
		'{' (
				vd = varDecl
					{
						VDList.addVarDecl(vd);
					}
			)*
			(
				st = statement
					{
						SList.addStmt(st);
					}
			)* '}'
	;

varDecl returns [VarDeclar VD]
	:
		ct = compoundType id = identifier
			{
				VD = new VarDeclar(ct,id);
			}
		';'
	;

compoundType returns [CompoundType CT]
	:
		  t1 = type
		  	{
				CT = new CompoundType(t1);
			}
		| t2 = type '[' i = intLit ']'
			{
				ArrayType anAT = new ArrayType(t2, i);
				CT = new CompoundType(anAT);
			}
	;

type returns [Type Ty]
	:
		  in = INT 		{Ty = new IntegerType($in.line);}
		| fl = FLOAT	{Ty = new FloatType($fl.line);}
		| ch = CHAR		{Ty = new CharType($ch.line);}
		| st = STRING	{Ty = new StringType($st.line);}
		| bo = BOOLEAN	{Ty = new BooleanType($bo.line);}
		| vo = VOID		{Ty = new VoidType($vo.line);}
	;
	
identifier returns [Identifier I]
	:
		id = IDENT
			{
				I = new Identifier($id.text, $id.line);
			}
	;
	
statement returns [Statement S] options {backtrack=true;}
	:
		  ems = emptyStmt	{ S = ems;}
		| exs = exprStmt	{ S = exs;}
		| ps  = printStmt	{ S = ps; }
		| pls = printLnStmt	{ S = pls;}
		| rs  = retStmt		{ S = rs; }
		| vas = varAssStmt 	{ S = vas;}
		| aas = arrAssStmt 	{ S = aas;}
		| ws  = whileStmt 	{ S = ws; }
		| is  = ifStmt 		{ S = is; }
	;

emptyStmt returns [EmptyStatement EmS]
	@init
	{
		EmS = new EmptyStatement();
	}
	:
		';'
	;

exprStmt returns [ExpressionStatement ExS]
	@after
	{
		ExS = new ExpressionStatement(e);
	}
	:
		e = expr ';'
	;

printStmt returns [PrintStatement PS]
	:
		PRINT e = expr
			{
				PS = new PrintStatement(e);
			}
		';'
	;
	
printLnStmt returns [PrintLineStatement PLS]
	:
		PRINTLN e = expr
			{
				PLS = new PrintLineStatement(e);
			}
		';'
	;

retStmt returns [ReturnStatement RS]
	@init
	{
		Expression exp = null;
	}
	@after
	{
		if (exp != null)
			RS = new ReturnStatement(e, $rst.line);
		else
			RS = new ReturnStatement($rst.line);
	}
	:
		rst = RETURN (e = expr {exp = e;})? ';'
	;

varAssStmt returns [VariableAssignmentStatement VAS]
	@after
	{
		VAS = new VariableAssignmentStatement(id, e);
	}
	:
		id = identifier '=' e = expr ';'
	;
	
arrAssStmt returns [ArrayAssignmentStatement AAS]
	@after
	{
		AAS = new ArrayAssignmentStatement(id, ex1, ex2);
	}
	:
		id = identifier '[' ex1 = expr ']' '=' ex2 = expr ';'
	;
	
whileStmt returns [WhileStatement WS]
	@after
	{
		WS = new WhileStatement(e, b);
	}
	:
		WHILE '(' e = expr ')' b = block
	;
	
ifStmt returns [IfStatement IS] options {backtrack = true;}
	@after
	{
		if (b2 != null) {
			IS = new IfElseStatement(e, b1, b2);
		} else {
			IS = new IfStatement(e,b1);
		}
	}
	:
		IF '(' e = expr ')' b1 = block ELSE b2 = block
		| IF '(' e = expr ')' b1 = block
	;

expr returns [Expression Ex]
	:
		e = equalExpr
			{
				Ex = e;
			}
	;

equalExpr returns [Expression Ex]
	@init
	{
		Expression it = null;
	}
	@after
	{
		Ex = it;
	}
	:
		e1 = lessThanExpr {it = e1;}
		('==' e2 = lessThanExpr {it = new EqualsExpression(it, e2);}
		)*
	;

lessThanExpr returns [Expression Ex]
	@init
	{
		Expression it = null;
	}
	@after
	{
		Ex = it;
	}
	:
		e1 = addExpr {it = e1;}
		('<' e2 = addExpr {it = new LessThanExpression(it, e2);}
		)*
	;

addExpr returns [Expression Ex]
	@init
	{
		Expression it = null;
	}
	@after
	{
		Ex = it;
	}
	:
		e1 = multExpr {it = e1;}
		(ch = ('+'|'-') e2 = multExpr
			{
				if ($ch.text.charAt(0) == '+') {
					it = new PlusExpression(it,e2);
				} else {
					it = new MinusExpression(it,e2);
				}
			}
		)*
	;

multExpr returns [Expression Ex]
	@init
	{
		Expression it = null;
	}
	@after
	{
		Ex = it;
	}
	:
		e1 = atom {it = e1;}
		('*' e2 = atom {it = new MultiplyExpression(it,e2);}
		)*
	;

atom returns [Expression Ex]
	:
		  id = identVal {Ex = id;}
		| ar = arrayRef {Ex = ar;}
		| fc = funcCall {Ex = fc;}
		| pe = parenExp {Ex = pe;}
		| li = literal  {Ex = li;}
	;


identVal returns [Expression IV]
	:
		id = identifier {IV = new IdentifierValue(id);}
	;
	
arrayRef returns [Expression AR]
	@after
	{
		AR = new ArrayReference(id, e);
	}
	:
		id = identifier '[' e = expr ']'
	;
	
funcCall returns [Expression FC]
	@after
	{
		FC = new FunctionCall(id, el);
	}
	:
		id = identifier '(' el = exprList ')'
	;

exprList returns [ExpressionList EL]
	@init
	{
		EL = new ExpressionList();
	}
	:
		(e1 = expr
			{
				EL.addExp(e1);
			}
		(e2 = exprMore
			{
				EL.addExp(e2);
			}
		)*)?
	;

exprMore returns [Expression Ex]
	:
		',' e = expr {Ex = e;}
	;
	
parenExp returns [Expression PE]
	:
		'(' e = expr {PE = new ParenthExpression(e);} ')'
	;
	
literal returns [Expression Lit]
	:
		  il = intLit	{Lit = il;}
		| fl = floatLit	{Lit = fl;}
		| cl = charLit	{Lit = cl;}
		| sl = strLit	{Lit = sl;}
		| bl = boolLit	{Lit = bl;}
	;
	
intLit returns [IntegerLiteral IL]
	:
		ic = INTCON
			{
				IL = new IntegerLiteral(Integer.parseInt($ic.text), $ic.line);
			}
	;
	
floatLit returns [FloatLiteral FL]
	:
		fc = FLTCON
			{
				FL = new FloatLiteral(Double.parseDouble($fc.text), $fc.line);
			}
	;
	
charLit returns [CharacterLiteral CL]
	:
		cc = CHRCON
			{
				CL = new CharacterLiteral($cc.text.charAt(1), $cc.line);
			}
	;
	
strLit returns [StringLiteral SL]
	:
		sc = STRCON
			{
				SL = new StringLiteral($sc.text, $sc.line);
			}
	;
	
boolLit returns [BooleanLiteral BL]
	@after
	{
		BL = new BooleanLiteral($val.text, $val.line);
	}
	:
		  val = TRUE
		| val = FALSE
	;
	

block returns [Block B]
	@init
	{
		B = new Block();
	}
	:
		'{' (s = statement
			{
				B.blockAddStmt(s);
			}
		)* '}'
	;





/* ============= Lexer ============= */

INT
	:
		'int'
	;

FLOAT
	:
		'float'
	;

CHAR
	:
		'char'
	;

STRING
	:
		'string'
	;

BOOLEAN
	:
		'boolean'
	;

VOID
	:
		'void'
	;


PRINT
	:
		'print'
	;

PRINTLN
	:
		'println'
	;

RETURN
	:
		'return'
	;

WHILE
	:
		'while'
	;

IF
	:
		'if'
	;

ELSE
	:
		'else'
	;

TRUE
	:
		'true'
	;

FALSE
	:
		'false'
	;

INTCON
	:
		('0'..'9')+
	;

FLTCON
	:
		(('0'..'9')+'.'('0'..'9')+)
	;

STRCON
	:
		'\u0022' (('a'..'z')|('A'..'Z')|('0'..'9')|' '|'_'|'\u002E'|'\u0021'|'\u002C')+ '\u0022'
	;

CHRCON
	:
		'\u0027' (('a'..'z')|('A'..'Z')|('0'..'9')|' '|'_'|'\u002E'|'\u0021'|'\u002C') '\u0027'
	;

IDENT
	:
		(('a'..'z')|('A'..'Z')|'_')(('a'..'z')|('A'..'Z')|('0'..'9')|'_')*
	;

WS
	:
		( ' ' | '\t' | '\r' | '\n' )+ { $channel = HIDDEN;}
	;

COMMENT
	:
		'//' ~('\r' | '\n')* ('\r' | '\n') {$channel = HIDDEN;}
	;

/* ============= End =============== */