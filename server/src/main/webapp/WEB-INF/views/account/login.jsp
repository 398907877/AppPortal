<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>登录页</title>
</head>

<body>
	<div class="row">
	<div class="col-sm-8 hidden-xs">
		<img class="login-slide"></img>
	</div>
	<div class="col-sm-4 well login-form">
		<form class="form-horizontal" role="form" action="${ctx}/login" method="post">
			<h2 class="text-center">华科移动终端微应用</h2>
			<%
			String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
			if(error != null){
			%>
				<div class="alert alert-error input-medium controls">
					<button class="close" data-dismiss="alert">×</button>登录失败，请重试.
				</div>
			<%
			}
			%>
		
		  <div class="form-group">
		    <label for="uid" class="col-sm-3 control-label">企业编号</label>
		    <div class="col-sm-9">
		      <input type="text" id="uid" name="uid"  value="${uid}" class="form-control input-medium required" placeholder="请输入企业编号">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="username" class="col-sm-3 control-label">登录名</label>
		    <div class="col-sm-9">
		      <input type="text" id="username" name="username"  value="${username}" class="form-control input-medium required" placeholder="请输入登录名">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="password" class="col-sm-3 control-label">登录密码</label>
		    <div class="col-sm-9">
		      <input type="password" id="password" name="password" placeholder="请输入密码" class="form-control input-medium required">
		    </div>
		  </div>
		    <div class="form-group">
		    <label for="password" class="col-sm-3 control-label">验证码</label>
		    <div class="col-sm-9">
		      <input type="text" id="captcha" name="captcha" placeholder="请输入验证码"  class="form-control  required">
		    </div>
		
		  </div>
		  <div class="form-group">
		        <label for="password" class="col-sm-3 control-label"></label>
		         <div class="col-sm-9">
		  			<img alt="验证码" src="images/kaptcha.jpg" title="点击更换" id="img_captcha" onclick="javascript:refreshCaptcha();"><br/>
                    看不清<a href="javascript:void(0)" onclick="javascript:refreshCaptcha()">换一张</a>
		  		</div>
		  </div>
		  
		  <div class="form-group">
		  	<div class="col-sm-3 control-label"></div>
		    <div class="col-sm-9">
		      <button type="submit" class="btn btn-default">登 录</button>
		    </div>
		  </div>
		</form>
	</div>
	</div>
	<script>
		$(document).ready(function() {
			$("#loginForm").validate();
			
		});
		function refreshCaptcha(){  
		    document.getElementById("img_captcha").src="${ctx }/images/kaptcha.jpg?t=" + Math.random();  
		}  
	</script>
</body>
</html>
