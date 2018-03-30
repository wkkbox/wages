package com.dylan.core.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.io.*;

public class Test {

	/**
	 * @param args
	 */
	public void fileInput() throws IOException {

		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream("E://ceshi.xlsx"));
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow hrow = sheet.getRow(8);
		XSSFCell hcell = hrow.getCell(6);
		String cellValue = this.getCellValue(hcell);
		System.out.println(cellValue);

	}

	public String getCellValue(XSSFCell hcell) {
		String value = null;
		if (hcell != null) {
			switch (hcell.getCellType()) {
			case HSSFCell.CELL_TYPE_FORMULA:
				// cell.getCellFormula();
				try {
					value = String.valueOf(hcell.getNumericCellValue());
				} catch (IllegalStateException e) {
					value = String.valueOf(hcell.getRichStringCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				value = String.valueOf(hcell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_STRING:
				value = String.valueOf(hcell.getRichStringCellValue());
				break;
			}
		}

		return value;
	}

	public static void main(String[] args) {
		try {
			// TODO Auto-generated method stub
			Test fts = new Test();
			fts.fileInput();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
