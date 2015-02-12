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
	<title>产品管理</title>
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
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
     <div class="page-header" style="margin-top:0px;">
			<h2  style="margin-top:0px">产品管理</h2>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
		 <div  class="bs-callout bs-callout-info">
		    <form class="form-search" action="#">
				 <select id="e2" name="search_EQ_categoryId" style="min-width:240px;" class="populate placeholder select2-offscreen" tabindex="-1" title=""><option></option>
                       <c:forEach items="${categorys}" var="category">
                          <option value="${category.id}" ${ param.search_EQ_categoryId == category.id ? 'selected':''}>${category.name }</option>
                     </c:forEach>
                 </select>
				<input type="hidden" name="search_EQ_status" value="1">
				<c:if test="${''!=uid&&null!=uid }">
				<input type="hidden" name="search_EQ_uid" value="${uid}">
				</c:if>
				<button type="submit" class="btn btn-success btn-sm" name="account-tab" id="search_btn">查 询</button>
				 <tags:sort/>
				</form>
	   
	    </div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th width="90">操作</th><th>产品名称<lable style="color:green;">（提示*点击可修改名称）</lable></th><th>产品介绍</th><th>产品详情</th></tr></thead>
		<tbody>
		<c:forEach items="${products.content}" var="product">
		<c:if test="${product.status!='0'}">
			<tr id="deleteProduct_${product.id}">
					<td width="100px">
					<div class="btn-group">
						<button type="button" class="btn btn-info">操作</button>
						<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown">
							<span class="caret"></span>
							<span class="sr-only">Toggle Dropdown</span>
						</button>
						<ul class="dropdown-menu">
					         <li><a href="${ctx}/mgr/product/update/${product.id}" ><i class="icon-edit"></i>修改</a></li>
					         <li><a href="javascript:void(0);" class="delete_" rel="${product.id}"><i class="icon-trash"></i>删除</a></li>
					         <li class="divider"></li>
					     </ul>
					</div>	
					</td>
					<td style="color: #3280AE; cursor: pointer;" id="changeName${product.id}" ref="${product.title}">
					<a href="javascript:aclick('${product.id}');" >${product.title}</a></td>
						<td id="oldCategory_${product.id}" style="display: none;">${product.title}</td>
					<td>${product.intro}</td>
					<td><a href="${ctx}/mgr/product/detail/${product.id}"class="playerName btn btn-default" data-fancybox-type="iframe" rel="fancy" id="iframe">产品详情</a></td>
				</tr>
		  </c:if>
		</c:forEach>
		</tbody>
	</table>
	<div class="row">
		<div class="col-sm-4">
			<a class="btn btn-primary" href="${ctx}/mgr/product/create"><span class="glyphicon glyphicon-plus"></span>添加产品信息</a>
		<%-- 	<a class="btn btn-primary" href="${ctx}/mgr/category"><span class="glyphicon glyphicon-pushpin"></span>产品类型管理</a>--%>
		</div> 
		<div class="col-sm-8">
				<tags:pagination page="${products}" paginationSize="5"/>
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
function aclick(id){
	var name = $("#changeName"+id).attr("ref");
	$("#changeName"+id).html("<input id='put"+id+"' onmouseout='savename(\""+id+"\",this.value);' rel='"+id+"' value='"+name+"'/>");
}
function savename(id,name){
	var oldName = $("#oldCategory_"+id).text();
	$.ajax({
		url: "${ctx}/mgr/product/changeName?id="+id+"&name="+name,
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
	         url: "${ctx}/mgr/product/delete/"+id,
	         type: 'DELETE',
	         async:false,
	         contentType: "application/json;charset=UTF-8"
	       })
	       .done(function( html ) {
	    	   $("#deleteProduct_"+id).remove();
	    	   $(".page-header").after("<div id='message' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>产品&nbsp;'"+html.title+"'&nbsp;删除成功</div>");
	       }).fail( function(jqXHR, textStatus){
	    	  alert("删除失败jqXHR="+jqXHR+"textStatus="+textStatus);
	   });
     }
   });
});
$("#e2").select2({
    placeholder: "-----------请选择产品类型----------",
    allowClear: true
});
</script>
</body>

</html>