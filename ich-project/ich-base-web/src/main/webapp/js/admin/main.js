//var topDisplayAll = true;  
//var weekArray = new Array("星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");

var table,columns,gAoData,gFnCallback,gDialog,entityName;
var homePath,findUrl,saveUrl,deleteUrl,uploadUrl;
var buildAddHtml = function(){},buildUpdateHtml = function(){},submitValidate = function(){return true};
$(function(){
	//$("#div_nav2").html((new Date()).toLocaleDateString() + "&nbsp;&nbsp;" + weekArray[(new Date()).getDay()]);
	//$("#div_cs").click(function(){
		//switchTopDisplay();
	//});
//	$.post('${ctx}/public/auth/getUser',
//		function(data){
//			var name = "管理员";
//			if (data !== null && typeof data.trueName !== "undefined")name = data.trueName;
//			$("#user_name_span_id").text("欢迎您："+name);
//		}, "json");
	buildPosition();
	$(window).resize(function(){
		buildPosition();
	});
	table = $('#datatable').dataTable({
		dom: '<t><<"col-sm-5"<"f-d"i>><"col-sm-7"p>>' ,
		lengthChange: false,
		sAjaxSource: findUrl,
        columns: columns,
        bLengthChange: true,
        orderClasses: true,
        bSort: false,
        iDisplayLength: 20,
        oLanguage: {
        	sLengthMenu: "每页显示 _MENU_ 条记录",
        	sZeroRecords: "抱歉， 没有找到",
        	sInfo: "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
        	sInfoEmpty: "没有数据",
        	sInfoFiltered: "(从 _MAX_ 条数据中检索)",
        	oPaginate: {sFirst:"首页",sPrevious:"前一页",sNext:"后一页",sLast:"尾页"},
        	sZeroRecords: "没有检索到数据",
        	sProcessing: "<img src='"+homePath+"/images/loader.gif' />"
        },
        select: true,
        bServerSide:true,
        fnServerData : getData
    });
	$('#datatable tbody').on( 'click', 'tr', function () {
	    $(this).toggleClass('selected');
	});
	var difX=0,difY=0;
	$("#tree_title").mousedown(function(e){
		$("#tree_title").css("cursor","move");
		difX=e.pageX-$("#tree_box").offset().left;
		difY=e.pageY-$("#tree_box").offset().top;
	})
	$("#tree_title").mouseup(function(e){
		$("#tree_title").css("cursor","auto");
		difX=0;
		difY=0;
	});
	$("body").mousemove(function(e){
		if(difX!=0||difY!=0){
			$("#tree_box").css("left",(e.pageX-difX)+"px");
			$("#tree_box").css("top",(e.pageY-difY)+"px");
		}
	});
});
function getData(sSource, aoData, fnCallback) {
	gAoData = aoData;
	gFnCallback=fnCallback;
	var data = {aoData : JSON.stringify(aoData),sParam : JSON.stringify($("#f_search").serializeArray())};
	$.getJSON(sSource,data,function(result){
		fnCallback(result);
	});
}
function refreshData(){
	getData(findUrl, gAoData, gFnCallback);
}
function buildPosition(){
	var wWidth = $(window).width();
	var wHeight = $(window).height();
	$("#center,#left,#right").css("height" ,(wHeight-$("#top").height()-$("#bottom").height())+"px");
	$("#right").css("width" ,(wWidth-180)+"px");
}

function slideUl(id){
	var thisLi = $("#"+id);
	var subleftmenu = thisLi.next("ul");
	var i=$("a[id^='li']").index(thisLi);
	if (subleftmenu.is(':hidden')) {
		subleftmenu.slideDown();
		thisLi.find("i").eq(1).attr('class', 'icon-caret-down now');
	} else {
		subleftmenu.slideUp();
		thisLi.find("i").eq(1).attr('class', 'icon-caret-left now');
	}
}
function changeLeft(url){
	$.post(url,function(data){$("#left").html(data);});
}
//function switchTopDisplay(){
//	if (topDisplayAll){
//		window.parent.parent.frames["frameset_main"].rows = "4,*,0";
//		$("#div_top").hide();
//		$("#div_cs").css("backgroundImage","url(${ctx}/admin/images/main_60_3.gif)");
//		$("#div_cs").parent().css("backgroundColor","#048E01");
//	}else{
//		window.parent.parent.frames["frameset_main"].rows = "100,*,25";
//		$("#div_top").show();
//		$("#div_cs").css("backgroundImage","url(${ctx}/admin/images/main_60_2.gif)");
//		$("#div_cs").parent().css("backgroundColor","transparent");
//	}
//	topDisplayAll = (!topDisplayAll);
//}
function getSelectedSize(id){
	return $("#"+id+" tr.selected").size();
}
function getSelectedIds(id){
	var ids = "";
	$("#"+id+" tr.selected").each(function(i){
		ids += $(this).children().eq(0).text();
		if((i+1)<getSelectedSize(id)) ids += ","
	});
	return ids;
}
function add(){
	showOriginalDialog("新增"+entityName,$('<div>'+$("#edit_box").html()+'</div>'),'保存',saveUrl);
	setTimeout("buildAddHtml()",500);
}
function update(){
	if(getSelectedSize("datatable") > 1){
		BootstrapDialog.alert("请选择一行数据！");
		return;
	}
	if(getSelectedSize("datatable") <= 0){
		BootstrapDialog.alert("请选择要修改的数据！");
		return;
	}
	var str = '<div><form class="e-form"><input type="hidden" name="ids" value="'+getSelectedIds("datatable")+'" />'+getSelectedSize("datatable")+'？</form>';
	var id = getSelectedIds("datatable");
	showOriginalDialog("修改"+entityName,$('<div>'+$("#edit_box").html()+'</div>'),'保存',saveUrl);
	setTimeout("buildUpdateHtml()",500);
}
function upoload(){
	if(getSelectedSize("datatable") > 1){
		BootstrapDialog.alert("请选择一行数据！");
		return;
	}
	if(getSelectedSize("datatable") <= 0){
		BootstrapDialog.alert("请选择要修改的数据！");
		return;
	}
	var str = '<div><form class="e-form"><input type="hidden" name="ids" value="'+getSelectedIds("datatable")+'" />'+getSelectedSize("datatable")+'？</form>';
	var id = getSelectedIds("datatable");
	showUploadDialog("修改"+entityName,$('<div>'+$("#upload").html()+'</div>'),'上传图片',uploadUrl+"?id="+id);
	setTimeout('closeDialog()',800);
	//setTimeout("buildUpdateHtml()",500);
	//location.reload();
}

