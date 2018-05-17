<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/weichat/shop/CSS/reset.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/weichat/shop/CSS/loading-style.css">
    <script src="${pageContext.request.contextPath }/assets/js/jquery.js"></script>
    <title>登录</title>
</head>
<body>
<div class="head">
    <img src="${pageContext.request.contextPath }/weichat/images/head_bg.jpg">
</div>
<div class="bottom">
    <form:form commandName="httpUser"
			action="${pageContext.request.contextPath }/weichatShop"
			 method="post" id="loginForm">
        <div class="f1">
            用户名：
            <form:input path="userName" cssClass="Text"/>
            <form:errors path="userName"/>
        </div>
        <div class="f2">
            密码：
            <form:password path="password" cssClass="Text"/>
            <form:errors path="password"/>
        </div>
        <div class="f3">
            <input class="butt" type="button" value="登录" onclick="submit();">
        </div>
    </form:form>
</div>
</body>
<script type="text/javascript">
	function submit(){
		$('#loginForm').submit();
	}
</script>
</html>