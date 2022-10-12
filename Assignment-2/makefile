JC := javac

BUILDDIR := build
SRCDIR := src

# Default arguments
INPUT := test/random_10
EXERCISE := 1

.PHONY: all clean run

all: $(SRCDIR)/*.java | $(BUILDDIR)
	@$(JC) $^ -d $(BUILDDIR)

clean:
	@rm -rv $(BUILDDIR) test/*.out test/*.csv

run: all
	@java -classpath $(BUILDDIR) Main $(INPUT) $(EXERCISE)

$(BUILDDIR):
	@mkdir -p $@
