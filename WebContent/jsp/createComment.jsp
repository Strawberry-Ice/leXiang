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
					创建评论<small> <i class="ace-icon fa fa-angle-double-right"></i>
					</small>
				</h1>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<form:form method="post" modelAttribute="httpActivityComments"
						action="${pageContext.request.contextPath }/tools/submitComment/${code }"
						cssClass="form-horizontal" id="activityForm">
						<!-- #section:elements.form -->

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 评论 </label>
							<div class="col-sm-9">
								<form:textarea path="content" cssClass="col-xs-10 col-sm-5" />
							</div>
						</div>
							
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-5">创建人</label>

							<div class="col-sm-9">
								<div class="clearfix">
									<select class="col-xs-10 col-sm-5" id="form-field-select-1" name="userId">
										<c:forEach var="user" items="${users }">
											<option value="${user.id }">${user.nickName }</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<input type="hidden" name="activityId" value="${activityId }"/>
						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
								<button class="btn btn-info" type="button" onclick="submitForm();">
									<i class="ace-icon fa fa-check bigger-110"></i> 提交评论
								</button>
								&nbsp; &nbsp; &nbsp;
							</div>
						</div>
					</form:form>
					
					
				</div>
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page-content -->
	</div>
</div>
<!-- /.main-content -->


<script type="text/javascript">
	function submitForm(){
		$('#activityForm').submit();
	}
</script>