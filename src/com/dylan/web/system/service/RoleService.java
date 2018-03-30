package com.dylan.web.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.service.BaseService;


public interface RoleService extends BaseService{
	
	List<E> selectRolesByManagerId(FormMap param);
	List<E> selectResourceByRoles(List<E> list);
	void authorize(FormMap formMap,HttpServletRequest request);
	
}
