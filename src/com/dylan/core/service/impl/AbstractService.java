package com.dylan.core.service.impl;

import java.util.List;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.mapper.BaseMapper;
import com.dylan.core.service.BaseService;



/**
 * Copyright(c) 
 * @description :公共服务基础实现
 * @create		:by author wqf on 2017年4月24日下午6:43:47
 */
public abstract class AbstractService implements BaseService{
	
	//定义成抽象方法,由子类实现,完成mapper的注入
	public abstract BaseMapper setMapper();
	
	public int insert(FormMap param) {
		return setMapper().insert(param);
	}

	public int edit(FormMap param) {
		return setMapper().edit(param);
	}

	public int remove(FormMap param) {
		return setMapper().remove(param);
	}

	public E selectById(FormMap param) {
		return setMapper().selectById(param);
	}

	public E selectOne(FormMap param) {
		return setMapper().selectOne(param);
	}

	public List<E> selectList(FormMap param) {
		return setMapper().selectList(param);
	}

}
