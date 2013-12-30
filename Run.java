/* Copyright (C) 2013 SheetJS */
/* vim: set ts=2: */
import com.sheetjs.*;

public class Run {
	public static void main(String args[]) throws Exception {
		try {
			XLSJS xlsjs = new XLSJS();

			/* open file */
			XLSFile xls = xlsjs.read_file(args[0]);

			/* get sheetnames */
			String[] sheetnames = xls.get_sheet_names();
			System.err.println(sheetnames[0]);

			/* csvify */
			XLSSheet sheet = xls.get_sheet(0);
			System.out.println(sheet.get_csv());
		} catch(Exception e) { throw e; } finally {
			JSHelper.close();
		}
	}	
}
