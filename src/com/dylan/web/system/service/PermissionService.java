package com.dylan.web.system.service;

import java.util.List;

import com.dylan.core.entity.E;
import com.dylan.core.service.BaseService;


public interface PermissionService extends BaseService{

	List<E> selectPermissionsByRoles(List<E> list);
	
}
