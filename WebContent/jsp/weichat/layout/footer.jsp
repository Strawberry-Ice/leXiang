<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${type=='1' }"></c:if>
<footer id="foot" class="footer fixed-bottom">
<ul class="nav-primary nav-primary-mix">
	<li><a href="${pageContext.request.contextPath }/weichatActivity/activity/1" <c:if test="${type=='1' }">class="current"</c:if>><i class="icon-chat"></i><p>我的社区</p></a></li>
	<li><a href="${pageContext.request.contextPath }/weichatActivity/activity/2" <c:if test="${type=='2' }">class="current"</c:if>><i class="icon-flag"></i><p>附近有啥</p></a></li>
	<li><a href="${pageContext.request.contextPath }/weichatShowUserInfo"><i class="icon-user"></i> <p>个人中心</p> </a> </li>
	<li><a href="${pageContext.request.contextPath }/weichatScoreShop/listAllScoreItems"><i class="icon-cart"></i><p>积分兑换</p></a></li>
</ul>
</footer>
