<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>产品管理</title>
	<%@include file="/common/css.jsp"%>
</head>

<style>
.caterSelect{
	width:20%!important;
}
.cagorInput{
width:20%!important;
}
</style>


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
					<a id="a_upload" href="javascript:uploadShowImage()" class="button button-primary button-rounded button-small">上传展示图</a>
					<a id="a_upload" href="javascript:uploadImage()" class="button button-primary button-rounded button-small">上传主图</a>
					<a id="a_upload" href="javascript:uploadImageDetail()" class="button button-primary button-rounded button-small">上传详情图</a>
					<a id="a_upload" href="javascript:editImg()" class="button button-primary button-rounded button-small">修改产品图</a>
					<a id="a_upload" href="javascript:addSku()" class="button button-primary button-rounded button-small">入库</a>
					<a id="a_upload" href="javascript:addIssue()" class="button button-primary button-rounded button-small">发行</a>
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
						<th>产品编号</th>
						<th>价格</th>
						<th>总股本</th>
						<th>库存量</th>
						<th>流通总量</th>
						<th>产品分类</th>
						<th>手艺人</th>
						<th>是否置顶</th>
						<th>是否最新</th>
						<th>是否最热</th>
						<th>是否进入交易所交易</th>
						<th>产品描述</th>
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
					<p>产品名称:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="name" param-index="1" />
				</li>
				<li>
					<p>价格:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="price" param-index="3" />
				</li>
				<li>
					<p>总股本:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="totalShareCapital" param-index="4" />
				</li>
				
				<li>
					<p>库存量:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="stock" param-index="5" />
				</li>
				
				<li>
					<p>流通总量:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="circulatingStock" param-index="6" />
				</li>
				
				<li>
					<p>产品分类:&nbsp;</p>
					<div class="mdr caterSelect">
						<select name=categoryId id="categoryId" class="form-control input-sm select2" url="/admin/base/category/findCategory" param-index="7" onchange="selectCategory(this)">
							<option value="0">请选择</option>
						</select>
					</div>
					
					<!-- <div class="cagoryId">
						<p>新建产品分类:&nbsp;</p>
						<input type="text" class="" name="cagoryName" param-index="" />
					</div> -->
				</li>
				
				<li>
					<p>手艺人:&nbsp;</p>
					<div class="mdr">
						<select name=craftsmanNo  class="form-control input-sm select2" param-index="8"></select>
					</div>
				</li>
				<li>
					<p>是否置顶:&nbsp;</p>
					<div class="mdr">
						<select name=categoryId class="form-control input-sm select2" param-index="9">
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</div>
				</li>
				<li>
					<p>是否最热:&nbsp;</p>
					<div class="mdr">
						<select name=isHot class="form-control input-sm select2" param-index="10">
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</div>
				</li>
				<li>
					<p>是否最新:&nbsp;</p>
					<div class="mdr">
						<select name=isNew class="form-control input-sm select2" param-index="11">
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</div>
				</li>
				<li>
					<p>是否进入交易所交易:&nbsp;</p>
					<div class="mdr">
						<select name=isInExchange class="form-control input-sm select2" param-index="12">
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</div>
				</li>
				
				<li>
					<p>描述:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="description" param-index="13" />
				</li>
				
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	<%@ include file="/common/js.jsp"%>
	<script type="text/javascript">
		homePath = "${ctx}";
		findUrl = homePath + "/admin/product/product/find";
		saveUrl = homePath + "/admin/product/product/save";
		deleteUrl = homePath + "/admin/product/product/delete";
		
		entityName = "产品";
		columns = [
			{ data: "id" },
			{ data: "name" },
			{ data: "code" },
			{ data: "price" },
			{ data: "totalShareCapital" },
			{ data: "stock" },
			{ data: "circulatingStock" },
			{ data: "categoryName" },
			{ data: "craftsmanName" },
			{ data: "isTopString" },
			{ data: "isHotString" },
			{ data: "isNewString" },
			{ data: "isInExchangeString" },
			{ data:	"description"}
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
				var productCode = $("#datatable tbody tr.selected").children("td").eq(2).text();
				findCateIdByCode(productCode);
			});
			$(".modal-body .e-form select").each(function(i){
				if($(this).attr("param-index")<8){
					$(this).buildSelect(homePath+$(this).attr("url"),$("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text(),false);
				}
			});
		}
		$(function(){
			$("#product_index").css("background-color","#ffffff");
			$("#content4").show();
		});
		
		//判断是否选中
		function selectTr(){
			if(getSelectedSize("datatable") > 1 || getSelectedSize("datatable") <= 0){
				BootstrapDialog.alert("请选择一行数据！");
				return false;
			}
		}
		
		//上传主图
		function uploadImage(){
			if(selectTr() != false){
				var id = getSelectedIds("datatable");
				layer.open({
				  type: 2,
				  title:"图片上传",
				  area: ['700px', '530px'],
				  fix: false, //不固定
				  maxmin: true,
				  content: '${ctx}/admin/product/product/toUploadImage?id='+id
				});
			};
			
		}
		//上传详情图
		function uploadImageDetail(){
			if(selectTr() != false){
				var id = getSelectedIds("datatable");
				layer.open({
				  type: 2,
				  title:"图片上传",
				  area: ['700px', '530px'],
				  fix: false, //不固定
				  maxmin: true,
				  content: '${ctx}/admin/product/product/uploadImageDetail?id='+id
				});
			};
		}
		
		//上传展示图
		function uploadShowImage(){
			if(selectTr() != false){
				var id = getSelectedIds("datatable");
				layer.open({
				  type: 2,
				  title:"图片上传",
				  area: ['700px', '530px'],
				  fix: false, //不固定
				  maxmin: true,
				  content: '${ctx}/admin/product/product/uploadShowImage?id='+id
				});
			};
		}
		
		//修改产品图片
		function editImg(){
			if(selectTr() != false){
				var code = $("#datatable tbody tr.selected").children("td").eq(2).text();
				var url = "${ctx}/admin/product/image/index?currPage=1&productCode="+code;
				window.location.href=url;
			}
		}
		
		//根据产品编号查找种类Id
		function findCateIdByCode(productCode){
			$.get("${ctx}/admin/base/category/findColumnByCode?productCode="+productCode,function(data){
				var obj = {};
				obj.value = data
				selectCategory(obj);
			})
		}
		
		
		//选择产品种类级联手艺人 
		function selectCategory(obj){
			$("select[name=craftsmanNo]").empty();
			var url = "/admin/seller/craftsmanCategory/findCraftsman";
			var categoryId = obj.value;//$("select[name=categoryId]").val();
			$.getJSON(homePath+url ,{categoryId:categoryId},function(result) {
				if(result.length>0){
					$(result).each(function(i){
						$("select[name=craftsmanNo]").append("<option value='"+result[i].value+"'>"+result[i].text+"</option>");
					});
				}
			});
		}
		
		//产品入库
		function addSku(){
			if(selectTr()!=false){
				var productCode = $("#datatable tr.selected").children().eq(2).text();
				layer.open({
					  type: 2,
					  title:"产品入库",
					  area: ['700px', '530px'],
					  fix: false, //不固定
					  maxmin: true,
					  content: '${ctx}/admin/product/sku/allotSku?productCode='+productCode
				});
			};
			
		}
		
		//发行
		function addIssue(){
			var productCode = $("#datatable tr.selected").children().eq(2).text();
			if(selectTr()!=false){
				layer.open({
					  type: 2,
					  title:"产品发行",
					  area: ['700px', '530px'],
					  fix: false, //不固定
					  maxmin: true,
					  content: '${ctx}/admin/product/issue/allotIssue?productCode='+productCode
				});
			}
		}
	</script>
</body>
</html>