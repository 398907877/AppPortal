<!DOCTYPE HTML>

<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	    <title>${news.title}</title>
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
	    <meta name="apple-mobile-web-app-capable" content="yes">
	    <meta name="apple-mobile-web-app-status-bar-style" content="black">
	    <meta name="format-detection" content="telephone=no">
	    <link href="http://static.yqkan.com.cn/static/bootstrap/3.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet">
	    <link href="http://static.yqkan.com.cn/static/mobile/news.css" type="text/css" rel="stylesheet">
	    <script type="text/javascript" src="http://static.yqkan.com.cn/static/mobile/NativeBridge.js"></script>
	    <script type="text/javascript">
			document.createElement( "picture" );
			var pics = [];
			<#list contents as con>
				<#if con.photo ? exists && con.photo != "">
					<#list con.photos?keys as itemKey>
						<#if itemKey="large${con.id}">
							pics.push("${con.photos[itemKey]}");
						</#if>
					</#list>
				</#if>
			</#list>
			function clickImg(index){
				showImages(pics,index);
			}
		</script>
		<script type="text/javascript" src="http://static.yqkan.com.cn/static/mobile/picturefill.min.js"></script>
	</head>

	<body>
		<div class="container">
		    <div class="row">
		        <div class="col-xs-12 col-md-12 col-lg-12">
		            <div class="header">
		                <h4 id="news-name">${news.title }</h4>
		                <p class="news-info">
		                    <span id="post-date" class="news-meta no-extra">${news.crtDate?string("yyyy-MM-dd HH:mm:ss")}</span>
		                </p>
		            </div>
		            <div class="page-content">
		            	<blockquote class="bs-callout bs-callout-info">${news.intro }</blockquote>
				<#list contents as con>
					<#if con.video ? exists && con.video != "">
						<div class="panel panel-default">
							<div class="panel-body">
								<video src="${con.video}" width="100%" height="auto" controls="controls">
									Your browser does not support the video tag.
								</video>	
							</div>
						</div>
					</#if>
		            	<div class="text-wrap">	
					
		            	<#if con.photo ? exists && con.photo != "">
		            	<picture>
		            	<#list con.photos?keys as itemKey>
		            		<#if itemKey="large${con.id}">
		            			<source srcset="${con.photos[itemKey]}" media="(min-width: 600px)">
		            		</#if>
		            	</#list>
		            	<#list con.photos?keys as itemKey>
							<#if itemKey="medium${con.id}">
								<source srcset="${con.photos[itemKey]}" media="(min-width: 300px)">
							</#if>
						</#list>
						<#list con.photos?keys as itemKey>
							<#if itemKey="small${con.id}">
								<img srcset="${con.photos[itemKey]}" alt="${con.photoTitle}" onclick="clickImg(${con_index})">
							</#if>
						</#list>
						</picture>
					
						<p class=" text-muted text-center">
							<small>${con.photoTitle}</small>
						</p>
						</#if>	
					
		            		<p>${con.detail}</p>
					
		            	</div>
			</#list>
		            </div>
		        </div>
		    </div>
		</div>
		
	</body>
</html>