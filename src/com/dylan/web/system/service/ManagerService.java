package com.dylan.web.system.service;

import java.util.List;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.service.BaseService;

public interface ManagerService extends BaseService {

	int insertManagerAndRole(FormMap param);

	void createTrans(FormMap param);

	void updateTrans(FormMap param);

	void deleteTrans(FormMap param);

	E selectShopByManagerId(FormMap param);

	List<E> selectManagerNames();

	boolean selectManager(FormMap param);
	List<E> selectAccountName(FormMap param);
}
