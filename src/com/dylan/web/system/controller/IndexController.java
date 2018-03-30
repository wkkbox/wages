
package com.dylan.web.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dylan.core.controller.TenantController;

@Controller
public class IndexController extends TenantController {
	
	@RequestMapping("/system/index")
	public String index(){
		//System.out.println("index");
		return "center/system/index";
	}
}

	