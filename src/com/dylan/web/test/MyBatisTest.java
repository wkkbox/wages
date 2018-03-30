package com.dylan.web.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.util.BASE64Util;
import com.dylan.core.util.DateUtil;
import com.dylan.core.util.ExcelUtil;
import com.dylan.core.util.MailUtil;
import com.dylan.core.util.RandomNumUtil;
import com.dylan.core.util.StringUtil;
import com.dylan.web.system.service.ManagerService;
import com.dylan.web.wages.service.WagesHeaderService;
import com.dylan.web.wages.service.WagesHeaderValueService;
import com.dylan.web.wages.service.WagesParentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-shiro.xml" })
public class MyBatisTest {

	@Autowired
	private WagesParentService parentService;
	@Autowired
	private WagesHeaderService headerService;
	@Autowired
	private WagesHeaderValueService headerValueService;
	@Resource
	private ManagerService managerService;

	@Test
	public void test() throws Exception {
		// 1.插入父表
/*		FormMap formMap = new FormMap();
		FormMap formMap1 = new FormMap();
		FormMap formMap2 = new FormMap();
		FormMap formMap4 = new FormMap();
		formMap.put("wagesMonth", "1");
		formMap.put("wagesYear", "2018");

		formMap.put("managerName", "1");
		formMap.put("createBy", "1");
		formMap.put("createTime", DateUtil.getCurrentDate("yyyy-MM-dd"));
		// 2.读取excel 插入数据
		File file = new File("E://201304AB.xls");

		ArrayList<ArrayList<Object>> result = ExcelUtil.readExcel(file);
		ArrayList<String> lists = new ArrayList<String>();
		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < result.get(i).size(); j++) {
				if (i == 0) {
					lists.add(result.get(i).get(j).toString());
				} else if (i != 0) {
					FormMap formMap3 = new FormMap();
					boolean flag = false;					
						formMap3.put("idCardNO", result.get(i).get(1).toString());
						flag = managerService.selectManager(formMap3);
						if(flag){
							if (j <= 4) {
								String accountName = result.get(i).get(1).toString();
								String idCardNO = result.get(i).get(1).toString();
								String manaherName = result.get(i).get(3).toString();
								String mail = result.get(i).get(4).toString();
								if (flag) {
									formMap4.put("accountName", accountName);
									formMap4.put("idCardNO", idCardNO);
									formMap4.put("managerName", manaherName);
									formMap4.put("mail", mail);
									formMap4.put("password", "00b3187384f2708025074f28764a4a30");
									formMap4.put("salt", "salt");
								
									managerService.insert(formMap4);
								}
							}
						}else{
							formMap.set("managerName", result.get(i).get(1).toString());
							int n1 = parentService.insert(formMap);
							System.out.println("插入总表");
							if (n1 > 0) {
								for (int k2 = 0; k2 < lists.size(); k2++) {
									formMap1.put("parentId", formMap.getStr("id"));
									formMap1.put("header", lists.get(k2));
									formMap1.put("createBy", "1");
									formMap1.put("createTime", DateUtil.getCurrentDate("yyyy-MM-dd"));
									int n2 = headerService.insert(formMap1);
									if (n2 > 0) {
										String key = RandomNumUtil.getRandNum(8);
										formMap2.put("parentId", formMap.getStr("id"));
										formMap2.put("salt", key);
										formMap2.put("headerId", formMap1.getStr("id"));
										formMap2.put("createBy", "1");
										formMap2.put("headerValue", BASE64Util.encrypt(result.get(i).get(k2).toString(), key));
										formMap2.put("createTime", DateUtil.getCurrentDate("yyyy-MM-dd"));
										int n3 = headerValueService.insert(formMap2);
										if (n3 > 0) {
										} else {
										}
									}
								}
							}
							break;
						}

				}

			}
		}*/
		/*
		 * HashMap<Integer, Object> gaugeOutfitMaps = new HashMap<Integer,
		 * Object>();//表头数据 HashMap<Integer, Object> dataMaps = new
		 * HashMap<Integer, Object>(); for (int i = 0; i < result.size(); i++) {
		 * for (int j = 0; j < result.get(i).size(); j++) { if (i == 0) {
		 * gaugeOutfitMaps.put(j, result.get(i).get(j).toString()); } else if (i
		 * == 1) { dataMaps.put(j, result.get(i).get(j).toString()); } } }
		 * Iterator<Map.Entry<Integer, Object>> it =
		 * gaugeOutfitMaps.entrySet().iterator(); Iterator<Map.Entry<Integer,
		 * Object>> its = dataMaps.entrySet().iterator(); for (int i = 0; i <
		 * gaugeOutfitMaps.size(); i++) { Entry<Integer, Object>
		 * gaugeOutfitEntry = it.next(); Entry<Integer, Object> dataMapsEntry =
		 * its.next(); System.out.println("key= " + gaugeOutfitEntry.getKey() +
		 * " and value= " + gaugeOutfitEntry.getValue());
		 * System.out.println("key= " + dataMapsEntry.getKey() + " and value= "
		 * + dataMapsEntry.getValue()); }
		 */
		/*
		 * Iterator<Map.Entry<Integer, Object>> it =
		 * gaugeOutfitMaps.entrySet().iterator(); Iterator<Map.Entry<Integer,
		 * Object>> its = dataMaps.entrySet().iterator(); for (int i = 0; i <
		 * gaugeOutfitMaps.size(); i++) { FormMap form=new FormMap(); String
		 * randomNum=RandomNumUtil.getRandNum(8); Entry<Integer, Object>
		 * gaugeOutfitEntry = it.next(); Entry<Integer, Object> dataMapsEntry =
		 * its.next(); form.put("parentId", formMap.getStr("id"));
		 * form.put("header", gaugeOutfitEntry.getValue());
		 * form.put("headerValue",
		 * BASE64Util.encrypt(dataMapsEntry.getValue().toString(),randomNum));
		 * form.put("salt", randomNum); form.put("createBy", "1");
		 * form.put("createTime", DateUtil.getCurrentDate("yyyy-MM-dd"));
		 * //detailService.insert(form); System.out.println("key= " +
		 * gaugeOutfitEntry.getKey() + " and value= " +
		 * gaugeOutfitEntry.getValue()); System.out.println("key= " +
		 * dataMapsEntry.getKey() + " and value= " + dataMapsEntry.getValue());
		 * }
		 */

/*		List<E> list=managerService.selectAccountName(null);
		FormMap formMap = new FormMap(); 
		  formMap.put("wagesMonth", 1);
		  formMap.put("wagesYear", 2018); 
		  formMap.put("managerName", "1");
		for (int i = 0; i < list.size(); i++) {
			formMap.set("managerName", list.get(i).getStr("accountName"));
			E detailMail = parentService.selectWagesDetailMail(formMap);
			formMap.put("id", detailMail.getStr("parentId")); 
			List<E> selectWagesParent = parentService.selectWagesDetail(formMap);
			StringBuffer sb=new StringBuffer();
			for (int j = 0; j <selectWagesParent.size(); j++) {
				sb.append(selectWagesParent.get(j).getStr("header")+"   "
						  +selectWagesParent.get(j).getStr("hreaderValue")+"<br>	");
			}
			System.out.println(sb.toString());
			System.out.println(list.get(i).getStr("mail"));
			  MailUtil mailUtil=new MailUtil(); 
			  mailUtil.setReceiveMailNames(new String[]{list.get(i).getStr("manaherName")}); 
			  mailUtil.setReceiveMailAccounts(new String[]{list.get(i).getStr("mail")}); 
			  mailUtil.sendMail(sb.toString(), "0");
		}*/
/*		
		  
		  E detailMail = parentService.selectWagesDetailMail(formMap);
		  formMap.put("id", detailMail.getStr("parentId")); 
		  List<E> selectWagesParent = parentService.selectWagesDetail(formMap);
		  StringBuffer sb=new StringBuffer(); for (int i = 0; i <
		  selectWagesParent.size(); i++) {
		  sb.append(selectWagesParent.get(i).getStr("header")+"   "
		  +selectWagesParent.get(i).getStr("hreaderValue")+"<br>	");
		  
		  } System.out.println(sb.toString());
		  
		  
		  MailUtil mailUtil=new MailUtil(); mailUtil.setReceiveMailNames(new
		  String[]{"王清芳"}); mailUtil.setReceiveMailAccounts(new
		  String[]{"310238796@qq.com"}); mailUtil.sendMail(sb.toString(), "0");*/
		
	}

}
