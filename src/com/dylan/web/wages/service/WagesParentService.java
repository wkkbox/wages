package com.dylan.web.wages.service;

import java.util.ArrayList;
import java.util.List;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.service.BaseService;

public interface WagesParentService extends BaseService {
	public List<E> selectListByManagerId(FormMap formMap);

	public List<E> selectWagesParent(FormMap formMap);

	List<E> selectWagesDetail(FormMap formMap);

	List<E> selectWagesDetailMail(FormMap formMap);

	int editState(FormMap formMap);

	public void insert_logs_parent_header_headerValue_total(FormMap logFormMap, FormMap parentFormMap, ArrayList<String> list,
			ArrayList<ArrayList<Object>> excelresult) throws Exception;

	public boolean checkMainWagesInsert(FormMap parentFormMap);
}