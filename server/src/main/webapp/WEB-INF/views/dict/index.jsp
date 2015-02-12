<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据字典管理</title>
<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
	<script src="${ctx}/static/select2-3.4.6/select2.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/jquery-ui-1.8.21.custom.min.js"></script>
	<script type="text/javascript" src="<c:url value="${ctx}/static/js/jquery.fancybox.pack.js?v=2.1.3" />"></script>
</head>
<body>
	<div class="page-header">
    		<h2>数据字典管理</h2>
  		</div>
  		<c:if test="${not empty message}">
			<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
		</c:if>
  		<div class="bs-callout bs-callout-info">
  		<form id="queryForm" class=" form-inline" method="get" action="${ctx }/mgr/dict/index">
				<label >ClassName:</label>
				
				<select name="search_EQ_className" style="min-width:220px;" class="populate placeholder select2-offscreen se2" tabindex="-1" title="">
					<option value="">------请选择ClassName------</option>	
					<c:forEach items="${ classNames}" var="cn"><option value="${cn }" <c:if test="${ param.search_EQ_className eq cn}">selected</c:if> >${cn }</option></c:forEach>				
				</select>
				<input type="submit" class="btn btn-success"  value="查 找" />
		</form>
		</div>
		<div class="row">

		<div class="col-sm-8">
		<!-- (<span  style="width:150px;color:#8B3A3A">拖动行更改排序</span>) -->
		<table class="table table-striped table-bordered table-condensed" id="table">
			<thead>
				<tr>
					<th title="ID" width="120px">操作</th>
					<th title="ClassKey" width="199px">键</th>
					<th title="value" width="249px">值</th>
					<th title="ClassName" width="200px">ClassName</th>
					<th title="seq" width="200px">排序
		  			<span style="margin-left:20px;"></span>&nbsp;<span id="prompt" style="color:#4169E1"></span>
					</th>
				</tr>
			</thead>
			<tbody id="tbody">
			<c:forEach items="${dicts.content}" var="item">
				<tr id="${item.iDictionary }" value="${item.seq}">
					<td id="iDictionary" value="${item.iDictionary}">
					<div class="btn-group">
							<a class="btn btn-info" href="#">操作</a>
							<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
							    <span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
					            <li><a href="${ctx}/mgr/dict/update/${item.iDictionary}"><i class="icon-edit"></i>修改</a></li>
					          	<li><a href="javascript:void(0);" rel="${ item.iDictionary}" class="delete"><i class="icon-th"></i>删除</a></li>
					        </ul>
					</div>
					</td>
					<td>${item.key}</td>
					<td>${item.value}</td>
					<td>${item.className}</td>
					<td id="seq">${item.seq}</td>
				</tr>
			</c:forEach>	
			</tbody>
		</table>
		<div class="row">
			<div class="col-sm-4">
				<a href="${ctx}/mgr/dict/create" class="btn btn-primary" id="addBtn">添加字典</a>
			</div>
			<div class="col-sm-8">
				<tags:pagination page="${dicts}" paginationSize="5"/>
			</div>
		</div>
	
		</div>
		<div class="col-sm-4" >字典描述：
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>键</th>
					<th>值</th>
					<th>排序</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>元素的value值</td>
					<td>界面的显示值</td>
					<td>显示顺序</td>
				</tr>	
			</tbody>
		</table>
		<table class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th>ClassName</th>
					<th>描述</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>ROLE</td>
					<td>用户角色</td>
				</tr>
				<tr>
					<td>BIZ_CODE</td>
					<td>业务模块列表</td>
				</tr>	
				<tr>
					<td>ORDER_STATE</td>
					<td>订单</td>
				</tr>	
				<tr>
					<td>STATUS</td>
					<td>状态</td>
				</tr>	
			</tbody>
		</table>
		</div>
		</div>
		
		<script type="text/javascript">
		var fixHelper = function(e, ui) {  
      		 ui.children().each(function() {  
          		 $(this).width($(this).width());     
      		  });  
      		 return ui;  
      		 };
		$(document).ready(function(){
			$(".se2").select2({
			    placeholder: "请选择一个分组",
			    allowClear: true
			});
			var $list=$("#tbody");
       		var old_order=[];
       		var ids=[];
       		 $list.children().each(function() {
          		 old_order.push($(this).attr("value"));   
       		 }); 
              $("#table tbody").sortable({  
                 helper: fixHelper,    
       		    cursor: 'move',
                  axis:"y",  
                  start:function(e, ui){  
                     ui.helper.css({"background":"#ccc"});    
       				return ui;
                 },  
                 stop:function(e, ui){
       				return ui;  
                  },
                  update:function(){
       				 $list.children().each(function(index) {
       					 $(this).find("#seq").text(old_order[index]);  
       					 ids.push($(this).find("#iDictionary").attr("value"));
       				 });
       				  $.ajax({   
       		                type: "get",   
       		                url: "<%=request.getContextPath()%>/mgr/dict/updateSeq", 
       		                contentType: "application/json;charset=UTF-8",
       		                data:"id="+ids.toString()+"&order="+old_order.toString(),
       		                success: function(msg) { 
       		                 	$("#prompt").text("操作成功");
       		                 	setInterval(function(){
       								$("#prompt").text("");
       							},2000);
       		                }
       		           });   
       				ids=[];
       			}
              }).disableSelection();    
		});
			$(function (){
				$(".delete").click(function(){
					if( confirm("确定删除吗？") == true){
						$.ajax({
							url: '<%=request.getContextPath()%>/mgr/dict/delete/' + $(this).attr("rel"), 
							type: 'DELETE',
							contentType: "application/json;charset=UTF-8",
							success: function(data){
								var da = eval("(" + data + ")");
								if(da.status == "ok"){
									location.href = location.href;
								}else{
									alert("删除失败请重试!");
								}
								
							},error:function(){}
						});
						return;
					}
					});
				
				
			});

       		
		</script>
</body>
</html>