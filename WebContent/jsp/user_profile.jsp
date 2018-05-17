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
									action="${pageContext.request.contextPath }/user/updateProfile"
									commandName="userProfile" >
									<div class="tabbable">
										<ul class="nav nav-tabs padding-16">
											<li class="active"><a
												href="${pageContext.request.contextPath }/user/profile"
												aria-expanded="true"> <i
													class="green ace-icon fa fa-pencil-square-o bigger-125"></i>
													基本信息
											</a></li>

											<li><a
												href="${pageContext.request.contextPath }/user/password">
													<i class="blue ace-icon fa fa-key bigger-125"></i> 修改密码
											</a></li>
										</ul>

										<div class="tab-content profile-edit-tab-content">
											<div id="edit-basic" class="tab-pane in active">
												<h4 class="header blue bolder smaller">基本信息</h4>

												<div class="row">
													<div class="col-xs-12 col-sm-4">
														<span class="profile-picture"> <img id="avatar"
															class="editable img-responsive" alt="Alex's Avatar"
															src="${pageContext.request.contextPath }${userProfile.logo}" onerror="javascript:this.src='${pageContext.request.contextPath }/assets/images/no-pic.jpg';"/>
															<form:hidden path="logo"/>
														</span>
													</div>

													<div class="col-xs-12 col-sm-8">
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right"
																for="form-field-username">昵称</label>

															<div class="col-sm-9">
																<form:input path="nickName" class="col-xs-12 col-sm-10"
																	id="form-field-username" placeholder="昵称" />
															</div>
														</div>
														<div class="space-4"></div>
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right"
																for="form-field-email">地址</label>

															<div class="col-sm-9">
																<form:input path="address" class="col-xs-12 col-sm-10"
																	id="form-field-username" placeholder="地址" />
															</div>
														</div>

														<div class="space-4"></div>


														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right"
																for="form-field-phone">电话</label>

															<div class="col-sm-9">
																<form:input path="tel" class="col-xs-12 col-sm-10"
																	id="form-field-username" placeholder="电话" />
															</div>
														</div>


														<div class="space-4"></div>

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
<!-- inline scripts related to this page -->
<script
	src="${pageContext.request.contextPath }/assets/js/jquery-ui.custom.js"></script>
<script
	src="${pageContext.request.contextPath }/assets/js/jquery.ui.touch-punch.js"></script>
<script
	src="${pageContext.request.contextPath }/assets/js/jquery.gritter.js"></script>
<script src="${pageContext.request.contextPath }/assets/js/bootbox.js"></script>
<script
	src="${pageContext.request.contextPath }/assets/js/jquery.easypiechart.js"></script>
<script
	src="${pageContext.request.contextPath }/assets/js/jquery.hotkeys.js"></script>
<script
	src="${pageContext.request.contextPath }/assets/js/fuelux/fuelux.spinner.js"></script>
<script
	src="${pageContext.request.contextPath }/assets/js/x-editable/bootstrap-editable.js"></script>
<script
	src="${pageContext.request.contextPath }/assets/js/x-editable/ace-editable.js"></script>
<script
	src="${pageContext.request.contextPath }/assets/js/jquery.maskedinput.js"></script>
	

