<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<% response.setStatus(HttpServletResponse.SC_OK); %> 
<%  
/** *//**  
* 本页面是在客户查找的页面无法找到的情况下调用的  
*/  
response.setStatus(HttpServletResponse.SC_OK);  
%>  
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>网页不存在</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/reset.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/homeBackground.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/plugins/bootstrap/css/bootstrap.min.css" />
</head>
<body>
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

	<div class="error" style="text-align: center; background-color: #ccc;">
    	<div class="error-con" >
        	<img src="${ctx}/images/error/aiya@3x.png" alt=""/>
        </div>
    </div>
	
    
   <footer>
		<img src="${ctx}/images/home/footerPic.png">
		<span>爱梦（北京）文化传播有限责任公司</span>
	</footer>
</body>
</html>
