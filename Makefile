GUROBI_JAR = /opt/gurobi1301/linux64/lib/gurobi.jar
GUROBI_LIB = /opt/gurobi1301/linux64/lib
PROTOBUF_JAR = ./lib/protobuf-java-4.34.1.jar

SRC_DIR = source/
BIN_DIR = bytecode/
BUILD_DIR = build/

MANIFEST = $(BIN_DIR)manifest.txt
JAR = $(BIN_DIR)server.jar
MAIN_CLASS = projects.multipath.Server.Server

# Find all java files in source
SOURCES := $(shell find $(SRC_DIR) -name "*.java")

all: compile

compile:
	@mkdir -p $(BIN_DIR) $(BUILD_DIR)
	javac -cp "$(GUROBI_JAR):$(PROTOBUF_JAR)" \
		-d $(BUILD_DIR) \
		-sourcepath $(SRC_DIR) \
		$(SOURCES)
	@echo "Main-Class: $(MAIN_CLASS)" > $(MANIFEST)
	@echo "Class-Path: $(GUROBI_JAR) $(PROTOBUF_JAR)" >> $(MANIFEST)
	@echo "" >> $(MANIFEST)
	jar -cfm $(JAR) $(MANIFEST) -C $(BUILD_DIR) .
	@echo "Compilation complete: $(JAR)"

run: compile
	java -jar $(JAR) "Arg 1" "Arg 2, Hello World!" 3

clean:
	rm -rf $(BIN_DIR) $(BUILD_DIR) 
