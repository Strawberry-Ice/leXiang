<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection"content="telephone=yes, email=yes" />
    <title><tiles:getAsString name="title" ignore="true"/></title>
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
<body <c:if test="${not empty errMsg}">onload="showError();"</c:if>>
	
	<tiles:insertAttribute name="header" />
	<%--<c:if test="${code=='05'}">
		<style>
			.list-ershou {}
			.list-ershou img {border: 1px solid #f27c25; width:40px; height: 40px;}
			.ershou-cate {margin-top: 5px;top:44px;background-color: #fff;}
			.ershou-cate li {display: inline-block; width: 24%; height: 44px;line-height: 42px; margin-bottom:5px; border: 1px solid orange;text-align: center;}
			.ershou-cate li a {display:block;color:#f60;}
			.ershou-cate li a.current,.ershou-cate li a:hover {background-color:#f8e5d3;}
			#main.wrap-page {padding-top: 135px;}
		</style>
		<section class="ershou-cate fixed-top">
				<ul>
					<c:forEach var="categorys" items="${httpCategoryList }">
						<c:if test="${not empty categorys.children }">
							<c:forEach var="category" items="${categorys.children }">
								<li>
									<c:choose>
										<c:when test="${category.code == goodsType }">
											<a class="current" href="${pageContext.request.contextPath }/weichatActivity/listAllActivity/${type}/${categorys.code}?goodsType=${category.code }">
												${category.name }
											</a>
										</c:when>
										<c:otherwise>
											<a href="${pageContext.request.contextPath }/weichatActivity/listAllActivity/${type}/${categorys.code}?goodsType=${category.code }">
												${category.name }
											</a>
										</c:otherwise>
									</c:choose>
								</li>
							</c:forEach>
						</c:if>
					</c:forEach>
			</ul>
		</section>
	</c:if> --%>
	<tiles:insertAttribute name="footer" />
	<!--<section id="main" class="wrap-page">  -->
	<tiles:insertAttribute name="content" />
	<!--</section>  -->
</body>
</html>