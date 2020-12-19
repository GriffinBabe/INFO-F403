BEGINPROG EmbeddedConditions

lower := 5
upper := 10
READ(input)

IF (input > lower) THEN
   IF (input > upper) THEN
   ELSE
        WHILE (input > lower) DO
            PRINT(input)
            input := input - 1
        ENDWHILE
    ENDIF
ENDIF
ENDPROG
