<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
     <div class="container">
       <div class="navbar-header">
         <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
           <span class="sr-only">Toggle navigation</span>
           <span class="icon-bar"></span>
           <span class="icon-bar"></span>
           <span class="icon-bar"></span>
         </button>
         <a class="navbar-brand" href="#">华科移动终端微应用系统</a>
       </div>
       <div class="navbar-collapse collapse">
         <ul class="nav navbar-nav">
         
          <li class="dropdown" id="nav_index">
             <a href="${ctx}/mgr/index">首页</a>
           </li>
           <shiro:hasAnyRoles name='SYSADMIN'>
      <li class="dropdown" id="nav_sup_admin">
             <a href="#" class="dropdown-toggle" data-toggle="dropdown">平台管理 <b class="caret"></b></a>
             <ul class="dropdown-menu">
                <li><a href="<%=request.getContextPath()%>/mgr/crm/index">租户管理</a></li>
                <li><a href="<%=request.getContextPath()%>/mgr/auth/index">授权管理</a></li>
                <li><a href="<%=request.getContextPath()%>/mgr/auth/index">平台统计(未)</a></li>
                <li><a href="<%=request.getContextPath()%>/mgr/dict/index">数据字典</a></li>
             </ul>
           </li>
           </shiro:hasAnyRoles>
           <shiro:hasAnyRoles name='SYSADMIN,ADMIN'>
           <li class="dropdown" id="nav_sys">
             <a href="#" class="dropdown-toggle" data-toggle="dropdown">系统管理 <b class="caret"></b></a>
             <ul class="dropdown-menu">
                  <li><a href="<%=request.getContextPath()%>/mgr/crm/userList?uid=${currentTenancyID}">用户管理</a></li>
                  <li><a href="<%=request.getContextPath()%>/mgr/tenancyUser/index">会员管理</a></li>
                <li><a href="<%=request.getContextPath()%>/mgr/payment/settings">支付设置</a></li>
                <li><a href="<%=request.getContextPath()%>/mgr/feedback/index?uid=${currentTenancyID}">版本管理</a></li>
                <li><a href="<%=request.getContextPath()%>/mgr/baidu/index">消息推送</a></li>
                
                <li class="divider"></li>
                <li><a href="<%=request.getContextPath()%>/mgr/company/info?uid=${currentTenancyID}">企业资料维护</a></li>
                <li><a href="<%=request.getContextPath()%>/mgr/feedback/index?">意见反馈</a></li>
                <li class="divider"></li>
                <li><a href="<%=request.getContextPath()%>/mgr/auth/index">统计分析(未)</a></li>
             </ul>
           </li>
           </shiro:hasAnyRoles>
           <shiro:hasAnyRoles name='SYSADMIN,ADMIN'>
           <li class="dropdown" id="nav_channel">
             <a href="#" class="dropdown-toggle" data-toggle="dropdown">渠道管理 <b class="caret"></b></a>
             <ul class="dropdown-menu">

                  <li><a href="<%=request.getContextPath()%>/mgr/weixin/index">微信管理</a></li>
                  <li><a href="<%=request.getContextPath()%>/mgr/mobileResouce/showInfo">微网站管理</a></li>
        

             </ul>
           </li>
           </shiro:hasAnyRoles>
           <li class="dropdown">
             <a href="#" class="dropdown-toggle" data-toggle="dropdown">应用包管理 <b class="caret"></b></a>
             <ul class="dropdown-menu">
              <li><a href="<%=request.getContextPath()%>/mgr/product/showInfo">产品管理</a></li>
              <li><a href="<%=request.getContextPath()%>/mgr/order/showInfo">订单管理</a></li>
				<li><a href="<%=request.getContextPath()%>/mgr/groupon/showInfo">团购管理</a></li>
              <li class="divider"></li>
              <li><a href="<%=request.getContextPath()%>/mgr/news/showInfo">新闻资讯</a></li>
              <li><a href="<%=request.getContextPath()%>/mgr/supply/showInfo">供求管理</a></li>
                <li><a href="<%=request.getContextPath()%>/mgr/addressList/showInfo">通讯录管理</a></li> 
                <li><a href="<%=request.getContextPath()%>/mgr/customer/zshowInfo">会员企业管理</a></li>
                <li><a href="<%=request.getContextPath()%>/mgr/events/showInfo">活动管理</a></li>

                <li class="divider"></li>
                <li><a href="<%=request.getContextPath()%>/mgr/membercard/info">会员卡管理</a></li>
                <li><a href="<%=request.getContextPath()%>/mgr/events/index?uid=${currentTenancyID}">优惠券管理（未）</a></li>
                <li class="divider"></li>
                <li><a href="<%=request.getContextPath()%>/mgr/ernies/showInfo">营销互动</a></li>
                <li><a href="<%=request.getContextPath()%>/mgr/invitation/showInfo">论坛管理</a></li>
                <li><a href="<%=request.getContextPath()%>/mgr/questions/index?uid=${currentTenancyID}">调查问卷</a></li>
                
             </ul>
           </li>
         </ul>
        <shiro:user>
        <ul class="nav navbar-nav navbar-right">
          <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-user icon-white"></i>&nbsp;${currentTenancyName } (<shiro:principal property="name"/>)<b class="caret"></b></a>
            <ul class="dropdown-menu nav-list">
          <%-- <li><a href="${ctx}/mgr/todos">待办事宜</a></li>
          <li class="divider"></li>
                  <li><a href="${ctx}/mgr/profile">编辑个人资料</a></li> --%>
                  <li><a href="<%=request.getContextPath()%>/mgr/company/info?uid=${currentTenancyID}">编辑企业资料</a></li>
          <li><a href="${ctx}/logout">安全退出</a></li>
               </ul>
          </li>
        </ul>
        </shiro:user>
       </div><!--/.nav-collapse -->
       
     </div>
   </div>