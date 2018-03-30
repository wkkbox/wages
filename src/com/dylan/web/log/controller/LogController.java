package com.dylan.web.log.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.dylan.core.controller.TenantController;
import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.web.log.service.LogService;



/**
 * 
* @ClassName: LogController 
* @Description: 日志控制器
* @author lcm
* @date 2017年2月18日 下午11:05:32 
*
 */
@Controller
@RequestMapping(value = "/log")
public class LogController extends TenantController{

	@Resource
	private LogService logService;
   
	private String page_list = "/center/logs/logs";

	//private String redirect_list = "redirect:list";
	 /**
	  * 
	 * @Title: log 
	 * @Description:日志列表查询
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	  */
	
    @RequestMapping("/list")
    public ModelAndView log(ModelAndView view) {
    	//获取请求参数
    	FormMap formMap=getFormMap();
    	//开启分页
    	PageHelper.startPage(formMap.getPage(), formMap.getRows());
    	//查询展示数量
    	List<E> list=logService.selectList(formMap);
    	//System.out.println(list.size()+"发现查询的集合并不是所有，而是按照数量查询出来展示，所以不影响性能!");
        //返回到list页面
        ModelAndView result = UIView(page_list);
        //绑定分页返回
        result.addObject("pageInfo", new PageInfo<E>(list));
        //绑定上一次参数
        result.addObject("queryParam", formMap);
        return result;
    }

   

    
}
