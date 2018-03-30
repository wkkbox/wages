package com.dylan.core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.dylan.core.entity.E;
import com.dylan.core.view.Message;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title: TenantController.java
 * @Description:基础控制器
 * @company:中国通信服务
 * @author Benjamin
 * @date 2017年12月26日 下午5:18:37
 * @version V1.0
 */
@SuppressWarnings("all")
public class TenantController extends BaseController {

	// 转为json
	public String toJson(Object obj) {
		return JSON.toJSONString(obj);
	}

	// 直接返回api接口,默认方法返回成功状态
	public String toJsonApi(Object obj) {
		return JSON.toJSONString(toJsonApi(obj, "success", "0"));
	}

	public String toJsonApiFailure(Object obj) {
		return JSON.toJSONString(toJsonApi(obj, "failure", "-1"));
	}

	public String toJsonApi(Object obj, String msg) {
		return JSON.toJSONString(toJsonApi(obj, msg, "0"));
	}

	public String toJsonApiFailure(Object obj, String msg) {
		return JSON.toJSONString(toJsonApi(obj, msg, "-1"));
	}

	// 直接返回api接口,可自定义返回msg,code
	public String toJsonApi(Object obj, String msg, String code) {
		E e = new E();
		e.set("code", code);
		e.set("message", msg);
		e.set("data", obj);
		return JSON.toJSONString(e);
	}

	// 封装view
	public ModelAndView UIView(String viewName) {
		ModelAndView view = new ModelAndView(viewName);
		return view;
	}

	// 自定义带默认内容View,根据级别返回
	public ModelAndView UIView(String viewName, int level) {
		ModelAndView view = new ModelAndView(viewName);
		Message messageView = new Message();
		return messageView.showView(view, level);
	}

	// 自定义带提示内容View,根据级别返回
	public ModelAndView UIView(String viewName, String message, int level) {
		ModelAndView view = new ModelAndView(viewName);
		Message messageView = new Message();
		return messageView.showView(view, message, level);
	}

}
