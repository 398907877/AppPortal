<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="function" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Bootstrap-会员卡使用记录表</title>
    <link href ="${ctx }/static/bootstrap/3.1.1/ css/bootstrap.min.css" rel ="stylesheet " />
    <script src ="${ctx }/static/js/jquery.min.js" type="text/javascript "></script>
    <script src ="${ctx }/static/bootstrap/3.1.1/ js/bootstrap.min.js"></script>
	<link href="${ctx}/static/bootstrap/additions/lightbox/bootstrap-lightbox.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" /> 
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>	
	
	<script type="text/javascript" src="${ctx}/static/js/jquery-ui-1.8.21.custom.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.js"></script>	
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.pack.js"></script>	
    <script src="${ctx}/static/select2-3.4.6/select2.js"></script>
	<script src="${ctx}/static/bootstrap/additions/lightbox/bootstrap-lightbox.min.js" type="text/javascript"></script>
<style>
.div_background{position:relative;text-align: center; width: 360px; height: 180px; border: 1px solid darkred}
.div_logo{position:absolute;width:50px;height:50px;left:20px;top:20px}
.div_text1{position:absolute;width:220px;height:90px;left:70px;top:0px;padding-top:30px;padding-left:10px;color:#FFFFFF}
.div_text2{position:absolute;width:90px;height:90px;left:270px;top:0px;padding-top:30px;padding-left:20px;color:#FFFFFF}
.div_number{position:absolute;width:360px;height:90px;left:0px;top:90px;padding-top:50px;padding-left:30px}

.display_no{display:none}
.display_ok{display:block}
.add1{background-color:red}
.red{color:red;border:1px solid darkred}
</style>	
</head>
<body>
   <div class="container">
	<div class="row">
	    <div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>
        <div class="col-md-10 bs-callout bs-callout-info">
			<div>
				<h4>会员卡使用记录表</h4>
				<h4><small>进入该页列表分页方式展示所有会员卡使用记录</small></h4>
			</div>
	        <c:if test="${not empty message}">
		    <div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	        </c:if>
	    
		    <form id="queryForm" class="form-inline" role="form"  method="get" action="${ctx}/mgr/membercard/indexusage">
		      <div class="form-group">
				<label for="cardNumber">会员卡编号：</label> 
				<input name="search_LIKE_cardNumber" type="text" class="form-control" value="${param.search_LIKE_cardNumber}" placeholder="请输入会员卡编号"/>
				<label for="location">使用位置：</label> 
				<input name="search_LIKE_location" type="text" class="form-control" value="${param.search_LIKE_location}" placeholder="请输入位置"/>
				<input type="hidden" name="search_EQ_status" value="1">
				<c:if test="${''!=uid&&null!=uid }">
				<input type="hidden" name="search_EQ_uid" value="${uid}">
				</c:if> 
				<input type="submit" class="btn btn-success" value="查 找" />
			  </div>	
				<tags:sort />
			</form>
			
		<table class="table table-striped table-bordered table-condensed table-hover">
			<thead>
				<tr>
					<th title="编号" style="width:100px">编号</th>
					<th title="会员卡编号">会员卡编号</th>
					<th title="使用日期">使用日期</th>
					<th title="使用位置">使用位置</th>
					<th title="在线使用">是否在线使用</th>
					<th title="关联订单">关联订单</th>					
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${records.content}" var="cardRecord" varStatus="s">
					<tr>
						<td>
							<div class="btn-group">
								<button type="button" class="btn btn-info" disabled="disabled">操作</button>
								<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown">
							        <span class="caret"></span>
							        <span class="sr-only">Toggle Dropdown</span>
							      </button>
								<ul class="dropdown-menu">
									<li><a href="${ctx}/mgr/membercard/update/${cardRecord.id}">修改</a></li>
									<li><a href="${ctx}/mgr/membercard/delete/${cardRecord.id}">删除</a></li>
								</ul>
								${cardRecord.id}
							</div>
						</td>
						<td>
							<a href="${ctx}/mgr/membercard/getuser/${cardRecord.cardNumber}" class="fancybox" data-fancybox-type="iframe" rel="fancy" id="iframe">${cardRecord.cardNumber}</a>
						</td>
						<td>
							<fmt:formatDate value="${cardRecord.crtDate}" pattern="yyyy年MM月dd日" />
						</td>                      
						<td>${cardRecord.location}</td>
						<td>${cardRecord.isOnline}</td>
						<td>${cardRecord.relations}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div>
			<tags:pagination page="${records}" paginationSize="5"/>
		</div>
	</div>
   </div>	
  </div>	
<script type="text/javascript">
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
<script type="text/javascript">
$(document).ready(function () {
    $('.fancybox').fancybox({
        type: "iframe",
        padding: 0,
        fitToView: false,
        autoSize: false,
        width: 718,
        height: 503
    });
    
		$("#submit").click(function(){
			$("#onsubmit").trigger("click"); 
		});
		$("#clearMember").click(function(){
			$("input[type=text]").val("");
		});    
});
</script>	
	
	
</body>
</html>