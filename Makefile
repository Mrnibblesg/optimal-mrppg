GUROBI_JAR = /opt/gurobi1301/linux64/lib/gurobi.jar
GUROBI_LIB = /opt/gurobi1301/linux64/lib
SRC_DIR = source
BIN_DIR = bin

# Find all java files in source
SOURCES := $(shell find $(SRC_DIR) -name "*.java")

all: compile

compile:
	@mkdir -p $(BIN_DIR)
	javac -cp "$(GUROBI_JAR)" -d $(BIN_DIR) -sourcepath $(SRC_DIR) $(SOURCES)
	@echo "Compilation complete. Files are in $(BIN_DIR)"

run:
	java -cp "$(BIN_DIR):$(GUROBI_JAR)" \
		 -Djava.library.path=$(GUROBI_LIB) \
		 projects.multipath.Server.Server

clean:
	rm -rf $(BIN_DIR)
