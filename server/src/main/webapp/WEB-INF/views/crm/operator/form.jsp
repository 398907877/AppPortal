<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<jsp:include page="../../common/alert-error.jsp" />
<div
	class="form-group">
	<label class="col-sm-2 control-label" for="name">操作员名称：</label>
	<div class="col-sm-10 controls">
		<input type="text" name="name" id="name" class="input-large form-control" value="${user.name }"   />
	</div>
</div>

<div
	class="form-group">
	<label class="col-sm-2 control-label" for="loginName">登录名：</label>
	<div class="controls col-sm-10">
		<input type="text" name="loginName" id="loginName" value="${user.loginName }"  ${(id == null||id =='')?"" : "disabled='disabled'"}  class="input-large form-control"  />
	</div>
</div>

<c:if test="${id == null || id == ''}" >
	<div
		class="form-group ">
		<label class="col-sm-2 control-label" for="pwdCipher">登录密码：</label>
		<div class="controls col-sm-10">
		<input type="password" id="pwdCipher" name="pwdCipher"  class="input-large form-control"  >
		</div>
	</div>
	<div class="form-group ">
		<label class="col-sm-2 control-label" for="confirmPwdCipher">确认密码：</label>
		<div class="controls col-sm-10">
			<input type="password" id="confirmPwdCipher" name="confirmPwdCipher"  class="input-large form-control"  />
		</div>
	</div>
</c:if>	
<c:if test="${tenancies != null }">
<div class="form-group">
	<label class="control-label col-sm-2" for="type">所属租户：</label>
	<div class="controls col-sm-10">
		<select name="uid" class="se" style="min-width:240px">
			<option value="">------请选择一个租户------</option>
			<c:forEach	items="${ tenancies}" var="te">
				<option value="${ te.uid}" <c:if test="${te.uid == uid }">selected</c:if>>${te.name }</option>
			</c:forEach>
		</select>	
	</div>
</div>
</c:if>	

<div class="form-group">
	<label class="control-label col-sm-2" for="type">操作员类型：</label>
	<div class="controls col-sm-10">
		<select  name="roles" id="type" class="form-control">
			  <shiro:hasAnyRoles name='SYSADMIN'>
			  	<option value="SYSADMIN"    ${ user.roles == 'SYSADMIN' ? 'selected' : ''} >平台管理员</option>
			  </shiro:hasAnyRoles>
			<option value="OPERATOR"    ${ user.roles == 'OPERATOR' ? 'selected' : ''} >普通用户</option>
			<option value="ADMIN"  ${ user.roles == 'ADMIN' ? 'selected' : '' }  >管理员</option>
		</select>
	</div>
</div>


	<div
		class="form-group ">
		<label class="control-label col-sm-2" for="status">操作员状态：</label>
		<div class="controls col-sm-10">
			<select  name="status" id="status" class="form-control">
				<option value="1" ${user.status=='1'?'selected' : ''} >有效</option>
				<option value="0" ${user.status=='0'?'selected' : ''} >无效</option>
				<option value="2" ${user.status=='2'?'selected' : ''} >冻结</option>
			</select>
		</div>
	</div>


<script type="text/javascript">


//$(document).ready(function() {
//	$("#inputForm").validate();	
//});
$(".se").select2({
    placeholder: "请选择一个分组",
    allowClear: true
});

$(function(){
	
	$("#inputForm").validate({
		rules:{
			name:{
				required:true,
				minlength:5,
				maxlength:20
			},
			loginName:{
				required:true,
				minlength:5,
				maxlength:20
			},pwdCipher:{
				required:true,
				minlength:5,
				maxlength:15
			},confirmPwdCipher:{
				required:true,
				minlength:5,
				maxlength:15,
				equalTo: "#pwdCipher"
			},uid:{
				required:true
			}
		},messages:{
			name:{
				required:"必须填写",
			},
			loginName:{
				required:"必须填写",
			},pwdCipher:{
				required:"必须填写",
				minlength:"密码长度5-15位",
				maxlength:"密码长度5-15位"
			},confirmPwdCipher:{
				required:"必须填写",
				minlength:"密码长度5-15位",
				maxlength:"密码长度5-15位",
				equalTo: "两次输入密码不一致，请重新输入"
			}
		}
	});
})

</script> 