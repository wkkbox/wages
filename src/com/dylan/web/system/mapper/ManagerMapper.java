package com.dylan.web.system.mapper;

import java.util.List;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.mapper.BaseMapper;

public interface ManagerMapper extends BaseMapper {

	int insertManagerAndRole(FormMap param);

	int removeManagerAndRole(FormMap param);

	E selectShopByManagerId(FormMap param);

	List<E> selectManagerNames();

	E selectManager(FormMap param);

	List<E> selectAccountName(FormMap param);
}