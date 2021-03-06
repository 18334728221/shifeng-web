<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>承销管理</title>
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
					<a id="a_update" href="javascript:update()" class="button button-primary button-rounded button-small">承销</a> -->
					<a id="a_remove" href="javascript:deleteData()" class="button button-primary button-rounded button-small">删除</a>
					<!-- <a id="a_upload" href="javascript:upoload()" class="button button-primary button-rounded button-small">上传图片</a> -->
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
						<th>产品代码</th>
						<th>顾客编号</th>
						<th>总数量</th>
						<th>当前价格</th>
						<th>持仓成本</th>
						<th>可卖数量</th>
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
					<input type="hidden" name="id" param-index="0" />
					<p>顾客编号:&nbsp;</p>
					<div class="mdr">
						<select name=customerNo id="customerNo" class="form-control input-sm select2" url="/admin/base/activity/findcustomerNo" param-index="1" onchange="selectCategory(this)">
							<option value="0">请选择</option>
						</select>
					</div>
				</li>
				
				<li>
					<p>顾客编号:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="customerNo" param-index="2" />
				</li>
				<li>
					<p>总数量:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="totalNum" param-index="3" />
				</li>
				<li>
					<p>当前价格:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="price" param-index="4" />
				</li>
				<li>
					<p>持仓数:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="cost" param-index="5" />
				</li>
				<li>
					<p>可卖数量:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="sellNum" param-index="6" />
				</li>
				
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	<%@ include file="/common/js.jsp"%>
	<script type="text/javascript">
		homePath = "${ctx}";
		findUrl = homePath + "/admin/product/customerProduct/find";
		saveUrl = homePath + "/admin/product/customerProduct/save";
		deleteUrl = homePath + "/admin/product/customerProduct/delete";     
		                                                             
		entityName = "承销";                                         
		columns = [                                                  
			{ data: "id" },                                          
			{ data: "productCode" },                                 
			{ data: "customerNo" },                                  
			{ data: "totalNum" },
			{ data: "price" },
			{ data: "cost" },
			{ data: "sellNum" }
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
						var productCode = $("#datatable tbody tr.selected").children("td").eq(2).text();
						if(productCode==result[i].value){
							$("select[name=productCode]").append("<option value='"+result[i].value+"' selected='selected'>"+result[i].text+"</option>");
						}else{
							$("select[name=productCode]").append("<option value='"+result[i].value+"'>"+result[i].text+"</option>");
						}
					});
				}
			});
		}
		
		$(function(){
			$("#customerProduct_index").css("background-color","#ffffff");
			$("#content4").show();
		});
	</script>
</body>
</html>