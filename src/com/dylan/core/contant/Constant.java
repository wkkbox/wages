package com.dylan.core.contant;

import com.dylan.core.config.Global;

public class Constant {
	
	//private static final ResourceBundle rb = ResourceBundle.getBundle("config",Locale.CHINESE);
	
	public static final String EQUAL = "=";
	public static final String SEMICOLON = ";";
	public static final String FILE_NAME = "filename";
	public static final String CONTENT_DISPOSITION = "content-disposition";

	//servlet 3.x upload config
	//上传文件保存路径,默认"",@see ServletContext.TEMPDIR
	public static final String UPLOAD_DIRECTORY = Global
			.getConfig("excel_addr");
	//用户使用路径
	public static final String USER_UPLOAD_DIRECTORY = Global
			.getConfig("user_image_addr");

	//允许上传文件大小的最大值,默认为-1,即没有限制,long类型
	public static final long MAX_FILE_SIZE = 1024 * 1024 * 2000;
	
	//对multipart/form-data请求的最大数据量,默认-1,即没有限制,long类型
	public static final long MAX_REQUEST_SIZE = 1024 * 1024 * 10000;
	
	//当数据两大于该阈值,内容将被写入文件,默认为 0,int类型
	public static final int FILE_SIZE_THRESHOLD = 0;
	
	public static final int BUFFER_SIZE = 1024 * 100;
	
	public static final String SPRING_SERVLET_NAME = "dispatcher";
	
	public static final String VIEW_RESOLVER_SUFFIX = ".jsp";
	public static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/view/";
	
	public static final String RESOURCE_HANDLER_STATIC = "/static/**";
	public static final String RESOURCE_LOCATION_STATIC = "/static/";
	
	public static final String BASE_PACKAGE_CONTROLLER = "com.ifly.upload.controller";
	public static final String BASE_PACKAGE_SPRINGMVC_CONFIG = "com.ifly.upload.config";
	
}
