<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>补发工资单合计</title>
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
    $(document).ready(function(){
    	var height1=$(window).height()-140;
        var height2=$(document.body).height();
        if(height2<height1){
        	//alert(height);
            $("#mains").css("min-height",height1);
            $("#left-container").css("min-height",height1);
        }else{
        	//alert(height);
            $("#mains").css("min-height",height2);
            $("#left-container").css("min-height",height2);
        }
        
    })
        function gopay(i){
        	$("#gopayFrom"+i+"").submit();
        }
        function getId(id){
        	$("#manggid").val(id);
        }
        $(function () {
       	   var telecomAuditFlag= $("#msg").val()
       	   if(telecomAuditFlag.length>0){
       		   $('#myModals').modal({
       			      keyboard: true
       			   }) 
       			 window.setTimeout(closeModel,3000); 
       	   }
       	
          });
         function closeModel(){
         	$('#myModals').modal('hide');
         }
    </script>
</head>
<body>
	<div class="modal fade bs-example-modal-sm" id="myModals" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
					<h4 class="modal-title" style="color: white;" id="myModalLabel">删除操作</h4>
				</div>
				<form
					action="${pageContext.request.contextPath}/center/wagesParent/removeMain"
					method="post" id="updact_form">
					<input type="hidden" name="addressId" id="addressId" value="" />
					<div class="modal-body form-horizontal">
						<div class="form-group">
							<label for="order-num" class="col-sm-4 control-label">选择年:</label>
							<div class="col-sm-4 selects">
								<div data-toggle="distpicker">
									<select name="wagesYear" class="form-control input-sm"
										id="id-type" class="form-control">
										<option value="-1">-----------------请选择-----------------</option>
										<option value="2018">2018年</option>
										<option value="2019">2019年</option>
										<option value="2020">2020年</option>
										<option value="2021">2021年</option>
										<option value="2022">2022年</option>
										<option value="2023">2023年</option>
										<option value="2024">2024年</option>
										<option value="2025">2025年</option>
										<option value="2026">2026年</option>
										<option value="2027">2027年</option>
										<option value="2028">2028年</option>
										<option value="2029">2029年</option>
										<option value="2030">2030年</option>
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="order-num" class="col-sm-4 control-label">选择月份:</label>
							<div class="col-sm-4 selects">
								<div data-toggle="distpicker">
									<select name="wagesMonth" class="form-control input-sm"
										id="id-type" class="form-control">
										<option value="-1">-----------------请选择-----------------</option>
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
							</div>
						</div>
						<div class="btns">
							<button type="submit" class="btn btn-danger"
								style="margin-right: 50px;">删除</button>
							<button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
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
						<li><a href="${pageContext.request.contextPath}/center/wagesParent/insertPayroll">新增主工资单</a></li>
						<li><a href="${pageContext.request.contextPath}/center/wagesParent/insertRePayroll">新增补发工资单</a></li>
					</c:if>
					<c:if test="${type!=1 }">
						<li><a href="${pageContext.request.contextPath}/center/wagesParent/checkPayroll">查看主工资单</a></li>
						<li><a href="${pageContext.request.contextPath}/center/wagesParent/checkRePayroll">查看补发工资单</a></li>
					</c:if>
					<c:if test="${type==0 }">
						<li><a href="${pageContext.request.contextPath}/center/wagesParent/checkPayrollTotal">主工资单合计</a></li>
						<li><a href="${pageContext.request.contextPath}/center/wagesParent/checkRePayrollTotal" style="color: #ffb900;">补发工资单合计</a></li>
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
				<div class="search-form">
					<c:if test="${type==0 }">
						<form class="form-inline"
							action="${pageContext.request.contextPath}/center/wagesParent/checkRePayrollTotal"
							method="post">
							<div class="form-group">
								<label for="product-num">补发工资单合计&nbsp;&nbsp;</label>
								<label for="product-num">&nbsp;年份:</label>
								<select name="year" class="form-control input-sm" id="id-type" class="form-control">
									<option value="">请选择</option>
									<option value="2018" ${queryParam.year=="2018" ? " selected = 'selected' " : " " }>2018年</option>
									<option value="2019" ${queryParam.year=="2019" ? " selected = 'selected' " : " " }>2019年</option>
									<option value="2020" ${queryParam.year=="2020" ? " selected = 'selected' " : " " }>2020年</option>
									<option value="2021" ${queryParam.year=="2021" ? " selected = 'selected' " : " " }>2021年</option>
									<option value="2022" ${queryParam.year=="2022" ? " selected = 'selected' " : " " }>2022年</option>
									<option value="2023" ${queryParam.year=="2023" ? " selected = 'selected' " : " " }>2023年</option>
									<option value="2024" ${queryParam.year=="2024" ? " selected = 'selected' " : " " }>2024年</option>
									<option value="2025" ${queryParam.year=="2025" ? " selected = 'selected' " : " " }>2025年</option>
									<option value="2026" ${queryParam.year=="2026" ? " selected = 'selected' " : " " }>2026年</option>
									<option value="2027" ${queryParam.year=="2027" ? " selected = 'selected' " : " " }>2027年</option>
									<option value="2028" ${queryParam.year=="2028" ? " selected = 'selected' " : " " }>2028年</option>
									<option value="2029" ${queryParam.year=="2029" ? " selected = 'selected' " : " " }>2029年</option>
									<option value="2030" ${queryParam.year=="2030" ? " selected = 'selected' " : " " }>2030年</option>
								</select>
								<label for="product-num">&nbsp;月份:</label>
								<select name="month" class="form-control input-sm" id="id-type" class="form-control">
									<option value="">请选择</option>
									<option value="1" ${queryParam.month=="1" ? " selected = 'selected' " : " " }>1月</option>
									<option value="2" ${queryParam.month=="2" ? " selected = 'selected' " : " " }>2月</option>
									<option value="3" ${queryParam.month=="3" ? " selected = 'selected' " : " " }>3月</option>
									<option value="4" ${queryParam.month=="4" ? " selected = 'selected' " : " " }>4月</option>
									<option value="5" ${queryParam.month=="5" ? " selected = 'selected' " : " " }>5月</option>
									<option value="6" ${queryParam.month=="6" ? " selected = 'selected' " : " " }>6月</option>
									<option value="7" ${queryParam.month=="7" ? " selected = 'selected' " : " " }>7月</option>
									<option value="8" ${queryParam.month=="8" ? " selected = 'selected' " : " " }>8月</option>
									<option value="9" ${queryParam.month=="9" ? " selected = 'selected' " : " " }>9月</option>
									<option value="10" ${queryParam.month=="10" ? " selected = 'selected' " : " " }>10月</option>
									<option value="11" ${queryParam.month=="11" ? " selected = 'selected' " : " " }>11月</option>
									<option value="12" ${queryParam.month=="12" ? " selected = 'selected' " : " " }>12月</option>
								</select>
							</div>
							<button type="submit" class="btn btn-primary">查 询</button>
						</form>
					</c:if>
				</div>
				<div class="info-table">
					<table class="table" id="order-title">
						<thead>
							<tr>
								<th>实发合计</th>
								<th>年份</th>
								<th>月份</th>
							</tr>
						</thead>
						<c:forEach items="${pageInfo.list }" var="wage" varStatus="i">
							<tr>
								<td colspan="6"></td>
							</tr>
						<tbody class="table-bordered">
							<tr>
								<td><font color="red"><fmt:formatNumber value="${wage.sfTotal }" pattern="0.00" type="number" /></font></td>
								<td>${wage.wagesYear }</td>
								<td>${wage.wagesMonth }</td>
							</tr>
						</tbody>
						</c:forEach>
					</table>
				</div>
				<input type="hidden" id="msg" value="${msg }" />
				<div class="pages">
					<a class="btn btn-default btn-sm" role="button"
						href="${pageContext.request.contextPath}/center/wagesParent/checkRePayrollTotal?page=1&rows=${pageInfo.pageSize}&year=${queryParam.year}&month=${queryParam.month}">首页</a>
					<a class="btn btn-default btn-sm" role="button"
						href="${pageContext.request.contextPath}/center/wagesParent/checkRePayrollTotal?page=${pageInfo.prePage}&rows=${pageInfo.pageSize}&year=${queryParam.year}&month=${queryParam.month}">&lt;&lt;上一页</a>
					<a class="btn btn-default btn-sm" role="button"
						href="${pageContext.request.contextPath}/center/wagesParent/checkRePayrollTotal?page=${pageInfo.nextPage}&rows=${pageInfo.pageSize}&year=${queryParam.year}&month=${queryParam.month}">下一页&gt;&gt;</a>
					<a class="btn btn-default btn-sm" role="button"
						href="${pageContext.request.contextPath}/center/wagesParent/checkRePayrollTotal?page=${pageInfo.pages}&rows=${pageInfo.pageSize}&year=${queryParam.year}&month=${queryParam.month}">尾页</a>
					<span style="color: #333333;">
						&nbsp;当前&nbsp;${pageInfo.pageNum}&nbsp;/&nbsp;${pageInfo.pages}&nbsp;页&nbsp;，&nbsp;共&nbsp;${pageInfo.total}&nbsp;条
					</span>
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