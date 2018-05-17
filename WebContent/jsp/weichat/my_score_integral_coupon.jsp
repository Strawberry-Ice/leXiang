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
		<span style="display: none;" class="fr"><a href="" class="btn"><i class="icon-add"></i><span>我要发布</span></a></span>
		<h4><i class="icon-file"></i><span>兑换码详情</span><i class="icon-arrow-down"></i></h4>
	</header>
	<section id="main" class="wrap-page">
		<div class="page">
			<div class="mt10 mb10">
				<div class="activity-content">
					<div class="title">
						<h2 class="ellipsis">${record.scoreShop.name }</h2>
					</div>
					
						<div class="activity-body">${record.scoreShop.desc }</div>
						
						<div class="form-group good-exchange">
							<div class="good-pic">
								<img src="${pageContext.request.contextPath }${record.scoreShop.logo}" alt="商品图片">
							</div>
							<div class="good-info">
								<h3>${record.scoreShop.name }</h3>
								<p>单个所需积分：<span class="orange">${record.scoreShop.score }</span></p>
								<p>数        量：<span class="orange">${record.number}</span></p>
								<p>花费积分：<span class="orange">${record.number*record.scoreShop.score }</span></p>
							</div>
						</div>
						
					
					<div class="intergral_history">
						<p>兑换码：${record.coupon }</p>
						<p>兑换状态：<c:choose><c:when test="${record.status }">已兑换</c:when><c:otherwise>未兑换</c:otherwise></c:choose></p>
						<p>使用截止日期：<fmt:formatDate value="${record.expiry }" type="date" pattern="yyyy-MM-dd"/></p>
						<p>兑换日期：<fmt:formatDate value="${record.verifyDate }" type="date" pattern="yyyy-MM-dd"/></p>
						<p>“可在个人中心”→“兑换券”中查看兑换记录。</p>
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