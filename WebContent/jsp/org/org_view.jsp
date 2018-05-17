<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fytec"  uri="/fengyuntec.com" %>
<!-- /section:basics/sidebar -->
<div class="main-content">
	<div class="main-content-inner">
		<div class="page-content">
			<!-- #section:settings.box -->

			<!-- /section:settings.box -->
			<div class="page-header">
				<h1>
					机构管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
						机构
					</small>
				</h1>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<form:form commandName="org" action="${pageContext.request.contextPath }/org/update" class="form-horizontal" method="post" role="form">
						<!-- #section:elements.form -->
						<form:hidden path="id"/>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 机构名称 </label>

							<div class="col-sm-9">
								<form:input path="name" id="form-field-1" readonly="true" placeholder="机构名称"
									cssClass="col-xs-10 col-sm-5" />
								<span class="help-inline col-xs-12 col-sm-7">
									<span class="middle"><font color="red"><form:errors path="name"/></font></span>
								</span>
							</div>
							
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 地址 </label>

							<div class="col-sm-9">
								<form:input path="address" id="form-field-1" readonly="true" placeholder="地址"
									class="col-xs-10 col-sm-5" />
								<span class="help-inline col-xs-12 col-sm-7">
									<span class="middle"><font color="red"><form:errors path="address"/></font></span>
								</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-5">电话</label>

							<div class="col-sm-9">
								<form:input path="tel" id="form-field-1" readonly="true" placeholder="电话"
									class="col-xs-10 col-sm-5" />
								<span class="help-inline col-xs-12 col-sm-7">
									<span class="middle"><font color="red"><form:errors path="tel"/></font></span>
								</span>
							</div>
						</div>

						<div class="form-group" style="display: none;">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-5">是否盈利</label>

							<div class="col-xs-3">
								<label> <fytec:checkbox path="profitable"
									class="ace ace-switch ace-switch-5" disabled="true"/> <span
									class="lbl"></span>
								</label>
							</div>
						</div>


						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
								<a class="btn btn-danger"
									href="${pageContext.request.contextPath }/org/list"> <i
									class="ace-icon fa fa-remove bigger-110"></i> 返回列表页面
								</a>

							</div>
						</div>


					</form:form>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page-content -->
	</div>
</div>
<!-- /.main-content -->
