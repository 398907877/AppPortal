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
	<title>关于我们</title>
	<link href="${ctx}/static/bootstrap/3.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<header class="bar bar-nav">
      <h1 class="title">${company.title}</h1>
    </header>
	<div class="content">
		<div class="row">
  			<div class="col-xs-12">
				<img src="${company.pic}"  alt="${company.title}" class="img-thumbnail">
				</div>
			</div>
		<div class="bs-callout bs-callout-info col-xs-12">
		    ${company.intro}
		</div>
		<div class="col-xs-12">
			${company.detail}
		</div>
		<div class="col-xs-12">
		<div class="slider" id="mySlider">
  			<div class="slide-group">
  				 <c:forEach items="${pictures }" var="pic">
					<div class="slide">
						<img src="${ pic.url}" class="img-thumbnail">
					</div>
				</c:forEach> 
    		<!-- 	<div class="slide">
    			 
     			 <img src="http://www.vitbbs.cn/uploads/huoche3/2011/20110917164303244.jpg" style="width:100%">
      				
  				</div>
  				 <div class="slide">
     			 <img src="http://photo.yupoo.com/nahco/C3V7yhmk/medish.jpg" style="width:100%">
      	
  				</div> -->
  				
  			</div>
  		</div>
  		</div>
	</div>
</body>
</html>