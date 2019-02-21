<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>手艺人管理</title>
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
					<a id="a_upload" href="javascript:uploadCard()" class="button button-primary button-rounded button-small">上传身份证</a>
					<a id="save_category" href="javascript:bClick(saveCategory,'选择手艺')" class="button button-primary button-rounded button-small">选择手艺</a>
				</div>
				<div style="float:right;padding:5px 0;">
					<form action="" id="f_search" style="display:inline-block;">
						<input name="color" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="颜色" />
						<input name="status" type="text" class="form-control input-sm dis-i-b w-auto" placeholder="状态" />
					</form>
					<a id="a_search" href="javascript:refreshData();" class="button button-primary button-rounded button-small">搜索</a>
				</div>
			</div>
			<table id="datatable" class="table table-striped table-bordered table-hover datatable" cellspacing="0" width="100%">
		        <thead>
		            <tr>
						<th>ID</th>
						<th>姓名</th>
						<th>编号</th>
						<th>商铺</th>
						<th>性别</th>
						<th>地区</th>
						<th>地址</th>
						<th>手机号</th>
						<th>身份证</th>
						<th>邮箱</th>
						<th>微信号</th>
						<th>微信</th>
						<th>QQ</th>
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
					<p>姓名:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="name" param-index="1" />
				</li>
				<li>
					<p>商铺:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="shopName" param-index="3" />
				</li>
				<li>
					<p>性别:&nbsp;</p>
					<!-- <input type="text" class="form-control input-sm" name="sex" param-index="2" /> -->
					<div>
						<select name="sex" id="sex"  param-index="4">
							<option>请选择</option>
							<option value="1">男</option>
							<option value="0">女</option>
						</select>
					</div>
				</li>
				<li>
					<p>地区:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="areaPlatMark" param-index="5" />
				</li>
				<li>
					<p>地址:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="address" param-index="6" />
				</li>
				<li>
					<p>手机号:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="mobile" param-index="7" />
				</li>
				<li>
					<p>身份证号:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="identityCardNo" param-index="8" />
				</li>
				<li>
					<p>邮箱:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="email" param-index="9" />
				</li>
				<li>
					<p>微信号:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="wxId" param-index="10" />
				</li>
				<li>
					<p>微信:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="weixin" param-index="11" />
				</li>
				<li>
					<p>QQ:&nbsp;</p>
					<input type="text" class="form-control input-sm" name="qq" param-index="12" />
				</li>
			</ul>
		</form>
		<div class="submitting_div" style="display:none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	
	
	<div id="edit_category" style="display: none;">
		<form action="" class="e-form" method="post">
			<input name="craftsmanNo" type="hidden"/>
			<input name="categoryIds" type="hidden"/>
			<table id="categoryTable" class="table table-striped table-bordered table-hover datatable" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>产品种类</th>
					</tr>
				</thead>
			</table>
		</form>
		<div class="submitting_div" style="display: none"><img src="${ctx}/images/loader.gif" border="0" /></div>
		<div class="serverResponse"></div>
	</div>
	
	<%@ include file="/common/js.jsp"%>
	<script type="text/javascript">
		homePath = "${ctx}";
		findUrl = homePath + "/admin/seller/craftsman/find";
		saveUrl = homePath + "/admin/seller/craftsman/save";
		deleteUrl = homePath + "/admin/seller/craftsman/delete";
		entityName = "手艺人";
		columns = [
			{ data: "id" },
			{ data: "name" },
			{ data: "craftsmanNo" },
			{ data: "shopName" },
			//性别 0:male 1:female
			{ data: "sexString" },
			//所属地区
			{ data: "areaPlatMark" },
			//地址
			{ data: "address" },
			//手机号
			{ data: "mobile" },
			//身份证号
			{ data: "identityCardNo" },
			//邮箱
			{ data: "email" },
			//微信号
			{ data: "wxId" },
			//微信
			{ data: "weixin" },
			//qq
			{ data: "qq" }
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
			var text = $("#datatable tbody tr.selected").children("td").eq(4).text();
			
			$("#sex option").each(function() {
			    if($(this).text() == text) {
			        $(this).attr("selected",true);
			    }
			})
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
		
		function saveCategory(title){
			var editCategoryHtml = $("#edit_category").html().replace("id=\"categoryTable\"","id=\"categoryTable1\"");
			showOriginalDialog(title,$('<div>'+editCategoryHtml+'</div>'),'保存',homePath+"/admin/seller/craftsman/saveCraftsmanCategory");
			setTimeout("buildCategoryClick()",600);
		}
		
		function buildCategoryClick(){
			$('#categoryTable1 tbody').on( 'click', 'tr', function () {
			    $(this).toggleClass('selected');
			    var categoryIds = getSelectedIds("categoryTable1");
			    $(".modal-body input[name='categoryIds']").val(categoryIds);
			});
			var craftsmanNo = $("#datatable tr.selected").children().eq(2).text();
			$(".modal-body input[name='craftsmanNo']").val(craftsmanNo);
			$.getJSON(homePath+"/admin/seller/craftsmanCategory/findCraftsmanCategoryList",{craftsmanNo:craftsmanNo},function(result) {
				var categoryIds="";
				if(result.length>0){
					var categoryId;
					$(result).each(function(i){
						categoryId = $(this)[0].categoryId;
						categoryIds += (categoryId+",");
						$("#categoryTable1 tbody tr").each(function(i){
							if($(this).children("td").eq(0).text() == categoryId){
								$(this).addClass("selected")
							}
						});
					});
					$(".modal-body input[name='categoryIds']").val(categoryIds);
				}
			});
		}
		
		
		//判断是否选中
		function selectTr(){
			if(getSelectedSize("datatable") > 1 || getSelectedSize("datatable") <= 0){
				BootstrapDialog.alert("请选择一行数据！");
				return false;
			}
		}
		
		//上传图片
		function uploadCard(){
			if(selectTr() != false){
				var id = getSelectedIds("datatable");
				layer.open({
				  type: 2,
				  title:"图片上传",
				  area: ['700px', '530px'],
				  fix: false, //不固定
				  maxmin: true,
				  content: '${ctx}/admin/seller/craftsman/toUploadCard?id='+id
				});
			};
			
		}
		
		
		$(function(){
			$("#craftsman_index").css("background-color","#ffffff");
			$("#content1").show();
			buildDataTable('categoryTable',"${ctx}/admin/base/category/findAll", [{data:"id"},{data:"name"}]);
		});
	</script>
</body>
</html>