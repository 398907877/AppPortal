<!DOCTYPE>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>论坛管理</title>
	<link href="${ctx}/static/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" /> 
	<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.js"></script>
	
</head>

<body>
<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">

     <div class="page-header" style="margin-top:0px;">
			<h2  style="margin-top:0px">论坛管理</h2>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
		 <div  class="bs-callout bs-callout-info">
		    <form class="form-inline" action="#">
				<label>标题</label> 
				<input type="text" name="search_LIKE_title" class="form-control" placeholder="请输入帖子标题" value="${param.search_LIKE_title}"> 
				<label>发布人</label> 
				<input type="text" name="search_LIKE_crUser" class="form-control" placeholder="请输入发布人" value="${param.search_LIKE_crUser}"> 
				<input type="hidden" name="search_EQ_status" value="1">
				<c:if test="${''!=uid&&null!=uid }">
				<input type="hidden" name="search_EQ_uid" value="${uid}">
				</c:if>
			<%--	<label>是否审核：</label>
			 	<select name="search_EQ_ischeck">
				<option value="">-------请选择是否审核------</option>
				<option value="0">未审核</option>
				<option value="1">已审核</option>
				</select>  --%>
				<button type="submit" class="btn btn-success" name="account-tab" id="search_btn">查询</button>
				 <tags:sort/>
				</form>
	   
	    </div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>操作</th><th>帖子标题<lable style="color:green;">（提示*点击可修改名称）</lable></th><th>发布人</th><th>发表时间</th><!-- <th>是否审核</th> --><th>帖子详情</th></tr></thead>
		<tbody>
		<c:forEach items="${invitations.content}" var="invitation">
		<c:if test="${invitation.status!='0'}">
			<tr id="deleteProduct_${invitation.id}">
					<td width="100px">
					<div class="btn-group">
							<a class="btn btn-info" href="#">操作</a>
							<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
							    <span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
					            <li><a href="${ctx}/mgr/invitation/update/${invitation.id}" ><i class="icon-edit"></i>修改</a></li>
					           <li><a href="javascript:void(0);" class="delete_" rel="${invitation.id}"><i class="icon-trash"></i>删除</a></li>
					           <%-- <c:if test="${invitation.ischeck eq 0}">
					            <li><a href="javascript:void(0);" class="check_" rel="${invitation.id}"><i class="icon-forward "></i>审核通过</a></li>
					           </c:if> --%>
					           <li><a href="${ctx}/mgr/invitation/reply/${invitation.id}/list" ><i class="icon-fast-forward"></i>评论管理</a></li>
					           <li class="divider"></li>
					          </ul>
						</div>	
					</td>
					<td style="color: #3280AE; cursor: pointer;" id="changeName${invitation.id}" ref="${invitation.title}">
					<a href="javascript:aclick('${invitation.id}');" >${invitation.title}</a></td>
					<td id="oldCategory_${invitation.id}" style="display: none;">${invitation.title}</td>
					<td>${invitation.crUser}</td>
						<td>${invitation.crtDate}</td>
					<%-- <td id="ischeck_${invitation.id}"><c:if test="${invitation.ischeck eq 0}">未审核</c:if> <c:if test="${invitation.ischeck eq 1}">已审核</c:if></td> --%>
					<td><a href="${ctx}/mgr/invitation/detail/${invitation.id}"class="btn btn-default playerName" data-fancybox-type="iframe" rel="fancy" id="iframe">帖子详情</a></td>
				</tr>
		  </c:if>
		</c:forEach>
		</tbody>
	</table>
	<div class="row">
		<div class="col-sm-2">
			<a class="btn btn-primary" href="${ctx}/mgr/invitation/create"><span class="glyphicon glyphicon-plus"></span>添加帖子</a>
		</div>
		<div class="col-sm-8">
				<tags:pagination page="${invitations}" paginationSize="5"/>
		</div>
	</div>
<script type="text/javascript">
$("#service").addClass("active");
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
		url: "${ctx}/mgr/invitation/changeName?id="+id+"&name="+name,
		type: 'POST',
		contentType: "application/json;charset=UTF-8",
		success: function(date){
				$("#changeName"+id).html('<a href="javascript:aclick('+id+')" >'+name+"</a>");
				$("#changeName"+id).attr("ref",name);
				if(name!=oldName){
				 $(".page-header").after("<div id='message' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>帖子标题修改成功</div>");
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
	         url: "${ctx}/mgr/invitation/delete/"+id,
	         type: 'POST',
	         async:false,
	         contentType: "application/json;charset=UTF-8"
	       })
	       .done(function( html ) {
	    	   $("#deleteProduct_"+id).remove();
	    	   $(".page-header").after("<div id='message' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>帖子&nbsp;'"+html.title+"'&nbsp;删除成功</div>");
	       }).fail( function(jqXHR, textStatus){
	    	  alert("删除失败jqXHR="+jqXHR+"textStatus="+textStatus);
	   });
     }
   });
	
	
	$(".check_").click(function(){
		var id = $(this).attr("rel");
		if(confirm("确定要审核通过吗?")){ 
           $.ajax({
	         url: "${ctx}/mgr/invitation/check/"+id,
	         type: 'POST',
	         async:false,
	         contentType: "application/json;charset=UTF-8"
	       })
	       .done(function( html ) {
	    	   $("#ischeck_"+id).text("已审核");
	    	   $(".page-header").after("<div id='message' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>帖子&nbsp;'"+html.title+"'&nbsp;审核通过</div>");
	       }).fail( function(jqXHR, textStatus){
	    	  alert("审核失败jqXHR="+jqXHR+"textStatus="+textStatus);
	   });
     }
   });
	
	
});
</script>
</div>
</div>
</body>

</html>