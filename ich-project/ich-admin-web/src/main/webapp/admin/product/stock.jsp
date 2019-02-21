<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>流通产品管理</title>
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
					<a id="a_remove" href="javascript:update()" class="button button-primary button-rounded button-small">设置</a>
				</div>
				<div style="float:right;padding:5px 0;">
					<form action="" id="f_search" style="display:inline-block;">
						<input name="name" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="名称" />
						<input name="code" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="编号" />
					</form>
					<a id="a_search" href="javascript:refreshData();" class="button button-primary button-rounded button-small">搜索</a>
				</div>
			</div>
			<table id="datatable" class="table table-striped table-bordered table-hover datatable" cellspacing="0" width="100%">
		        <thead>
		            <tr>
						<th>ID</th>
						<th>名称</th>
						<th>编号</th>
						<th>是否新股</th>
						<th>是否停盘</th>
						<th>涨跌幅</th>
						<th>流通量</th>
						<th>收盘价格</th>
						<th>成交总金额</th>
						<th>成交量</th>
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
					<p>是否停盘:&nbsp;</p>
					<div class="mdr">
						<select name="isSuspended" id="isSuspended" class="form-control input-sm select2"  param-index="4">
							<option value="-1">请选择</option>
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</div>
				</li>
				<li>
					<p>涨跌幅:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="priceFluctuation" param-index="5"/>
				</li>
				<li>
					<p>流通量:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="circulatingStock" param-index="6" />
				</li>
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	<%@ include file="/common/js.jsp"%>
	<script type="text/javascript">                                	
		homePath = "${ctx}";                                  
		findUrl = homePath + "/admin/product/stock/find";     
		saveUrl = homePath + "/admin/product/stock/save";     
		deleteUrl = homePath + "/admin/product/stock/delete"; 
		entityName = "股票";                          
		columns = [                                           
			{ data: "id" },                                 
			{ data: "name" },                              
			{ data: "code" },
			{ data: "isNew" },
			{ data: "isSuspended" },
			{ data: "priceFluctuation" },
			{ data: "circulatingStock" },
			{ data: "price" },
			{ data: "totalAmount" },
			{ data: "volume" }
		];
		
		var mBFormTarget = ".modal-body .e-form ";
		buildAddHtml = function(){
			$(mBFormTarget+"select[url]").each(function(i){
				$(this).buildSelect(homePath+$(this).attr("url"),'',false);
			});
		}
		buildUpdateHtml = function(){
			$(".modal-body .e-form input").each(function(i){
				if($(this).attr("param-index")==5){
					$(this).attr("value",$("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text());
				}else{
					$(this).attr("value",$("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text());
				}
			});
			$(".modal-body .e-form select").each(function(i){
				var select  = $("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text();
				 $("#isSuspended option").each(function(i){
					if(select==$(this).text()){
						$(this).attr("selected",true);
					}
				});
				
			});
		}
		
		function getSelectedIsTop(id){
			var ids = "";
			$("#"+id+" tr.selected").each(function(i){
				ids += $(this).children().eq(4).text();
				if((i+1)<getSelectedSize(id)) ids += ","
			});
			return ids;
		}
		
		//判断是否选中
		function selectTr(){
			if(getSelectedSize("datatable") > 1 || getSelectedSize("datatable") <= 0){
				BootstrapDialog.alert("请选择一行数据！");
				return false;
			}
		}
		
		$(function(){
			$("#stock_index").css("background-color","#ffffff");
			$("#content4").show();
		});
	</script>
</body>
</html>