<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>权限功能管理</title>
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
						<input id="h_moduleId" name="moduleId" type="hidden"/>
						<input name="target" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="引用" />
						<input name="operation" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="操作" />
						<input name="description" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="描述" />
					</form>
					<a id="a_search" href="javascript:refreshData();" class="button button-primary button-rounded button-small">搜索</a>
				</div>
			</div>
			<table id="datatable" class="table table-striped table-bordered table-hover datatable" cellspacing="0" width="100%">
		        <thead>
		            <tr>
						<th>ID</th>
						<th>模块名称</th>
						<th>引用</th>
						<th>操作</th>
						<th>权限类型</th>
						<th>描述</th>
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
					<input type="hidden" name="id" param-index="0" />
					<p>模块名称:&nbsp;</p>
					<div class="mdr"><select name="moduleId" class="form-control input-sm select2" url="/admin/auth/permission/findModule" param-index="1"></select></div>
				</li>
				<li>
					<p>引用:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="target" param-index="2" />
				</li>
				<li>
					<p>操作:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="operation" param-index="3" />
				</li>
				<li>
					<p>权限类型:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="permissionType" param-index="4" />
				</li>
				<li>
					<p>描述:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="description" param-index="5" />
				</li>
				<li>
					<p>控制等级:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="controlLevel" param-index="6" />
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
		findUrl = homePath + "/admin/auth/permission/findAll";
		saveUrl = homePath + "/admin/auth/permission/save";
		deleteUrl = homePath + "/admin/auth/permission/delete";
		entityName = "权限功能";
		columns = [
			{ data: "id" },
			{ data: "moduleName" },
			{ data: "target" },
			{ data: "operation" },
			{ data: "permissionType" },
			{ data: "description" },
			{ data: "controlLevel" }
		];
		
		buildAddHtml = function(){
			$(".modal-body .e-form input").attr("value","");
			$(".modal-body .e-form select").each(function(i){
				$(this).children("option").removeAttr("selected");
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
		buildAddHtml = function(){
			$(".modal-body .e-form select").each(function(i){
				$(this).buildSelect(homePath+$(this).attr("url"),$("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text(),false);
			});
		}
		$(function(){
			$("#permission_index").css("background-color","#ffffff");
			$("#content1").show();
			var setting = {
				view: {
					selectedMulti: false	//禁止多点选中 
				},
				callback: {  
					onClick: function(event,treeId,treeNode){
						$("#h_moduleId").val(treeNode.id);
						refreshData();
					}
				}
			};
			
			$.getJSON(homePath+"/admin/auth/module/findListAllModuleTree",{},function(result){
				$.fn.zTree.init($("#tree"), setting, result);
			});
		});
	</script>
</body>
</html>