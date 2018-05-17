<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fytec"  uri="/fengyuntec.com" %>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection"content="telephone=yes, email=yes" />
    <title>我的活动</title>
    <meta name="description" content="" />
	<meta name="keywords" content="" />
	<jsp:include page="weichart_js_and_css.jsp"></jsp:include>
<style>
#mcover {
position: fixed;
top: 0;
left: 0;
width: 100%;
height: 100%;
background: rgba(0, 0, 0, 0.7);
display: none;
z-index: 20000;
}
#mcover img {
position: fixed;
right: 18px;
top: 5px;
width: 260px!important;
height: 180px!important;
z-index: 20001;
}
</style>
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
			<div id="mcover" onclick="weChat()" style="display:none;">
	          <img src="${pageContext.request.contextPath }/weichat/images/weichat.png" />
		   </div>
		   	<div id="content" class="mt10 mb10">
		   
				<div class="activity-content">
					<div class="title">
						<span class="fr gray">
							<!-- 已收藏&nbsp;<a href="javascript:;"><i class="icon-star2 orange"></i></a> -->
						</span>
						<h2 class="ellipsis" style="white-space: normal;">${httpActivity.name }</h2>
					</div>
					<div class="gray ellipsis activity-status">
						<small class="hide">${httpActivity.govName}</small>
						<small class="badge ${httpActivity.applicationColor}">${httpActivity.applicationStatus}</small>
						<small>报名截止&nbsp;${httpActivity.applicationEndDates }</small>
						<small>阅读&nbsp;${httpActivity.readCounts }</small>
						<small>分享&nbsp;${httpActivity.shardCounts }</small>
					</div>
					<div class="activity-body">
						<c:if test="${not empty httpActivity.imgUrl}">
							<img id="my_img" width="334px" height="324px" src="<fytec:image url="${httpActivity.imgUrl }" size="242_253"/>" alt=""><br>
						</c:if>
						${httpActivity.content }
						<c:if test="${not empty httpActivity.url }">
						<br><a href="${httpActivity.url }">${httpActivity.url }</a>
						</c:if>
					</div>
					<c:if test="${not empty httpActivity.score && httpActivity.score!=0}">
						<div class="activity-meta">
							<p>积          分：${httpActivity.score }</p>
						</div>
					</c:if>
					
					<div class="actors">
						<div class="activity-btn">
							<a href="${pageContext.request.contextPath }/weichatActivity/editActivity/${httpActivity.id}">编辑</a>
						</div>
						
						<div class="list-actor">
							<ul class="line-list" id="line-list">
								<li class="divider gray"><small class="orange">${httpActivity.applyNumber }</small>&nbsp;报名</li>
								<c:forEach var="httpUser" items="${httpActivity.httpApplications }" varStatus="status">
									<li <c:if test="${status.count > 2}">style="display:none;"</c:if>>
										<span class="fr text-center gray"><small>${httpUser.applyDate }</small></span>
										<span class="personimg">
											<c:choose>
												<c:when test="${not empty httpUser.userLogo }">
													<img src="${pageContext.request.contextPath }${httpUser.userLogo }" alt="" class="br50">
												</c:when>
												<c:otherwise>
													<img src="${pageContext.request.contextPath }/weichat/images/personimg.jpg" alt="" class="br50">
												</c:otherwise>
											</c:choose>
										</span>
										<span class="personname">${httpUser.applyName }</span>
									</li>
								</c:forEach>
							</ul>
							<c:if test="${fn:length(httpActivity.httpApplications) >= 2}">
								<h3 class="mt10 text-center">
									<a href="javascript:void(0);" class="click-more" onclick="showMore(this);">
										<span>展开更多报名</span>
									</a>
								</h3>
							</c:if>
						</div>
					</div>
					<div class="attention" style="display: none;">
						<h3>点击关注&nbsp;"<a href="">园区微笑生活</a>"</h3>
					</div>
				</div>
			</div>
		</div>
</section>
</body>
<script type="text/javascript">

function showMore(obj){
	var objs=$('#line-list li:gt(1)');
	for(var i=0;i<objs.length;i++){
		$(objs[i]).show();
	}
	
	$(obj).attr('onclick','hideMore(this);');
	$(obj).html('<span>隐藏更多评论</span>');
}

function hideMore(obj){
	var objs=$('#line-list li:gt(1)');
	for(var i=0;i<objs.length;i++){
		$(objs[i]).hide();
	}
	
	$(obj).attr('onclick','showMore(this);');
	$(obj).html('<span>显示更多评论</span>');
}
</script>


