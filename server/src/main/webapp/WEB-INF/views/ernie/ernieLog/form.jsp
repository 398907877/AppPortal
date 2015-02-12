<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

  <div class="form-group" style="height: 30px;line-height: 30px">
    <label class="col-sm-2 control-label">中奖用户:</label>
    <div class="col-sm-10">
       <form:select path="memberId" id="e1" cssClass="populate placeholder select2-offscreen" name="memberId" style="min-width:230px" tabindex="-1" title="">
			<form:options items="${TenancyUsers }" itemLabel="name" itemValue="id"/>
		</form:select>
		</div>
  </div>

  <div class="form-group" style="height: 30px;line-height: 30px" >
  <label class="col-sm-2 control-label">中奖活动:</label>
    <div class="col-sm-10">
      <form:select path="erineId" id="e2" cssClass="populate placeholder select2-offscreen" name="erineId" style="min-width:230px" tabindex="-1" title="">
			<form:options items="${ernies }" itemLabel="title" itemValue="id"/>
		</form:select>
		</div>
      </div>
  <div class="form-group" style="height: 30px;line-height: 30px">
   <label class="col-sm-2 control-label">中奖奖品:</label>
    <div class="col-sm-10">
       <form:select path="winning" id="e3" cssClass="populate placeholder select2-offscreen" name="winning" style="min-width:230px" tabindex="-1" title="">
			<form:options items="${items }" itemLabel="name" itemValue="id"/>
		</form:select>
		</div>
  </div>
  <div class="form-group" style="height: 30px;line-height: 30px">
    <label class="col-sm-2 control-label">中奖时间:</label>
    <div class="col-sm-10">
		<input type = "text" class="form-control datepicker" id="createdAt" name="createdAt"  value="<fmt:formatDate value='${ernieLog.createdAt}' pattern='yyyy-MM-dd' />"  />
    </div>
  </div>
 

  <input type="hidden" value="${currentTenancyID }" id="uid" name="uid"/>
  <button type="submit" class="btn btn-default">保存</button>
<script type="text/javascript">
$(document).ready(function() {
	$("#e1").select2({
	    placeholder: "---------中奖用户-------",
	    allowClear: true
	});
	
	$("#e2").select2({
	    placeholder: "---------中奖活动-------",
	    allowClear: true
	});
	
	$("#e3").select2({
	    placeholder: "---------中奖奖品-------",
	    allowClear: true
	});
	

	
	$('.datepicker').datetimepicker({
		minView:"month",
	    format: 'yyyy-mm-dd hh:mm:ss',
	    language: 'zh-CN'
	});
	
	   $("#e2").change(function(){
			 $.ajax({
		         url: "${ctx}/mgr/ernieLog/"+$(this).val()+"/selectItems",
		         type: 'GET',
		         async:false,
		         contentType: "application/json;charset=UTF-8"
		       })
		       .done(function( date ) {
		    	   $("#winning").empty();
		    	   $.each(date,function(k,val){
		    		    $("#winning").append("<option  value='"+val.id+"'>"+val.name+"</option>");
		    		  }); 
		       }).fail( function(jqXHR, textStatus){
		    	  alert("删除失败jqXHR="+jqXHR+"textStatus="+textStatus);
		   });
		  });
	
	
	
	
	
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