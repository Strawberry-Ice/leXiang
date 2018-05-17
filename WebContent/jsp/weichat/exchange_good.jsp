<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fytec" uri="/fengyuntec.com"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection"content="telephone=yes, email=yes" />
    <title>兑换</title>
    <meta name="description" content="" />
	<meta name="keywords" content="" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/weichat/css/global.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/weichat/css/layout.css">
		<script src="${pageContext.request.contextPath }/assets/js/jquery.js"></script>
</head>
<body>
	<nav id="head" class="header fixed-top" style="background-color: white;">
		<ul class="nav-primary nav-primary-mix">
			<li><a href="${pageContext.request.contextPath}/weichatScoreShop/listAllScoreItems" class="current"><i class="icon-cart"></i><p>积分兑换</p></a></li>
			<li style="display: none;"><a href=""><i class="icon-alarm"></i><p>限时福利</p></a></li>
			<li><a href="${pageContext.request.contextPath}/weichatScoreShop/findAllMyScoreRecords"><i class="icon-file"></i><p>积分记录</p> </a> </li>
		</ul>
	</nav>
	<footer id="foot" class="footer fixed-bottom">
	<ul class="nav-primary nav-primary-mix">
		<li><a href="${pageContext.request.contextPath }/weichatActivity/activity/1"><i class="icon-chat"></i><p>我的社区</p></a></li>
		<li><a href="${pageContext.request.contextPath }/weichatActivity/activity/2"><i class="icon-flag"></i><p>附近有啥</p></a></li>
		<li><a href="${pageContext.request.contextPath }/weichatShowUserInfo"><i class="icon-user"></i> <p>个人中心</p> </a> </li>
		<li><a href="${pageContext.request.contextPath }/weichatScoreShop/listAllScoreItems" class="current"><i class="icon-cart"></i><p>积分兑换</p></a></li>
	</ul>
	</footer>
	<section id="main" class="wrap-page">
		<div class="page">
			<div class="mt10 mb10">
				<form class="pd10 form" action="${pageContext.request.contextPath }/weichatScoreShop/doExchangeItem" method="post" name="form1">
					<input type="hidden" name="id" value="${item.id }">
					<div class="form-group good-exchange">
						<div class="good-pic">
							<img src="<fytec:image url="${item.logo }" size="200_150"/>" alt="商品图片">
						</div>
						<div class="good-info">
							<h3>${item.name }</h3>
							<p>所需积分：<span class="orange">${item.score }</span></p>
							剩余数量：${item.restNum }份
							<div class="good-num">
								<!--<input type="hidden" name="number" value="1"/>-->
								<a href="javascript:qtyUpdate('down')"><i class="icon-minus"></i></a>
								<input type="text" name="number" value="1" pattern="[0-9]" readonly="readonly">
								<a href="javascript:qtyUpdate('up')"><i class="icon-plus"></i></a>
							</div>
						</div>
					</div>
					<div class="form-btn">
						<input type="submit" value="确定兑换" class="btn btn-orange">
					</div>
					<div class="mt10">
						<h3>兑换说明：</h3>
						<div class="mt10 gray">${item.desc }</div>
					</div>
				</form>
			</div>
		</div>
	</section>
</body>
<script src="<%=request.getContextPath()%>/weichat/js/swiper.min.js" type="text/javascript"></script>
<script>
var swiper = new Swiper('.swiper-container', {
    slidesPerView: 4,
    spaceBetween: 10,
    freeMode: true
});
</script>
<script type="text/JavaScript">
	var score=${item.score};
	var restNum=${item.restNum}
	// 数量
	function qtyUpdate(n){
	    var f = document.form1;
	    var c = f.number.value;
	    if(n == "up"){
	    	if(c<restNum){
	    		c++;
	    	}
	    }else if(n == "down"){
	        if(c > 1) c--;
	    }
	    $('.orange').html(c*score);
	    f.number.value = c;
	}
	if('${msg}'){
		alert('${msg}')
	}
</script>
</html>