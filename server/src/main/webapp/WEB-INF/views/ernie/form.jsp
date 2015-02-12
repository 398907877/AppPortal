<!DOCTYPE>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

  <div class="form-group">
    <label for="title">活动标题</label>
    <input type="text" class="form-control" id="title" name="title" value="${ernie.title }" ng-model="appid">
  </div>


<c:if test="${ ernie.image != null && ernie.image!='' }">
	<div class="form-group" id="image">
		<label for="image">宣传图：</label>
		<div id="uploadpictureid">

			<div data-provides="fileinput" class="fileinput fileinput-exists">
				<div class="fileinput-new thumbnail"
					style="width: 300px; height: 150px;">
					<img data-src="holder.js/300x150" alt="" />
				</div>
				<div class="fileinput-preview fileinput-exists thumbnail"
					style="max-width: 300px; max-height: 150px;">
					<img src="${ernie.image}"
						style="max-width: 300px; max-height: 150px;" />
				</div>
				<div>
					<span class="btn btn-default btn-file"> <span
						class="fileinput-new">选择图片</span> <span class="fileinput-exists">更改</span>
						<input type="file" class="file-input" name="images"
						id="file" /> 
					</span> <a class="btn btn-danger fileinput-exists" id="removePic"
						data-dismiss="fileinput"><span
						class="glyphicon glyphicon-remove"></span>移除</a>
				</div>
			</div>
		</div>
	</div>

</c:if>

<c:if test="${ernie.image  == null || ernie.image==''}">

  <div class="form-group" id="image">
	<label for="image">宣传图：</label>
	<div  id="uploadpictureid">
		<div class="fileinput fileinput-new" data-provides="fileinput">
		  <div class="fileinput-new thumbnail" style="width: 300px; height: 150px;">
		    <img data-src="holder.js/300x150" alt="宣传图">
		  </div>
		  <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 300px; max-height: 150px;"></div>
		  <div>
		    <span class="btn btn-default btn-file"><span class="fileinput-new">选择图片</span><span class="fileinput-exists">更改</span><input type="file" name="images"></span>
		    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">移除</a>
		  </div>
		</div>
	</div>
</div>
</c:if >

  <div class="form-group">
    <label for="description">简要说明</label>
    <textarea rows="3" name="description" id="description"  class="form-control">${ernie.description}</textarea>
  </div>
  <div class="form-group">
    <label for="content">详细内容</label>
    <textarea rows="6" name="content"  class="form-control intro">${ernie.content}</textarea>
  </div>
  <div class="form-group">
    <label for="startDate">开始时间</label>
    <div class="row">
    	<div class="col-lg-6">
    		<div class='input-group date datetimepicker'>
         		<input type='text' class="form-control" id="startDate" name="startDate" value="<fmt:formatDate value="${ernie.startDate}" pattern="yyyy/MM/dd HH:mm" />"/>
         		<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
         		</span>
     		</div>
     	</div>
     </div>
  </div>
  <div class="form-group">
    <label for="endDate">结束时间</label>
    <div class="row">
    	<div class="col-lg-6">
    		<div class='input-group date datetimepicker'>
         		<input type='text' class="form-control" id="endDate" name="endDate" value="<fmt:formatDate value="${ernie.endDate}" pattern="yyyy/MM/dd HH:mm" />"/>
         		<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
         		</span>
     		</div>
    	</div>
    </div>
  </div>
  <div class="form-group">
    <label for="timer">抽奖次数（单人）</label>
    <input type="text" class="form-control" id="time" name="time" value="${ernie.time }" >
  </div>
  <div class="form-group">
   <label for="timer">中奖概率</label><span style="color: red">（博饼没有中奖概率！）</span>
  <div class="progress"  style="cursor:pointer">
  <div class="progress-bar" id="progress" role="progressbar" aria-valuenow="${ernie.probability == null ? 0 : ernie.probability}" aria-valuemin="0" aria-valuemax="100" style="width: ${ernie.probability == null ? 0 : ernie.probability}%;">
    ${ernie.probability == null ? 0 : ernie.probability}%
  </div>

   <input type="hidden" name="probability" id="probability" value="${ernie.probability == null ? 0 : ernie.probability}" />

</div>
</div>

  
  <div class="form-group">
    <label for="category">互动种类</label>
    <form:select path="category" items="${ernieCates}" itemLabel="value" itemValue="key" cssClass="form-control" />
  </div>
  <button type="submit" class="btn btn-default">保存</button>
<script type="text/javascript">
$('.intro').wysihtml5({locale: "zh-CN"});
$(function(){
	 $('.form-group').delegate('.progress','mousemove',function(e){    
			
			
			var $mouse = e.pageX - $(this).offset().left; 
			var ss = $mouse/this.scrollWidth;
			 var $span = Math.round(ss*100); 
			 $("#progress").css({ "width":$span+"%" });
	         $("#progress").attr("aria-valuenow",$span);
	         $("#progress").html($span+"%");
	        
	     }).delegate('.progress','mouseleave',function(){  
	    	   var width =  $("#progress").attr("aria-valuenow")+"%";
	    	   $("#progress").css({ "width":width });
	    	   $("#probability").val($("#progress").attr("aria-valuenow"));
	   
	     }).delegate('.progress','click',function(e){  
	    	
	    	 var $mouse = e.pageX - $(this).offset().left; 
	 		 var ss = $mouse/this.scrollWidth;
	 		 var $span = Math.round(ss*100); 
	 		 $("#progress").attr("aria-valuenow",$span);
	   	     $("#progress").css({ "width":$span+"%" });
	         $("#progress").html($span+"%");
	         $("#probability").val($span);


	                
	     });     
	
	
	$('.fileinput').fileinput();
	$('.datetimepicker').datetimepicker({
        format: 'yyyy/mm/dd hh:ii',
        pickDate: true,
        pickTime: true,
        hourStep: 1,
        minuteStep: 15,
        inputMask: true,
        language: 'zh-CN'
      });
	   jQuery.validator.methods.compareDate = function(value, element, param) {
           var startDate = jQuery(param).val();
           var date1 = new Date(Date.parse(startDate.replace("-", "/")));
           var date2 = new Date(Date.parse(value.replace("-", "/")));         
           return date1 < date2;
       };
	
	$("#form").validate({
		rules:{
			title:{
				required:true,
				maxlength:20
			},
			description:{
				required:true
			},
			content:{
				required:true
			},
			startDate:{
	            required: true
	        },
	        endDate: {
	            required: true,
	            compareDate: "#startDate"
	        },
	        time:{
				required:true,
				min:0,
				number:true
			}
		},messages:{
			title:{
				required:"必须填写",
				maxlength:"超出20字符"
			},
			description:{
				required:"必须填写"
			}
			,
			content:{
				required:"必须填写"
			},
			startDate:{
                required: "开始时间不能为空"
            },
            endDate:{
                required: "结束时间不能为空",
                compareDate: "结束日期必须大于开始日期!"
            },
            time:{
				required:"必须填写"
			}
			
		}
	});
	
	
});
</script>