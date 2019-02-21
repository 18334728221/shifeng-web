<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>角色管理</title>
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
					<a id="a_remove" href="javascript:bClick(savePermission,'分配权限')" class="button button-primary button-rounded button-small">分配权限</a>
				</div>
				<div style="float:right;padding:5px 0;">
					<form action="" id="f_search" style="display:inline-block;">
						<input name="roleName" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="角色名" />
						<input name="description" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="描述" />
					</form>
					<a id="a_search" href="javascript:refreshData();" class="button button-primary button-rounded button-small">搜索</a>
				</div>
			</div>
			<table id="datatable" class="table table-striped table-bordered table-hover datatable" cellspacing="0" width="100%">
		        <thead>
		            <tr>
						<th>ID</th>
						<th>角色名称</th>
						<th>是否系统内定角色</th>
						<th>角色描述</th>
						<th>是否用户类型</th>
						<th>是否前端访问</th>
						<th>是否后台访问</th>
					</tr>
		        </thead>
		    </table>
	    </div>
    </div>
    
    <div id="bottom" class="of-h bottom"><%@ include file="/common/admin/bottom.jsp"%></div>
	
	<div id="edit_box" style="display:none;">
		<form action="" class="e-form" method="post">
			<ul class="e-table" border="0">
				<input type="hidden" name="id" id="ipt_id" param-index="0" />
				<li>
					<p class="t-r">角色名称:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="roleName" id="ipt_target" param-index="2" />
				</li>
				<li>
					<p class="t-r">是否系统内定角色:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="isInnerRole" id="ipt_operation_id" param-index="3" />
				</li>
				<li>
					<p class="t-r">角色描述:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="description" id="ipt_permissionType_id" param-index="4" />
				</li>
				<li>
					<p class="t-r">是否用户类型:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="isUserType" id="ipt_description_id" param-index="5" />
				</li>
				<li>
					<p class="t-r">是否前端访问:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="isFrontAccesse" id="ipt_controlLevel_id" param-index="6" />
				</li>
				<li>
					<p class="t-r">是否后台访问:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="isBackgroundAccesse" id="ipt_controlLevel_id" param-index="6" />
				</li>
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	<div id="edit_permission" style="display:none;">
		<form action="" class="e-form" method="post">
			<input name="roleId" type="hidden"/>
			<input name="permissionIds" type="hidden"/>
			<ul id="p_tree" class="ztree"></ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	<%@ include file="/common/js.jsp"%>
	<script type="text/javascript" src="${ctx}/plugins/ztree/js/jquery.ztree.core.min.js"></script>
	<script type="text/javascript" src="${ctx}/plugins/ztree/js/jquery.ztree.excheck.min.js"></script>
	<script type="text/javascript">
		homePath = "${ctx}";
		findUrl = homePath + "/admin/auth/role/findAll";
		saveUrl = homePath + "/admin/auth/role/save";
		deleteUrl = homePath + "/admin/auth/role/delete";
		entityName = "角色";
		columns = [
			{ data: "id" },
			{ data: "roleName" },
			{ data: "isInnerRole" },
			{ data: "description" },
			{ data: "isUserType" },
			{ data: "isFrontAccesse" },
			{ data: "isBackgroundAccesse" }
		];
		buildUpdateHtml = function(){
			$(".modal-body .e-form input").each(function(i){
				$(this).attr("value",$("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text());
			});
			$(".modal-body .e-form select").each(function(i){
				$(this).buildSelect(homePath+$(this).attr("url"),$("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text(),false);
			});
		}
		function showEditDialog(title,msg,lbl,url){
			BootstrapDialog.show({
				title:title,
		        message: msg,
		        buttons: [{
		            label: lbl,
		            cssClass: 'button button-primary button-rounded button-small',
		            action: function(dialog){
		            	gDialog = dialog;
		            	$('.modal-body .submitting_div').show();
		            	$('.modal-body .e-form').hide();
		            	$.getJSON(url,$('.modal-body .e-form').serializeArray(),function(result) {
		            		$('.modal-body .submitting_div').hide();
		            		$('.modal-body .serverResponse').html(result.result);
		            		setTimeout('closeDialog()',1000);
		                });
		            }
		        }]
		    });
		}
		function bClick(method,title){
			if(getSelectedSize("datatable") > 1){
				BootstrapDialog.alert("请选择一行数据！");
				return;
			}
			if(getSelectedSize("datatable") <= 0){
				BootstrapDialog.alert("请选择要修改的数据！");
				return;
			}
			method(title);
		}
		function savePermission(title){
			var editPermissionHtml = $("#edit_permission").html().replace("id=\"p_tree\"","id=\"pp_tree\"");
			showEditDialog(title,$('<div style="overflow:auto;height:'+($(window).height()-208)+'px">'+editPermissionHtml+'</div>'),'保存',homePath+"/admin/auth/role/saveRolePermission");
			setTimeout("buildPermissionClick()",600);
		}
		var pmsTree;
		function buildPermissionClick(){
			var setting = {
				view: {selectedMulti: false},
				check: {enable: true,chkStyle: "checkbox",chkboxType: { "Y": "ps", "N": "ps" }},
				callback: {
					onCheck: function(event,treeId,treeNode){
						var nodes = pmsTree.getCheckedNodes(true);
						var ids = "";
						for (var n = 0; n < nodes.length; n++) {
							if(!nodes[n].isParent) ids += nodes[n].id+","
						}
						$(".modal-body input[name='permissionIds']").val(ids);
					}
				},
			};
			var roleId = $("#datatable tbody tr.selected").children("td").eq(0).text();
			$(".modal-body input[name='roleId']").val(roleId);
			$.getJSON(homePath+"/admin/auth/module/findListAllTree",function(result) {
				pmsTree = $.fn.zTree.init($("#pp_tree"), setting, result);
				$.getJSON(homePath+"/admin/auth/role/findRolePermissionList",{roleId:roleId},function(result) {
		 			var pIds="";
		 			if(result.length>0){
		 				var pId;
		 				var node;
		 				$(result).each(function(i){
		 					pId = $(this)[0].permissionId;
		 					pIds += (pId+",");
		 					node = pmsTree.getNodeByParam("id",pId,null);
		 					pmsTree.checkNode(node, true, true);
		 					pmsTree.updateNode(node);
		 				});
		 				$(".modal-body input[name='permissionIds']").val(pIds);
		 			}
				});
			});
		}
		$(function(){
			$("#role_index").css("background-color","#ffffff");
			$("#content1").show();
		});
	</script>
</body>
</html>