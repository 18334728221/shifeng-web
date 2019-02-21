<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>上传模板</title>
<style type="text/css">
	.itemDel, .itemStop, .itemUpload{
		margin-left: 15px;
		color: #018E25;
		cursor: pointer;
	}
	#theList{
		width: 80%;
		min-height: 100px;
/* 		border: 1px solid #018E25; */
	}
	#theList .itemStop{
	    display: none;
	}
	#Container{
    width:1000px;
    margin:0 auto;/*设置整个容器在浏览器中水平居中*/
}
.left{ float:left; width:100px;margin-right:50px;padding-top:20px;}
.right{ float:right; width:100px; background:#0f0; margin-left:50px;}
.center{margin:0 100px; _margin:0 97px;}
</style>
	<link rel="stylesheet" type="text/css" href="${ctx}/plugins/upload/css/styles.css">
	<link rel="stylesheet" href="${ctx}/plugins/upload/css/webuploader.css" />
	<script src="${ctx}/plugins/jquery/jquery-2.1.4.min.js" type="text/javascript"></script>
<!-- 	<script src="js/jquery.min.js" type="text/javascript"></script> -->
	<script src="${ctx}/plugins/upload/js/modernizr.js" type="text/javascript"></script>
	<script src='${ctx}/plugins/upload/js/stopExecutionOnTimeout.js?t=1'></script>
       <script src="${ctx}/plugins/upload/js/md5.js" type="text/javascript" charset="utf-8"></script>
	<script src="${ctx}/plugins/upload/js/webuploader.js" type="text/javascript" charset="utf-8"></script>
	<script src="${ctx}/plugins/upload/js/mywebupload.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<div id="Container">
		<div id="picker" class="left">选择文件</div>
		<div id="uploader" class="center">
			<ul id="theList">
				<div class="htmleaf-content">
				<div class="progress">
				  <b class="progress__bar">
				    <span class="progress__text">
				      	Progress: <em>0%</em>
				    </span>
				  </b>
				</div>
			</div>
			</ul>
		</div>
	</div>
</body>
<script>
var ctx = "${ctx}";
</script>
</html>