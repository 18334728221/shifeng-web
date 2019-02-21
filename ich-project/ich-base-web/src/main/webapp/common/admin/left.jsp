<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
	<div class="contentAll" id="content">
		<div class="content"  id="content1">
			<div class="content-left">
				<ul>
					<li><a id="user_index" href="${ctx}/admin/auth/user/index">用户账号管理</a></li>
					<li><a id="role_index" href="${ctx}/admin/auth/role/index" >角色管理</a></li>
					<li><a id="group_index" href="${ctx}/admin/auth/group/index">组管理</a></li>
					<li><a id="module_index" href="${ctx}/admin/auth/module/index">模块管理</a></li>
					<li><a id="permission_index" href="${ctx}/admin/auth/permission/index">功能管理</a></li>
					<li><a id="craftsman_index" href="${ctx}/admin/base/craftsma/index">手艺人管理</a></li>
				</ul>
			</div>
		</div>
		<div class="content"  id="content2">
			<div class="content-left">
				<ul>
					<li><a id="server_index" href="${ctx}/admin/base/email/server/index" >邮件服务管理</a></li>
					<li><a id="mq_index" href="${ctx}/admin/base/mq/index">消息中间件管理管理</a></li>
					<li><a id="theme_index" href="${ctx}/admin/base/mq/theme/index">消息中间件主题管理管理</a></li>
					<%-- <li><a id="sms_server_index" href="${ctx}/admin/base/sms/server/index">短信管理</a></li> --%>
					<li><a id="config_index" href="${ctx}/admin/base/sys/config/index">系统高配置管理</a></li>
					<li class=""><a href="${ctx}/admin/message/craftsmanLogistics/index">物流信息管理</a></li>
				</ul>
			</div>
		</div>
		<div class="content"  id="content3">
			<div class="content-left">
				<ul>
					<li><a id="area_index" href="${ctx}/admin/dd/area/index">地区管理</a></li>	
					<li><a id="festival_index" href="${ctx}/admin/base/festival/index">节假日管理</a></li>	
					<li><a id="bank_index" href="${ctx}/admin/base/bank/index">银行管理</a></li>	
				</ul>
			</div>
		</div>
		<div class="content"  id="content4">
			<div class="content-left">
				<ul>
					<li><a id="product_index" href="${ctx}/admin/product/product/index">产品管理</a></li>
					<li><a id="image_index" href="${ctx}/admin/product/image/index?currPage=1">产品图片管理</a></li>
					<li><a id="sku_index" href="${ctx}/admin/product/sku/index">产品入库管理</a></li>
					<li><a id="skuImage_index" href="${ctx}/admin/product/skuImage/index">产品展示图片管理</a></li>
					<li><a id="product_customer_index" href="${ctx}/admin/product/customer/index">产品指定会员管理</a></li>
					<li><a id="product_evaluate_index" href="${ctx}/admin/product/evaluate/index">产品评价内容管理</a></li>
					<li><a id="issue_index" href="${ctx}/admin/product/issue/index">产品发行管理</a></li>
					<li><a id="trader_index" href="${ctx}/admin/product/trader/index">操盘手管理</a></li>
					<li><a id="video_index" href="${ctx}/admin/product/video/index">产品视频管理</a></li>
					<li><a id="stock_index" href="${ctx}/admin/product/stock/index">流通产品管理</a></li>
					<li><a id="customerProduct_index" href="${ctx}/admin/product/customerProduct/index">承销管理</a></li>
					<li><a id="purchase_index" href="${ctx}/admin/product/productPurchase/index">申购管理</a></li>
				</ul>
			</div>
		</div>
		
		<div class="content"  id="content5">
			<div class="content-left">
				<ul>
					<li><a id="category_index" href="${ctx}/admin/base/category/index">产品种类管理</a></li>
					<li><a id="categoryProperty_index" href="${ctx}/admin/base/categoryProperty/index">分类属性管理</a></li>
				</ul>
			</div>
		</div>
		
		<div class="content"  id="content6">
			<div class="content-left">
				<ul>
					<li><a id="evaluate_index" href="${ctx}/admin/evaluate/evaluate/index"> 评价管理</a></li>
					<li><a id="evaluateBec_index" href="${ctx}/admin/evaluate/evaluateBec/index">产品购买评价</a></li>
					<li><a id="evaluateImage_index" href="${ctx}/admin/evaluate/evaluateImage/index">评价图片</a></li>
					<li><a id="evaluatePec_index" href="${ctx}/admin/evaluate/evaluatePec/index">用户产品评价内容</a></li>
					<li><a id="thumbsUp_index" href="${ctx}/admin/evaluate/thumbsUp/index">点赞</a></li>
				</ul>
			</div>
		</div>
		
		<div class="content"  id="content7">
			<div class="content-left">
				<ul>
					<li><a id="logAccount_index" href="${ctx}/admin/base/logAccount/index">账户流水日志</a></li>
					<li><a id="logBuyerCommission_index" href="${ctx}/admin/base/logBuyerCommission/index">买家佣金收入</a></li>
					<li><a id="logCustomer_index" href="${ctx}/admin/base/logCustomer/index">客户操作日志</a></li>
					<li><a id="evaluatePec_index" href="${ctx}/admin/base/logDeposits/index">充值日志</a></li>
					<li><a id="logSellerCommission_index" href="${ctx}/admin/base/logSellerCommission/index">卖家佣金收入</a></li>
					<li><a id="logWithdraws_index" href="${ctx}/admin/base/logWithdraws/index">提现记录</a></li>
				</ul>
			</div>
		</div>
		
		<div class="content"  id="content8">
			<div class="content-left">
				<ul>
					<li><a id="LogUser_index" href="${ctx}/admin/base/logAccount/index">系统日志管理</a></li>
				</ul>
			</div>
		</div>
		
		<div class="content"  id="content9">
			<div class="content-left">
				<ul>
					<li><a id="goodsOrder_index" href="${ctx}/admin/mall/goodsOrder/index">客户商品订单管理</a></li>
					<li><a id="invoice_index" href="${ctx}/admin/mall/invoice/index">发票管理</a></li>
					<li><a id="orderItem_index" href="${ctx}/admin/mall/orderItem/index">订单项目管理</a></li>
				</ul>
			</div>
		</div>
		
		<div class="content"  id="content10">
			<div class="content-left">
				<ul>
					<li><a id="trade_index" href="${ctx}/admin/product/trade/index">交易成交记录</a></li>
					<li><a id="tradeOrder_index" href="${ctx}/admin/product/tradeOrder/index">交易订单记录</a></li>
					<%-- <li><a id="tradeOrderCancel_index" href="${ctx}/admin/product/cancelOrder/index">交易撤单记录</a></li> --%>
				</ul>
			</div>
		</div>
		
		<div class="content"  id="content11">
			<div class="content-left">
				<ul>
					<li><a id="activity_index" href="${ctx}/admin/base/activity/index">活动管理</a></li>
				</ul>
			</div>
		</div>
		<div class="content"  id="content12">
			<div class="content-left">
				<ul>
					<li><a id="banner_index" href="${ctx}/admin/base/banner/index">轮播图管理</a></li>
				</ul>
			</div>
		</div>
	</div>
	
	<%-- <div class="menu">
		<ul>
			<li class="h_submenu">
				<a id="li1" onclick="javascript:slideUl('li1');" href="javascript:void(0);"><i class="icon-user-md"></i>用户权限管理 <i class="icon-caret-left now"></i></a>
				<ul class="submenu" style="display:block;">
					<auth:hasOperationPermission target="com:rjsj:system:auth:model:AuthUser" operation="read" not="false">
					<li><a href="${ctx}/admin/auth/user/index"><i class="icon-user"></i>用户账号管理</a></li> 
					</auth:hasOperationPermission>
					<auth:hasOperationPermission target="com:rjsj:system:auth:model:AuthRole" operation="read" not="false">
					<li><a href="${ctx}/admin/auth/role/index"><i class="icon-user"></i>角色管理</a></li>
					</auth:hasOperationPermission>
				</ul>
			</li>
	</div> --%>