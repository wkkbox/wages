<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>修改密码</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<meta name="keywords" content="" />
<!-- .css files -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/bootstrap.min.css"
	type="text/css" media="all" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/style.css"
	type="text/css" media="all" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/editPassword.css"
	type="text/css" media="all" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/font-awesome.css" />
<!-- //.css files -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var height1 = $(window).height() - 140;
		var height2 = $(document.body).height();
		if (height2 < height1) {
			//alert(height);
			$("#mains").css("min-height", height1);
			$("#left-container").css("min-height", height1);
		} else {
			//alert(height);
			$("#mains").css("min-height", height2);
			$("#left-container").css("min-height", height2);
		}

	})
	$(function() {
		var telecomAuditFlag = $("#msg").val()
		if (telecomAuditFlag.length > 0) {
			$('#myModals').modal({
				keyboard : true
			})
			window.setTimeout(closeModel, 3000);
		}

	});
	function closeModel() {
		$('#myModals').modal('hide');
	}
	var a = false;
	var b = false;
	var c = false;
	function getPassword() {
		var password = $("#originalPassword").val();
		$
				.ajax({
					url : "${pageContext.request.contextPath}/center/system/manager/getPassword?now="
							+ new Date().getTime(),
					type : "post",
					data : "password=" + password,
					dataType : 'json',
					success : function(result) {
						if (result.code == "1111") {
							alert("提交方式有误");
						} else if (result.code == "0000") {
							if (result.message == "zq") {
								$("#cnspan")
										.html(
												"&nbsp;&nbsp;<span class='fa fa-check' style='color:green' ></span>");
								a = true;
							} else {
								$("#cnspan")
										.html(
												"&nbsp;&nbsp;<span  style='color:red' >输入有误</span>");
							}
						}
					}
				});
	}
	function checkNewpassword() {
		var password = $("#newPassword").val();
		var z = /^[0-9a-zA-Z_@]{6,20}$/;
		if (!z.test(password)) {
			$("#ctspan")
					.html(
							"&nbsp;&nbsp;<span  style='color:red' >密码必须为6-20位数字密码下划线@符号组成</span>");
		} else {
			$("#ctspan")
					.html(
							"&nbsp;&nbsp;<span class='fa fa-check' style='color:green' ></span");
			b = true;
		}
	}
	function checkPassword() {
		var newPassword = $("#newPassword").val();
		var password = $("#confirm-password").val();
		var z = /^[0-9a-zA-Z_@]{6,20}$/;
		if (newPassword != password) {
			$("#onspan").html(
					"&nbsp;&nbsp;<span  style='color:red' >两次密码不匹配</span>");
		} else if (!z.test(password)) {
			$("#onspan")
					.html(
							"&nbsp;&nbsp;<span  style='color:red' >密码必须为6-20位数字密码下划线@符号组成</span>");
		} else if (z.test(password) && newPassword == password) {
			$("#onspan")
					.html(
							"&nbsp;&nbsp;<span class='fa fa-check' style='color:green' ></span");
			c = true;
		}
	}
	function check() {
		if (a && b && c) {
			return true;
		} else {
			getPassword();
			checkNewpassword();
			checkPassword();
			return false;
		}
	}
