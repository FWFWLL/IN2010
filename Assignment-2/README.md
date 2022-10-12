# Assignment 2

## How to run

First compile the .java files
```bash
make
```

---

To run the program:
```bash
java -classpath build/ Main INPUT=$INPUT EXERCISE=$EXERCISE
```
Replace `$INPUT` with an input file check like `test/random_10`
Replace `$EXERCISE` with either `1` or `2`

**OR**

Just run:
```bash
make run INPUT=$INPUT EXERCISE=$EXERCISE
```
Remember to replace `$INPUT` and `$EXERCISE`

**OR**

Run this:
```bash
make run
```
this will run `INPUT=test/random_10` with `EXERCISE=1` by default
