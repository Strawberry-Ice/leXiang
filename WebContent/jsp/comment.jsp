<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fytec" uri="/fengyuntec.com"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div class="main-content">
	<div class="main-content-inner">
		<div class="page-content">
			<!-- #section:settings.box -->

			<!-- /section:settings.box -->
			<div class="page-header">
				<h1>
					评论管理<small> <i class="ace-icon fa fa-angle-double-right"></i>
					</small>
				</h1>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<table id="simple-table"
						class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>头像</th>
								<th>姓名</th>
								<th>内容</th>
								<th>时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="httpActivityComment" items="${hActivity.httpActivityComments }" varStatus="status">
								<tr>
									<td><span ><img src="${pageContext.request.contextPath }${httpActivityComment.httpUser.imageUrl }" alt="" class="br50" style="width: 32px;height: 32px;"></span></td>
									<td>${httpActivityComment.httpUser.nickName }</td>
									<td>${httpActivityComment.content }</td>
									<td>${httpActivityComment.createDate }</td>
									<td>
										<div class="hidden-sm hidden-xs btn-group">
											<a class="btn btn-xs btn-danger" href="${pageContext.request.contextPath }/activity/delComment/${code }/${activityId }/${httpActivityComment.id }">
												<i class="ace-icon fa fa-trash-o bigger-120"></i>
											</a>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							&nbsp; &nbsp; &nbsp; <a class="btn btn-danger"
								href="${pageContext.request.contextPath }/activity/activity/${code }">
								<i class="ace-icon fa fa-remove bigger-110"></i> 返回
							</a> &nbsp; &nbsp; &nbsp;
							
							<sec:authorize access="hasAnyRole('ROLE_Role_sys_admin')">
								<a class="btn btn-info"
									href="${pageContext.request.contextPath }/tools/createComment/${code }/${activityId }">
									<i class="ace-icon fa fa-check bigger-110"></i> 创建评论
								</a>
							</sec:authorize>
							
						</div>
					</div>
				</div>
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page-content -->
	</div>
</div>
<!-- /.main-content -->


<script type="text/javascript">
	function del(id){
		$.ajax({
			url : '${pageContext.request.contextPath }/activity/delComment/'+id,
			cache : false,
			dataType : "json",
			success : function(r) {
				alert('删除成功！');
			}
		});
	}
</script>