<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fytec" uri="/fengyuntec.com"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection"content="telephone=yes, email=yes" />
    <title>个人资料</title>
    <meta name="description" content="" />
	<meta name="keywords" content="" />
	<jsp:include page="weichart_js_and_css.jsp"></jsp:include>
	<style type="text/css">
		.upload {
		position:absolute;
		left:0;
		top:0;
		width:100%;
		height:100%;
		opacity: 0;
		display: block;
		z-index: 10;
		}
	</style>
</head>
<script type="text/javascript">
function imagesSelected(myFiles) {
  for (var i = 0, f; f = myFiles[i]; i++) {
    var imageReader = new FileReader();
    imageReader.onload = (function(aFile) {
      return function(e) {
       $('#my_img').attr('src',e.target.result);
      };
    })(f);
    imageReader.readAsDataURL(f);
  }
}
$(function($) {
	$("#forsubmit").click(function(){
	  $("#httpUser").submit();
  });
  // 你可以在这里继续使用$作为别名...
  $('#headImage').click(function (){
	 $(this).css("display","none"); 
	 $('.personimg-update').removeAttr("style");
	 $("#forsubmit1").css("display","block"); 
  });
  $('#nickName_dl').click(function (){
	 $(this).find("input").removeAttr("readonly"); 
	 $("#forsubmit1").css("display","block"); 
  });
  $('#tel_dl').click(function (){
	 $(this).find("input").removeAttr("readonly"); 
	 $("#forsubmit1").css("display","block"); 
  });
  $('#needReceive').click(function (){
		 $(this).find("input").removeAttr("readonly"); 
		 $("#forsubmit1").css("display","block"); 
	  });
  
  $(".show_info").each(function(i){
	  $(this).click(function (){
		window.location.href=$(this).find('a').attr('href');
	  });
  });
});

</script>
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
			<form:form action="${pageContext.request.contextPath }/weichatUpdateUserInfo" commandName="httpUser" enctype="multipart/form-data" class="update">
				<dl id="forsubmit1" style="background-color: black;height: 50px;display: none;">
					<dt></dt>
					<dd>
						<div id="forsubmit" class="activity-btn" style="position: relative;top: -9px;right: -125px;width: 50%;margin: 0px 0px;">
							<a href="#" style="display: inline;">保存</a>
						</div>
					</dd>
				</dl>
				<div class="activity-btn personimg-update" style="display: none;">
					<p style="position: relative;"><a href="#">更新我的头像</a><input name="file" type="file" accept="image/*" class="upload" onchange="imagesSelected(this.files)"/></p>
					<p class="text-right"><img id="my_img" width="38px" height="38px" src="<fytec:image url="${user.logo }" size="108_108"/>" onerror="javascript:this.src='${pageContext.request.contextPath }/weichat/images/personimg.jpg';" alt="" class="fr br50"></p>
				</div>
				<dl id="headImage">
					<dt>头像：</dt>
					<dd><p class="text-right"><img width="38px" height="38px" src="<fytec:image url="${user.logo }" size="108_108"/>" onerror="javascript:this.src='${pageContext.request.contextPath }/weichat/images/personimg.jpg';" alt="" class="br50"></p></dd>
				</dl>
				<dl id="nickName_dl">
					<dt>昵称：</dt>
					<dd><input type="text" name="nickName" value="${user.nickName }" readonly></dd>
				</dl>
				<dl id="needReceive">
					<dt>接收社区通知</dt>
					<dd class="text-right"><form:checkbox path="needReceive" cssStyle="width: 10%;height: 25px;"/></dd>
				</dl>
				<dl id="tel_dl">
					<dt>手机号：</dt>
					<dd><input type="text" name="tel" value="${user.tel }" readonly></dd>
				</dl>
				<dl>
					<dt>所在社区：</dt>
					<dd class="text-right">${user.govOrg.name }</dd>
				</dl>
				<dl class="show_info">
					<dt>爱心积分：</dt>
					<dd class="text-right"><a href="${pageContext.request.contextPath }/weichatScoreShop/findAllMyScoreRecords">${user.scorePool.balance }</a></dd>
				</dl>
				<dl class="show_info">
					<dt>兑换劵：</dt>
					<dd class="text-right"><a href="${pageContext.request.contextPath }/weichatScoreShop/findAllMyCoupons">查看</a></dd>
				</dl>
				<dl class="show_info">
					<dt>我发起的：</dt>
					<dd class="text-right"><a href="${pageContext.request.contextPath }/weichatShowMyActivity">查看</a></dd>
				</dl>
				<dl class="show_info">
					<dt>我参与的：</dt>
					<dd class="text-right"><a href="${pageContext.request.contextPath }/weichatShowMyApplications">查看</a></dd>
				</dl>
				<!--<div id="forsubmit" class="activity-btn" style="display: none;">
					<a href="#">保存</a>
				</div>-->
			</form:form>
		</div>
	</section>
</body>
</html>