<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="form-group">
	<label class="col-sm-2 control-label">产品分类:</label>
	
	<div class="col-sm-10">
		<form:select path="categoryId" name="categoryId" id="e2" cssStyle="min-width:240px;">
			<form:options items="${categorys }" itemLabel="name" itemValue="id"/>
		</form:select>
	</div>
</div>

<div class="form-group">
	<label class="col-sm-2 control-label" for="title">产品名称:</label>
	<div class="col-sm-10 controls">
		<form:input path="title" name="title" cssClass="form-control input-large required" value="${product.title }"/>
	</div>
</div>
<div class="form-group" id="picFile">
     <label class="col-sm-2 control-label" for="file-input">标题缩略图</label>
<div class="col-sm-10 controls" id="uploadpictureid">
	<div data-provides="fileinput" class="fileinput <c:if test='${product.thumb == null || product.thumb ==""}'>fileinput-new</c:if> <c:if test='${product.thumb != null && product.thumb !=""}'>fileinput-exists</c:if>">
					<div class="fileinput-new thumbnail" style="width: 300px; height: 150px;">
						<img data-src="holder.js/300x150" alt=""/>
					</div>
				<div  class="fileinput-preview fileinput-exists thumbnail" style="max-width: 300px; max-height: 150px;">
					<c:if test='${product.thumb != null && product.thumb !=""}'><img src="${product.thumb}" style="max-width: 300px;max-height: 150px;"/>
						
					</c:if>
				</div>
				<div>
					<span class="btn btn-default btn-file">
					<span class="fileinput-new">添加图片</span>
					<span class="fileinput-exists">更改</span>
					<input type="file" class="file-input" name="fileInput_thum" id="file" />
					 </span>
					<a class="btn btn-danger fileinput-exists" id="removePic" data-dismiss="fileinput"><span class="glyphicon glyphicon-remove"></span>移除</a>
					<input type="hidden" value="${product.thumb}" id="thumb" name="thumb"/>
				</div>
				</div>
	</div>
</div>
<div class="form-group" id="picFile">
	<label class="col-sm-2 control-label" >产品图片:</label>
	<div class="col-sm-10" id="pics">
		<p class="text-success">提示：请把图片放在同一文件夹下并排好序   方便多图片上传</p>
        
        <c:if test="${product.id!=null&&product!=''}">
  			<c:forEach items="${pictures}" var="pic">
  			
				<div data-provides="fileinput" class="fileinput fileinput-exists" id="dispic_${pic.pid }">
					<div class="fileinput-new thumbnail" style="width: 300px; height: 150px;">
						<img data-src="holder.js/300x150" alt=""/>
					</div>
				<div style="width: 300px; height: 150px;" class="fileinput-preview fileinput-exists thumbnail">
					<img src="${pic.url}" style='max-width: 300px;max-height:150px;'/>
				</div>
				<div>
			 		<a data-dismiss="fileinput" href="javascript:void(0)" onclick='deleteoldpic(${pic.pid});' rel="${pic.url}" class="btn btn-danger fileinput-exists deleteOld"><span class="glyphicon glyphicon-remove"></span>移除</a>
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
	<label class="col-sm-2 control-label" for="intro">产品简介:</label>
	<div class="col-sm-10 controls">
		<form:textarea path="intro" name="intro" cssClass="form-control input-small required" placeholder="输入产品简介" cols="10" rows="3" value="${product.intro }"/>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="price">产品价格:</label>
	<div class="col-sm-10 controls">
		<form:input path="price" name="price" cssClass="form-control required" value="${product.price }"/>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="total">产品库存:</label>
	<div class="col-sm-10 controls">
		<form:input path="total" name="total" cssClass="form-control required" value="${product.total }"/>
	</div>
</div>

