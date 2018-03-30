package com.dylan.web.system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dylan.core.controller.TenantController;
import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.view.Message;
import com.dylan.web.system.service.ResourceService;
import com.dylan.web.system.service.RoleService;


@Controller
@RequestMapping("/system/resource")
public class ResourceController extends TenantController{
	
	@Resource
	private RoleService roleService;
	@Resource
	private ResourceService resourceService;
	
	private String page_list = "/center/system/resource";
	private String redirect_index = "redirect:index";
	@RequestMapping("/index")
    public String index(HttpServletRequest request) {
        return page_list;
    }
	
	@ResponseBody
    @RequestMapping("/list")
    public String list() {
    	FormMap formMap=getFormMap();
        List<E> list=resourceService.selectList(formMap);
        List<E> menuList = new ArrayList<E>();
        for (E menu : list) {
        	if(menu.getInt("parentId")==-1){
        		menuList.add(menu);
        	}
        }
        for (E menu : menuList) {
            menu.set("children", getChild(menu.getInt("resourceId"), list));
        }
        return toJsonApi(menuList);
    }
	@ResponseBody
    @RequestMapping("/view")
    public String view() {
    	FormMap formMap=getFormMap();
        E e=resourceService.selectById(formMap);
        return toJsonApi(e);
    }
	
    @RequestMapping("/save")
    public ModelAndView insert() {
    	ModelAndView view=UIView(redirect_index);
    	FormMap formMap=getFormMap();
    	if(StringUtils.isEmpty(formMap.getStr("resourceId"))){
    		resourceService.insert(formMap);
    	}else{
    		resourceService.edit(formMap);
    	}
        return view;
    }
    
    @RequestMapping("/remove")
    public ModelAndView remove() {
    	ModelAndView view=UIView(redirect_index,Message.DANGER);
    	FormMap formMap=getFormMap();
    	resourceService.remove(formMap);
        return view;
    }
   
    private List<E> getChild(int resourceId, List<E> rootMenu) {
	    List<E> childList = new ArrayList<E>();
	    for (E e : rootMenu) {
	    	if(e.getInt("parentId")!=-1){
	    		if (e.getInt("parentId").equals(resourceId)) {
		            childList.add(e);
		        }
	    	}
	    }
	    for (E e : childList) {
	        e.set("children", getChild(e.getInt("resourceId"), rootMenu));
	    } 
	    if (childList.size() == 0) {
	        return new ArrayList<E>();
	    }
	    return childList;
	}
    
}