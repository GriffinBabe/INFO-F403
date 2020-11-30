all:
	jflex src/scanner/LexicalAnalyzer.flex
	javac -d bin -cp src/ src/Main.java
	jar cfe dist/part2.jar Main -C bin .

testing:
		java -jar dist/Part2.jar test/Factorial.fs
