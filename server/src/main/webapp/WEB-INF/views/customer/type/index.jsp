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

<title>租户客户类型管理</title>
</head>
<body>
   	<div >
		<div class="page-header">
			<h2>租户客户类型管理</h2>
		</div>
		<div>
		 <c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="bs-callout bs-callout-info">
				<form id="queryForm" class="form-inline"  method="get"
					action="${ctx}/mgr/customer/type/index">
					<label>类型名称：</label> 
					<input name="search_LIKE_typeContent"
						type="text" value="${param.search_LIKE_typeContent}" />
						 <input type="submit" class="btn btn-success" value="查 找" />
				<tags:sort />
				</form>
			
			
		</div>
		</div>
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th title="编号" width="120px">操作</th>
					
                    <th title="内容">类型名称</th>
					<th title="crt_date" width="240px">创建时间</th>

				</tr>
			</thead>
			<tbody>
		<c:forEach items="${typeList.content}" var="item" varStatus="s">
					<tr>
						<td>
							<div class="btn-group">
								<a class="btn btn-info" href="#">操作</a> <a
									class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
									<span class="caret"></span>
								</a>
								<ul class="dropdown-menu">
									<li><a
										href="<%=request.getContextPath()%>/mgr/customer/type/edit?id=${item.id}"><i
											class="icon-edit"></i>修改</a></li>
									
									<li><a href="javascript:void(0);" rel="${item.id}"
										class="del"><i class="icon-th"></i>删除</a></li>
						
								</ul>
							</div>
						</td>	
						<td>
						${item.typeContent }
						</td>

                        
					
						<td><fmt:formatDate value="${item.createDate}"
								pattern="yyyy/MM/dd  HH:mm:ss" /></td>

					</tr>
				
				</c:forEach>
		

				
			</tbody>
		</table>
		<div class="row">
		<div class="col-sm-4">
			<a href="<%=request.getContextPath()%>/mgr/customer/type/add"
				class="btn btn-primary">新增客户类型</a>
		</div>
		<div class="col-sm-8">
			<tags:pagination page="${typeList}" paginationSize="5"/>
		</div>
	</div>
		<script type="text/javascript">
		$(document).ready(function(){
			
			$(".del").click(function(){
				var id = $(this).attr("rel");
				if(confirm("此操作不可恢复，确定删除吗？")){
					$.ajax({
						url: '<%=request.getContextPath()%>/mgr/customer/type/del?id=' + id, 
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