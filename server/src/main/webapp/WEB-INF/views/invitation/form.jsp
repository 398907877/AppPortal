<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>			

<div class="form-group">
	<label class="col-sm-2 control-label" for="title">帖子标题:</label>
	<div class="col-sm-10 controls">
		<form:input path="title" name="title" cssClass="form-control" value="${invitation.title }"/>
	</div>
</div>
<%-- <c:if test="${invitation.id==null}">
<div class="form-group">
	<label class="col-sm-2 control-label" for="title">选择会员:</label>
     	<div class="col-sm-10 controls">
                 <select id="e2" name="userId" style="min-width:240px;" class="populate placeholder select2-offscreen" tabindex="-1" title=""><option></option>
                       <c:forEach items="${forumUsers}" var="user">
                          <option value="${user.id}">${user.name}</option>
                     </c:forEach>
              </select>
      </div>
      </div>
  </c:if> --%>
 <%--  <c:if test="${invitation.id!=null}">
  <div class="form-group">
	<label class="col-sm-2 control-label" for="title">发布人:</label>
	<div class="col-sm-10 controls">
		<select name="userId">
		<option value="${invitation.userId}">${user.name}</option>
		</select><label style="color: green;">提示：发布人添加后不可改</label>
	</div>
</div>
  </c:if> --%>
<div class="form-group" id="picFile">
	<label class="col-sm-2 control-label" for="file-input">上传图片:</label>
	<div class="col-sm-10 controls" id="picssss">
	    <p class="text-success">提示：请把图片放在同一文件夹下并排好序   方便多图片上传</p>
      <!--   <p class="text-success">建议 大图640*320，小图80*80，JPEG/PNG格式.</p> -->
       
        <c:if test="${invitation.id!=null&&invitation.id !=''}">
  			<c:forEach items="${pictures}" var="pic">
				<div data-provides="fileinput" class="fileinput fileinput-exists" id="dispic_${pic.id }">
					<div class="fileinput-new thumbnail" style="width: 300px; height: 150px;">
						<img data-src="holder.js/300x150" alt=""/>
					</div>
				<div style="width: 300px; height: 150px;" class="fileinput-preview fileinput-exists thumbnail">
					<img src="${pic.url}" style="max-width: 300px;max-height: 150px;"/>
				</div>
				<div>
			 		<a data-dismiss="fileinput" href="javascript:void(0)" onclick='deleteoldpic(${pic.id});' rel="${pic.url}" class="btn btn-danger fileinput-exists deleteOld"><span class="glyphicon glyphicon-remove"></span>移除</a>
				</div>
				</div>
  			</c:forEach>
  		</c:if>
  		<div class="fileinput fileinput-new" data-provides="fileinput">
			<div class="fileinput-new thumbnail" style="width: 300px; height: 150px;">
					     <img data-src="holder.js/300x150" alt=""/>
			</div>
				 <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 300px; max-height: 150px;">
				 </div>  
			<div>
				<span class="btn btn-default btn-file">
				<span class="fileinput-new">添加图片</span>
				<span class="fileinput-exists">更改</span>
				<input type="file" class="file-input" name="fileInput" id="file" />
			 </span>
				<a href="#" class="btn btn-danger fileinput-exists" data-dismiss="fileinput"><span class="glyphicon glyphicon-remove"></span>移除</a>
			</div>
		</div>
	</div>
</div>
<div class="form-group">
		<label class="col-sm-2 control-label" for="contiueadd"></label>
		<div class="col-sm-10 controls">
			<input type="button" class="btn btn-success" name="contiueadd" id="contiueadd" value="继续添加">
			<input type="button" class="btn btn-danger" name="deleteAll" id="deleteAll" value="移除全部">
		</div>
		
	</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="detail">详细内容:</label>
	<div class="col-sm-10 controls">
		<form:textarea path="introduce" id="introduce" name="introduce" value="${invitation.introduce }" class="form-control input-small required" cols="10" rows="5"/>
		<span id="introduceTip" for="introduce" class="error" style="display:none">详细内容不能为空</span>
	</div>
</div>

	<script type="text/javascript">
/* 		CKEDITOR.replace('intro',{width:600,filebrowserUploadUrl:'${ctx}/mgr/product/upload'}); */
		$('#introduce').wysihtml5({locale: "zh-CN"});
		
		$(function(){
			$('.fileinput').fileinput();
			$("#form").validate({
				rules:{
					title:{
						required:true,
						maxlength:30
					},
					userId:{
						required:true
					},
					introduce:{
						required:true
					}
				},messages:{
					title:{
						required:"必须填写",
						maxlength:"超出30字符"
					},
					userId:{
						required:"必须选择一个会员"
					},
					introduce:{
						required:"必须填写",
					}
				},submitHandler:function(form){
					if($("#introduce").val().trim() == ""){
						$("#introduceTip").show();
						return;
					}
	   	 			form.submit();
	         	}
			});
		});
		
		$("#contiueadd").click(function(){
			$("#picssss").append("<div class='fileinput fileinput-new' data-provides='fileinput' style='margin-right:5px'>"
					+"<div class='fileinput-new thumbnail' style='width: 300px; height: 150px;'>"
							+"<img data-src='holder.js/300x150' alt=''/>"
					+"</div>"
					+"<div class='fileinput-preview fileinput-exists thumbnail' style='max-width: 300px; max-height: 150px;'>"
					+"</div>"  
					+"<div>"
						+"<span class='btn btn-default btn-file'>"
							+"<span class='fileinput-new'>添加图片</span>"
							+"<span class='fileinput-exists'>更改</span>"
							+"<input type='file' class='file-input' name='fileInput' id='file' />"
						+"</span>"
						+"<a href='#' class='btn btn-danger fileinput-exists' data-dismiss='fileinput' style='margin-left:5px'><span class='glyphicon glyphicon-remove'></span>移除</a>"
					+"</div>"
				+"</div>");
			//$('.fileinput').fileinput();
			$('.fileinput:last').fileinput();
			Holder.run();
		});
		
		$("#deleteAll").click(function(){
			$(".deleteOld").trigger("click");
			$(".fileinput").remove();
			$("#contiueadd").trigger("click");
		});
	</script>  
