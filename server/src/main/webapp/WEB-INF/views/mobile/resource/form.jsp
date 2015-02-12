<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页设置</title>
<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
<script src="${ctx}/static/select2-3.4.6/select2.js"></script>
</head>
<body class="modal-open">
	<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>
		<div class="col-md-10 ">
			<div class="page-header">
				<h2  style="margin-top:0px">首页设置</h2>
			</div>
			<c:if test="${not empty message}">
				<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
			</c:if>
			<form:form id="form" method="post" modelAttribute="news" cssClass="form-horizontal" action="${ctx}/mgr/mobileResource/save" >
				<input type="hidden" name="id" value="${resource.id }"/>
				<div class="form-group ">
                <label class="col-sm-2 control-label" for="title">页面标题</label>
                	<div class="col-sm-10 controls">
                 		<input type="text" class="form-control" name="title" value="${resource.title}"/>
              		</div>
      			</div>
				<div class="form-group ">
                <label class="col-sm-2 control-label" for="target">目标地址</label>
                	<div class="col-sm-10 controls">
                 		<input type="text" class="form-control" name="target" value="${resource.target ==null ? '/m/index' : resource.target}"/>
              		</div>
      			</div>
      			<div class="form-group">
                <label class="col-sm-2 control-label" for="show">显示内容</label>
                <div class="col-sm-10 controls">
                 <select id="show" name="show" multiple="multiple" style="width:100%" class="populate placeholder select2-offscreen se" tabindex="-1" title="">
                       <c:forEach items="${bizcode}" var="biz">
                          <option value="${biz.key}">${biz.value}</option>
                     </c:forEach>
              </select>
              </div>
              </div>
              
      			<div class="form-group ">
                <label class="col-sm-2 control-label" for="template">选择模板</label>
                <div class="col-sm-10 controls">
                 <select id="template" name="template" style="min-width:230px" class="populate placeholder select2-offscreen se" tabindex="-1" title="">
                 	<option value="">----------请选择模板----------</option>
                       <c:forEach items="${templates}" var="tem">
                          <option value="${tem.key}">${tem.value}</option>
                     </c:forEach>
              </select>
              </div>
      </div>
      	<div class="form-group ">
                <label class="col-sm-2 control-label" for="template">效果预览</label>
                <div class="col-sm-10 controls">
                 	<iframe width="300px" height="480px">
                 		
                 	</iframe>
              </div>
        </div>
      <div class="form-group">
			<label class="col-sm-2 control-label" ></label>
 			<div class="col-sm-10 controls">
			<input value="提交" class="btn btn-primary"  type="submit"/>
			</div>
		</div>
			</form:form>
		</div>
	</div>
	<script type="text/javascript">
		$(".se").select2({
			 placeholder: "请选择一个或多个业务模块",
			allowClear: true
		});
	</script>
</body>
</html>