# Oppgave 2


## Pseudocode
```
push_front(x)
	CREATE new node

	IF size of queue EQUALS 0 THEN
		SET head, middle, tail to the new node
	END

	SET new node's next to head

	IF head is NOT null THEN
		SET head's prev to the new node
	END

	SET head to the new node

	INCREMENT size of queue
END
```

```
push_middle(x)
	CREATE new node

	IF size of queue EQUALS 0 THEN
		SET head, middle, tail to the new node
	ELSE
		FIND middle node

		SET new node's previous to middle

		IF middle is NOT null THEN
			SET new node's next to middle's next
			SET middle's next's previous to the new node
			SET middle's next to the new node
		END

		SET middle to the new node
	END

	INCREMENT size of queue
END
```

```
push_back(x)
	CREATE new node

	IF size of queue EQUALS 0 THEN
		SET head, middle, tail to the new node
	END

	SET new node's previous to the tail

	IF tail is NOT null THEN
		SET tail's next to the new node
	END

	SET tail to the new node

	INCREMENT size of queue
END
```

```
get(i)
	SET current node to head

	FOREACH node up to index i
		SET current node to current node's next
	END

	RETURN current node
END
```

## Big-O Notation
if `N` has no limit:
push_front(x): O(1)
push_middle(x): O(n)
push_back(x): O(1)
get(i): O(n)

if `N` has a bound:
push_front(x): O(1)
push_middle(x): O(1)
push_back(x): O(1)
get(i): O(1)
