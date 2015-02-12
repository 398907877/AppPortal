<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>			

<div class="form-group">
	<label class="col-sm-2 control-label" for="title">帖子标题:</label>
	<div class="col-sm-10 controls">
		<input disabled="disabled" class="form-control"	 value="${invitation.title }"/>
	</div>
</div>
<%-- <c:if test="${invitationReply.id==null}">
<div class="form-group">
     <div class="select2-container populate placeholder select2-allowclear" id="s2id_e2" style="width: 300px;display: none">
                 <a href="javascript:void(0)"  class="select2-choice" tabindex="-1">  
                  <span class="select2-chosen" id="select2-chosen-13">California</span>
                  <abbr class="select2-search-choice-close"></abbr>  
                   <span class="select2-arrow" role="presentation">
                   <b role="presentation"></b></span></a>
                   <label for="s2id_autogen13" class="select2-offscreen"></label>
                </div>
                 <label class="col-sm-2 control-label" for="title">选择会员:</label>
                 <div class="col-sm-10 controls">
                 	 <select id="e2" name="userId" style="width:240px;" class="populate placeholder select2-offscreen" tabindex="-1" title=""><option></option>
                       <c:forEach items="${forumUsers}" var="user">
                          <option value="${user.id}">${user.name}</option>
	                   </c:forEach>
	                 </select>
                 </div>
	      </div>
	  </c:if> --%>

    <div class="form-group">
	<label class="col-sm-2 control-label" for="title">评论人:</label>
	<div class="col-sm-10 controls">
		<input type="hidden" name="userId" value="${invitationReply.userId }"/>
		<input name="crUser" class="form-control" value="${invitationReply.crUser }"/>
	</div>
</div>
<%-- <div class="form-group" id="picFile">
	<label class="col-sm-2 control-label" for="file-input">上传图片:</label>
	<div class="col-sm-10 controls" id="picssss">
	    <p class="text-success">提示：请把图片放在同一文件夹下并排好序   方便多图片上传</p>
        <p class="text-success">建议 大图640*320，小图80*80，JPEG/PNG格式.</p>
       
        <c:if test="${invitation.id!=null&&invitation!=''}">
  			<c:forEach items="${pictures}" var="pic">
  			    
				<div data-provides="fileinput" class="fileinput fileinput-exists">
					<div class="fileinput-new thumbnail" style="width: 150px; height: 150px;">
						<img data-src="holder.js/100%x100%" alt=""/>
					</div>
				<div style="width: 150px; height: 150px;" class="fileinput-preview fileinput-exists thumbnail">
					<img src="${pic.url}" style="max-width: 150px;max-height: 150px;"/>
				</div>
				<div>
			 		<a data-dismiss="fileinput" href="javascript:void(0)" onclick='deleteoldpic(${pic.id});' rel="${pic.url}" class="btn btn-danger fileinput-exists deleteOld"><span class="glyphicon glyphicon-remove"></span>移除</a>
				</div>
				</div>
  			</c:forEach>
  		</c:if>
  		<div class="fileinput fileinput-new" data-provides="fileinput">
			<div class="fileinput-new thumbnail" style="width: 150px; height: 150px;">
					     <img data-src="holder.js/100%x100%" alt=""/>
			</div>
				 <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 150px; max-height: 150px;">
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
</div> --%>
<!-- <div class="form-group">
		<label class="col-sm-2 control-label" for="contiueadd"></label>
		<div class="col-sm-10 controls">
			<input type="button" class="btn btn-success" name="contiueadd" id="contiueadd" value="继续添加">
			<input type="button" class="btn btn-danger" name="deleteAll" id="deleteAll" value="移除全部">
		</div>
	</div> -->
<div class="form-group">
	<label class="col-sm-2 control-label" for="intro">评论内容:</label>
	<div class="col-sm-10 controls">
		<form:textarea path="introduce" name="introduce" cols="10" rows="5" cssClass="form-control" value="${invitationReply.introduce}"/>
		<span id="introduceTip" for="introduce" class="error" style="display:none">详细内容不能为空</span>
	</div>
</div>
	<script type="text/javascript">
/* 		CKEDITOR.replace('intro',{width:600,filebrowserUploadUrl:'${ctx}/mgr/product/upload'}); */
		/* function getContentDetail(){
			return CKEDITOR.instances.contentDetail.getData(); 
		} 
		
		CKEDITOR.replace('introduce',{filebrowserUploadUrl:'${ctx}/mgr/product/upload'});	*/	
		$(function(){
			$('#introduce').wysihtml5({locale: "zh-CN"});
			$('.fileinput').fileinput();
			$("#form").validate({
				rules:{
					introduce:{
						required:true
					},
					crUser:{
						required:true,
						maxlength:20
					}
				},messages:{
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
					+"<div class='fileinput-new thumbnail' style='width: 150px; height: 150px;'>"
							+"<img data-src='holder.js/100%x100%' alt=''/>"
					+"</div>"
					+"<div class='fileinput-preview fileinput-exists thumbnail' style='max-width: 150px; max-height: 150px;'>"
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
		});
	
		$("#deleteAll").click(function(){
			$(".deleteOld").trigger("click");
			$(".fileinput").remove();
		});
	</script>  
