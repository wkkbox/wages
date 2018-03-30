package com.dylan.core.controller;

import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.dylan.core.entity.FormMap;



/**
 * Copyright(c) 
 * @description :基础控制器
 * @create		:by author wqf on 2017年4月24日下午6:13:08
 */
public abstract class BaseController {
	
	//ThreadLocal确保高并发每个请求的request,response各自独立
	private static ThreadLocal<ServletRequest> currentRequest = new ThreadLocal<ServletRequest>();
	private static ThreadLocal<ServletResponse> currentResponse = new ThreadLocal<ServletResponse>();

	@ModelAttribute
	public void initReqAndRep(HttpServletRequest request,HttpServletResponse response) {
		currentRequest.set(request);
		currentResponse.set(response);
	}

	protected Cookie[] getCookies() {
		return getRequest().getCookies();
	}
	protected HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		session.setMaxInactiveInterval(604800);
		return session;
	}
	
	public HttpServletRequest getRequest() {
		return (HttpServletRequest) currentRequest.get();
	}
	public HttpServletResponse getResponse() {
		return (HttpServletResponse) currentResponse.get();
	}
	
	protected void setRequestAttribute(String key,Object value){
		getRequest().setAttribute(key,value);
	}
	protected void setSessionAttribute(String key,Object value){
		getSession().setAttribute(key,value);
	}
	protected Object getRequestAttribute(String key){
		return getRequest().getAttribute(key);
	}
	protected Object getSessionAttribute(String key){
		return getSession().getAttribute(key);
	}
	
	public Map<String,String[]> getParamMap(){
		return getRequest().getParameterMap();
	}
	public boolean containKey(String key){
		return getParamMap().containsKey(key);
	}
	
	public String getReferer(){
		String referer = getRequest().getHeader("referer");
		return Objects.toString(referer,"");
	}
	
	protected Map<String,String[]> getParameterMap() throws Exception {
		return getRequest().getParameterMap();
	}
	protected String[] getParameterValues(String parameter) {
		String[] valueArr = getRequest().getParameterValues(parameter);
		if (valueArr != null) {
			return valueArr;
		}
		return new String[0];
	}
	
	//把参数map化
	public FormMap getFormMap(){
		Enumeration<String> en = getRequest().getParameterNames();
		FormMap map = new FormMap();
		try {
			String order = "",sort="";
			while (en.hasMoreElements()) {
				String element = en.nextElement().toString();
				if(element.endsWith("[]")){
					String[] as = getRequest().getParameterValues(element);
					if(as != null && as.length != 0 && as.toString() != "[]"){
						map.set(element,as);
					}
				}else{
					String as = getRequest().getParameter(element);
					if(!StringUtils.isEmpty(as)){
						map.set(element, as);
						if(element.toLowerCase().equals("column"))order = as;
						if(element.toLowerCase().equals("sort"))sort = as;
					}
				}
			}
			if(!StringUtils.isEmpty(order) && !StringUtils.isEmpty(sort))
				map.set("orderby", " order by " + order + " " + sort);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  map;
	}
}
