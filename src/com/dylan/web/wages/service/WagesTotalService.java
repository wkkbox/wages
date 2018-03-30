
package com.dylan.web.wages.service;

import java.util.List;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.service.BaseService;

public interface WagesTotalService extends BaseService{

	List<E> selectMainTotal(FormMap formMap);

	List<E> selectReTotal(FormMap formMap);

}

	