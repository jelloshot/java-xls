/* Copyright (C) 2013 SheetJS */
/* vim: set ts=2: */
package com.sheetjs;

import java.lang.Integer;
import java.io.RandomAccessFile;
import java.io.IOException;
import org.mozilla.javascript.*;

public class JSHelper {
	static double[] read_file(String file) throws IOException {
		RandomAccessFile f = new RandomAccessFile(file,"r");
		byte[] b = new byte[(int)f.length()];
		double[] d = new double[(int)f.length()];
		f.read(b);
		for(int i = 0; i != (int)f.length(); ++i) d[i] = b[i]>=0 ? b[i] : b[i]+256;
		return d;
	}

	static Object get_object(String path, Object base) throws ObjectNotFoundException {
		int idx = path.indexOf(".");
		Scriptable b = (Scriptable)base;
		if(idx == -1) return b.get(path, b);
		Object o = b.get(path.substring(0,idx), b);
		if(o == Scriptable.NOT_FOUND) throw new ObjectNotFoundException("not found: |" + path.substring(0,idx) + "|" + Integer.toString(idx));
		return get_object(path.substring(idx+1), (NativeObject)o);
	}

	static Object[] get_array(String path, Object base) throws ObjectNotFoundException {
		NativeArray arr = (NativeArray)get_object(path, base);
		Object[] out = new Object[(int)arr.getLength()];
		int idx;
		for(Object o :arr.getIds()) out[idx = (Integer)o] = arr.get(idx, arr); 
		return out;
	}

	static String[] get_string_array(String path, Object base) throws ObjectNotFoundException {
		NativeArray arr = (NativeArray)get_object(path, base);
		String[] out = new String[(int)arr.getLength()];
		int idx;
		for(Object o :arr.getIds()) out[idx = (Integer)o] = arr.get(idx, arr).toString(); 
		return out;
	}
	
	public static void close() { Context.exit(); }

}
