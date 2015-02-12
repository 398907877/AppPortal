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
<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
	<script src="${ctx}/static/select2-3.4.6/select2.js"></script> 
<title>会员企业管理</title>
</head>
<body>
<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
		<div class="page-header" style="margin-top:0px;">
			<h2  style="margin-top:0px">会员企业管理</h2>
		</div>
		<div>
		 <c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div  class="bs-callout bs-callout-info">
				<form id="queryForm" class="form-inline"  method="get"
					action="${ctx}/mgr/customer/index">
					<label>企业名称：</label> <input placeholder="请输入企业名称" name="search_LIKE_customer0name"
						type="text" class="form-control" value="${param.search_LIKE_customer0name}" /> 
			      		
					<label>企业分类：</label> 
					<select name="search_EQ_type0id" style="min-width:220px;" id="category" class="populate placeholder select2-offscreen se2" tabindex="-1" title="">
					<option value="">--------请选择企业分类--------</option>
					<c:forEach items="${category}" var="ca" varStatus="i">
						<c:if test="${ca.parentId == 0 }">
							<optgroup label="${ca.name }">
								<c:forEach items= "${category }" var="c" >
									<c:if test="${ca.id == c.parentId }">
										<option value="${c.id }"> ${c.name }</option>
									</c:if>
								</c:forEach>
							</optgroup>
						</c:if>
					</c:forEach>
					</select>	
					
						 <input type="submit" class="btn btn-success"
						value="查 找" />
				<tags:sort />
				</form>
			</div>
			
		</div>
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th title="编号" width="120px">操作</th>
					<th title="企业名称">企业名称</th>
					<th title="经营范围">经营范围</th>
					<th title="联系人">联系人</th>
					<th title="联系电话">联系电话</th>
					<th title="企业地址">企业地址</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${customers.content}" var="item" varStatus="s">
				
					<tr>
					
						<td>
							<div class="btn-group">
								<a class="btn btn-info" href="#">操作</a> <a
									class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
									<span class="caret"></span>
								</a>
								<ul class="dropdown-menu">
									<li><a
										href="<%=request.getContextPath()%>/mgr/customer/edit?id=${item.id}"><i
											class="icon-edit"></i>修改</a></li>
									
									<li><a href="javascript:void(0);" rel="${item.id}"
										class="del"><i class="icon-th"></i>删除</a></li>
						
								</ul>
							</div>
						</td>
						
						<td>
					
						<a
							href="<%=request.getContextPath()%>/mgr/customer/show?id=${item.id}"
							data-fancybox-type="iframe" rel="fancy" class="showInfo" >${item.name }</a>
						</td>

						<td>
						${item.businessScope }
						</td>
					
						<td>${item.linkman }</td>
						<td>${item.tel }</td>
						<td>${item.address }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="row">
			<div class="col-sm-4">
		<a href="<%=request.getContextPath()%>/mgr/customer/add?uid=${uid}"
				class="btn btn-primary">新增会员企业</a>
					
			</div>
			<div class="col-sm-8">
					<tags:pagination page="${customers}" paginationSize="5"/>
			</div>
		</div>
		
		<script type="text/javascript">
		$(".se2").select2({
		    placeholder: "请选择一个分组",
		    allowClear: true
		});
		$("#tenancy").addClass("active");
		$(document).ready(function(){

			$("#category").select2("val","${param.search_EQ_type0id}");
			$('.showInfo').fancybox({
				autoDimensions:false,
				width:800,
				height:500
			});
			
			$(".del").click(function(){
				var id = $(this).attr("rel");
				if(confirm("此操作不可恢复，确定删除吗？")){
					$.ajax({
						url: '<%=request.getContextPath()%>/mgr/customer/del?id=' + id, 
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
	</div>
</body>
</html>