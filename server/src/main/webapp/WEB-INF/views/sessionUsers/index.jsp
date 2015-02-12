<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>


<link href="${ctx}/static/fancybox/jquery.fancybox-1.3.4.css"
	rel="stylesheet" /> 
<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/jquery-ui-1.8.21.custom.min.js"></script>	

<title>APP用户管理</title>
</head>
<body>
		<div class="page-header">
			<h2>APP用户管理</h2>
		</div>
		 <c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
		<div class="bs-callout bs-callout-info">
				<form id="queryForm" class=" form-inline"  method="get"
					action="${ctx}/mgr/tenancyUsers/index">
					<label>用户名：</label> <input placeholder="请输入用户名" name="search_LIKE_name"
						type="text" class="form-control" value="${param.search_LIKE_name}" /> 
			      		
						<label>状态：</label> 
						
						<select name="search_EQ_status" class="form-control">
						<option value="">---------请选择---------</option>
						<option value="1" ${param.search_EQ_status == '1' ? 'selected' : '' } >正常</option>
						<option value="0" ${param.search_EQ_status == '0' ? 'selected' : '' }  >注销</option>
						</select>
						 <input type="submit" class="btn btn-success"
						value="查 找" />
					<tags:sort />
				</form>
			</div>
			
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th title="编号" width="120px">编号</th>
					<th title="用户名">用户名</th>
					<th title="昵称">昵称</th>
                    <th title="性别">性别</th>
					<th title="登入名">登入名</th>
					<th title="创建时间" width="240px">创建时间</th>
					<th title="状态">状态</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${forumUsers.content}" var="item" varStatus="s">
				
					<tr  >
					
						<td>
							<div class="btn-group">
								<a class="btn btn-info" href="#">操作</a> <a
									class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
									<span class="caret"></span>
								</a>
								<ul class="dropdown-menu">
									<li><a
										href="<%=request.getContextPath()%>/mgr/tenancyUsers/edit?id=${item.id}"><i
											class="icon-edit"></i>修改</a></li>
									
									<li><a href="javascript:void(0);" rel="${item.id}"
										class="del"><i class="icon-th"></i>注销</a></li>
									<li><a href="javascript:void(0);" rel="${item.id}"
										class="unlock" ><i class="icon-th"></i>激活</a></li>
							
									<li><a
										href="<%=request.getContextPath()%>/mgr/tenancyUsers/resetPwd?id=${item.id}"><i
											class="icon-edit"></i>重置密码</a></li>
										<li><a
										href="<%=request.getContextPath()%>/mgr/tenancyUsers/setTag?id=${item.id}&name=${item.name}"><i
											class="icon-edit"></i>设置tag</a></li>	
						
								</ul>
							</div>
						</td>
						
						<td>
					
						<a
							href="<%=request.getContextPath()%>/mgr/tenancyUsers/show?id=${item.id}"
							data-fancybox-type="iframe" rel="fancy" class="showInfo" >${item.name }</a>
						</td>
						<td>${item.nickname}</td>
                      <td><c:if test= "${item.sex eq 0}">女</c:if><c:if test= "${item.sex eq 1}">男</c:if> </td>
						<td>
						${item.loginname}
						</td>
					
						<td><fmt:formatDate value="${item.createDate}"
								pattern="yyyy/MM/dd  HH:mm:ss" /></td>
						<td>${item.status == 1 ? '正常' : '注销' }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="row">
		<div class="col-sm-4">
			 <a href="<%=request.getContextPath()%>/mgr/tenancyUsers/add"
				class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>新增App用户</a> 
		</div>
		<div class="col-sm-8">
			<tags:pagination page="${forumUsers}" paginationSize="5"/>
		</div>
	</div>
		<script type="text/javascript">
		$("#service").addClass("active");
		$(document).ready(function(){
			$('.showInfo').fancybox({
				autoDimensions:false,
				width:800,
				height:500
			});
			
			$(".del").click(function(){
				var id = $(this).attr("rel");
					$.ajax({
						url: '<%=request.getContextPath()%>/mgr/tenancyUsers/del?id=' + id, 
						type: 'DELETE',
						contentType: "application/json;charset=UTF-8",
						dataType: 'json',
						success: function(data){
							window.location.href = window.location.href;
						},error:function(xhr){
							alert('错误了，请重试');
						}
					});
				
			});
			
			
			$(".unlock").click(function(){
				var id = $(this).attr("rel");
					$.ajax({
						url: '<%=request.getContextPath()%>/mgr/tenancyUsers/activate?id=' + id, 
						type: 'DELETE',
						contentType: "application/json;charset=UTF-8",
						dataType: 'json',
						success: function(data){
							window.location.href = window.location.href;
						},error:function(xhr){
							alert('错误了，请重试');
						}
					});
				
			});
			
			
		});
		</script> 	
</body>
</html>