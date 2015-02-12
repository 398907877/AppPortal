<!DOCTYPE>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.huake.com/functions" prefix="function" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>论坛评论管理</title>
	<link href="${ctx}/static/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" /> 
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
	<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/jquery-ui-1.8.21.custom.min.js"></script>	
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.pack.js"></script>	
    <script src="${ctx}/static/select2-3.4.6/select2.js"></script>
</head>

<body>
<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
     <div class="page-header">
		<h2>评论管理</h2>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
		 <div  class="bs-callout bs-callout-info">
		    <form class="form-inline" action="#">
				<label>评论人：</label>
				<input type="text"  placeholder="请输入评论用户的名称" name="search_LIKE_crUser" class="form-control" value="${param.search_LIKE_crUser}"> 
				<input type="hidden" name="search_EQ_status" value="1">
				<c:if test="${''!=uid&&null!=uid }">
				<input type="hidden" name="search_EQ_uid" value="${uid}">
				</c:if>
				<button type="submit" class="btn btn-success" name="account-tab" id="search_btn">查询</button>
				<tags:sort/>
				</form>
	    </div>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>操作</th><th>帖子名称</th><th>评论内容</th><th>评论人</th><th>评论时间</th></tr></thead>
		<tbody>
		<c:forEach items="${invitationReplys.content}" var="invitationReply">
			<tr id="deleteProduct_${invitationReply.id}">
					<td width="100px">
					<div class="btn-group">
							<a class="btn btn-info" href="#">操作</a>
							<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
							    <span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
					            <li><a href="${ctx}/mgr/invitation/reply/update/${invitationReply.id}" ><i class="icon-edit"></i>修改</a></li>
					           <li><a href="javascript:void(0);" class="delete_" rel="${invitationReply.id}"><i class="icon-trash"></i>删除</a></li>
					           <li class="divider"></li>
					          </ul>
						</div>	
					</td>
					<td><a href="${ctx}/mgr/invitation/detail/${invitation.id}"class="playerName" data-fancybox-type="iframe" rel="fancy" id="iframe"><abbr title="${invitation.title }">${function:truncate(invitation.title, 15)}</abbr></a></td>
					<td <%-- style="color: #3280AE; cursor: pointer;" id="changeName${invitationReply.id}" ref="${invitationReply.introduce}" --%>>
					<%-- <a href="javascript:aclick('${invitationReply.id}');" > --%><abbr title="${invitationReply.introduce }">${function:truncate(invitationReply.introduce, 30)}</abbr></td>
					
					<td>${invitationReply.crUser}</td>
					<td><fmt:formatDate value="${invitationReply.crtDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				    <%-- <td><a href="${ctx}/mgr/invitation/reply/detail/${invitationReply.id}"class="playerName btn btn-default" data-fancybox-type="iframe" rel="fancy" id="iframe">点击查看详情</a></td> --%>
				</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<div class="row">
		<div class="col-sm-4">
			<a class="btn btn-primary" href="${ctx}/mgr/invitation/reply/${invitation.id}/create"><span class="glyphicon glyphicon-plus"></span>添加评论</a>
			<input id="cancel_btn" class="btn btn-primary" type="button" value="返回" onclick="history.back()"/>
		</div>
		<div class="col-sm-8">
			<tags:pagination page="${invitationReplys}" paginationSize="5"/>
		</div>
	</div>
<script type="text/javascript">
$(document).ready(function(){
	$('.playerName').fancybox({
		autoDimensions:false,
		width:800,
		height:500
	});});
function aclick(id){
	var name = $("#changeName"+id).attr("ref");
	$("#changeName"+id).html("<input id='put"+id+"' onmouseout='savename(\""+id+"\",this.value);' rel='"+id+"' value='"+name+"'/>");
}
function savename(id,name){
	var oldName = $("#oldCategory_"+id).text();
	$.ajax({
		url: "${ctx}/mgr/invitation/reply/changeName?id="+id+"&name="+name,
		type: 'POST',
		contentType: "application/json;charset=UTF-8",
		success: function(date){
				$("#changeName"+id).html('<a href="javascript:aclick('+id+')" >'+name+"</a>");
				$("#changeName"+id).attr("ref",name);
				if(name!=oldName){
				 $(".page-header").after("<div id='message' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>产品名称修改成功</div>");
				 $("#oldCategory_"+id).text(name);
				}
		},error:function(xhr){
			alert('错误了，请重试1');
		}
	});
}
$(document).ready(function() {
	$(".delete_").click(function(){
		var id = $(this).attr("rel");
		if(confirm("确定要删除吗?")){ 
           $.ajax({
	         url: "${ctx}/mgr/invitation/reply/delete/"+id,
	         type: 'DELETE',
	         async:false,
	         contentType: "application/json;charset=UTF-8"
	       })
	       .done(function( html ) {
	    	   location.href = location.href;
	       }).fail( function(jqXHR, textStatus){
	    	  alert("删除失败jqXHR="+jqXHR+"textStatus="+textStatus);
	   });
     }
   });
});
</script>
</div>
</div>
</body>
</html>