<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>


<link href="${ctx}/static/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" />
<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/> 
<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/jquery-ui-1.8.21.custom.min.js"></script>	
<script src="${ctx}/static/select2-3.4.6/select2.js"></script>
<title>通讯录管理</title>
</head>
<body>
<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
		<div class="page-header" style="margin-top:0px;">
			<h2  style="margin-top:0px">通讯录管理</h2>
		</div>
		 <c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
		<div class="bs-callout bs-callout-info">
				<form id="queryForm" class="form-inline"  method="get"
					action="${ctx}/mgr/addressList/index">
					<label>组名：</label>
				<select name="search_EQ_deptId" style="min-width:220px;" class="populate placeholder select2-offscreen se2" tabindex="-1" title="">
				<option value="">---------请选择通讯录组名---------</option>
				<c:forEach items="${addressListTypes}" var="addressListType">
				<option value="${addressListType.id}" ${ param.search_EQ_deptId == addressListType.id ? 'selected':''}>${addressListType.name }</option>
				</c:forEach>
				</select>
					<label>姓名：</label> <input name="search_LIKE_name"
						type="text" placeholder="请输入要查询人的名称" value="${param.search_LIKE_name}" class="form-control"/> 
						 <input type="submit" class="btn btn-success"
						value="查 找" />
				<tags:sort />
				</form>
			
			
		</div>
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th title="编号" width="100px">操作</th>
					<th title="姓名">姓名</th>
					<th title="职位">职位</th>
					<th title="私人电话">私人电话</th>
					<th title="办公电话">办公电话</th>
					<th title="公司名称">公司</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${AddressLists.content}" var="item" varStatus="s">
					<tr id="add${item.id}" >
						<td>
							<div class="btn-group">
								<a class="btn btn-info" href="#">操作</a> <a
									class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
									<span class="caret"></span>
								</a>
								<ul class="dropdown-menu">
									<li><a
										href="<%=request.getContextPath()%>/mgr/addressList/edit?id=${item.id}"><i
											class="icon-edit"></i>修改</a></li>
									
									<li><a href="javascript:void(0);" rel="${item.id}"
										class="del"><i class="icon-th"></i>删除</a></li>
						
								</ul>
							</div>
						</td>
						<td>
						<%-- <a href="<%=request.getContextPath()%>/mgr/addressList/show?id=${item.id}"
							data-fancybox-type="iframe" rel="fancy" class="showInfo" ></a> --%>
							${item.name }
						</td>
						<td>${item.position }</td>
						<td>${item.tel }</td>
						<td>${item.officePhone }</td>
						<td>${item.companyName }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="row">
		<div class="col-sm-4">
			<a href="<%=request.getContextPath()%>/mgr/addressList/add"
				class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>新增通讯信息</a>
		
		</div>
		<div class="col-sm-8">
				<tags:pagination page="${AddressLists}" paginationSize="5"/>
		</div>
	</div>
		<script type="text/javascript">
		$("#service").addClass("active");
		$(".se2").select2({
		    placeholder: "-----------请选择产品类型----------",
		    allowClear: true
		});
		$(document).ready(function(){
			
		 
			
			
			$('.showInfo').fancybox({
				autoDimensions:false,
				width:600,
				height:400
			});
			
			$(".del").click(function(){
				var id = $(this).attr("rel");
				if(confirm("此操作不可恢复，确定删除吗？")){
					$.ajax({
						url: '<%=request.getContextPath()%>/mgr/addressList/del?id=' + id, 
						type: 'DELETE',
						contentType: "application/json;charset=UTF-8",
						dataType: 'json',
						success: function(data){
							$("#add"+id).remove();
						},error:function(xhr){
							alert('错误了，请重试');
						}
					});
				}
			});
		});
	
		</script> 	
	</div>
	</div>
</body>
</html>