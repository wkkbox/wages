package com.dylan.web.security;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.util.StringUtil;
import com.dylan.web.system.service.ManagerService;
import com.dylan.web.system.service.PermissionService;
import com.dylan.web.system.service.RoleService;

/**
 * Copyright(c)
 * 
 * @description :认证和授权
 * @create :by author wqf on 2017年4月26日下午6:07:14
 */
@Component(value = "securityRealm1")
public class SecurityRealm1 extends AuthorizingRealm {

	@Resource
	private RoleService roleService;
	@Resource
	private ManagerService managerService;
	@Resource
	private PermissionService permissionService;

	// 认证
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userCode = (String) token.getPrincipal();
		FormMap formMap = new FormMap();
		formMap.put("accountName", userCode);
		E userInfo = new E();
		E authentication = managerService.selectOne(formMap);
		if (StringUtil.isEmpty(authentication)) {
			return null;
		}
		String salt = authentication.getStr("salt");
		String password = authentication.getStr("password");
		userInfo.put("accountName", authentication.getStr("accountName"));
		userInfo.put("manageId", authentication.getStr("id"));
		// 将userInfo设置simpleAuthenticationInfo
		
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userInfo, password,
				ByteSource.Util.bytes(salt), getName());
		// 将认证数据返回
		return simpleAuthenticationInfo;
	}

	// 授权
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		String accountName = String.valueOf(pc.getPrimaryPrincipal());
		FormMap formMap = new FormMap();
		formMap.set("accountName", accountName);
		E user = managerService.selectOne(formMap);
		formMap.set("managerId", user.getBigInteger("managerId"));
		List<E> roles = roleService.selectRolesByManagerId(formMap);
		List<E> resources = roleService.selectResourceByRoles(roles);
		List<E> permissions = permissionService.selectPermissionsByRoles(roles);
		// 添加菜单权限
		for (E e : resources) {
			authorizationInfo.addRole(e.getStr("resourceSign"));
		}
		// 添加菜单操作(按钮)权限
		for (E e : permissions) {
			authorizationInfo.addStringPermission(e.getStr("permissionSign"));
		}
		return authorizationInfo;
	}

}
