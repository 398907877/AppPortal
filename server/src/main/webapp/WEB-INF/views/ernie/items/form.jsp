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
    <input type="text" class="form-control" id="integral" name="integral" value="${ernieItem.integral}">
  </div>
  <div class="form-group">
   <label for="title">奖品数量</label>
    <input type="text" class="form-control" id="num" name="num" value="${ernieItem.num}">
  </div>
  
    <div class="form-group">
   <label for="timer">当前项中奖概率</label>
  <div class="progress progress-now"  style="cursor:pointer">
  <div class="progress-bar" id="progress" role="progressbar" aria-valuenow="${ernieItem.probability == null ? 0 : ernieItem.probability}"  aria-valuemin="0" aria-valuemax="100" style="width: ${ernieItem.probability == null ? 0 : ernieItem.probability}%; ">
    ${ernieItem.probability == null ? 0 : ernieItem.probability}%
  </div>
  </div>
     <input type="hidden" name="probability" id="probability" value="${ernieItem.probability == null ? 0 : ernieItem.probability}" />
  
  </div>
  
    <div class="form-group">
   <label for="timer">其它项中奖概率</label>
  <div class="progress"  style="cursor:pointer">
   <div class="progress-bar progress-bar-danger" id="progress1" role="progressbar" aria-valuenow="${otherProbability }"  aria-valuemin="0" aria-valuemax="100"  style="width: ${otherProbability }%;">
    ${otherProbability }%
  </div>
</div>
</div>


  <input type="hidden" value="${ernieID}" id="ernie" name="ernie.id"/>
   <input type="hidden" value="${ernieItem.id}" id="id" name="id"/>
  <button type="submit" class="btn btn-default">保存</button>
<script type="text/javascript">
$(function(){
	
	 $('.form-group').delegate('.progress-now','mousemove',function(e){    

			var $mouse = e.pageX - $(this).offset().left; 
			var ss = $mouse/this.scrollWidth;
			 var $span = Math.round(ss*100); 
			 var max = 100 - ${otherProbability};
			 if($span <= max)
			{
			 $("#progress").css({ "width":$span+"%" });
	         $("#progress").attr("aria-valuenow",$span);
	         $("#progress").html($span+"%");
			}
	     }).delegate('.progress-now','mouseleave',function(){  
	 
	    	   var width =  $("#progress").attr("aria-valuenow")+"%";
	    	   $("#progress").css({ "width":width });
	    	   $("#probability").val($("#progress").attr("aria-valuenow"));
	           
	     }).delegate('.progress-now','click',function(e){  
	    	
	    	 var $mouse = e.pageX - $(this).offset().left; 
	 		 var ss = $mouse/this.scrollWidth;
	 		 var $span = Math.round(ss*100); 
	 		 var max = 100 - ${otherProbability};
			 if($span <= max)
			{
	 		 $("#progress").attr("aria-valuenow",$span);
	   	     $("#progress").css({ "width":$span+"%" });
	         $("#progress").html($span+"%");
	         $("#probability").val($span);
			}            
	     });     
	
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
				min:0,
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