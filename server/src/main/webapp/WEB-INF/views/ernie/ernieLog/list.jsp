<!DOCTYPE>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="huake" uri="/huake"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>中奖记录管理</title>
	<link href="${ctx}/static/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" /> 
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
	
	<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/js/jquery-ui-1.8.21.custom.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.js"></script>	
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.pack.js"></script>	
    <script src="${ctx}/static/select2-3.4.6/select2.js"></script>
</head>

<body>
	<div class="row">
	
		<div class="col-md-2">
			<%@ include file="../nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
			<div class="page-header" >
				<h4>中奖记录管理</h4>
			</div>
			<c:if test="${not empty message}">
				<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
			</c:if>
			<div  class="bs-callout bs-callout-info">
			<label>选择相应的营销活动，查看相关中奖记录(不选则查询全部)</label>
		    <form class="form-search" action="#">
				 <select id="e2" name="search_EQ_erineId" style="min-width:240px;" class="populate placeholder select2-offscreen" tabindex="-1" title=""><option></option>
                       <c:forEach items="${ernies}" var="ernie">
                          <option value="${ernie.id}" ${ param.search_EQ_erineId == ernie.id ? 'selected':''}>${ernie.title }</option>
                     </c:forEach>
                 </select>
				
				<c:if test="${''!=uid&&null!=uid }">
				<input type="hidden" name="search_EQ_uid" value="${uid}">
				</c:if>
				<button type="submit" class="btn btn-success btn-sm" name="account-tab" id="search_btn">查 询</button>
				</form>
	   
	    </div>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th width="10%">操作</th>
						<th>用户</th>
						<th>中奖活动</th>
						<th>中奖奖品</th>
						<th>中奖时间 </th>
						<!-- <th>中奖详情 </th> -->
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${ernieLogs.content}" var="ernieItem" varStatus="i">
					<tr id="delete_${ernieItem.id}">
						<td>
							<div class="btn-group">
								<button type="button" class="btn btn-info"># ${i.index+1}</button>
								<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown">
									<span class="caret"></span>
									<span class="sr-only">Toggle Dropdown</span>
								</button>
								<ul class="dropdown-menu">
							         <!-- <li><a href="${ctx}/mgr/ernieLog/${ernieItem.id}/delete"><i class="icon-trash"></i>删除</a></li> -->
							         <li><a href="javascript:void(0);" class="delete_" rel="${ernieItem.id}"><i class="icon-trash"></i>删除</a></li>
							     </ul>
							</div>	
						</td>
						<td><a href="#"><huake:getMember id="${ernieItem.memberId}" /> </a></td>
						<td><huake:getErine id="${ernieItem.erineId}" /></td>
						<td><huake:getErineItems id="${ernieItem.winning}" /></td>
						<td><fmt:formatDate value="${ernieItem.createdAt}" pattern="yyyy年MM月dd日 HH时mm分" /></td>
						<%-- <td><a href="${ctx}/mgr/ernieLog/detail/${ernieItem.id}"class="playerName btn btn-default" data-fancybox-type="iframe" rel="fancy" id="iframe">中奖详情</a></td>	 --%>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="row">
				<div class="col-sm-4">
					<input id="cancel_btn" class="btn btn-primary" type="button" value="返回" onclick="history.back()"/>
				</div>
				<div class="col-sm-15">
					<tags:pagination page="${ernieLogs}" paginationSize="5"/>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
$("#service").addClass("active");
$(document).ready(function(){
	$('.playerName').fancybox({
		autoDimensions:false,
		width:800,
		height:500,
		scrolling:"yes"
	});});
$(document).ready(function() {
	$(".delete_").click(function(){
		var id = $(this).attr("rel");
		alert(id);
		if(confirm("确定要删除吗?")){ 
           $.ajax({
	         url: "<%=request.getContextPath()%>/mgr/ernieLog/"+id+"/delete",
	         type: 'GET',
	         async:false,
	         contentType: "application/json;charset=UTF-8",
	        	 success: function(data){
	        		 $("#delete_"+id).remove();
	  	    	   $(".page-header").after("<div id='message' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>中奖记录删除成功</div>");
	        	 },error:function(data){
						alert("删除失败！");
	        	 }
	   });
     }
   });
});
$("#e2").select2({
    placeholder: "-----------请选择活动----------",
    allowClear: true
});
	</script>
  </body>
</html>