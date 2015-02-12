<!DOCTYPE>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.huake.com/functions" prefix="function" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>图文资讯管理</title>
	<link href="${ctx}/static/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" /> 
	<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.js"></script>
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
	<script src="${ctx}/static/select2-3.4.6/select2.js"></script> 
	<style type="text/css">
		.modal-dialog{
			width:370px;
			max-height:300px;
		}
		.borderS{
			border-color: #DDD;
			border-width: 1px;
border-radius: 6px 6px 6px 6px;
-webkit-box-shadow: none;
box-shadow: none;
		}
	</style>
</head>
<body class="modal-open">
<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
     <div class="page-header" style="margin-top:0px;">
			<h2  style="margin-top:0px">图文资讯管理</h2>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
		 <div  class="bs-callout bs-callout-info">
		    <form class="form-inline" action="#">
				<label>图文资讯类型：</label>
				<select name="search_EQ_categoryId" style="min-width:220px;" class="populate placeholder select2-offscreen se2" tabindex="-1" title="">
				<option value="">---------请选择图文资讯类型---------</option>
				<c:forEach items="${newsCategories}" var="newsCategorie">
				<option value="${newsCategorie.id}" ${ param.search_EQ_categoryId == newsCategorie.id ? 'selected':''}>${newsCategorie.name }</option>
				</c:forEach>
				</select>
				<label>图文资讯标题：</label>
				<input type="text" name="search_LIKE_title" class="form-control" placeholder="请输入图文资讯标题" value="${param.search_LIKE_title}">
				<input type="hidden" name="search_EQ_status" value="1">
				<c:if test="${''!=uid&&null!=uid }">
				<input type="hidden" name="search_EQ_uid" value="${uid}">
				</c:if>
				<button type="submit" class="btn btn-success" name="account-tab" id="search_btn">查询</button>
				  <tags:sort/>
				</form>
	  
	    </div>

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>操作</th><th>所属分类</th><th>图文资讯标题<!-- <lable style="color:green;">（提示*点击可修改标题）</lable> --></th><th>图文资讯介绍</th><th>图文资讯详情</th><th>是否发布</th></tr></thead>
		<tbody>
		<c:forEach items="${newss.content}" var="news">
			<tr id="deleteNews_${news.id}">
					<td width="100px">
					<div class="btn-group">
							<button class="btn btn-info">操作</button>
							<button class="btn btn-info dropdown-toggle" data-toggle="dropdown">
							    <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
					            <li><a href="${ctx}/mgr/news/update/${news.id}" ><i class="icon-edit"></i>修改</a></li>
					            <li><a href="javascript:void(0);" class="delete_" rel="${news.id}"><i class="icon-trash"></i>删除</a></li>
					            <li><a href="javascript:void(0);" class="setTop" rel="${news.id }">置顶</a></li>
					            <c:if test="${news.stick != null }"><li><a href="javascript:void(0);" class="cancelTop" rel="${news.id }">取消置顶</a></li></c:if>
					            <c:if test="${news.banner != '1' }"><li><a href="javascript:void(0);" class="setBanner" rel="${news.id }">设为Banner</a></li></c:if>
					            <c:if test="${news.banner == '1' }"><li><a href="javascript:void(0);" class="cancelBanner" rel="${news.id }">取消Banner</a></li></c:if>
					          </ul>
						</div>	
					</td>
					<td>
						<c:forEach items="${newsCategories}" var="nc">
							<c:if test="${nc.id == news.categoryId }">${ nc.name}</c:if>
						</c:forEach>
					</td>
					<td style="color: #3280AE;" id="changeName${news.id}" ref="${news.title}">
					<%-- <a href="javascript:aclick('${news.id}');" >${news.title}</a></td> --%>
					<abbr title="${news.title }">${function:truncate(news.title, 10)}</abbr>
					<td id="oldCategory_${news.id}" style="display: none;">${news.title}</td>
					<td><abbr title="${news.intro }">${function:truncate(news.intro,20)}</abbr></td>
					
					<td><a href="${ctx}/mgr/news/many/${news.id}/info" class="playerName btn btn-info " data-fancybox-type="iframe" rel="detail" id="iframeDetail">详情</a>&nbsp;&nbsp;&nbsp;
					<a data-toggle="modal" data-target="#news${news.id }" class="btn btn-info">预览</a>
					</td>
					<c:if test="${news.publish eq '0'}">
					<td id="publishid_${news.id}">未发布</td>
					</c:if>
					<c:if test="${news.publish eq '1'}">
					<td id="publishid_${news.id}">已发布</td>
					</c:if>
				</tr>
				<div class="modal fade" id="news${news.id }">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
								</button>
								<h4 class="modal-title">${news.title }</h4>
							</div>
							<div class="modal-body">
								<iframe src="${news.url }" width="330px" height="570px"
									class="borderS"></iframe>
							</div>

						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal-dialog -->
				</div>
			</c:forEach>
		</tbody>
	</table>
	<div class="row">
		<div class="col-sm-4">
			<a class="btn btn-primary" href="${ctx}/mgr/news/create"><span class="glyphicon glyphicon-plus"></span>添加图文资讯</a>
			<%-- <a class="btn btn-primary" href="${ctx}/mgr/news/category"><span class="glyphicon glyphicon-pushpin"></span>图文资讯类型管理</a> --%>
		</div>
		<div class="col-sm-8">
			<tags:pagination page="${newss}" paginationSize="5"/>
		</div>
	</div>

