package com.dylan.web.wages.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.dylan.core.controller.TenantController;
import com.dylan.web.wages.service.WagesHeaderService;

@Controller
@RequestMapping("/wagesHeader")
public class WagesHeaderController extends TenantController {

	// private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private WagesHeaderService wagesHeaderService;

}
