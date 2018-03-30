package com.dylan.web.wages.mapper;

import java.util.List;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.mapper.BaseMapper;

public interface WagesParentMapper extends BaseMapper {
	public List<E> selectListByManagerId(FormMap formMap);

	public List<E> selectWagesParent(FormMap formMap);

	List<E> selectWagesDetail(FormMap formMap);

	List<E> selectWagesDetailMail(FormMap formMap);
	int editState(FormMap formMap);

	public List<E> checkMainWagesInsert(FormMap parentFormMap);
}