package com.dylan.core.resolver;

import java.net.ConnectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

public class MyExceptionResolver implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		System.out.println("resolveException");
		// TODO Auto-generated method stub
		ModelAndView mv = null;
		// 如果是shiro无权操作，因为shiro 在操作auno等一部分不进行转发至无权限url
		if (ex instanceof AuthorizationException) {
			mv = new ModelAndView("/center/unauthorizedUrl");
			return mv;
		} else if (ex instanceof ConnectException) {
			mv = new ModelAndView("/center/unauthorizedUrl");
			return mv;
		} else if (ex instanceof CommunicationsException) {
			mv = new ModelAndView("/center/unauthorizedUrl");
			return mv;
		}
		mv = new ModelAndView("center/error/error");
		ex.printStackTrace();
		return mv;
	}

}
