<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<style>
	.vt{
		margin-bottom:15px;
	}
	.pt{
		margin-top:5px;
	}
</style>
<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
<c:if test="${news.categoryId != null }">			
<div class="form-group">
	<label class="col-sm-2 control-label" for="categoryId">资讯类型</label>
	<div class="col-sm-10 controls">
		<form:select path="categoryId" id="e2" cssClass="populate placeholder select2-offscreen" name="categoryId" style="min-width:230px" tabindex="-1" title="">
			<form:options items="${newsCategorys }" itemLabel="name" itemValue="id"/>
		</form:select>
	</div>
</div>
</c:if>
<c:if test="${news.categoryId == null }">
<div class="form-group ">
                <label class="col-sm-2 control-label" for="title">资讯类型</label>
                <div class="col-sm-10 controls">
                 <select id="e2" name="categoryId" style="min-width:230px" class="populate placeholder select2-offscreen" tabindex="-1" title=""><option></option>
                       <c:forEach items="${newsCategorys}" var="category">
                          <option value="${category.id}">${category.name}</option>
                     </c:forEach>
              </select>
              </div>
      </div>
</c:if>
<div class="form-group">
	<label class="col-sm-2 control-label" for="title">资讯标题</label>
	<div class="col-sm-10 controls">
	<div class="input-group">
		<form:input path="title" name="title" cssClass="input form-control" value="${news.title }"/>
		 <span class="input-group-addon">
      	 	<input type="checkbox" id="setTop" <c:if test="${news.stick != null && news.stick != '' }">checked</c:if> />置顶
			<input type="hidden" value="1" <c:if test="${news.stick != null && news.stick != '' }"> name="stick"</c:if> id="stick">
			<input type="checkbox" id="setBanner" <c:if test="${news.banner != null && news.banner != '0' }">checked</c:if>/>设为Banner
			<input type="hidden" value="1" <c:if test="${news.banner != null && news.banner != '0' }"> name="banner"</c:if> id="banner">
			
     	 </span>
		
	</div>
	</div>
</div>
<div class="form-group" id="picFile">
     <label class="col-sm-2 control-label" for="file-input">标题图片</label>
<c:if test="${news.pic != null && news.pic!=''}">
<div class="col-sm-10 controls" id="uploadpictureid">
	<div data-provides="fileinput" class="fileinput fileinput-exists">
					<div class="fileinput-new thumbnail" style="width: 300px; height: 150px;">
						<img data-src="holder.js/300x150" alt=""/>
					</div>
				<div  class="fileinput-preview fileinput-exists thumbnail" style="max-width: 300px; max-height: 150px;">
					<img src="${news.pic}" style="max-width: 300px;max-height: 150px;"/>
				</div>
				<div>
					<span class="btn btn-default btn-file">
					<span class="fileinput-new">添加图片</span>
					<span class="fileinput-exists">更改</span>
					<input type="file" class="file-input" name="fileInput_thum" id="file" />
					<input type="hidden" name="pic" id="pic" value="${news.pic }" />
					 </span>
					<a class="btn btn-danger fileinput-exists" id="removePic" data-dismiss="fileinput"><span class="glyphicon glyphicon-remove"></span>移除</a>
				</div>
				</div>
	</div>

</c:if>
<c:if test="${ news.pic == null || news.pic==''}">
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
				<a class="btn btn-danger fileinput-exists" data-dismiss="fileinput"><span class="glyphicon glyphicon-remove"></span>移除</a>
			</div>
	</div>
</div>
</c:if>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="intro">资讯简介</label>
	<div class="col-sm-10 controls">
		<form:textarea path="intro" name="intro" rows="4" cssClass="form-control required detail" value="${news.intro }" placeholder="此处输入简介"/>
	</div>
</div>


