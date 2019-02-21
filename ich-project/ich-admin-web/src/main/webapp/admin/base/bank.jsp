<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>银行管理</title>
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
				</div>
			</div>
			
			<div style="float:right;padding:5px 0;">
					<form action="" id="f_search" style="display:inline-block;">
						<input name="CODE" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="卡号" />
						<input name="NAME" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="银行名称"/>
						<!-- <input name="expenses" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="支持" /> -->
					</form>
					<a id="a_search" href="javascript:refreshData();" class="button button-primary button-rounded button-small">搜索</a>
			</div> 
				
			<table id="datatable" class="table table-striped table-bordered table-hover datatable" cellspacing="0" width="100%">
		        <thead>
		            <tr>
						<th>ID</th>
						<th>卡号</th>
						<th>银行名称</th>
						<th>描述</th>
					</tr>
		        </thead>
		    </table>
	    </div>
    </div>
    
    <div id="bottom" class="of-h bottom"><%@ include file="/common/admin/bottom.jsp"%></div>
	
	<div id="edit_box" style="display:none;">
		<form action="" class="e-form" method="post">
			<ul class="e-table" border="0">
				<input type="hidden" name="id" param-index="0" />
				<li>
					<p>卡号:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="code" param-index="1" />
				</li>
				<li>
					<p>名称:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="name" param-index="2"  />
				</li>
				<li>
					<p>描述:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="description" param-index="2"  />
				</li>
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	<%@ include file="/common/js.jsp"%>
	<script type="text/javascript">
		homePath = "${ctx}";
		findUrl = homePath + "/admin/base/bank/find";
		saveUrl = homePath + "/admin/base/bank/save";
		deleteUrl = homePath + "/admin/base/bank/delete";
		entityName = "银行";
		columns = [
			{ data: "id" },
			{ data: "code" },
			{ data: "name" },
			{ data: "description" },
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
		
		$(function(){
			$("#bank_index").css("background-color","#ffffff");
			$("#content3").show();
		});
	</script>
</body>
</html>