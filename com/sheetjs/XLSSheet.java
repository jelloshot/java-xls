/* Copyright (C) 2013 SheetJS */
/* vim: set ts=2: */
package com.sheetjs;

import org.mozilla.javascript.*;

public class XLSSheet {
	public NativeObject ws;
	public XLSFile wb;
	public XLSSheet(XLSFile wb, int idx) throws ObjectNotFoundException {
		this.wb = wb;
		this.ws = (NativeObject)JSHelper.get_object("Sheets." + wb.get_sheet_names()[idx],wb.wb);
	}
	public String get_range() throws ObjectNotFoundException {
		return JSHelper.get_object("!ref",this.ws).toString();
	}
	public String get_string_value(String address) throws ObjectNotFoundException {
		return JSHelper.get_object(address + ".v",this.ws).toString();
	}

	public String get_csv() throws ObjectNotFoundException {
		Function csvify = (Function)JSHelper.get_object("XLS.utils.sheet_to_csv",this.wb.xlsjs.scope);
		Object csvArgs[] = {this.ws};
		Object csv = csvify.call(this.wb.xlsjs.cx, this.wb.xlsjs.scope, this.wb.xlsjs.scope, csvArgs);
		return csv.toString();
	}
}