<c:forEach var="con" items="${ content}" varStatus="status"> 	
<div class="form-group content" id="content${status.index+1}">
	<input type="hidden" name="contents[${status.index}].id" value="${con.id }" class="cid" id="contentid${status.index+1}"/>
	<div class="col-sm-2">
		<label class="control-label" for="detail">详细内容${status.index+1} </label>
		<a class="btn btn-danger btn-xs deleteOld" onclick="deleteOldContent(${con.id},${status.index+1})">删除当前</a>
	</div>
	<div class="col-sm-10 controls">
		<c:if test="${con.photo==null || con.photo==''}">
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
				<input type="file" class="file-input" name="photo" id="file" />
			 </span>
				<a class="btn btn-danger fileinput-exists" data-dismiss="fileinput"><span class="glyphicon glyphicon-remove"></span>移除</a>
			</div>
			<input type="text" name="contents[${status.index}].photoTitle" class="form-control pt" id="pt${status.index+1}" placeholder="此处输入图片标题，非必填" value="${con.photoTitle }"/>
			</div>
		</c:if>
		<c:if test="${con.photo!=null }">
			<div data-provides="fileinput" class="fileinput fileinput-exists">
					<div class="fileinput-new thumbnail" style="width: 300px; height: 150px;">
						<img data-src="holder.js/300x150" alt=""/>
					</div>
				<div  class="fileinput-preview fileinput-exists thumbnail" style="max-width: 300px;max-height: 150px;">
					<img src="${con.photo}" />
				</div>
				<div>
					<span class="btn btn-default btn-file">
					<span class="fileinput-new">添加图片</span>
					<span class="fileinput-exists">更改</span>
					<input type="file" class="file-input" name="photo" id="file" />
					<input type="hidden" name="contents[${status.index}].photo" id="photo${status.index}" value="${con.photo }"/>
					 </span>
					<a onclick="deleteOld(${status.index})" class="btn btn-danger fileinput-exists" data-dismiss="fileinput"><span class="glyphicon glyphicon-remove"></span>移除</a>
				</div>
				<input type="text" name="contents[${status.index}].photoTitle" class="form-control pt" id="pt${status.index+1}" placeholder="此处输入图片标题，非必填" value="${con.photoTitle }"/>
				</div>
		</c:if>
		
		<div id="queue"></div>  
   		<input id="file${status.index+1}" name="vfile" type="file" class="vfile"> 
   		<input type="hidden" name="contents[${status.index}].video" id="video${status.index+1}" value="${con.video }" class="video" />
   		<input type="text" name="contents[${status.index}].videoTitle" value="${con.videoTitle}" class="form-control vt" id="vt${status.index+1}" placeholder="此处输入视频标题，非必填"/>
   		<textarea name="contents[${status.index}].detail" rows="10" class="input-xlarge form-control detail intro" placeholder="此处输入内容详细">${con.detail }</textarea>
	</div>
	
	
	
	
</div>
</c:forEach>

<c:if test="${content == null }" >
<div class="form-group content" id="content1">
	<div class="col-sm-2">
		<label  for="detail">详细内容1</label>
	 	<a class="btn btn-danger btn-xs deleteCon" onclick="deleteCon(1)">删除当前</a>
	</div>
	<div class="col-sm-10 controls">
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
				<input type="file" class="file-input" name="photo" id="file" />
			 </span>
				<a class="btn btn-danger fileinput-exists" data-dismiss="fileinput"><span class="glyphicon glyphicon-remove"></span>移除</a>
			</div>
			<input type="text" name="contents[0].photoTitle" value="" class="form-control pt" id="pt1" placeholder="此处输入图片标题，非必填"/>
		</div>
		<input type="hidden" name="contents[0].video" id="video1" class="video"/>
		<div id="queue"></div>  
   		<input id="file1" name="vfile" type="file" class="vfile"> 
   		<input type="text" name="contents[0].videoTitle" value="" class="form-control vt" id="vt1" placeholder="此处输入视频标题，非必填"/>
   		<textarea name="contents[0].detail" rows="10" class="input-xlarge form-control detail intro" placeholder="此处输入内容详细"></textarea>
		
	</div>
</div>
</c:if>
	<div class="form-group">
		<label class="col-sm-2 control-label"></label>
		<div class="col-sm-10 controls">
			<input type="button" class="btn btn-success" name="contiueadd" id="contiueadd" value="继续添加">
		</div>
	</div>
		
	<script type="text/javascript">
	var conCount="${fn:length(content)}"=="0"? 1 : ${fn:length(content)};//记录新闻详细内容数量
	$("#e2").select2({
	    placeholder: "---------请选择新闻类型-------",
	    allowClear: true
	});
	function deleteOld(index){
		$("#photo"+index).remove();
	}
