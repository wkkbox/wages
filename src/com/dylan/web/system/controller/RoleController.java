package com.dylan.web.system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dylan.core.controller.TenantController;
import com.dylan.core.entity.E;
import com.dylan.core.entity.FormMap;
import com.dylan.core.view.Message;
import com.dylan.web.system.service.PermissionService;
import com.dylan.web.system.service.ResourceService;
import com.dylan.web.system.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/system/role")
public class RoleController extends TenantController {
	@Resource
	private RoleService roleService;
	@Resource
	private ResourceService resourceService;
    @Resource
    private PermissionService permissionService;
	
	private String page_list = "/center/system/role";

	private String redirect_list = "redirect:list";
	
    /**
     * 角色视图
     */
    @RequestMapping("/list")
    public ModelAndView list() {
    	FormMap formMap=getFormMap();
    	PageHelper.startPage(formMap.getPage(), formMap.getRows());
    	List<E> list=roleService.selectList(formMap);
    	
    	List<E> resultMenuList=resourceService.selectList(null);
        List<E> menuList = new ArrayList<E>();
        for (E menu : resultMenuList) {
        	if(menu.getInt("parentId")==-1){
        		menuList.add(menu);
        	}
        }
        for (E menu : menuList) {
            menu.set("children", getChild(menu.getInt("resourceId"), resultMenuList));
        }
    	
        ModelAndView result = UIView(page_list);
        result.addObject("pageInfo", new PageInfo<E>(list));
        result.addObject("queryParam", formMap);
        result.addObject("menuList", menuList);
        return result;
    }
    
    @ResponseBody
    @RequestMapping("/view")
    public String view() {
    	FormMap formMap=getFormMap();
    	E e=roleService.selectById(formMap);
        return toJsonApi(e);
    }
    
    @RequestMapping("/save")
    public ModelAndView save() {
    	ModelAndView result = UIView(redirect_list, Message.INFO);
    	FormMap formMap=getFormMap();
        if (StringUtils.isEmpty(formMap.getStr("roleId"))) {
        	roleService.insert(formMap);
        } else {
        	roleService.edit(formMap);
        }
        return result;
    }
    
    @RequestMapping("/authorize")
    public ModelAndView authorize() { 
    	FormMap formMap=getFormMap(); 
    	
    	List<E> roles=new ArrayList<E>();
    	E re=new E();
    	re.put("roleId", formMap.getInt("roleId"));
    	roles.add(re);	
    	//当前角色拥有的菜单和操作权限	
        List<E> resourcesList=roleService.selectResourceByRoles(roles);
        List<E> permissionsList=permissionService.selectPermissionsByRoles(roles);
        
    	List<E> resultMenuList=resourceService.selectList(null);
    	List<E> allPermList=permissionService.selectPermissionsByRoles(null);
        List<E> menuList = new ArrayList<E>();
        for (E menu : resultMenuList) {
        	if(menu.getInt("parentId")==-1){
        		menuList.add(menu);
        	}
        }
        for (E menu : menuList) {
            menu.set("children", getChild(menu.getInt("resourceId"), resultMenuList));
        }
        
        ModelAndView result = UIView("/center/system/authorize"); 
        //绑定上一次参数
        result.addObject("queryParam", formMap);
        result.addObject("menuList", menuList);
        result.addObject("allPermList", allPermList);
        result.addObject("resourcesList", resourcesList);
        result.addObject("permissionsList", permissionsList);
        return result;
    }
    
    @RequestMapping("/saveAuthorize")
    public ModelAndView saveAuthorize() {
    	ModelAndView result = UIView(redirect_list, Message.INFO);
    	FormMap formMap=getFormMap();
    	roleService.authorize(formMap, getRequest());
        return result;
    }
    
    @RequestMapping("/remove")
    public ModelAndView remove() {
    	ModelAndView result = UIView(redirect_list, Message.DANGER);
    	FormMap formMap=getFormMap();
    	String idStr = formMap.getStr("idStr");
    	if(null == idStr || idStr.equals("")){
    		roleService.remove(formMap);
    	}
    	else{
    		String[] ids = idStr.split(",");
    		for(int i = 0 ; i < ids.length ; i ++){
    			formMap.put("roleId", ids[i]);
    			roleService.remove(formMap);
    		}
    	}  	
        return result;
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