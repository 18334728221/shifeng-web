<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>发行管理</title>
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
					<a id="a_upload" href="javascript:underwrit()" class="button button-primary button-rounded button-small">承销</a>
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
						<th>产品编码</th>
						<th>流通盘</th>
						<th>承销开始时间</th>
						<th>承销结束时间</th>
						<th>申购开始时间</th>
						<th>申购结束时间</th>
						<th>流通时间</th>
						<th>积分会员申购数</th>
						<th>申购参与积分人数</th>
						<th>申购总数</th>
						<th>已申购总数</th>
						<th>申购价格</th>
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
					<p>流通盘:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="circulatingStock" param-index="2" />
				</li>
				<li>
					<p>承销开始时间:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="underwritingStartTime" param-index="3" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"  />
				</li>
				<li>
					<p>承销结束时间:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="underwritingEndTime" param-index="4" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
				</li>
				<li>
					<p>申购开始时间:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="purchaseStartTime" param-index="5" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
				</li>
				<li>
					<p>申购结束时间:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="purchaseEndTime" param-index="6" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
				</li>
				<li>
					<p>流通时间:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="circulateTime" param-index="7" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
				</li>
				<li>
					<p>积分会员申购数:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="integralPurchaseNum" param-index="8" />
				</li>
				<li>
					<p>申购参与积分人数:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="integralNum" param-index="9" />
				</li>
				<li>
					<p>申购总数:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="purchaseTotalAmount" param-index="10" />
				</li>
				<li>
					<p>已申购总数:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="purchaseNum" param-index="11" />
				</li>
				<li>
					<p>申购价格:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="purchasePrice" param-index="12" />
				</li>
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	
	<div id="underwrit_box" style="display:none;">
		<form action="" class="e-form" method="post" id="underwrit_form">
			<ul class="e-table" border="0">
				<li>
					<p>顾客编号:&nbsp;</p>
					<div class="mdr">
						<select name=customerNo id="customerNo" class="form-control input-sm select2" url="/admin/product/productIssue/findcustomerNo" param-index="3" >
							<option value="0">请选择</option>
						</select>
					</div>
				</li>
				<li>
					<p>产品编号:&nbsp;</p>
					<input type="text" class="form-control input-sm" class="productCode" id="productCode" name="productCode"  param-index="1" readonly="readonly"/>
				</li>
				<li>
					<p>开盘数:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="circulatingStock" param-index="2" readonly="readonly"/>
				</li>
				<li>
					<p>申购比例:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="integralRatio" param-index="8" readonly="readonly"/>%
				</li>
				<li>
					<p>申购积分人数:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="integralNum" param-index="9" readonly="readonly"/>
				</li>
				<li>
					<p>申购总数:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="purchaseTotalAmount" param-index="10" readonly="readonly"/>
				</li>
				<li>
					<p>已申购总数:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="purchaseNum" param-index="11" readonly="readonly"/>
				</li>
				<li>
					<p>申购价格:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="purchasePrice" param-index="12" readonly="readonly" />
				</li>
				<li>
					<p>承销价格:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="price"  />
				</li>
				<li>
					<p>承销数量:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="totalNum"  />
				</li>
				
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	
	
	<%@ include file="/common/js.jsp"%>
	<script type="text/javascript">
		homePath = "${ctx}";
		findUrl = homePath + "/admin/product/issue/find";
		saveUrl = homePath + "/admin/product/issue/save";
		deleteUrl = homePath + "/admin/product/issue/delete";
		underwritUrl = homePath + "/admin/product/issue/saveCalculate";
		entityName = "承销";
		columns = [
			{ data: "id" },
			{ data: "productCode" },
			{ data: "circulatingStock" },
			{ data: "underwritingStartTimeString" },
			{ data: "underwritingEndTimeString" },
			{ data: "purchaseStartTimeString" },
			{ data: "purchaseEndTimeString" },
			{ data: "circulateTimeString" },
			{ data: "integralPurchaseNum" },
			{ data: "integralNum" },//9
			{ data: "purchaseTotalAmount" },
			{ data: "purchaseNum" },
			{ data: "purchasePrice" },
			
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
		
		function underwrit(){
			if(getSelectedSize("datatable") > 1){
				BootstrapDialog.alert("请选择一行数据！");
				return;
			}
			if(getSelectedSize("datatable") <= 0){
				BootstrapDialog.alert("请选择一行数据！");
				return;
			}
			var purchaseNum = $("#datatable tbody tr.selected").children("td").eq(9).text();
			var productCode = $("#datatable tbody tr.selected").children("td").eq(1).text();
			var url = "/admin/product/issue/findcustomerNo?purchaseNum="+purchaseNum;
			$("#customerNo").attr("url",url);
			var str = '<div><form class="e-form"><input type="hidden" name="ids" value="'+getSelectedIds("datatable")+'" />'+getSelectedSize("datatable")+'？</form>';
			var id = getSelectedIds("datatable");
			showOriginalDialog(entityName,$('<div>'+$("#underwrit_box").html()+'</div>'),'保存',underwritUrl);
			setTimeout("buildUpdateHtml()",500);
		}
		$(function(){
			$("#issue_index").css("background-color","#ffffff");
			$("#content4").show();
		});
	</script>
</body>
</html>