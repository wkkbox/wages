package com.dylan.web.wages.mapper;

import com.dylan.core.mapper.BaseMapper;

import java.util.List;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;

public interface WagesTotalMapper extends BaseMapper{

	void insertMainTotal(FormMap totalFormMap);

	void insertReTotal(FormMap totalFormMap);

	List<E> selectMainTotal(FormMap formMap);

	List<E> selectReTotal(FormMap formMap);  
	
	
}