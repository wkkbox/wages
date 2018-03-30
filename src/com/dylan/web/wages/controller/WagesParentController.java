package com.dylan.web.wages.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dylan.core.contant.Constant;
import com.dylan.core.contant.Utility;
import com.dylan.core.controller.TenantController;
import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.util.DateUtil;
import com.dylan.core.util.ExcelUtil;
import com.dylan.core.util.StringUtil;
import com.dylan.core.util.TableHeaderUtil;
import com.dylan.web.log.service.LogService;
import com.dylan.web.system.service.ManagerService;
import com.dylan.web.wages.service.WagesParentService;
import com.dylan.web.wages.service.WagesReParentService;
import com.dylan.web.wages.service.WagesTotalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/wagesParent")
public class WagesParentController extends TenantController {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	public static final long MAXSIZE = 10485760000l;// 最大文件大小
	private static final String MANAGERTYPE2 = "2";// 员工

	@Autowired
	private WagesParentService parentService;

	@Autowired
	private WagesReParentService reParentService;

	@Autowired
	private WagesTotalService totalService;

	// @Autowired
	// private WagesHeaderService headerService;

	// @Autowired
	// private WagesHeaderValueService headerValueService;

	@Autowired
	private LogService logService;
	@Resource
	private ManagerService managerService;

