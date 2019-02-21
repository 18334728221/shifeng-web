<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>产品图片管理</title>
	<%@include file="/common/css.jsp"%>
</head>
<style>
	.img_body{width: 16%;height: 100%;margin-left: 4%;margin-right: 2%;float: left;}
	.img_body img{width: 254px;height: 300px;}
	.select_div select{width: 16%;height: 34px;text-align: center;}
</style>
<body>
	<div id="top" class="of-h top"><%@ include file="/common/admin/top.jsp"%></div>
	<div id="left" class="left"><%@ include file="/common/admin/left.jsp"%></div>
	<div id="center" class="of-h center">
		<div class="top_div" style="margin-top: 20px;">
			<div class="select_div" style="text-align: center;">
				<span>产品种类:
					<select id="selelctCategory"  name="categoryId"><!-- onchange="selectCa(this)" -->
						<option value="0">请选择</option>
					</select>
				</span>
				<span style="margin-left: 40px;">产品:
					<select id="selelctProduct" name="productCode">
						<option>请选择</option>
					</select>
				</span>
				<input type="button" value="查看" class="button button-primary button-rounded button-small" style="margin-left: 40px;" id="findImage"/>
				<input type="button" value="上传图片" class="button button-primary button-rounded button-small" style="margin-left: 40px;" id="uploadImage"/>
			</div>
		</div>
		
		
	    <div style="width:100%; margin-top: 10px;" id="imageContent">
			<form id="inputList">
				<input name="id" value="" type="hidden">
			</form>
		</div>
	    <div class="history">
		    <div class="hist-con">
				<span>共找到</span>
				<strong id="dataTotal">0</strong> 
				<span>条记录,每页</span>
				<strong id="pageSize">0</strong> 
				<span>条,共</span> 
				<strong id="pages">0</strong>
				<span>页</span>
			</div>
			<div class="perform-pos clearfix" style="bottom:12px;">
				<p><span class="perform-prev"></span> <span class="perform-prev1" id="lastPage">上一页</span></p>
				<i class="active" id="currPage">1</i>
				<p style="margin-left: 4px;"><span class="perform-prev1" id="nextPage">下一页</span> 
					<span class="perform-next" style="background: url(../images/sale/52_03.png) no-repeat;"></span>
				</p>
			</div>
    	</div>
    </div>
    
    <div id="bottom" class="of-h bottom"><%@ include file="/common/admin/bottom.jsp"%></div>
	
	<%@ include file="/common/js.jsp"%>
	<script type="text/javascript">
		
		$(function(){
			$("#skuImage_index").css("background-color","#ffffff");
			$("#content4").show();
			var pageSize=10;
			var pages=0;
			var currPage=1;
			var dataTotal=0;
			
			//产品种类
			$.get("${ctx}/admin/base/category/findCategory",function(data){
				for(var i = 0 ;i<data.length;i++ ){
					$("select[name=categoryId]").append("<option value='"+data[i].value+"'>"+data[i].text+"</option>");
				}
			})
			
			//产品
			$("#selelctCategory").change(function(){
				$("select[name=productCode]").empty();
				var url = "${ctx}/admin/product/product/findProduct";
				var categoryId = $(this).val();
				$.getJSON(url ,{categoryId:categoryId},function(result) {
					if(result.length>0){
						$(result).each(function(i){
							$("select[name=productCode]").append("<option value='"+result[i].value+"'>"+result[i].text+"</option>");
						});
					}
				});
			})	
			
			//查看
			$("#findImage").click(function(){
				$("#imageContent").empty();
				var productCode = $("select[name=productCode]").val();
				//请求后台数据
				var url = "${ctx}/admin/product/image/find";
				$.get(url,{"start":(currPage-1)*pageSize,"limit":pageSize,"productCode":productCode},function(data){
					if(data != undefined){
						var result = data.aaData;
						dataTotal=data.iTotalRecords;
						 $("#dataTotal").html(dataTotal);
						if(dataTotal%pageSize==0){
							pages=parseInt(dataTotal/pageSize);
						}else{
							pages=parseInt(dataTotal/pageSize)+1;
						}
						$("#pages").html(pages);
						$("#pageSize").html(pageSize); 
						
						for(var i=0;i<result.length;i++){
							var str = "<div class='img_body'><img src='"+result[i].imageUrl+"'/><span>"+result[i].name+"</span>";
							if(result[i].isMain == 1){
								str += "<span><button onclick='noMain(this)' value='"+result[i].id+"'>取消主图</button><button onclick='deleteImg(this)' value='"+result[i].id+"'>删除</button></span></div>"
							}else{
								str += "<span><button onclick='isMain(this)' value='"+result[i].id+"-"+result[i].productCode+"'>设为主图</button><button onclick='deleteImg(this)' value='"+result[i].id+"'>删除</button></span></div>"
							}
							$("#imageContent").append(str);
						}
					}
				}); 
			})
			
			//上传图片
			$("#uploadImage").click(function(){
				var productCode = $("#selelctProduct").val();
				
				if(productCode != ''){
					var id = getSelectedIds("datatable");
					layer.open({
					  type: 2,
					  title:"图片上传",
					  area: ['700px', '530px'],
					  fix: false, //不固定
					  maxmin: true,
					  content: '${ctx}/admin/product/image/toUploadImage?productCode='+productCode
					});
				};
			})
			//请求后台数据
			var url = "${ctx}/admin/product/skuImage/find";
			$.get(url,{"start":(currPage-1)*pageSize,"limit":pageSize},function(data){
				if(data != undefined){
					var result = data.aaData;
					dataTotal=data.iTotalRecords;
					 $("#dataTotal").html(dataTotal);
					if(dataTotal%pageSize==0){
						pages=parseInt(dataTotal/pageSize);
					}else{
						pages=parseInt(dataTotal/pageSize)+1;
					}
					$("#pages").html(pages);
					$("#pageSize").html(pageSize); 
					
					for(var i=0;i<result.length;i++){
						var str = "<div class='img_body'><img src='"+result[i].imageUrl+"'/><span>"+result[i].productName+"</span>";
						
						str += "<span><button onclick='deleteImg(this)' value='"+result[i].id+"'>删除</button></span></div>"
						
						$("#imageContent").append(str);
					}
				}
			}); 
		});
		
		
		//点击上一页
		$("#lastPage").click(function(){
			if(currPage > 1) {
				currPage=currPage-1;
			}else {
				currPage=1;
			}
			$("#currPage").html(currPage);
			$.get(url,{"start":(currPage-1)*pageSize,"limit":pageSize},function(data){
				$("#content").html("");
				showData(data);
			});
		});
		//点击当前页
		$("#currPage").click(function(){
			$.get(url,{"start":(currPage-1)*pageSize,"limit":pageSize},function(data){
				$("#content").html("");
				showData(data);
			});
		});
		//点击下一页
		$("#nextPage").click(function(){
			if (pages > 0) {
    			if(currPage < pages) {
    				currPage=currPage+1;
    			}else {
    				currPage=pages;
    			}
			}else {
				currPage = 1;
			}
			$("#currPage").html(currPage);
			$.get(url,{"start":(currPage-1)*pageSize,"limit":pageSize},function(data){
				$("#content").html("");
				showData(data);
			});
		});
		
		
		//设为主图
		function isMain(obj){
			var id = obj.value;
			$("#inputList").attr("action","${ctx}/admin/product/image/isMain/"+id)
			$("#inputList").submit();
		}
		//取消主图
		function noMain(obj){
			var id = obj.value;
			$("#inputList").attr("action","${ctx}/admin/product/image/noMain/"+id)
			$("#inputList").submit();
		}
		//删除图片
		function deleteImg(obj){
			var id = obj.value;
			$("#inputList").attr("action","${ctx}/admin/product/image/delete/"+id)
			$("#inputList").submit();
		}
		
		
	</script>
</body>
</html>