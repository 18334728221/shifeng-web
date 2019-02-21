<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="description" content="非物质交易">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="refresh" content="20000">>
	<title>分配库存</title>
	<%@include file="/common/css.jsp"%>
</head>

<body>
	<div class="ctt-box" style="margin-top: 20px; margin-left: 40px;">
		<div class="ctt">
			<div>
				<input type="hidden" name="productCode" id="productCode" value="${productCode}"/>
				<span class="span2">可发行数量:</span>
				<span id="allot" class="form-control input-sm dis-i-b w-auto">${productAmount}</span>
			</div>
			
			<div>
				<span class="span2">流通盘:</span><input class="form-control input-sm dis-i-b w-auto" id="circulatingStock" name=circulatingStock type="text"/>
			</div>
			<div>
				<span class="span2">承销开始时间:</span><input class="form-control input-sm dis-i-b w-auto" id="underwritingStartTime" name="underwritingStartTime" type="text" readonly="readonly" style="width: 210px"  value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:MM:ss'});" />
			</div>
			<div>
				<span class="span2">承销结束时间:</span><input class="form-control input-sm dis-i-b w-auto" id="underwritingEndTime" name="underwritingEndTime" type="text" readonly="readonly" style="width: 210px"  value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:MM:ss'});" />
			</div>
			<div>
				<span class="span2">申购开始时间:</span><input class="form-control input-sm dis-i-b w-auto" id="purchaseStartTime" name="purchaseStartTime" type="text" readonly="readonly" style="width: 210px"  value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:MM:ss'});" />
			</div>
			<div>
				<span class="span2">申购结束时间:</span><input class="form-control input-sm dis-i-b w-auto" id="purchaseEndTime" name="purchaseEndTime" type="text" readonly="readonly" style="width: 210px"  value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:MM:ss'});" />
			</div>
			<div>
				<span class="span2">流通时间:</span><input class="form-control input-sm dis-i-b w-auto" id="circulateTime" name="circulateTime" type="text" readonly="readonly" style="width: 210px"  value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:MM:ss'});" />
			</div>
			<div>
				<span class="span2">总申购数:</span><input class="form-control input-sm dis-i-b w-auto" id="purchaseTotalAmount" name="purchaseTotalAmount" type="text"/></span>
			</div>
			<div>
				<span class="span2">会员积分申购总数:</span><input class="form-control input-sm dis-i-b w-auto" id="integralPurchaseNum" name="integralPurchaseNum" type="text"/></span>
			</div>
			<div>
				<span class="span2">申购参与积分人数:</span><input class="form-control input-sm dis-i-b w-auto" id="integralNum"  name="integralNum" type="text"/></span>
			</div>
			<div>
				<span class="span2">申购价格:</span><input class="form-control input-sm dis-i-b w-auto" id="purchasePrice"  name="purchasePrice" type="text"/></span>
			</div>
				
			
		</div>
		<button onclick="addSku();" class="form-control input-sm dis-i-b w-auto">确定</button>
	</div>
	<input type="hidden" id="aqId" />
	<%@ include file="/common/js.jsp"%>
	<script src="${ctx}/plugins/layer/layer.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function(){
			$("#config_index").css("background-color","#ffffff");
		})
		//确定
		function addSku(){
			/* var allot = $("#allot").html();
			var totalStocks =  $("#totalStock").val();
			if(totalStocks > allot){
				alert("入库量大于可入库量")
				return false;
			} */
			
			var productCode = $("#productCode").val();
			//流通盘
			var circulatingStock = $("#circulatingStock").val();
			//开始时间
			var underwritingStartTime = $("#underwritingStartTime").val();
			//承销结束时间
			var underwritingEndTime = $("#underwritingEndTime").val();
			//申购开始时间
			var purchaseStartTime = $("#purchaseStartTime").val();
			//申购结束时间
			var purchaseEndTime = $("#purchaseEndTime").val();
			//流通时间
			var circulateTime = $("#circulateTime").val();
			//总申购数
			var purchaseTotalAmount = $("#purchaseTotalAmount").val();
			//积分会员可申购股数
			var integralPurchaseNum = $("#integralPurchaseNum").val();
			//申购参与积分人数
			var integralNum = $("#integralNum").val();
			//申购价格
			var purchasePrice = $("#purchasePrice").val();
			
			//拼接参数
			var formData = new FormData();
		    formData.append("circulatingStock",circulatingStock);
		    formData.append("underwritingStartTime",underwritingStartTime);
		    formData.append("underwritingEndTime",underwritingEndTime);
		    formData.append("purchaseStartTime",purchaseStartTime);
		    formData.append("purchaseEndTime",purchaseEndTime);
		    formData.append("circulateTime",circulateTime);
		    formData.append("purchaseTotalAmount",purchaseTotalAmount);
		    formData.append("integralPurchaseNum",integralPurchaseNum);
		    formData.append("integralNum",integralNum);
		    formData.append("productCode",productCode);
		    formData.append("purchasePrice",purchasePrice);
			
			$.ajax({
				url:"${ctx}/admin/product/issue/save",
				type:"POST",
				data:formData,
				dataType:"json",
				cache: false,
		        contentType: false,
		        processData: false,
				success:function(imagePath){
					if(imagePath){
						//layer.msg('恭喜你，上传成功....');
						//parent.$("#img").attr("src",imagePath.data);
						parent.layer.closeAll();
					}
				}
			});
		}
	</script>
</body>
</html>