<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		<div class="page" style="padding-bottom: 20px;">
			<div id="mcover" onclick="weChat()" style="display:none;">
	          <img src="${pageContext.request.contextPath }/weichat/images/weichat.png" />
		   </div>
			<div class="mt10 mb10" style="margin-top: 48px;">
				<div class="activity-content">
					<div class="title">
						<h2 class="ellipsis" style="white-space: normal;width:100%;">${httpActivity.name }</h2>
					</div>
					<div class="gray ellipsis activity-status">
						<small>发布人:&nbsp;${httpActivity.frontName }</small>
					</div>
					<div class="gray ellipsis activity-status text-right"
>						<small>发布时间：&nbsp;${httpActivity.createDates }</small>
						<small>阅读&nbsp;${httpActivity.readCounts }</small>
						<small>分享&nbsp;${httpActivity.shardCounts }</small>
					</div>
					<div class="activity-body">
						<c:if test="${not empty httpActivity.imgUrl}">
							<img id="my_img" width="334px" height="324px" src="<fytec:image url="${httpActivity.imgUrl }" size="242_253"/>" alt=""><br>
						</c:if>
						${httpActivity.content }
					</div>
					<c:if test="${not empty httpActivity.score && httpActivity.score!=0}">
						<div class="activity-meta">
							<p>积          分：${httpActivity.score }</p>
						</div>
					</c:if>
					<div class="actors">
						<div class="list-actor">
							<ul class="line-list" id="line-list">
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
										<a href="javascript:void(0);" class="click-more" onclick="showMore(this);">
											<span>展开更多评论</span>
										</a>
									</h3>
								</c:when>
								<c:otherwise>
									<h3 class="mt10 text-center" style="display: none;" id="comment1">
										<a href="javascript:void(0);" class="click-more" onclick="showMore(this);">
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
					<div class="text-center share" style="padding-bottom: 50px;">
						<a href="javascript:;" class="bg-orange">
							<i class="icon-forward">发送给朋友</i>
						</a>
						<a href="javascript:;" class="bg-orange" style="margin-left: 25px;">
							<i class="icon-picassa">分享到朋友圈</i>
						</a>
					</div>
				</div>
			</div>
		</div>
	</section>

<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<fytec:wechatShareTag url="http://www.headline.cc${pageContext.request.contextPath }/weichatActivity/view/${type}/${code}/${httpActivity.id}"/>
<script type="text/javascript">

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
			      link:'https://open.weixin.qq.com/connect/oauth2/authorize?&appid=wxf6e18731811efcca&redirect_uri=http%3a%2f%2fwww.headline.cc%2fsmilecommunity%2fweichatActivity%2fshare1%2f${type}%2f${code}%2f${httpActivity.id}&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect',
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
			      link:'https://open.weixin.qq.com/connect/oauth2/authorize?&appid=wxf6e18731811efcca&redirect_uri=http%3a%2f%2fwww.headline.cc%2fsmilecommunity%2fweichatActivity%2fshare1%2f${type}%2f${code}%2f${httpActivity.id}&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect',
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
				content:$('#comment').val()
				
			},
			success : function(r) {
				var httpActivityCommentList = r.obj;
				//var httpActivityComment=
					$('#line-list').html("<li class='divider gray'><small class='orange'>"+httpActivityCommentList.length+"</small>&nbsp;评论</li>");
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
					$('#line-list').append($li);
					$('#comment').val('');
				}
				
				if(httpActivityCommentList.length>2){
					$('#comment1').show();
				}
			}
		});
	}


	function showMore(obj){
		var objs=$('#line-list li:gt(2)');
		for(var i=0;i<objs.length;i++){
			$(objs[i]).show();
		}
		
		$(obj).attr('onclick','hideMore(this);');
		$(obj).html('<span>隐藏更多评论</span>');
	}
	
	function hideMore(obj){
		var objs=$('#line-list li:gt(2)');
		for(var i=0;i<objs.length;i++){
			$(objs[i]).hide();
		}
		
		$(obj).attr('onclick','showMore(this);');
		$(obj).html('<span>显示更多评论</span>');
	}
</script>
