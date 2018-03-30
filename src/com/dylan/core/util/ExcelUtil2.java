package com.dylan.core.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil2 {
	// 默认单元格内容为数字时格式
	private static DecimalFormat df = new DecimalFormat("0");
	// 默认单元格格式化日期字符串
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 格式化数字
	private static DecimalFormat nf = new DecimalFormat("0.00");

	public static ArrayList<ArrayList<Object>> readExcel(File file) {
		if (file == null) {
			return null;
		}
		if (file.getName().endsWith("xlsx")) {
			// 处理ecxel2007
			return readExcel2007(file);
		} else {
			// 处理ecxel2003
			return readExcel2003(file);
		}
	}

	/*
	 * @return 将返回结果存储在ArrayList内，存储结构与二位数组类似
	 * lists.get(0).get(0)表示过去Excel中0行0列单元格
	 */
	public static ArrayList<ArrayList<Object>> readExcel2003(File file) {
		try {
			ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
			ArrayList<Object> colList;
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			Object value;
			for (int i = sheet.getFirstRowNum(), rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				colList = new ArrayList<Object>();
				if (row == null) {
					// 当读取行为空时
					if (i != sheet.getPhysicalNumberOfRows()) {// 判断是否是最后一行
						rowList.add(colList);
					}
					continue;
				} else {
					rowCount++;
				}
				for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
					cell = row.getCell(j);
					if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
						// 当该单元格为空
						if (j != row.getLastCellNum()) {// 判断是否是该行中最后一个单元格
							colList.add("");
						}
						continue;
					}
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case XSSFCell.CELL_TYPE_FORMULA:
						// try {
						switch (cell.getCachedFormulaResultType()) {
						case 0:
							// if (0 == cell.getCellStyle().getDataFormat()) {
							// DecimalFormat df = new DecimalFormat("0");
							// value = df.format(cell.getNumericCellValue());
							// } else {
							value = String.valueOf(cell.getNumericCellValue());
							value=nf.format(Double.parseDouble((String) value));
							// }
							break;
						case 1:
							value = String.valueOf(cell.getRichStringCellValue());
							value=nf.format(Double.parseDouble((String) value));
							break;
						case 4:
							value = String.valueOf(cell.getBooleanCellValue());
							break;
						case 5:
							value = String.valueOf(cell.getErrorCellValue());
							break;
						default:
							value = cell.getCellFormula();
						}
						// value = String.valueOf(cell.getNumericCellValue());
						/*
						 * } catch (IllegalStateException e) { value =
						 * String.valueOf(cell.getRichStringCellValue()); }
						 */
						// System.out.println("value="+value);
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						if ("@".equals(cell.getCellStyle().getDataFormatString())) {
							value = df.format(cell.getNumericCellValue());
						} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
							value = nf.format(cell.getNumericCellValue());
						} else {
							value = df.format(cell.getNumericCellValue());
							// value =
							// sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
						}
						break;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						value = Boolean.valueOf(cell.getBooleanCellValue());
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						value = "";
						break;
					default:
						value = cell.toString();
					}// end switch
					colList.add(value);
				} // end for j
				rowList.add(colList);
			} // end for i

			return rowList;
		} catch (Exception e) {
			return null;
		}
	}

	public static ArrayList<ArrayList<Object>> readExcel2007(File file) {
		try {
			ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
			ArrayList<Object> colList;
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row;
			XSSFCell cell;
			Object value;
			for (int i = sheet.getFirstRowNum(), rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				colList = new ArrayList<Object>();
				if (row == null) {
					// 当读取行为空时
					if (i != sheet.getPhysicalNumberOfRows()) {// 判断是否是最后一行
						rowList.add(colList);
					}
					continue;
				} else {
					rowCount++;
				}
				for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
					cell = row.getCell(j);
					if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
						// 当该单元格为空
						if (j != row.getLastCellNum()) {// 判断是否是该行中最后一个单元格
							colList.add("");
						}
						continue;
					}
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						if ("@".equals(cell.getCellStyle().getDataFormatString())) {
							value = df.format(cell.getNumericCellValue());
						} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
							value = nf.format(cell.getNumericCellValue());
						} else {
							value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
						}
						break;
					case XSSFCell.CELL_TYPE_FORMULA:
						// try {
						switch (cell.getCachedFormulaResultType()) {
						case 0:
							// if (0 == cell.getCellStyle().getDataFormat()) {
							// DecimalFormat df = new DecimalFormat("0");
							// value = df.format(cell.getNumericCellValue());
							// } else {
							value = String.valueOf(cell.getNumericCellValue());
							value=nf.format(Double.parseDouble((String) value));
							// }
							break;
						case 1:
							value = String.valueOf(cell.getRichStringCellValue());
							value=nf.format(Double.parseDouble((String) value));
							break;
						case 4:
							value = String.valueOf(cell.getBooleanCellValue());
							break;
						case 5:
							value = String.valueOf(cell.getErrorCellValue());
							break;
						default:
							value = cell.getCellFormula();
						}
						// value = String.valueOf(cell.getNumericCellValue());
						/*
						 * } catch (IllegalStateException e) { value =
						 * String.valueOf(cell.getRichStringCellValue()); }
						 */
						// System.out.println("value="+value);
						break;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						System.err.println("boo");

						value = Boolean.valueOf(cell.getBooleanCellValue());
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						value = "";
						break;
					default:
						value = cell.toString();
					}// end switch
					colList.add(value);
				} // end for j
				rowList.add(colList);
			} // end for i

			return rowList;
		} catch (Exception e) {
			System.out.println("exception");
			return null;
		}
	}

	public static void writeExcel(ArrayList<ArrayList<Object>> result, String path) {
		if (result == null) {
			return;
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		for (int i = 0; i < result.size(); i++) {
			HSSFRow row = sheet.createRow(i);
			if (result.get(i) != null) {
				for (int j = 0; j < result.get(i).size(); j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellValue(result.get(i).get(j).toString());
				}
			}
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		File file = new File(path);// Excel文件生成后存储的位置。
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(content);
			os.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DecimalFormat getDf() {
		return df;
	}

	public static void setDf(DecimalFormat df) {
		ExcelUtil2.df = df;
	}

	public static SimpleDateFormat getSdf() {
		return sdf;
	}

	public static void setSdf(SimpleDateFormat sdf) {
		ExcelUtil2.sdf = sdf;
	}

	public static DecimalFormat getNf() {
		return nf;
	}

	public static void setNf(DecimalFormat nf) {
		ExcelUtil2.nf = nf;
	}

	public static ArrayList<String> getReTableHeader(ArrayList<ArrayList<Object>> result) {
		ArrayList<String> list = new ArrayList<String>();
		HashMap<Integer, Object> gaugeOutfitMaps = new HashMap<Integer, Object>();
		// 解析出所有表头
		for (int i = 0; i < result.size(); i++) {
			if (i == 1 || i == 2) {
				for (int j = 0; j < result.get(i).size(); j++) {
					if (!StringUtil.isEmpty(result.get(i).get(j).toString())) {
						gaugeOutfitMaps.put(j, result.get(i).get(j).toString());
					}
				}
			}
		}
		//System.out.println("所有表头：" + gaugeOutfitMaps);
		for (int j2 = 0; j2 < gaugeOutfitMaps.size(); j2++) {
			list.add(gaugeOutfitMaps.get(j2).toString());
		}
		//System.out.println(list);
		return list;
		//得到表头对应的值
		/*for (int i = 0; i < result.size(); i++) {
			if (i >= 5 && !StringUtil.isEmpty(result.get(i).get(1).toString())) {// 从第5行开始是每个人的工资细节，所以从5开始做逻辑且判断是否到达合计
				for (int k2 = 0; k2 < lists.size(); k2++) {
					System.out.print(lists.get(k2) + ":" + result.get(i).get(k2) + " ");
				}
				System.out.println();
			}
		}*/
	}

	public static void main(String[] args) {
		System.out.println("开始运行");
		// File file = new File("E://1//2018年1月工资表.xlsx");
		File file = new File("E://1//20180201//补发九江所、南昌所1月绩效.xls");
		ArrayList<ArrayList<Object>> result = ExcelUtil2.readExcel(file);
		/*
		 * System.out.println(new DecimalFormat("0.00").format(973.66));
		 * System.out.println(new DecimalFormat("0").format(1.00));
		 * System.out.println(new DecimalFormat("0").format(500.00));
		 * System.out.println(new DecimalFormat("0.0").format(500.00));
		 * System.out.println(new DecimalFormat("0").format(485.0));
		 * System.out.println(new
		 * DecimalFormat("0.00").format(23232.130906004));
		 */
		System.out.println(ExcelUtil2.getReTableHeader(result));
		/*
		 * System.out.println(result.get(26).get(9));
		 * System.out.println(result.get(28).get(5));
		 * System.out.println(result.get(29).get(7));
		 * System.out.println(result.get(127).get(30));
		 */
		
		 for (int i = 0; i < result.size(); i++) { System.out.println(i + " "
		 + result.get(i)); }
		 

		/*
		 * for (int i = 0; i < result.size(); i++) { System.out.println(i + "  "
		 * + result.get(i).get(5)); }
		 */

		/*
		 * HashMap<Integer, Object> gaugeOutfitMaps = new HashMap<Integer,
		 * Object>();
		 */
		System.out.println(1 / 0);

		String personalWithholding = "企业、个人缴纳的扣款项";
		String socialBase = "社保基数调整补扣项";
		String yl = "养老保险";
		String yil = "医疗保险";
		String sy = "失业保险";
		String gs = "工伤保险";
		String shy = "生育保险";
		String db = "大病保险";
		String gjj = "公积金";
		String qy = "企业年金";
		int a = 0;
		int b = 0;
		ArrayList<String> lists = new ArrayList<String>();
		HashMap<Integer, Object> gaugeOutfitMaps = new HashMap<Integer, Object>();
		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < result.get(i).size(); j++) {
				if (i == 1) {
					if (!StringUtil.isEmpty(result.get(i).get(j).toString())) {
						if (!"员工信息".equals(result.get(i).get(j).toString())
								&& !"月固定工资项".equals(result.get(i).get(j).toString())
								&& !"变化工资项".equals(result.get(i).get(j).toString())) {
							if (personalWithholding.equals(result.get(i).get(j).toString())) {
								a = j;
							} else if (socialBase.equals(result.get(i).get(j).toString())) {
								b = j;
							}
							System.out
									.println("i:" + i + "key= " + j + " and value= " + result.get(i).get(j).toString());
							gaugeOutfitMaps.put(j, result.get(i).get(j).toString());
						}
					}
				} else if (i == 2) {

					if (j >= a && j < b) {
						if (StringUtil.isEmpty(result.get(i).get(j).toString())) {
							System.out.println("i:" + i + "key= " + j + " and value= " + personalWithholding
									+ result.get(i).get(j - 1).toString() + "个人");
							gaugeOutfitMaps.put(j, personalWithholding + result.get(i).get(j - 1).toString() + "个人");
						} else {
							if (yl.equals(result.get(i).get(j).toString())
									|| yil.equals(result.get(i).get(j).toString())
									|| sy.equals(result.get(i).get(j).toString())
									|| gs.equals(result.get(i).get(j).toString())
									|| shy.equals(result.get(i).get(j).toString())
									|| db.equals(result.get(i).get(j).toString())
									|| gjj.equals(result.get(i).get(j).toString())
									|| qy.equals(result.get(i).get(j).toString())) {
								System.out.println("i:" + i + "key= " + j + " and value= " + personalWithholding
										+ result.get(i).get(j).toString() + "企业");
								gaugeOutfitMaps.put(j, personalWithholding + result.get(i).get(j).toString() + "企业");
							} else {
								System.out.println("i:" + i + "key= " + j + " and value= " + personalWithholding
										+ result.get(i).get(j).toString());
								gaugeOutfitMaps.put(j, personalWithholding + result.get(i).get(j).toString());
							}

						}
					} else if (j >= b) {
						if (StringUtil.isEmpty(result.get(i).get(j).toString())) {
							if (!StringUtil.isEmpty(result.get(i).get(j - 1).toString())) {
								System.out.println("i:" + i + "key= " + j + " and value= " + socialBase
										+ result.get(i).get(j - 1).toString() + "个人");
								gaugeOutfitMaps.put(j, socialBase + result.get(i).get(j - 1).toString() + "个人");
							}

						} else {
							if (yl.equals(result.get(i).get(j).toString())
									|| yil.equals(result.get(i).get(j).toString())
									|| sy.equals(result.get(i).get(j).toString())
									|| gs.equals(result.get(i).get(j).toString())
									|| shy.equals(result.get(i).get(j).toString())
									|| db.equals(result.get(i).get(j).toString())
									|| gjj.equals(result.get(i).get(j).toString())
									|| qy.equals(result.get(i).get(j).toString())) {
								System.out.println("i:" + i + "key= " + j + " and value= " + socialBase
										+ result.get(i).get(j).toString() + "企业");
								gaugeOutfitMaps.put(j, socialBase + result.get(i).get(j).toString() + "企业");
							} else {
								System.out.println("i:" + i + "key= " + j + " and value= " + socialBase
										+ result.get(i).get(j).toString());
								gaugeOutfitMaps.put(j, socialBase + result.get(i).get(j).toString());
							}

						}
					} else if (j < a) {
						if (!StringUtil.isEmpty(result.get(i).get(j).toString())) {
							System.out
									.println("i:" + i + "key= " + j + " and value= " + result.get(i).get(j).toString());
							gaugeOutfitMaps.put(j, result.get(i).get(j).toString());

						}
					}
				}
			}
		}
		for (int k = 0; k < gaugeOutfitMaps.size(); k++) {
			lists.add(gaugeOutfitMaps.get(k).toString());
		}
		System.out.println(lists);

	}

}