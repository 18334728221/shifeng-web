<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>身份证上传</title>
	<%@include file="/common/css.jsp"%>
</head>
<body>
	<div class="ctt-box">
		<div class="ctt">
			<p id="f_sub" class="f-sub">
			<a id="f_a"  href="${void0}"><input class="button button-primary button-rounded button-small" type="file" id="f" name="file" /></a>
			<a id="s" class="button button-primary button-rounded button-small" href="${void0}">上传正面身份证</a></p>
			<div id="drop_div" class="drop-div" ondrop="sss(event)" ondragover="sss1(event)">也可将图片拖拽到此处</div>
		</div>
	</div>
	<input type="hidden" id="aqId" />
	<%@ include file="/common/js.jsp"%>
	<script src="${ctx}/plugins/layer/layer.js" type="text/javascript"></script>
	<script type="text/javascript">
		var socket;
		var cFile;
		var cFileName;
		var id = ${id};
		$(function(){
			$("#f").change(function(){
				var objUrl;
				if(navigator.userAgent.indexOf("MSIE")>0){
					objUrl = this.value;
				}else{
					objUrl = getObjectURL(this.files[0]);
				}
				cFile = this.files[0];
				cFileName = $("#f").val();
				$("#img").attr("src",objUrl);
			});
			$("#s").click(function(){
				if(cFile == undefined || cFile == null){
					alert("请选择图片或者将图片拖拽到指定位置");
					return;
				}
				var formData = new FormData();
			    formData.append("file",cFile);
			    formData.append("fileName",cFileName);
			    formData.append("id",id);
				$.ajax({
					url:"${ctx}/admin/base/category/uploadImage",
					type:"POST",
					data:formData,
					dataType:"json",
					cache: false,
			        contentType: false,
			        processData: false,
					success:function(imagePath){
						if(imagePath){
							layer.msg('恭喜你，上传成功....');
							parent.$("#img").attr("src",imagePath.data);
							parent.layer.closeAll();
						}
					}
				});
			});
		});
		function getObjectURL(file) {
			var url = null ; 
			if (window.createObjectURL!=undefined) { // basic
				url = window.createObjectURL(file) ;
			} else if (window.URL!=undefined) { // mozilla(firefox)
				url = window.URL.createObjectURL(file) ;
			} else if (window.webkitURL!=undefined) { // webkit or chrome
				url = window.webkitURL.createObjectURL(file) ;
			}
			return url ;
		}
	 	//也可将图片拖拽到此处
		function sss(event){
			event.stopPropagation();
			event.preventDefault();
			console.info(event.dataTransfer);
			var objUrl;
			if(navigator.userAgent.indexOf("MSIE")>0){
				objUrl = this.value;
			}else{
				objUrl = getObjectURL(event.dataTransfer.files[0]);
			}
			cFile = event.dataTransfer.files[0];
			cFileName = event.dataTransfer.files[0].name;
			$("#img").attr("src",objUrl);
		}
		//也可将图片拖拽到此处
		function sss1(event){
			event.stopPropagation();
			event.preventDefault();
		}
	</script>
</body>
</html>