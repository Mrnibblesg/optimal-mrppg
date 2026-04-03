GUROBI_JAR = /opt/gurobi1301/linux64/lib/gurobi.jar
GUROBI_LIB = /opt/gurobi1301/linux64/lib

PROTO_DEF = multiagent-pathfinding-protobuf/problem.proto
PROTOBUF_JAR = ./lib/protobuf-java-4.34.1.jar
PROTOBUF_DST = source/projects/multipath/protos

SRC_DIR = source/
BIN_DIR = bytecode/
BUILD_DIR = build/

MANIFEST = $(BIN_DIR)manifest.txt
JAR = $(BIN_DIR)server.jar
MAIN_CLASS = projects.multipath.Server.Server



# Find all java files in source
SOURCES := $(shell find $(SRC_DIR) -name "*.java")

all: $(JAR)

$(JAR): $(SOURCES) $(PROTO_DEF)
	@mkdir -p $(BIN_DIR) $(BUILD_DIR)
	protoc --java_out=$(SRC_DIR) $(PROTO_DEF)
	javac -cp "$(GUROBI_JAR):$(PROTOBUF_JAR)" \
		-d $(BUILD_DIR) \
		-sourcepath $(SRC_DIR) \
		$(SOURCES)
	@echo "Main-Class: $(MAIN_CLASS)" > $(MANIFEST)
	@echo "Class-Path: $(GUROBI_JAR) $(abspath $(PROTOBUF_JAR))" >> $(MANIFEST)
	@echo "" >> $(MANIFEST)
	jar -cfm $(JAR) $(MANIFEST) -C $(BUILD_DIR) .
	@echo "Compilation complete: $(JAR)"

run: $(JAR)
	java -jar $(JAR) 1 "Arg 2, Hello World!" 3

clean:
	rm -rf $(BIN_DIR) $(BUILD_DIR) $(PROTOBUF_DST) bin