function deleteData(){
	if(getSelectedSize("datatable") <= 0){
		BootstrapDialog.alert("请选择要删除的数据！");
		return;
	}
	var str = '<div><form class="e-form"><input type="hidden" name="ids" value="'+getSelectedIds("datatable")+'" />你确定要删除这'+getSelectedSize("datatable")+'条数据吗？</form>'
				+'<div class="submitting_div" style="display:none"><img src="'+homePath+'/images/loader.gif" border="0" /></div><div class="serverResponse"></div></div>';
	showOriginalDialog("删除"+entityName,str,'删除',deleteUrl);
}
//修改密码
function saveUserPwd(){
	if(getSelectedSize("datatable") > 1){
		BootstrapDialog.alert("请选择一行数据！");
		return;
	}
	if(getSelectedSize("datatable") <= 0){
		BootstrapDialog.alert("请选择要修改的数据！");
		return;
	}
	var id = getSelectedIds("datatable");
	showOriginalDialog("修改"+entityName+"密码",$('<div>'+$("#edit_pwd").html()+'</div>'),'保存',savePwdUrl+"?id="+id);
	setTimeout("buildAddHtml()",500);
}
function showOriginalDialog(title,msg,lbl,url){
	BootstrapDialog.show({
		title:title,
		message: msg,
		buttons: [{
			label: lbl,
			cssClass: 'button button-primary button-rounded button-small',
			action: function(dialog){
				if(!submitValidate) return
				btnSubmit(dialog,url,$('.modal-body .e-form').serializeArray(),title)
			}
		}]
	});
}
function showUploadDialog(title,msg,lbl,url){
	BootstrapDialog.show({
		title:title,
		message: msg,
		buttons: [{
			label: lbl,
			cssClass: 'button button-primary button-rounded button-small',
			action: function(dialog){
				if(!submitValidate) return
				//window.location.href=url;
				uploadImage(url);
			}
		}]
	});
}
function uploadImage(url){
	layer.open({
	  type: 2,
	  title:"图片上传",
	  area: ['700px', '530px'],
	  fix: false, //不固定
	  maxmin: true,
	  content: url
	});
}
function btnSubmit(dialog,subUrl,data,title){
	gDialog = dialog;
	$('.modal-body .msg').hide();
	$('.modal-body .submitting_div').show();
	$('.modal-body .e-form').hide();
	$.post(subUrl,data, function(result) {
		if("success" == result.result){
			$('.modal-body .submitting_div').hide();
			$('.modal-body .serverResponse').html(title+"成功");
			setTimeout('closeDialog()',800);
		}else{
			$('.modal-body .submitting_div').hide();
			$('.modal-body .e-form').show();
			$('.modal-body .msg').html(result.error);
			$('.modal-body .msg').show();
		}
	});
}
function closeDialog(){
	gDialog.close();
	refreshData();
}
function getHtml(target){
	return $(target).html().replace(new RegExp(/(\n)|(\t)/g),"")
}
jQuery.fn.buildSelect = function(url,value,isAddEmpty){
	var select = $(this);
	$.getJSON(url,function(result) {
    	if(isAddEmpty) select.append("<option></option>");
    	for(i=0;i<result.length;i++)
    		select.append("<option value=\""+result[i].value+"\" "+(result[i].text==value?"selected=\"selected\"":"")+" >"+result[i].text+"</option>");
	});
}
jQuery.fn.setSelectVal = function(mate,val){
	if("label" == mate) $(this).children("option").each(function(i){if($(this).text()==val) $(this).attr("selected","selected")})
	else if("value" == mate) $(this).val(val)
}

$(".list .li1").mouseover(function (){
	$(this).children($(".select")).show();
});

$(".list .li1").mouseout(function (){
	$(".select").hide();
});
