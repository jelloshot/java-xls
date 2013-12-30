/* Copyright (C) 2013 SheetJS */
/* vim: set ts=2: */
package com.sheetjs;

import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Function;

public class XLSFile {
	public NativeObject wb;
	public XLSJS xlsjs;
	public XLSFile() {}
	public XLSFile(NativeObject wb, XLSJS xlsjs) { this.wb = wb; this.xlsjs = xlsjs; }
	public String[] get_sheet_names() {
		try { return JSHelper.get_string_array("SheetNames", this.wb); } 
		catch(ObjectNotFoundException e) { return null; }
	}
	public XLSSheet get_sheet(int idx) throws ObjectNotFoundException {
		return new XLSSheet(this, idx);
	}
}
