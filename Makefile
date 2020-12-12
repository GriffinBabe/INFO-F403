all:
	jflex src/scanner/LexicalAnalyzer.flex
	javac -d bin -cp src/ src/Main.java
	jar cfe dist/part3.jar Main -C bin .

testing:
		java -jar dist/part3.jar test/Factorial.fs
