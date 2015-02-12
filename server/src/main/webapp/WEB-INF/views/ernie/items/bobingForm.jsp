<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
  <div class="form-group">
    <label for="title">奖项名称</label>
    <input type="text" class="form-control" id="name" name="name" value="${ernieItem.name}">
  </div>
  <div class="form-group" id="image">
	<label for="image">奖品图：</label>
	<div  id="uploadpictureid">
		<div class="fileinput <c:if test='${ernieItem.image == null || ernieItem.image==""}'>fileinput-new</c:if> <c:if test='${ernieItem.image != null && ernieItem.image!=""}'>fileinput-exists</c:if>" data-provides="fileinput">
		  <div class="fileinput-new thumbnail" style="width: 300px; height: 150px;">
		    <img data-src="holder.js/300x150" alt="奖品图">
		  </div>
		  <div  class="fileinput-preview fileinput-exists thumbnail" style="max-width: 300px; max-height: 150px;">
			<c:if test='${ernieItem.image != null && ernieItem.image!=""}'><img src="${ernieItem.image}" style="max-width: 300px;max-height: 150px;"/></c:if>
		  </div>
		  <div>
		    <span class="btn btn-default btn-file"><span class="fileinput-new">选择图片</span><span class="fileinput-exists">更改</span>
		    <input type="file" class="file-input" name="fileInput" id="file"></span>
		    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">移除</a>
		    <input type="hidden" value="${ernieItem.image}" id="image" name="image"/>
		  </div>
		</div>
	</div>
</div>
  <div class="form-group">
    <label for="title">营销活动</label>
    <input type="text" class="form-control" value="${ernieTitle}" disabled='disabled'>
  </div>
  <div class="form-group">
   <label for="title">中奖积分</label>
    <input type="text" class="form-control" id="integral" name="integral" value="${ernieItem.integral==null ? '0' : ernieItem.integral }">
  </div>
  <div class="form-group">
   <label for="title">奖品数量</label>
    <input type="text" class="form-control" id="num" name="num" value="${ernieItem.num}">
  </div>
  <div class="form-group">
    <label for="bobing">奖品等级</label><span style="color: red">（每个奖品等级仅能有一个奖品！）</span>
    <form:select path="bobing" items="${ernieBobing}" itemLabel="value" itemValue="key" cssClass="form-control" />
  </div>
  <input type="hidden" value="${ernieID}" id="ernie" name="ernie.id"/>
   <input type="hidden" value="${ernieItem.id}" id="id" name="id"/>
  <button type="submit" class="btn btn-default">保存</button>
<script type="text/javascript">
$(function(){
	
	$('.wysihtml5').wysihtml5({locale: "zh-CN"});
	$('.fileinput').fileinput();
	$('.datetimepicker').datetimepicker({
        format: 'yyyy/mm/dd hh:mm',
        pickDate: true,
        pickTime: true,
        hourStep: 1,
        minuteStep: 15,
        secondStep: 30,
        inputMask: true
      });
	
	$("#from").validate({
		rules:{
			name:{
				required:true,
				maxlength:15
			},
			integral:{
				required:true,
				min:0,
				number:true
			},
			probability:{
				required:true,
				min:0,
				number:true
			},
			num:{
				required:true,
				min:1,
				number:true
			}
		},messages:{
			name:{
				required:"必须填写",
				maxlength:"超出15字符"
			},
			integral:{
				required:"必须填写"
			},
			probability:{
				required:"必须填写"
			},
			num:{
				required:"必须填写"
			}
		}
	});
});
</script>