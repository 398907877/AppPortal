<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="header">
	<div id="title">

	<%-- <div align="center">
	<h1>
	 <a href="${ctx}">华科企业APP门户</a><small>--接入管理</small>
	 </h1>
	 </div> --%>

	<shiro:hasAnyRoles name='tadmin,admin,user'>
	<div class="navbar navbar-fixed-top navbar-inverse" >
			<div class="navbar-inner">
				<div class="container head_content">
					<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> 
						<span class="icon-bar"></span> 
						<span class="icon-bar"></span> 
						<span class="icon-bar"></span> 
					</a> 
					
					<div class="nav-collapse"> 
					<shiro:hasAnyRoles name='tadmin,admin,user'>
			           <ul class="nav">
				            <li class="dropdown" id="tenancy">
				                <a href="#" class="dropdown-toggle" data-toggle="dropdown">平台管理 <b class="caret"></b></a>
				                <ul class="dropdown-menu">
				                    <shiro:hasAnyRoles name='tadmin,admin'>
				                	  <li><a href="<%=request.getContextPath()%>/mgr/crm/index">租户管理</a></li>
				                    </shiro:hasAnyRoles>
				                	<li><a href="<%=request.getContextPath()%>/mgr/customer/index">租户客户管理</a></li>
				                
				                	<shiro:hasRole name="admin">
				                	<li><a href="<%=request.getContextPath()%>/mgr/contract/index">合同管理</a></li>
				                	<li><a href="<%=request.getContextPath()%>/mgr/auth/index">授权管理</a></li>
				                	<li><a href="<%=request.getContextPath()%>/mgr/supply/type">供求类型管理</a></li>
				                	</shiro:hasRole>
				                </ul>
			                </li>
			          	</ul>
			         </shiro:hasAnyRoles>
			     
			      
			          	<ul class="nav">
				            <li class="dropdown" id="service">
				                <a href="#" class="dropdown-toggle" data-toggle="dropdown" >服务包管理 <b class="caret"></b></a>
				                <ul class="dropdown-menu" id="serverPackage">
				                    <li><a href="<%=request.getContextPath()%>/mgr/product">产品管理</a></li>
				                	<li><a href="<%=request.getContextPath()%>/mgr/news">新闻资讯</a></li>
				                	<li><a href="<%=request.getContextPath()%>/mgr/company">企业介绍</a></li>
				                    <li><a href="<%=request.getContextPath()%>/mgr/supply">供求管理</a></li>
				                    <li><a href="<%=request.getContextPath()%>/mgr/invitation">论坛管理</a></li>
		                            <li><a href="<%=request.getContextPath()%>/mgr/forumUser/index">用户管理</a></li>     			                     
		                            <li><a href="<%=request.getContextPath()%>/mgr/addressList/index">通讯录管理</a></li> 		                     
				                </ul>
			                </li>
			          	</ul>
			          	
			          	   <shiro:user>
			<div class="btn-group pull-right member_bar">
				<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
					<i class="icon-user"></i> <shiro:principal property="name"/>
					<span class="caret"></span>
				</a>
			
				<ul class="dropdown-menu">
					<shiro:hasRole name="admin">
						<li><a href="${ctx}/admin/user">Admin Users</a></li>
						<li class="divider"></li>
					</shiro:hasRole>
					<li><a href="${ctx}/api">APIs</a></li>
					<li><a href="${ctx}/profile">Edit Profile</a></li>
					<li><a href="${ctx}/logout">Logout</a></li>
				</ul>
			</div>
		</shiro:user>
	        </div>
			  </div>
		  </div>
	  </div>

	   </shiro:hasAnyRoles>
	</div>
</div>
