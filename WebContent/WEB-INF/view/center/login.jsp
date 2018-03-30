<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.dylan.core.util.BASE64Util" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录界面</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,user-scalable=no">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/bootstrap.css">
<link
	href="${pageContext.request.contextPath}/resource/iconfont/style.css"
	type="text/css" rel="stylesheet">
<style>
body {
	color: #fff;
	font-family: "微软雅黑";
	font-size: 14px;
}

.wrap1 {
	position: absolute;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	margin: auto
} /*把整个屏幕真正撑开--而且能自己实现居中*/
.main_content {
	background:
		url(${pageContext.request.contextPath}/resource/images/main_bg.png)
		repeat;
	margin-left: auto;
	margin-right: auto;
	text-align: left;
	float: none;
	border-radius: 8px;
}

.form-group {
	position: relative;
}

.login_btn {
	display: block;
	background: #3872f6;
	color: #fff;
	font-size: 15px;
	width: 100%;
	line-height: 50px;
	border-radius: 3px;
	border: none;
}

.login_input {
	width: 100%;
	border: 1px solid #3872f6;
	border-radius: 3px;
	line-height: 40px;
	padding: 2px 5px 2px 30px;
	background: none;
	height: 46px;
}

.icon_font {
	position: absolute;
	bottom: 15px;
	left: 10px;
	font-size: 18px;
	color: #3872f6;
}

.font16 {
	font-size: 16px;
}

.mg-t20 {
	margin-top: 20px;
}

@media ( min-width :200px) {
	.pd-xs-20 {
		padding: 20px;
	}
}

@media ( min-width :768px) {
	.pd-sm-50 {
		padding: 50px;
	}
}

#grad {
	background: -webkit-linear-gradient(#4990c1, #52a3d2, #6186a3);
	/* Safari 5.1 - 6.0 */
	background: -o-linear-gradient(#4990c1, #52a3d2, #6186a3);
	/* Opera 11.1 - 12.0 */
	background: -moz-linear-gradient(#4990c1, #52a3d2, #6186a3);
	/* Firefox 3.6 - 15 */
	background: linear-gradient(#4990c1, #52a3d2, #6186a3); /* 标准的语法 */
}
</style>
</head>

<body style="background:url(${pageContext.request.contextPath}/resource/images/bg.jpg) no-repeat;">
	<%
		//记住我
		//取出cookie
		Cookie[] cookies = request.getCookies();
		pageContext.setAttribute("rememberMe", "off");
		String name = "";
		String pwd = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("name".equals(cookie.getName())) {
					name = cookie.getValue();
				}
				if ("pwd".equals(cookie.getName())) {
					//解密
					try{
						pwd = BASE64Util.decrypt(cookie.getValue(), "279ki954");
					}catch (Exception e){
						e.printStackTrace();
					}
				}
				if("rememberMe".equals(cookie.getName())){
					pageContext.setAttribute("rememberMe", "on");
				}
			}
		}
		//把用户名和密码存储到page域对象，然后设置到input框上
		//没有记住我那么cookie没有，还是""，选中过记住我那么会得到cookie的name和pwd
		pageContext.setAttribute("name", name);
		pageContext.setAttribute("pwd", pwd);
	%>
	<div class="container wrap1" style="height: 450px;">
		<h2 class="mg-b20 text-center">软件中心工资管理系统</h2>
		<div class="col-sm-8 col-md-5 center-auto pd-sm-50 pd-xs-20 main_content">
			<p class="text-center font16">用户登录</p>
			<form action="${pageContext.request.contextPath}/center/manager/dologin" method="post">
				<div class="form-group mg-t20">
					<input type="text" class="login_input" name="accountName" value="${pageScope.name}" placeholder="请输入用户名" />
				</div>
				<div class="form-group mg-t20">
					<input type="password" class="login_input" id="Password1" name="password" value="${pageScope.pwd}" placeholder="请输入密码" />
				</div>
				<div class="checkbox mg-b25">
					<label>
						<input ${pageScope.rememberMe=="on" ? " checked = 'checked' " : " " } type="checkbox" name="rememberMe" value="on" id="rememberMe" /> 记住我
					</label>
					<label> 
						<font style="color: red;">${msg }</font>
					</label>
				</div>
				<input type="submit" value="登   录" class="login_btn" />
			</form>
		</div>
		<!--row end-->
	</div>
	<!--container end-->
	<div style="text-align: center;"></div>
</body>
</html>