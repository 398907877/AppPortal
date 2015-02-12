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
	<title>微信规则list</title>
	
	<script src="${ctx}/static/layer1.7.0/layer.min.js"></script>
	
</head>
<body ng-controller="WeixinController">
	<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
		<br/><br/>
			<div class="bs-callout bs-callout-info">
				<h4>微信规则设置</h4>
				<p>用于登记微信平台规则内容。</p>
			</div>
			
			<div>	
	 <form class="navbar-form navbar-left"  method="get"  action="${ctx}/mgr/weixin/keywords">
        <div class="form-group">
          <label>规则名称：</label> 
          <input   name="search_LIKE_title"   value="${param.search_LIKE_title}"  type="text" class="form-control" placeholder="输入查找的规则名称">
        </div>
         <div class="form-group">
        
                        <label>回复类型：</label> 
						<select name="search_EQ_wpCategory"  class="form-control">
						<option value="">---------请选择业务类型---------</option>
						<option value="biz" ${param.search_EQ_wpCategory == 'biz' ? 'selected' : '' } >业务</option>
						<option value="url" ${param.search_EQ_wpCategory == 'url' ? 'selected' : '' }  >连接</option>
						<option value="text" ${param.search_EQ_wpCategory == 'text' ? 'selected' : '' }  >文本</option>
						</select>
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
							规则名称
						</th>
						<th>
							规则类型
						</th>
						<th>
							创建时间
						</th>
						<th>
							规则内容
						</th>
					</tr>
				</thead>
				<tbody>
	                  <c:forEach items="${rules.content}" var="rule">
					<tr>
                 		<td>
                 		<!-- ${ctx}/mgr/weixin/rules/edit?id=${rule.id} -->
                 		
	                      
						<div class="btn-group">
								<a class="btn btn-info" href="#">操作</a> <a
									class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
									<span class="caret"></span>
								</a>
								<ul class="dropdown-menu">
									<li><a
										href="${ctx}/mgr/weixin/rules/modify?id=${rule.id}"><i
											class="icon-edit"></i>修改</a></li>
									
									<li><a  onclick="dodelete()" href="${ctx}/mgr/weixin/rules/delete?id=${rule.id}"
										class="del"><i class="icon-th"   ></i>删除</a></li>
						
								</ul>
							</div>
						
						</td>
						<td>
							${rule.title}
						</td>
						<td>
							<span class="label label-success"><tags:dict className="WP_REPLY_CATE" value="${rule.wpCategory}"></tags:dict></span> 
						</td>
						<td>
						
							<fmt:formatDate value="${rule.ruleCreate}" pattern=" yyyy年MM月dd日 HH时mm分ss秒"/>
							
						</td>
						
						<td   width="30%"  style="word-break : break-all; overflow:hidden;">
						<c:if test="${rule.wpCategory eq 'text'}">
									${rule.text}
								</c:if>
										<c:if test="${rule.wpCategory eq 'url'}">
									${rule.text}
								</c:if >
								<c:if test="${rule.wpCategory eq 'biz'}">
								${ rule.chineseProxyInterFace}
									</c:if >
						</td>
					</tr>
	    	</c:forEach>
					
				</tbody>
			</table>
			

				<div class="col-md-10">
						<tags:pagination page="${rules}" paginationSize="10"/>
					</div>
			</div>
			
			
			<br/><br/><br/>
				<div class="row">
					<div class="col-md-2">
						<a href="${ctx}/mgr/weixin/keywords/new" class="btn btn-primary">新建规则</a>	
					</div>
				
				</div>
		</div>
	</div>
	<script type="text/javascript" src="<c:url value="/static/angularjs/angular.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/static/angularjs/module/Weixin.js" />"></script>
</body>
</html>