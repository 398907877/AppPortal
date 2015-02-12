<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
			

    <div class="form-group">
	<label class="col-sm-2 control-label" for="title">供求标题:</label>
	<div class="col-sm-10 controls">
		<form:input path="title" name="title" cssClass="form-control required" value="${supplyDemand.title }"/>
	</div>
 </div>
  <div class="form-group">
	<label class="col-sm-2 control-label" for="toUser">联系人:</label>
	<div class="col-sm-10 controls">
		<form:input path="toUser" name="toUser" id="toUser" cssClass="form-control" value="${supplyDemand.toUser }"/>
	</div>
 </div>
 <div class="form-group">
	<label class="col-sm-2 control-label" for="tel">联系方式:</label>
	<div class="col-sm-10 controls">
		<form:input path="tel" name="tel" id="tel" cssClass="form-control required" value="${supplyDemand.tel }"/>
		<label style="color: green;">提示：(电话/手机/邮箱 /QQ)都可以</label>
	</div>
 </div>
<div class="form-group" id="picFile">
     <label class="col-sm-2 control-label" for="file-input">标题缩略图</label>
<div class="col-sm-10 controls" id="uploadpictureid">
	<div data-provides="fileinput" class="fileinput <c:if test='${supplyDemand.thumb == null || supplyDemand.thumb==""}'>fileinput-new</c:if> <c:if test='${supplyDemand.thumb != null && supplyDemand.thumb!=""}'>fileinput-exists</c:if>">
					<div class="fileinput-new thumbnail" style="width: 300px; height: 150px;">
						<img data-src="holder.js/300x150" alt=""/>
					</div>
				<div  class="fileinput-preview fileinput-exists thumbnail" style="max-width: 300px; max-height: 150px;">
					<c:if test='${supplyDemand.thumb != null && supplyDemand.thumb!=""}'><img src="${supplyDemand.thumb}" style="max-width: 300px;max-height: 150px;"/></c:if>
				</div>
				<div>
					<span class="btn btn-default btn-file">
					<span class="fileinput-new">添加图片</span>
					<span class="fileinput-exists">更改</span>
					<input type="file" class="file-input" name="fileInput_thum" id="file" />
					 </span>
					<a class="btn btn-danger fileinput-exists" id="removePic" data-dismiss="fileinput"><span class="glyphicon glyphicon-remove"></span>移除</a>
					<input type="hidden" value="${supplyDemand.thumb}" id="thumb" name="thumb"/>
				</div>
				</div>
	</div>
</div>
<div class="form-group" id="picFile">
	<label class="col-sm-2 control-label" >更多图片:</label>
	<div class="col-sm-10" id="pics">
		<p class="text-success">提示：请把图片放在同一文件夹下并排好序   方便多图片上传</p>
        <c:if test="${supplyDemand.id!=null&&supplyDemand!=''}">
  			<c:forEach items="${pictures}" var="pic">
  			    
				<div data-provides="fileinput" class="fileinput fileinput-exists" id="dispic_${pic.id }">
					<div class="fileinput-new thumbnail" style="width: 300px; height: 150px;">
						<img data-src="holder.js/300x150" alt=""/>
					</div>
				<div style="width: 300px; height: 150px;" class="fileinput-preview fileinput-exists thumbnail">
					<img src="${pic.filePath}" style="max-width: 300px;max-height: 150px;"/>
				</div>
				<div>
			 		<a data-dismiss="fileinput" href="javascript:void(0)" onclick='deleteoldpic(${pic.id});' rel="${pic.filePath}" class="btn btn-danger fileinput-exists deleteOld"><span class="glyphicon glyphicon-remove"></span>移除</a>
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
	<label class="col-sm-2 control-label" for="price">价格:</label>
	<div class="col-sm-10 controls">
		<form:input path="price" name="price" cssClass="form-control" value="${supplyDemand.price  }"/>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="total">总量:</label>
	<div class="col-sm-10 controls">
		<form:input path="total" name="total" cssClass="form-control" value="${supplyDemand.total  }"/>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="address">所在地:</label>
	<div class="col-sm-10 controls">
		<form:input path="address" name="address" cssClass="form-control" value="${supplyDemand.address  }"/>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="limitNum">起购数:</label>
	<div class="col-sm-10 controls">
		<form:input path="limitNum" name="limitNum" cssClass="form-control" value="${supplyDemand.limitNum  }"/>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="keyword">关键词:</label>
	<div class="col-sm-10 controls">
		<form:input path="keyword" name="keyword" cssClass="form-control" value="${supplyDemand.keyword  }"/>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="startDate">开始日期：</label>
	<div class="col-sm-10 controls">
		<input type = "text" class="form-control datepicker" id="startDate" name="startDate"  value="<fmt:formatDate value="${supplyDemand.startDate}" pattern="yyyy-MM-dd" />"  />
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="endDate">截止日期：</label>
	<div class="col-sm-10 controls">
		<input type = "text" class="form-control datepicker" id="endDate" name="endDate"  value="<fmt:formatDate value="${supplyDemand.endDate}" pattern="yyyy-MM-dd" />"  />
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="introduce">简介:</label>
	<div class="col-sm-10 controls">
		<form:textarea path="introduce" name="introduce" cssClass="form-control intro" placeholder="输入产品简介" cols="10" rows="3" value="${supplyDemand.introduce  }"/>
	</div>
