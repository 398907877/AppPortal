<!DOCTYPE>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<html>
<head>
<title>LIGHTBOX EXAMPLE</title>

<script src="${ctx}/static/layer1.7.0/layer.min.js"></script>
<script type="text/javascript">
	$(function() {
	

		$(".junzee tr").addClass("info");
		$(".junzee tr:even").addClass("warning");

	});
</script> 



</head>
<body>
	<div id="alerttable">
		<br />
		<div style="margin-left: 30px" class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<h3 class="text-center">用户选择列表</h3>
					<br />

					<!-- 搜索开始 -->
					<form class="form-inline" role="form">
						<div class="form-group">
							
								<input  class="form-control"
							 placeholder=" 请输入搜素的关键字"  name="search_LIKE_name">
						</div>


						<button type="submit" class="btn btn-default">搜索</button>
					</form>
					<!-- 搜索结束 -->
					<br />

					<table class="table table-hover table-bordered">
						<thead>
							<tr>
								<th>选择</th>
								<th>用户名</th>
								<th>设备数量</th>
								<th>电话号码</th>
								<th>状态</th>
						
							</tr>
						</thead>
						<tbody class="junzee">
						<c:forEach items="${users.content}" var="user">
							<tr>
								<td><input type="radio" name="optionsRadios"
									id="optionsRadios1" value="option1"></td>
								<td>${user.name}</td>
								<td>
								${fn:length(user.baiduDevice)}
								</td>
								<td>${user.mobile}</td>
						<td>${user.status == 1 ? '开通' : '注销'}</td> 

							
							</tr>
						</c:forEach>
						
					
						</tbody>
					</table>

					<!--  按钮--开始-->
					<button type="button" class="btn btn-primary">选择</button>
					<!--  按钮--结束-->


					<br /> <br />
<div class="col-sm-10">
				<tags:pagination page="${users}" paginationSize="5"/>
			</div>
					
				</div>
			</div>
		</div>
	</div>

</body>


</html>