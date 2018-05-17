<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fytec"  uri="/fengyuntec.com" %>
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
<section id="main" class="wrap-page">
		<div class="page">
		 	<div id="mcover" onclick="weChat()" style="display:none;">
	          <img src="${pageContext.request.contextPath }/weichat/images/weichat.png" />
		   	</div>
			<div id="content" class="mb10" style="margin-top: 48px;">
				<div class="article-content">
					<h2 class="ellipsis" style="white-space: normal;width:100%;">${httpActivity.name }</h2>
					<div class="gray ellipsis activity-status">
						<small>发布人:&nbsp;${httpActivity.frontName }</small>
					</div>
					<div class="gray text-right meta">
						<small>${httpActivity.createDates }</small>
						<small>阅读&nbsp;${httpActivity.readCounts }</small>
						<small>分享&nbsp;${httpActivity.shardCounts }</small>
					</div>
					<div class="article-body">
						${httpActivity.content }
						<c:if test="${not empty httpActivity.url }">
						<br><a href="${httpActivity.url }">${httpActivity.url }</a>
						</c:if>
					</div>
					<div class="text-center share" style="padding-bottom: 50px;">
						<a href="javascript:;" class="bg-orange">
							<i class="icon-forward">发送给朋友</i>
						</a>
						<a href="javascript:;" class="bg-orange" style="margin-left: 25px;">
							<i class="icon-picassa">分享到朋友圈</i>
						</a>
					</div>
					<div class="attention" style="display: none;">
						<h3>点击关注&nbsp;"<a href="">园区微笑生活</a>"</h3>
					</div>
				</div>
			</div>
		</div>
		
</section>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<fytec:wechatShareTag url="http://www.headline.cc${pageContext.request.contextPath }/weichatActivity/view/${type}/${code}/${httpActivity.id}"/>
<script>
function weChat(){
	$("#mcover").css("display","none");  // 点击弹出层，弹出层消失
}
	
$(function($) {
	
	$(".share a").each(function(i){
		$(this).click( function () {
			$("#mcover").css("display","block")    // 分享给好友按钮触动函数
		});
	 });
	
	wx.ready(function () {
		  // 2. 分享接口
		  // 2.1 监听“分享给朋友”，按钮点击、自定义分享内容及分享结果接口

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

