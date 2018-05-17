<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<%@ taglib prefix="fytec"  uri="/fengyuntec.com" %>
<!-- /section:basics/sidebar -->
<div class="main-content">
	<div class="main-content-inner">
		<div class="page-content">
			<!-- #section:settings.box -->

			<!-- /section:settings.box -->
			<div class="page-header">
				<h1>
					用户管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
						修改用户
					</small>
				</h1>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<form:form commandName="user" action="${pageContext.request.contextPath }/user/update" class="form-horizontal" method="post" role="form">
						<!-- #section:elements.form -->
						<form:hidden path="id"/>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 用户名称 </label>

							<div class="col-sm-9">
								<form:input path="userName" id="form-field-1" placeholder="用户名称"
									cssClass="col-xs-10 col-sm-5" readonly="true"/>
								<span class="help-inline col-xs-12 col-sm-7">
									<span class="middle"><font color="red"><form:errors path="userName"/></font></span>
								</span>
							</div>
							
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 密码 </label>

							<div class="col-sm-9">
								<form:password path="password" id="form-field-1" placeholder="密码"
									cssClass="col-xs-10 col-sm-5" />
								<span class="help-inline col-xs-12 col-sm-7">
									<span class="middle"><font color="red"><form:errors path="password"/></font></span>
								</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 昵称 </label>

							<div class="col-sm-9">
								<form:input path="nickName" id="form-field-1" placeholder="昵称"
									class="col-xs-10 col-sm-5" />
								<span class="help-inline col-xs-12 col-sm-7">
									<span class="middle"><font color="red"><form:errors path="nickName"/></font></span>
								</span>
							</div>
						</div>



						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 地址 </label>

							<div class="col-sm-9">
								<form:input path="address" id="form-field-1" placeholder="地址"
									class="col-xs-10 col-sm-5" />
								<span class="help-inline col-xs-12 col-sm-7">
									<span class="middle"><font color="red"><form:errors path="address"/></font></span>
								</span>
							</div>
						</div>

<sec:authorize access="hasRole('ROLE_Role_sys_admin')">  
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-5">角色</label>

							<div class="row">
								<div class="col-xs-12 col-sm-5">
									<div class="control-group">
										<div class="radio">
										<fytec:radiobuttons path="role.id" items="${roles }" itemLabel="name" itemValue="id"/>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div class="form-group" id="parentGovOrgDiv" style="display: none">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 街道（社工委） </label>
							<div class="col-sm-9">
								<form:select class="form-control" cssStyle="width:auto" path="parentGovOrg" id="parentGovOrg">
									<form:option value="">请选择</form:option>
									<form:options items="${govOrgs }" itemLabel="name" itemValue="id"/>
								</form:select>
								<span class="help-inline col-xs-12 col-sm-7">
									<span class="middle"><font color="red"><form:errors path="parentGovOrg"/></font></span>
								</span>
							</div>
						</div>
						
						
						<div class="form-group" id="childGovOrgDiv" style="display: none">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 社区 </label>
							<div class="col-sm-9">
								<select class="form-control" style="width:auto" name="govOrg.id" id="childGovOrg">
									<option value="">请选择</option>
								</select>
							</div>
						</div>
						
						
						<div class="form-group" id="orgDiv" style="display: none">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 机构 </label>
							<div class="col-sm-9">
								<form:select class="form-control" cssStyle="width:auto" path="org.id" id="org.id">
									<form:option value="">请选择</form:option>
									<form:options items="${orgs }" itemLabel="name" itemValue="id"/>
								</form:select>
								<span class="help-inline col-xs-12 col-sm-7">
									<span class="middle"><font color="red"><form:errors path="org.id"/></font></span>
								</span>
							</div>
						</div>

</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_Role_street_admin','ROLE_Role_community_admin')">  
<form:hidden path="govOrg.id"/>
<form:hidden path="role.id"/>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_Role_org_admin')">  
<form:hidden path="role.id"/>
<form:hidden path="org.id"/>
</sec:authorize>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-5">电话</label>

							<div class="col-sm-9">
								<form:input path="tel" id="form-field-1" placeholder="电话"
									class="col-xs-10 col-sm-5" />
								<span class="help-inline col-xs-12 col-sm-7">
									<span class="middle"><font color="red"><form:errors path="tel"/></font></span>
								</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-5">是否启用</label>

							<div class="col-xs-3">
								<label> <fytec:checkbox path="state"
									class="ace ace-switch ace-switch-5" disabled="false"/> <span
									class="lbl"></span>
								</label>
							</div>
						</div>


						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 备注 </label>
							<div class="col-sm-9">
								<form:textarea path="description" class="col-xs-10 col-sm-5"/>
								<span class="help-inline col-xs-12 col-sm-7">
									<span class="middle"><font color="red"><form:errors path="description"/></font></span>
								</span>
							</div>
						</div>

						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
								<button class="btn btn-info" type="submit">
									<i class="ace-icon fa fa-check bigger-110"></i> 提交
								</button>

								&nbsp; &nbsp; &nbsp; <a class="btn btn-danger"
									href="${pageContext.request.contextPath}/user/list"> <i
									class="ace-icon fa fa-remove bigger-110"></i> 取消
								</a> &nbsp; &nbsp; &nbsp;
								<button class="btn" type="reset">
									<i class="ace-icon fa fa-undo bigger-110"></i> 重置
								</button>

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
<sec:authorize access="hasRole('ROLE_Role_sys_admin')">  
<script>
var initUserCreate=true;
$("#parentGovOrg").change( function () {
	if($(this).val()!=""){
		var parentId=$(this).val();
		$.getJSON("${pageContext.request.contextPath}/user/getGovOrg", { parentId: parentId, time: new Date().getTime() }, function(data){
			$("#childGovOrg").empty();
			$("#childGovOrg").append("<option value='"+parentId+"'>街道（社工委）</option>"); 
			for(var i=0;i<data.length;i++){
		  		$("#childGovOrg").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>"); 
		  	}
			if(initUserCreate){
				$("#childGovOrg").val("${user.govOrg.id}");
				initUserCreate=false;
			}
		});
	}else{
		$("#childGovOrg").empty();
		$("#childGovOrg").append("<option value=''>请选择</option>"); 
		if(initUserCreate){
			$("#childGovOrg").val("${user.govOrg.id}");
			initUserCreate=false;
		}
	}
	
});

$("input[name='role.id']").change( function () {
	if($(this).val()==6||$(this).val()==7){
		$("#orgDiv").css('display','block');
		$("#parentGovOrgDiv").css('display','none'); 
		$("#childGovOrgDiv").css('display','none'); 
		
		$("#orgDiv select").attr("disabled",false); 
		$("#parentGovOrgDiv select").attr('disabled',true);
		$("#childGovOrgDiv select").attr('disabled',true);
	}else{
		$("#orgDiv").css('display','none'); 
		$("#parentGovOrgDiv").css('display','block'); 
		$("#childGovOrgDiv").css('display','block'); 
		
		$("#orgDiv select").attr("disabled",true); 
		$("#parentGovOrgDiv select").attr('disabled',false);
		$("#childGovOrgDiv select").attr('disabled',false);
	}
});

$(function($) {
	
	$("input[name='role.id']").each(function(i, n){
		if($(this).attr("checked")){
			$(this).change();
		}
	});
	$("#parentGovOrg").change();
	
});

</script>
</sec:authorize>