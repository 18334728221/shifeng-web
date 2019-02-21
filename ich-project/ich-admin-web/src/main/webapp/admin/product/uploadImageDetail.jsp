<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<style>
#f_a{
width:50%;
}
.file{
	display:inline !important;
	margin: 2px !important;
}
.addPic{
	float:right;
	font-size:25px;
	background: url("../../image/jiaHao.png")no-repeat;
}
.reducePic{
	float:right;
	font-size:30px;
	margin-top: -4px;
	padding-left: 10px;
}
.imgStr img{
    width: 100px;
    height: 100px;
    float: left;
    margin-top:10px;
}
.imgStr img:not(:first-child){
	margin-left: 10px;
}
</style>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>图片上传</title>
	<%@include file="/common/css.jsp"%>
</head>
<body>
	<div class="ctt-box">
		<div class="ctt">
			<p id="f_sub" class="f-sub">
				<a id="f_a"  href="${void0}">
					<input  class="button button-primary button-rounded button-small file" id="0" type="file" name="file" onchange="uploadImg(this,0)"/>
					<span onclick="reducePic()" value="添加图片" class="reducePic" >-</span>
					<span onclick="addPic()" value="添加图片" class="addPic" >+</span>
				</a>
				
				<a id="sub" class="button button-primary button-rounded button-small" href="${void0}">上传</a>
			</p>
			<div id="drop_div" class="drop-div" ondrop="sss(event)" ondragover="sss1(event)">也可将图片拖拽到此处</div>
			<div class="imgStr">
				<!-- <img alt="" src="" id="img"> -->
			</div>
		</div>
	</div>
	<input type="hidden" id="aqId" />
	<%@ include file="/common/js.jsp"%>
	<script src="${ctx}/plugins/layer/layer.js" type="text/javascript"></script>
	<script type="text/javascript">
		var socket;
		var cFile0;
		var cFileName="";
		var id = ${id};
		var num=0;
		var fileLh = $(".file").length;//文件数
		
		function addPic(){
			num++;
			if(num<5){
				var file='<input class="button button-primary button-rounded button-small file" type="file" id='+num+' name="file" onchange="javascript:uploadImg(this,'+num+');" />';
				$("#f_a").append(file);
			}else{
				alert("一次最多上传5张图片");
				return;
			}
		}
		
		function reducePic(){
			if(num>0){
				$(".file").eq(num).remove();
				num--;
			}else{
				alert("最少有一张图片");
				return;
			}
		}
		
		function uploadImg(obj,index){
			var objUrl;
			if(navigator.userAgent.indexOf("MSIE")>0){
				objUrl = obj.value;
			}else{
				objUrl = getObjectURL(obj.files[0]);
			}
			fileLh = $(".file").length;
			var varname="cFile"+index;
			window[varname] = $(".file").eq(index)[0].files[0];
			cFileName += obj.value+",";
		}
		
		$(function(){
			$("#sub").click(function(){
				if(cFile0 == undefined || cFile0 == null){
					alert("请选择图片或者将图片拖拽到指定位置");
					return;
				}
				var formData = new FormData();
			    for(var i=0;i<fileLh;i++){
			    	var varname="cFile"+i;
			    	formData.append("file"+i,window[varname]);	
			    }
			    formData.append("fileLh",fileLh);
			    formData.append("fileName",cFileName);
			    formData.append("isMain",0);
			    formData.append("id",id);
				$.ajax({
					url:"${ctx}/admin/product/product/uploadImage",
					type:"POST",
					data:formData,
					dataType:"json",
					cache: false,
			        contentType: false,
			        processData: false,
					success:function(imagePath){
						if(imagePath){
							layer.msg('恭喜你，上传成功....');
							var imgHttp = imagePath.data.split("-");
							var imgArr = imgHttp[1];
							var imgArrVal = imgArr.split(",");
							for(var i=0;i<imgArrVal.length;i++){
								var imgStr = imgArrVal[i];
								//$("#img").attr("src",imgHttp[0]+"/"+imgStr);
								var img='<img alt="" src="'+imgHttp[0]+"/"+imgStr+'" id="img">';
								$(".imgStr").append(img);
							}
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