JC := javac

BUILDDIR := build
SRCDIR := src

.PHONY: all clean run

all: $(SRCDIR)/*.java | $(BUILDDIR)
	@$(JC) $^ -d $(BUILDDIR)

clean:
	@rm -rv $(BUILDDIR)

run: all
	@java -classpath $(BUILDDIR) BalanceArray

$(BUILDDIR):
	@mkdir -p $@
