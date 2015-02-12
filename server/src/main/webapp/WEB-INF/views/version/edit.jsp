<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>修改客户端版本</title>
<style type="text/css">
	.error{color:red}
</style>
</head>

<body>
	<div class="page-header">
   		<h2>修改客户端版本</h2>
 	</div>
	<form id="inputForm" method="post" Class="form-horizontal" action="${ctx}/mgr/apps/version/update" enctype="multipart/form-data"  >
			
<input type="hidden" name="id" value="${versionInfo.id }"/>
<div class="form-group">
	<label class="col-sm-2 control-label" for="title">所属租户：</label>
	<div class="col-sm-10 controls">
						<select name="tenancyId">
						<c:forEach items="${tenancys }" var="tenancy" >
								<option value="${tenancy.id }"  ${tenancy.id == currentTenancyID ? "selected":""} >${tenancy.name }</option>
						</c:forEach>
						</select>
				</div>
			</div>
			
			<div class="form-group">
	<label class="col-sm-2 control-label" for="title">手机类型：</label>
	<div class="col-sm-10 controls">
		
						<select name="appCategory">
						<option value="">---------请选择---------</option>
										<option value="ANDROID-NHD" ${versionInfo.appCategory=='ANDROID-NHD'?'selected':'' } >新华都android版</option>
						<option value="IOS-NHD_FREE" ${versionInfo.appCategory=='IOS-NHD_FREE'?'selected':'' } >新华都iPhone越狱版</option>
						<option value="IOS-NHD_ITUNES" ${versionInfo.appCategory=='IOS-NHD_ITUNES'?'selected':'' }>新华都iPhone正式版</option>
						</select>
				</div>
			</div>
						<div class="form-group">
	<label class="col-sm-2 control-label" for="title">应用软件名称：</label>
	<div class="col-sm-10 controls">
		
					<input type="text"  name="appName" class="input-large" value="${versionInfo.appName }"/>
				</div>
			</div>
						<div class="form-group">
	<label class="col-sm-2 control-label" for="title">版本代码：</label>
	<div class="col-sm-10 controls">
		
		
					<input type="text" name="verCode" class="input-large" value="${versionInfo.verCode }"/>
				</div>
			</div>
						<div class="form-group">
	<label class="col-sm-2 control-label" for="title">版本名称：</label>
	<div class="col-sm-10 controls">
		
		
					<input type="text" name="verName" class="input-large" value="${versionInfo.verName }"/>
				</div>
			</div>
						<div class="form-group">
	<label class="col-sm-2 control-label" for="title">下载地址：</label>
	<div class="col-sm-10 controls">
		
					<input type="text"  name="url" class="input-large" value="${versionInfo.url }"/>
				</div>
			</div>
			
				<div class="form-group">
	<label class="col-sm-2 control-label" for="title">说明：</label>
	<div class="col-sm-10 controls">
	
					<textarea rows="3" style="width:250px" name="intro"  class="input-large">${versionInfo.intro }</textarea>
				</div>
			</div>
			
			<div class="form-actions">
		      <button type="submit" class="btn btn-primary" id="submit">保存</button>
			<a href="<%=request.getContextPath()%>/mgr/apps/version/index" class="btn">返回</a>
	        </div>
	</form>
	
	
	<script type="text/javascript">
	$(function(){
		$("#submit").click(function(){
			$("#inputForm").validate({
				rules:{
					
					intro:{
						maxlength:255,
					},
					verCode:{
						required:true,
						number:true
					}
				},
				messages:{
					
					intro:{
						maxlength:" 超过255个字符",
					}
				},

			});
		});
	})
	</script>
</body>