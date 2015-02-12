<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html >
<head>
	<title>微信关键字list</title>
<link href="${ctx}/static/zebra_tooltips/zebra_tooltips.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/zebra_tooltips/zebra_tooltips.js" type="text/javascript"></script>
<script src="${ctx}/static/zebra_tooltips/zebra_tooltips.src.js" type="text/javascript"></script>
<script type="text/javascript">  
$(document).ready(function() {
    new $.Zebra_Tooltips($('.zebra_tips1'));
});
</script>
	
</head>
<body >
	<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
	
		
		
			<div class="bs-callout bs-callout-info">
			<h4   style="color: #222;">微信关键字设置    
			     <span   style="font-size:x-small;"  class="label label-default">新增</span>
			 </h4>
				
				<p >用于登记微信平台关键字内容。</p>
			</div>


    <br/>
	<div>	

      
      <form class="navbar-form navbar-left"  method="get"  action="${ctx}/mgr/weixin/keywordlist">
        <div class="form-group">
          <label>关键字名称：</label> 
          <input   name="search_LIKE_word"   value="${param.search_LIKE_word}"  type="text" class="form-control" placeholder="输入查找的名称">
        </div>
         
        <button type="submit" class="btn btn-default">查找</button>
      </form>
      <tags:sort />
      
          <br/> <br/> <br/>
           <table class="table table-hover table-bordered">
				<thead>
					<tr>
						<th>
							操作
						</th>
						<th>
							关键字名称
						</th>
						<th>
							规则
						</th>
						<th>
							创建时间
						</th>

					</tr>
				</thead>
				<tbody>
				<c:forEach items="${keywords.content}" var="keyword">
					<tr>
                 		<td>

	                		<div class="btn-group">
								<a class="btn btn-info" href="#">操作</a> <a
									class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
									<span class="caret"></span>
								</a>
								<ul class="dropdown-menu">
									<li><a
										href="${ctx}/mgr/weixin/keywords/edit?id=${keyword.id}"><i
											class="icon-edit"></i>修改</a></li>
									
									<li><a href="${ctx}/mgr/weixin/keywords/delete?id=${keyword.id}"
										class="del"><i class="icon-th"></i>删除</a></li>
						
								</ul>
							</div>
						</td>
						<td>
							${keyword.word}
						</td>
						<td>
						 <a   href="#" class="zebra_tips1" title="<span class='label label-success'><tags:dict className='WP_REPLY_CATE'   value="${keyword.rule.wpCategory}"></tags:dict></span>
						 
						 <c:if test="${keyword.rule.wpCategory eq 'text'}">
									${keyword.rule.text}
								</c:if>
										<c:if test="${keyword.rule.wpCategory eq 'url'}">
									${keyword.rule.text}
								</c:if>
									${ keyword.rule.chineseProxyInterFace} 
						 
						 
						 "  >${keyword.rule.title}</a>
						</td>
						<td>
							
							<fmt:formatDate value="${keyword.keywordCreate}" pattern=" yyyy年MM月dd日 HH时mm分ss秒"/>
						</td>
						
				
					</tr>
	    	</c:forEach>
					
				</tbody>
			</table>
			
			
				<div class="col-md-10" style="margin-left:35%;">
						<tags:pagination page="${keywords}" paginationSize="10"/>
			    </div>
			
			</div>
			  <br/><br/>  <br/><br/>
			
				
			  
					<div   style="margin-left: -20px;">
              <a href="${ctx}/mgr/weixin/keywordadd" class="btn"><button type="button" class="btn btn-info">新建关键字</button></a>
					</div>
		</div>
		
		

		
		
	</div>
	<script type="text/javascript" src="<c:url value="/static/angularjs/angular.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/static/angularjs/module/Weixin.js" />"></script>
</body>
</html>