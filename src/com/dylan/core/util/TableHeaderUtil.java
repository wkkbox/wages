
package com.dylan.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TableHeaderUtil {

	// 得到补发工资表表的表头集合
	public static ArrayList<String> getReTableHeader(ArrayList<ArrayList<Object>> excelresult) {
		int changeWagesBegin = -1;
		int changeWagesEnd = -1;
		ArrayList<String> list = new ArrayList<String>();// 用于存表头的字段的名，例如序号，姓名，部门这样
		HashMap<Integer, Object> gaugeOutfitMaps = new HashMap<Integer, Object>();
		// 解析出所有表头
		for (int i = 0; i < excelresult.size(); i++) {
			if (i == 0) {
				continue;
			} else if (i == 1 || i == 2) {
				for (int j = 0; j < excelresult.get(i).size(); j++) {
					if ("变化工资项".equals(excelresult.get(i).get(j).toString())) {
						changeWagesBegin = j;
					}
					if ("应发合计".equals(excelresult.get(i).get(j).toString())) {
						changeWagesEnd = j - 1;
					}
					if (!StringUtil.isEmpty(excelresult.get(i).get(j).toString())) {
						gaugeOutfitMaps.put(j, excelresult.get(i).get(j).toString());
					} else {
						if (j >= changeWagesBegin && j <= changeWagesEnd) {
							gaugeOutfitMaps.put(j, "无");
						}
					}
				}
			} else {
				break;
			}
		}
		//System.out.println("changeWagesBegin" + changeWagesBegin);
		//System.out.println("changeWagesEnd" + changeWagesEnd);
		//System.out.println(gaugeOutfitMaps.size());
		//System.out.println("所有表头：" + gaugeOutfitMaps);
		//System.out.println(gaugeOutfitMaps.get(0));
		for (int k = 0; k < gaugeOutfitMaps.size(); k++) {
			if (gaugeOutfitMaps.get(k) == null) {
				list.add("无");
			} else {
				list.add(gaugeOutfitMaps.get(k).toString());
			}

		}
		// System.out.println(list);
		return list;
		// 得到表头对应的值
		/*
		 * for (int i = 0; i < result.size(); i++) { if (i >= 5 &&
		 * !StringUtil.isEmpty(result.get(i).get(1).toString())) {//
		 * 从第5行开始是每个人的工资细节，所以从5开始做逻辑且判断是否到达合计 for (int k2 = 0; k2 <
		 * lists.size(); k2++) { System.out.print(lists.get(k2) + ":" +
		 * result.get(i).get(k2) + " "); } System.out.println(); } }
		 */
	}

	// 得到主工资表的表头集合
	public static ArrayList<String> getMainTableHeader(ArrayList<ArrayList<Object>> excelresult) {
		String personalWithholding = "企业、个人缴纳的扣款项";
		String socialBase = "社保基数调整补扣项";
		String yfhj = "应发合计";
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
		int c = 0;
		int d = 0;
		ArrayList<String> list = new ArrayList<String>();// 用于存表头的字段的名，例如序号，姓名，部门这样
		HashMap<Integer, Object> gaugeOutfitMaps = new HashMap<Integer, Object>();
		// 循环用于往map：gaugeOutfitMaps里面存放表头的字段的名
		for (int i = 0; i < excelresult.size(); i++) {// 行
			for (int j = 0; j < excelresult.get(i).size(); j++) {// 列
				// System.out.println(excelresult.get(i).size());//65
				if (i == 1) {
					if (!StringUtil.isEmpty(excelresult.get(i).get(j).toString())) {
						if (!"员工信息".equals(excelresult.get(i).get(j).toString())
								&& !"月固定工资项".equals(excelresult.get(i).get(j).toString())) {
							if (personalWithholding.equals(excelresult.get(i).get(j).toString())) {
								a = j;
							} else if (socialBase.equals(excelresult.get(i).get(j).toString())) {
								b = j;
							} else if ("变化工资项".equals(excelresult.get(i).get(j).toString())) {
								c = j;
							} else if (yfhj.equals(excelresult.get(i).get(j).toString())) {
								d = j;
							}

							gaugeOutfitMaps.put(j, excelresult.get(i).get(j).toString());
						}
					}
				} else if (i == 2) {
					if (j >= c && j < d) {
						if (StringUtil.isEmpty(excelresult.get(i).get(j).toString())) {
							gaugeOutfitMaps.put(j, "无");
						} else {
							gaugeOutfitMaps.put(j, excelresult.get(i).get(j).toString());
						}
					} else if (j >= a && j < b) {
						if (StringUtil.isEmpty(excelresult.get(i).get(j).toString())) {
							gaugeOutfitMaps.put(j, excelresult.get(i).get(j - 1).toString() + "个人");
						} else {
							if (yl.equals(excelresult.get(i).get(j).toString())
									|| yil.equals(excelresult.get(i).get(j).toString())
									|| sy.equals(excelresult.get(i).get(j).toString())
									|| gs.equals(excelresult.get(i).get(j).toString())
									|| shy.equals(excelresult.get(i).get(j).toString())
									|| db.equals(excelresult.get(i).get(j).toString())
									|| gjj.equals(excelresult.get(i).get(j).toString())) {

								gaugeOutfitMaps.put(j, excelresult.get(i).get(j).toString() + "企业");
							} else if (qy.equals(excelresult.get(i).get(j).toString())) {
								gaugeOutfitMaps.put(j, excelresult.get(i).get(j).toString() + "个人");
							} else {

								gaugeOutfitMaps.put(j, excelresult.get(i).get(j).toString());
							}

						}
					} else if (j >= b) {
						if (StringUtil.isEmpty(excelresult.get(i).get(j).toString())) {
							if (!StringUtil.isEmpty(excelresult.get(i).get(j - 1).toString())) {

								gaugeOutfitMaps.put(j, excelresult.get(i).get(j - 1).toString() + "个人");
							}

						} else {
							if (yl.equals(excelresult.get(i).get(j).toString())
									|| yil.equals(excelresult.get(i).get(j).toString())
									|| sy.equals(excelresult.get(i).get(j).toString())
									|| gs.equals(excelresult.get(i).get(j).toString())
									|| shy.equals(excelresult.get(i).get(j).toString())
									|| db.equals(excelresult.get(i).get(j).toString())
									|| gjj.equals(excelresult.get(i).get(j).toString())
									|| qy.equals(excelresult.get(i).get(j).toString())) {

								gaugeOutfitMaps.put(j, excelresult.get(i).get(j).toString() + "企业");
							} else {
								gaugeOutfitMaps.put(j, excelresult.get(i).get(j).toString());
							}
						}
					} else if (j < c) {
						if (!StringUtil.isEmpty(excelresult.get(i).get(j).toString())) {
							gaugeOutfitMaps.put(j, excelresult.get(i).get(j).toString());
						}
					}
				} else if (i == 4) {

				}
			}
		}
		// 循环用于把map里的表头的名放到list中
		for (int k = 0; k < gaugeOutfitMaps.size(); k++) {
			// System.out.println("表头的名有" + gaugeOutfitMaps.size() + "个");
			// 把map里的表头的名(65个)放到list中
			list.add(gaugeOutfitMaps.get(k).toString());
		}
		return list;
	}

	public static void main(String[] args) {

		/*
		 * File file = new File("E://1//补发九江所、南昌所1月绩效.xls");
		 * ArrayList<ArrayList<Object>> result = ExcelUtil2.readExcel(file);
		 * System.out.println(TableHeaderUtil.getReTableHeader(result));
		 * 
		 * File file2 = new File("E://1//2018年1月工资表.xlsx");
		 * ArrayList<ArrayList<Object>> result2 = ExcelUtil2.readExcel(file2);
		 * System.out.println(TableHeaderUtil.getMainTableHeader(result2));
		 */

		File file = new File("C://Users//Administrator//Desktop//工资系统//补发工资表表样.xls");
		System.out.println(ExcelUtil.readExcel(file));
		System.out.println(TableHeaderUtil.getReTableHeader(ExcelUtil.readExcel(file)));
	}

}
