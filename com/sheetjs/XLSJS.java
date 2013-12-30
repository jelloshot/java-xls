/* Copyright (C) 2013 SheetJS */
/* vim: set ts=2: */
package com.sheetjs;

import java.lang.Integer;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import org.mozilla.javascript.*;

public class XLSJS {
	public Scriptable scope;
	public Context cx;
	public NativeObject nXLS;

	public XLSJS() throws Exception {
		this.cx = Context.enter();
		this.scope = this.cx.initStandardObjects();
			
		/* run xls.js, get XLS object */
		String s = new Scanner(new File("xls.js")).useDelimiter("\\Z").next();
		cx.evaluateString(scope, s, "<cmd>", 1, null);
		Object XLS = scope.get("XLS", scope);
		if(XLS == Scriptable.NOT_FOUND) throw new Exception("XLS not found");
		this.nXLS = (NativeObject)XLS;
	}

	public XLSFile read_file(String filename) throws IOException, ObjectNotFoundException {
		/* open file */
		double[] d = JSHelper.read_file(filename);

		/* options argument */
		NativeObject q = (NativeObject)this.cx.evaluateString(this.scope, "q = {'type':'array'};", "<cmd>", 2, null);

		/* set up function arguments */
		Object functionArgs[] = {d, q};

		/* call read -> wb workbook */
		Function readfunc = (Function)JSHelper.get_object("XLS.read",this.scope);
		NativeObject wb = (NativeObject)readfunc.call(this.cx, this.scope, this.nXLS, functionArgs);

		return new XLSFile(wb, this);
	}
}
