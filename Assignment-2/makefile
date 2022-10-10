JC := javac

BUILDDIR := build
SRCDIR := src

.PHONY: all clean run

all: $(SRCDIR)/*.java | $(BUILDDIR)
	@$(JC) $^ -d $(BUILDDIR)

clean:
	@rm -rv $(BUILDDIR) test/*.out test/*.csv

run: all $(INPUT)
	@java -classpath $(BUILDDIR) Main $(INPUT)

$(BUILDDIR):
	@mkdir -p $@
