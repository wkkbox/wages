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
 * 用户身份验证
 * 
 * @author wqf
 * @date：2017年2月14日 上午2:36:38
 * @version 1.0
 */
@Component(value = "securityRealm")
public class SecurityRealm extends AuthorizingRealm {

	@Resource
	private ManagerService managerService;

	@Resource
	private RoleService roleService;

	@Resource
	private PermissionService permissionService;

	/**
	 * 权限检查
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		String accountName = String.valueOf(principals.getPrimaryPrincipal());
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

	/**
	 * 登录验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		FormMap formMap = new FormMap();
		// 获取用户输入的code
		String userCode = (String) token.getPrincipal();
		formMap.put("accountName", userCode);
		// 查询用户信息
		E sysUser = managerService.selectOne(formMap);
		// 查询用户信息为null;
		if (StringUtil.isEmpty(sysUser)) {
			return null;
		}
		// 获取用户输入的密码
		String password = sysUser.getStr("password");
		// 用户盐数据
		String salt = sysUser.getStr("salt");	
		// 将userInfo设置simpleAuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(sysUser, password,
				ByteSource.Util.bytes(salt), getName());
		// 将认证数据返回
		return simpleAuthenticationInfo;
	}

}
