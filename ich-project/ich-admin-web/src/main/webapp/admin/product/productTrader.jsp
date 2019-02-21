<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>产品操盘手管理</title>
	<%@include file="/common/css.jsp"%>
</head>

<body>

	<div id="top" class="of-h top"><%@ include file="/common/admin/top.jsp"%></div>
	
	<div id="center" class="of-h center">
		<div id="left" class="left"><%@ include file="/common/admin/left.jsp"%></div>
		<div id="right" class="right">
			<div style="overflow:hidden;">
				<div style="float:left;padding:5px 0;">
					<a id="a_add" href="javascript:add()" class="button button-primary button-rounded button-small">新增</a>
					<a id="a_update" href="javascript:update()" class="button button-primary button-rounded button-small">修改</a>
					<a id="a_remove" href="javascript:deleteData()" class="button button-primary button-rounded button-small">删除</a>
					<a id="a_update" href="javascript:distriBid()" class="button button-primary button-rounded button-small">分配股数</a>
				</div>
				<div style="float:right;padding:5px 0;">
					<form action="" id="f_search" style="display:inline-block;">
						<input name="name" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="名称" />
						<input name="description" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="描述" />
					</form>
					<a id="a_search" href="javascript:refreshData();" class="button button-primary button-rounded button-small">搜索</a>
				</div>
			</div>
			<table id="datatable" class="table table-striped table-bordered table-hover datatable" cellspacing="0" width="100%">
		        <thead>
		            <tr>
						<th>ID</th>
						<th>产品分类</th>
						<th>产品名称</th>
						<th>顾客编码</th>
						<th>持股数</th>
						<th>剩余量</th>
					</tr>
		        </thead>
		    </table>
	    </div>
    </div>
    
    <div id="bottom" class="of-h bottom"><%@ include file="/common/admin/bottom.jsp"%></div>
	
	<div id="edit_box" style="display:none;">
		<form action="" class="e-form" method="post">
			<input type="hidden" name="id" param-index="0" />
			<ul class="e-table" border="0">
				<li>
					<p>产品分类:&nbsp;</p>
					<div class="mdr">
						<select name=categoryName id="categoryName" class="form-control input-sm select2" url="/admin/base/activity/findCategory" param-index="1" onchange="selectCategory(this)">
							<option value="0">请选择</option>
						</select>
					</div>
				</li>
				
				<li>
					<p>产品名称:&nbsp;</p>
					<div class="mdr">
						<select name=productCode  class="form-control input-sm select2" param-index="2" ></select>
					</div>
				</li>
				
				<li>
					<p>顾客编码:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="customerNo" param-index="3" />
				</li>
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	
	
	<div id="bidNum_box" style="display:none;">
		<form action="" class="e-form" method="post">
			<ul class="e-table" border="0">
				<li>
					<input type="hidden" name="id" param-index="0" />
					<input type="hidden" name="productCode" param-index="1" />
					<input type="hidden" name="customerNo" param-index="2" />
					<p>股数:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="totalNum" param-index="3" />
				</li>
				
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	
	
	
	<%@ include file="/common/js.jsp"%>
	<script type="text/javascript">
		homePath = "${ctx}";
		findUrl = homePath + "/admin/product/trader/find";
		saveUrl = homePath + "/admin/product/trader/save";
		bidNumUrl = homePath + "/admin/product/trader/distrBidNum";
		deleteUrl = homePath + "/admin/product/trader/delete";
		
		entityName = "操盘手";
		columns = [
			{ data: "id" },
			{ data: "categoryName" },
			{ data: "productCode" },
			{ data: "customerNo" },
			{ data: "bidNum" },
			{ data: "surplusNum" },
		];
		var mBFormTarget = ".modal-body .e-form ";
		buildAddHtml = function(){
			$(mBFormTarget+"select[url]").each(function(i){
				alert($(this).attr("url"));
				$(this).buildSelect(homePath+$(this).attr("url"),'',false);
			});
		}
		buildUpdateHtml = function(){
			 $(".modal-body .e-form input").each(function(i){
				$(this).attr("value",$("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text());
			});
			$(".modal-body .e-form select").each(function(i){
				$(this).buildSelect(homePath+$(this).attr("url"),$("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text(),false);
			});
			
			 getSelectedProName("datatable");
		}
		
		distriBidHtml = function(){
			$(".modal-body .e-form input").each(function(i){
				$(this).attr("value",$("#datatable tbody tr.selected").children("td").eq(3).text());
			});
		}
		
		function distriBid(){
			if(getSelectedSize("datatable") > 1){
				BootstrapDialog.alert("请选择一行数据！");
				return;
			}
			if(getSelectedSize("datatable") <= 0){
				BootstrapDialog.alert("请选择一行数据！");
				return;
			}
			
			
			var str = '<div><form class="e-form"><input type="hidden" name="ids" value="'+getSelectedIds("datatable")+'" />'+getSelectedSize("datatable")+'？</form>';
			var id = getSelectedIds("datatable");
			showOriginalDialog("分配股数",$('<div>'+$("#bidNum_box").html()+'</div>'),'保存',bidNumUrl);
			setTimeout("buildUpdateHtml()",500);
		}
		
		function getSelectedProName(id){
			$("select[name=productCode]").empty();
			var ids = "";
			$("#"+id+" tr.selected").each(function(i){
				ids += $(this).children().eq(0).text();
				if((i+1)<getSelectedSize(id)) ids += ","
			});
			var url = "/admin/base/activity/findProductName";
			
			$.getJSON(homePath+url ,{activId:ids},function(result) {
				if(result.length>0){
					$(result).each(function(i){
						var productCode = $("#datatable tbody tr.selected").children("td").eq(3).text();
						if(productCode==result[i].value){
							$("select[name=productCode]").append("<option value='"+result[i].value+"' selected='selected'>"+result[i].text+"</option>");
						}else{
							$("select[name=productCode]").append("<option value='"+result[i].value+"'>"+result[i].text+"</option>");
						}
					});
				}
			});
		}
		
		//选择产品种类级联产品编号
		function selectCategory(obj){
			$("select[name=productCode]").empty();
			var url = "/admin/base/activity/findProductCode";
			var categoryId = obj.value;
			$.getJSON(homePath+url ,{categoryId:categoryId},function(result) {
				if(result.length>0){
					$(result).each(function(i){
						$("select[name=productCode]").append("<option value='"+result[i].value+"'>"+result[i].text+"</option>");
					});
				}
			});
		}
		
		$(function(){
			$("#trader_index").css("background-color","#ffffff");
			$("#content4").show();
		});
	</script>
</body>
</html>