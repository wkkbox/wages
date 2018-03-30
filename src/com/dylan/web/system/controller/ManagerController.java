package com.dylan.web.system.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dylan.core.controller.TenantController;
import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.util.DateUtil;
import com.dylan.core.util.RandomNumUtil;
import com.dylan.core.view.Message;
import com.dylan.web.log.service.LogService;
import com.dylan.web.system.service.ManagerService;
import com.dylan.web.system.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "/system/manager")
public class ManagerController extends TenantController {

	@Resource
	private ManagerService managerService;
	@Resource
	private RoleService roleService;
	@Autowired
	private LogService logService;

	private String page_list = "/center/system/managerList";

	private String redirect_list = "redirect:list";

	//管理员的员工管理展示员工
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		// 获取请求参数
		FormMap formMap = getFormMap();
		// getCacheFormMap();//开启缓存
		// 开启分页
		PageHelper.startPage(formMap.getPage(), formMap.getRows());
		// 查询展示数量
		List<E> list = managerService.selectList(formMap);
		// 返回到list页面
		ModelAndView result = UIView(page_list);
		// 绑定分页返回
		result.addObject("pageInfo", new PageInfo<E>(list));
		// 绑定上一次参数
		result.addObject("queryParam", formMap);
		result.addObject("msg", request.getParameter("msg"));
		return result;
	}

	@ResponseBody
	@RequestMapping("/selectRoles")
	public String selectRoles() {
		// 查询展示数量
		List<E> roleList = roleService.selectList(null);
		return toJson(roleList);
	}

	@ResponseBody
	@RequestMapping("/selectRolesByManagerId")
	public String selectRolesByManagerId() {
		// 获取请求参数
		FormMap formMap = getFormMap();
		// 查询展示数量
		List<E> list = roleService.selectRolesByManagerId(formMap);
		return toJson(list);
	}

	@ResponseBody
	@RequestMapping("/view")
	public String view() {
		FormMap formMap = getFormMap();
		E e = managerService.selectById(formMap);
		return toJsonApi(e);
	}

	@RequestMapping("/save")
	public ModelAndView save() {
		ModelAndView result = UIView(redirect_list, Message.INFO);
		FormMap formMap = getFormMap();
		String[] roleSigns = getRequest().getParameterValues("roleSign");
		if (getSession().getAttribute("managerId") != null) {// 保存上下级关系
			formMap.set("parentId", getSession().getAttribute("managerId").toString());
		}
		if (StringUtils.isEmpty(formMap.getStr("managerId"))) {
			formMap.set("roleSigns", roleSigns);
			managerService.createTrans(formMap);// 事务方法
		} else {
			formMap.set("roleSigns", roleSigns);
			managerService.updateTrans(formMap);// 事务方法
		}
		return result;
	}

	@RequestMapping("/remove")
	public ModelAndView remove() {
		ModelAndView result = UIView(redirect_list, Message.DANGER);
		FormMap formMap = getFormMap();
		String idStr = formMap.getStr("idStr");
		if (null == idStr || idStr.equals("")) {
			managerService.deleteTrans(formMap);
		} else {
			String[] ids = idStr.split(",");
			for (int i = 0; i < ids.length; i++) {
				formMap.put("managerId", ids[i]);
				managerService.deleteTrans(formMap);
			}
		}

		return result;
	}

	// 跳转到修改密码界面
	@RequestMapping("/skipEditPassword")
	public ModelAndView skipEditPassword(HttpServletRequest request) {
		ModelAndView result = UIView("/center/system/editPassword");
		result.addObject("msg", request.getParameter("msg"));
		return result;
	}

	/**
	 * 获取密码
	 * 
	 * @author Benjamin 2017年9月19日
	 */
	@ResponseBody
	@RequestMapping("/getPassword")
	public String getPassword(HttpServletRequest request) {
		if (!request.getMethod().equals("POST")) {// 防止不正确提交
			return toJsonApi(null, "提交有误", "1111");
		} else {
			FormMap formMap = getFormMap();
			String managerId = (String) getSession().getAttribute("managerId");
			formMap.put("managerId", managerId);
			E user = managerService.selectById(formMap);
			String password = formMap.getStr("password");
			String source = user.getStr("password");
			String salt = user.getStr("salt");
			Md5Hash md5Hash = new Md5Hash(password, salt, 2);
			String upass = md5Hash.toString();
			if (upass.equals(source)) {
				return toJsonApi(null, "zq", "0000");
			} else {
				return toJsonApi(null, "bzq", "0000");
			}
		}
	}

	/**
	 * 修改密码
	 * 
	 * @author Benjamin 2017年9月19日
	 */
	@RequestMapping("/editPassword")
	public ModelAndView editPassword(HttpServletRequest request) {
		ModelAndView result = UIView("redirect:skipEditPassword");
		if (!request.getMethod().equals("POST")) {
			result.addObject("msg", "提交方式有误！");
		} else {
			FormMap formMap = getFormMap();
			String managerId = (String) getSession().getAttribute("managerId");
			formMap.put("managerId", managerId);
			String salt = RandomNumUtil.getRandNum(4);
			Md5Hash md5Hash = new Md5Hash(formMap.getStr("password"), salt, 2);
			formMap.set("password", md5Hash.toString());
			formMap.put("salt", salt);
			int n = managerService.edit(formMap);
			if (n > 0) {
				result.addObject("msg", "修改密码成功！重新登录请用新密码！");
				// 添加修改密码日志
				FormMap logFormMap = new FormMap();
				logFormMap.put("logLevel", 0);
				logFormMap.put("content", getSession().getAttribute("userName") + "执行修改密码");
				logFormMap.put("recordTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
				logFormMap.put("logName", "修改密码");
				// 插入日志表
				logService.insert(logFormMap);
			} else {
				result.addObject("msg", "修改密码失败");
			}
		}
		return result;
	}

	/**
	 * admin点击密码重置
	 * 
	 * @author Benjamin 2017年9月19日
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request) {
		ModelAndView result = UIView("redirect:list");
		if (!request.getMethod().equals("POST")) {
			result.addObject("msg", "提交方式有误！");
		} else {
			FormMap formMap = getFormMap();
			formMap.put("password", "551c49acece4c3b4e809f93d1dfd62c1");
			formMap.put("salt", "hmnb");
			int n = managerService.edit(formMap);
			if (n > 0) {
				result.addObject("msg", "重置密码成功");
				FormMap logFormMap = new FormMap();
				logFormMap.put("logLevel", 0);
				logFormMap.put("content", getSession().getAttribute("userName") + "执行密码重置");
				logFormMap.put("recordTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
				logFormMap.put("logName", "密码重置");
				logService.insert(logFormMap);
			} else {
				result.addObject("msg", "重置密码失败");
			}
		}
		return result;
	}

}
