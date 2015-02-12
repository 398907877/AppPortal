<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>

<title>租户管理</title>
	<link href="${ctx}/static/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" /> 
	<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.js"></script>
</head>
<body>
   
	<div >
		<div class="page-header">
			<h2>租户管理</h2>
		</div>
		 <c:if test="${not empty message}">
			<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
		</c:if>
		<div class="bs-callout bs-callout-info">
		    <p>输入查询条件过滤列表.</p>
		    <form id="queryForm" class="form-inline" role="form"  method="get" action="${ctx}/mgr/crm/index">
				<label>企业名称：</label> 
				<input name="search_LIKE_name" type="text" class="form-control" value="${param.search_LIKE_name}" /> 
				<label>合同状态：</label> 
				<select name="deadline" class="form-control" >
					<option value="0" <c:if test="${deadline eq '0' }">selected</c:if>>全部</option>
					<option value="1" <c:if test="${deadline eq '1' }">selected</c:if>>快过期</option>
					<option value="2" <c:if test="${deadline eq '2' }">selected</c:if>>已过期</option>
				</select> 
				<input type="submit" class="btn btn-success" value="查 找" />
			</form>
		</div>
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th title="编号">编号</th>
					<th title="租户全称">企业全称</th>
				
					<th title="公钥">公钥</th>
					<th title="start_date">合同开始日期</th>
					<th title="end_date">合同结束日期</th>
					<!-- <th title="status">状态</th> -->
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${users.content}" var="item" varStatus="s">
				
					<tr>
						<td>
							<div class="btn-group">
								<button type="button" class="btn btn-info">操作</button>
								<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown">
							        <span class="caret"></span>
							        <span class="sr-only">Toggle Dropdown</span>
							      </button>
								<ul class="dropdown-menu">
									<li><a
										href="<%=request.getContextPath()%>/mgr/crm/tenancy/edit?uid=${item.uid}"><i
											class="icon-edit"></i>修改</a></li>
									<li><a href="javascript:void(0);" rel="${item.uid}"
										class="del"><i class="icon-th"></i>删除</a></li>
									<%-- <li><a
										href="<%=request.getContextPath()%>/mgr/contract/add?tid=${item.uid}"><i
											class="icon-edit"></i>生成合同</a></li> --%>
									<li><a
										href="<%=request.getContextPath()%>/mgr/company/info?uid=${item.uid}"><i
											class="icon-edit"></i>租户介绍</a></li>
									<li><a
										href="<%=request.getContextPath()%>/mgr/crm/auth/grant?uid=${item.uid}"><i
											class="icon-edit"></i>授权管理</a></li>
									<li class="divider"></li>
									<%-- <li><a
										href="<%=request.getContextPath()%>/mgr/crm/userList?uid=${item.uid}"><i
											class="icon-edit"></i>租户用户管理</a></li> --%>
								</ul>
							</div>
						</td>
						<td>
					
						<a href="<%=request.getContextPath()%>/mgr/crm/tenancy/show?uid=${item.uid}"
							data-fancybox-type="iframe" rel="fancy" class="showInfo" >${item.name }</a>
						</td>

						<td>${item.appId }</td>
						<td><fmt:formatDate value="${item.startDate}"
								pattern="yyyy-MM-dd" /></td>
						<td><fmt:formatDate value="${item.endDate}"
								pattern="yyyy-MM-dd" /></td>
						<%-- <td>${item.status == 1 ? '有效' : '无效'}</td> --%>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="row">
			<div class="col-sm-2">
				<a href="<%=request.getContextPath()%>/mgr/crm/tenancy/add" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>租户登记</a>
			</div>
			<div class="col-sm-10">
				<tags:pagination page="${users}" paginationSize="5"/>
			</div>
		</div>
		
		
		<script type="text/javascript">
		$("#tenancy").addClass("active");
		$(document).ready(function(){
			$('.showInfo').fancybox({
				autoDimensions:false,
				width:800,
				height:600
			});
			$(".del").click(function(){
				var id = $(this).attr("rel");
				if(confirm("此操作不可恢复，确定删除吗？")){
					$.ajax({
						url: '<%=request.getContextPath()%>/mgr/crm/tenancy/del?uid=' + id, 
						type: 'DELETE',
						contentType: "application/json;charset=UTF-8",
						dataType: 'json',
						success: function(data){
							 
							window.location.href = window.location.href;
						},error:function(xhr){
							alert('错误了，请重试');
						}
					});
				}
			});
		});
		</script> 	
	</div>
</body>
</html>