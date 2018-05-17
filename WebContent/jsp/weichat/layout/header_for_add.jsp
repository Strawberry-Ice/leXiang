<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav id="head" class="header fixed-top article-top">
	<span class="fr"><a href="${pageContext.request.contextPath }/weichatActivity/createActivity/${type}/${code}" class="btn"><i class="icon-add"></i><span>我要发布</span></a></span>
	<h4 >
	<span onclick="menu();">
		<c:forEach var="categorys" items="${httpCategoryList }">
			<c:if test="${categorys.code == code }">
				<i class="${categorys.logo }">
				</i><span>${categorys.name }</span>
			</c:if>
		</c:forEach>
		<i class="icon-arrow-down"></i></span>
	</h4>
	<ul class="swiper-wrapper" style="display: none;margin-top: -1px;margin-left: -4px; text-align: center;" id="menu">
		<c:forEach var="categorys" items="${httpCategoryList }">
			<li class="swiper-slide" style="width: 86px;background-color: #f27c25;">
				<a href="${pageContext.request.contextPath }/weichatActivity/listAllActivity/${type}/${categorys.code}">
					<i class="${categorys.logo }"></i>
					<span>${categorys.name }</span>
				</a>
			</li>
		</c:forEach>
	</ul>
</nav>
<script>
	function menu(){
		
		var ui = document.getElementById("menu");
		if(ui.style.display=="block"){
			ui.style.display="none";
		}else{
			ui.style.display="block";
		}
		
	}
</script>