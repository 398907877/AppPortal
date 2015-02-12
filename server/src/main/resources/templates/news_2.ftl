<!DOCTYPE HTML>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>App接入 -     ${newsCategory.name }</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<style type="text/css">
			.page-header{margin-top:0px}
			.page-header, .content{background-color: white;}
			
		</style>
	</head>
	<body>
		<div class="col-sm-10 thumbnail container">
			<div class="page-header">
				<h4>${news.title }</h4><small class="text-muted">来源:${tenancy.name } <br>${news.crtDate }  </small>
			</div>
			<div class="alert alert-success">
				${news.intro }
			</div>
			<#list contents as con>
			<#if con.video ? exists && con.video != "">
				视频：${con.videoTitle }<br/>
			</#if>
			<#if con.photo ? exists && con.video != "">
				<img src="${con.photo }" alt="标题图片"></img>
				<p class=" text-muted text-center">
					<small>${con.photoTitle}</small>
				</p>
		</#if>
		<p class="form-control-static"> &nbsp &nbsp &nbsp${con.detail }</p>
		</#list>
		</div>
		
	</body>
</html>