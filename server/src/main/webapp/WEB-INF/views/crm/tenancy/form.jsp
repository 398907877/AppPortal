<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<jsp:include page="../../common/alert-error.jsp" />

<div class="form-group ">
	<label class="col-sm-2 control-label" for="name">企业名称：</label>
	<div class="col-sm-10 controls">
	    
		<input  type = "text" id="name" name="name"  class="form-control required"  value="${tenancy.name}" />
	</div>
</div>

<div class="form-group">
	<label class="col-sm-2 control-label" for="tel">公司电话：</label>
	<div class="col-sm-10 controls">
		<input type = "text" class="form-control" id="tel" name="tel"  value="${tenancy.tel}" />
	</div>
</div>

<div class="form-group">
	<label class="col-sm-2 control-label" for="tel">公司邮箱：</label>
	<div class="col-sm-10 controls">
		<input type = "text" class="form-control" id="email" name="email"  value="${tenancy.email}" />
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="address">公司地址：</label>
	<div class="col-sm-10 controls">
		<input type = "text" class="form-control" id="address" name="address"  value="${tenancy.address}"  />
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="startDate">合同开始日期：</label>
	<div class="col-sm-10 controls">
		<input type = "text" class="form-control datepicker" id="startDate" name="startDate"  value="<fmt:formatDate value="${tenancy.startDate}" pattern="yyyy-MM-dd" />"  />
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="endDate">合同结束日期：</label>
	<div class="col-sm-10 controls">
		<input type = "text" class="form-control datepicker" id="endDate" name="endDate"  value="<fmt:formatDate value="${tenancy.endDate}" pattern="yyyy-MM-dd" />"  />
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="linkName">联系人姓名：</label>
	<div class="col-sm-10 controls">
		<input type = "text" class="form-control" id="linkName" name="linkName"  value="${tenancy.linkName}"  />
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="linkTel">联系人电话：</label>
	<div class="col-sm-10 controls">
		<input type = "text" class="form-control" id="linkTel" name="linkTel"  value="${tenancy.linkTel}"  />
	</div>
</div>

<script type="text/javascript">

$(document).ready(function() {
	$('.datepicker').datetimepicker({
		minView:"month",
	    format: 'yyyy-mm-dd',
	    language: 'zh-CN'
	});
	jQuery(function(){        
        jQuery.validator.methods.compareDate = function(value, element, param) {
            
            var startDate = jQuery(param).val();
            
            var date1 = new Date(Date.parse(startDate.replace("-", "/")));
            var date2 = new Date(Date.parse(value.replace("-", "/")));
            
            return date1 < date2;
        };
    });
	  // 联系电话(手机/电话皆可)验证   
    jQuery.validator.addMethod("isTel", function(value,element) {   
        var length = value.length;   
        var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;   
        var tel = /^(\d{3,4}-?)?\d{7,8}$/g;       
        return this.optional(element) || tel.test(value) || (length==11 && mobile.test(value));   
    }, "请正确填写联系方式"); 
    jQuery.validator.addMethod("isPhone", function(value, element) {    
        var tel = /^(\d{3,4}-?)?\d{7,8}$/g;    
        return this.optional(element) || (tel.test(value));    
      }, "请正确填写您的电话号码。");
	 $("#inputForm").validate({
         focusInvalid:false,
         rules:{
        	 name:{
                 required: true,
                 minlength:5,
				 maxlength:30
             },
             address:{
                 required: true,
                 minlength:5,
				 maxlength:60
             },
             linkName:{
                 required: true,
                 minlength:2,
				 maxlength:60

             },
             startDate:{
                 required: true
             },
             endDate: {
                 required: true,
                 compareDate: "#startDate"
             },
             linkTel:{
            	 isTel:true
             },
             tel:{
            	 isPhone:true
             },
             email:{
            	 email:true
             }
         },
         messages:{
        	name:{
				 required:"必须填写"
			 },
			address:{
				 required:"必须填写"
			},
			linkName:{
				required:"必须填写"
			},
         	startDate:{
                 required: "开始时间不能为空"
             },
             endDate:{
                 required: "结束时间不能为空",
                 compareDate: "结束日期必须大于开始日期!"
             }
         }
     });
	$("#name").focus();

});

</script> 