<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fytec"  uri="/fengyuntec.com" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div class="main-content">
	<div class="main-content-inner">
		<div class="page-content">
			<!-- #section:settings.box -->

			<!-- /section:settings.box -->
			<div class="page-header">
				<h1>
					招募管理
					<small>
						<i class="ace-icon fa fa-angle-double-right"></i>
						新增招募
					</small>
				</h1>
			</div>
			
			
			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<form:form method="post" modelAttribute="httpActivity" action="${pageContext.request.contextPath }/activity/" cssClass="form-horizontal" id="activityForm">
						<!-- #section:elements.form -->
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 活动名称 </label>
							<div class="col-sm-9">
								<div class="clearfix">
									<form:input path="name" cssClass="col-xs-10 col-sm-5" placeholder="招募名称"/>
								</div>
								<div style="color: red;" class="help-block" ><form:errors path="name"/></div>
							</div>
						</div>
						
						<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1">  </label>
										<div class="col-xs-12 col-sm-9">
											<div class="checkbox">
												<label>
													<input name="sendMessage" type="checkbox" class="ace" value="true" checked="checked"/>
													<span class="lbl">发送模板消息(可通过社区头条微信号向绑定本社区的居民发送提醒通知)</span>
												</label>
											</div>	
										</div>
										</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-5">发布场合</label>

							<div class="row">
							<div class="col-xs-12 col-sm-5">
								<div class="col-sm-9">
									<div class="clearfix">
										<div class="radio">
											<label>
												<form:radiobutton path="occasion" value="1" cssClass="ace" onclick="checkOccasion(this);"/><span class="lbl"> 社区</span>
											</label>
											<label>
												<form:radiobutton path="occasion" value="2" cssClass="ace" onclick="checkOccasion(this);"/><span class="lbl"> 广场</span>
											</label>
											
										</div>
									</div>
									（选择广场发布则非本社区居民也可看到）
									<div style="color: red;" class="help-block" ><form:errors path="occasion"/></div>
								</div>
							</div>
						</div>
						</div>
						
						
						
						<sec:authorize
							access="hasAnyRole('ROLE_Role_sys_admin','ROLE_Role_org_admin','ROLE_Role_org_user')">
						<c:choose>
							    <c:when test="${httpActivity.id == ''|| httpActivity.id == null}">
							   			<div class="form-group" id="square">
											<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 街道或社工委 </label>
											<div class="col-sm-9">
												<select class="col-xs-10 col-sm-5" id="form-field-select-1"  multiple="multiple" onchange="getValue();">
												</select>
												<label><b style="color:red;">*</b> 按住Ctrl可多选 </label>
											</div>
										</div>
										
										
										<div class="form-group" id="community">
											<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 社区 </label>
											<div class="col-sm-9">
												<div class="clearfix">
													<select class="col-xs-10 col-sm-5" id="form-field-select-2" multiple="multiple" name="govOrgIds">
														</select>
														<label><b style="color:red;">*</b> 按住Ctrl可多选 </label>
												</div>
												<div style="color: red;" class="help-block" ><form:errors path="govOrgIds"/></div>
											</div>
										</div>
							    </c:when>
							    <c:when test="${httpActivity.id != ''&&httpActivity.id != null}">
							  		<input type="hidden" name="govOrgIds" value="${httpActivity.govOrgIds }"/>
							  			<div class="form-group">
											<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 社区 </label>
											<div class="col-sm-9">
												 <label class="col-xs-10 col-sm-5">${httpActivity.govName }</label>
											</div>
									</div>
							  </c:when>
						</c:choose>
						<script type="text/javascript">
								function checkOccasion(obj) {
									if (obj.value == '2') {
										$('#community').hide();
										$('#square').hide();
										$('#remark1').html('备注:&nbsp; &nbsp; &nbsp;活动需平台审核后方可在平台发布，请及时与管理员联系!');
									} else {
										$('#community').show();
										$('#square').show();
										$('#remark1').html('备注:&nbsp; &nbsp; &nbsp;活动需所在社区居委会审核通过后方可在平台发布，请及时与社区联系!');
									}
								}
							</script>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('ROLE_Role_community_user','ROLE_Role_community_admin')">  
							<input type="hidden" name="govOrgIds"
										value="${httpActivity.govOrgIds }" />
						</sec:authorize>
						
						<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否置顶</label>
										<div class="col-sm-9">
											<div class="clearfix">
												<div class="radio">
													<label> <form:radiobutton path="needTop" value="false"
															cssClass="ace" onclick="checkTop(this);" /><span
														class="lbl"> 否</span>
													</label> <label> <form:radiobutton path="needTop" value="true"
															cssClass="ace" onclick="checkTop(this);" /><span
														class="lbl"> 是</span>
													</label>
	
												</div>
											</div>
										</div>
									</div>
									<script type="text/javascript">
										function checkTop(obj) {
											if (obj.value=='true') {
												$('#topStartDate').show();
												$('#topEndDate').show();
											} else {
												$('#topStartDate').hide();
												$('#topEndDate').hide();
											}
										}
									</script>
									
									<div class="form-group" style="display: none;" id="topStartDate">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 置顶开始时间 </label>

							<div class="col-sm-9">
								<div class="clearfix">
									<div class="input-group col-xs-10 col-sm-5">
										<form:input path="topStartDates"
											cssClass="form-control date-picker" id="id-date-picker-1"
											data-date-format="yyyy-mm-dd" />
										<span class="input-group-addon"> <i
											class="fa fa-calendar bigger-110"></i>
										</span>

									</div>
								</div>
								<div style="color: red;" class="help-block">
									<form:errors path="topStartDates" />
								</div>
							</div>
						</div>

						<div class="form-group" style="display: none;" id="topEndDate">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 置顶截止时间 </label>

							<div class="col-sm-9">
								<div class="clearfix">
									<div class="input-group col-xs-10 col-sm-5">
										<form:input path="topEndDates"
											cssClass="form-control date-picker" id="id-date-picker-1"
											data-date-format="yyyy-mm-dd" />
										<!--<input class="form-control date-picker" id="id-date-picker-4" type="text" data-date-format="yyyy-mm-dd" name="activityEndDates"/>  -->
										<span class="input-group-addon"> <i
											class="fa fa-calendar bigger-110"></i>
										</span>

									</div>
								</div>
								<div style="color: red;" class="help-block">
									<form:errors path="topEndDates" />
								</div>
							</div>
						</div>
						
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 报名开始时间 </label>

							<div class="col-sm-9">
								<div class="clearfix">
									<div class="input-group col-xs-10 col-sm-5">
										<form:input path="applicationStartDates" cssClass="form-control date-picker" id="id-date-picker-1" data-date-format="yyyy-mm-dd"/>
										<span class="input-group-addon">
											<i class="fa fa-calendar bigger-110"></i>
										</span>
										
									</div>
								</div>
								<div style="color: red;" class="help-block" ><form:errors path="applicationStartDates"/></div>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 报名截止时间 </label>

							<div class="col-sm-9">
								<div class="clearfix">
									<div class="input-group col-xs-10 col-sm-5">
										<form:input path="applicationEndDates" cssClass="form-control date-picker" id="id-date-picker-1" data-date-format="yyyy-mm-dd"/>
										<span class="input-group-addon">
											<i class="fa fa-calendar bigger-110"></i>
										</span>
										
									</div>
								</div>
								<div style="color: red;" class="help-block" ><form:errors path="applicationEndDates"/></div>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 招募开始时间 </label>

							<div class="col-sm-9">
								<div class="clearfix">
									<div class="input-group col-xs-10 col-sm-5">
										<form:input path="activtyStartDates" cssClass="form-control date-picker" id="id-date-picker-1" data-date-format="yyyy-mm-dd"/>
										<span class="input-group-addon">
											<i class="fa fa-calendar bigger-110"></i>
										</span>
										
									</div>
								</div>
								<div style="color: red;" class="help-block" ><form:errors path="activtyStartDates"/></div>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> </label>

							<div class="col-sm-9">
								<div class="clearfix">
									<div class="input-group col-xs-10 col-sm-5">
										<form:input path="activtyStartTimes"
											cssClass="form-control" id="timepicker1"/>
											<span class="input-group-addon">
												<i class="fa fa-clock-o bigger-110"></i>
											</span>

									</div>
								</div>
								<div style="color: red;" class="help-block">
									<form:errors path="activtyStartTimes" />
								</div>
							</div>
						</div>
						
						<!--<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 招募截止时间 </label>

							<div class="col-sm-9">
								<div class="clearfix">
									<div class="input-group col-xs-10 col-sm-5">
										<form:input path="activityEndDates" cssClass="form-control date-picker" id="id-date-picker-1" data-date-format="yyyy-mm-dd"/>
										<span class="input-group-addon">
											<i class="fa fa-calendar bigger-110"></i>
										</span>
										
									</div>
								</div>
								<div style="color: red;" class="help-block" ><form:errors path="activityEndDates"/></div>
							</div>
						</div>-->
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否启用外链接</label>
							<div class="col-sm-9">
								<div class="clearfix">
									<div class="radio">
										<label> <form:radiobutton path="needUrl" value="false"
												cssClass="ace" onclick="checkUrl(this);"/><span
											class="lbl"> 否</span>
										</label> <label> <form:radiobutton path="needUrl" value="true"
												cssClass="ace" onclick="checkUrl(this);"/><span
											class="lbl"> 是</span>
										</label>

									</div>
								</div>
							</div>
						</div>
						<script type="text/javascript">
							function checkUrl(obj) {
								if (obj.value=='false') {
									$('#isUrl').show();
									$('#outUrl').hide();
								} else {
									$('#isUrl').hide();
									$('#outUrl').show();
								}
							}
						</script>
						<div class="form-group" id="outUrl">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 链接 </label>
							<div class="col-sm-9">
								<div class="clearfix">
									<form:input path="url" cssClass="col-xs-10 col-sm-5"/>
								</div>
							</div>
						</div>
						<div id="isUrl">
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-5">招募名额</label>

							<div class="col-sm-9">
								<div class="clearfix">
								<c:if test="${empty httpActivity.id}">
									<form:input path="quota" cssClass="col-xs-1" placeholder="0"/><label>（填写0则不限制名额）</label>
								</c:if>
								<c:if test="${!empty httpActivity.id}">
									<form:input path="quota" readonly="true" cssClass="col-xs-1" placeholder="0"/><label>（填写0则不限制名额）</label>
								</c:if>
									<form:errors path="quota" cssClass="help-block" />
								</div>
							</div>
						</div>
						
							<sec:authorize
							access="hasAnyRole('ROLE_Role_org_admin','ROLE_Role_org_user','ROLE_Role_street_admin','ROLE_Role_street_admin')">
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-5">招募积分</label>

							<div class="col-sm-9">
								<div class="clearfix">
								<c:if test="${empty httpActivity.id}">
									<form:input path="score" cssClass="col-xs-1" readonly="true" placeholder="0"/>
								</c:if>
								<c:if test="${!empty httpActivity.id}">
									<form:input path="score" readonly="true" cssClass="col-xs-1" placeholder="0"/>
								</c:if>
									<form:errors path="score" cssClass="help-block" />
								
								</div>
							</div>
						</div>
						</sec:authorize>
						
						<sec:authorize
							access="hasAnyRole('ROLE_Role_sys_admin','ROLE_Role_community_admin','ROLE_Role_community_user')">
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-5">招募积分</label>

							<div class="col-sm-9">
								<div class="clearfix">
								<c:if test="${empty httpActivity.id}">
									<form:input path="score" cssClass="col-xs-1" placeholder="0"/>
								</c:if>
								<c:if test="${!empty httpActivity.id}">
									<form:input path="score" readonly="true" cssClass="col-xs-1" placeholder="0"/>
								</c:if>
									<form:errors path="score" cssClass="help-block" />
								
								</div>
							</div>
						</div>
						</sec:authorize>
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-5">报名审核</label>

							<div class="col-xs-3">
								<label>
									<fytec:checkbox path="needVerify" cssClass="ace ace-switch ace-switch-5"/>
									<span class="lbl"></span>
								</label>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-5">报名数据</label>

							<div class="row">
							<div class="col-xs-12 col-sm-5">
								<div class="clearfix">
									<div class="control-group">
										<div class="checkbox">
											<c:forEach var="fieldsType" items="${httpActivity.fieldsType}">  
	  												<label>
	  													<fytec:checkbox cssClass="ace ace-checkbox-2" path="filedType" value="${fieldsType.id }"/><span class="lbl">${fieldsType.name }</span>
													</label>
											</c:forEach> 
										</div>
									</div>
								</div>
								<div style="color: red;" class="help-block" ><form:errors path="filedType"/></div>
							</div>
						</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 摘要</label>
							<div class="col-sm-9">
								<form:textarea path="remark" cssClass="col-xs-10 col-sm-5" placeholder="选填，简要说明信息内容，发送模板消息时便于居民快速了解"/>发送模板消息(可通过微信服务号向本社区居民发送提醒通知)
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 主办方 </label>
							<div class="col-sm-9">
								<div class="clearfix">
									<form:input path="sponsor" cssClass="col-xs-10 col-sm-5" placeholder="主办方"/>
								</div>
								<div style="color: red;" class="help-block" ><form:errors path="sponsor"/></div>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-5">活动内容</label>

							<div class="col-sm-9">
								<div class="clearfix">
									<div class="wysiwyg-editor" id="editor1" onblur="setValue();">${httpActivity.content }</div>
								</div>
								<div style="color: red;" class="help-block" ><form:errors path="content"/></div>
								<div class="hr hr-double dotted"></div>
							
							</div>
						</div>
						</div>
						<textarea name="content" id="content" rows="1" cols="1" style="display: none;">${httpActivity.content }</textarea>
						<input type="hidden" name="code" value="${code }"/>
						<input type="hidden" name="id" value="${httpActivity.id }"/>
						<input type="hidden" name="valid" id="valid" value="${httpActivity.valid }"/>
									
						
						<script type="text/javascript">
							var $path_assets = "${pageContext.request.contextPath }/assets";//this will be used in loading jQuery UI if needed!
						</script>
						
						
						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
								<button class="btn btn-info" type="button" onclick="submitForm();">
									<i class="ace-icon fa fa-check bigger-110"></i>
									发布
								</button>
								
								&nbsp; &nbsp; &nbsp;
								<a class="btn btn-danger" href="${pageContext.request.contextPath }/activity/activity/${code }">
									<i class="ace-icon fa fa-remove bigger-110"></i>
									返回
								</a>

								&nbsp; &nbsp; &nbsp;
								<button class="btn" type="button" onclick="save();">
									<i class="ace-icon fa fa-undo bigger-110"></i> 保存
								</button>
								
							</div>
							<sec:authorize access="hasAnyRole('ROLE_Role_org_admin','ROLE_Role_org_user')">  
								<div class="col-md-offset-3 col-md-9" id="remark1">
								</div>
							</sec:authorize>
						</div>
						</form:form>
				</div><!-- /.col -->
			</div><!-- /.row -->
		</div><!-- /.page-content -->
	</div>
