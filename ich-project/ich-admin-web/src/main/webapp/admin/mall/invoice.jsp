<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>发票管理</title>
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
				<div style="float:right;padding:5px 0;">
					<form action="" id="f_search" style="display:inline-block;">
						<input name="customerNo" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="客户编号" />
						<input name="orderNo" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="订单编号" />
					</form>
					<a id="a_search" href="javascript:refreshData();" class="button button-primary button-rounded button-small">搜索</a>
				</div>
			</div>
			<table id="datatable" class="table table-striped table-bordered table-hover datatable" cellspacing="0" width="100%">
		        <thead>
		            <tr>
						<th>ID</th>
						<th>订单编号</th>
						<th>客户编号</th>
						<th>发票类型</th>
						<th>发票抬头</th>
						<th>发票内容</th>
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
					<p>订单编号:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="orderNo" param-index="1" />
				</li>
				<li>
					<p>客户编号:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="customerNo" param-index="2"/>
				</li>
				 <li>
					<p>发票类型:&nbsp;</p>
					<div class="mdr">
						<select name=type id="type" class="form-control input-sm select2"  param-index="3">
								<option value="-1" >请选择</option>
								<option value="0">不要发票</option>
								<option value="1">普通定额发票</option>
								<option value="2">机打发票</option>
								<option value="3">打印增值税发票</option>
						</select>
					</div>
				</li>
				<li>
					<p>发票抬头:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="title" param-index="4" />
				</li>
				<li>
					<p>发票内容:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="content" param-index="5" />
				</li>
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	<%@ include file="/common/js.jsp"%>
	<script type="text/javascript">
		homePath = "${ctx}";
		findUrl = homePath + "/admin/mall/invoice/find";
		saveUrl = homePath + "/admin/mall/invoice/save";
		deleteUrl = homePath + "/admin/mall/invoice/delete";
		entityName = "发票";
		columns = [
			{ data: "id" },
			{ data: "orderNo" },
			{ data: "customerNo" },
			{ data: "typeString" },
			{ data: "title" },
			{ data: "content" }
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
			var select  = $("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text();
				 $("#type option").each(function(i){
					if(select==$(this).text()){
						$(this).attr("selected",true);
					}
				});
			});
		}
		$(function(){
			$("#content9").show();
		});
	</script>
</body>
</html>