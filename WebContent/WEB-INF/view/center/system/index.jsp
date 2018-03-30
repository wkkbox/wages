<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>首页</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<meta name="keywords" content="" />
<!-- .css files -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/bootstrap.min.css"
	type="text/css" media="all" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/style.css"
	type="text/css" media="all" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/wages/index.css"
	type="text/css" media="all" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/font-awesome.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/css/My97DatePickerBeta/bootstrap-datetimepicker.css"
	type="text/css" media="all" />
<!-- //.css files -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/My97DatePickerBeta/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resource/js/bootstrap-datetimepicker.js"></script>
<script>
	//创建屏幕的高度
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

		/* $("#rePayRoll").click(function(){
			 if("${msg }"!="操作成功"){
				 alert("请先新增主工资单");
			 }else if("${msg }"=="操作成功"){
				 $.ajax({
					 	url:"${pageContext.request.contextPath}/center/wagesParent/insertRePayroll",
						data:{},
						dataType:"json",
						type:"post",
						cache:false,
						asynch:true,
						success:function(data){
							
						},
						error:function(){
							alert("服务器异常");
						}
				 });
			 }
		 }); */

	})
	function gopay(i) {
		$("#gopayFrom" + i + "").submit();
	}
	function getId(id) {
		$("#manggid").val(id);
	}
	$(function() {
		var telecomAuditFlag = $("#msg").val()
		if (telecomAuditFlag.length > 0) {
			$('#myModals').modal({
				keyboard : true
			})
			window.setTimeout(closeModel, 3000); /* 3000 */
		}

	});
	function closeModel() {
		$('#myModals').modal('hide');
	}
</script>
</head>
<body>
	<div class="modal fade bs-example-modal-sm" id="confirmMessage"
		tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-sm" role="document">
			<form
				action="${pageContext.request.contextPath}/center/productOrder/delete"
				method="post">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">确定删除吗？</h4>
					</div>
					<div class="modal-body">
						<div class="control-group">
							<div class="controls">
								<input type="hidden" id="manggid" name="id" value="" />
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary tijiao">确定</button>
						<button type="button" class="btn btn-default quxiao"
							data-dismiss="modal">取消</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="modal fade bs-example-modal-sm" id="myModals" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">系统提醒：</h4>
				</div>
				<div class="modal-body">${queryParam.msg }</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="bodys">
		<!--nav-->
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
									data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="true">
									欢迎&nbsp;${userName }&nbsp;进入系统 <span class="caret" style="color: #fff;"></span>
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
							<li><a href="#" class="active">首页</a></li>
							<li style="visibility: hidden;">xx</li>
							<li><a href="#">个人中心</a></li>
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
					<li><a href="${pageContext.request.contextPath}/center/system/manager/skipEditPassword">修改密码</a></li>
					<c:if test="${type==1 }">
						<li><a href="${pageContext.request.contextPath}/center/log/list">日志查看</a></li>
						<li><a href="${pageContext.request.contextPath}/center/system/manager/list">员工管理</a></li>
					</c:if>
				</ul>
			</div>
			<hr>
			<div class="main-container">
				<div class="search-form"></div>
				<div class="main-container">
					<img alt="" style="max-width: 100%; height: auto;" src="${pageContext.request.contextPath}/resource/images/welcome.jpg">
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
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