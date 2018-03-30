package com.dylan.core.service;

import java.util.List;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;



/**
 * Copyright(c) 
 * @description :公共服务
 * @create		:by author wqf on 2017年4月24日下午6:41:47
 */
public interface BaseService {
	
	//插入对象
    int insert(FormMap param);

    //更新对象
    int edit(FormMap param);

    //通过主键,删除对象
    int remove(FormMap param);

    //通过主键,查询对象
    E selectById(FormMap param);

    //查询单个对象
    E selectOne(FormMap param);

    //查询多个对象
    List<E> selectList(FormMap param);
    
}
