<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title></title>
</head>

<body>
	
	<font style="font-size: 14px">&nbsp;&nbsp;&nbsp;欢迎使用！</font>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>
			${message}
		</div>
	</c:if>
	<shiro:hasAnyRoles name='SYSADMIN'>
	<ul class="nav nav-pills ">
 		<li>
   		 <a href="${ctx }/mgr/crm/index?deadline=1">
      			<span class="badge pull-right">${expiring }</span>
     		 快到期租户数
    		</a>
  		</li>
  		<li>
   		 <a href="${ctx }/mgr/crm/index?deadline=2">
      			<span class="badge pull-right">${expired }</span>
     		 	已过期租户数
    		</a>
  		</li>
	</ul>
	</shiro:hasAnyRoles>
</body>
</html>
