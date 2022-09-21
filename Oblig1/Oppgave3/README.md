# Oppgave 3

## Pseudocode
```
SET cat's node to first number from STDIN

WHILE STDIN is not "-1" THEN
	INSERT node-relations into HashMap with KEY as child nodes, PARENT as VALUE
END

REPEAT until HashMap does NOT contain cat's node
	SET cat's node to VALUE (PARENT) from HashMap using KEY (cat's current node)
END
```
