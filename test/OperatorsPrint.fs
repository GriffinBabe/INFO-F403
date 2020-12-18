BEGINPROG AdditionSubstraction
a := 1
b := 2
c := a + b - 3
d := a - b * c
e := a * b - c
f := a * b - d / e
PRINT(c)
PRINT(d)
PRINT(e)
PRINT(f) // expects 2 as d / e = 0.5 => 0 in integer
ENDPROG
