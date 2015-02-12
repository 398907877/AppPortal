<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.huake.com/functions" prefix="function"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>Bootstrap-定制的会员卡模块显示</title>

<link rel="stylesheet" href="${ctx}/static/forcard/card.css">
<script src="${ctx}/static/forcard/card.js"></script>
<script src="${ctx}/static/layer1.8.4/layer.min.js"></script>
<script src="${ctx}/static/layer1.8.4/extend/layer.ext.js"></script>

<!-- boot strap fileupload -->
<link href="${ctx}/static/bootstrap_fileinput/css/fileinput.css"
	media="all" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/bootstrap_fileinput/js/fileinput.js"
	type="text/javascript"></script>



<style>
/*卡片背景*/
.card .front,.card .back {
	background-image: url('${ctx}/static/forcard/back.jpg');
	background-size: 100% 100%;
}

/*LOGO 背景

.card .front .shiny, .card .back .shiny{
	background-image: url('${ctx}/static/forcard/logo.png');
	background-size: 100% 100%;

}
*/
.demo-container {
	width: 350px;
	margin: 50px auto;
}

form {
	margin: 30px;
}

input {
	width: 200px;
	margin: 10px auto;
	display: block;
}
</style>

<script type="text/javascript">
	$(function() {

		//	$("button[class='btn btn-default kv-fileinput-upload']").css("display","none");

		//把文件上传的的预览图隐藏
		$(".file-preview").css("display", "none");

		$("#fullname").click(function() {

			var index1 = layer.prompt({
				title : '输入个性化名字？'
			},

			function(name) {

				$("#fullname").text(name);
				layer.close(index1);

			});

		});

		//
		$("#backid").click(function() {

			var index1 = layer.prompt({
				title : '背部标识？'
			},

			function(name) {

		
				$("#backid").text(name);
				layer.close(index1);

			});

		});

		//

		$("#dateonly").click(function() {

			var index1 = layer.prompt({
				title : '输入日期'
			},

			function(name) {

	
				$("#dateonly").text(name);
				layer.close(index1);

			});

		});

		//上传自定义  logo	

		$(" #logopic button[class='btn btn-default kv-fileinput-upload']")
				.click(
						function() {

							var image = $(
									"#logopic .file-preview-thumbnails .file-preview-frame  img")
									.attr("src");

							$("#logoonly").attr("src", image);

							return false;

						});

		//上传   自定义  背景  图片

		$("#backpic button[class='btn btn-default kv-fileinput-upload']")
				.click(
						function() {

							var image = $(
									" #backpic .file-preview-thumbnails .file-preview-frame  img")
									.attr("src");

							$(".card .front,.card .back").css(
									"background-image",
									" url(' " + image + " ')");

							//$(".card .front,.card .back").css("background-size","100%  100%");

							//background-image: url('${ctx}/static/forcard/back.jpg');
							//background-size: 100% 100%;

							//alert(image);

							return false;

						});
		
		
		
		
		//选择不一样de 会员卡，背景变化
		
		$("#cardtheme").click(function(){
		
			//var val = $(this).val();
			
			var backval = $(" .panel-body  .radio  input:radio:checked").val();
			
		
			if(backval==null){
				alert("请选择一种 在确认");
				
			}else{
				//高级会员 ONE 普通TWO
				
				if(backval=="ONE"){
			
					
					var imageone = '${ctx}/static/forcard/simpleuser2.jpg';
					
					$(".card .front,.card .back").css(
							"background-image",
							" url(' " + imageone + " ')");
					
					
				}else if(backval=="TWO"){
			
					var imagetwo = '${ctx}/static/forcard/simpleuser1.jpg';
					
					$(".card .front,.card .back").css(
							"background-image",
							" url(' " + imagetwo + " ')");
					
				}
			}
			
			
			
		});
	
		
		
		
		

	});
</script>


