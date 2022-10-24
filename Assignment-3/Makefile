JC := javac

BUILDDIR := build
SRCDIR := src

.PHONY: all clean run

all: $(SRCDIR)/*.java | $(BUILDDIR)
	@$(JC) $^ -d $(BUILDDIR)

clean:
	@rm -rv $(BUILDDIR) cache.ffl

run: all
	@java -classpath $(BUILDDIR) Main

$(BUILDDIR):
	@mkdir -p $@
