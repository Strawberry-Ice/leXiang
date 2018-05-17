<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- /section:basics/sidebar -->
<div class="main-content">
	<div class="main-content-inner">
		<div class="page-content">
			<!-- #section:settings.box -->

			<!-- /section:settings.box -->
			<div class="page-header">
				<h1>
					积分管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
						积分查询
					</small>
				</h1>
			</div>


			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->

					<h4 class="pink">
						<i
							class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
						<a href="#modal-table" role="button" class="green"
							data-toggle="modal">积分使用状态</a>
					</h4>

					<div class="row">
						<div class="col-xs-12">
							<table id="simple-table"
								class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>管理员积分池</th>
										<th>划拨积分</th>
										<th>积分回收</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>${adminScorePool.balance }</td>
										<td>${adminScorePool.dispensedTotalScore }</td>
										<td>${adminScorePool.recoveredTotalScore }</td>
									</tr>

								</tbody>
							</table>
						</div>
					</div>


					<div class="row">

						<div class="col-xs-3">
							<h4 class="pink">
								<i
									class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
								<a href="#modal-table" role="button" class="green"
									data-toggle="modal">街道（社工委）积分排行</a>
							</h4>
							<table id="simple-table"
								class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>街道（社工委）名称</th>
										<th>积分</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${govOrgs}" var="govOrg">
									<tr>
										<td>${govOrg.govOrg.name }</td>
										<td>${govOrg.balance }</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="col-xs-1"></div>
						<div class="col-xs-3">
							<h4 class="pink">
								<i
									class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
								<a href="#modal-table" role="button" class="green"
									data-toggle="modal">社区积分排行</a>
							</h4>
							<table id="simple-table"
								class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>社区</th>
										<th>积分</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${communities}" var="community">
										<tr>
											<td>${community.govOrg.name }</td>
											<td>${community.balance }</td>
										</tr>
									</c:forEach>
									
								</tbody>
							</table>
						</div>
						<div class="col-xs-1"></div>
						<div class="col-xs-4">
							<h4 class="pink">
								<i
									class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
								<a href="#modal-table" role="button" class="green"
									data-toggle="modal">居民积分排行</a>
							</h4>
							<table id="simple-table"
								class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>用户民</th>
										<th>积分</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${users}" var="user">
										<tr>
											<td>${user.user.nickName }</td>
											<td>${user.balance }</td>
										</tr>
									</c:forEach>

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