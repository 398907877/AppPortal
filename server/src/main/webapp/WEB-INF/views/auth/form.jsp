<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<div
	class="form-group ">
	<label class="control-label col-sm-2" for="name">企业名称：</label>
	<div class="controls col-sm-10">
		<input type="hidden" value="${tenancy.uid}" name="uid"  id="uid"/>
		<c:if test="${auth.id != null }"><input type="hidden" value="${auth.id}" name="auth_id"/></c:if>
		<input type="text" value="${tenancy.name }" id="name" class="form-control" readonly="readonly" />	
	</div>
</div>

<div
	class="form-group ">
	<label class="control-label col-sm-2" for="bizScopes">业务模块及设置：</label>
	<div class="controls col-sm-10">
		<c:forEach items="${bizCodes }" var="bizc" varStatus="s">
			<%-- <div id="box${bizc.key }" class="checkbox">
			 	<label>
				<input type="checkbox" class="ck" name="${bizc.key }" id="${bizc.key }"<c:if test="${authCfgs[bizc.key] != null}">checked="checked"</c:if>/>${bizc.value }</label>
				<c:if test="${authCfgs[bizc.key] != null && bizc.key eq 'news'}">
				<input type="hidden" name="${bizc.key }_id" value="${authCfgs[bizc.key].id}"/>
					<div class="form-group">
						<label class="control-label col-sm-2" for="${bizc.key}dayLimit">每日限额条数：</label>
						<div class="controls col-sm-4">
							<input type="text" value="${authCfgs[bizc.key].dayLimit }" class="form-control" id="${bizc.key }_dayLimit" name="${bizc.key }_dayLimit"/>
						</div>
					 </div>
					 <div class="form-group">
						<label class="control-label col-sm-2" for="${bizc.key}monthVideoLimit">月度视频限额：</label>
						<div class="controls col-sm-4">
							<input type="text" value="${authCfgs[bizc.key].monthVideoLimit }" class="form-control" id="${bizc.key }_monthVideoLimit" name="${bizc.key}_monthVideoLimit"/>
						</div>
					 </div>
				</c:if>
				<c:if test="${authCfgs[bizc.key] == null && bizc.key eq 'news'}">
					<div class="form-group none">
						<label class="control-label col-sm-2" for="${bizc.key}dayLimit">每日限额条数：</label>
						<div class="controls col-sm-4">
							<input type="text" value="1" class="form-control" id="${bizc.key}dayLimit" name="${bizc.key }_dayLimit"/>
						</div>
					 </div>
					 <div class="form-group none">
						<label class="control-label col-sm-2" for="${bizc.key}monthVideoLimit">月度视频限额：</label>
						<div class="controls col-sm-4">
							<input type="text" value="1" class="form-control" id="${bizc.key}monthVideoLimit" name="${bizc.key }_monthVideoLimit"/>
						</div>
					 </div>
				</c:if>
			</div> --%>
			<div class="panel-group" id="accordion">
			<c:if test="${bizc.key eq 'news' }">
  			<div class="panel panel-default">
    			<div class="panel-heading">
      				<h4 class="panel-title">
					<input type="checkbox" class="ck" name="${bizc.key }" id="${bizc.key }" <c:if test="${authCfgs[bizc.key] != null}">checked</c:if> />
        			<a  data-toggle="collapse" data-parent="#accordion" href="#${bizc.key }Collapse">${bizc.value }</a>
      				</h4>
   				</div>
    			<div id="${bizc.key }Collapse" class="panel-collapse collapse <c:if test='${authCfgs[bizc.key] != null}'>in</c:if>">
      				<div class="panel-body">
      					<input type="hidden" class="" name="<c:if test="${authCfgs[bizc.key] == null}">disable</c:if>${bizc.key }_id" value="${authCfgs[bizc.key].id}"/>
      					<div class="form-group">
						<label class="control-label col-sm-2" for="${bizc.key}dayLimit">每日限额条数：</label>
						<div class="controls col-sm-4">
							<input type="text" value="${authCfgs[bizc.key]==null ? '1' : authCfgs[bizc.key].dayLimit }" class="form-control ${bizc.key}" id="${bizc.key}dayLimit" name="<c:if test="${authCfgs[bizc.key] == null}">disable</c:if>${bizc.key }_dayLimit"/>
						</div>
					 </div>
					 <div class="form-group">
						<label class="control-label col-sm-2" for="${bizc.key}monthVideoLimit">月度视频限额：</label>
						<div class="controls col-sm-4">
							<input type="text" value="${authCfgs[bizc.key]==null ? '1' : authCfgs[bizc.key].monthVideoLimit }" class="form-control ${bizc.key}" id="${bizc.key}monthVideoLimit" name="<c:if test="${authCfgs[bizc.key] == null}">disable</c:if>${bizc.key }_monthVideoLimit"/>
						</div>
					 </div>
      				</div>
    			</div>
  			</div>
  			</c:if>
  			<c:if test="${bizc.key != 'news' }">
  			<div class="panel panel-default">
    			<div class="panel-heading">
      				<h4 class="panel-title">
					<input type="checkbox" class="ck" name="${bizc.key }" id="${bizc.key }" <c:if test="${authCfgs[bizc.key] != null}">checked</c:if> />
        			<a  data-toggle="collapse" data-parent="#accordion" href="#${bizc.key }Collapse">${bizc.value }</a>
      				</h4>
   				</div>
    			<div id="${bizc.key }Collapse" class="panel-collapse collapse <c:if test='${authCfgs[bizc.key] != null}'>in</c:if>">
      				<div class="panel-body">
      					<div class="form-group">
      					<input type="hidden" class="" name="<c:if test="${authCfgs[bizc.key] == null}">disable</c:if>${bizc.key }_id" value="${authCfgs[bizc.key].id}"/>
						<label class="control-label col-sm-2" for="${bizc.key}dayLimit">每日限额条数：</label>
						<div class="controls col-sm-4">
							<input type="text" value="${authCfgs[bizc.key]==null ? '1' : authCfgs[bizc.key].dayLimit }" class="form-control ${bizc.key}" id="${bizc.key}dayLimit" name="<c:if test="${authCfgs[bizc.key] == null}">disable</c:if>${bizc.key }_dayLimit"/>
						</div>
					 </div>
      				</div>
    			</div>
  			</div>
  			</c:if>
  			</div>
		</c:forEach>
		<span id="tip" style="color: red; display: none;">业务模块至少选择一个</span>
	</div>
