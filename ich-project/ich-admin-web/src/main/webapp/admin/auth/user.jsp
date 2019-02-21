<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>用户管理</title>
	<%@ include file="/common/css.jsp"%>
	<link rel="stylesheet" href="${ctx}/plugins/ztree/css/zTreeStyle/zTreeStyle.css" />
</head>

<body>

	<div id="top" class="of-h top"><%@ include file="/common/admin/top.jsp"%></div>
	
	<div id="center" class="of-h center" >
		<div id="left" class="left"><%@ include file="/common/admin/left.jsp"%></div>
		<div id="right" class="right">
			<div style="overflow:hidden;">
				<div style="float:left;padding:5px 0;">
					<a id="a_add" href="javascript:add()" class="button button-primary button-rounded button-small">新增</a>
					<a id="a_update" href="javascript:update()" class="button button-primary button-rounded button-small">修改</a>
					<!-- <a id="a_remove" href="javascript:deleteData()" class="button button-primary button-rounded button-small">删除</a> -->
					<a id="a_updatepwd" href="javascript:saveUserPwd()" class="button button-primary button-rounded button-small">修改密码</a>
					<a id="a_findUserGroup" href="javascript:bClick(saveGroup,'分配组')" class="button button-primary button-rounded button-small">分配组</a>
					<a id="a_findRole" href="javascript:bClick(saveRole,'分配角色')" class="button button-primary button-rounded button-small">分配角色</a>
					<a id="a_findPermission" href="javascript:bClick(savePermission,'分配功能')" class="button button-primary button-rounded button-small">分配功能</a>
					<a id="a_findRole" href="javascript:bClick(saveUserRelation,'添加用户关系')" class="button button-primary button-rounded button-small">添加用户关系</a>
				</div>
				<div style="float:right;padding:5px 0;">
					<form action="" id="f_search" style="display:inline-block;">
						<input name="name" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="用户名" />
					</form>
					<a id="a_search" href="javascript:refreshData();" class="button button-primary button-rounded button-small">搜索</a>
				</div>
			</div>
			<table id="datatable" class="table table-striped table-bordered table-hover datatable" cellspacing="0" width="100%">
		        <thead>
					<tr>
						<th>ID</th>
						<th>用户名</th>
						<th>手机</th>
						<th>邮箱</th>
						<th>真实姓名</th>
					</tr>
		        </thead>
		    </table>
	    </div>
    </div>
    <div id="bottom" class="of-h bottom"><%@ include file="/common/admin/bottom.jsp"%></div>

	<div id="edit_pwd" style="display: none;">
		<form action="" class="e-form" method="post">
			<ul class="e-table" border="0">
				<input type="hidden" name="id" id="ipt_id" param-index="0" />
				<li>
					<p>当前密码:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="oldpassword" id="ipt_target" />
				</li>
				<li>
					<p>新密码:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="newpassword" id="ipt_operation_id" />
				</li>
				<li>
					<p>确认密码:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="confirmPassword" id="ipt_permissionType_id" />
				</li>
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>

	<div id="edit_group" style="display: none;">
		<form action="" class="e-form" method="post">
			<input name="userId" type="hidden"/>
			<input name="groupIds" type="hidden"/>
			<table id="groupTable" class="table table-striped table-bordered table-hover datatable" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>组名称</th>
						<th>组描述</th>
					</tr>
				</thead>
			</table>
		</form>
		<div class="submitting_div" style="display: none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>

	<div id="edit_role" style="display: none;">
		<form action="" class="e-form" method="post">
			<input name="userId" type="hidden"/>
			<input name="roleIds" type="hidden"/>
			<table id="roleTable" class="roleTable table table-striped table-bordered table-hover datatable" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>角色名称</th>
						<th>角色描述</th>
					</tr>
				</thead>
			</table>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	<div id="edit_permission" style="display: none;">
		<form action="" class="e-form" method="post">
			<input name="userId" type="hidden"/>
			<input name="permissionIds" type="hidden"/>
			<ul id="p_tree" class="ztree"></ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	
	<div id="edit_UserRelation" style="display: none;">
		<form action="" class="e-form" method="post">
			<input id="userId" name="userId" type="hidden"/>
			<input name="parentUserIds" type="hidden"/>
			<table id="userRelationTable" class="userRelationTable table table-striped table-bordered table-hover datatable" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>用户名</th>
						<th>真实姓名</th>
					</tr>
				</thead>
			</table>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	
	<div id="edit_box" style="display:none;">
		<form action="" class="e-form" method="post">
			<ul class="e-table" border="0">
				
				<li>
					<input type="hidden" name="id" id="ipt_id" param-index="0" />
					<p class="t-r">用户名:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="name" id="ipt_target" param-index="1" />
				</li>
				<li>
					<p class="t-r">手机:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="mobile" id="ipt_operation_id" param-index="2" />
				</li>
				<li>
					<p class="t-r">邮箱:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="email" id="ipt_permissionType_id" param-index="3" />
				</li>
				<li>
					<p class="t-r">真实姓名:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="trueName" id="ipt_controlLevel_id" param-index="4" />
				</li>
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	
	<%@ include file="/common/js.jsp"%>
	<script type="text/javascript" src="${ctx}/plugins/ztree/js/jquery.ztree.core.min.js"></script>
	<script type="text/javascript" src="${ctx}/plugins/ztree/js/jquery.ztree.excheck.min.js"></script>
	<script type="text/javascript">
		homePath = "${ctx}";
		findUrl = homePath + "/admin/auth/user/findAll";
		saveUrl = homePath + "/admin/auth/user/save";
		deleteUrl = homePath + "/admin/auth/user/delete";
		savePwdUrl = homePath + "/admin/auth/user/updatePwd";
		entityName = "用户";
		columns = [
			{ data: "id" },
			{ data: "name" },
			{ data: "mobile" },
			{ data: "email" },
			{ data: "trueName" }
		];
		 function buildDataTable(id,url,columns){
			$('#'+id).dataTable({
				dom: '<t>' ,
				lengthChange: false,
				sAjaxSource: url,
				columns: columns,
				bLengthChange: true,
				orderClasses: true,
				iDisplayLength: 100,
				select: true,
				bServerSide:true,
				retrieve:true,
				destroy:true,
				fnServerData : function(sSource, aoData, fnCallback) {
					$.getJSON(sSource,{aoData : JSON.stringify(aoData)},function(result) {
						fnCallback(result);
					});
				}
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
		
		//添加用户关系
		/* function addRelation(method,title){
			
			if(getSelectedSize("datatable") > 1 || getSelectedSize("datatable") <= 0){
				BootstrapDialog.alert("请选择一行数据！");
				return;
			}
			
			var userId = getSelectedIds("datatable");
			var addRelationUrl = homePath + "/admin/auth/user/findExceptByUserId/"+userId;
			buildDataTable('userRelationTable',addRelationUrl, [{data:"id"},{data:"name"},{data:"trueName"}]);
			
			method(title);
		} */
		
		
		function saveGroup(title){
			var editGrupHtml = $("#edit_group").html().replace("id=\"groupTable\"","id=\"groupTable1\"");
			showOriginalDialog(title,$('<div>'+editGrupHtml+'</div>'),'保存',homePath+"/admin/auth/user/saveUserGroup");
			setTimeout("buildGroupClick()",600);
		}
		function saveRole(title){
			var editRoleHtml = $("#edit_role").html().replace("id=\"roleTable\"","id=\"roleTable1\"");
			showOriginalDialog(title,$('<div>'+editRoleHtml+'</div>'),'保存',homePath+"/admin/auth/user/saveUserRole");
			setTimeout("buildRoleClick()",600);
		}
		function savePermission(title){
			var editPermissionHtml = $("#edit_permission").html().replace("id=\"p_tree\"","id=\"pp_tree\"");
			showOriginalDialog(title,$('<div style="overflow:auto;height:'+($(window).height()-208)+'px">'+editPermissionHtml+'</div>'),'保存',homePath+"/admin/auth/user/saveUserPermission");
			setTimeout("buildPermissionClick()",600);
		}
		function saveUserRelation(title){
			var editUserRelationHtml = $("#edit_UserRelation").html().replace("id=\"userRelationTable\"","id=\"userRelationTable1\"");
			showOriginalDialog(title,$('<div>'+editUserRelationHtml+'</div>'),'保存',homePath+"/admin/auth/user/saveUserRelation");
			setTimeout("buildUserRelationClick()",600);
		}
		function buildGroupClick(){
			$('#groupTable1 tbody').on( 'click', 'tr', function () {
			    $(this).toggleClass('selected');
			    var groupIds = getSelectedIds("groupTable1");
			    $(".modal-body input[name='groupIds']").val(groupIds);
			});
			var userId = $("#datatable tbody tr.selected").children("td").eq(0).text();
			$(".modal-body input[name='userId']").val(userId);
			$.getJSON(homePath+"/admin/auth/user/findUserGroupList",{userId:userId},function(result) {
				var groupIds="";
				if(result.length>0){
					var groupId;
					$(result).each(function(i){
						groupId = $(this)[0].groupId;
						groupIds += (groupId+",");
						$("#groupTable1 tbody tr").each(function(i){
							if($(this).children("td").eq(0).text() == groupId){
								$(this).addClass("selected")
							}
						});
					});
					$(".modal-body input[name='groupIds']").val(groupIds);
				}
			});
		}
		function buildRoleClick(){
			$('#roleTable1 tbody').on( 'click', 'tr', function () {
			    $(this).toggleClass('selected');
			    var roleIds = getSelectedIds("roleTable1");
			    $(".modal-body input[name='roleIds']").val(roleIds);
			});
			var userId = $("#datatable tbody tr.selected").children("td").eq(0).text();
			$(".modal-body input[name='userId']").val(userId);
			$.getJSON(homePath+"/admin/auth/user/findUserRoleList",{userId:userId},function(result) {
				var roleIds="";
				if(result.length>0){
					var roleId;
					$(result).each(function(i){
						roleId = $(this)[0].roleId;
						roleIds += (roleId+",");
						$("#roleTable1 tbody tr").each(function(i){
							if($(this).children("td").eq(0).text() == roleId){
								$(this).addClass("selected")
							}
						});
					});
					$(".modal-body input[name='roleIds']").val(roleIds);
				}
			});
		}
		
		function buildUserRelationClick(){
			$('#userRelationTable1 tbody').on( 'click', 'tr', function () {
			    $(this).toggleClass('selected');
			    var parentUserIds = getSelectedIds("userRelationTable1");
			    $(".modal-body input[name='parentUserIds']").val(parentUserIds);
			});
			var userId = $("#datatable tbody tr.selected").children("td").eq(0).text();
			$(".modal-body input[name='userId']").val(userId);
			$.getJSON(homePath+"/admin/auth/user/findUserRelationList",{userId:userId},function(result) {
				var parentUserIds="";
				if(result.length>0){
					var parentUserId;
					$(result).each(function(i){
						parentUserId = $(this)[0].roleId;
						parentUserIds += (parentUserId+",");
						$("#userRelationTable1 tbody tr").each(function(i){
							if($(this).children("td").eq(0).text() == roleId){
								$(this).addClass("selected")
							}
						});
					});
					$(".modal-body input[name='parentUserIds']").val(parentUserIds);
				}
			});
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
			var userId = $("#datatable tbody tr.selected").children("td").eq(0).text();
			$(".modal-body input[name='userId']").val(userId);
			$.getJSON(homePath+"/admin/auth/module/findListAllTree",function(result) {
				pmsTree = $.fn.zTree.init($("#pp_tree"), setting, result);
				$.getJSON(homePath+"/admin/auth/user/findUserPermissionList",{userId:userId},function(result) {
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
		buildUpdateHtml = function(){
			$(".modal-body .e-form input").each(function(i){
				$(this).attr("value",$("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text());
			});
			$(".modal-body .e-form select").each(function(i){
				$(this).buildSelect(homePath+$(this).attr("url"),$("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text(),false);
			});
		}
		$(function(){
			$("#user_index").css("background-color","#ffffff");
			$("#content1").show();
			buildDataTable('roleTable',"${ctx}/admin/auth/role/findAll", [{data:"id"},{data:"roleName"},{data:"description"}]);
			buildDataTable('groupTable',"${ctx}/admin/auth/group/findAll", [{data:"id"},{data:"name"},{data:"description"}]);
			buildDataTable('userRelationTable',"${ctx}/admin/auth/user/findAll", [{data:"id"},{data:"name"},{data:"trueName"}]);
		});
	</script>
</body>
</html>