</div><!-- /.main-content -->


<script type="text/javascript">
var globalGovOrg;

			jQuery(function($){
				
				<c:choose>
					<c:when test="${httpActivity.needTop}">
						$('#topStartDate').show();
						$('#topEndDate').show();
					</c:when>
					<c:otherwise>
						$('#topStartDate').hide();
						$('#topEndDate').hide();
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${httpActivity.needUrl}">
						$('#isUrl').hide();
						$('#outUrl').show();
					</c:when>
					<c:otherwise>
						$('#isUrl').show();
						$('#outUrl').hide();
					</c:otherwise>
				</c:choose>
				
				
				<c:if test="${httpActivity.occasion=='2' }">
					$('#community').hide();
					$('#square').hide();
					$('#remark1').html('备注:&nbsp; &nbsp; &nbsp;活动需平台审核后方可在平台发布，请及时与管理员联系!');
				</c:if>
				<c:if test="${httpActivity.occasion=='1' }">
					$('#remark1').html('备注:&nbsp; &nbsp; &nbsp;活动需所在社区居委会审核通过后方可在平台发布，请及时与社区联系!');
				</c:if>
				$.ajax({
					url : '${pageContext.request.contextPath }/govOrg/getGovOrg',
					cache : false,
					dataType : "json",
					success : function(r) {
						var httpGovOrgList=r.obj;
						globalGovOrg=httpGovOrgList;
						for(var i=0;i<httpGovOrgList.length;i++){
							var httpGovOrg=httpGovOrgList[i];
							$('#form-field-select-1').append("<option value='"+httpGovOrg.id+"'>"+httpGovOrg.name+"</option>");
							var children=httpGovOrg.children;
							for(var j=0;j<children.length;j++){
								var child=children[j];
								$('#form-field-select-2').append("<option value='"+child.id+"'>"+child.name+"</option>");
							}
						}
					}
				});
				
				$('.date-picker').datepicker({
					autoclose: true,
					todayHighlight: true
				}).next().on(ace.click_event, function(){
					$(this).prev().focus();
				});
				
				
				$('#timepicker1').timepicker({
					minuteStep: 1,
					showSeconds: false,
					showMeridian: false
				}).next().on(ace.click_event, function(){
					$(this).prev().focus();
				});
				
			function showErrorAlert (reason, detail) {
				var msg='';
				if (reason==='unsupported-file-type') { msg = "Unsupported format " +detail; }
				else {
					//console.log("error uploading file", reason, detail);
				}
				$('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'+ 
				 '<strong>File upload error</strong> '+msg+' </div>').prependTo('#alerts');
			}
		
			//$('#editor1').ace_wysiwyg();//this will create the default editor will all buttons
		
			//but we want to change a few buttons colors for the third style
			$('#editor1').ace_wysiwyg({
				toolbar:
				[
					'font',
					null,
					'fontSize',
					null,
					{name:'bold', className:'btn-info'},
					{name:'italic', className:'btn-info'},
					{name:'strikethrough', className:'btn-info'},
					{name:'underline', className:'btn-info'},
					null,
					{name:'insertunorderedlist', className:'btn-success'},
					{name:'insertorderedlist', className:'btn-success'},
					{name:'outdent', className:'btn-purple'},
					{name:'indent', className:'btn-purple'},
					null,
					{name:'justifyleft', className:'btn-primary'},
					{name:'justifycenter', className:'btn-primary'},
					{name:'justifyright', className:'btn-primary'},
					{name:'justifyfull', className:'btn-inverse'},
					null,
					{name:'createLink', className:'btn-pink'},
					{name:'unlink', className:'btn-pink'},
					null,
					{name:'insertImage', className:'btn-success'},
					null,
					'foreColor',
					null,
					{name:'undo', className:'btn-grey'},
					{name:'redo', className:'btn-grey'}
				],
				'wysiwyg': {
					fileUploadError: showErrorAlert
				}
			}).prev().addClass('wysiwyg-style2');
		
			
			/**
			//make the editor have all the available height
			$(window).on('resize.editor', function() {
				var offset = $('#editor1').parent().offset();
				var winHeight =  $(this).height();
				
				$('#editor1').css({'height':winHeight - offset.top - 10, 'max-height': 'none'});
			}).triggerHandler('resize.editor');
			*/
		
		
			$('[data-toggle="buttons"] .btn').on('click', function(e){
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				var toolbar = $('#editor1').prev().get(0);
				if(which >= 1 && which <= 4) {
					toolbar.className = toolbar.className.replace(/wysiwyg\-style(1|2)/g , '');
					if(which == 1) $(toolbar).addClass('wysiwyg-style1');
					else if(which == 2) $(toolbar).addClass('wysiwyg-style2');
					if(which == 4) {
						$(toolbar).find('.btn-group > .btn').addClass('btn-white btn-round');
					} else $(toolbar).find('.btn-group > .btn-white').removeClass('btn-white btn-round');
				}
			});
		
		
			
		
			//RESIZE IMAGE
			
			//Add Image Resize Functionality to Chrome and Safari
			//webkit browsers don't have image resize functionality when content is editable
			//so let's add something using jQuery UI resizable
			//another option would be opening a dialog for user to enter dimensions.
			if ( typeof jQuery.ui !== 'undefined' && ace.vars['webkit'] ) {
				
				var lastResizableImg = null;
				function destroyResizable() {
					if(lastResizableImg == null) return;
					lastResizableImg.resizable( "destroy" );
					lastResizableImg.removeData('resizable');
					lastResizableImg = null;
				}
		
				var enableImageResize = function() {
					$('.wysiwyg-editor')
					.on('mousedown', function(e) {
						var target = $(e.target);
						if( e.target instanceof HTMLImageElement ) {
							if( !target.data('resizable') ) {
								target.resizable({
									aspectRatio: e.target.width / e.target.height,
								});
								target.data('resizable', true);
								
								if( lastResizableImg != null ) {
									//disable previous resizable image
									lastResizableImg.resizable( "destroy" );
									lastResizableImg.removeData('resizable');
								}
								lastResizableImg = target;
							}
						}
					})
					.on('click', function(e) {
						if( lastResizableImg != null && !(e.target instanceof HTMLImageElement) ) {
							destroyResizable();
						}
					})
					.on('keydown', function() {
						destroyResizable();
					});
			    }
		
				enableImageResize();
			}
		
		
		});
			
			function getValue(){
				var ids=$('#form-field-select-1').val();
				$('#form-field-select-2').html('');
				for(var i=0;i<globalGovOrg.length;i++){
					var httpGovOrg=globalGovOrg[i];
					for(var k=0;k<ids.length;k++){
						if(httpGovOrg.id === ids[k]){
							var children=httpGovOrg.children;
							for(var j=0;j<children.length;j++){
								var child=children[j];
								$('#form-field-select-2').append("<option value='"+child.id+"'>"+child.name+"</option>");
							}
						}
					}
				}
				
			}
			
			
			function setValue(){
				$('#content').val($('#editor1').html());
			}
			
			function submitForm() {
				$('#valid').val('true');
				$('#activityForm').submit();
			}
			
			function save() {
				$('#valid').val('false');
				$('#activityForm').submit();
			}
</script>