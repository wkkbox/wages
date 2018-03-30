package com.dylan.web.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dylan.core.controller.TenantController;

/**
 * 
 * @author Benjamin
 *
 *         2017年11月27日
 */
@Controller
public class PageController extends TenantController {

	// 服务中心后台登录视图
	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView result = UIView("/center/login");
		return result;
	}

	// 系统主页
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView result = UIView("/center/system/index");
		return result;
	}

	@RequestMapping("/renew")
	public ModelAndView renew(HttpServletRequest request) {
		ModelAndView result = UIView("/center/index");
		return result;
	}

	/**
	 * 后台布局-左视图
	 */
	@RequestMapping("/left")
	public String left() {
		return "/center/left";
	}

	/**
	 * 后台布局-右视图
	 */
	@RequestMapping("/right")
	public String right() {
		return "/center/right";
	}

	/**
	 * 后台布局-顶部视图
	 */
	@RequestMapping("/top")
	public String top() {
		return "/center/top";
	}

	/**
	 * 控制面板视图
	 */
	@RequestMapping("/dashboard")
	public String dashboard() {
		return "/center/dashboard";
	}

	/**
	 * 404视图
	 */
	@RequestMapping("/404")
	public String error404() {
		return "/center/404";
	}

	/**
	 * 401视图
	 */
	@RequestMapping("/401")
	public String error401() {
		return "/center/401";
	}

	/**
	 * 500视图
	 */
	@RequestMapping("/500")
	public String error500() {
		return "/center/500";
	}

	/**
	 * 忘记密码页面
	 * 
	 * @date2017年5月8日
	 * @author Benjamin
	 */
	@RequestMapping("/forgot")
	public String forgot() {
		return "/center/forgot";
	}
}