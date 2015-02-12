<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
	<head>
		<title>${demand.title}</title>
		<link href="${ctx}/static/mobile/event.css" type="text/css" rel="stylesheet" />

		<style type="text/css" >
			body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,textarea,p,blockquote,th,td {padding: 0;margin: 0;}
			
			#info img{width:55%} 
			span{font-size:20px;color:#272626;}
			.showtxt{font-size:17px; color: #777;margin-bottom:15px;margin-top:3px;}
		</style>
	</head>
	<body>
		<header class="bar bar-nav">
			 <a class="icon icon-left-nav pull-left" style="font-size:17px;padding-top:15px" href="javascript:history.go(-1);">返回</a>
	      <c:choose>
			<c:when test="${type eq 0 }">
				<h1 class="title">供应列表</h1>
			</c:when>
			<c:otherwise>
				<h1 class="title">需求列表</h1>
			</c:otherwise>
		</c:choose>
	    </header>
		<div class="content">
			<div class="slider">
		        <div class="slide-group">
			      	<c:forEach var="pic" items="${pictures}">
			          <div class="slide">
			            <img src="${pic.filePath}" width="100%" height="50%">
			          </div>
			       </c:forEach>
		        </div>
	    	</div>

        	<div class="table-view-cell" id="info" style="padding-left:10px;padding-right:15px;line-height:30px">

                <div>
	        	<span  >标题：</span>
	        	<div  class="showtxt"  style="line-height:20px;">${demand.title}</div>
	        	</div>
	        	    <div>
	        	<span  >开始日期：</span>
	        	<span class="showtxt">${fn:substring(demand.startDate,0,10)}</span>
	        	</div>
	        	<div>
	        	<span  >截止日期：</span>
	        	<span class="showtxt" >${fn:substring(demand.endDate,0,10)}</span>
	        	</div>
	        	<div>
	        	<span>所在地：</span>
	        	<span class="showtxt" >${demand.address}</span>
	        	</div>
	        	<div>
	        	<span  >价格：</span>
	        	<span class="showtxt">${demand.price}</span>
	        </div>
	        	<div>
	        	<span >总量：</span>
	        	<span class="showtxt">${demand.total}</span>
	        	</div>
	        	<div>
	        	<span  >起购数：</span>
	        	<span class="showtxt">${demand.limitNum}</span>
	        	</div>
	        	<div>
	        	<span >联系人：</span>       	
	        	<span class="showtxt">${demand.toUser}</span>
	        	</div>
	        	<div>
	        	<span >联系电话：</span>
	        	<span class="showtxt">${demand.tel}</span>
	        	</div>
	        	<div>
	        	<span >详细内容：</span>
	        	<div class="showtxt" style="line-height:20px;">${demand.detailInfo}</div>
                </div>
	        	<div>
        	</div>
	    </div>
	</body>
</html>