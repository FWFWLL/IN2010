# Exercise 4

## How to run

First compile the .java files
```bash
make
```

Run this command to run this exercise:
```bash
seq <INTEGER> | java -classpath build/ BalanceArray
```
Replace `<INTEGER>` with any integer

OR

Just run this:
```bash
seq `<INTEGER>` | make run
```
Remember to replace `<INTEGER>`

## Pseudocode

```lua
FUNCTION printBalancedArray(ARRAY, START, END)
	-- Base case
	IF START < END
		STOP
	END

	SET MIDDLE to (START + END) / 2

	PRINT middle element in ARRAY using MIDDLE

	printBalancedArray(ARRAY, START, MIDDLE - 1) -- Recursively PRINT left side of ARRAY
	printBalancedArray(ARRAY, MIDDLE + 1, END) -- Recursively PRINT right side of ARRAY
END
```

```lua
FUNCTION printBalancedHeap(HEAP, START, END)
	CREATE temporary heap using HEAP
	-- Base case
	IF START < END
		STOP
	END

	SET MIDDLE to (START + END) / 2

	POLL HEAP until we reach index of middle

	PRINT polled element from HEAP

	printBalancedArray(HEAP, START, MIDDLE - 1) -- Recursively PRINT left side of HEAP
	printBalancedArray(HEAP, MIDDLE + 1, END) -- Recursively PRINT right side of HEAP
END
```
