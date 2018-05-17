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
    <title>积分兑换</title>
    <meta name="description" content="" />
	<meta name="keywords" content="" />
	<jsp:include page="weichart_js_and_css.jsp"></jsp:include>
	<script type="text/javascript">
	
        $(document).ready(function() {
        	var $container = $('.list-goods');
    
        	$container.infinitescroll({
                navSelector: "#navigation", //导航的选择器，会被隐藏
                nextSelector: "#navigation a", //包含下一页链接的选择器
                itemSelector: ".list-goods li", //你将要取回的选项(内容块)
                debug: false, //启用调试信息
                animate: false, //当有新数据加载进来的时候，页面是否有动画效果，默认没有
                extraScrollPx: 150, //滚动条距离底部多少像素的时候开始加载，默认150
                bufferPx: 40, //载入信息的显示时间，时间越大，载入信息显示时间越短
                errorCallback: function() {
                    //alert('error');
                }, //当出错的时候，比如404页面的时候执行的函数
                localMode: true, //是否允许载入具有相同函数的页面，默认为false
                dataType: 'html',//可以是json
//                template: function(data) {
//                    //data表示服务端返回的json格式数据，这里需要把data转换成瀑布流块的html格式，然后返回给回到函数
//                    return '';
//                },
                loading: {
                    msgText: "加载中...",
                    finishedMsg: '没有新数据了...',
                    selector: '.loading' // 显示loading信息的div
                }
            }, function(newElems) {
                //程序执行完的回调函数
                var $newElems = $(newElems);
                $container.masonry('appended', $newElems );
            });
			
        	if('${msg}'=='success'){
        		alert('兑换成功！！')
        	}
        });
    </script>
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
	<section id="main" class="wrap-page index-page">
		<div class="page">
			<div align="center">积分兑换功能目前在湖东第五元素社区试运行！</div>
			<div class="mt10 mb10">
				<ul class="list-goods pd10">
					<c:forEach items="${pagingData.content }" var="item">
						<li>
							<a href="${pageContext.request.contextPath }/weichatScoreShop/viewScoreItem/${item.id}">
								<img src="<fytec:image url="${item.logo }" size="200_150"/>" alt="${item.name }">
								<p>${item.score }积分 ${item.name }</p>
							</a>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		 <div class="loading" style="display: none;">
                <img src="${pageContext.request.contextPath }/weichat/images/loading.gif"><span>正在加载...</span></div>
	</section>
<div id="navigation" style="display: none;">
	<c:if test="${not empty nextParam}">
   		<a href="${pageContext.request.contextPath }/weichatScoreShop/listAllScoreItems?${nextParam}">下一页</a>
   	</c:if>
</div>
 
</body>
<script src="<%=request.getContextPath()%>/weichat/js/swiper.min.js" type="text/javascript"></script>
<script>
var swiper = new Swiper('.swiper-container', {
    slidesPerView: 4,
    spaceBetween: 10,
    freeMode: true
});
</script>
</html>
