<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav id="head" class="header fixed-top">
<div class="swiper-container">
	<ul class="swiper-wrapper">
		<c:forEach var="categorys" items="${httpCategoryList }">
			<c:if test="${categorys.code == code }">
				<li class="swiper-slide"><a href="${pageContext.request.contextPath }/weichatActivity/listAllActivity/${type}/${categorys.code}" class="on"><i class="${categorys.logo }"></i><span>${categorys.name }</span></a></li>
			</c:if>
			<c:if test="${categorys.code != code }">
				<li class="swiper-slide"><a href="${pageContext.request.contextPath }/weichatActivity/listAllActivity/${type}/${categorys.code}"><i class="${categorys.logo }"></i><span>${categorys.name }</span></a></li>
			</c:if>
			
		</c:forEach>
		<!-- class="on" -->
	</ul>
</div>
</nav>


<script src="<%=request.getContextPath()%>/weichat/js/swiper.min.js" type="text/javascript"></script>
<script>
var swiper = new Swiper('.swiper-container', {
    slidesPerView: 4,
    spaceBetween: 10,
    freeMode: true
});
</script>