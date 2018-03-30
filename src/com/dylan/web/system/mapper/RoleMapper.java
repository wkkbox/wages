package com.dylan.web.system.mapper;

import java.util.List;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.mapper.BaseMapper;



public interface RoleMapper extends BaseMapper{
	
	List<E> selectRolesByManagerId(FormMap param);
	List<E> selectResourceByRoles(List<E> list);
	void cleanRoleResourceByroleId(FormMap formMap);
	void insertRoleResource(FormMap formMap);
	E isExitsPermission(FormMap formMap);
	void insertPermission(FormMap formMap);
	void cleanRolePermissionByroleId(FormMap formMap);
	void insertRolePermission(FormMap formMap);

}