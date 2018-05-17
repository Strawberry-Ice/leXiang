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
					用户管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
						新增用户
					</small>
				</h1>
			</div>


			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<form:form method="post" modelAttribute="httpActivity"
						action="${pageContext.request.contextPath }/tools/save"
						cssClass="form-horizontal" id="activityForm">
						<!-- #section:elements.form -->
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1">名字</label>
							<div class="col-sm-9">
								<textarea rows="5" cols="50" name="names" id="names"></textarea>
							</div>
						</div>

						<script type="text/javascript">
							var $path_assets = "${pageContext.request.contextPath }/assets";//this will be used in loading jQuery UI if needed!
						</script>

						
						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
								<button class="btn btn-info" type="button" onclick="submitForm();">
									<i class="ace-icon fa fa-check bigger-110"></i> 发布
								</button>
								&nbsp; &nbsp; &nbsp;
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


<script type="text/javascript">
	function submitForm(){
		$.ajax({
			url : '${pageContext.request.contextPath }/tools/save',
			cache : false,
			type : "post",
			dataType : "json",
			data : {names:$('#names').val()},
			success : function(r) {
				alert(r.msg);
				if(r.success){
					$('#names').val('');
				}
			}
		});
	}
	
</script>