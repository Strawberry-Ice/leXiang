<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection"content="telephone=yes, email=yes" />
    <title>兑换记录</title>
    <meta name="description" content="" />
	<meta name="keywords" content="" />
	<jsp:include page="weichart_js_and_css.jsp"></jsp:include>
</head>
<body>
	<header id="head" class="header fixed-top article-top">
		<span class="fr"><a href="" class="btn"><i class="icon-add"></i><span>我要发布</span></a></span>
		<h4><i class="icon-file"></i><span>积分兑换详情</span><i class="icon-arrow-down"></i></h4>
	</header>
	<footer id="foot" class="footer fixed-bottom">
		<ul class="nav-primary nav-primary-mix">
			<li><a href="${pageContext.request.contextPath}/weichatScoreShop/listAllScoreItems"><i class="icon-cart"></i><p>积分兑换</p></a></li>
			<li style="display: none;"><a href=""><i class="icon-alarm"></i><p>限时福利</p></a></li>
			<li><a href="${pageContext.request.contextPath}/weichatScoreShop/findAllMyScoreRecords" class="current"><i class="icon-file"></i><p>积分记录</p> </a> </li>
		</ul>
	</footer>
	<section id="main" class="wrap-page">
		<div class="page">
			<div class="mt10 mb10">
				<div class="activity-content">
					<div class="title">
						<h2 class="ellipsis">${record.message } ${record.name }</h2>
					</div>
					
					<c:choose>
						<c:when test="${record.plus }">
							<div class="activity-body">${record.content }</div>
						</c:when>
						<c:otherwise>
						
						<div class="form-group good-exchange">
							<div class="good-pic">
								<img src="${pageContext.request.contextPath }${record.logo}" alt="商品图片">
							</div>
							<div class="good-info">
								<h3>${record.name }</h3>
								<p>所需积分：<span class="orange">${record.score }</span></p>
								<div class="good-num">
									<a href="javascript:qtyUpdate('down')"><i class="icon-minus"></i></a>
									<input type="text" name="number" value="${record.number }" readonly="readonly" pattern="[0-9]">
									<a href="javascript:qtyUpdate('up')"><i class="icon-plus"></i></a>
								</div>
							</div>
						</div>
						
						</c:otherwise>
					</c:choose>
					
					<div class="intergral_history">
						<p><c:choose><c:when test="${record.plus }">获得积分：</c:when><c:otherwise>使用积分：</c:otherwise></c:choose><input type="text" value="<c:choose><c:when test="${record.plus }">+</c:when><c:otherwise>-</c:otherwise></c:choose>${record.score }" readonly></p>
						<p>日　　期：<fmt:formatDate value="${record.createDate }" type="date" pattern="yyyy-MM-dd"/></p>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
<script src="js/swiper.min.js" type="text/javascript"></script>
<script>
var swiper = new Swiper('.swiper-container', {
    slidesPerView: 4,
    spaceBetween: 10,
    freeMode: true
});
</script>
</html>