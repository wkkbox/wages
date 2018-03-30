package com.dylan.web.wages.service;

import java.util.List;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.service.BaseService;

public interface WagesHeaderService extends BaseService {
	List<E> selectTotal(FormMap formMap);
}