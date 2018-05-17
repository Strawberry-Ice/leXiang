<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- /section:basics/sidebar -->
<div class="main-content">
	<div class="main-content-inner">
		<div class="page-content">
			<!-- #section:settings.box -->
			<div class="ace-settings-container" id="ace-settings-container">

			</div>
			<!-- /.ace-settings-container -->

			<!-- /section:settings.box -->
			<div class="page-header">
				<h1>个人信息</h1>
			</div>
			<!-- /.page-header -->

			<div class="row">
				<div class="col-xs-12">

					<div class="hr dotted"></div>

					<div>
						<div id="user-profile-3" class="user-profile row">
							<div class="col-sm-offset-1 col-sm-10">

								<div class="space"></div>

								<form:form class="form-horizontal"
									action="${pageContext.request.contextPath }/user/changePassword"
									commandName="userPasswordValid">
									<div class="tabbable">
										<ul class="nav nav-tabs padding-16">
											<li><a
												href="${pageContext.request.contextPath }/user/profile">
													<i class="green ace-icon fa fa-pencil-square-o bigger-125"></i>
													基本信息
											</a></li>

											<li class="active"><a
												href="${pageContext.request.contextPath }/user/password"
												aria-expanded="true"> <i
													class="blue ace-icon fa fa-key bigger-125"></i> 修改密码
											</a></li>
										</ul>

										<div class="tab-content profile-edit-tab-content">

											<div id="edit-password" class="tab-pane in active">
												<div class="space-10"></div>

												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"
														for="form-field-pass1">旧密码</label>

													<div class="col-sm-9">
														<form:password path="oldPassword" id="form-field-1"
															placeholder="旧密码" cssClass="col-xs-10 col-sm-5" />
														<span class="help-inline col-xs-12 col-sm-7"> <span
															class="middle"><font color="red"><form:errors
																		path="oldPassword" /></font></span>
														</span>
													</div>
												</div>

												<div class="space-4"></div>

												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"
														for="form-field-pass1">新密码</label>

													<div class="col-sm-9">
														<form:password path="password" id="form-field-1"
															placeholder="新密码" cssClass="col-xs-10 col-sm-5" />
														<span class="help-inline col-xs-12 col-sm-7"> <span
															class="middle"><font color="red"><form:errors
																		path="password" /></font></span>
														</span>
													</div>
												</div>

												<div class="space-4"></div>

												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"
														for="form-field-pass2">确认密码</label>

													<div class="col-sm-9">
														<form:password path="password2" id="form-field-1"
															placeholder="确认密码" cssClass="col-xs-10 col-sm-5" />
														<span class="help-inline col-xs-12 col-sm-7"> <span
															class="middle"><font color="red"><form:errors
																		path="password2" /></font></span>
														</span>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="submit">
												<i class="ace-icon fa fa-check bigger-110"></i> 保存
											</button>

											&nbsp; &nbsp;
											<button class="btn" type="reset">
												<i class="ace-icon fa fa-undo bigger-110"></i> 重置
											</button>
										</div>
									</div>
								</form:form>
							</div>
							<!-- /.span -->
						</div>
						<!-- /.user-profile -->
					</div>

					<!-- PAGE CONTENT ENDS -->
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
		alert('${msg}')
	}
</script>
