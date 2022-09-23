# Exercise 4

## How to run

First compile the .java files
```bash
make
```

---

To run `BalanceArray`:
```bash
seq <INTEGER> | java -classpath build/ BalanceArray
```
Replace `<INTEGER>` with any integer

**OR**

Just run:
```bash
seq `<INTEGER>` | make run_array
```
Remember to replace `<INTEGER>`  

---

To run `BalanceHeap`:
```bash
seq `<INTEGER>` | java -classpath build/ BalanceHeap
```

**OR**

Just run:
```bash
seq `<INTEGER>` | make run_heap
```
Remember to replace `<INTEGER>`  

## Pseudocode

`BalanceArray` algorithm:
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

`BalanceHeap` algorithm:
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
