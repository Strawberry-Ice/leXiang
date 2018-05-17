<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- /section:basics/sidebar -->
<div class="main-content">
	<div class="main-content-inner">
		<div class="page-content">
			<div class="page-header">
				<h1>
					积分管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
						礼品兑换
					</small>
				</h1>
			</div>


			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<form class="form-horizontal" action="${pageContext.request.contextPath }/score/shop/exchange/valid" method="post" role="form">
						<!-- #section:elements.form -->
						<div class="form-group">
							<div class="col-sm-3"></div>
							<div class="col-sm-9">
								<input type="text" id="form-field-1" name="key" placeholder="兑换码"
									class="col-xs-10 col-sm-5" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button class="btn btn-info" type="submit">
									<i class="ace-icon fa fa-check bigger-110"></i> 验证
								</button>
							</div>

						</div>
					</form>

					<div class="row">
						<div class="col-xs-12">
							<table id="simple-table"
								class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>兑换码</th>
										<th>兑换人员</th>
										<th>奖品名称</th>
										<th>验证时间</th>
										<th>状态</th>
									</tr>
								</thead>
								<tbody>
									<tr>
									<c:if test="${!empty record}">
										<td>${record.coupon }</td>
										<td>${record.exchanger.nickName }</td>
										<td>${record.scoreShop.name }</td>
										<td><fmt:formatDate value="${record.verifyDate}" type="date" pattern="yyyy-MM-dd"/></td>
										<td>
											<c:choose>
												<c:when test="${record.status==true}">
												已兑换
												</c:when>
												<c:otherwise>
												未兑换
												</c:otherwise>
											</c:choose>
										</td>
									</c:if>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page-content -->
	</div>
</div>
<!-- /.main-content -->
<script>
if('${msg}'){
	alert('${msg}');
}
</script>