</head>
<body>
	<!-- 需要  class="form-container active"   里面包住一个form  -->

	<div class="form-container active">
		<form>



			<div class="container">
				<div class="row">
					<div class="col-md-2">
						<%@ include file="nav.jsp"%>
					</div>
					<div class="col-md-10">



						<div class="bs-callout bs-callout-info">
							<h4>会员卡</h4>
							<p>会员卡</p>
						</div>
						
						<br />

						<legend>会员卡样式编辑</legend>


						<!-- 面版 -->

						<div class="row">
							<div class="col-xs-12 col-md-8">

								<!-- 预览图 -->


								<!--  -->
								<div class="panel panel-default">
									<div class="panel-heading">
										<h3 class="panel-title">预览</h3>
									</div>
									<div class="panel-body">

										<!-- 卡片 -->
										<div class="demo-container">
											<div class="card-wrapper"></div>

										</div>


										<!--  卡片下方按钮 -->
										<div style="margin-left: 30%">

											<button name="turnbutton" id="turnorturn"
												class="btn btn-success" type="button">卡片翻转</button>
											<button id="logoup" class="btn btn-success" type="button">:::::</button>
											<button class="btn btn-danger" type="button">完成编辑</button>

										</div>

										<br />
										<!-- 上传logo -->
										<div id="logopic">
											<div class="form-group">
												<input id="file-1a" type="file" class="file"
													data-preview-file-type="any"
													data-initial-caption="选择上传的LOGO文件（PNG类型）"
													data-overwrite-initial="false">
											</div>
										</div>
										<!-- 上传背景 -->
										<div id="backpic">

											<div class="form-group">
												<input id="file-1a" type="file" class="file"
													data-preview-file-type="any" data-initial-caption="选择上传的壁纸"
													data-overwrite-initial="false">
											</div>


										</div>





										<!--  卡片下方按钮 -->


									</div>
								</div>





							</div>
							<div class="col-xs-6 col-md-4">

								<!-- 缩略图 -->
								<!--  -->
								<div class="panel panel-default">
									<div class="panel-heading">
										<h3 class="panel-title">缩略图</h3>
									</div>
									<div class="panel-body">


										<div>
											<div class="radio">
												<label> <input type="radio" name="optionsRadios"
													id="optionsRadios1" value="ONE"  >高级会员
												</label>
											</div>

											<img src="${ctx}/static/forcard/3333.jpg" alt="..."
												class="img-rounded" width="100%" height="50%">
										</div>



										<div class="radio">
											<label> <input type="radio" name="optionsRadios"
												id="optionsRadios2" value="TWO"   > 普通会员
											</label>
										</div>
										<img src="${ctx}/static/forcard/4444.jpg" alt="..."
											class="img-rounded" width="100%" height="50%">
											
											
									<div   style="margin-top: 20px;"  align="center">		
									<button   id="cardtheme"   type="button" class="btn btn-default">选择该卡种</button>
									</div>
											
									</div>
								</div>


							</div>
						</div>
						<!-- 卡片表单 -->

						<br />
						<br />
						<br />
						<legend>会员卡信息填写</legend>
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="form-group">
									<label for="exampleInputPassword1">唯一编号（系统生成吧）</label> <input
										name="cvc" name="expiry" type="text" class="form-control"
										id="exampleInputPassword1" placeholder=" 唯一编号">
								</div>

								<div class="form-group">
									<label for="exampleInputEmail1"> 输入卡号</label> <input
										name="number" type="text" class="form-control"
										id="exampleInputEmail1" placeholder=" 输入卡号">
								</div>
								<div class="form-group">
									<label for="exampleInputPassword1">输入 个性标识</label> <input
										name="name" type="text" class="form-control"
										id="exampleInputPassword1" placeholder=" 输入 个性标识">
								</div>

								<div class="form-group">
									<label for="exampleInputPassword1">输入到期 月/年</label> <input
										name="expiry" type="text" class="form-control"
										id="exampleInputPassword1" placeholder=" 输入到期 月/年">
								</div>
								<div class="form-group">
									<label for="exampleInputFile">LOGO上传</label>
								</div>
								<button type="submit" class="btn btn-default">Submit</button>

							</div>
						</div>
					</div>
				</div>
			</div>

		</form>
	</div>
	<script>
		$('.active form').card({
			container : $('.card-wrapper')
		})
	</script>
</body>
</html>