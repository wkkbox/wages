package com.dylan.core.entity;

/**
 * Copyright(c) 
 * @description :请求参数接收实体类,接收来自GET/POST传递过来的参数,并提供取值与转型
 * @create		:by author wqf on 2017年4月24日下午6:37:49
 */
public class FormMap extends BaseMap<String, Object>{
	private static final long serialVersionUID = 8781487565264137852L;
	
	//设置分页默认为第一页开始
	public Integer getPage(){
		return null == this.get("page") ? 1 : Integer.parseInt(this.get("page").toString());
	}
	
	//设置分页默认为每页展示3条数据
	public Integer getRows(){
		return null == this.get("rows") ? 8 : Integer.parseInt(this.get("rows").toString());
	}
	
}
