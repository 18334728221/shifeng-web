<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
 	<div class="container">
		<div class="header">
			<div class="header-top"></div>
			<div class="header-center">
				<div class="logo">
					<img src="${ctx}/images/123.png" alt="" class="logo-left">
					<img src="${ctx}/images/school.png" alt="" class="logo-right">
				</div>
				<ul class="list">
					<li class="li1 li"><span>系统管理</span><i></i>
						<ul class="select"  >
							<li class="" ><a href="${ctx}/admin/auth/user/index">用户账号管理</a></li>
							<li class="" ><a href="${ctx}/admin/base/email/server/index">服务器管理</a></li>
							<li class="" ><a href="${ctx}/admin/dd/area/index">基础数据管理</a></li>
						</ul>
					</li>
					<li class="li1 li"><span>产品管理</span><i></i>
						<ul class="select">
							<li class=""><a href="${ctx}/admin/product/product/index">产品管理</a></li>
							<li class=""><a href="${ctx}/admin/base/category/index">产品属性管理</a></li>
							<li class=""><a href="${ctx}/admin/mall/goodsOrder/index">订单管理</a></li>
							<li class=""><a href="${ctx}/admin/base/activity/index">活动管理</a></li>
							<li class=""><a href="${ctx}/admin/product/trade/index">交易管理</a></li>
						</ul>
					</li>
					
					<li class="li1 li"><a href="${ctx}/admin/evaluate/evaluate/index"><span>评价管理</span><i></i></a></li>
					
					
					<li class="li1 li" id="message"><span>消息管理</span><i></i>
						<ul class="select">
							<li class=""><a href="${ctx}/admin/message/smsUser/index">短信消息管理</a></li>
							<li class=""><a href="${ctx}/admin/message/emailUser/index">邮件消息管理</a></li>
							<li class=""><a href="${ctx}/admin/message/craftsmanLogistics/index">物流信息管理</a></li>
							<li class=""><a href="${ctx}/admin/message/campaign/index">活动提示管理</a></li>
						</ul>
					</li>
					<%-- <li class="li1 li" id="server"><a href="${ctx}/admin/base/emailServer/index"><span>服务器管理</span><i></i></a></li> --%>
					<li class="li1 li" ><span>日志管理</span><i></i>
						<ul class="select"  >
							<li class="" ><a href="${ctx}/admin/base/logUser/index">系统日志管理</a></li>
							<li class="" ><a href="${ctx}/admin/base/logAccount/index">客户日志管理</a></li>
						</ul>
					<li>
					<li class="li1 li" ><span>App管理</span><i></i>
						<ul class="select"  >
							<li class="" ><a href="${ctx}/admin/base/banner/index">轮播图管理</a></li>
						</ul>
					<li>
				</ul>
				<div class="info">
					<div class="date">
						<p class="date1">
							<script type="text/javascript">
								document.write(new Date().getFullYear()+"年"+(new Date().getMonth()+1)+"月"+new Date().getDate()+"日");
							</script>
						</p>
						<p class="date2">
							<script type="text/javascript">
								var week;
								if(new Date().getDay()==0)          week="星期日"
								if(new Date().getDay()==1)          week="星期一"
								if(new Date().getDay()==2)          week="星期二"
								if(new Date().getDay()==3)          week="星期三"
								if(new Date().getDay()==4)          week="星期四"
								if(new Date().getDay()==5)          week="星期五"
								if(new Date().getDay()==6)          week="星期六"
								document.write(week);
							</script>
						</p>
					</div>
					<div class="user">
						<img src="${ctx}/images/User.png" alt="" class="people">
						<a class="quit" href="javascript:logout();">退出</a>
						<a class="password" class="quit" href="javascript:saveUserPwd();">修改密码</a>
				   </div>
			</div>
			<div class="header-bottom"></div>
		</div>
	</div>
	</div>
	
	 <script type="text/javascript">
	    //退出实现
	    function logout(){
	    	$.ajax({
	            url: '${ctx}/public/user/logoutAjax',
	            data: {},
	            type: "post",
	            cache: false,
	            dataType: 'json',
	            timeout: 300,
	            beforeSend: function (XMLHttpRequest) {
	            },
	            success: function (data) {
	            	window.location.href = "${ctx}/"+data.result;
	            },
	            error: function (errorThrown) {
	            	
	            }
	        });
	    }
    </script>
