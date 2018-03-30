package com.dylan.web.system.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.mapper.BaseMapper;
import com.dylan.core.service.impl.AbstractService;
import com.dylan.web.system.mapper.RoleMapper;
import com.dylan.web.system.service.RoleService;


@Service
@Transactional(readOnly = false)
public class RoleServiceImpl extends AbstractService implements RoleService {

    @Resource
    private RoleMapper roleMapper;

	public BaseMapper setMapper() {
		return roleMapper;
	}
    
	public List<E> selectRolesByManagerId(FormMap param) {
		return roleMapper.selectRolesByManagerId(param);
	}

	public List<E> selectResourceByRoles(List<E> list) {
		return roleMapper.selectResourceByRoles(list);
	}

	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)//事务可写，记住方法中不能捕获异常，捕获异常不会回滚事务，trycatch也不能用
	public void authorize(FormMap formMap, HttpServletRequest request) {
    	String [] resources=request.getParameterValues("resources");//获取用户的菜单权限
    	String [] permissions=request.getParameterValues("permissions");//获取用户的操作权限
    	//创建角色与操作权限表的关系，system_role_permission表
    	
    	//将当期角色与菜单创建映射关系，system_role_resource表
    	roleMapper.cleanRoleResourceByroleId(formMap);
    	if(resources!=null){
	    	for (String resourceId : resources) {
	    		formMap.set("resourceId", resourceId);
	    		roleMapper.insertRoleResource(formMap);
			}
    	}

		roleMapper.cleanRolePermissionByroleId(formMap);//根据当前角色先清理操作权限，然后重新建立关系
    	if(permissions!=null){
    		//将获取的操作权限先添加到权限表中，确保唯一system_permissions表
        	for (String permissionId : permissions) { 
    			formMap.set("permissionId", permissionId);
    			roleMapper.insertRolePermission(formMap);
    		}
    	} 
	}

}