</div>
</div>
<script type="text/javascript">
$("#service").addClass("active");

$(".publish").on('click', function(){
	if(!window.confirm("确定发布为静态页面吗？")){
		return false;
	}
	var id=$(this).attr("rel");
	$.ajax({
		url: '<%=request.getContextPath()%>/mgr/news/publishing/' + $(this).attr("rel") +'/html' ,
		type: 'post',
		success: function(data){
		$("publish_a_"+id).text("重新发布静态页面");
		$("#publishid_"+id).text("已发布");
		$(".page-header").after("<div id='message' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>发布静态页面成功</div>");
		},
		complete: function () {
			$("#finishModal").modal('toggle');
		}
	});
});


$(document).ready(function(){
	$('.playerName').fancybox({
		autoDimensions:false,
		width:800,
		height:600
	});
	$('.playerName2').fancybox({
		autoDimensions:false,
		width:320,
		height:570
	});
});
function aclick(id){
	var name = $("#changeName"+id).attr("ref");
	$("#changeName"+id).html("<input id='put"+id+"' onmouseout='savename(\""+id+"\",this.value);' rel='"+id+"' value='"+name+"'/>");
}
function savename(id,name){
	var oldName = $("#oldCategory_"+id).text();
	$.ajax({
		url: "${ctx}/mgr/news/changeName?id="+id+"&name="+name,
		type: 'POST',
		contentType: "application/json;charset=UTF-8",
		success: function(date){
				$("#changeName"+id).html('<a href="javascript:aclick('+id+')" >'+name+"</a>");
				$("#changeName"+id).attr("ref",name);
				if(name!=oldName){
				$(".page-header").after("<div id='message' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>修改新闻标题成功</div>");
				 $("#oldCategory_"+id).text(name);
				}
		},error:function(xhr){
			alert('错误了，请重试1');
		}
	});
};
$(".se2").select2({
    placeholder: "请选择一个分组",
    allowClear: true
});
$(document).ready(function() {
	$(".delete_").click(function(){
		var id = $(this).attr("rel");
		if(confirm("确定要删除吗?")){ 
           $.ajax({
	         url: "${ctx}/mgr/news/delete/"+id,
	         type: 'POST',
	         async:false,
	         contentType: "application/json;charset=UTF-8"
	       })
	       .done(function( html ) {
	    	   $("#deleteNews_"+id).remove();
	    	   $(".page-header").after("<div id='message' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>新闻&nbsp;'"+html.title+"'&nbsp;删除成功</div>");
	       }).fail( function(jqXHR, textStatus){
	    	  alert("删除失败jqXHR="+jqXHR+"textStatus="+textStatus);
	   });
     }
   });
	
	$(".setTop").click(function(){
		var id = $(this).attr("rel");
		if(confirm("确定要置顶吗?")){ 
           $.ajax({
	         url: "${ctx}/mgr/news/setTop/"+id,
	         type: 'GET',
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
	$(".cancelTop").click(function(){
		var id = $(this).attr("rel");
		if(confirm("确定要取消置顶吗?")){ 
           $.ajax({
	         url: "${ctx}/mgr/news/cancelTop/"+id,
	         type: 'GET',
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
	$(".setBanner").click(function(){
		var id = $(this).attr("rel");
		if(confirm("确定要设置为Banner吗?")){ 
           $.ajax({
	         url: "${ctx}/mgr/news/setBanner/"+id,
	         type: 'GET',
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
	$(".cancelBanner").click(function(){
		var id = $(this).attr("rel");
		if(confirm("确定要取消Banner吗?")){ 
           $.ajax({
	         url: "${ctx}/mgr/news/cancelBanner/"+id,
	         type: 'GET',
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
</body>

</html>