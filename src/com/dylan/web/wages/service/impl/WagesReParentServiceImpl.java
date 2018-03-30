
package com.dylan.web.wages.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.mapper.BaseMapper;
import com.dylan.core.service.impl.AbstractService;
import com.dylan.core.util.BASE64Util;
import com.dylan.core.util.DateUtil;
import com.dylan.core.util.RandomNumUtil;
import com.dylan.core.util.StringUtil;
import com.dylan.web.log.mapper.LogMapper;
import com.dylan.web.wages.mapper.WagesReHeaderMapper;
import com.dylan.web.wages.mapper.WagesReHeaderValueMapper;
import com.dylan.web.wages.mapper.WagesReParentMapper;
import com.dylan.web.wages.mapper.WagesTotalMapper;
import com.dylan.web.wages.service.WagesReParentService;

@Service
@Transactional(readOnly = false)
public class WagesReParentServiceImpl extends AbstractService implements WagesReParentService {

    @Autowired
    private WagesReParentMapper wagesReParentMapper;

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private WagesReHeaderMapper reHeaderMapper;

    @Autowired
    private WagesReHeaderValueMapper reheaderValueMapper;

    @Autowired
    private WagesTotalMapper totalMapper;

    @Override
    public BaseMapper setMapper() {
        return wagesReParentMapper;
    }

    @Override
    public List<E> selectListByManagerId(FormMap formMap) {
        return null;
    }

    @Override
    public List<E> selectWagesParent(FormMap formMap) {
        if (StringUtil.isEmpty(formMap.getStr("month"))) {
            formMap.put("month", null);
        }
        if (StringUtil.isEmpty(formMap.getStr("year"))) {
            formMap.put("year", null);
        }
        List<E> list = wagesReParentMapper.selectWagesParent(formMap);
        try {
            for (int i = 0; i < list.size(); i++) {
                E e = list.get(i);
                if (!StringUtil.isEmpty(e)) {
                    e.set("headerValue", BASE64Util.decrypt(e.getStr("headerValue"), e.getStr("salt")));
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }

        return list;

    }

    @Override
    public List<E> selectWagesDetail(FormMap formMap) {
        List<E> list = wagesReParentMapper.selectWagesDetail(formMap);
        try {
            for (int i = 0; i < list.size(); i++) {
                E e = list.get(i);
                if (!StringUtil.isEmpty(e)) {
                    e.set("hreaderValue", BASE64Util.decrypt(e.getStr("hreaderValue"), e.getStr("salt")));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return list;
    }

    @Override
    public List<E> selectWagesDetailMail(FormMap formMap) {
        return wagesReParentMapper.selectWagesDetailMail(formMap);
    }

    @Override
    public int editState(FormMap formMap) {
        return wagesReParentMapper.editState(formMap);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void insert_logs_reparent_reheader_reheaderValue_total(FormMap logFormMap, FormMap parentFormMap,
                                                                  ArrayList<String> list, ArrayList<ArrayList<Object>> excelresult) throws Exception {
        //实发合计
        double sfTotal = 0;

        // 插入日志表
        logMapper.insert(logFormMap);
        // 下面的循环用于 1.插入到parent表(最基本的工资单的信息表) 2.插入到header表
        // 3.插入到headerValue表
        for (int i = 0; i < excelresult.size(); i++) {
            if (i >= 5 && !StringUtil.isEmpty(excelresult.get(i).get(1).toString())) {// 从第5行开始是每个人的工资细节，所以从5开始做逻辑且判断是否到达合计
                // 插入parent header headerValue表中
                parentFormMap.put("createTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
                //360xxxxxxxx55810.00,21060xxxxxx019,无,实习期限201xxxxxx28,362xxxxxx05002X
                String idCardNO = excelresult.get(i).get(5).toString().trim();// 身份证号码
                try {
                    Double.valueOf(idCardNO);
                    idCardNO = idCardNO.substring(0, idCardNO.lastIndexOf("."));
                } catch (Exception e) {
                    //accountName = accountName;
                }
                parentFormMap.put("managerName", idCardNO);// 身份证号码
                // 插入到reparent表(最基本的工资单的信息表),managerName,wagesYear,wagesMonth,createBy,createTime
                int n1 = wagesReParentMapper.insert(parentFormMap);
                if (n1 > 0) {
                    for (int k2 = 0; k2 < list.size(); k2++) {
                        // System.out.print(list.get(k2) + ":" +
                        // excelresult.get(i).get(k2) + " ");
                        FormMap headerFormMap = new FormMap();
                        headerFormMap.put("parentId", parentFormMap.getStr("id"));
                        headerFormMap.put("header", list.get(k2));
                        headerFormMap.put("createBy", parentFormMap.getStr("createBy"));// wqf写的是1，1就是对应的人力的主键id,getSession().getAttribute("managerId")
                        headerFormMap.put("createTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
                        // 插入到reheader表,parentId,header,createBy,createTime
                        int n2 = reHeaderMapper.insert(headerFormMap);
                        if (n2 > 0) {
                            FormMap headerValueFormMap = new FormMap();
                            headerValueFormMap.put("headerId", headerFormMap.getStr("id"));
                            headerValueFormMap.put("parentId", parentFormMap.getStr("id"));
                            String salt = RandomNumUtil.getRandNum(8);
                            headerValueFormMap.put("salt", salt);


                            if ("实发合计".equals(headerFormMap.getStr("header"))) {
                                sfTotal = sfTotal + Double.valueOf(excelresult.get(i).get(k2).toString());
                                //System.out.println(parentFormMap.getStr("wagesYear")+"年"+parentFormMap.getStr("wagesMonth")+"月"+"实发合计其中一个="+excelresult.get(i).get(k2));
                            }


                            headerValueFormMap.put("headerValue",
                                    BASE64Util.encrypt(excelresult.get(i).get(k2).toString(), salt));// BASE64加密
                            headerValueFormMap.put("createBy", parentFormMap.getStr("createBy"));// wqf写的是1，1就是对应的人力的主键id,getSession().getAttribute("managerId")
                            headerValueFormMap.put("createTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
                            // 插入到reheaderValue表,headerId,parentId,salt,headerValue,createBy,createTime
                            reheaderValueMapper.insert(headerValueFormMap);
                        }
                    }
                }
            }
        }

        //System.out.println(parentFormMap.getStr("wagesYear")+"年"+parentFormMap.getStr("wagesMonth")+"月"+"实发合计="+sfTotal);
        //插入补发工资单合计表
        String totalSalt = RandomNumUtil.getRandNum(8);
        FormMap totalFormMap = new FormMap();
        totalFormMap.put("sfTotal", BASE64Util.encrypt(String.valueOf(sfTotal), totalSalt));//实发合计
        totalFormMap.put("salt", totalSalt);
        totalFormMap.put("total_year", parentFormMap.getStr("wagesYear"));//合计年份
        totalFormMap.put("total_month", parentFormMap.getStr("wagesMonth"));//合计月份
        totalMapper.insertReTotal(totalFormMap);
    }
}
