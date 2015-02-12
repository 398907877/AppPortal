<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html ng-app="weixin">
<head>
	<title>微信接入管理</title>
	
	<script type="text/javascript">
	function getAccessToken(){

     	
		$.ajax({
		type: "get", 
		url: "${ctx}/mgr/weixin/getAccessToken", 
		dataType: 'text',//返回的类型，对象的话改成 json即可
		//data:JSON.stringify(datatoservice),
		contentType:"application/json;charset=UTF-8",
		success: function (data) {
			
			$("#accessToken").val(data);
			
			return false;
		   
		           },
		 error: function(){
		        	   alert('操作错误,请与系统管理员联系!');
		        	   }
		});
		
		
	}
	
	</script>
</head>
<body ng-controller="WeixinController">
	<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
			<div class="bs-callout bs-callout-info">
				<h4>微信渠道接入设置</h4>
				<p>用于登记微信平台的基础设置项，服务号需要填完整项，订阅号只需填写Token项。</p>
			</div>

			<form:form role="form" method="post" modelAttribute="weixinCfg" action="${ctx}/mgr/weixin/saveCfg">
			  <div class="form-group">
			    <label for="exampleInputEmail1">appid</label>
			    <input type="text" class="form-control" id="appid" name="appid"  value="${cfg.appid }"    >
			    <span class="help-block">看看该填什么?</span>
			  </div>
			  <div class="form-group">
			    <label for="exampleInputPassword1">Secure</label>
			    <input type="text" class="form-control" id="secret" name="secret"    value="${cfg.secret }">
			    <span class="help-block">看看该填什么?</span>
			  </div>
			  <div class="form-group">
			    <label for="exampleInputPassword1">Token</label>
			    <input type="text" class="form-control" id="token" name="token"   value="${cfg.token }">
			    <span class="help-block">看看该填什么?<span class="glyphicon glyphicon-exclamation-sign"></span></span>
			  </div>
			  <div class="form-group">
			    <label for="exampleInputPassword1">AccessToken</label>
			    <input type="text" class="form-control" id="accessToken"   name="accessToken" readonly="readonly" >
			    
			    <a    href="#"    onclick="getAccessToken()"><span class="help-block">点击获取按钮获取</span></a>
			    
			  </div>
			  <button type="submit" class="btn btn-default">保存</button>
			  <br/><br/>
			  <c:if test="${not empty msg}">
		       <div id="message" class="alert alert-success">
		    	<button data-dismiss="alert" class="close">×</button>
		     	${msg}
	         	</div>
	            </c:if>
			</form:form>
		</div>
	</div>
	<script type="text/javascript" src="<c:url value="/static/angularjs/angular.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/static/angularjs/module/Weixin.js" />"></script>
</body>
</html>