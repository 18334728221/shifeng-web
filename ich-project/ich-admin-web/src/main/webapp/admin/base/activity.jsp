<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>活动管理</title>
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
					<a id="a_upload" href="javascript:uploadImage()" class="button button-primary button-rounded button-small">上传图片</a>
				</div>
			</div>
			
			<div style="float:right;padding:5px 0;">
					<form action="" id="f_search" style="display:inline-block;">
						<input name="startTime" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="活动开始时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>--
						<input name="endTime" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="活动结束时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						<!-- <input name="expenses" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="支持" /> -->
					</form>
					<a id="a_search" href="javascript:refreshData();" class="button button-primary button-rounded button-small">搜索</a>
			</div> 
				
			<table id="datatable" class="table table-striped table-bordered table-hover datatable" cellspacing="0" width="100%">
		        <thead>
		            <tr>
						<th>ID</th>
						<th>产品分类</th>
						<th>产品名称</th>
						<th>产品编号</th>
						<th>活动结束时间</th>
						<th>活动结束时间</th>
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
				
				<input type="hidden" class="form-control input-sm" name="code" id="code" param-index="3" />
				
				<li>
					<p>活动开始时间:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="startTime" param-index="4" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
				</li>
				<li>
					<p>活动结束时间:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="endTime" param-index="5" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
				</li>
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	<%@ include file="/common/js.jsp"%>
	<script type="text/javascript">
		homePath = "${ctx}";
		findUrl = homePath + "/admin/base/activity/find";
		saveUrl = homePath + "/admin/base/activity/save";
		deleteUrl = homePath + "/admin/base/activity/delete";
		entityName = "活动";
		columns = [
			{ data: "id" },
			{ data: "categoryName" },
			{ data: "productName" },
			{ data: "productCode" },
			{ data: "startTimeString" },
			{ data: "endTimeString" },
		];
		var mBFormTarget = ".modal-body .e-form ";
		buildAddHtml = function(){
			$(mBFormTarget+"select[url]").each(function(i){
				$(this).buildSelect(homePath+$(this).attr("url"),'',false);
			});
		}
		var seleCategoryVal = "";
		buildUpdateHtml = function(){
			 $(".modal-body .e-form input").each(function(i){
				$(this).attr("value",$("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text());
			}); 
			 $(".modal-body .e-form select").each(function(i){
				$(this).buildSelect(homePath+$(this).attr("url"),$("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text(),false);
			 });
			getSelectedProName("datatable");
		}
		
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
				  content: '${ctx}/admin/base/activity/toUploadImage?id='+id
				});
			};
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
		
		
		
		$(function(){
			$("#activity_index").css("background-color","#ffffff");
			$("#content11").show();
		});
	</script>
</body>
</html>