</div>
 
<div class="form-group">
	<label class="col-sm-2 control-label" for="detail">详细内容</label>
	<div class="col-sm-10 controls">
		<form:textarea path="detailInfo" name="detailInfo" cssClass="form-control intro" rows="10" value="${supplyDemand.detailInfo }"/>
	</div>
</div>
	<script type="text/javascript">
	$('.datepicker').datetimepicker({
		minView:"month",
	    format: 'yyyy-mm-dd',
	    language: 'zh-CN'
	});
		/* function getContentDetail(){
			return CKEDITOR.instances.contentDetail.getData(); 
		}

		CKEDITOR.replace('detailInfo',{filebrowserUploadUrl:'${ctx}/mgr/product/upload'}); */
 		$('.intro').wysihtml5({locale: "zh-CN"}); 
		$(function(){
			jQuery(function(){        
		        jQuery.validator.methods.compareDate = function(value, element, param) {

		            var startDate = jQuery(param).val();
		            var arr = startDate.split("-");
		            var starttime = new Date(arr[0], arr[1], arr[2]);
		            var starttimes = starttime.getTime();

		            var arrs = value.split("-");
		            var lktime = new Date(arrs[0], arrs[1], arrs[2]);
		            var lktimes = lktime.getTime();

		            if (starttimes >= lktimes) {
		                alert('开始时间大于离开时间，请检查');
		                return false;
		            }
		            else
		                return true;
		        
		        
		        };
		    });
			$("#form").validate({
				rules:{
					title:{
						required:true,
						maxlength:30
					},
					type:{
						required:true
					},
					typeId:{
						//required:true
					},
					categoryId:{
						//required:true
					},
					introduce:{
						//required:true
					},
					price:{
						//required:true,
						min:0,
						number:true
					},total:{
						//required:true,
						min:0,
						digits:true
					},limitNum:{
						min:0,
						digits:true
					},startDate:{
		                 //required: true
		             },
		             endDate: {
		                 //required: true,
		                 //compareDate: "#startDate"
		             },
		             toUser: {
		                 maxlength:50
		             },
		             tel: {
		                 maxlength:50
		             } 
		             
				},messages:{
					title:{
						required:"必须填写",
						maxlength:"超出30字符"
					},
					type:{
						required:"必须选择"
					},
					typeId:{
						required:"必须选择"
					},
					categoryId:{
						required:"必须填写"
					},
					introduce:{
						required:"必须填写"
					},startDate:{
		                 required: "开始时间不能为空"
		             },
		             endDate:{
		                 required: "结束时间不能为空",
		                 compareDate: "结束日期必须大于开始日期!"
		             }
				}
			});
		});
		
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
