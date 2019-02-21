<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>备课</title>
        <link rel="stylesheet" href="${ctx}/css/teacher/teacher.css?v=${version}" />
		<link rel="stylesheet" href="${ctx}/css/home/public.css?v=${version}" />
        <link rel="stylesheet" href="${teacherctx}/css/lesson/base.css?v=${version}"/>
        <link rel="stylesheet" href="${teacherctx}/css/lesson/prepare.css?v=${version}"/>
        <link rel="stylesheet" href="${ctx}/plugins/webuploader/webuploader.css?v=${version}"/>
    </head>
    <body>
        <!----top---->
		<auth:authenticated not="false">
			<%@ include file="/commons/home/top.jsp" %>
		</auth:authenticated>
		<auth:authenticated not="true">
			<%@ include file="/commons/top.jsp" %>
		</auth:authenticated>
		<!-- nav -->
		<%@ include file="/commons/teacher/nav.jsp"%>
		<%@ include file="/commons/lesson/nav.jsp"%>
        <p class="prepare-p">当前位置&nbsp;:&nbsp;教学资源</p>
        <div class="resource-con">
            <dl class="prepare-dl clearfix" id="prepare_dl">
                <dt class="prepare-dt">
                    <span class="serial">序号</span>
                    <h3 class="prepare-h3-title">标题</h3>
                    <span class="serial-sp">编辑</span>
                </dt>
            </dl>
            <div class="prepare-green">添加教学资源</div>
            <div class="prepare-bar">
                <span>标&nbsp;&nbsp;&nbsp;&nbsp;题</span>
                <input id="title" type="text" value="" class="prepare-ipt"/>
            </div>
            <div class="prepare-bar">
                 <span>文&nbsp;&nbsp;&nbsp;&nbsp;件</span>
                 <input id="fname" type="text" value="" class="prepare-ipt" readonly="readonly"/>
                 <a id="picker" href="javascript:;" class="prepare-bar-a">选择文件</a>
            </div>
            <a href="javascript:;" class="prepare-save">保存</a>
        </div>
         <!-- alert弹框 -->
		<div class="tq-model3" style="width: 100%;height: 100%;position:fixed;top: 0;left: 0; background-color: rgba(0,0,0,0.2); z-index:10000; display:none;">
			<div style=" width:458px; height:220px; background:#fff; position:fixed; top:50%; left:50%; margin-left:-229px; margin-top:-110px; z-index:999;">
				<span style=" width:38px; height:38px; background:url(${ctx}/images/teacher/3_03.png) no-repeat; position:absolute; top:9px; right:26px ;display:block; cursor:pointer;" class="tq-model-close"></span>
				<i style="width:100px; height:100px; display:block;  position:absolute; top:68px;left:182px;  background:url(${ctx}/images/teacher/orrer.png) no-repeat;"></i>
				<p style="font-size:24px; color:#333333; position:absolute;bottom:20px; left:0px; font-weight:bold; border:none; width:458px; text-align:center;" class="tq-p"></p>
			</div>
		</div>
        <%@ include file="/commons/footer.jsp"%>
	    <%@ include file="/commons/bottom.jsp"%>
        <script src="${ctx}/plugins/jquery/jquery-2.1.4.min.js?v=${version}" type="text/javascript"></script>
		<script src="${ctx}/plugins/webuploader/webuploader.min.js?v=${version}" type="text/javascript"></script>
		<script src="${ctx}/js/check.js?v=${version}" type="text/javascript"></script>
        <script type="text/javascript">
       		$('#resource').addClass('active');
       		var ctx = "${ctx}";
       		var courseId = "${courseId}";
       		var title ='';
       		$(function(){
       			init();
       		});
       		
       		function init(){
       			$.get('${teacherctx}/teacher/lesson/resource/courseware/find/' + courseId, function(data) {
       				if(data.result && data.result=='failure'){
       					$(".tq-model3").show();
	    				$(".tq-p").html("请先备课。");
       					return;
       				}
    				if(data){
    					var html='';
    					for(var i=0;i < data.length; i++){
    						html += "<dd class='prepare-dt prepare-dd'><span class='serial'></span>";
    						html +="<span class='serial-text'>" + data[i].title + "</span>";
    						html += "<span class='serial-sp'>";
    						html += "<a href='javascript:;' class='resources-delete' id='" + data[i].id + "' >删除</a></span></dd>";
    					}
    					$("#prepare_dl").append(html);
    				}
    				  //删除
    	            $(".resources-delete").click(function (){
    	            	$(this).parent().parent().remove();
    	                var url="${teacherctx}/teacher/lesson/resource/delete/" + this.id;
    	       			$.post(url, function(data){
    	       				show();
    	       			});
    	            });

    				show();
    			});
       		}
       		
       		
            //修改可以输入
            $(".resources-modify").click(function (){
                $(this).parent().siblings("input").removeAttr("readonly");
                $(this).parent().siblings("input").focus(); 
            });

           
          
            //序号
            
            function show(){
                var singles = $('.prepare-dl dd').length;
                for(var i = 0; i < singles; i++){
                	$('.prepare-dl dd').eq(i).find('.serial').html(i+1);
                }
            }

            //点击保存
            $(".prepare-save").click(function (){
            	title = $("#title").val();
            	title=$.trim(title);
            	if(title == ''){
            		$(".tq-model3").show();
    				$(".tq-p").html("标题不能为空。");
    				return;
            	}
            	if(strlen(title)>=50){
    				$(".tq-model3").show();
    				$(".tq-p").html("标题不能超过50个字符。");
    				return;
    			}
            	//检查是否创建备课信息
            	 var url="${teacherctx}/teacher/lesson/plan/get/" + courseId;
	       		 $.get(url, function(data){
	       			 if(data.result && data.result == 'failure'){
	       				$(".tq-model3").show();
	    				$(".tq-p").html("请先备课。");
	       			 }else{
	       				upload();
	       			 }
	       		 });
            });
            
          //点击关闭弹框
    		$(".tq-model-close").click(function (){
    			$(".tq-model3").hide();
    		});
        </script>
        <script src="${teacherctx}/js/lesson/resource/courseware.js" type="text/javascript"></script>
    </body>
</html>