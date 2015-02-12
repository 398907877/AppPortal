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
    <meta charset="UTF-8">
    <title>新闻资讯</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

  </head>
  <body>

<div id="category" class="popover">

    <ul class="table-view">
	   <li class="table-view-cell">		
			  新闻类型
		</li>
    	<li class="table-view-cell">
    		<a class="navigate-right" href="javascript:turn(${UID});" data-transition="slide-in">
			  全部新闻
			</a>
		</li>
    	<c:forEach var="category" items="${categories}">
    		  <li class="table-view-cell">
			    <a class="navigate-right" href="javascript:turn(${UID},${category.id});" data-transition="slide-in">
			      ${category.name}
			    </a>
			  </li>
		</c:forEach>
    </ul>
</div>




    <header class="bar bar-nav">	
      <a class="pull-right" href="#category">
            <span class="icon icon-w3c-list" style="font-size: 18px;">分类</span></a> 
            <a class="icon icon-left-nav pull-left" style="font-size:18px;padding-top:12px" href="javascript:go();">返回</a>
            
      <h1 class="title">新闻资讯</h1>
    </header>

    <div class="content">

      <div class="slider">
        <div class="slide-group">
	      	<c:forEach var="new" items="${banners.content}">
						<div class="slide">
							<a class="" href="${new.url}">
			      				<img src="${new.pic}" alt="Argo" width="100%" height="50%">
			      			</a>
			      			<p style="color:#ffffff;" align="center">${new.title}</p>
			      		</div>
	      	</c:forEach> 
        </div>
      </div>

      <ul class="table-view" id="table-view">
     
      	<c:choose>
			<c:when test="${category.name eq null }">
				<li class="table-view-cell table-view-divider">新闻资讯</li>
			</c:when>
			<c:otherwise>
				<li class="table-view-cell table-view-divider">新闻资讯--${category.name}</li>
			</c:otherwise>
		</c:choose>
      
		<c:forEach var="new" items="${news.content}">
			  <li class="table-view-cell media">
			    <a href="${new.url}">
			      <img class="media-object pull-left" width="92px" height="62px" src="${new.pic}">
			      <div class="media-body">
			      	${new.title}
			        <p>
			         <c:choose> 
    					<c:when test="${fn:length(new.intro)>30 }"> 
     						<c:out escapeXml="false" value="${fn:substring(new.intro,0,30) }..." /> 
    					</c:when> 
    					<c:otherwise> 
     						<c:out escapeXml="false" value="${new.intro}" /> 
    					</c:otherwise> 
   					</c:choose>
			       </p>
			      </div>
			      <c:choose>
					<c:when test="${new.stick eq null }">
					
					</c:when>
					<c:otherwise>
						<span class="badge badge-negative">置顶</span>
					</c:otherwise>
				</c:choose>
			      
			    </a>
			  </li>
		  </c:forEach>
	
		  
      </ul>

     <div style="padding: 14px 40px;">
       <input type="hidden" id="UID" name="UID" value="${UID}"/>
       <input type="hidden" id="categoryId" name="categoryId" value="${categoryId}"/>
	   <button class="btn btn-block btn-primary" style="background-color: #f8f9fa;color:black;border: 1px solid #ccc;" onclick="more()">点击加载更多</button>
	 </div>
    </div>
	<script type="text/javascript">
	    function turn(uid,category)
	    {
	    	var url = "<%=request.getContextPath()%>/m/news?uid="+uid;
	    	if(category)
	    	{
	    		 url = url + "&categoryId="+category;
	    	}
	    	location.href = url;
	    }
	    function go()
	    {
	    	var uid = $("#UID").val();
	    	var url = "<%=request.getContextPath()%>/m/index?uid="+uid;
	    	location.href = url;
	    }
	
		var pn =1;
		function more(){
			pn = pn+1;
			var UID = $("#UID").val();
			var categoryId = $("#categoryId").val();
			$.ajax({
				url: '<%=request.getContextPath()%>/m/news/list?UID='+UID+'&pageNum='+pn+'&categoryId='+categoryId, 
				type: 'GET',
				contentType: "application/json;charset=UTF-8",
				dataType: 'text',
				success: function(data){
					var parsedJson = $.parseJSON(data);
					jQuery.each(parsedJson, function(index, itemData) {
						
						if(itemData.intro.length>30)
						{
							var intro = itemData.intro.substring(0,30)+"...";	
						}
						else
						{
							var intro = itemData.intro;	
						}
						
						 if(itemData.stick!=null){
							 $("#table-view").append("<li class='table-view-cell media'><a data-transition='slide-in' href='"+itemData.url+"'><img class='media-object pull-left' width='92px' height='62px' src='"+itemData.pic+"'><div class='media-body'>"+itemData.title+"<p>"+intro+"</p></div><span class='badge badge-negative'>置顶</span></a></li>"); 
						 }else{
							 $("#table-view").append("<li class='table-view-cell media'><a data-transition='slide-in' href='"+itemData.url+"'><img class='media-object pull-left' width='92px' height='62px' src='"+itemData.pic+"'><div class='media-body'>"+itemData.title+"<p>"+intro+"</p></div></a></li>"); 

						 }
					});
				}
			});

		}
	
	</script>
  </body>
</html>
