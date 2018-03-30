<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>新增主工资单</title>
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
	href="${pageContext.request.contextPath}/resource/wages/personal-orderstyle.css"
	type="text/css" media="all" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/font-awesome.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resource/personal-companyinfo/style.css"
	type="text/css" media="all" />
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
			window.setTimeout(closeModel, 3000);
		}

	});
	function closeModel() {
		$('#myModals').modal('hide');
	}

</script>
</head>
<body>
	<div class="bodys">
		<div class="modal fade bs-example-modal-sm" id="myModals"
			tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
			aria-hidden="true">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="myModalLabel">系统提醒：</h4>
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
		<div class="modal fade bs-example-modal-lg" id="updateUserAddr"
			tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content" id="contantDetail">
					<div class="modal-header" style="background-color: #333">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" style="color: white;" id="myModalLabel">系统正在加载中。。。。</h4>
					</div>
				</div>
			</div>
		</div>
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
									欢迎&nbsp;${userName }&nbsp;进入系统<span class="caret" style="color: #fff;"></span>
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
						<li><a href="${pageContext.request.contextPath}/center/wagesParent/insertPayroll" style="color: #ffb900;">新增主工资单</a></li>
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
				<form id="addWagesForm" class="form-horizontal" method="post"
					enctype="multipart/form-data"
					action="${pageContext.request.contextPath}/center/wagesParent/uploadSpringMvcSingleFile">
					<div class="form-group rusults">
						<label for="company-name" class="col-sm-6 control-label">新增<font color="red">主</font>工资单</label>
					</div>
					<div class="borders-info">
						<div class="form-group">
							<label for="id-type" class="col-sm-4 control-label"><span
								style="color: red; margin-right: 5px;" id="itspan1s">*</span>主工资单年份:</label>
							<div class="col-sm-4 selects">
								<select name="wagesYear" class="form-control input-sm"
									id="id-type" class="form-control">
									<option value="-1">-------------------请选择-------------------</option>
									<option value="2018">2018年</option>
									<option value="2019">2019年</option>
									<option value="2020">2020年</option>
									<option value="2021">2021年</option>
									<option value="2022">2023年</option>
									<option value="2023">2022年</option>
									<option value="2024">2024年</option>
									<option value="2025">2025年</option>
									<option value="2025">2025年</option>
									<option value="2026">2026年</option>
									<option value="2027">2027年</option>
									<option value="2028">2028年</option>
									<option value="2029">2029年</option>
									<option value="2030">2030年</option>
								</select>
							</div>
							<div class="col-sm-4" style="margin-top: 7px;" id="itspan1">
								<span style="color: red"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="id-type" class="col-sm-4 control-label"><span
								style="color: red; margin-right: 5px;" id="itspan1s">*</span>主工资单月份:</label>
							<div class="col-sm-4 selects">
								<select name="wagesMonth" class="form-control input-sm"
									id="id-type" class="form-control">
									<option value="-1">-------------------请选择-------------------</option>
									<option value="1">1月</option>
									<option value="2">2月</option>
									<option value="3">3月</option>
									<option value="4">4月</option>
									<option value="5">5月</option>
									<option value="6">6月</option>
									<option value="7">7月</option>
									<option value="8">8月</option>
									<option value="9">9月</option>
									<option value="10">10月</option>
									<option value="11">11月</option>
									<option value="12">12月</option>
								</select>
							</div>
							<div class="col-sm-4" style="margin-top: 7px;" id="itspan1">
								<span style="color: red"></span>
							</div>
						</div>
						<div class="form-group">
							<input type="hidden" id="msg" value="${msg }" /> <label
								for="operator-id" class="col-sm-4 control-label"><span
								style="color: red; margin-right: 5px;" id="oispans">*</span>Excel文件:</label>
							<div class="col-sm-4">
								<input type="file" style="margin-left: 10PX" name="excelData">
							</div>
							<div class="col-sm-4" style="margin-top: 7px;" id="oispan">
								<span style="color: red">${msg }</span>
							</div>
						</div>
						<div class="btns">
							<button type="submit" class="btn btn-danger"
								style="margin-right: 50px;" data-toggle='modal'
								data-target='#updateUserAddr'>提&nbsp;&nbsp;&nbsp;交</button>
							<button type="reset" class="btn btn-danger">重&nbsp;&nbsp;&nbsp;置</button>
						</div>
					</div>
				</form>
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