<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section id="main" class="wrap-page">
		<div class="page">
			<div class="mt10 mb10">
				<ul class="list-activity" style="margin-top: 42px;">
					<c:forEach var="httpActivity" items="${httpActivityList }">
							<li>
								<a href="${pageContext.request.contextPath }/weichatActivity/view/${type}/${httpActivity.type}/${httpActivity.id}">
									<div class="fr text-center">
										<span class="badge bg-purple br50"><i class="${httpActivity.logo}"></i></span>
										<c:choose>
											<c:when test="${httpActivity.type=='06' or httpActivity.type=='05'}">
											   <p class="gray">
													<small>
														阅读数：${httpActivity.commentCounts}
													</small>
												</p>
											</c:when>
											<c:when test="${httpActivity.type=='03' or httpActivity.type=='04'}">
												<p class="gray">
													<small>
														${httpActivity.applyNumber}报名
													</small>
												</p>
											</c:when>
										</c:choose>
										
									</div>
									<div class="text">
										<h3 class="ellipsis">${httpActivity.name}</h3>
										<div class="status">
											<div class="gray community-name">
												<small>
													${httpActivity.frontName}
												</small>
											</div>
											<c:choose>
												<c:when test="${httpActivity.type=='05'}">
													<c:if test="${not empty httpActivity.goodsStatus}">
														<div class="badge bg-red"><small>${httpActivity.goodsStatus}</small></div>
													</c:if>
													<c:if test="${not empty httpActivity.goodsTypeName}">
														<div class="countdown">
															<p><small class="gray">${httpActivity.goodsTypeName}</small></p>
														</div>
													</c:if>
												</c:when>
												<c:otherwise>
														<c:if test="${not empty httpActivity.applicationStatus}">
															<div class="badge ${httpActivity.applicationColor}"><small>${httpActivity.applicationStatus}</small></div>
														</c:if>
														<c:if test="${not empty httpActivity.uneatenTime and not empty httpActivity.uneatenTime}">
															<div class="countdown">
																<p><small class="gray">剩余时间：</small><small class="red">${httpActivity.uneatenTime}</small></p>
																<p><small class="gray">剩余名额：</small><small class="red">${httpActivity.uneatenNumber}</small></p>
															</div>
														</c:if>
												</c:otherwise>
											</c:choose>
											
										</div>
									</div>
								</a>
							</li>
					</c:forEach>
				</ul>
			</div>
		</div>
</section>

<div id="navigation">
	<c:if test="${not empty nextParam}">
   		<a href="${pageContext.request.contextPath }/weichatActivity/listAllActivity2/${type }/${code }?${nextParam}">下一页</a>
   	</c:if>
 </div>
