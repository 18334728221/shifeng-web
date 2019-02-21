<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>权限模块管理</title>
	<%@ include file="/common/css.jsp"%>
	<link rel="stylesheet" href="${ctx}/plugins/ztree/css/zTreeStyle/zTreeStyle.css" />
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
						<input id="h_id" name="id" type="hidden"/>
						<input id="h_parentId" name="parentId" type="hidden"/>
						<input name="moduleName" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="模块名称" />
					</form>
					<a id="a_search" href="javascript:refreshData();" class="button button-primary button-rounded button-small">搜索</a>
				</div>
			</div>
			<table id="datatable" class="table table-striped table-bordered table-hover datatable" cellspacing="0" width="100%">
		        <thead>
		            <tr>
						<th>ID</th>
						<th>模块名称</th>
						<th>父模块名称</th>
						<th>控制等级</th>
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
					<input type="hidden" name="id" id="ipt_id" param-index="0" />
					<p>模块名称:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="moduleName" id="ipt_module_name_id" param-index="1" />
				</li>
				<li>
					<p>父模块ID:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="parentId" id="ipt_parent_id" param-index="3" />
				</li>
				<li>
					<p>控制等级:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="controlLevel" id="ipt_controlLevel_id" param-index="6" />
				</li>
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	<div id="tree_box" class="tree_box">
		<%-- <div id="tree_title" class="title"></div>--%>
		<div id="tree_content" class="content">
			<ul id="tree" class="ztree"></ul>
		</div>
	</div>
	<%@ include file="/common/js.jsp"%>
	<script type="text/javascript" src="${ctx}/plugins/ztree/js/jquery.ztree.core.min.js"></script>
	<script type="text/javascript">
		homePath = "${ctx}";
		findUrl = homePath + "/admin/auth/module/findAll";
		saveUrl = homePath + "/admin/auth/module/save";
		deleteUrl = homePath + "/admin/auth/module/delete";
		columns = [
			{ data: "id" },
			{ data: "moduleName" },
			{ data: "parentId" },
			{ data: "controlLevel" },
		];
		$(function(){
			var setting = {
				view: {
					selectedMulti: false	//禁止多点选中 
				},
				callback: {  
					onClick: function(event,treeId,treeNode){
						$("#h_id,#h_parentId").removeAttr("value");
						$("#h_"+(treeNode.isParent?"parentId":"id")).val(treeNode.id);
						refreshData();
					}
				}
			};
		    $.ajax({
		        type : 'get',
		        url : homePath+"/admin/auth/module/findListAllModuleTree",
		        dataType : "json",
		        success : function(result) {
		        	$.fn.zTree.init($("#tree"), setting, result);
		        }
		    });
		});
		buildUpdateHtml = function(){
			$(".modal-body .e-form input").each(function(i){
				$(this).attr("value",$("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text());
			});
			$(".modal-body .e-form select").each(function(i){
				$(this).buildSelect(homePath+$(this).attr("url"),$("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text(),false);
			});
		}
		$(function(){
			$("#module_index").css("background-color","#ffffff");
			$("#content1").show();
		});
	</script>
</body>
</html>