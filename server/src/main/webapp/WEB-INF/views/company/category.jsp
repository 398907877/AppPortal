<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>行业类型管理</title>
</head>

<body>
 <div class="page-header">
		<h2>行业类型管理</h2>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="bs-callout bs-callout-info">
			<form class="form-inline" action="#">
				<label>类型名称：</label> 
				<input type="text" name="search_LIKE_name" class="form-control" value="${param.search_LIKE_name}"> 
				<input type="hidden" name="search_EQ_status" class="input-medium" value="1"> 
				<button type="submit" class="btn btn-success" id="search_btn">查询</button>
				<tags:sort/>
		    </form>
	    
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>类型名称(点击名称可修改)</th><th>创建时间</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${companyCategorys.content}" var="category">
		<c:if test="${category.status!='0'}">
			<tr id="deleteCategory_${category.id}">
				<td style="color: #3280AE; cursor: pointer;" id="changeName${category.id}" ref="${category.name}">
				<a href="javascript:aclick('${category.id}');" >${category.name}</a></td>
				<td>${category.crtDate}</td>
				<td id="oldCategory_${category.id}" style="display: none;">${category.name}</td>
				<td><a href="javascript:void(0);" class="btn btn-danger btn-xs delete_" rel="${category.id}"><span class="glyphicon glyphicon-remove"></span>删除</a></td>
			</tr>
		</c:if>
		</c:forEach>
		</tbody>
	</table>
	<div class="row">
		<div class="col-sm-6">
			<%-- <a class="btn btn-primary" href="${ctx}/mgr/company">返回企业介绍管理</a><input id="cancel_btn" class="btn btn-primary" style="margin-left:3px" type="button" value="返回上一级" onclick="history.back()"/> --%>
			<a class="btn btn-info" href="#">创建新类型<span class="glyphicon glyphicon-hand-right"></span></a>
			<input id="addcategory" style="display:inline;width:140px" type="text" class="form-control"> 
			<input type="button" class='jiaojiao btn btn-success'  value='提交'>
		</div>
		<div class="col-sm-6">
			<tags:pagination page="${companyCategorys}" paginationSize="5"/>
		</div>
	</div>
	<%-- <tags:pagination page="${companyCategorys}" paginationSize="5"/>
	<div id="divcate">
	<a class="btn btn-primary" href="${ctx}/mgr/company">返回企业介绍管理</a><input id="cancel_btn" class="btn btn-primary" type="button" value="返回上一级" onclick="history.back()"/>
	<input id="create_btn" class="btn btn-primary" type="button" value="创建新类型"/>
	<input id="addcategory" type="text"> <input type="button" class='jiaojiao'  value='提交'></div> --%>
<script type="text/javascript">
$(".jiaojiao").click(function(){
	var name = $("#addcategory").val();
	$.ajax({
		url: "${ctx}/mgr/company/category/create?name="+name,
		type: 'POST',
		contentType: "application/json;charset=UTF-8",
		success: function(date){
			window.top.location.reload();
		},error:function(xhr){
			alert('错误了，请重试1');
		}
	});
});

function aclick(id){
	var name = $("#changeName"+id).attr("ref");
	$("#changeName"+id).html("<input id='put"+id+"' onmouseout='savename(\""+id+"\",this.value);' rel='"+id+"' value='"+name+"'/>");
}
function savename(id,name){
	var oldName = $("#oldCategory_"+id).text();
	$.ajax({
		url: "${ctx}/mgr/company/category/changeName?id="+id+"&name="+name,
		type: 'POST',
		contentType: "text",
		success: function(date){
				$("#changeName"+id).html('<a href="javascript:aclick('+id+')" >'+name+"</a>");
				$("#changeName"+id).attr("ref",name);
			if(name!=oldName){
				
			 $(".page-header").after("<div id='message' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>企业类型修改成功 </div>");
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
	         url: "${ctx}/mgr/company/category/delete/"+id,
	         type: 'DELETE',
	         async:false,
	         contentType: "application/json;charset=UTF-8"
	       })
	       .done(function( html ) {
	    	   $("#deleteCategory_"+id).remove();
               alert("删除成功！");
	    //	   $(".page-header").after("<div id='message' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>企业类型&nbsp;'"+html.name+"'&nbsp;删除成功</div>");
	       }).fail( function(jqXHR, textStatus){
	    	  alert("删除失败jqXHR="+jqXHR+"textStatus="+textStatus);
	   });
     }
   });
});
</script>
</body>

</html>
