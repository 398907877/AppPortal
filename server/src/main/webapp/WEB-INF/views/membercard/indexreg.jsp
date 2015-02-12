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
    <title>Bootstrap-所有会员卡列表显示</title>
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
				<h4>会员卡领取与发放</h4>
				<h4><small>会员卡与会员的绑定情况，管理会员卡与会员的绑定与生效，会员卡和会员的信息查询</small></h4>
			</div>
	        <c:if test="${not empty message}">
		    <div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	        </c:if>
	    
		    <form id="queryForm" class="form-inline" role="form"  method="get" action="${ctx}/mgr/membercard/indexreg">
		     <div class="form-group">
                <label for="nickName">会员名称:</label>
				<input name="search_LIKE_nickName" type="text" class="form-control" value="${param.search_LIKE_nickName}" placeholder="请输入会员名称进行查询"/>
				<input type="hidden" name="search_EQ_status" value="1">
				<c:if test="${''!=uid&&null!=uid }">
				<input type="hidden" name="search_EQ_uid" value="${uid}">
				</c:if> 
				<input type="submit" class="btn btn-success" value="查询" />
			  </div>
			  <tags:sort />	
			</form>
		
		<table class="table table-striped table-bordered table-condensed table-hover">
			<thead>
				<tr>
					<th title="编号" style="width:100px">编号</th>
					<th title="会员名称">会员名称</th>
					<th title="会员卡名称">会员卡名称</th>
					<th title="会员卡编号">会员卡编号</th>
					<th title="会员卡持有者">会员卡持有者</th>
					<th title="获卡方式">获卡方式</th>
					<th title="获卡日期">获卡日期</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${registers.content}" var="cardRegister" varStatus="s">
					<tr>
						<td>
							<div class="btn-group">
								<button type="button" class="btn btn-info" disabled="disabled">操作</button>
								<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" disabled="disabled">
							        <span class="caret"></span>
							        <span class="sr-only">Toggle Dropdown</span>
							      </button>
								<ul class="dropdown-menu">
									<li><a href="${ctx}/mgr/membercard/update/${cardRegister.id}">修改</a></li>
									<li><a href="${ctx}/mgr/membercard/delete/${cardRegister.id}">删除</a></li>
								</ul>
							</div>
						</td>
						<td>
							<a href="${ctx}/mgr/membercard/showuser/${cardRegister.nickName}" class="fancybox" data-fancybox-type="iframe" rel="fancy_${cardRegister.id}" id="iframe">${cardRegister.nickName}</a>
						</td>
						<td>
                          <c:forEach items="${mcards}" var="mcard">
                            <c:if test="${mcard.id==cardRegister.cardId}">
                       		 <div><a href="${ctx}/mgr/membercard/showcard/${mcard.id}" class="fancybox" data-fancybox-type="iframe" rel="fancy_${cardRegister.id}" id="iframe">${mcard.cardName}</a></div>
                       		</c:if>  
                          </c:forEach>
                       </td>
						<td><a href="${ctx}/mgr/membercard/getuser/${cardRegister.cardNumber}" class="fancybox" data-fancybox-type="iframe" rel="fancy_${cardRegister.id}" id="iframe">${cardRegister.cardNumber}</a></td>
						<td>${cardRegister.holderName}</td>
						<td>${cardRegister.getMethod}</td>
						<td>
							<fmt:formatDate value="${cardRegister.crtDate}" pattern="yyyy年MM月dd日" />
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>
			<a class="btn btn-primary" href="${ctx}/mgr/membercard/release"><span class="glyphicon glyphicon-plus"></span>领取或发放会员卡</a>
			<tags:pagination page="${registers}" paginationSize="5"/>
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