/* 		CKEDITOR.replace('intro',{width:600,filebrowserUploadUrl:'${ctx}/mgr/news/upload'});
		function getContentDetail(){
			return CKEDITOR.instances.contentDetail.getData(); 
		}
		CKEDITOR.replace('detail',{filebrowserUploadUrl:'${ctx}/mgr/product/upload'});
*/
		
		$(function(){
			$('.intro').wysihtml5({locale: "zh-CN"});
			$("#setTop").click(function(){
				var ischeck = document.getElementById("setTop").checked;
				if(ischeck){
					$("#stick").attr("name","stick");
				}else{
					$("#stick").attr("name","");
				}
			});
			$("#setBanner").click(function(){
				var ischeck = document.getElementById("setBanner").checked;
				if(ischeck){
					$("#banner").attr("name","banner");
				}else{
					$("#banner").attr("name","");
				}
			});
			if(conCount <= 1){
				$(".deleteOld").hide();
				$(".deleteCon").hide();
			}
			for(var i=1;i<conCount+1;i++){
				
				var video = $("#video"+i).val();
				if(video != undefined && video != ""){
					video = video.replace(/^.+\d{4}_\d{1,2}_\d{1,2}_\d{13}_/,"");
					$("#video"+i).after("<div id='old"+i+"' class='uploadify-queue'><div id='' class='uploadify-queue-item'>"
					+"<div class='cancel'>"						
					+"<a href='javascript:delVideo("+i+")' style='background-image: url(${ctx}/static/uploadify/uploadify-cancel.png); background-position: 0px 0px; background-repeat: no-repeat no-repeat;'>X</a>					</div>					<span class='fileName'>"+video+"</span><span class='data'> - 已上传</span>"					
					+"<div class='uploadify-progress'>"						
						+"<div class='uploadify-progress-bar' style='width: 100%;'></div>"
					+"</div>"				
					+"</div></div>");
					$("#vt"+i).show();
					
					upload(i,true);
				}else{
					$("#vt"+i).hide();
					upload(i,false);
				}
			}
			//window.setTimeout($("#file2").uploadify("disable",true),20000)
			$('.fileinput').fileinput();
			$("#form").validate({
				rules:{
					title:{
						required:true,
						maxlength:30
					},
					intro:{
						required:true
					},
					categoryId:{
						required:true
					}
				},messages:{
					title:{
						required:"必须填写",
						maxlength:"超出30字符"
					},
					categoryId:{
						required:"必须填写"
					},
					intro:{
						required:"必须填写"
					}
				}
			});
		});
	
	/* $(".picture").click(function(){
		$("#uploadpictureid").append("普通图片：<input class='input-file' name='fileInput_pic' id='fileInput' type='file'><lable style='color:green'>提示:此处直接上传原图 </lable><br>");
	}); */
	$("#contiueadd").click(function(){
		var cur = conCount+1;
		$("#content"+conCount).after("<div class='form-group content' id='content"+cur+"'>"
				+"<div class='col-sm-2'>"
				+"<label class=' control-label' for='detail'>详细内容"+cur+"</label><a class='btn btn-danger btn-xs deleteCon' onclick='deleteCon("+cur+")'>删除当前</a></div>"
				+"<div class='col-sm-10 controls'>"
					+"<div class='fileinput fileinput-new' data-provides='fileinput'>"
						+"<div class='fileinput-new thumbnail' style='width: 300px; height: 150px;'>"
							+" <img data-src='holder.js/300x150' alt=''/>"
						 +"</div>"
							+"<div class='fileinput-preview fileinput-exists thumbnail' style='max-width: 300px; max-height: 150px;'>"
							+"</div>"
						+"<div>"
							+"<span class='btn btn-default btn-file'>"
							+"<span class='fileinput-new'>添加图片</span>"
							+"<span class='fileinput-exists'>更改</span>"
							+"<input type='file' class='file-input' name='photo' />"
							+"</span>"
						 +"<a class='btn btn-danger fileinput-exists' data-dismiss='fileinput'><span class='glyphicon glyphicon-remove'></span>移除</a>"
						+"</div>"
						+"<input type='text' name='contents["+conCount+"].photoTitle' value='' class='form-control pt' id='pt"+cur+"' placeholder='此处输入图片标题，非必填'/>"
					+"</div>"
					+"<input type='hidden' name='contents["+conCount+"].video' id='video"+cur+"' class='video'/>"
					+"<div id='queue'></div>"  
					+"<input id='file"+cur+"' name='vfile' type='file' class='vfile'>"
					+"<input type='text' name='contents["+conCount+"].videoTitle' value='' class='form-control vt' id='vt"+cur+"' placeholder='此处输入视频标题，非必填'/>"
			   		+"<textarea name='contents["+conCount+"].detail' rows='10' class='input-xlarge form-control detail addIntro' placeholder='此处输入内容详细'></textarea>"
			   	+"</div>"
			+"</div>");
		upload(cur,false);
		$("#vt"+cur).hide();
		conCount = cur;
		Holder.run();
		$('.addIntro').wysihtml5({locale: "zh-CN"});
		if(conCount>1){
			$(".deleteOld").show();
			$(".deleteCon").show();
		};
	});
	function upload(id,disable){
		$("#file"+id).uploadify({  
	        method   : 'post',  
	        swf           : '${ctx}/static/uploadify/uploadify.swf',  // uploadify.swf在项目中的路径  
	        uploader      : '${ctx}/mgr/news/uploadify',  // 后台Controller处理上传的方法  
	        fileObjName     : 'vfile',         
	        successTimeout  : 30,                
	        removeCompleted : false,            
	        fileSizeLimit : '500MB',  
	        buttonText      : '添加视频', 
	        height        : 30,  
	        width         : 120,
	        auto          : true,
	        multi		  : false,
	        onSWFReady    :function(){
				if(disable){
					$("#file"+id).uploadify("disable",true);
				}
	        },
	       // uploadLimit:1,
	      //  fileTypeExts  : '*.mp4,*.mkv',
	      //fileTypeDesc    : ''
	        onSelect: function(e, queueId, fileObj) { 
	        	$(".uploadify-queue-item>.cancel>a").css("background","url('${ctx}/static/uploadify/uploadify-cancel.png') 0 0 no-repeat");
	            //alert('The file ' + file.name + ' was added to the queue!');
	        },
	        onUploadSuccess:function(file,data,response){
	        	$("#video"+id).val(data);
	        	$("#file"+id).uploadify("disable",true);
	        	$("#vt"+id).show();
				var cancel=$('#file'+id+'-queue .uploadify-queue-item[id="' + file.id + '"]').find(".cancel a");
				if (cancel) {
					//自定义取消点击事件
				  // cancel.attr("rel", "file"+id);
				   cancel.click(function () {
				                //在这此处处理...
				                $("#file"+id).uploadify("disable",false);
					   	var item = $('#file' +id+"-queue" ).find('.uploadify-queue-item').get(0);
						$item = $(item);
						$("#file"+id).data('uploadify').cancelUpload($item.attr('id'));
						$item.find('.data').removeClass('data').html(' - Cancelled');
						$item.find('.uploadify-progress-bar').remove();
						$item.delay(1000).fadeOut(500, function() {
							$(this).remove();
						});
						$("#video"+id).val("");
						$("#file"+id+"-button").css("display","block");
						$("#vt"+id).hide();
						$("#vt"+id).val("");
				   });
				};
	        }
	    });
	};
	function deleteCon(index){
		$("#content"+index).remove();
		var cur = conCount -1;
		for(var i=0;i<$(".content").length;i++){
			var $content = $($(".content").get(i));
			var cindex = i+1;//
			$content.attr("id","content"+cindex);
			$content.find("label").text("详细内容"+cindex);
			$content.find("a.deleteCon").attr("onclick","deleteCon("+cindex+")");
			var cid = $content.find(".cid").val();
			$content.find("a.deleteOld").attr("onclick","deleteOldContent("+cid+","+cindex+")");
			$content.find(".video").attr("id","video"+cindex);
			$content.find(".video").attr("name","contents["+i+"].video");
			$content.find(".cid").attr("name","contents["+i+"].id");
			
			$content.find(".vt").attr("id","vt"+cindex);
			$content.find(".vt").attr("name","contents["+i+"].videoTitle");
			
			$content.find(".pt").attr("name","contents["+i+"].photoTitle");
			
			$content.find(".vfile").attr("id","file"+cid);
			$content.find(".detail").attr("name","contents["+i+"].detail");
			
		}
		if(cur <=1){
			$(".deleteOld").hide();
			$(".deleteCon").hide();
		}
		conCount = cur;
	};
	function delVideo(id){
		if(!window.confirm("确定要删除该视频吗？")){
			return false;
		}
		$("#old"+id).remove();
		$("#file"+id).uploadify("disable",false);
		$("#video"+id).val("");
		$("#vt"+id).hide();
		$("#vt"+id).val("");
	};
	
	$("#removePic").click(function(){
		$("#pic").val("");
	});
	</script>  
