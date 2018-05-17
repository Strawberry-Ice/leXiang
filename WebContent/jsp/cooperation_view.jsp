<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div class="main-content">
				<div class="main-content-inner">
					<div class="page-content">
						<!-- #section:settings.box -->

						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
								二手管理
								<small>
									<i class="ace-icon fa fa-angle-double-right"></i>
									查看
								</small>
							</h1>
						</div>
						
						
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form:form method="post" modelAttribute="httpActivity" action="${pageContext.request.contextPath }/activity/" cssClass="form-horizontal" id="activityForm">
									<!-- #section:elements.form -->
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 互助名称 </label>

										<div class="col-xs-12 col-sm-9">
											<div class="clearfix">
												<div style="margin-top: 8px;">
												${httpActivity.name }
												</div>
											</div>
										</div>
										</div>
									
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-5">互助内容</label>

										<div class="col-sm-9">
											<div style="margin-top: 8px;">
											${httpActivity.content }
											</div>
										</div>
									</div>
									<input type="hidden" name="id" value="${httpActivity.id }"/>
									<script type="text/javascript">
										var $path_assets = "../assets";//this will be used in loading jQuery UI if needed!
									</script>
									
									
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											&nbsp; &nbsp; &nbsp;
											<a class="btn btn-danger" href="${pageContext.request.contextPath }/activity/activity/${code }">
												<i class="ace-icon fa fa-remove bigger-110"></i>
												取消
											</a>
											&nbsp; &nbsp; &nbsp;
										</div>
									</div>
									
									
								</form:form>
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div>
			</div><!-- /.main-content -->
