package com.dylan.web.wages.service.impl;

import java.io.IOException;
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
import com.dylan.web.system.mapper.ManagerMapper;
import com.dylan.web.system.service.ManagerService;
import com.dylan.web.wages.mapper.WagesHeaderMapper;
import com.dylan.web.wages.mapper.WagesHeaderValueMapper;
import com.dylan.web.wages.mapper.WagesParentMapper;
import com.dylan.web.wages.mapper.WagesTotalMapper;
import com.dylan.web.wages.service.WagesParentService;

@Service
@Transactional(readOnly = false)
public class WagesParentServiceImpl extends AbstractService implements WagesParentService {
    @Autowired
    private WagesParentMapper wagesParentMapper;

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private WagesParentMapper parentMapper;

    @Autowired
    private WagesHeaderMapper headerMapper;

    @Autowired
    private WagesHeaderValueMapper headerValueMapper;

    @Autowired
    private WagesTotalMapper totalMapper;

    @Override
    public BaseMapper setMapper() {
        return wagesParentMapper;
    }

    @Override
    public List<E> selectListByManagerId(FormMap formMap) {
        List<E> list = wagesParentMapper.selectListByManagerId(formMap);
        try {
            if (!StringUtil.isEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    E e = list.get(i);
                    if (!StringUtil.isEmpty(e)) {
                        e.set("header_value", BASE64Util.decrypt(e.getStr("header_value"), e.getStr("salt")));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<E> selectWagesParent(FormMap formMap) {
        if (StringUtil.isEmpty(formMap.getStr("month"))) {
            formMap.put("month", null);
        }
        if (StringUtil.isEmpty(formMap.getStr("year"))) {
            formMap.put("year", null);
        }
        List<E> list = wagesParentMapper.selectWagesParent(formMap);
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
        List<E> list = wagesParentMapper.selectWagesDetail(formMap);
        try {
            for (int i = 0; i < list.size(); i++) {
                E e = list.get(i);
                if (!StringUtil.isEmpty(e)) {
                    e.set("hreaderValue", BASE64Util.decrypt(e.getStr("hreaderValue"), e.getStr("salt")));
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }

        return list;
    }

    @Override
    public List<E> selectWagesDetailMail(FormMap formMap) {
        return wagesParentMapper.selectWagesDetailMail(formMap);
    }

    @Override
    public int editState(FormMap formMap) {
        return wagesParentMapper.editState(formMap);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void insert_logs_parent_header_headerValue_total(FormMap logFormMap, FormMap parentFormMap, ArrayList<String> list,
                                                            ArrayList<ArrayList<Object>> excelresult) throws Exception {
        //应发合计
        double yfTotal = 0;
        //实发合计
        double sfTotal = 0;

        // 插入日志表
        logMapper.insert(logFormMap);

        // 下面的循环用于 1.为员工注册 2.插入到parent表(最基本的工资单的信息表) 3.插入到header表
        // 4.插入到headerValue表
        for (int i = 0; i < excelresult.size(); i++) {// 表的行数
            // 从第5行开始是每个人的工资细节，所以从5开始做逻辑且判断是否到达合计，到达合计停止
            if (i >= 5 && !StringUtil.isEmpty(excelresult.get(i).get(1).toString())) {
                FormMap selectManagerFormMap = new FormMap();// 用于员工表manager的信息插入
                boolean flag = false;
                //360xxxxxxxx55810.00,21060xxxxxx019,无,实习期限201xxxxxx28,362xxxxxx05002X
                String idCardNO = excelresult.get(i).get(5).toString().trim();// 身份证号码
                try {
                    Double.valueOf(idCardNO);
                    idCardNO = idCardNO.substring(0, idCardNO.lastIndexOf("."));
                } catch (Exception e) {
                    //accountName = accountName;
                }
                selectManagerFormMap.put("idCardNO", idCardNO);// 身份证号码
                String manaherName = excelresult.get(i).get(1).toString().trim();// 姓名
                selectManagerFormMap.put("manaherName", manaherName);// 姓名
                flag = managerService.selectManager(selectManagerFormMap);// 查询是否已经存在员工
                if (flag) {// 这个是用来判断是否要新增员工,插入到manager表，初始时员工不存在，为true，执行
                    // 为员工注册
                    // System.out.println("身份证：" + idCardNO);
                    //String idCardNO = excelresult.get(i).get(5).toString();// 身份证号码
                    //String manaherName = excelresult.get(i).get(1).toString();// 姓名
                    FormMap managerFormMap = new FormMap();
                    managerFormMap.put("accountName", idCardNO);// accountName身份证号码
                    managerFormMap.put("idCardNO", idCardNO);
                    managerFormMap.put("managerName", manaherName);
                    managerFormMap.put("password", "551c49acece4c3b4e809f93d1dfd62c1");
                    managerFormMap.put("salt", "hmnb");
                    managerFormMap.put("createTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
                    // 为员工注册,插入到manager表
                    // accountName,managerName,idCardNO,salt,password
                    managerMapper.insert(managerFormMap);
                }
                // 插入parent header headerValue表中
                // parentFormMap.put("createBy",
                // "1");//getSession().getAttribute("managerId")
                // System.out.println("session_managerId=="+SecurityUtils.getSubject().getSession().getAttribute("managerId"));
                parentFormMap.put("createTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
                parentFormMap.put("managerName", idCardNO);// 身份证号码
                // System.out.println("parentFormMap:" + parentFormMap);
                // 插入到parent表(最基本的工资单的信息表)
                // managerName,wagesYear,wagesMonth,createBy,createTime
                int n1 = parentMapper.insert(parentFormMap);
                // System.out.println("n1=" + n1);
                // System.out.println("parent....id==" + formMap.getStr("id"));
                if (n1 > 0) {
                    for (int k2 = 0; k2 < list.size(); k2++) {// lists.size()=65,list存放的是表头集合
                        FormMap headerFormMap = new FormMap();
                        headerFormMap.put("parentId", parentFormMap.getStr("id"));// 这个怎么得到的？？？
                        headerFormMap.put("header", list.get(k2));
                        // System.out.println("表头:" + list.get(k2) + " ");
                        headerFormMap.put("createBy", parentFormMap.getStr("createBy"));// wqf写的是1，1就是对应的人力的主键id,getSession().getAttribute("managerId")
                        // System.out.println("session_managerId=="+SecurityUtils.getSubject().getSession().getAttribute("managerId"));
                        // System.out.println("parentFormMap中的createBy =
                        // "+parentFormMap.getStr("createBy"));
                        headerFormMap.put("createTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
                        // 插入到header表
                        // parentId,header,createBy,createTime
                        int n2 = headerMapper.insert(headerFormMap);
                        // System.out.println("n2=" + n2);
                        // System.out.println("header....id==" +
                        // formMap1.getStr("id"));
                        if (n2 > 0) {
                            FormMap headerValueFormMap = new FormMap();
                            headerValueFormMap.put("headerId", headerFormMap.getStr("id"));
                            headerValueFormMap.put("parentId", parentFormMap.getStr("id"));
                            String salt = RandomNumUtil.getRandNum(8);
                            headerValueFormMap.put("salt", salt);

                            if ("应发合计".equals(headerFormMap.getStr("header"))) {
                                yfTotal = yfTotal + Double.valueOf(excelresult.get(i).get(k2).toString());
                                //System.out.println(parentFormMap.getStr("wagesYear")+"年"+parentFormMap.getStr("wagesMonth")+"月"+"应发   合计其中一个="+excelresult.get(i).get(k2));
                            }

                            if ("实发合计".equals(headerFormMap.getStr("header"))) {
                                sfTotal = sfTotal + Double.valueOf(excelresult.get(i).get(k2).toString());
                                //System.out.println(parentFormMap.getStr("wagesYear")+"年"+parentFormMap.getStr("wagesMonth")+"月"+"实发合计其中一个="+excelresult.get(i).get(k2));
                            }

                            headerValueFormMap.put("headerValue",
                                    BASE64Util.encrypt(excelresult.get(i).get(k2).toString(), salt));// BASE64加密
                            headerValueFormMap.put("createBy", parentFormMap.getStr("createBy"));// wqf写的是1，1就是对应的人力的主键id,getSession().getAttribute("managerId")
                            // System.out.println("session_managerId=="+SecurityUtils.getSubject().getSession().getAttribute("managerId"));
                            // System.out.println("parentFormMap中的createBy =
                            // "+parentFormMap.getStr("createBy"));
                            headerValueFormMap.put("createTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
                            // 插入到headerValue表
                            // headerId,parentId,salt,headerValue,createBy,createTime
                            headerValueMapper.insert(headerValueFormMap);
                            // int n3 = headerValueService.insert(formMap2);
                            // System.out.println("n3=" + n3);
                            // System.out.println("headerValue....id==" +
                            // formMap2.getStr("id"));
                            /*
							 * if (n3 > 0) { result =
							 * UIView("redirect:insertRePayroll");//
							 * 主工资单插入成功跳转到补发工资单新增页面 result.addObject("msg",
							 * "操作成功"); } else { result =
							 * UIView("redirect:insertPayroll");
							 * result.addObject("msg", "操作失败"); return result; }
							 */
                        }
                    }
                }
            }
        }

        //System.out.println(parentFormMap.getStr("wagesYear")+"年"+parentFormMap.getStr("wagesMonth")+"月"+"应发合计="+yfTotal);
        //System.out.println(parentFormMap.getStr("wagesYear")+"年"+parentFormMap.getStr("wagesMonth")+"月"+"实发合计="+sfTotal);
        //插入主工资单合计表
        String totalSalt = RandomNumUtil.getRandNum(8);
        FormMap totalFormMap = new FormMap();
        totalFormMap.put("yfTotal", BASE64Util.encrypt(String.valueOf(yfTotal), totalSalt));//应发合计
        totalFormMap.put("sfTotal", BASE64Util.encrypt(String.valueOf(sfTotal), totalSalt));//实发合计
        totalFormMap.put("salt", totalSalt);
        totalFormMap.put("total_year", parentFormMap.getStr("wagesYear"));//合计年份
        totalFormMap.put("total_month", parentFormMap.getStr("wagesMonth"));//合计月份
        totalMapper.insertMainTotal(totalFormMap);
    }

    //判断主工资表是否已经插入，没有插入主工资表就不能插入补发的
    @Override
    public boolean checkMainWagesInsert(FormMap parentFormMap) {
        List<E> list = wagesParentMapper.checkMainWagesInsert(parentFormMap);
        if (list.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

}
