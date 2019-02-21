<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>分配库存</title>
	<%@include file="/common/css.jsp"%>
</head>
<body>
	<div class="ctt-box" style="margin-top: 20px;margin-left: 40px;">
		<div class="ctt">
			<div>
				<input class="form-control input-sm dis-i-b w-auto" type="hidden" name="productCode" id="productCode" value="${productCode}"/>
				<span>可入库数量:</span>
				<span class="form-control input-sm dis-i-b w-auto" id="allot">${productAmount}</span>
			</div>
		</div>
		<div>
			<div>
				<span>产品规格:
					<c:forEach items="${assemblePropertyName}" var="property">
						<p><span>${property}:</span><span><input class="form-control input-sm dis-i-b w-auto" type="text" name="totalStock" value=""></span></p>
					</c:forEach>
				</span>
			</div>
			<c:forEach items="${assemblePropertyId}" var="property">
					<input class="form-control input-sm dis-i-b w-auto" type="hidden" name="av" value="${property}">
				</c:forEach>
		</div>
		
		<button onclick="addSku();" class="form-control input-sm dis-i-b w-auto">确定</button>
	</div>
	<input type="hidden" id="aqId" />
	<%@ include file="/common/js.jsp"%>
	<script src="${ctx}/plugins/layer/layer.js" type="text/javascript"></script>
	<script type="text/javascript">
		
		//确定
		function addSku(){
			var allot = $("#allot").html();
			var totalStocks =  $("#totalStock").val();
			if(totalStocks > allot){
				alert("入库量大于可入库量")
				return false;
			}
			//数量
			var totalStock = '';
			$("input[name='totalStock']").each(function(){
				if($(this).val()==''){
					totalStock += "0"+'-'; 
				}else{
					totalStock += $(this).val()+'-'; 
				}
			})
			//属性
			var av = '';
			$("input[name='av']").each(function(){
				av += $(this).val()+'-'; 
			})
			//产品编号
			var productCode = $("#productCode").val();
			//拼接参数
			var formData = new FormData();
		    formData.append("totalStock",totalStock);
		    formData.append("av",av);
		    formData.append("productCode",productCode);
			
			$.ajax({
				url:"${ctx}/admin/product/sku/save",
				type:"POST",
				data:formData,
				dataType:"json",
				cache: false,
		        contentType: false,
		        processData: false,
				success:function(imagePath){
					if(imagePath){
						layer.msg('恭喜你，上传成功....');
						parent.$("#img").attr("src",imagePath.data);
						parent.layer.closeAll();
					}
				}
			});
		}
	</script>
</body>
</html>