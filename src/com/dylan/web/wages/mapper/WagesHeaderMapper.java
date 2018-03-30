package com.dylan.web.wages.mapper;

import java.util.List;

import com.dylan.core.mapper.BaseMapper;
import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;

public interface WagesHeaderMapper extends BaseMapper{  
	List<E> selectTotal(FormMap formMap);
	
}