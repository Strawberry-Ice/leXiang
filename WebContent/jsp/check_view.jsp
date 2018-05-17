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
								活动审核
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
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 名称 </label>

										<div class="col-xs-12 col-sm-9">
											<div class="clearfix">
												<div style="margin-top: 8px;">
												${httpActivity.name }
												</div>
											</div>
										</div>
										</div>
										
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 报名开始时间 </label>
	
										<div class="col-xs-12 col-sm-9">
											<div class="clearfix">
												<div style="margin-top: 8px;">
												${httpActivity.applicationStartDates }
												</div>
											</div>
										</div>
										</div>
										
										<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 报名截止时间 </label>
	
										<div class="col-xs-12 col-sm-9">
											<div class="clearfix">
												<div style="margin-top: 8px;">
												${httpActivity.applicationEndDates }
												</div>
											</div>
										</div>
										</div>
									
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-5">内容</label>

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
											<button class="btn btn-info" type="button" onclick="agree();">
												<i class="ace-icon fa fa-check bigger-110"></i> 通过
											</button>
											&nbsp; &nbsp; &nbsp;
											<a class="btn btn-danger" href="${pageContext.request.contextPath }/activity/check/${code }">
												<i class="ace-icon fa fa-remove bigger-110"></i>
												返回
											</a>
											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="button" onclick="reject();">
												<i class="ace-icon fa fa-undo bigger-110"></i> 拒绝
											</button>
										</div>
									</div>
									
									
								</form:form>
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div>
			</div><!-- /.main-content -->
			
			<script type="text/javascript">
			function agree(){
				if(confirm("确定要通过吗？")){
					$.ajax({
						url : '${pageContext.request.contextPath }/activity/agreeForActivity/${httpActivity.id }',
						cache : false,
						dataType : "json",
						success : function(r) {
							localtion.href='${pageContext.request.contextPath }/activity/check/${code }';
						}
					});
				}else{
					return;
				}
			}
			
			function reject(){
				if(confirm("确定要拒绝吗？")){
					$.ajax({
						url : '${pageContext.request.contextPath }/activity/rejectForActivity/${httpActivity.id }',
						cache : false,
						dataType : "json",
						success : function(r) {
							location.href='${pageContext.request.contextPath }/activity/check/${code }';
						}
					});
				}else{
					return;
				}
			}
			</script>
