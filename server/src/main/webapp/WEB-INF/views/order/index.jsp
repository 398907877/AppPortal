<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.huake.com/functions" prefix="function" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>订单管理</title>
	<link href="${ctx}/static/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" /> 
	<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.js"></script>
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
	<script src="${ctx}/static/select2-3.4.6/select2.js"></script> 
</head>
<body>
<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
	<div >
		<div class="page-header" style="margin-top:0px;">
			<h2  style="margin-top:0px">订单管理</h2>
		</div>
		<div>
		 <c:if test="${not empty message}">
			<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
		</c:if>
		<div class="bs-callout bs-callout-info">
		<form id="queryForm" class="form-inline"  method="get" action="${ctx}/mgr/order">
				<label>订单号：</label> <input type="text" name="search_LIKE_orderNo"
				class="form-control" value="${param.search_LIKE_orderNumber}">
				<label>订单状态：</label> 
				<select name="search_EQ_state" style="min-width:180px;" class="populate placeholder select2-offscreen se2" tabindex="-1" title="">
					<option value="">---------请选择---------</option>
					<c:forEach items="${ orderState}" var="os">
					
						<option value="${os.key }">${os.value}</option>
					</c:forEach>
				</select>	
				<label>物流单号：</label> 
				<input name="search_EQ_logisticNo" class="form-control" value="${param.search_EQ_logisticNo }"/>				
				<button type="submit" class="btn btn-success" id="search_btn">查询</button>
			
		</form>
		</div>	
		</div>
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th title="操作" width="100">操作</th>
					<th title="订单号">订单号</th>
					
					<th title="运费">运费(元)</th>
					<th title="应支付">应支付</th>
					<th title="订单状态">订单状态</th>
					<th title="物流单号">物流单号</th>
					<th title="订单更新时间">更新时间</th>
					<th title="备注">备注</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orders.content}" var="item" varStatus="s">
					<tr>
						<td>
							<div class="btn-group">
								<a class="btn btn-info" href="操作">操作</a> <a
									class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
									<span class="caret"></span>
								</a>
								<ul class="dropdown-menu">
									<li><a
										href="<%=request.getContextPath()%>/mgr/order/update/${item.id}"><i
											class="icon-edit"></i>修改</a>
									</li>
									       <c:if test="${item.state!='0' &&item.state!= '1' && item.state!= '2' }">
										        <li><a
													href="http://m.kuaidi100.com/index_all.html?type=${item.logistic.logistiCore}&postid=${item.logisticNo}" target="_blank"><i
														class="icon-edit" ></i>查看物流</a>
												</li>  
									    	</c:if>
									      
								</ul>
							</div>
						</td>
						<td><a href='<%=request.getContextPath()%>/mgr/order/detail/${item.id}' class="playerName" title="查看订单详情" data-fancybox-type="iframe" rel="fancy" id="iframe${item.id}" onclick="fancy(${item.id})">${item.orderNo}</a></td>
					
						<td><span class="price">￥ </span>${item.freight}</td>
						<td><span class="price">￥ </span>${item.payment}</td>
						<td>
							<c:forEach items="${ orderState}" var="os">
								 <c:if test="${item.state == os.key }">
								 	${os.value }
								 </c:if>
							</c:forEach>
						</td>
						<td>${item.logisticNo}</td>
						<td>${item.upDate}</td>
						<td>
							<abbr title="${item.message }">${function:truncate(item.message, 10)}</abbr>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<tags:pagination page="${orders}" paginationSize="5"/>
		
	</div>
	</div>
	</div>
		<script type="text/javascript">
		$('.playerName').fancybox({
			autoDimensions:false,
			width:500,
			height:500
		});
		$(".se2").select2({
		    placeholder: "请选择一个分组",
		    allowClear: true
		});
		$(document).ready(function(){	
			$(".se2").select2("val", "${param.search_EQ_state}");
			$('.description').tooltip();
			$(".del").click(function(){
				var id = $(this).attr("rel");
					$.ajax({
						url: '<%=request.getContextPath()%>/manage/orders/delOrder?id=' + id, 
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