<script type="text/javascript">
jQuery(function($) {
	$.fn.editable.defaults.mode = 'inline';
	$.fn.editableform.loading = "<div class='editableform-loading'><i class='ace-icon fa fa-spinner fa-spin fa-2x light-blue'></i></div>";
	$.fn.editableform.buttons = '<button type="submit" class="btn btn-info editable-submit"><i class="ace-icon fa fa-check"></i></button>'+
								'<button type="button" class="btn editable-cancel"><i class="ace-icon fa fa-times"></i></button>';    


	try {//ie8 throws some harmless exceptions, so let's catch'em

		//first let's add a fake appendChild method for Image element for browsers that have a problem with this
		//because editable plugin calls appendChild, and it causes errors on IE
		try {
			document.createElement('IMG').appendChild(document.createElement('B'));
		} catch(e) {
			Image.prototype.appendChild = function(el){}
		}

		var last_gritter
		$('#avatar').editable({
			type: 'image',
			name: 'avatar',
			value: null,
			image: {
				//specify ace file input plugin's options here
				btn_choose: 'Change Avatar',
				droppable: true,
				maxSize: 110000,//~100Kb

				//and a few extra ones here
				name: 'avatar',//put the field name here as well, will be used inside the custom plugin
				on_error : function(error_type) {//on_error function will be called when the selected file has a problem
					if(last_gritter) $.gritter.remove(last_gritter);
					if(error_type == 1) {//file format error
						last_gritter = $.gritter.add({
							title: 'File is not an image!',
							text: 'Please choose a jpg|gif|png image!',
							class_name: 'gritter-error gritter-center'
						});
					} else if(error_type == 2) {//file size rror
						last_gritter = $.gritter.add({
							title: 'File too big!',
							text: 'Image size should not exceed 100Kb!',
							class_name: 'gritter-error gritter-center'
						});
					}
					else {//other error
					}
				},
				on_success : function() {
					$.gritter.removeAll();
				}
			},
			url: function(params) {
				// ***UPDATE AVATAR HERE*** //
				var submit_url = '${pageContext.request.contextPath}/upload';//please modify submit_url accordingly
				var deferred = null;
				var avatar = '#avatar';

				//if value is empty (""), it means no valid files were selected
				//but it may still be submitted by x-editable plugin
				//because "" (empty string) is different from previous non-empty value whatever it was
				//so we return just here to prevent problems
				var value = $(avatar).next().find('input[type=hidden]:eq(0)').val();
				if(!value || value.length == 0) {
					deferred = new $.Deferred
					deferred.resolve();
					return deferred.promise();
				}

				var $form = $(avatar).next().find('.editableform:eq(0)')
				var file_input = $form.find('input[type=file]:eq(0)');
				var pk = $(avatar).attr('data-pk');//primary key to be sent to server

				var ie_timeout = null


				if( "FormData" in window ) {
					var formData_object = new FormData();//create empty FormData object
					
					//serialize our form (which excludes file inputs)
					$.each($form.serializeArray(), function(i, item) {
						//add them one by one to our FormData 
						formData_object.append(item.name, item.value);							
					});
					//and then add files
					$form.find('input[type=file]').each(function(){
						var field_name = $(this).attr('name');
						var files = $(this).data('ace_input_files');
						if(files && files.length > 0) {
							formData_object.append(field_name, files[0]);
						}
					});
					//append primary key to our formData
					formData_object.append('pk', pk);
					deferred = $.ajax({
								url: submit_url,
							   type: 'POST',
						processData: false,//important
						contentType: false,//important
						   dataType: 'json',//server response type
							   data: formData_object
					})
				}
				else {
					deferred = new $.Deferred

					var temporary_iframe_id = 'temporary-iframe-'+(new Date()).getTime()+'-'+(parseInt(Math.random()*1000));
					var temp_iframe = 
							$('<iframe id="'+temporary_iframe_id+'" name="'+temporary_iframe_id+'" \
							frameborder="0" width="0" height="0" src="about:blank"\
							style="position:absolute; z-index:-1; visibility: hidden;"></iframe>')
							.insertAfter($form);
							
					$form.append('<input type="hidden" name="temporary-iframe-id" value="'+temporary_iframe_id+'" />');
					
					//append primary key (pk) to our form
					$('<input type="hidden" name="pk" />').val(pk).appendTo($form);
					
					temp_iframe.data('deferrer' , deferred);
					//we save the deferred object to the iframe and in our server side response
					//we use "temporary-iframe-id" to access iframe and its deferred object

					$form.attr({
							  action: submit_url,
							  method: 'POST',
							 enctype: 'multipart/form-data',
							  target: temporary_iframe_id //important
					});

					$form.get(0).submit();

					//if we don't receive any response after 30 seconds, declare it as failed!
					ie_timeout = setTimeout(function(){
						ie_timeout = null;
						temp_iframe.attr('src', 'about:blank').remove();
						deferred.reject({'status':'fail', 'message':'Timeout!'});
					} , 30000);
				}


				//deferred callbacks, triggered by both ajax and iframe solution
				deferred
				.done(function(result) {//success
					var res = result;//the `result` is formatted by your server side response and is arbitrary
					if(res.status == 'OK') {
						$(avatar).get(0).src = res.url;
						$("input[name='logo']").val(res.logo);	
					}
					else alert(res.message);
				})
				.fail(function(result) {//failure
					alert(result.message);
				})
				.always(function() {//called on both success and failure
					if(ie_timeout) clearTimeout(ie_timeout)
					ie_timeout = null;
				});

				return deferred.promise();
				// ***END OF UPDATE AVATAR HERE*** //
			},
				
			success: function(response, newValue) {
				$("#avatar").attr("style","display: block;");
				$(".editableform-loading").attr("style","display: none;");
			}
		})
		
		/**
		//let's display edit mode by default?
		$('#avatar').editable('show').on('hidden', function(e, reason) {
			if(reason == 'onblur') {
				$('#avatar').editable('show');
				return;
			}
			$('#avatar').off('hidden');
		})
		*/
		
	}catch(e) {}
	
});

</script>
<script>
	if('${msg}'){
		alert('${msg}')
	}
</script>