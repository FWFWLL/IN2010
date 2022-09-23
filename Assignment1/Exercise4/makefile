JC := javac

BUILDDIR := build
SRCDIR := src

.PHONY: all clean run_array run_heap

all: $(SRCDIR)/*.java | $(BUILDDIR)
	@$(JC) $^ -d $(BUILDDIR)

clean:
	@rm -rv $(BUILDDIR)

run_array: all
	@java -classpath $(BUILDDIR) BalanceArray

run_heap: all
	@java -classpath $(BUILDDIR) BalanceHeap

$(BUILDDIR):
	@mkdir -p $@
