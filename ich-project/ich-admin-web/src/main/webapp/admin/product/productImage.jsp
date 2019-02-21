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
.proImg_btnShow{border-radius: 5px;background: #1FC01F !important;}
.proImgBttn_top{margin-left: 10px;}
.proImg_title{display: block;}
.dialog{position: fixed;left:34.375vw;top:30px;z-index: 3;width:31.25vw;}
.dialog_head{height:40px;background:#1fc01f;padding-top: 10px;}
.dialog_head i{font-size:15px;color: #fff;padding-left:10px;}
.dialog_head span{float:right;margin: 0px 10px 0 0;font-size: 15px;cursor: pointer;}
.dialog_head span:hover{color:#fff}
.dialog_content{height:100px;background: #fff;line-height: 100px;padding-left:10px;}
.dialog_sure{background: #fff;height: 45px;border-top: 1px solid #e5e5e5;}
.dialog_sure button{float: right; background-color: #1FC01F;color: #fff;margin: 7px 12px;border:0;border-radius: 4px}
.dialog_sure button:hover{background: #00ff00}
.maskBoxs {position: fixed;top: 0;right: 0; bottom: 0;left: 0;z-index:2;background-color: #000;opacity:.5}
</style>
<body>
	<div class="dialog submenu">
		<div class="dialog_head">
			<i>删除图片</i>
			<span onclick="cancelDel();">×</span>
		</div>
		<div class="dialog_content">
			确定删除这<span class="delNum">1</span>条数据吗
		</div>
		<div class="dialog_sure">
			<button class="button-small" onclick="sure();">删除</<button>
		</div>
	</div>
	
	<div class="maskBoxs submenu"></div>
	<div id="top" class="of-h top"><%@ include file="/common/admin/top.jsp"%></div>
	<div id="left" class="left"><%@ include file="/common/admin/left.jsp"%></div>
	<div id="center" class="of-h center" style="overflow:auto;">
		<div class="top_div" style="margin-top: 20px;">
		
			<div class="select_div" style="text-align: center;">
				<span>产品种类:
					<select id="selelctCategory"  name="categoryId">
						<option value="0" >请选择</option>
					</select>
				</span>
				<span style="margin-left: 40px;">产品:
					<select id="selelctProduct" name="productCode">
						<option value="">请选择</option>
					</select>
				</span>
				<input type="button" value="查看" class="button button-primary button-rounded button-small proImgBttn_top"  id="findImage"/>
				<input type="button" value="上传图片" class="button button-primary button-rounded button-small proImgBttn_top"  id="uploadImage"/>
				<input type="button" value="删除" class="button button-primary button-rounded button-small proImgBttn_top"     id="deleteImage"/>
			</div>
		</div>
	
		<form  id="inputList">
			<input name="id" value="" type="hidden">
			<input type="hidden" name="currPage" id="currPageVal" value="${currPage}">
			<input type="hidden" name="productCode" id="productCode" value="${productCode}">
			<input type="hidden" name="delVal" id="delVal" value="">
		</form>
	    <div style="width:100%; margin-top: 10px;overflow:hidden" id="imageContent">
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
				<div class="perform-pos clearfix">
					<p class="nextPage" id="lastPage">上一页</p>
					<!-- <i class="active" id="currPage">&nbsp;1&nbsp;</i>
					<i class="active" id="currPage">&nbsp;1&nbsp;</i> -->
					
					<p class="nextPage1" id="nextPage">下一页
						<!-- style="margin-left: 4px;"<span class="perform-next" style="background: url(../images/sale/52_03.png) no-repeat;"></span> -->
					</p>
				</div>
		</div>
    </div>
    
    <div>
    	
    </div>
    
   	<div id="bottom" class="of-h bottom"><%@ include file="/common/admin/bottom.jsp"%></div>
	</div>
	<%@ include file="/common/js.jsp"%>
	<script type="text/javascript">
	//分页信息
	var pageSize=12;
	var pages=0;
	var currPage=1;
	var dataTotal=0;
		$(function(){
			var productCode = $("#productCode").val();
			var curr = $("#currPageVal").val();
			if(curr!=""){
				currPage = curr;
			}
			
			$(".nextPage").css("cursor","not-allowed");
			$("#image_index").css("background-color","#ffffff");
			$("#content4").show();
			
			//选择产品种类
			var coulumnId = ""
			if(productCode){
				$.get("${ctx}/admin/base/category/findColumnByCode?productCode="+productCode,function(data){
					coulumnId = data;
					selectProduct()
				})
			}
			//产品种类
			$.get("${ctx}/admin/base/category/findCategory",function(data){
				for(var i = 0 ;i<data.length;i++ ){
					if(data[i].value==coulumnId){
						$("select[name=categoryId]").append("<option value='"+data[i].value+"' selected='selected'>"+data[i].text+"</option>");
					}else{
						$("select[name=categoryId]").append("<option value='"+data[i].value+"'>"+data[i].text+"</option>");
					}
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
							if(productCode==result[i].value){
								$("select[name=productCode]").append("<option value='"+result[i].value+"' selected='selected'>"+result[i].text+"</option>");
							}else{
								$("select[name=productCode]").append("<option value='"+result[i].value+"' >"+result[i].text+"</option>");
							}
						});
					}
				});
			})	
			
			//option下拉框选中产品
			function selectProduct(){
				$("select[name=productCode]").empty();
				var url = "${ctx}/admin/product/product/findProduct";
				$.getJSON(url ,{categoryId:coulumnId},function(result) {
					if(result.length>0){
						$(result).each(function(i){
							if(productCode==result[i].value){
								$("select[name=productCode]").append("<option value='"+result[i].value+"' selected='selected'>"+result[i].text+"</option>");
							}else{
								$("select[name=productCode]").append("<option value='"+result[i].value+"' >"+result[i].text+"</option>");
							}
						});
					}
				});
			}
			
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
						if(pages==1){
							$(".nextPage1").css("cursor","not-allowed"); 
						}
						for(var i=0;i<result.length;i++){
							var str = "<div class='img_body'><img src='"+result[i].imageUrl+"'/><h1><span>"+result[i].name+"</span><input type='checkbox' value='"+result[i].id+"' name='delImgChebox' id='delImgChebox'></h1>";
							if(result[i].isMain == 1){
								str += "<span><button onclick='setShow(this)' value='"+result[i].id+"-"+result[i].productCode+"' class='proImg_btnShow'>设为展示图 </button>&nbsp;<button onclick='noMain(this)' value='"+result[i].id+"'>取消主图</button>&nbsp;</span></div>"
							}else{
								str += "<span><button onclick='isMain(this)' value='"+result[i].id+"-"+result[i].productCode+"'>设为主图</button>&nbsp;<button onclick='deleteImg(this)' value='"+result[i].id+"'>删除</button></span></div>"
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
				}else{
					alert("请选择产品");
					return;
				}
			})
			
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
					
					if(curr>=pages){
						$(".nextPage1").css("cursor","not-allowed");
					}
					if(pages==1){
						$(".nextPage").css("cursor","not-allowed");
						$(".nextPage1").css("cursor","not-allowed");
					}
					
					var pageText = ""
					
					for(var i=0;i<pages;i++){
						if((i+1)==curr){
							pageText+='<i class="active" style="color:red" id="currPage" onclick="currentPage('+(i+1)+')">&nbsp;'+(i+1)+'&nbsp;</i>';
						}else{
							pageText+='<i class="active" id="currPage" onclick="currentPage('+(i+1)+')">&nbsp;'+(i+1)+'&nbsp;</i>';
						}
					}
						$("#lastPage").after(pageText);
					
					$("#pages").html(pages);
					$("#pageSize").html(pageSize); 
					
					for(var i=0;i<result.length;i++){
						var str = "<div class='img_body'><img src='"+result[i].imageUrl+"' class='product_img' /><h1><span class=''>"+result[i].name+"</span><input type='checkbox' value='"+result[i].id+"' name='delImgChebox' id='delImgChebox'></h1>";
						if(result[i].isMain == 1){
							str += "<span class='proImg_list'><button onclick='setShow(this)' value='"+result[i].id+"-"+result[i].productCode+"' class='proImg_btnShow'>设为展示图 </button>&nbsp;<button onclick='noMain(this)' value='"+result[i].id+"' >取消主图</button></span></div>"
						}else{
							str += "<span class='proImg_list'><button onclick='isMain(this)' value='"+result[i].id+"-"+result[i].productCode+"' >设为主图</button></span></div>"
						}
						$("#imageContent").append(str);
					}
				}
			}); 
		});
		
		
		//点击上一页
		$("#lastPage").click(function(){
			if(currPage > 1) {
				currPage--;
				$("#currPageVal").val(currPage)
				$(".active").eq(currPage-1).css("color","red").siblings().css("color","black");
				/* $("#currPage").html("&nbsp;"+currPage+"&nbsp;"); */
				$(".nextPage1").css("cursor","pointer");
				findProductImg();
			}else {
				return;
			}
			  if(currPage==1){
				 $(".nextPage").css("cursor","not-allowed"); 
			 } 
			
		});
		
		//点击下一页
		$("#nextPage").click(function(){
			if (currPage<pages) {
				currPage++;
				$("#currPageVal").val(currPage)
				/* $("#currPage").html("&nbsp;"+currPage+"&nbsp;"); */
				$(".active").eq(currPage-1).css("color","red").siblings().css("color","black");
				$(".nextPage").css("cursor","pointer");
				findProductImg();
			}else{
 				return;
			}
			 if(currPage>=pages){
				$(".nextPage1:hover").css("cursor","not-allowed");
				$(".nextPage").css("cursor","pointer");
			}else{
				$(".nextPage1:hover").css("cursor","pointer");
			} 
		});
		//点击当前页
		function currentPage(cur){
			currPage=cur;
			$("#currPageVal").val(currPage)
			$(".active").eq(cur-1).css("color","red").siblings().css("color","black");
			if(currPage>=pages){
				$(".nextPage1").css("cursor","not-allowed");
				$(".nextPage").css("cursor","pointer");
			}else{
				$(".nextPage1:hover").css("cursor","pointer");
			}
			if(currPage==1){
				$(".nextPage").css("cursor","not-allowed");
				$(".nextPage1").css("cursor","pointer");
			}
			findProductImg();
		}
		function findProductImg(){
			var url = "${ctx}/admin/product/image/find";
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
					$("#imageContent").html("");
					$("#pages").html(pages);//总页数
					$("#pageSize").html(pageSize); //每页多少条
					$("#currPage").html(); //第*页
					
					for(var i=0;i<result.length;i++){
						var str = "<div class='img_body'><img src='"+result[i].imageUrl+"'/><h1><span class=''>"+result[i].name+"</span><input type='checkbox' value='"+result[i].id+"' name='delImgChebox' id='delImgChebox'></h1>";
						if(result[i].isMain == 1){
							str += "<span><button onclick='setShow(this)' value='"+result[i].id+"-"+result[i].productCode+"' class='proImg_btnShow'>设为展示图 </button>&nbsp;<button onclick='noMain(this)' value='"+result[i].id+"'>取消主图</button></span></div>"
						}else{
							str += "<span><button onclick='isMain(this)' value='"+result[i].id+"-"+result[i].productCode+"'>设为主图</button></span></div>"
						}
						$("#imageContent").append(str);
					}
				}
			});
		}
		//设为主图
		function isMain(obj){
			var strs = obj.value.split("-");
			var id = strs[0];
			var productCode = strs[1];
			$("#inputList").attr("action","${ctx}/admin/product/image/isMain/"+productCode+"/"+id)
			$("#inputList").submit();
		}
		//取消主图
		function noMain(obj){
			var id = obj.value;
			$("#inputList").attr("action","${ctx}/admin/product/image/noMain/"+id);
			$("#inputList").submit();
		}
		$("#deleteImage").click(function(){
			var delVal="";
			var delNum = 0;
			var arr=document.getElementsByName("delImgChebox");
			for(i=0;i<arr.length;i++){
				if(arr[i].checked){
					delVal += arr[i].value+",";
					delNum++;
				}
			}
			$(".delNum").html(delNum);
			if(delVal==""){
				alert("请选择图片");
				return;
			}else{
				$(".maskBoxs").removeClass("submenu");
				$(".dialog").removeClass("submenu");
				delVal.substring(0,delVal.length-1);
				$("#delVal").val(delVal);
			}
		});
		function sure(){
			$("#inputList").attr("action","${ctx}/admin/product/image/delete");
			$("#inputList").submit();
		}
		function cancelDel(){
			$(".maskBoxs").addClass("submenu");
			$(".dialog").addClass("submenu");
		}
		function setShow(obj){
			var strs = obj.value.split("-");
			var id = strs[0];
			var productCode = strs[1];
			$("#productCode").val(productCode);
			$("#inputList").attr("action","${ctx}/admin/product/image/setShow/"+id)
			$("#inputList").submit();
		}
	</script>
</body>
</html>