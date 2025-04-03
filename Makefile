#
GNAME= ul
GSRC= $(GNAME).g

all: grammar compiler

grammar: $(GSRC)
	java org.antlr.Tool -fo . $(GSRC) 

compiler:
	javac *.java

clean:
	rm *.class $(GNAME)*.java $(GNAME)__.g $(GNAME).tokens AST/*.class Visitor/*.class Types/*.class Environment/*.class SemanticException/*.class Temps/*.class Instructions/*.class Labels/*.class