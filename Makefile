# Copyright (C) 2013 SheetJS
.PHONY: run
run: Run.class
	java -cp .:XLSJS.jar:rhino.jar Run formula_stress_test.xls Text

DEPS=$(filter-out Run.class,$(patsubst %.java,%.class,$(wildcard com/sheetjs/*.java)))
$(DEPS): %.class: %.java rhino.jar
	javac -cp .:XLSJS.jar:rhino.jar $*.java

.PHONY: jar
jar: XLSJS.jar

XLSJS.jar: $(DEPS)
	jar -cf $@ $^

Run.class: Run.java XLSJS.jar
	javac -cp .:rhino.jar Run.java

rhino.jar: rhino
	if [ ! -e rhino/build/rhino*/js.jar ]; then cd rhino; ant jar; fi
	cp rhino/build/rhino*/js.jar rhino.jar

rhino:
	git clone https://github.com/mozilla/rhino

.PHONY: clean
clean:
	rm -f *.class com/sheetjs/*.class XLSJS.jar rhino.jar
	rm -rf rhino
