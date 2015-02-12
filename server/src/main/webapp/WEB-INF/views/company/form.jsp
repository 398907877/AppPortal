<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%-- <div class="form-group">
	<label class="col-sm-2 control-label" for="categoryId">企业类型：</label>
	<div class="col-sm-10 controls">
		<form:select path="categoryId" style="min-width:240px" cssClass="populate placeholder select2-offscreen" name="categoryId" tabindex="-1" title="" id="e2">
			<option></option>
			<form:options items="${companyCategorys }" itemLabel="name" itemValue="id"/>
		</form:select>
	</div>
</div> --%>
<%-- </c:if> --%>
<%-- <c:if test="${news.id==null }">
   <div class="form-group">
   		<label class="col-sm-2 control-label" for="title">企业类型:</label>
        <div class="col-sm-10 controls">
       		 <select id="e2" name="categoryId" style="min-width:240px" class="populate placeholder select2-offscreen" tabindex="-1" title=""><option></option>
                       <c:forEach items="${companyCategorys}" var="category">
                          <option value="${category.id}">${category.name}</option>
                     </c:forEach>
              </select>
        </div>       
                 
      </div>
    </c:if> --%>
<div class="form-group">
	<label class="col-sm-2 control-label" for="title">名称：</label>
	<div class="col-sm-10 controls">
		<form:input path="title" name="title" cssClass="form-control input-large required" readonly="true" value="${company.title }"/>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label">logo标志：</label>
	

<c:if test="${company.pic != null && company.pic!=''}">
<div class="col-sm-10 controls" id="uploadpictureid">
	<div data-provides="fileinput" class="fileinput fileinput-exists">
					<div class="fileinput-new thumbnail" style="width: 300px; height: 150px;">
						<img data-src="holder.js/300x150" alt=""/>
					</div>
				<div  class="fileinput-preview fileinput-exists thumbnail" style="max-width: 300px; max-height: 150px;">
					<img src="${company.pic}" style="max-width: 300px;max-height: 150px;"/>
				</div>
				<div>
					<span class="btn btn-default btn-file">
					<span class="fileinput-new">添加图片</span>
					<span class="fileinput-exists">更改</span>
					<input type="file" class="file-input" name="fileInput_thum" id="file" />
					<input type="hidden" name="pic" id="pic" value="${company.pic }" />
					 </span>
					<a class="btn btn-danger fileinput-exists" id="removePic" data-dismiss="fileinput"><span class="glyphicon glyphicon-remove"></span>移除</a>
				</div>
				</div>
	</div>

</c:if>
<c:if test="${ company.pic == null || company.pic==''}">
	<div class="col-sm-10 controls" id="uploadpictureid">
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
				<input type="file" class="file-input" name="fileInput_thum" id="file" />
			 </span>
				<a class="btn btn-danger fileinput-exists" id="removePic" data-dismiss="fileinput"><span class="glyphicon glyphicon-remove"></span>移除</a>
			</div>
	</div>
