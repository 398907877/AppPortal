<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Bootstrap-发放或领取会员卡</title>
    <link href ="${ctx }/static/bootstrap/3.1.1/ css/bootstrap.min.css" rel ="stylesheet ">
    <script src ="${ctx }/static/js/jquery.min.js"></script>
    <script src ="${ctx }/static/js/jquery.validate.js"></script>
    <script src ="${ctx }/static/bootstrap/3.1.1/ js/bootstrap.min.js"></script>
	<link href="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap-wysihtml5.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/static/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" /> 
	<link href="${ctx}/static/bootstrap/additions/lightbox/bootstrap-lightbox.min.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/static/js/jquery-ui-1.8.21.custom.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.js"></script>	
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.pack.js"></script>	
	<script src="${ctx}/static/bootstrap/additions/lightbox/bootstrap-lightbox.min.js" type="text/javascript"></script>    
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/wysihtml5-0.3.0.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap3-wysihtml5.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/locales/bootstrap-wysihtml5.zh-CN.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.js" type="text/javascript"></script>
</head>

<body>
  <div class="container">
	<div class="row">
	    <div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>
		<div class="col-md-10 bs-callout bs-callout-info">
	      <div>
				<h4>会员卡领取和发放</h4>
				<h4><small>用于会员卡的领取和发放,将会员卡与会员昵称进行绑定，同时产生会员卡的卡号，获得方式，获得日期等属性</small></h4>
			</div>
		    <div>			
		      <c:if test="${not empty message}">
			  <div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
		    </c:if>
		    </div>
		    
		<form:form id="registerForm" method="post" modelAttribute="cardRegister" cssClass="form-horizontal" role="form" action="${ctx}/mgr/membercard/register"  enctype="multipart/form-data">
            <div class="form-group">
	          <label class="col-sm-2 control-label">会员卡类别:</label>
	            <div class="col-sm-10">			
				   <select path="cardId" name="cardId" style="min-width:240px;" class="populate placeholder select2-offscreen" tabindex="-1" title="-----请选择会员卡类别-----">
				       <option value="">-----请选择会员卡类别-----</option>
                       <c:forEach items="${mcards}" var="mcard">
						<c:if test="${mcard.status!='0'}">
                          <option value="${mcard.id}">${mcard.cardName }</option>
                         </c:if> 
                       </c:forEach>
                   </select>			
	            </div>		
            </div>					
			
			<div class="form-group">
	          <label class="col-sm-2 control-label">获得方式:</label>
	            <div class="col-sm-10">			
				   <select path="getMethod" name="getMethod" style="min-width:240px;" class="populate placeholder select2-offscreen">
				   	   <option value="">----请选择方式----</option>
				       <option value="领取">领取</option>
				       <option value="发放">发放</option>
                   </select>			
	            </div>		
            </div>	
			
			<div class="form-group">
	          <label class="col-sm-2 control-label"><a href="${ctx}/mgr/membercard/selects " class="fancybox" data-fancybox-type="iframe" rel="fancy" id="iframe"">选择会员:</a></label>
	          <input path="selectMember" type="text" name="selectMember" id="addMember" />
	          <input type="button" id="clearMember" value="重新选择" />
            </div>			
			
		    <div class="form-group">
		       <label class="col-sm-2 control-label"></label>
			   <div class="col-sm-10">
				<input type="submit" id="onsubmit" class="display" style="display:none"/>
				<input  value="提交"  class="btn btn-primary" id="submit"  type="button"/>
				<input id="cancel_btn" class="btn btn-primary" type="button" value="返回" onclick="history.back()"/>
			   </div>
		    </div>
		</form:form>		    
     </div>
    </div>
  </div>
 <script>
$().ready(function() {
 $("#registerForm").validate({
rules:{
					cardId:{
						required:true
					},
					getMethod:{
						required:true
					},				
					selectMember:{
						required:true
					}
				},messages:{
					cardId:{
						required:"必须选择"
					},
					getMethod:{
						required:"必须选择"
					},
					selectMember:{
						required:"必须选择"
					},						
				} 	
 });
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