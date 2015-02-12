<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>供求信息管理</title>
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
 <div class="page-header" style="margin-top:0px;">
			<h2  style="margin-top:0px">供求信息管理</h2>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	 <div  class="bs-callout bs-callout-info">
		    <form class="form-inline" action="#">
				<label>供求类型：</label>
				<select id="selectType" name="search_EQ_type" style="min-width:220px;" class="populate placeholder select2-offscreen se2" tabindex="-1" title="">
				<option value=""  selected="selected">---------请选择供求类型---------</option>
				<option value="0" ${ param.search_EQ_type == 0 ? 'selected':''}>供（提供给他人信息）</option>
				<option value="1" ${ param.search_EQ_type == 1 ? 'selected':''}>求（想要获得的信息）</option>
				</select>
				<!--  
				<select id="selectinfoType" name="search_EQ_typeId" style="min-width:220px;" class="populate placeholder select2-offscreen se2" tabindex="-1" title="">
				<option value="">---------请选择所属行业---------</option>
				</select>
				-->
				<label>供求标题：</label>
				<input type="text" name="search_LIKE_title" class="form-control">
				<input type="hidden" name="search_EQ_status" value="1">
				<c:if test="${''!=uid&&null!=uid }">
				<input type="hidden" name="search_EQ_uid" value="${uid}">
				</c:if>
				<button type="submit" class="btn btn-success btm-sm" name="account-tab" id="search_btn">查询</button>
				<tags:sort/>
				</form>
	    
	    </div>
	
   <table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>操作</th><th>信息标题<lable style="color:green;">（提示*点击可修改标题）</lable></th><th>发布时间<th>联系人<th>信息详情</th></tr></thead>
		<tbody>
		<c:forEach items="${supplyDemands.content}" var="supply">
		
		<c:if test="${supply.status!='0'}">
			<tr id="deleteProduct_${supply.id}">
					<td width="100px">
					<div class="btn-group">
							<a class="btn btn-info" href="#">操作</a>
							<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
							    <span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
					            <li><a href="${ctx}/mgr/supply/update/${supply.id}" ><i class="icon-edit"></i>修改</a></li>
					           <li><a href="javascript:void(0);" class="delete_" rel="${supply.id}"><i class="icon-trash"></i>删除</a></li>
					            <li class="divider"></li>
					          </ul>
						</div>	
					</td>
					<td <%-- style="color: #3280AE; cursor: pointer;" id="changeName${supply.id}" ref="${supply.title}" --%>>
					<%-- <a href="javascript:aclick('${supply.id}');" > --%>${supply.title}<!-- </a> --></td>
					<td>${supply.crtDate}</td>
					<td>${supply.toUser}</td>
					<td><a href="${ctx}/mgr/supply/detail/${supply.id}"class="playerName btn btn-default" data-fancybox-type="iframe" rel="fancy" id="iframe">信息详情</a></td>
				</tr>
		  </c:if>
		</c:forEach>
		</tbody>
	</table>
	<div class="row">
		<div class="col-sm-4">
			<a class="btn btn-primary" href="${ctx}/mgr/supply/create"><span class="glyphicon glyphicon-plus"></span>添加供求信息</a>
		<%-- 	<a class="btn btn-primary" href="${ctx}/mgr/supply/type"><span class="glyphicon glyphicon-pushpin"></span>供求信息类型管理</a>--%>
		</div> 
		<div class="col-sm-8">
				<tags:pagination page="${supplyDemands}" paginationSize="5"/>
		</div>
	</div>
</div>
</div>
	
<script type="text/javascript">
$(".se2").select2({
    placeholder: "-----------请选择产品类型----------",
    allowClear: true
});
	$('.playerName').fancybox({
		autoDimensions:false,
		width:800,
		height:500
	});
function aclick(id){
	var name = $("#changeName"+id).attr("ref");
	$("#changeName"+id).html("<input id='put"+id+"' onmouseout='savename(\""+id+"\",this.value);' rel='"+id+"' value='"+name+"'/>");
}
function savename(id,name){
	$.ajax({
		url: "${ctx}/mgr/supply/changeName?id="+id+"&name="+name,
		type: 'POST',
		contentType: "application/json;charset=UTF-8",
		success: function(date){
				$("#changeName"+id).html('<a href="javascript:aclick('+id+')" >'+name+"</a>");
				$("#changeName"+id).attr("ref",name);
				 $(".page-header").after("<div id='message' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>供求信息修改成功</div>");
		},error:function(xhr){
			alert('错误了，请重试1');
		}
	});
}

	$(".delete_").click(function(){
		var id = $(this).attr("rel");
		if(confirm("确定要删除吗?")){ 
           $.ajax({
	         url: "${ctx}/mgr/supply/delete/"+id,
	         type: 'DELETE',
	         async:false,
	         contentType: "application/json;charset=UTF-8"
	       })
	       .done(function( html ) {
	    	   $("#deleteProduct_"+id).remove();
	    	   $(".page-header").after("<div id='message' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>产品删除成功</div>");
	       }).fail( function(jqXHR, textStatus){
	    	  alert("删除失败jqXHR="+jqXHR+"textStatus="+textStatus);
	   });
     }
   });
	
/*    $("#selectType").change(function(){
	 $.ajax({
         url: "${ctx}/mgr/supply/select/type?type="+$(this).val(),
         type: 'POST',
         async:false,
         contentType: "application/json;charset=UTF-8"
       })
       .done(function( date ) {
    	   $("#selectinfoType").empty();
    	   $.each(date,function(k,val){
    		    $("#selectinfoType").append("<option  value='"+val.id+"'>"+val.supplyDemandType+"</option>");
    		  }); 
    	   $("#selectinfoType").prepend("<option value=''>---------请选择信息类型---------</option>");
    	   $("#selectinfoType").select2("val", "");
       }).fail( function(jqXHR, textStatus){
    	  alert("删除失败jqXHR="+jqXHR+"textStatus="+textStatus);
   });
  }); */
</script>
</body>
</html>
