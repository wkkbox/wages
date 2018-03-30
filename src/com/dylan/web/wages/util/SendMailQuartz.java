package com.dylan.web.wages.util;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dylan.core.config.Global;
import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.util.DateUtil;
import com.dylan.core.util.MailUtil;
import com.dylan.core.util.StringUtil;
import com.dylan.web.system.service.ManagerService;
import com.dylan.web.wages.service.WagesParentService;

/**
 * 定时任务配置器
 */
public class SendMailQuartz {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Resource
    private ManagerService managerService;
    private static final String SEND_MAIL_COUNT = Global.getConfig("send_mail_count");// 50

    @Autowired
    private WagesParentService parentService;

    public void sendEmail() throws Exception {
        FormMap formMap = new FormMap();
        FormMap formMap1 = new FormMap();
        FormMap formMap2 = new FormMap();
        FormMap formMap3 = new FormMap();
        formMap.put("wagesYear", DateUtil.getYear(new Date()));
        formMap.put("wagesMonth", DateUtil.getMonth(new Date()));
        List<E> selectWagesDetailMail = parentService.selectWagesDetailMail(formMap);// list中是parentId和身份证号
        Integer size = selectWagesDetailMail.size();// 得到有多少人能被发送邮件
        //System.out.println("size=" + size);
        Integer mailCount = Integer.parseInt(SEND_MAIL_COUNT);// 50
        if (StringUtil.isEmpty(selectWagesDetailMail)) {// 查询不到数据---无需发送邮件
            logger.info("查询无数据");
        } else {
            //if (size > mailCount) {// 超出发送范围，超过50人
            if (size > 250) {
                //System.out.println("大于100");
                //只发送250
                for (int i = 0; i < 250; i++) {//size--->mailCount
                    try {
                        E e = selectWagesDetailMail.get(i);
                        //System.out.println((i + 1) + "......e.getStr(managerName)=" + e.getStr("managerName"));
                        String idCardNO = e.getStr("managerName");// 身份证号码
                        if (idCardNO.trim().length() == 18) {
                            formMap1.put("accountName", idCardNO);// 身份证
                            E user = managerService.selectOne(formMap1);// 查到身份证对应的人
                            formMap2.put("id", e.getStr("parentId"));
                            List<E> selectWagesParent = parentService.selectWagesDetail(formMap2);// 得到工资单详情，header，headerValue，salt
                            StringBuffer sb = new StringBuffer();
                            for (int j = 0; j < selectWagesParent.size(); j++) {
                                String header = selectWagesParent.get(j).getStr("header");
                                String headerValue;
                                if ("".equals(selectWagesParent.get(j).getStr("hreaderValue"))) {
                                    headerValue = "无";
                                } else {
                                    headerValue = selectWagesParent.get(j).getStr("hreaderValue");
                                }
                                sb.append(header + "  :  " + headerValue + "<br>	");
                            }
                            //System.out.println("user.getStr(mail)=" + user.getStr("mail"));
                            if (!StringUtil.isEmpty(user.getStr("mail"))) {// 填写了邮箱
                                MailUtil mailUtil = new MailUtil();
                                mailUtil.setReceiveMailNames(new String[]{user.getStr("manaherName")});
                                mailUtil.setReceiveMailAccounts(new String[]{user.getStr("mail")});
                                //System.out.println((i + 1) + "..." + user.getStr("mail"));
                                mailUtil.sendMail(sb.toString(), "0");
                                formMap3.put("id", e.getStr("parentId"));
                                parentService.editState(formMap3);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //System.out.println("小于100");
                for (int i = 0; i < size; i++) {//size--->mailCount
                    try {
                        E e = selectWagesDetailMail.get(i);
                        //System.out.println((i + 1) + "......e.getStr(managerName)=" + e.getStr("managerName"));
                        String idCardNO = e.getStr("managerName");// 身份证号码
                        if (idCardNO.trim().length() == 18) {
                            formMap1.put("accountName", idCardNO);// 身份证
                            E user = managerService.selectOne(formMap1);// 查到身份证对应的人
                            formMap2.put("id", e.getStr("parentId"));
                            List<E> selectWagesParent = parentService.selectWagesDetail(formMap2);// 得到工资单详情，header，headerValue，salt
                            StringBuffer sb = new StringBuffer();
                            for (int j = 0; j < selectWagesParent.size(); j++) {
                                String header = selectWagesParent.get(j).getStr("header");
                                String headerValue;
                                if ("".equals(selectWagesParent.get(j).getStr("hreaderValue"))) {
                                    headerValue = "无";
                                } else {
                                    headerValue = selectWagesParent.get(j).getStr("hreaderValue");
                                }
                                sb.append(header + "  :  " + headerValue + "<br>	");
                            }
                            //System.out.println("user.getStr(mail)=" + user.getStr("mail"));
                            if (!StringUtil.isEmpty(user.getStr("mail"))) {// 填写了邮箱
                                MailUtil mailUtil = new MailUtil();
                                mailUtil.setReceiveMailNames(new String[]{user.getStr("manaherName")});
                                mailUtil.setReceiveMailAccounts(new String[]{user.getStr("mail")});
                                //System.out.println((i + 1) + "..." + user.getStr("mail"));
                                mailUtil.sendMail(sb.toString(), "0");
                                formMap3.put("id", e.getStr("parentId"));
                                parentService.editState(formMap3);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            //}
        }
    }
}