</script>
</head>
<body>
	<!--nav-->
	<div class="modal fade bs-example-modal-sm" id="myModals" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">系统提醒:</h4>
				</div>
				<div class="modal-body">${msg }</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<%-- 	<a type="button" href="${ctx}/sys/user/info" class="btn btn-primary">
		点击查看
	</a> --%>
				</div>
			</div>
		</div>
	</div>
	<div class="top-main">
		<div class="number">
			<h3>
				<i class="fa fa-phone" aria-hidden="true"></i>
			</h3>
			<div class="clearfix"></div>
		</div>
		<div class="social-icons">
			<div class="form-top">
				<div class="navbar-form navbar-left">
					<div class="form-group">
						<div class="dropdown">
							<p class="dropdown-toggle" id="dropdownMenu1"
								data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
								欢迎&nbsp;${userName }&nbsp;进入系统<span class="caret"
									style="color: #ffffff;"></span>
							</p>
							<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
								<li><a href="${pageContext.request.contextPath}/center/manager/logout">&nbsp;&nbsp;&nbsp;退出系统&nbsp;&nbsp;&nbsp;</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="top-bar">
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="collapse navbar-collapse" id="myNavbar">
					<ul class="nav navbar-nav navbar-right">
						<li><img class="personal-logo" src="${pageContext.request.contextPath}/resource/images/logo.png"></li>
						<li><a href="${pageContext.request.contextPath}/center/system/index">首页</a></li>
						<li style="visibility: hidden;">xx</li>
						<li><a href="#" class="active">个人中心</a></li>
						<li style="visibility: hidden;">xx</li>
						<li><a href="#" class="scroll">消息</a></li>
						<li style="visibility: hidden;">xx</li>
						<li class="clearfix"></li>
					</ul>
				</div>
			</div>
		</nav>
	</div>
	<!--//nav-->
	<!--container-->
	<div class="container-fluid" id="mains">
		<div class="left-container" id="left-container">
			<img src="${pageContext.request.contextPath}/resource/images/logo.jpg">
			<ul>
				<c:if test="${type==0 }">
					<li><a href="${pageContext.request.contextPath}/center/wagesParent/insertPayroll">新增主工资单</a></li>
					<li><a href="${pageContext.request.contextPath}/center/wagesParent/insertRePayroll">新增补发工资单</a></li>
				</c:if>
				<c:if test="${type!=1 }">
					<li><a href="${pageContext.request.contextPath}/center/wagesParent/checkPayroll">查看主工资单</a></li>
					<li><a href="${pageContext.request.contextPath}/center/wagesParent/checkRePayroll">查看补发工资单</a></li>
				</c:if>
				<c:if test="${type==0 }">
					<li><a href="${pageContext.request.contextPath}/center/wagesParent/checkPayrollTotal">主工资单合计</a></li>
					<li><a href="${pageContext.request.contextPath}/center/wagesParent/checkRePayrollTotal">补发工资单合计</a></li>
				</c:if>
				<li><a href="${pageContext.request.contextPath}/center/system/manager/skipEditPassword" style="color: #ffb900;">修改密码</a></li>
				<c:if test="${type==1 }">
					<li><a href="${pageContext.request.contextPath}/center/log/list">日志查看</a></li>
					<li><a href="${pageContext.request.contextPath}/center/system/manager/list">员工管理</a></li>
				</c:if>
			</ul>
		</div>
		<input type="hidden" id="msg" value="${msg }" />
		<hr>
		<div class="main-container">
			<form class="form-horizontal" method="post"
				action="${pageContext.request.contextPath}/center/system/manager/editPassword"
				onsubmit="return check()">
				<div class="form-group rusults">
					<label class="col-sm-6 control-label">修改密码</label>
				</div>
				<div class="borders-info">
					<div class="form-group">
						<label for="company-name" class="col-sm-4 control-label">原始密码:</label>
						<div class="col-sm-4">
							<input type="password" class="form-control input-sm"
								id="originalPassword" placeholder="请输入原始密码"
								onblur="getPassword()">
						</div>
						<div class="col-sm-4" style="margin-top: 7px;" id="cnspan">
							<span style="color: red" id="originalPassword-span"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="company-tel" class="col-sm-4 control-label">新密码:</label>
						<div class="col-sm-4">
							<input type="password" class="form-control input-sm"
								id="newPassword" placeholder="请输入新密码"
								onblur="checkNewpassword()">
						</div>
						<div class="col-sm-4" style="margin-top: 7px;" id="ctspan">
							<span style="color: red"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="operator-name" class="col-sm-4 control-label">确认密码:</label>
						<div class="col-sm-4">
							<input type="password" class="form-control input-sm"
								id="confirm-password" name="password" placeholder="请再次输入密码"
								onblur="checkPassword()">
						</div>
						<div class="col-sm-4" style="margin-top: 7px;" id="onspan">
							<span style="color: red"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="operator-name" class="col-sm-4 control-label">邮箱:</label>
						<div class="col-sm-4">
							<input type="text" class="form-control input-sm" name="mail" placeholder="请输入邮箱">
						</div>
						<div class="col-sm-4" style="margin-top: 7px;" id="onspan">
							<span style="color:red">${msg }</span>
						</div>
					</div>
					<div class="btns">
						<button type="submit" class="btn btn-danger" style="margin-right: 50px;">提&nbsp;&nbsp;&nbsp;交</button>
						<button type="reset" class="btn btn-danger">重&nbsp;&nbsp;&nbsp;置</button>
					</div>
				</div>
			</form>
		</div>
		<div class="clearfix"></div>
	</div>
	<!--//container-->
	<!--footing-->
	<section class="copyright">
		<div class="agileits_copyright text-center">
			<p>Copyright &copy; 2017-2022&nbsp;江西省邮电规划设计院</p>
		</div>
	</section>
	<!--//footing-->
</body>
</html>