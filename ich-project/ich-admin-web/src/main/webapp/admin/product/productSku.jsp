<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>库存管理</title>
	<%@include file="/common/css.jsp"%>
</head>

<body>

	<div id="top" class="of-h top"><%@ include file="/common/admin/top.jsp"%></div>
	
	<div id="center" class="of-h center">
		<div id="left" class="left"><%@ include file="/common/admin/left.jsp"%></div>
		<div id="right" class="right">
			<div style="overflow:hidden;">
				<div style="float:left;padding:5px 0;">
					<!-- <a id="a_add" href="javascript:add()" class="button button-primary button-rounded button-small">新增</a>
					<a id="a_update" href="javascript:update()" class="button button-primary button-rounded button-small">修改</a> -->
					<a id="a_remove" href="javascript:deleteData()" class="button button-primary button-rounded button-small">删除</a>
					<a id="a_upload" href="javascript:uploadImage()" class="button button-primary button-rounded button-small">上传图片</a> 
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
						<th>产品名称</th>
						<th>SKU</th>
						<th>产品属性</th>
						<th>总库存量</th>
						<th>库存</th>
						<th>上架标识</th>
					</tr>
		        </thead>
		    </table>
	    </div>
    </div>
    
    <div id="bottom" class="of-h bottom"><%@ include file="/common/admin/bottom.jsp"%></div>
	
	<div id="edit_box" style="display:none;">
		<form action="" class="e-form" method="post">
			<ul class="e-table" border="0">
				<li>
					<input type="hidden" name="id" param-index="0" />
					<p>产品编码:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="productCode" param-index="1" />
				</li>
				<li>
					<p>属性值:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="sku" param-index="2" />
				</li>
				<li>
					<p>库存:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="stock" param-index="3" />
				</li>
				<li>
					<p>上架标识:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="putaway" param-index="4" />
				</li>
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	<%@ include file="/common/js.jsp"%>
	<script type="text/javascript">
		homePath = "${ctx}";
		findUrl = homePath + "/admin/product/sku/find";
		saveUrl = homePath + "/admin/product/sku/save";
		deleteUrl = homePath + "/admin/product/sku/delete";
		//uploadUrl = homePath + "/admin/base/category/toUploadImage";
		entityName = "库存";
		columns = [
			{ data: "id" },
			{ data: "productName" },
			{ data: "sku" },
			{ data: "avName" },
			{ data: "totalStock" },
			{ data: "stock" },
			{ data: "putaway" }
		];
		var mBFormTarget = ".modal-body .e-form ";
		buildAddHtml = function(){
			$(mBFormTarget+"select[url]").each(function(i){
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
		}
		$(function(){
			$("#sku_index").css("background-color","#ffffff");
			$("#content4").show();
		});
		
		
		//判断是否选中
		function selectTr(){
			if(getSelectedSize("datatable") > 1 || getSelectedSize("datatable") <= 0){
				BootstrapDialog.alert("请选择一行数据！");
				return false;
			}
		}
		
		//上传图片
		function uploadImage(){
			if(selectTr() != false){
				var id = getSelectedIds("datatable");
				layer.open({
				  type: 2,
				  title:"图片上传",
				  area: ['700px', '530px'],
				  fix: false, //不固定
				  maxmin: true,
				  content: '${ctx}/admin/product/sku/toUploadImage?id='+id
				});
			};
			
		}
	</script>
</body>
</html>