</div>
</c:if>
	</div>	
 <div class="form-group" id="picFile">
	<label class="col-sm-2 control-label" for="file-input">图片展示：</label>
	<div class="col-sm-10" id="picssss">
		 <p class="text-success">提示：请把图片放在同一文件夹下并排好序   方便多图片上传</p>
       <!--  <p class="text-success">建议 大图300*150，小图60*60，JPEG/PNG格式.</p> -->
        <%-- <c:if test="${company.id!=null&&company!=''}">
        <div class="gallery-set-thumbail">
  			<c:forEach items="${pictures}" var="pic">
  			     <div class="image" id='dispic_${pic.pid}'>
				  		<a href="#" rel="facebox"><img src="${pic.url}" style="width: 80px;height: 80px;"/></a>
				  		<a href="javascript:void(0)" class="delete" onclick='deleteoldpic(${pic.pid});' rel="${pic.url}">删除</a>
				  	</div>
  			</c:forEach>
  		</div>
  		</c:if> --%>
  		<c:if test="${company.id!=null&&company!=''}">
  			<c:forEach items="${pictures}" var="pic">
  			    <!--   <div class="image" id='dispic_${pic.pid}'>
				  		<a href="#" rel="facebox"><img src="${pic.url}" style="width: 80px;height: 80px;"/></a>
				  		<a href="javascript:void(0)" class="delete" onclick='deleteoldpic(${pic.pid});' rel="${pic.url}">删除</a>
				  	</div>
				 -->
				<div data-provides="fileinput" class="fileinput fileinput-exists filePics" id="dispic_${pic.pid }">
					<div class="fileinput-new thumbnail" style="width: 300px; height: 150px;">
						<img data-src="holder.js/300x150" alt=""/>
					</div>
				<div style="max-width:300px; max-height:150px;" class="fileinput-preview fileinput-exists thumbnail">
					<img src="${pic.url}" style="max-width: 300px;max-height: 150px;"/>
				</div>
				<div>
			 		<a data-dismiss="fileinput" href="javascript:void(0)" onclick='deleteoldpic(${pic.pid});' rel="${pic.url}" class="btn btn-danger fileinput-exists deleteOld"><span class="glyphicon glyphicon-remove"></span>移除</a>
				</div>
				</div>
  			</c:forEach>
  		</c:if>
  		
  		<div class="fileinput fileinput-new filePics" data-provides="fileinput">
			<div class="fileinput-new thumbnail" style="width: 300px; height: 150px;">
					     <img data-src="holder.js/300x150" alt=""/>
			</div>
				 <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 300px; max-height: 150px;"></div>
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
	<label class="col-sm-2 control-label" for="intro">简介:</label>
	<div class="col-sm-10 controls">
		<form:textarea path="intro" name="intro" cssClass="form-control intro required" placeholder="输入企业简介" cols="10" rows="3" value="${company.intro }"/>
	</div>
</div>


<div class="form-group">
	<label class="col-sm-2 control-label" for="detail">详情:</label>
	<div class="col-sm-10 controls">
		<form:textarea path="detail" name="detail" cssClass="intro form-control" rows="10" value="${company.detail }"/>
	</div>
</div>
	<script type="text/javascript">
	
	$("#e2").select2({
	    placeholder: "---------请选择企业类型-------",
	    allowClear: true
	});
	$("#removePic").click(function(){
		$("#pic").val("");
	});
/* 		CKEDITOR.replace('intro',{width:600,filebrowserUploadUrl:'${ctx}/mgr/news/upload'});
 */		
/*  function getContentDetail(){
			return CKEDITOR.instances.contentDetail.getData(); 
		} */
 		$('.intro').wysihtml5({locale: "zh-CN"});
 		  
 		/* var editor = CKEDITOR.replace('detail',{filebrowserUploadUrl:'${ctx}/mgr/product/upload'}); */
	
		$(function(){
			
			$('.datepicker').datetimepicker({
		    	format: 'yyyy-mm-dd hh:ii:00',
		   		language: 'zh-CN'
			});
			$("#form").validate({
				rules:{
					title:{
						required:true,
						maxlength:100
					},
					categoryId:{
						required:true
					},
					intro:{
						required:true
					},
					crtDate:{
						required:true
					},
					detail:{
						required:true
					}
				},messages:{
					title:{
						required:"必须填写",
						maxlength:"超出100字符"
					},
					categoryId:{
						required:"必须填写"
					},
					intro:{
						required:"必须填写"
					},
					crtDate:{
						required:"必须填写"
					},
					detail:{
						required:"必须填写",
					}
				}
			});
		});
		
		$("#contiueadd").click(function(){
			$("#picssss").append("<div class='fileinput fileinput-new filePics' data-provides='fileinput' style='margin-right:5px'>"
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
			Holder.run();
			//$('.fileinput').fileinput();
			//$('.fileinput:last').fileinput();
		});
		$("#deleteAll").click(function(){
			$(".deleteOld").trigger("click");
			$(".filePics").remove();
			$("#contiueadd").trigger("click");
			
		});
	</script>  
