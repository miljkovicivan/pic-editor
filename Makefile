SRC = .
SOURCES = $(shell ls $(SRC)/*.scala)
S = scala
SC = scalac

# default target is to show help
all: build run clean

help:
	@echo "make all           - build run clean"
	@echo "make build         - Create .class objects"
	@echo "make clean         - Delete .class objects"
	@echo "make run           - Run compiled objects"
	@echo "make help          - Print this message"

build: $(SOURCES:.scala=.class)

%.class: %.scala
	@echo "Compiling $*.scala..."
	@$(SC) -d . $*.scala

clean:
	@$(RM) $(SRC)/*.class

run: 
	$(S) Main