</div> 

<script type="text/javascript">
<c:forEach items="${bizCodes }" var="bizc" varStatus="s">
	$("#${bizc.key}").click(function(){
		var ischeck = document.getElementById("${bizc.key}").checked;
		if(ischeck){
			$('#${bizc.key}Collapse').collapse('show');
			$('.${bizc.key}').each(function(){
				$(this).attr("name",$(this).attr("name").substring(7));
			});
			
		}else{
			$('#${bizc.key}Collapse').collapse('hide');
			
			$('.${bizc.key}').each(function(){
				$(this).attr("name","disable"+$(this).attr("name"));
			});
		}
	});
</c:forEach>

$(document).ready(function(){
	
	$("#form").validate({
		rules:{
			issueDate:{
				required:true
			},
			dueDate:{
				required:true
			},
			news_dayLimit:{
				required:true,
				range:[1,100],
				digits:true
			}
		},messages:{
			issueDate:{
				required:"必须填写",
			},
			dueDate:{
				required:"必须填写",
			},
			news_dayLimit:{
				required:"必须填写",
			}
		},
		submitHandler: function(inputform) {
			var countServ = 0;
			$(".ck").each(function(i,data){
		
				if(data.checked){
					countServ = countServ + 1;
				}
			});
			if(countServ == 0){
				$("#tip").css("display","inline");
			}else{
				$("#tip").css("display","none");
				inputform.submit();
			}
		}
	});
	$("#submit").click(function(){
		
	});
});
	
</script> 