<div class="form-group">
	<label class="col-sm-2 control-label" for="freight">产品运费:</label>
	<div class="col-sm-10 controls">
		<form:input path="freight" name="freight" cssClass="form-control" value="${product.freight }"/>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="buyLimit">限购数:</label>
	<div class="col-sm-10 controls">
		<form:input path="buyLimit" name="buyLimit" cssClass="form-control" value="${product.buyLimit }"/>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="detail">详细内容:</label>
	<div class="col-sm-10 controls">
		<form:textarea path="detail" name="detail" cssClass="input" value="${product.detail }"/>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 text-right"><span class="btn btn-info" id="addfield"><icon class="glyphicon glyphicon-plus"></icon>新增拓展属性</span></label>
	<div class="col-sm-10 container" id="fields">
		<span class="help-block">属性值有多个，可以用下划线('_')分隔</span>
		<c:forEach items="${ product.productFields}" var="f" varStatus="s">
			<div class='row oldfield' >
				<input type="hidden" name='productFields[${ s.index}].fieldId' value="${f.fieldId }">
				<div class='col-md-6 form-group'><label class='col-sm-3 control-label'>属性名</label>
					<div class='col-sm-9'>
						<input type='text' name='productFields[${ s.index}].name'  class='form-control fieldName' value='${ f.name}' />
					</div>
				</div>
				<div class='col-md-6 form-group'><label class='col-sm-3 control-label'>属性值</label>
					<div class='col-sm-9'>
						<input type='text' name='productFields[${ s.index}].value'  class='form-control fieldValue' value='${ f.value}' />
					</div>
				</div>
				<input type='button' id="delField${f.fieldId}" onclick='delOldField(${f.fieldId})' class='btn btn-danger' value='删除'/>
			</div>
		</c:forEach>
	</div>
</div>
	<script type="text/javascript">
		
/* 		CKEDITOR.replace('intro',{width:600,filebrowserUploadUrl:'${ctx}/mgr/product/upload'}); */
		function getContentDetail(){
			return CKEDITOR.instances.contentDetail.getData(); 
		}
		var editor1 = CKEDITOR.replace('detail',{filebrowserUploadUrl:'${ctx}/mgr/product/upload'});
		
		$(function(){
			$('.fileinput').fileinput();
			$("#e2").select2({
			    placeholder: "-----------请选择产品类型----------",
			    allowClear: true
			});
			$("#form").validate({
				rules:{
					title:{
						required:true,
						maxlength:100
					},
					intro:{
						required:true
					},
					detail:{
						required:true
					},
					price:{
						required:true,
						min:0,
						number:true
					},total:{
						required:true,
						min:0,
						max:9999,
						digits:true
					},buyLimit:{
						min:0,
						max:9999,
						digits:true
					},freight:{
						min:0,
						number:true
					}
				},messages:{
					title:{
						required:"必须填写",
						maxlength:"超出100字符"
					},
					intro:{
						required:"必须填写"
					}
					,
					detail:{
						required:"必须填写"
					}
					
				}
			});
		});
		
		$("#addfield").click(function(){
		     $("#fields").append( "<div class='row'><div class='col-md-6 form-group'><label class='col-sm-3 control-label'>属性名</label><div class='col-sm-9'><input type='text' name='fieldName'  class='form-control' value='' placeholder='颜色' /></div></div>"+
		    	"<div class='col-md-6 form-group'><label class='col-sm-3 control-label'>属性值</label><div class='col-sm-9'><input type='text' name='fieldValue' class='form-control' value='' placeholder='红_白'/></div></div><input type='button' onclick='delNewField(this)' class='btn btn-danger' value='删除'/></div>");
		});
		function delNewField(obj){
			$(obj).parents(".row").remove();
		}
		
		function delOldField(id){
			if(confirm("确定要删除吗?")){ 
		           $.ajax({
			         url: "${ctx}/mgr/product/delfield/"+id,
			         type: 'DELETE',
			         async:false,
			         contentType: "application/json;charset=UTF-8"
			       })
			       .done(function( html ) {
			    	   $("#delField"+id).parents('.row').remove();
			    	   var length = $(".oldfield").length;
			    	   for(var i=0;i<length;i++){
			    		   var $oldfield = $($(".oldfield").get(i));
			    		   $oldfield.find("input:hidden").attr("name","productFields["+i+"].fieldId");
			    		   $oldfield.find(".fieldName").attr("name","productFields["+i+"].name");
			    		   $oldfield.find(".fieldValue").attr("name","productFields["+i+"].value");
			    	   }
			       }).fail( function(jqXHR, textStatus){
			    	  alert("删除失败jqXHR="+jqXHR+"textStatus="+textStatus);
			   });
		    };
		    
		};
		$("#contiueadd").click(function(){
			$("#pics").append("<div class='fileinput fileinput-new' data-provides='fileinput' style='margin-right:5px'>"
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
			$("#pics .fileinput").remove();
			$("#contiueadd").trigger("click");
		});
		
		$("#removePic").click(function(){
			$("#thumb").val("");
		});
	</script>  
