<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN" ng-app="webapp">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <title>微笑生活</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/weichat/shop/CSS/bootstrap.min.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/weichat/shop/CSS/waitMe.min.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/weichat/shop/CSS/style.css"/>
    <script src="${pageContext.request.contextPath }/assets/js/jquery.js"></script>
</head>
<body ng-controller="submitPageCtrl">
    <div class="container-fluid submitpage">
        <div class="row">
        </div>

        <div class="row">
            <div class="col-xs-12">
                <div class="submitText">
                    <input type="text" placeholder="请填写SN码" class="col-xs-9 " ng-model="submitText" name="key" id="key"/>
                    <button class="btn btn-default pull-right"
                            onclick="check()"><i class="glyphicon glyphicon-search"></i></button>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-xs-12" ng-show="result">
                <p>查询结果</p>
                <div class="col-xs-12 submitresult">
                    <span ng-bind="ssnn" id="msg1"></span><br/>
                    <span ng-bind="result.msg" id="msg2"></span>
                </div>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
	function check(){
		$.ajax({
			url : '${pageContext.request.contextPath }/weichatShop/valid?key='+$('#key').val(),
			cache : false,
			dataType : "json",
			success : function(r) {
				if (r.success) {
					$('#msg1').html(r.obj);
					$('#msg2').html(r.msg);
				}
			}
		});
	}
</script>
</html>