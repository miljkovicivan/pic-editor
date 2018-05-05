SRC = .
SOURCES = $(shell ls $(SRC)/*.scala)
S = scala
SC = scalac

# default target is to show help
all: build run clean

help:
	@echo "make build         - Create .class objects"
	@echo "make clean         - Delete .class objects"

build: $(SOURCES:.scala=.class)

%.class: %.scala
	@echo "Compiling $*.scala..."
	@$(SC) -d . $*.scala

clean:
	@$(RM) $(SRC)/*.class

run: 
	$(S) Main
