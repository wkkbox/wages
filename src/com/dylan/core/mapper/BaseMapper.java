package com.dylan.core.mapper;

import java.util.List;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;



/**
 * Copyright(c) 
 * @description :公共数据库操作接口mapper,简单sql通过Mapper实现类处理,复杂逻辑采用xml处理
 * @create		:by author wqf on 2017年4月24日下午6:39:34
 */
public interface BaseMapper {
	
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
