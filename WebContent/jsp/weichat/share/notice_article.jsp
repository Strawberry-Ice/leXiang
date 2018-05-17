<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			<div id="content" class="mb10">
				<div class="article-content">
					<h2 class="ellipsis" style="white-space: normal;width:100%;">${httpActivity.name }</h2>
					<div class="gray ellipsis activity-status">
						<small>${httpActivity.frontName }</small>
					</div>
					<div class="gray text-right meta">
						<small>${httpActivity.createDates }</small>
						<small>阅读&nbsp;${httpActivity.readCounts }</small>
						<small>分享&nbsp;${httpActivity.shardCounts }</small>
					</div>
					<div class="article-body">
						${httpActivity.content }
					</div>
					<div class="text-center share" style="padding-bottom: 50px;">
						<a href="javascript:;" class="bg-orange">
							<i class="icon-forward">发送给朋友</i>
						</a>
						<a href="javascript:;" class="bg-orange" style="margin-left: 25px;display: none;">
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
<script>
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
	$(".share a").each(function(i){
		$(this).click( function () {
			$("#mcover").css("display","block")    // 分享给好友按钮触动函数
		});
	 });
	
	wx.ready(function () {
		  // 2. 分享接口
		  // 2.1 监听“分享给朋友”，按钮点击、自定义分享内容及分享结果接口
			pageUrl = pageUrl.substr(0, pageUrl.indexOf('%26'));  
		    wx.onMenuShareAppMessage({
		      title: '微笑社区，发现身边更多美好',
		      desc: '${httpActivity.name }',
		      link: 'http://www.headline.cc${pageContext.request.contextPath }/weichatActivity/share/${type}/${code}/${httpActivity.id}',
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
		      link: 'http://www.headline.cc${pageContext.request.contextPath }/weichatActivity/share/${type}/${code}/${httpActivity.id}',
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
</html>
