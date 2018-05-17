<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>微笑生活</title>
    <style>
        *{
            padding: 0;
            margin: 0;
        }
        body{
            font-family: 微软雅黑;
            font-size: 14px;
            text-align: center;
            color: #7e7e7e;
            line-height: 20px;
            -webkit-appearance: none;
        }
        .main{
            width: 100%;
        }
        .middle{
            width: 120px;
            margin: 50% auto;
        }
        .middle .image{
            width: 110px;
            height: 110px;
            margin-left: 5px;
        }
        .middle img{
            width: 100%;
            height: 100%;
        }
        .middle .text{
            margin-top: 10px;
        }
        .bottom{
            width: 100%;
            position: absolute;
            bottom: 25px;
        }
    </style>
</head>
<body>
<div class="main">
    <div class="middle">
        <div class="image"><img src="${pageContext.request.contextPath }/weichat/images/smile.png"></div>
        <div>发现身边更多美好</div>
    </div>
</div>
<div class="bottom">
    <div>微笑科技 ©2015 headline.cc</div>
</div>
</body>
<script src="${pageContext.request.contextPath }/assets/js/jquery.js"></script>
<script type="text/javascript">
	$(function(){
		setTimeout(setUrl,2000)
	})

	function setUrl(){
		//location.href='${pageContext.request.contextPath }/weichatMyCommunity?code=${code}';
		location.href='https://open.weixin.qq.com/connect/oauth2/authorize?&appid=wxf6e18731811efcca&redirect_uri=http%3a%2f%2fwww.headline.cc%2fsmilecommunity%2fweichatMyCommunity&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect';
	}
</script>
</html>