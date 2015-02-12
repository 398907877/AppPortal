<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<jsp:include page="../../common/alert-error.jsp" />
<!-- 
<div class="control-group ">
	<div class="controls">
		<label class="control-label" >输入原密码：</label>
		<input type="password" id="oldPwd" name="oldPwd" />
	</div>
</div>
 -->
<div class="form-group ">
	<label class="control-label col-sm-2" for="pwdCipher">新密码：</label>
	<div class="controls col-sm-10">
		
		<input type="password" class="form-control" id="pwdCipher" name="pwdCipher" />
	</div>
</div>

<div class="form-group">
	<label class="control-label col-sm-2" for="confirmPwdCipher">再次输入新密码：</label>
	<div class="controls col-sm-10">
		<input type="password" class="form-control" id="confirmPwdCipher" name="confirmPwdCipher" />
	</div>
</div>

<script type="text/javascript">

$(function(){
	$("#inputForm").validate({
		rules:{
		    pwdCipher:{
				required:true,
				minlength:5,
				maxlength:15
			},confirmPwdCipher:{
				required:true,
				minlength:5,
				maxlength:15,
				equalTo: "#pwdCipher"
			}
		},messages:{
		    pwdCipher:{
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