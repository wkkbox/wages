package com.dylan.web.system.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dylan.core.controller.TenantController;
import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.util.BASE64Util;
import com.dylan.core.util.DateUtil;
import com.dylan.core.util.StringUtil;
import com.dylan.web.log.service.LogService;
import com.dylan.web.system.service.ManagerService;
import com.dylan.web.system.service.PermissionService;
import com.dylan.web.system.service.ResourceService;
import com.dylan.web.system.service.RoleService;

@Controller
@RequestMapping(value = "/manager")
public class LoginController extends TenantController {
	
	@Resource
	private ManagerService managerService;
	
	@Resource
	private PermissionService permissionService;
	
	@Resource
	private ResourceService resourceService;
	
	@Resource
	private RoleService roleService;
	
	@Autowired
	private LogService logService;

	@RequestMapping("/login")
	public String loginView() {
		return "center/login";
	}

	// 用户登录
	@RequestMapping("/dologin")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = null;
		FormMap formMap = getFormMap();
		Subject subject = SecurityUtils.getSubject();
		E user = managerService.selectOne(formMap);
		if (!"POST".equals(request.getMethod())) {
			result = UIView("/center/login");
			result.addObject("msg", "提交方式有误");
			return result;
		} else if (StringUtil.isEmpty(formMap)) {
			result = UIView("/center/login");
			result.addObject("msg", "请输入用户名密码");
			return result;
		} else if (subject.isAuthenticated()) {// 表示已经登录
			result = UIView("forward:/center/system/index");
			return result;
		} else {
			try {
				UsernamePasswordToken token = new UsernamePasswordToken(formMap.getStr("accountName"),
						formMap.getStr("password"));
				Cookie cookieName = new Cookie("name", formMap.getStr("accountName"));
				// cookie密码加密
				String pwd = "";
				try {
					pwd = BASE64Util.encrypt(formMap.getStr("password"), "279ki954");
				} catch (Exception e) {
					e.printStackTrace();
				}
				// new Md5Hash(formMap.getStr("password"), "hmnb", 2);
				Cookie cookiePwd = new Cookie("pwd", pwd);
				Cookie rememberMe = new Cookie("rememberMe", "on");
				if ("on".equals(formMap.get("rememberMe"))) {// 在登录界面选中了记住我
					// 设置cookie过期时间
					rememberMe.setMaxAge(7 * 24 * 3600);
					cookieName.setMaxAge(7 * 24 * 3600);
					cookiePwd.setMaxAge(7 * 24 * 3600);
					// token.setRememberMe(true);
				} else {
					// 在登录界面没有选中记住我
					// 删除cookie
					rememberMe.setMaxAge(0);
					cookieName.setMaxAge(0);
					cookiePwd.setMaxAge(0);
					// token.setRememberMe(false);
				}
				// 添加，修改记住我所用cookie
				response.addCookie(rememberMe);
				response.addCookie(cookieName);
				response.addCookie(cookiePwd);
				subject.login(token);
			} catch (UnknownAccountException uae) {
				// 用户名不存在
				result = UIView("/center/login");
				result.addObject("msg", "用户名或密码错误");
				return result;
			} catch (IncorrectCredentialsException ice) {
				// 用户名或者密码错误
				result = UIView("/center/login");
				result.addObject("msg", "用户名或密码错误");
				return result;
			}
		}
		if (subject.isAuthenticated()) {
			// 跳转到首页
			result = UIView("forward:/center/system/index");
			getSession().setAttribute("managerId", user.getStr("id"));
			getSession().setAttribute("type", user.getStr("type"));
			getSession().setAttribute("userName", user.getStr("manaherName"));// 姓名
			getSession().setAttribute("accountName", user.getStr("accountName"));// 身份证
			// 添加登录日志
			FormMap logFormMap = new FormMap();
			logFormMap.put("logLevel", 0);
			logFormMap.put("content", user.getStr("manaherName") + "登录系统");
			logFormMap.put("recordTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			logFormMap.put("logName", "登录系统");
			// 插入日志表
			logService.insert(logFormMap);
			return result;
		}
		result = UIView("/center/login");
		result.addObject("msg", "用户名或密码错误");
		return result;
	}

	/**
	 * 用户登出
	 * 
	 * @author Benjamin 2017年7月16日
	 */
	@RequestMapping("/logout")
	public String logout() {
		// 登出操作
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "/center/login";
	}

	/**
	 * 密码修改
	 * 
	 * @return
	 */
	@RequestMapping("/password")
	public String password() {
		return "/center/password";
	}

	@ResponseBody
	@RequestMapping("/updatePassword")
	public String updatePassword() throws Exception {
		try {
			FormMap paramMap = new FormMap();
			String managerId = getSession().getAttribute("managerId").toString();
			String accountName = getSession().getAttribute("accountName").toString();
			String managerPwd = this.getRequest().getParameter("oldPassword");
			paramMap.put("accountName", accountName);
			// if(StringUtils.isNotEmpty(managerPwd)){
			// managerPwd=MD5.sign(managerPwd, "123", "utf-8");
			paramMap.put("password", managerPwd);
			// }
			E map = managerService.selectOne(paramMap);
			if (map == null || map.get("managerId") == null) {
				return toJsonApi("", "旧密码输入错误，请重新输入!", "-1");
			} else {
				FormMap passMap = new FormMap();
				passMap.put("managerId", managerId);
				String managerPwd11 = this.getRequest().getParameter("newPassword");
				// if(StringUtils.isNotEmpty(managerPwd11)){
				// managerPwd11=MD5.sign(managerPwd11, "123", "utf-8");
				passMap.put("password", managerPwd11);
				// }
				managerService.edit(passMap);
			}
			return toJsonApi("密码修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return toJsonApi("", "操作异常：" + e.getMessage(), "-1");
		}
	}

}
