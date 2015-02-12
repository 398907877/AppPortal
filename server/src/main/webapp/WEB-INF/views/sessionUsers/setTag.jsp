<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>>设置分组</title>
	<style type="text/css"> 
	   .error{ color:Red; margin-left:10px;  } 
	   .sub{margin: 0 15px 0 150px;}
	</style> 
</head>

<body>
	<div class="page-header">
   		<h2>设置分组</h2>
 	</div>
 	 <c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<form id="inputForm"  Class="form-horizontal" action="${ctx}/mgr/baidu/setTag" >
		
		
		
		  <div  class="form-group">
				<label class="control-label col-sm-2" >用户名</label> 
				
				<div class=" col-sm-10 controls"><input type="text"
				readonly="readonly"	id="disabledTextInput" class="form-control" value="${name}"
				 style="width: 600px">
				 </div>
				 <input name="id" type="hidden" value="${id}" />
			</div>
		
		  <div  class="form-group">
				<label class="control-label col-sm-2" >tag分组</label> 
		
				<div class=" col-sm-10 controls"><input  type="text" name="tag"
					readonly="readonly"	 id="tagInput" class="form-control" value=""
				 style="width: 300px"/>
				 </div>
			</div>
		
		
		  <div  class="form-group">
				<div class="col-sm-10 col-sm-offset-2 controls">	
				<select id="selectTag" class="form-control"    style="width: 300px">
					<option selected="selected" >请选择</option>
				      <c:forEach items="${tags}" var="tag">
					  <option value="${tag.name}">${tag.name}</option>    
				        </c:forEach>
				</select>
			</div>
			</div>
		
			<button id="jun" type="button" class="btn btn-success"
				onclick="this.form.submit()">保存</button>
		
		   <button type="button" class="btn btn-warning"   style="margin-left: 50px" onclick="javascript:history.go(-1);">返回</button>
	</form>
	<script type="text/javascript">
$(function(){
	$("#selectTag").change(function(){
		var tagval=$(this).val();
		$("#tagInput").val(tagval);
		
	});		
})

</script>
</body>

</html>