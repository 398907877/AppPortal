<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>供求信息类型管理</title>
	<link href="${ctx}/static/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" /> 
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
	
	<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.js"></script>	
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
		<h2>供求信息类型管理</h2>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="bs-callout bs-callout-info">
			<form class="form-inline" action="#">
			   <label>供求类型：</label>
				<select id="selectType" name="search_EQ_type" style="min-width:220px;" class="populate placeholder select2-offscreen se2" tabindex="-1" title="">
				<option value=""  selected="selected">---------请选择供求类型---------</option>
				<option value="0" ${ param.search_EQ_type == 0 ? 'selected':''}>供（提供给他人信息）</option>
				<option value="1" ${ param.search_EQ_type == 1 ? 'selected':''}>求（想要获得的信息）</option>
				</select>
				<label>类型名称：</label> 
				<input type="text" name="search_LIKE_supplyDemandType" class="form-control " value="${param.search_LIKE_supplyDemandType}"> 
				<input type="hidden" name="search_EQ_status" class="input-medium" value="1"> 
				<button type="submit" class="btn btn-success" id="search_btn">查询</button>
				<tags:sort/>
		    </form>
	    
	</div>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>信息类型(点击可修改)</th><th>供求类型</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${supplyDemandTypes.content}" var="category">
		<c:if test="${category.status!= 0}">
		
			<tr id="deleteCategory_${category.id}">
				
				<td style="color: #3280AE; cursor: pointer;" id="changeName${category.id}" ref="${category.supplyDemandType}">
				<a href="javascript:aclick('${category.id}');" >${category.supplyDemandType}</a></td>
				<td id="oldCategory_${category.id}" style="display: none;">${category.supplyDemandType}</td>
			 	   <td><c:if test="${category.type eq 0}"> 供</c:if><c:if test="${category.type eq 1}"> 求</c:if></td>
				<td><a href="javascript:void(0);" class="delete_ btn btn-danger btn-xs" rel="${category.id}"><i class="icon-trash"></i>删除</a></td>
			</tr>
		</c:if>
		</c:forEach>
		</tbody>
	</table>
	<div class="row">
		<div class="col-sm-4">
			<a  href="${ctx}/mgr/supply/type/create"class="playerName btn btn-primary" data-fancybox-type="iframe" rel="fancy" id="iframe">添加新类型</a>
	<a class="btn btn-primary" href="${ctx}/mgr/supply">供求信息管理</a>
	<input id="cancel_btn" class="btn btn-primary" type="button" value="返回上一级" onclick="history.back()"/>
		</div>
		<div class="col-sm-8">
				<tags:pagination page="${supplyDemandTypes}" paginationSize="5"/>
		</div>
	</div>

<script type="text/javascript">
$("#tenancy").addClass("active");
$('.playerName').fancybox({
	autoDimensions:false,
	width:500,
	height:300
});
$(".se2").select2({
    placeholder: "-----------请选择产品类型----------",
    allowClear: true
});
function aclick(id){
	var name = $("#changeName"+id).attr("ref");
	$("#changeName"+id).html("<input id='put"+id+"' onmouseout='savename(\""+id+"\",this.value);' rel='"+id+"' value='"+name+"'/>");
}
function savename(id,name){
	var oldName = $("#oldCategory_"+id).text();
	$.ajax({
		url: "${ctx}/mgr/supply/type/changeName?id="+id+"&name="+name,
		type: 'POST',
		contentType: "text",
		success: function(date){
				$("#changeName"+id).html('<a href="javascript:aclick('+id+')" >'+name+"</a>");
				$("#changeName"+id).attr("ref",name);
				if(name!=oldName){
				$(".page-header").after("<div id='message' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>修改供求类型名称成功</div>");
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
	         url: "${ctx}/mgr/supply/type/delete/"+id,
	         type: 'DELETE',
	         async:false,
	         contentType: "application/json;charset=UTF-8"
	       })
	       .done(function( html ) {
	    	   $("#deleteCategory_"+id).remove();
	    		$(".page-header").after("<div id='message' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>删除供求类型信息成功</div>");
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