	// 新增补发工资单
	@RequestMapping(value = "/reUploadSpringMvcSingleFile", method = RequestMethod.POST)
	public ModelAndView reUploadSingleFile(HttpServletRequest request,
			@RequestParam("excelData") MultipartFile excelData) throws Exception {
		ModelAndView result = null;
		// 判断提交方式
		if (!"POST".equals(request.getMethod())) {
			result = UIView("redirect:insertRePayroll");
			result.addObject("msg", "提交方式有误");
			return result;
		}
		// parentFormMap用于存parent表(最基本的工资单的信息表)的数据，有request传过来的wages_year和wages_month
		FormMap parentFormMap = getFormMap();// 已经有request传过来的wages_year和wages_month
		// 拿到人力的id号,可以得到是哪个人在插入工资单
		parentFormMap.put("createBy", getSession().getAttribute("managerId"));
		// System.out.println("session_managerId=="+SecurityUtils.getSubject().getSession().getAttribute("managerId"));
		// 判断表单年月是否选中
		if (Integer.valueOf(parentFormMap.getStr("wagesYear")) < 0) {
			result = UIView("redirect:insertRePayroll");
			result.addObject("msg", "请选择年份");
			return result;
		}
		if (Integer.valueOf(parentFormMap.getStr("wagesMonth")) < 0) {
			result = UIView("redirect:insertRePayroll");
			result.addObject("msg", "请选择月份");
			return result;
		}

		// 判断是否已经导入主工资表,已经插入主表为true，否则为false
		//boolean isMainWagesInsert = parentService.checkMainWagesInsert(parentFormMap);
		if (true) {
			// logFormMap用于数据库日志表log_logs的信息插入
			FormMap logFormMap = new FormMap();
			logFormMap.put("logLevel", 0);
			logFormMap.put("content", getSession().getAttribute("userName") + "执行新增" + parentFormMap.getStr("wagesYear")
					+ "年" + parentFormMap.getStr("wagesMonth") + "月" + "补发工资单");
			logFormMap.put("recordTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			logFormMap.put("logName", "新增" + parentFormMap.getStr("wagesYear") + "年"
					+ parentFormMap.getStr("wagesMonth") + "月" + "补发工资单");
			// 用于存表头的字段的名，例如序号，姓名，部门这样
			ArrayList<String> list = null;
			// 解析工资表表后得到的对象
			ArrayList<ArrayList<Object>> excelresult = null;
			logger.info("操作excel插入");
			// 判断上传Excel表是否正确
			if (!StringUtil.isEmpty(excelData)) {// excel文件
				try {
					long size = excelData.getSize();
					if (size > 0) {
						if (size > MAXSIZE) {
							result = UIView("redirect:insertRePayroll");
							result.addObject("msg", "文件大小超过最大限度");
							return result;
						} else {
							String fileName = excelData.getOriginalFilename();// 获取文件名
							String sname = fileName.substring(fileName.lastIndexOf("."));
							//System.out.println("excel后缀：" + sname);
							if (".xls".equalsIgnoreCase(sname) || ".xlsx".equalsIgnoreCase(sname)) {
								String filePath = Constant.UPLOAD_DIRECTORY + getSession().getAttribute("managerId")
										+ "/" + DateUtil.getCurrentDate("yyyyMMdd") + "/";
								// 不存在文件夹创建文件夹
								Utility.makeDirectory(filePath);
								File file = new File(filePath + fileName);
								// System.out.println("size:" + size);
								FileCopyUtils.copy(excelData.getBytes(), file);// 文件上传后存放的位置
								excelresult = ExcelUtil.readExcel(file);
								list = TableHeaderUtil.getReTableHeader(excelresult);
							} else {
								result = UIView("redirect:insertRePayroll");
								result.addObject("msg", "请上传相对应文件");
								return result;
							}
						}

					} else {
						result = UIView("redirect:insertRePayroll");
						result.addObject("msg", "请上传相对应文件");
						return result;
					}

				} catch (IOException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			} else {
				result = UIView("redirect:insertRePayroll");
				result.addObject("msg", "请上传相对应文件");
				return result;
			}

			// 插入日志，reparent，reheader，reheaderValue，reTotal
			try {
				reParentService.insert_logs_reparent_reheader_reheaderValue_total(logFormMap, parentFormMap, list,
						excelresult);
				result = UIView("redirect:insertRePayroll");// 补发工资单插入成功
				result.addObject("msg", "操作成功");
			} catch (Exception e) {
				e.printStackTrace();
				result = UIView("redirect:insertRePayroll");
				result.addObject("msg", "操作失败");
				return result;
			}
		} 
		//else {
			//result = UIView("redirect:insertRePayroll");
			//result.addObject("msg", "请先新增主工资单");
		//}
		// 返回ModelAndView
		return result;
	}

	// 新增主工资单
	@RequestMapping(value = "/uploadSpringMvcSingleFile", method = RequestMethod.POST)
	public ModelAndView uploadSingleFile(HttpServletRequest request, @RequestParam("excelData") MultipartFile excelData)
			throws Exception {
		ModelAndView result = null;
		// 判断提交方式
		if (!"POST".equals(request.getMethod())) {
			result = UIView("redirect:insertPayroll");
			result.addObject("msg", "提交方式有误");
			return result;
		}
		// parentFormMap用于存parent表(最基本的工资单的信息表)的数据，有request传过来的wages_year和wages_month
		FormMap parentFormMap = getFormMap();// 已经有request传过来的wages_year和wages_month
		// 拿到人力的id号,可以得到是哪个人在插入工资单
		parentFormMap.put("createBy", getSession().getAttribute("managerId"));
		// System.out.println("session_managerId=="+SecurityUtils.getSubject().getSession().getAttribute("managerId"));
		// 判断表单年月是否选中
		if (Integer.valueOf(parentFormMap.getStr("wagesYear")) < 0) {
			result = UIView("redirect:insertPayroll");
			result.addObject("msg", "请选择年份");
			return result;
		}
		if (Integer.valueOf(parentFormMap.getStr("wagesMonth")) < 0) {
			result = UIView("redirect:insertPayroll");
			result.addObject("msg", "请选择月份");
			return result;
		}

		// logFormMap用于数据库日志表log_logs的信息插入
		FormMap logFormMap = new FormMap();
		// logFormMap加数据用于插入数据库日志表log_logs
		logFormMap.put("logLevel", 0);
		logFormMap.put("content", getSession().getAttribute("userName") + "执行新增" + parentFormMap.getStr("wagesYear")
				+ "年" + parentFormMap.getStr("wagesMonth") + "月" + "主工资单");
		logFormMap.put("recordTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		logFormMap.put("logName",
				"新增" + parentFormMap.getStr("wagesYear") + "年" + parentFormMap.getStr("wagesMonth") + "月" + "主工资单");

		// 用于存表头的字段的名，例如序号，姓名，部门这样
		ArrayList<String> list = null;
		// 解析工资表表后得到的对象
		ArrayList<ArrayList<Object>> excelresult = null;
		logger.info("操作excel插入");
		// 判断上传Excel表是否正确
		if (!StringUtil.isEmpty(excelData)) {// excel文件
			try {
				long size = excelData.getSize();// 文件大小
				if (size > 0) {
					if (size > MAXSIZE) {
						result = UIView("redirect:insertPayroll");
						result.addObject("msg", "文件大小超过最大限度");
						return result;
					} else {
						String fileName = excelData.getOriginalFilename();// 获取文件名
						String sname = fileName.substring(fileName.lastIndexOf("."));// 获取后缀
						if (".xls".equalsIgnoreCase(sname) || ".xlsx".equalsIgnoreCase(sname)) {
							String filePath = Constant.UPLOAD_DIRECTORY + getSession().getAttribute("managerId") + "/"
									+ DateUtil.getCurrentDate("yyyyMMdd") + "/";
							// 不存在文件夹创建文件夹
							Utility.makeDirectory(filePath);
							File file = new File(filePath + fileName);
							// System.out.println("size:" + size);
							FileCopyUtils.copy(excelData.getBytes(), file);// 文件上传后存放的位置
							excelresult = ExcelUtil.readExcel(file);
							list = TableHeaderUtil.getMainTableHeader(excelresult);
						} else {
							result = UIView("redirect:insertPayroll");
							result.addObject("msg", "请上传相对应文件");
							return result;
						}
					}
				} else {
					result = UIView("redirect:insertPayroll");
					result.addObject("msg", "请上传相对应文件");
					return result;
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		} else {
			result = UIView("redirect:insertPayroll");
			result.addObject("msg", "请上传相对应文件");
			return result;
		}

		// 插入日志，parent，header，headerValue，mainTotal
		try {
			parentService.insert_logs_parent_header_headerValue_total(logFormMap, parentFormMap, list, excelresult);
			result = UIView("redirect:insertPayroll");// 主工资单插入成功
			result.addObject("msg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = UIView("redirect:insertPayroll");
			result.addObject("msg", "操作失败");
			return result;
		}
		// 返回ModelAndView
		return result;
	}

	// 点击 新增主工资单 执行的控制器
	@RequestMapping("/insertPayroll")
	public ModelAndView insertPayroll(HttpServletRequest request) {
		ModelAndView result = UIView("/center/wages/selectCategory");
		result.addObject("msg", request.getParameter("msg"));
		return result;
	}

	// 点击 新增补发工资单 执行的控制器
	@RequestMapping("/insertRePayroll")
	public ModelAndView insertRePayroll(HttpServletRequest request) {
		ModelAndView result = UIView("/center/wages/selectReCategory");
		result.addObject("msg", request.getParameter("msg"));
		return result;
	}

	// 点击 查看主工资单 执行的控制器
	@RequestMapping("/checkPayroll")
	public ModelAndView checkPayroll(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = UIView("/center/wages/checkPayroll");
		FormMap formMap = getFormMap();
		// 开启分页
		PageHelper.startPage(formMap.getPage(), formMap.getRows());
		String type = (String) getSession().getAttribute("type");
		if (!StringUtil.isEmpty(type)) {
			if (MANAGERTYPE2.equals(type)) {// 2员工，是员工那么只查到自己的，否则可以查到所有的
				formMap.put("managerName", getSession().getAttribute("accountName"));// 身份证
			}
		}
		List<E> list = parentService.selectWagesParent(formMap);
		// 绑定分页返回
		result.addObject("pageInfo", new PageInfo<E>(list));
		// 绑定上一次参数
		result.addObject("queryParam", formMap);
		result.addObject("msg", request.getParameter("msg"));
		// 添加查看主工资单日志
		FormMap logFormMap = new FormMap();
		logFormMap.put("logLevel", 0);
		logFormMap.put("content", getSession().getAttribute("userName") + "查看主工资单");
		logFormMap.put("recordTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		logFormMap.put("logName", "查看主工资单");
		// 插入日志表
		logService.insert(logFormMap);
		return result;
	}

	// 点击 查看补发工资单 执行的控制器
	@RequestMapping("/checkRePayroll")
	public ModelAndView checkRePayroll(HttpServletRequest request) {
		ModelAndView result = UIView("/center/wages/checkRePayroll");
		FormMap formMap = getFormMap();
		// 开启分页
		PageHelper.startPage(formMap.getPage(), formMap.getRows());
		String type = (String) getSession().getAttribute("type");
		if (!StringUtil.isEmpty(type)) {
			if (MANAGERTYPE2.equals(type)) {// 2员工，是员工那么只查到自己的，否则可以查到所有的
				formMap.put("managerName", getSession().getAttribute("accountName"));// 身份证
			}
		}
		List<E> list = reParentService.selectWagesParent(formMap);
		// 绑定分页返回
		result.addObject("pageInfo", new PageInfo<E>(list));
		// 绑定上一次参数
		result.addObject("queryParam", formMap);
		result.addObject("msg", request.getParameter("msg"));
		// 添加查看补发工资单日志
		FormMap logFormMap = new FormMap();
		logFormMap.put("logLevel", 0);
		logFormMap.put("content", getSession().getAttribute("userName") + "查看补发工资单");
		logFormMap.put("recordTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		logFormMap.put("logName", "查看补发工资单");
		// 插入日志表
		logService.insert(logFormMap);
		return result;
	}

	// 查看主工资单合计
	@RequestMapping("/checkPayrollTotal")
	public ModelAndView checkPayrollTotal(HttpServletRequest request) throws Exception {
		ModelAndView result = UIView("/center/wages/payrollTotal");
		FormMap formMap = getFormMap();
		List<E> list = totalService.selectMainTotal(formMap);
		// 绑定分页返回
		result.addObject("pageInfo", new PageInfo<E>(list));
		// 绑定上一次参数
		result.addObject("queryParam", formMap);
		result.addObject("msg", request.getParameter("msg"));
		// 添加查看主工资单合计日志
		FormMap logFormMap = new FormMap();
		logFormMap.put("logLevel", 0);
		logFormMap.put("content", getSession().getAttribute("userName") + "查看主工资单合计");
		logFormMap.put("recordTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		logFormMap.put("logName", "查看主工资单合计");
		// 插入日志表
		logService.insert(logFormMap);
		return result;
	}

	// 查看补发工资单合计
	@RequestMapping("/checkRePayrollTotal")
	public ModelAndView checkRePayrollTotal(HttpServletRequest request) throws Exception {
		ModelAndView result = UIView("/center/wages/rePayrollTotal");
		FormMap formMap = getFormMap();
		List<E> list = totalService.selectReTotal(formMap);
		// 绑定分页返回
		result.addObject("pageInfo", new PageInfo<E>(list));
		// 绑定上一次参数
		result.addObject("queryParam", formMap);
		result.addObject("msg", request.getParameter("msg"));
		// 添加查看补发工资单合计日志
		FormMap logFormMap = new FormMap();
		logFormMap.put("logLevel", 0);
		logFormMap.put("content", getSession().getAttribute("userName") + "查看补发工资单合计");
		logFormMap.put("recordTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		logFormMap.put("logName", "查看补发工资单合计");
		// 插入日志表
		logService.insert(logFormMap);
		return result;
	}

	// 点击 主工资单详情 执行的控制器
	@RequestMapping("/checkPayrollDetail")
	public ModelAndView checkPayrollDetail(HttpServletRequest request) {
		ModelAndView result = UIView("/center/wages/payrollDetail");
		FormMap formMap = getFormMap();
		if (!"POST".equals(request.getMethod())) {
			result.addObject("msg", "提交方式有误");
			return result;
		}
		List<E> list = parentService.selectWagesDetail(formMap);
		result.addObject("list", list);// header headerValue salt
		result.addObject("msg", request.getParameter("msg"));
		// 添加查看主工资单详情日志
		FormMap logFormMap = new FormMap();
		logFormMap.put("logLevel", 0);
		logFormMap.put("content", getSession().getAttribute("userName") + "查看主工资单详情");
		logFormMap.put("recordTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		logFormMap.put("logName", "查看主工资单详情");
		// 插入日志表
		logService.insert(logFormMap);
		return result;
	}

	// 点击 补发工资单详情 执行的控制器
	@RequestMapping("/checkRePayrollDetail")
	public ModelAndView checkRePayrollDetail(HttpServletRequest request) {
		ModelAndView result = UIView("/center/wages/rePayrollDetail");
		FormMap formMap = getFormMap();
		if (!"POST".equals(request.getMethod())) {
			result.addObject("msg", "提交方式有误");
			return result;
		}
		List<E> list = reParentService.selectWagesDetail(formMap);
		result.addObject("list", list);// header headerValue salt
		result.addObject("msg", request.getParameter("msg"));
		// 添加查看补发工资单详情日志
		FormMap logFormMap = new FormMap();
		logFormMap.put("logLevel", 0);
		logFormMap.put("content", getSession().getAttribute("userName") + "查看补发工资单详情");
		logFormMap.put("recordTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		logFormMap.put("logName", "查看补发工资单详情");
		// 插入日志表
		logService.insert(logFormMap);
		return result;
	}

	// 删除主工资单
	@RequestMapping("/removeMain")
	public ModelAndView removeMain(HttpServletRequest request) throws Exception {
		ModelAndView result = UIView("redirect:checkPayroll");
		FormMap formMap = getFormMap();
		if (!"POST".equals(request.getMethod())) {
			result.addObject("msg", "提交方式有误");
			return result;
		}
		// 删除主工资单是否选择了年月份
		if (StringUtil.isEmpty(formMap.getStr("wagesYear")) || StringUtil.isEmpty(formMap.getStr("wagesMonth"))) {
			result.addObject("msg", "请选择年月份");
			return result;
		}

		int remove = parentService.remove(formMap);
		if (remove > 0) {
			result.addObject("msg", "删除成功");
			// 添加删除主工资单日志
			FormMap logFormMap = new FormMap();
			logFormMap.put("logLevel", 0);
			logFormMap.put("content", getSession().getAttribute("userName") + "执行删除" + formMap.getStr("wagesYear") + "年"
					+ formMap.getStr("wagesMonth") + "月" + "主工资单");
			logFormMap.put("recordTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			logFormMap.put("logName",
					formMap.getStr("wagesYear") + "年" + formMap.getStr("wagesMonth") + "月" + "主工资单删除");
			logService.insert(logFormMap);
		} else {
			result.addObject("msg", "删除失败");
		}
		return result;
	}

	// 删除补发工资单
	@RequestMapping("/removeRe")
	public ModelAndView removeRe(HttpServletRequest request) throws Exception {
		ModelAndView result = UIView("redirect:checkRePayroll");
		FormMap formMap = getFormMap();
		if (!"POST".equals(request.getMethod())) {
			result.addObject("msg", "提交方式有误");
			return result;
		}
		// 删除补发工资单是否选择了年月份
		if (StringUtil.isEmpty(formMap.getStr("wagesYear")) || StringUtil.isEmpty(formMap.getStr("wagesMonth"))) {
			result.addObject("msg", "请选择年月份");
			return result;
		}

		int remove = reParentService.remove(formMap);
		if (remove > 0) {
			result.addObject("msg", "删除成功");
			// 添加删除补发工资单日志
			FormMap logFormMap = new FormMap();
			logFormMap.put("logLevel", 0);
			logFormMap.put("content", getSession().getAttribute("userName") + "执行删除" + formMap.getStr("wagesYear") + "年"
					+ formMap.getStr("wagesMonth") + "月" + "补发工资单");
			logFormMap.put("recordTime", DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			logFormMap.put("logName",
					formMap.getStr("wagesYear") + "年" + formMap.getStr("wagesMonth") + "月" + "补发工资单删除");
			logService.insert(logFormMap);
		} else {
			result.addObject("msg", "删除失败");
		}
		return result;
	}
}
