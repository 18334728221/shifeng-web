<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>账户管理</title>
	<%@include file="/common/css.jsp"%>
</head>

<body>

	<div id="top" class="of-h top"><%@ include file="/common/admin/top.jsp"%></div>
	
	<div id="center" class="of-h center">
		<div id="left" class="left"><%@ include file="/common/admin/left.jsp"%></div>
		<div id="right" class="right">
			<div style="overflow:hidden;">
				<div style="float:left;padding:5px 0;">
					<a id="a_remove" href="javascript:deleteData()" class="button button-primary button-rounded button-small">删除</a>
				</div>
				<div style="float:right;padding:5px 0;">
					<form action="" id="f_search" style="display:inline-block;">
						<input name="customerNo" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="用户编号" />
						<!-- <input name="income" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="收入" />
						<input name="expenses" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="支持" /> -->
					</form>
					<a id="a_search" href="javascript:refreshData();" class="button button-primary button-rounded button-small">搜索</a>
				</div> 
			</div>
			<table id="datatable" class="table table-striped table-bordered table-hover datatable" cellspacing="0" width="100%">
		        <thead>
		            <tr>
						<th>用户编号</th>
						<th>来源</th>
						<th>收入</th>
						<th>支出</th>
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
					<p>用户编号:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="customerNo" param-index="1"  />
				</li>
				<li>
					<p>结束时间:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="sourceType" param-index="2" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
				</li>
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	<%@ include file="/common/js.jsp"%>
	<script type="text/javascript">
	var customerNo = $('input[name="customerNo"]').val();
		homePath = "${ctx}";
		findUrl = homePath + "/admin/base/logAccount/find";
		saveUrl = homePath + "/admin/base/logAccount/save";
		deleteUrl = homePath + "/admin/base/logAccount/delete";
		entityName = "日志管理";
		columns = [
			{ data: "customerNo" },
			{ data: "sourceType" },
			{ data: "income" },
			{ data: "expenses" },
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
			$("#logAccount_index").css("background-color","#ffffff");
			$("#content7").show();
		});
	</script>
</body>
</html>