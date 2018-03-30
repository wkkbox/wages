package com.dylan.web.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dylan.core.entity.E;
import com.dylan.core.mapper.BaseMapper;
import com.dylan.core.service.impl.AbstractService;
import com.dylan.web.system.mapper.PermissionMapper;
import com.dylan.web.system.service.PermissionService;


@Service
@Transactional(readOnly = false)
public class PermissionServiceImpl extends AbstractService implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

	public BaseMapper setMapper() {
		return permissionMapper;
	}
    
	public List<E> selectPermissionsByRoles(List<E> list) {
		return permissionMapper.selectPermissionsByRoles(list);
	}
   
}
