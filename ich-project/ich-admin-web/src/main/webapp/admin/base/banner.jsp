<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<style>
.bannerBtn_carouse{
	margin: 5px 10px 0 0;
}
</style>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>产品图片管理</title>
	<%@include file="/common/css.jsp"%>
</head>
<style>
	 .img_body{width: 254px;height: 320px;}
</style>
<body>
	<div id="top" class="of-h top"><%@ include file="/common/admin/top.jsp"%></div>
	<div id="left" class="left"><%@ include file="/common/admin/left.jsp"%></div>
	<div id="center" class="of-h center"  style="overflow:auto;">
		<div class="top_div" style="margin-top: 20px;">
			<div class="select_div" style="text-align: center;">
				<input type="button" value="上传图片" class="button button-primary button-rounded button-small"  id="uploadImage"/>
				<input type="button" value="删除" class="button button-primary button-rounded button-small proImgBttn_top" id="deleteImage"/>
			</div>
		</div>
		
		<form id="inputList">
			<input name="id"  id="bannerId" value="" type="hidden">
		</form>
	    <div style="width:100%; margin-top: 10px;" id="imageContent">
		</div>
    </div>
   	<div id="bottom" class="of-h bottom"><%@ include file="/common/admin/bottom.jsp"%></div>
	<%@ include file="/common/js.jsp"%>
	
	<script type="text/javascript">
		$(function(){
			$("#image_index").css("background-color","#ffffff");
			$("#content12").show();
			//上传图片
			$("#uploadImage").click(function(){
				var id = getSelectedIds("datatable");
				layer.open({
				  type: 2,
				  title:"图片上传",
				  area: ['700px', '530px'],
				  fix: false, //不固定
				  maxmin: true,
				  content: '${ctx}/admin/base/banner/toUploadImage'
				});
			})
			
			//请求后台数据
			var url = "${ctx}/admin/base/banner/find";
			$.get(url,function(result){
				if(result != undefined){
					for(var i=0;i<result.length;i++){
						var str = "<div class='img_body'><img src='"+result[i].imageUrl+"'/>";
					if(result[i].type == 1){
							str += "<span><button class='bannerBtn_carouse' onclick='banner(this)' value='"+result[i].id+"-"+result[i].type+"-"+i+"' id='banner_"+i+"'>设为轮播图</button><input type='checkbox' value='"+result[i].id+"' name='delImgChebox' id='delImgChebox'></span></div>"
						} else{
							str += "<span><button class='bannerBtn_carouse' onclick='banner(this)' value='"+result[i].id+"-"+result[i].type+"-"+i+"' id='banner_"+i+"'>设为展示图</button><input type='checkbox' value='"+result[i].id+"' name='delImgChebox' id='delImgChebox'></span></div>"
						} 
						$("#imageContent").append(str);
					}
				}
			}); 
		});
		
		//设置为轮播图
		 function banner(obj){
			var strs = obj.value.split("-");
			var id = strs[0];
			var type = strs[1];
			var index = strs[2];
			var str =''; 
			if(type == '1'){
				str = "商城轮播图";
			}else{
				str = "首页轮播图";
			}
			$("#bannerId").val(id);
			$("#inputList").attr("action","${ctx}/admin/base/banner/setBanner");
			$("#inputList").submit();
		} 
		
		$("#deleteImage").click(function(){
			var delVal="";
			var arr=document.getElementsByName("delImgChebox");
			for(i=0;i<arr.length;i++){
				if(arr[i].checked){
					delVal += arr[i].value+",";
				}
			}
			if(delVal==""){
				alert("请选择图片");
				return;
			}else{
				delVal = delVal.substring(0,delVal.length-1);
				$("#bannerId").val(delVal);
			}
			$("#inputList").attr("action","${ctx}/admin/base/banner/delete");
			$("#inputList").submit();
		});
	</script>
</body>
</html>