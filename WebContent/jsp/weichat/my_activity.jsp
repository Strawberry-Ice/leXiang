<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection"content="telephone=yes, email=yes" />
    <title>我的活动</title>
    <meta name="description" content="" />
	<meta name="keywords" content="" />
	<jsp:include page="weichart_js_and_css.jsp"></jsp:include>
	<script type="text/javascript">
	
        $(document).ready(function() {
        	var $container = $('.list-activity');
    
        	$container.infinitescroll({
                navSelector: "#navigation", //导航的选择器，会被隐藏
                nextSelector: "#navigation a", //包含下一页链接的选择器
                itemSelector: ".list-activity li", //你将要取回的选项(内容块)
                debug: false, //启用调试信息
                animate: true, //当有新数据加载进来的时候，页面是否有动画效果，默认没有
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
	<footer id="foot" class="footer fixed-bottom">
		<ul class="nav-primary nav-primary-mix">
			<li><a href="${pageContext.request.contextPath }/weichatActivity/activity/1"><i class="icon-chat"></i><p>我的社区</p></a></li>
			<li><a href="${pageContext.request.contextPath }/weichatActivity/activity/2"><i class="icon-flag"></i><p>附近有啥</p></a></li>
			<li><a href="${pageContext.request.contextPath }/weichatShowUserInfo" class="current"><i class="icon-user"></i> <p>个人中心</p> </a> </li>
			<li><a href="${pageContext.request.contextPath }/weichatScoreShop/listAllScoreItems"><i class="icon-cart"></i><p>积分兑换</p></a></li>
		</ul>
	</footer>
	<section id="main" class="wrap-page">
		<div class="page">
			<div class="mt10 mb10">
				<ul class="list-activity my-activity">
					<c:forEach items="${pagingData.content }" var="activity">
						<li>
							<a href="${pageContext.request.contextPath }/weichatActivity/viewActivity/${activity.id}">
								<div class="fr text-center">
									<span class="badge bg-purple br50"><i class="icon-calendar"></i></span>
									<c:choose>
										<c:when test="${activity.category.code=='03' or activity.category.code=='04'}">
											<p class="gray">
												<small>
													${activity.applyNumber}报名
												</small>
											</p>
										</c:when>
										<c:otherwise>
											<p class="gray">
												<small>
													${fn:length(activity.comments)}阅读
												</small>
											</p>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="text">
									<h3 class="ellipsis">${activity.name}</h3>
									<div class="status">
										<div class="gray community-name">
											<small>
												昵称：${activity.creator.nickName}<br/>
											</small>
										</div>
										<c:choose>
												<c:when test="${activity.category.code=='03' or activity.category.code=='04' or activity.category.code=='05'}">
													<div class="act-status"><small class="gray"></small><small class="light-orange">${activity.frontStautus}</small></div>
												</c:when>
												<c:otherwise>
													
												</c:otherwise>
											</c:choose>
												
									</div>
								</div>
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
   		<a href="${pageContext.request.contextPath }/weichatShowMyActivity?${nextParam}">下一页</a>
   	</c:if>
</div>
</body>
</html>