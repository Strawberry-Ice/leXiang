<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=yes, email=yes" />
<title>积分记录</title>
<meta name="description" content="" />
<meta name="keywords" content="" />
<jsp:include page="weichart_js_and_css.jsp"></jsp:include>
<style>
* {
	margin: 0;
	padding: 0;
}

.list_rank {
	border-top: 2px solid #fff;
	width: 100%;
	line-height: 42px;
	text-align: center;
	border-collapse: collapse;
}

.list_rank .title {
	background-color: #f27c25;
	color: #ffffff;
	font-size: 12px;
}

.list_rank th {
	position: relative;
	font-weight: 500;
	width: 150px;
}

.list_rank th:after {
	content: '';
	position: absolute;
	width: 1px;
	height: 12px;
	background-color: #eeeeee;
	top: 15px;
	right: 0;
}

.list_rank th:last-child:after {
	content: '';
	display: none;
}

.list_rank tr:nth-child(2) {
	background-color: #fed4b4;
	color: #ff0000;
}

.list_rank tr:nth-child(3) {
	background-color: #fed4b4;
	color: #ff0000;
}

.list_rank tr:nth-child(4) {
	background-color: #fed4b4;
	color: #ff0000;
}

.list_rank td {
	font-size: 12px;
	border-bottom: 1px solid #eee;
}

.list_rank td:nth-child(3) {
	color: #ff0000;
}

.list_rank td:nth-child(4) {
	color: #999999;
}
</style>
</head>
<body>
	<nav id="head" class="header fixed-top"
		style="background-color: white;">
		<ul class="nav-primary nav-primary-mix">
			<li><a
				href="${pageContext.request.contextPath}/weichatScoreShop/findAllMyScoreRecords"
				><i class="icon-file"></i>
				<p>积分记录</p></a></li>
			<li style="display: none;"><a href=""><i class="icon-alarm"></i>
				<p>限时福利</p></a></li>
			<li><a
				href="${pageContext.request.contextPath}/weichatScoreShop/findAllMyScoreRecords2" class="current"><i
					class="icon-cart2"></i>
				<p>积分排行</p> </a></li>
		</ul>
	</nav>
	<footer id="foot" class="footer fixed-bottom">
		<ul class="nav-primary nav-primary-mix">
			<li><a
				href="${pageContext.request.contextPath }/weichatActivity/activity/1"><i
					class="icon-chat"></i>
				<p>我的社区</p></a></li>
			<li><a
				href="${pageContext.request.contextPath }/weichatActivity/activity/2"><i
					class="icon-flag"></i>
				<p>附近有啥</p></a></li>
			<li><a
				href="${pageContext.request.contextPath }/weichatShowUserInfo"><i
					class="icon-user"></i>
					<p>个人中心</p> </a></li>
			<li><a
				href="${pageContext.request.contextPath }/weichatScoreShop/listAllScoreItems"
				class="current"><i class="icon-cart"></i>
				<p>积分兑换</p></a></li>
		</ul>
	</footer>
	<section id="main" class="wrap-page">
		<div class="page">
			<table class="list_rank">
    <tr class="title">
        <th>排名</th>
        <th>用户名称</th>
        <th>当前积分</th>
        <th>历史最高积分</th>
    </tr>
    <c:forEach var="data" items="${pagingData}" varStatus="index">
    <tr>
        <td>${index.count }</td>
        <td>${data.user.nickName }</td>
        <td>${data.balance }</td>
        <td>${data.allGetPoints }</td>
    </tr>
    </c:forEach>
</table>
		</div>
	</section>
</body>
</html>