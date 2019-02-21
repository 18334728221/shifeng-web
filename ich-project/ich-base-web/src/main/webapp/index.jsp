<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>绝艺</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/reset.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/homeBackground.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/plugins/bootstrap/css/bootstrap.min.css" />
</head>
<body>
	<!-- 头部 -->
	<header class="clear">
		<div class="logo fl">
			<img src="${ctx}/images/home/land_logo.png">
			<font>非遗服务系统</font>
		</div>
		<div class="tel fr">
			<img src="${ctx}/images/home/tel.png">
			<font id="tel">400-123-4567</font>	
		</div>
	</header>
	
	<div class="bg">
		<div class="loginBox clear">
			<div class="loginBoxleft fl"></div>
				<div class="loginBoxRight fl">
					<div class="login_title">
						<p style="color: #454545;font-size: 15px">登录</p>
					</div>
					<div class="box clear">
						<div class="box1 fl">
							<img src="${ctx}/images/home/homeUser.png">
						</div>
						<!-- 账号 -->
						<input type="text"  class="text1 fl" id="ip-uname" name="name" value="" placeholder="请输入有效的账号"/>
						<label id="userMsg"><label id="userMsg"></label>
                        <span></span>
                        <i id="c-name"></i>
					</div>
					<div class="box clear" style="margin-bottom: 5px">
						<div class="box1 fl">
							<img src="${ctx}/images/home/homePass.png">
						</div>
						<input type="password" text1 fl id="ip-pwd" name="password" value="" placeholder="请输入密码" class="active"/><label id="pwdMsg"></label>
                        <span></span>
                        <i id="c-pwd"></i>
					</div>
					<div class="choose">
						<p class="choose1" style="color: #a0a0a0">
                           <label> 
	                           <input type="checkbox" value="checkbox" id="rememberMe" name="rememberMe" value="1"/>
	                           <span style="color: #454545;font-size: 10px">记住密码</span>
                           </label>
						</p>
					</div>
					<div class="landBtn">
						<p onclick="submitDialog();">登录</p>
					</div>
				</div>
			</div>
		</div>
		
		<footer>
			<img src="${ctx}/images/home/footerPic.png">
			<span>爱梦（北京）文化传播有限责任公司</span>
		</footer>
		
		<script src="${ctx}/plugins/jquery/jquery-2.1.4.min.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	    var vodroot = "${vodroot}";
	    var livethumb = "${livethumb}";
	    var liveroot = "${liveroot}";
	    var ctx = "${ctx}";
		var liveroot = "${liveroot}";
		var user = "${user}";
		
		function indexSearch(){
			//点击搜索按钮查询
			$("#indexSearchForm").submit();
		}
		
		function isLogin(){
	    	if (user == "" || user == null) {
				return true;
		    }else{
		    	return false;
		    }
	    }
		
		$(function(){
	    	$("#btn_login").click(function(){
	    		$("#pop_login").fadeIn(200);
	    	})
	    	$(".popbox .close").click(function(){
	    		$("#uname").val("");
	    		$("#pwd").val("");
	    		$(this).parents(".popbox").fadeOut(200);
	    	})
	    	
	    	var matchCur = false;
	    	$("#divNavMenu a:first").removeClass("cur");
	    	$("#divNavMenu a").each(function(i){
	    		if (this.href.lastIndexOf(window.location.href) != -1){
	    			$(this).toggleClass("cur");
	    			matchCur = true;
	    			return false;
	    		}
	    	});
	    	if (!matchCur){
	    		$("#divNavMenu a:first").toggleClass("cur");
	    	}
	    	
	
	        $('input', $('#login_panel')).keydown(function (evt) {
	            if (evt.keyCode == 13) {
	                submitDialog();
	            }
	        });
	    });
	    
	    document.onkeydown=function(event){
	        var e = event || window.event || arguments.callee.caller.arguments[0];
	         if(e && e.keyCode==13){ // enter 键
	        	 submitDialog();
	        }
	    }; 
	    
	  //登录实现
	    var submitDialog = function () {
	        $("#passportError").text("");
	        if ($("input[name='name']").val() == "") {
	            $("#passportError").text("请输入用户名！");
	            $("input[name='name']").focus();
	            window.setTimeout(function () {
	                $("#passportError").text("");
	            }, 3000);
	            return;
	        }
	        
	        if ($("input[name='password']").val() == "") {
	            $("#passportError").text("请输入密码！");
	            $("input[name='password']").focus();
	            window.setTimeout(function () {
	                $("#passportError").text("");
	            }, 3000);
	            return;
	        }
	       
	        var rememberMe = 0;
	        if ($("#rememberMe").prop("checked")) {
	            rememberMe = 1;
	        }
	       
	        var submitData = {
	            name: $("input[name='name']").val(),
	            password: $("input[name='password']").val(),
	            code: $("input[name='code']").val(),
	            loginNumber: $("input[name='loginNumber']").val(),
	            rememberMe: rememberMe,
	            _ret : "${param._ret}"
	        };
	        $.post('${ctx}/public/user/login', submitData, function (data) {
	            if (data.loginNumber != null) {
	                $("#loginNumber").val(data.loginNumber);
	                if (data.loginNumber == '4') {
	                    $("#code_div").show();
	                }
	            }
	            if (data.message != null) {
	                if (data.message == 'userMsg') {
	                    $("#passportError").text("用户名或密码错误，请重新输入。");
	                    window.setTimeout(function () {
	                        $("#passportError").text("");
	                    }, 1000);
	                }
	                if (data.message == 'pwdMsg') {
	                    $("#passportError").text("用户名或密码错误，请重新输入。");
	                    window.setTimeout(function () {
	                        $("#passportError").text("");
	                    }, 1000);
	                }
	                return;
	            } else {
	            	 if (data.result == 'success') {
	            		 if(data._ret){
	     		   			window.location.href=data._ret;
	     		   		 }else{
	     		   			window.location.href=data._ret;
	     		   		 }
	                 }else{
	                	 if(data._ret){
	     		   			window.location.href="?_ret="+data._ret;
	     		   		}else{
	     		   			window.location.href="?_ret="+data._ret;
	     		   		}
	            	}
	            } 
	        });
	    }
	
	    //退出实现
	    function logout(){
	    	$.ajax({
	            url: window.ctx + '/public/user/logoutAjax',
	            data: {},
	            type: "post",
	            cache: false,
	            dataType: 'json',
	            timeout: 300,
	            beforeSend: function (XMLHttpRequest) {
	            },
	            success: function (data) {
	            	window.location.reload();
	            },
	            error: function (errorThrown) {
	            	
	            }
	        });
	    }
		$("i[id^='c-']").click(function(){
			$("#ip-"+$(this).attr('id').split("-")[1]).val("");
		});
	</script>
	<%-- <div class="top">
        <div class="top-con clearfix">
            <h1><a href="${ctx}/">非物质文化遗产交易</a></h1>
            <h2>欢迎登陆</h2>
        </div>
    </div>
    <div class="login">
        <div class="login-box">
            <div class="login-con clearfix">
                <div class="login-left">
                	<h2>登录</h2>
					<div id="passportError" class="error" style="width:324px; height:44px ;margin-left:20px; margin:20px 0 0 0; float:left;margin-left:40px; color:#ff8500; line-height:30px;"></div>
					<input type="hidden" id="loginNumber" name="loginNumber" value="<%=session.getAttribute("loginNumber") %>">
                    
                    <div class="login-phone login-phone3" style="margin-top:0;">
                    	<label class="login_tr_div_lbl"></label>
                    	<input type="text" id="ip-uname" name="name" value="" placeholder="请输入有效的账号"/><label id="userMsg"></label>
                        <span></span>
                        <i id="c-name"></i>
                    </div>
                    
                    <div class="login-phone2 login-phone3">
                    	<label class="login_tr_div_lbl"></label>
                    	<input type="password" id="ip-pwd" name="password" value="" placeholder="请输入密码" class="active"/><label id="pwdMsg"></label>
                        <span></span>
                        <i id="c-pwd"></i>
                    </div>
                    
                    <div class="login-pass">
                    	<input type="checkbox" id="rememberMe" name="rememberMe" value="1"/>
                        <span>记住密码</span>
                        <a href="javascript:;">忘记密码</a>
                    </div>
                    <input type="submit" value="登录" class="login-sub" onclick="submitDialog();"/>
                    <p class="login-p2">还没有账号 快去<a href="${ctx}/public/student/register">注册</a></p>
                </div>
                
                <div class="login-middle">
                	<p>下载123学堂APP随时随地查看课表</p>
                    <span><img src="${ctx}/images/home/31_03.png" alt="" style="width: 213px;height: 213px"/></span>
                    <i></i>
                    <strong>扫描二维码下载安装</strong>
                </div>
                
                <div class="login-right">
                	<img src="${ctx}/images/222.png" alt=""/>
                </div>
            </div>
        </div>
    </div>
    
    <!--footer-->
	<div class="bottomCenter clearfix">
          <ul>
              <li class="li1"><a href="javascript:;">关于我们</a></li>
              <li><a href="javascript:;">联系我们</a></li>
              <li><a href="javascript:;">服务条款</a></li>
              <li><a href="javascript:;">服务保障</a></li>
              <li><a href="javascript:;">网站地图</a></li>
              <li><a href="javascript:;">帮助中心</a></li>
          </ul>
	</div>
	<div class="bottomBottom"><a href="http://www.ievshow.com">www.ievshow.com</a>北京海德拉投资管理有限公司版权所有</div>
	<script src="${ctx}/plugins/jquery/jquery-2.1.4.min.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	    var vodroot = "${vodroot}";
	    var livethumb = "${livethumb}";
	    var liveroot = "${liveroot}";
	    var ctx = "${ctx}";
		var liveroot = "${liveroot}";
		var user = "${user}";
		
		function indexSearch(){
			//点击搜索按钮查询
			$("#indexSearchForm").submit();
		}
		
		function isLogin(){
	    	if (user == "" || user == null) {
				return true;
		    }else{
		    	return false;
		    }
	    }
		
		$(function(){
	    	$("#btn_login").click(function(){
	    		$("#pop_login").fadeIn(200);
	    	})
	    	$(".popbox .close").click(function(){
	    		$("#uname").val("");
	    		$("#pwd").val("");
	    		$(this).parents(".popbox").fadeOut(200);
	    	})
	    	
	    	var matchCur = false;
	    	$("#divNavMenu a:first").removeClass("cur");
	    	$("#divNavMenu a").each(function(i){
	    		if (this.href.lastIndexOf(window.location.href) != -1){
	    			$(this).toggleClass("cur");
	    			matchCur = true;
	    			return false;
	    		}
	    	});
	    	if (!matchCur){
	    		$("#divNavMenu a:first").toggleClass("cur");
	    	}
	    	
	
	        $('input', $('#login_panel')).keydown(function (evt) {
	            if (evt.keyCode == 13) {
	                submitDialog();
	            }
	        });
	    });
	    
	    document.onkeydown=function(event){
	        var e = event || window.event || arguments.callee.caller.arguments[0];
	         if(e && e.keyCode==13){ // enter 键
	        	 submitDialog();
	        }
	    }; 
	    
	  //登录实现
	    var submitDialog = function () {
	        $("#passportError").text("");
	        if ($("input[name='name']").val() == "") {
	            $("#passportError").text("请输入用户名！");
	            $("input[name='name']").focus();
	            window.setTimeout(function () {
	                $("#passportError").text("");
	            }, 3000);
	            return;
	        }
	        
	        if ($("input[name='password']").val() == "") {
	            $("#passportError").text("请输入密码！");
	            $("input[name='password']").focus();
	            window.setTimeout(function () {
	                $("#passportError").text("");
	            }, 3000);
	            return;
	        }
	       
	        var rememberMe = 0;
	        if ($("#rememberMe").prop("checked")) {
	            rememberMe = 1;
	        }
	       
	        var submitData = {
	            name: $("input[name='name']").val(),
	            password: $("input[name='password']").val(),
	            code: $("input[name='code']").val(),
	            loginNumber: $("input[name='loginNumber']").val(),
	            rememberMe: rememberMe,
	            _ret : "${param._ret}"
	        };
	        $.post('${ctx}/public/user/login', submitData, function (data) {
	            if (data.loginNumber != null) {
	                $("#loginNumber").val(data.loginNumber);
	                if (data.loginNumber == '4') {
	                    $("#code_div").show();
	                }
	            }
	            if (data.message != null) {
	                if (data.message == 'userMsg') {
	                    $("#passportError").text("用户名或密码错误，请重新输入。");
	                    window.setTimeout(function () {
	                        $("#passportError").text("");
	                    }, 1000);
	                }
	                if (data.message == 'pwdMsg') {
	                    $("#passportError").text("用户名或密码错误，请重新输入。");
	                    window.setTimeout(function () {
	                        $("#passportError").text("");
	                    }, 1000);
	                }
	                return;
	            } else {
	            	 if (data.result == 'success') {
	            		 if(data._ret){
	     		   			window.location.href=data._ret;
	     		   		 }else{
	     		   			window.location.href=data._ret;
	     		   		 }
	                 }else{
	                	 if(data._ret){
	     		   			window.location.href="?_ret="+data._ret;
	     		   		}else{
	     		   			window.location.href="?_ret="+data._ret;
	     		   		}
	            	}
	            } 
	        });
	    }
	
	    //退出实现
	    function logout(){
	    	$.ajax({
	            url: window.ctx + '/public/user/logoutAjax',
	            data: {},
	            type: "post",
	            cache: false,
	            dataType: 'json',
	            timeout: 300,
	            beforeSend: function (XMLHttpRequest) {
	            },
	            success: function (data) {
	            	window.location.reload();
	            },
	            error: function (errorThrown) {
	            	
	            }
	        });
	    }
		$("i[id^='c-']").click(function(){
			$("#ip-"+$(this).attr('id').split("-")[1]).val("");
		});
	</script> --%>
</body>
</html>
