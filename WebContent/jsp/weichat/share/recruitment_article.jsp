<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fytec"  uri="/fengyuntec.com" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection"content="telephone=yes, email=yes" />
    <title>周边活动</title>
    <meta name="description" content="" />
	<meta name="keywords" content="" />
	<jsp:include page="../weichart_js_and_css.jsp"></jsp:include>
	<style>
		.publish {background-color:#f27c25;}
		.publish a {color:#fff;}
		.publish-btn {background-color:transparent;}
		.publish-btn a {margin: 7px auto 0;background-color: #f27c25;color:#fff;}
	</style>
</head>
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
						<h2 class="ellipsis" style="white-space: normal;width:100%;">${httpActivity.name }</h2>
					</div>
					<div class="gray ellipsis activity-status">
						<small>${httpActivity.frontName }</small>
					</div>
					<div class="gray ellipsis activity-status">
						<small class="hide">${httpActivity.govName}</small>
						<small class="badge bg-red">${httpActivity.applicationStatus}</small>
						<small>报名截止&nbsp;${httpActivity.applicationEndDates }</small>
						<small>阅读&nbsp;${httpActivity.readCounts }</small>
						<small>分享&nbsp;${httpActivity.shardCounts }</small>
					</div>
					<div class="activity-body">
						${httpActivity.content }
					</div>
					<div class="activity-meta">
						<p>积          分：${httpActivity.score }</p>
					</div>
					<div class="actors">
						<c:choose>
							<c:when test="${httpActivity.ifBegin }">
								<c:choose>
									<c:when test="${httpActivity.flag }">
										<div class="activity-btn activity-btn-gray">
											<a href="JavaScript:void(0);">已报名</a>
											<!-- <a href="${pageContext.request.contextPath }/weichatActivity/cancelApplication/${type}/${code}/${httpActivity.id}">取消报名</a> -->
										</div>
									</c:when>
									<c:otherwise>
										<div class="activity-btn">
											<!--<a href="https://open.weixin.qq.com/connect/oauth2/authorize?&appid=wxf6e18731811efcca&redirect_uri=http%3a%2f%2fwww.headline.cc%2fsmilecommunity%2fweichatActivity%2fapplyForShare%2f${type}%2f${code}%2f${httpActivity.id}&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect">我要报名</a>  -->
											<a href="${pageContext.request.contextPath}/weichatActivity/applyForShare/${type}/${code}/${httpActivity.id}">我要报名</a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${!httpActivity.ifEnd }">
								<div class="activity-btn activity-btn-gray">
									<a href="javascript:vodi(0);">报名已结束</a>
								</div>
							</c:when>
							<c:when test="${!httpActivity.ifBegin }">
								<div class="activity-btn">
									<a href="javascript:vodi(0);">报名未开始</a>
								</div>
							</c:when>
						</c:choose>
						
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
										<!--<span class="orange">+5</span>  -->
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
					
					<div class="actors">
						<div class="list-actor">
							<ul class="line-list" id="line-list1">
								<li class="divider gray"><small class="orange">${httpActivity.commentCounts }</small>&nbsp;评论</li>
								<c:forEach var="httpActivityComment" items="${httpActivity.httpActivityComments }" varStatus="status">
									<li <c:if test="${status.count > 2}">style="display:none;"</c:if>>
										<span class="fr text-center gray"><small>${httpActivityComment.createDate }</small></span>
										<span class="personimg">
											<c:choose>
												<c:when test="${not empty httpActivityComment.httpUser.imageUrl }">
													<img src="${pageContext.request.contextPath }${httpActivityComment.httpUser.imageUrl }" alt="" class="br50">
												</c:when>
												<c:otherwise>
													<img src="${pageContext.request.contextPath }/weichat/images/personimg.jpg" alt="" class="br50">
												</c:otherwise>
											</c:choose>
										</span>
										<span class="orange personname">${httpActivityComment.httpUser.nickName }</span>
										<p>${httpActivityComment.content }</p>
									</li>
								</c:forEach>
							</ul>
							<c:choose>
								<c:when test="${httpActivity.commentCounts > 2}">
									<h3 class="mt10 text-center">
										<a href="javascript:void(0);" class="click-more" onclick="showMore1(this);">
											<span>展开更多评论</span>
										</a>
									</h3>
								</c:when>
								<c:otherwise>
									<h3 class="mt10 text-center" style="display: none;" id="comment1">
										<a href="javascript:void(0);" class="click-more" onclick="showMore1(this);">
											<span>展开更多评论</span>
										</a>
									</h3>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="bbs-comment">
						<p class="gray mb10">我要评论：</p>
						<textarea name="" id="comment"></textarea>
						<div class="activity-btn">
							<a href="javascript:void(0);" onclick="submitComment();">发布</a>
						</div>
					</div>
					
					<div class="text-center share" style="padding-bottom: 50px;display: none;">
						<a href="javascript:;" class="bg-orange">
							<i class="icon-forward">发送给朋友</i>
						</a>
						<a href="javascript:;" class="bg-orange" style="margin-left: 25px;">
							<i class="icon-picassa">分享到朋友圈</i>
						</a>
					</div>
					<div class="attention">
						<h3><img style="width: 100%;" alt="${pageContext.request.contextPath }/weichat/images/1.gif" src="${pageContext.request.contextPath }/weichat/images/1.gif"></h3>
					</div>
				</div>
			</div>
		</div>
</section>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<fytec:wechatShareTag url="http://www.headline.cc${pageContext.request.contextPath }/weichatActivity/share/${type}/${code}/${httpActivity.id}?from=singlemessage&isappinstalled=0"/><script>
function weChat(){
	$("#mcover").css("display","none");  // 点击弹出层，弹出层消失
}
	
$(function($) {
	
	var pageUrl = window.location.href.split('#')[0];  
	console.info(pageUrl);
	pageUrl = pageUrl.replace(/\&/g, '%26');
	$.ajax({
		url : '${pageContext.request.contextPath }/weichatActivity/createSignature?url='+pageUrl,
		cache : false,
		dataType : "json",
		success : function(r) {
			if (r.success) {
				wx.config({  
		            debug: false,  
		            appId: r.obj.appId,  
		            timestamp: r.obj.timestamp,  
		            nonceStr: r.obj.nonceStr,  
		            signature: r.obj.signature,  
		            jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage']  
		        });  
			}
		},
		error:function(data){
			showMsg("登录失败，请稍候再试！");
		}
	});
	
	wx.ready(function () {
		  // 2. 分享接口
		  // 2.1 监听“分享给朋友”，按钮点击、自定义分享内容及分享结果接口
			pageUrl = pageUrl.substr(0, pageUrl.indexOf('%26'));  
		    wx.onMenuShareAppMessage({
		      title: '微笑社区，发现身边更多美好',
		      desc: '${httpActivity.name }',
		      link: pageUrl,
		      imgUrl: 'http://www.headline.cc${pageContext.request.contextPath }/weichat/images/logo.jpg',
		      success: function () {
		    	  $.get("${pageContext.request.contextPath}/weichatActivity/shared/${httpActivity.id}");
		    	  
		        //alert('已分享');
		      },
		      cancel: function () {
		        //alert('已取消');
		      }
		    });
		 
		  // 2.2 监听“分享到朋友圈”按钮点击、自定义分享内容及分享结果接口
		
		    wx.onMenuShareTimeline({
		      title: '${httpActivity.name }',
		      link: pageUrl,
		      imgUrl: 'http://www.headline.cc${pageContext.request.contextPath }/weichat/images/logo.jpg',
		      success: function () {
		    	  $.get("${pageContext.request.contextPath}/weichatActivity/shared/${httpActivity.id}");
		    	  
		        //alert('已分享');
		      },
		      cancel: function () {
		        //alert('已取消');
		      }
		    });
		   

		});
	});
</script>
<script type="text/javascript">
	function showMore(obj){
		var objs=$('#line-list li:gt(1)');
		for(var i=0;i<objs.length;i++){
			$(objs[i]).show();
		}
		
		$(obj).attr('onclick','hideMore(this);');
		$(obj).html('<span>隐藏更多报名</span>');
	}
	
	function hideMore(obj){
		var objs=$('#line-list li:gt(1)');
		for(var i=0;i<objs.length;i++){
			$(objs[i]).hide();
		}
		
		$(obj).attr('onclick','showMore(this);');
		$(obj).html('<span>显示更多报名</span>');
	}
	
	function submitComment(){
		if($('#comment').val()=='' || $('#comment').val()==null){
			alert('请输入评论内容！');
			return;
		}
		$.ajax({
			url : '${pageContext.request.contextPath }/weichatActivity/submitComment',
			cache : false,
			dataType : "json",
			data:{
				activityId:'${httpActivity.id }',
				content:$('#comment').val(),
				openId:'${httpUser.openid }',
				nickName :'${httpUser.nickName }',
				address:'${httpUser.address }',
				headimgurl:'${httpUser.headimgurl }'
			},
			success : function(r) {
				var httpActivityCommentList = r.obj;
				//var httpActivityComment=
					$('#line-list1').html("<li class='divider gray'><small class='orange'>"+httpActivityCommentList.length+"</small>&nbsp;评论</li>");
				for(var i=0;i<httpActivityCommentList.length;i++){
					var httpActivityComment=httpActivityCommentList[i];
					var httpUser=httpActivityComment.httpUser;
					
					var $li="<li>";
					if(i>1){
						$li="<li style='display:none;'>"
					}
					$li+="<span class='fr text-center gray'><small>"+httpActivityComment.createDate+"</small></span>"+
					"<span class='personimg'><img src='${pageContext.request.contextPath }"+httpUser.imageUrl+"' alt='' class='br50'></span>"+
					"<span class='orange personname'>"+httpUser.nickName+"</span>"+
					"<p>"+httpActivityComment.content+"</p>"
					+"</li>";
					$('#line-list1').append($li);
					$('#comment').val('');
				}
				
				if(httpActivityCommentList.length>2){
					$('#comment1').show();
				}
			}
		});
	}

		function showMore1(obj){
			var objs=$('#line-list1 li:gt(2)');
			for(var i=0;i<objs.length;i++){
				$(objs[i]).show();
			}
			
			$(obj).attr('onclick','hideMore1(this);');
			$(obj).html('<span>隐藏更多评论</span>');
		}
		
		function hideMore1(obj){
			var objs=$('#line-list1 li:gt(2)');
			for(var i=0;i<objs.length;i++){
				$(objs[i]).hide();
			}
			
			$(obj).attr('onclick','showMore1(this);');
			$(obj).html('<span>显示更多评论</span>');
		}
</script>
</html>