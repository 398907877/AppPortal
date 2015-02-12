<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="form-group">
	<label class="col-sm-2 control-label" for="cardName">会员卡名称:</label>
	<div class="col-sm-10 controls">
		<form:input path="cardName" name="cardName" id="cardName" class="form-control input-large required" placeholder="请输入会员卡名称" />
	</div>
</div>
<div class="clearfix visible-xs"></div>
<div class="form-group" id="picFile">
     <label class="col-sm-2 control-label" for="file-input">模板图片：</label>
<div class="col-sm-10 controls" id="uploadpictureid">
	<p class="text-success">建议 大图640*320，小图80*80，JPEG/PNG格式.</p>
	<div data-provides="fileinput" class="fileinput <c:if test='${mcard.cardModel == null}'>fileinput-new</c:if> <c:if test='${mcard.cardModel != null}'>fileinput-exists</c:if>">
				<div class="fileinput-new thumbnail" style="width: 200px; height: 120px;">
						<img data-src="holder.js/100%x100%" alt=""/>
				</div>
				<div  class="fileinput-preview fileinput-exists thumbnail" style="max-width: 200px; max-height: 120px;">
					<c:if test='${mcard.cardModel != null}'><img src="${mcard.cardModel}" style="max-width: 200px;max-height: 120px;"/></c:if>
				</div>
				<div>
					<span class="btn btn-default btn-file">
					<span class="fileinput-new">添加图片</span>
					<span class="fileinput-exists">更改</span>
					<input type="file" class="file-input" name="fileInput_model" id="file" />
					 </span>
					<a class="btn btn-danger fileinput-exists" data-dismiss="fileinput"><span class="glyphicon glyphicon-remove"></span>移除</a>
				</div>
		</div>
	</div>
</div>
<div class="clearfix visible-xs"></div>
<div class="form-group" id="picFile">
     <label class="col-sm-2 control-label" for="file-input">LOGO缩略图：</label>
<div class="col-sm-10 controls" id="uploadpictureid">
	<div data-provides="fileinput" class="fileinput <c:if test='${mcard.logo == null}'>fileinput-new</c:if> <c:if test='${mcard.logo != null}'>fileinput-exists</c:if>">
			   <div class="fileinput-new thumbnail" style="width: 200px; height: 120px;">
						<img data-src="holder.js/100%x100%" alt=""/>
			   </div>
				<div  class="fileinput-preview fileinput-exists thumbnail" style="max-width: 200px; max-height: 120px;">
					<c:if test='${mcard.logo != null}'><img src="${mcard.logo}" style="max-width: 200px;max-height: 120px;"/></c:if>
				</div>
				<div>
					<span class="btn btn-default btn-file">
					<span class="fileinput-new">添加图片</span>
					<span class="fileinput-exists">更改</span>
					<input type="file" class="file-input" name="fileInput_thum" id="file" />
					 </span>
					<a class="btn btn-danger fileinput-exists" data-dismiss="fileinput"><span class="glyphicon glyphicon-remove"></span>移除</a>
				</div>
		</div>
	</div>
</div>
<div class="clearfix visible-xs"></div>
<div class="form-group" id="picFile">
     <label class="col-sm-2 control-label" for="file-input">背景图片：</label>
<div class="col-sm-10 controls" id="uploadpictureid">
    <p class="text-success">建议 大图640*320，小图80*80，JPEG/PNG格式.</p>
	<div data-provides="fileinput" class="fileinput <c:if test='${mcard.background == null}'>fileinput-new</c:if> <c:if test='${mcard.background != null}'>fileinput-exists</c:if>">
				<div class="fileinput-new thumbnail" style="width: 200px; height: 120px;">
						<img data-src="holder.js/100%x100%" alt=""/>
				</div>
				<div  class="fileinput-preview fileinput-exists thumbnail" style="max-width: 200px; max-height: 120px;">
					<c:if test='${mcard.background != null}'><img src="${mcard.background}" style="max-width: 200px;max-height: 120px;"/></c:if>
				</div>
				<div>
					<span class="btn btn-default btn-file">
					<span class="fileinput-new">添加图片</span>
					<span class="fileinput-exists">更改</span>
					<input type="file" class="file-input" name="fileInput_background" id="file" />
					 </span>
					<a class="btn btn-danger fileinput-exists" data-dismiss="fileinput"><span class="glyphicon glyphicon-remove"></span>移除</a>
				</div>
		</div>
	</div>
</div>
<div class="clearfix visible-xs"></div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="discount">折扣额度:</label>
			    <div class="col-sm-10">
			    	<form:input path="discount" name="discount" id="discount" value="${mcard.discount}" class="form-control input-small required" />
			    </div>
			</div>
	
			<div class="form-group">
				<label class="col-sm-2 control-label" for="startDate">生效日期:</label>
			    <div class="col-sm-10">
			    	<form:input path="startDate" name="startDate" id="startDate" class="datepicker form-control input-small required"/>
			    </div>
			</div>			
		
			<div class="form-group">
				<label class="col-sm-2 control-label" for="expDate">截止日期:</label>
			    <div class="col-sm-10">
			    	<form:input path="expDate" name="expDate" id="expDate" class="datepicker form-control input-small required"/>
			    </div>
			</div>					