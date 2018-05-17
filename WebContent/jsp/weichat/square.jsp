<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<section id="main" class="wrap-page">
		<div class="page">
			<div class="mt10 mb10" style="margin-bottom: 40px;">
				<ul class="list-activity" style="margin-top: 42px;">
					<c:forEach var="httpActivity" items="${httpActivityList }">
							<li>
								<a <c:choose><c:when test="${httpActivity.needUrl }">href="${httpActivity.url}"</c:when><c:otherwise>href="${pageContext.request.contextPath }/weichatActivity/view/${type}/${httpActivity.type}/${httpActivity.id}"</c:otherwise></c:choose>>
									<div class="fr text-center">
										<span class="badge bg-purple br50"><i class="${httpActivity.logo}"></i></span>
										<c:if test="${httpActivity.type=='04' or httpActivity.type=='03'}">
										<p class="gray"><small>${httpActivity.applyNumber}报名</small></p>
										</c:if>
										<c:if test="${httpActivity.type=='02'}">
										<p class="gray"><small>${httpActivity.readCount}阅读</small></p>
										</c:if>
									</div>
									<div class="text">
										<h3 class="ellipsis">${httpActivity.name}</h3>
										<div class="status">
											<div class="gray community-name">
												<small>
													${httpActivity.frontName}
												</small>
											</div>
											<c:if test="${not empty httpActivity.applicationStatus}">
												<div class="badge ${httpActivity.applicationColor}"><small>${httpActivity.applicationStatus}</small></div>
											</c:if>
											<c:if test="${not empty httpActivity.uneatenTime and not empty httpActivity.uneatenTime}">
												<div class="countdown">
													<p><small class="gray">剩余时间：</small><small class="red">${httpActivity.uneatenTime}</small></p>
													<p><small class="gray">剩余名额：</small><small class="red">${httpActivity.uneatenNumber}</small></p>
												</div>
											</c:if>
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

<div id="navigation">
	<c:if test="${not empty nextParam}">
   		<a href="${pageContext.request.contextPath }/weichatActivity/listAllActivity/${type }/${code }?${nextParam}">下一页</a>
   	</c:if>
 </div>



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

        });
        
        function showError(){
        	if(confirm("${errMsg}确定要绑定社区吗？")){
    			location.href='https://open.weixin.qq.com/connect/oauth2/authorize?&appid=${appid}&redirect_uri=${basePath}&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect';
    		}
        }
        
        
       

</script>