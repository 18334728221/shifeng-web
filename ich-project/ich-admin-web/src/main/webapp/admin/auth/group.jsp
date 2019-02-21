<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>组管理</title>
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
					<a id="a_findRole" href="javascript:bClick(saveRole,'分配角色')" class="button button-primary button-rounded button-small">分配角色</a>
					<a id="a_findRole" href="javascript:bClick(savePermission,'分配功能')" class="button button-primary button-rounded button-small">分配功能</a>
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
						<th>名称</th>
						<th>描述</th>
					</tr>
		        </thead>
		    </table>
	    </div>
    </div>
    
    <div id="bottom" class="of-h bottom"><%@ include file="/common/admin/bottom.jsp"%></div>

	<div id="edit_role" style="display: none;">
		<form action="" class="e-form" method="post">
			<input name="groupId" type="hidden"/>
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
			<input name="groupId" type="hidden"/>
			<input name="permissionIds" type="hidden"/>
			<ul id="p_tree" class="ztree"></ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	<div id="edit_box" style="display:none;">
		<form action="" class="e-form" method="post">
			<ul class="e-table" border="0">
				<li>
					<input type="hidden" name="id" id="ipt_id" param-index="0" />
					<p>名称:</p>
					<input type="text" class="form-control input-sm" tip="组名称" name="name" id="ipt_module_id" param-index="1" />
				</li>
				<li>
					<p>描述:</p>
					<input type="text" class="form-control input-sm" name="description" id="ipt_description_id" param-index="2" />
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
		findUrl = homePath + "/admin/auth/group/findAll";
		saveUrl = homePath + "/admin/auth/group/save";
		deleteUrl = homePath + "/admin/auth/group/delete";
		entityName = "组";
		columns = [
			{ data: "id" },
			{ data: "name" },
			{ data: "description" },
// 			{render: function ( data, type, row ) {
// 						return "<button>Click!</button>";
// 			}}
		];
		buildUpdateHtml = function(){
			$(".modal-body .e-form input").each(function(i){
				$(this).attr("value",$("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text());
			});
			$(".modal-body .e-form select").each(function(i){
				$(this).buildSelect(homePath+$(this).attr("url"),$("#datatable tbody tr.selected").children("td").eq($(this).attr("param-index")).text(),false);
			});
		}
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
		function saveRole(title){
			var editRoleHtml = $("#edit_role").html().replace("id=\"roleTable\"","id=\"roleTable1\"");
			showOriginalDialog(title,$('<div>'+editRoleHtml+'</div>'),'保存',homePath+"/admin/auth/group/saveGroupRole");
			setTimeout("buildRoleClick()",600);
		}
		function savePermission(title){
			var editPermissionHtml = $("#edit_permission").html().replace("id=\"p_tree\"","id=\"pp_tree\"");
			showOriginalDialog(title,$('<div style="overflow:auto;height:'+($(window).height()-208)+'px">'+editPermissionHtml+'</div>'),'保存',homePath+"/admin/auth/group/saveGroupPermission");
			setTimeout("buildPermissionClick()",600);
		}
		function buildRoleClick(){
			$('#roleTable1 tbody').on( 'click', 'tr', function () {
			    $(this).toggleClass('selected');
			    var roleIds = getSelectedIds("roleTable1");
			    $(".modal-body input[name='roleIds']").val(roleIds);
			});
			var groupId = $("#datatable tbody tr.selected").children("td").eq(0).text();
			$(".modal-body input[name='groupId']").val(groupId);
			$.getJSON(homePath+"/admin/auth/group/findGroupRoleList",{groupId:groupId},function(result) {
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
			var groupId = $("#datatable tbody tr.selected").children("td").eq(0).text();
			$(".modal-body input[name='groupId']").val(groupId);
			$.getJSON(homePath+"/admin/auth/module/findListAllTree",function(result) {
				pmsTree = $.fn.zTree.init($("#pp_tree"), setting, result);
				$.getJSON(homePath+"/admin/auth/group/findGroupPermissionList",{groupId:groupId},function(result) {
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
			$("#group_index").css("background-color","#ffffff");
			$("#content1").show();
			buildDataTable('roleTable',"${ctx}/admin/auth/role/findAll", [{data:"id"},{data:"roleName"},{data:"description"}]);
			//buildDataTable('groupTable',"${ctx}/admin/auth/group/findAll", [{data:"id"},{data:"name"},{data:"description"}]);
		});
		
		
		function initPermission(){
			var url="${ctx}/public/auth/initPermission.do?random="+Math.random();
			var submitData={
				target : 'com:rjsj:system:auth:model:AuthRole',
				operations:'create,update,delete,read,assignpermission'
		    }; 

	        $.ajax({
	            url: url,
	            data: submitData,
	            type: "post",
	            cache: false,
	            dataType: 'json',
	            timeout: 30000,
	            beforeSend: function (XMLHttpRequest) {
	            },
	            success: function (data) {
	                var result = data.result;
	                if(result.indexOf("all") == -1){
	                    var result = data.result;
	                    if(result.indexOf("all") == -1){
	                        if(result.indexOf("create") == -1){
	                            $("#button1").remove();
	                        }
	                        if(result.indexOf("update") == -1){
	                            $("#button2").remove();
	                        }
	                        if(result.indexOf("delete") == -1){
	                            $("#button3").remove();
	                        }
	                        if(result.indexOf("read") == -1){
	                            $("#button4").remove();
	                        }
	                        if(result.indexOf("assignpermission") == -1){
	                            $("#button22").remove();
	                        }
	                    }
	                }
	            },
	            error: function (errorThrown) {
					
	            }
	        });
		}
	</script>
</body>
</html>