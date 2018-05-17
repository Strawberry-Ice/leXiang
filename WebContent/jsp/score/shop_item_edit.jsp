<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fytec" uri="/fengyuntec.com"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- /section:basics/sidebar -->
<div class="main-content">
	<div class="main-content-inner">
		<div class="page-content">
			<!-- #section:settings.box -->

			<!-- /section:settings.box -->
			<div class="page-header">
				<h1>
					积分商城 <small> <i class="ace-icon fa fa-angle-double-right"></i>
						新增礼品
					</small>
				</h1>
			</div>


			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<form:form class="form-horizontal" commandName="scoreShopItem"
						role="form"
						action="${pageContext.request.contextPath }/score/shop/update">
						<!-- #section:elements.form -->
						<form:hidden path="id" />
						<form:hidden path="status" id="status" />
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 礼品名称 </label>

							<div class="col-sm-9">
								<form:input path="name" id="form-field-1" placeholder="礼品名称"
									class="col-xs-10 col-sm-5" />
								<span class="help-inline col-xs-12 col-sm-7"> <span
									class="middle"><font color="red"><form:errors
												path="name" /></font></span>
								</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 礼品来源 </label>
							<div class="col-sm-9">
								<form:select class="form-control" cssStyle="width:auto"
									path="org.id" id="parentGovOrg">
									<form:option value="">请选择</form:option>
									<form:options items="${orgs }" itemLabel="name" itemValue="id" />
								</form:select>
								<span class="help-inline col-xs-12 col-sm-7"> <span
									class="middle"><font color="red"><form:errors
												path="org.id" /></font></span>
								</span>
							</div>
						</div>


						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 礼品图片 </label>

							<div class="col-sm-9">
								<div class="col-xs-12 col-sm-4">
									<span class="profile-picture"> <img id="avatar"
										class="editable img-responsive" alt="Alex's Avatar"
										src="${pageContext.request.contextPath }${scoreShopItem.logo}"
										onerror="javascript:this.src='${pageContext.request.contextPath }/assets/images/no-pic.jpg';" />
										<form:hidden path="logo" />
									</span>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-5">礼品描述</label>

							<div class="col-sm-9">



								<div class="wysiwyg-editor" id="editor1">${scoreShopItem.desc}</div>

								<div class="hr hr-double dotted"></div>
								<div class="row-fluid">
									<form:hidden path="desc" />
								</div>


							</div>
						</div>
						<script type="text/javascript">
							var $path_assets = "../assets";//this will be used in loading jQuery UI if needed!
						</script>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-5">礼品价格</label>

							<div class="col-sm-9">
								<div class="clearfix">
									<form:input path="price" readonly="true" class="col-xs-1"
										id="form-field-5" placeholder="0" />
									<span class="middle"><font color="red"><form:errors
												path="price" /></font></span>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 下架时间 </label>

							<div class="col-sm-9">
								<div class="clearfix">
									<div class="input-group col-xs-10 col-sm-5">
										<form:input path="expiry" cssClass="form-control date-picker"
											id="id-date-picker-1" data-date-format="yyyy-mm-dd" />
										<span class="input-group-addon"> <i
											class="fa fa-calendar bigger-110"></i>
										</span>

									</div>
								</div>
								<div style="color: red;" class="help-block">
									<form:errors path="expiry" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-5">兑换有效天数</label>

							<div class="col-sm-9">
								<div class="clearfix">
									<form:input path="day" class="col-xs-1" id="form-field-5"
										placeholder="0" />
									<span class="middle"><font color="red"><form:errors
												path="day" /></font></span>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-5">礼品数量</label>

							<div class="col-sm-9">
								<div class="clearfix">
									<form:input path="num" readonly="true" class="col-xs-1"
										id="form-field-5" placeholder="0" />
									<span class="middle"><font color="red"><form:errors
												path="num" /></font></span>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-5">剩余数量</label>

							<div class="col-sm-9">
								<div class="clearfix">
									<form:input path="restNum" readonly="true" class="col-xs-1"
										id="form-field-5" placeholder="0" />
									<span class="middle"><font color="red"><form:errors
												path="restNum" /></font></span>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-5">礼品类别</label>

							<div class="row">
								<div class="col-xs-12 col-sm-5">
									<div class="control-group">
										<div class="radio">
											<fytec:radiobuttons path="type.id" items="${shopItemTypes }"
												itemLabel="name" itemValue="id" disabled="true" />
											<label> <form:radiobutton path="type.id"
													disabled="true" class="ace" value="-1" /> <span
												class="lbl">&nbsp;&nbsp;其他类</span>
											</label>
										</div>
									</div>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-5">礼品系数</label>

							<div class="col-sm-9">
								<div class="clearfix">
									<form:input path="type.ratio" readonly="true" class="col-xs-1"
										id="form-field-5" placeholder="0" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-5">所需积分</label>

							<div class="col-sm-9">
								<form:input path="score" readonly="true" id="form-field-1"
									placeholder="所需积分" class="col-xs-1" />
							</div>
						</div>


						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
									<button class="btn btn-info" type="button" onclick="toVaild()">
										<i class="ace-icon fa fa-check bigger-110"></i> 上架
									</button>
									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="button" onclick="toVaild2()">
										<i class="ace-icon fa fa-undo bigger-110"></i> 下架
									</button>
								&nbsp; &nbsp; &nbsp; <a class="btn btn-danger"
									href="${pageContext.request.contextPath }/score/shop/list">
									<i class="ace-icon fa fa-remove bigger-110"></i> 取消
								</a> &nbsp; &nbsp; &nbsp;
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
<!-- inline scripts related to this page -->
<script type="text/javascript">
	function initScore() {
		var price = $("input[name='price']").val() * 10000;
		var ratio = $("input[name='type.ratio']").val() * 10000;
		$("input[name='score']").val(Math.ceil(price * ratio / 10000 / 10000));
	}

	jQuery(function($) {

		$('.date-picker').datepicker({
			autoclose : true,
			todayHighlight : true
		}).next().on(ace.click_event, function() {
			$(this).prev().focus();
		});

		$("input[name='type.id']")
				.each(
						function(i) {
							$(this)
									.click(
											function() {
												if ($(this).val() == -1) {
													$(
															"input[name='type.ratio']")
															.removeAttr(
																	"disabled");
													$(
															"input[name='type.ratio']")
															.attr("readOnly",
																	false);
													$(
															"input[name='type.ratio']")
															.val(0);
													$("input[name='score']")
															.removeAttr(
																	"disabled");
													$("input[name='score']")
															.val(0);
												} else {
													$
															.get(
																	"${pageContext.request.contextPath}/score/shop/getRatio",
																	{
																		id : $(
																				this)
																				.val(),
																		time : new Date()
																				.getTime()
																	},
																	function(
																			data) {
																		if (data == -1) {
																			$(
																					"input[name='type.ratio']")
																					.attr(
																							"disabled",
																							true);
																			$(
																					"input[name='type.ratio']")
																					.val(
																							0);
																			$(
																					"input[name='score']")
																					.attr(
																							"disabled",
																							true);
																		} else {
																			$(
																					"input[name='type.ratio']")
																					.removeAttr(
																							"disabled");
																			$(
																					"input[name='type.ratio']")
																					.attr(
																							"readOnly",
																							true);
																			$(
																					"input[name='type.ratio']")
																					.val(
																							data);
																			$(
																					"input[name='score']")
																					.removeAttr(
																							"disabled");
																			initScore();
																		}
																	});
												}
											});
						});

		$("input[name='type.ratio']").keyup(function() {
			initScore();
		});

		$("input[name='price']").keyup(function() {
			initScore();
		});

		function showErrorAlert(reason, detail) {
			var msg = '';
			if (reason === 'unsupported-file-type') {
				msg = "Unsupported format " + detail;
			} else {
				//console.log("error uploading file", reason, detail);
			}
			$(
					'<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'
							+ '<strong>File upload error</strong> '
							+ msg
							+ ' </div>').prependTo('#alerts');
		}

		//$('#editor1').ace_wysiwyg();//this will create the default editor will all buttons

		//but we want to change a few buttons colors for the third style
		$('#editor1').ace_wysiwyg({
			toolbar : [ 'font', null, 'fontSize', null, {
				name : 'bold',
				className : 'btn-info'
			}, {
				name : 'italic',
				className : 'btn-info'
			}, {
				name : 'strikethrough',
				className : 'btn-info'
			}, {
				name : 'underline',
				className : 'btn-info'
			}, null, {
				name : 'insertunorderedlist',
				className : 'btn-success'
			}, {
				name : 'insertorderedlist',
				className : 'btn-success'
			}, {
				name : 'outdent',
				className : 'btn-purple'
			}, {
				name : 'indent',
				className : 'btn-purple'
			}, null, {
				name : 'justifyleft',
				className : 'btn-primary'
			}, {
				name : 'justifycenter',
				className : 'btn-primary'
			}, {
				name : 'justifyright',
				className : 'btn-primary'
			}, {
				name : 'justifyfull',
				className : 'btn-inverse'
			}, null, {
				name : 'createLink',
				className : 'btn-pink'
			}, {
				name : 'unlink',
				className : 'btn-pink'
			}, null, {
				name : 'insertImage',
				className : 'btn-success'
			}, null, 'foreColor', null, {
				name : 'undo',
				className : 'btn-grey'
			}, {
				name : 'redo',
				className : 'btn-grey'
			} ],
			'wysiwyg' : {
				fileUploadError : showErrorAlert
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

		$('#editor2').css({
			'height' : '200px'
		}).ace_wysiwyg(
				{
					toolbar_place : function(toolbar) {
						return $(this).closest('.widget-box').find(
								'.widget-header').prepend(toolbar).find(
								'.wysiwyg-toolbar').addClass('inline');
					},
					toolbar : [ 'bold', {
						name : 'italic',
						title : 'Change Title!',
						icon : 'ace-icon fa fa-leaf'
					}, 'strikethrough', null, 'insertunorderedlist',
							'insertorderedlist', null, 'justifyleft',
							'justifycenter', 'justifyright' ],
					speech_button : false
				});

		$('[data-toggle="buttons"] .btn').on(
				'click',
				function(e) {
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					var toolbar = $('#editor1').prev().get(0);
					if (which >= 1 && which <= 4) {
						toolbar.className = toolbar.className.replace(
								/wysiwyg\-style(1|2)/g, '');
						if (which == 1)
							$(toolbar).addClass('wysiwyg-style1');
						else if (which == 2)
							$(toolbar).addClass('wysiwyg-style2');
						if (which == 4) {
							$(toolbar).find('.btn-group > .btn').addClass(
									'btn-white btn-round');
						} else
							$(toolbar).find('.btn-group > .btn-white')
									.removeClass('btn-white btn-round');
					}
				});

		//RESIZE IMAGE

		//Add Image Resize Functionality to Chrome and Safari
		//webkit browsers don't have image resize functionality when content is editable
		//so let's add something using jQuery UI resizable
		//another option would be opening a dialog for user to enter dimensions.
		if (typeof jQuery.ui !== 'undefined' && ace.vars['webkit']) {

			var lastResizableImg = null;
			function destroyResizable() {
				if (lastResizableImg == null)
					return;
				lastResizableImg.resizable("destroy");
				lastResizableImg.removeData('resizable');
				lastResizableImg = null;
			}

			var enableImageResize = function() {
				$('.wysiwyg-editor')
						.on(
								'mousedown',
								function(e) {
									var target = $(e.target);
									if (e.target instanceof HTMLImageElement) {
										if (!target.data('resizable')) {
											target.resizable({
												aspectRatio : e.target.width
														/ e.target.height,
											});
											target.data('resizable', true);

											if (lastResizableImg != null) {
												//disable previous resizable image
												lastResizableImg
														.resizable("destroy");
												lastResizableImg
														.removeData('resizable');
											}
											lastResizableImg = target;
										}
									}
								})
						.on(
								'click',
								function(e) {
									if (lastResizableImg != null
											&& !(e.target instanceof HTMLImageElement)) {
										destroyResizable();
									}
								}).on('keydown', function() {
							destroyResizable();
						});
			}

			enableImageResize();

			/**
			//or we can load the jQuery UI dynamically only if needed
			if (typeof jQuery.ui !== 'undefined') enableImageResize();
			else {//load jQuery UI if not loaded
				//in Ace demo ../assets will be replaced by correct assets path
				$.getScript("../assets/js/jquery-ui.custom.min.js", function(data, textStatus, jqxhr) {
					enableImageResize()
				});
			}
			 */
		}

		$.fn.editable.defaults.mode = 'inline';
		$.fn.editableform.loading = "<div class='editableform-loading'><i class='ace-icon fa fa-spinner fa-spin fa-2x light-blue'></i></div>";
		$.fn.editableform.buttons = '<button type="submit" class="btn btn-info editable-submit"><i class="ace-icon fa fa-check"></i></button>'
				+ '<button type="button" class="btn editable-cancel"><i class="ace-icon fa fa-times"></i></button>';

		try {//ie8 throws some harmless exceptions, so let's catch'em

			//first let's add a fake appendChild method for Image element for browsers that have a problem with this
			//because editable plugin calls appendChild, and it causes errors on IE
			try {
				document.createElement('IMG').appendChild(
						document.createElement('B'));
			} catch (e) {
				Image.prototype.appendChild = function(el) {
				}
			}

			var last_gritter
			$('#avatar')
					.editable(
							{
								type : 'image',
								name : 'avatar',
								value : null,
								image : {
									//specify ace file input plugin's options here
									btn_choose : 'Change Avatar',
									droppable : true,
									maxSize : 11000000,//~100Kb

									//and a few extra ones here
									name : 'avatar',//put the field name here as well, will be used inside the custom plugin
									on_error : function(error_type) {//on_error function will be called when the selected file has a problem
										if (last_gritter)
											$.gritter.remove(last_gritter);
										if (error_type == 1) {//file format error
											last_gritter = $.gritter
													.add({
														title : 'File is not an image!',
														text : 'Please choose a jpg|gif|png image!',
														class_name : 'gritter-error gritter-center'
													});
										} else if (error_type == 2) {//file size rror
											last_gritter = $.gritter
													.add({
														title : 'File too big!',
														text : 'Image size should not exceed 1000Kb!',
														class_name : 'gritter-error gritter-center'
													});
										} else {//other error
										}
									},
									on_success : function() {
										$.gritter.removeAll();
									}
								},
								url : function(params) {
									// ***UPDATE AVATAR HERE*** //
									var submit_url = '${pageContext.request.contextPath}/upload';//please modify submit_url accordingly
									var deferred = null;
									var avatar = '#avatar';

									//if value is empty (""), it means no valid files were selected
									//but it may still be submitted by x-editable plugin
									//because "" (empty string) is different from previous non-empty value whatever it was
									//so we return just here to prevent problems
									var value = $(avatar).next().find(
											'input[type=hidden]:eq(0)').val();
									if (!value || value.length == 0) {
										deferred = new $.Deferred
										deferred.resolve();
										return deferred.promise();
									}

									var $form = $(avatar).next().find(
											'.editableform:eq(0)')
									var file_input = $form
											.find('input[type=file]:eq(0)');
									var pk = $(avatar).attr('data-pk');//primary key to be sent to server

									var ie_timeout = null

									if ("FormData" in window) {
										var formData_object = new FormData();//create empty FormData object

										//serialize our form (which excludes file inputs)
										$.each($form.serializeArray(),
												function(i, item) {
													//add them one by one to our FormData 
													formData_object.append(
															item.name,
															item.value);
												});
										//and then add files
										$form
												.find('input[type=file]')
												.each(
														function() {
															var field_name = $(
																	this).attr(
																	'name');
															var files = $(this)
																	.data(
																			'ace_input_files');
															if (files
																	&& files.length > 0) {
																formData_object
																		.append(
																				field_name,
																				files[0]);
															}
														});
										//append primary key to our formData
										formData_object.append('pk', pk);
										deferred = $.ajax({
											url : submit_url,
											type : 'POST',
											processData : false,//important
											contentType : false,//important
											dataType : 'json',//server response type
											data : formData_object
										})
									} else {
										deferred = new $.Deferred

										var temporary_iframe_id = 'temporary-iframe-'
												+ (new Date()).getTime()
												+ '-'
												+ (parseInt(Math.random() * 1000));
										var temp_iframe = $(
												'<iframe id="'+temporary_iframe_id+'" name="'+temporary_iframe_id+'" \
								frameborder="0" width="0" height="0" src="about:blank"\
								style="position:absolute; z-index:-1; visibility: hidden;"></iframe>')
												.insertAfter($form);

										$form
												.append('<input type="hidden" name="temporary-iframe-id" value="'+temporary_iframe_id+'" />');

										//append primary key (pk) to our form
										$('<input type="hidden" name="pk" />')
												.val(pk).appendTo($form);

										temp_iframe.data('deferrer', deferred);
										//we save the deferred object to the iframe and in our server side response
										//we use "temporary-iframe-id" to access iframe and its deferred object

										$form.attr({
											action : submit_url,
											method : 'POST',
											enctype : 'multipart/form-data',
											target : temporary_iframe_id
										//important
										});

										$form.get(0).submit();

										//if we don't receive any response after 30 seconds, declare it as failed!
										ie_timeout = setTimeout(function() {
											ie_timeout = null;
											temp_iframe.attr('src',
													'about:blank').remove();
											deferred.reject({
												'status' : 'fail',
												'message' : 'Timeout!'
											});
										}, 30000);
									}

									//deferred callbacks, triggered by both ajax and iframe solution
									deferred
											.done(
													function(result) {//success
														var res = result;//the `result` is formatted by your server side response and is arbitrary
														if (res.status == 'OK') {
															$(avatar).get(0).src = res.url;
															$(
																	"input[name='logo']")
																	.val(
																			res.logo);
														} else
															alert(res.message);
													}).fail(function(result) {//failure
												alert(result.message);
											}).always(function() {//called on both success and failure
												if (ie_timeout)
													clearTimeout(ie_timeout)
												ie_timeout = null;
											});

									return deferred.promise();
									// ***END OF UPDATE AVATAR HERE*** //
								},

								success : function(response, newValue) {
									$("#avatar").attr("style",
											"display: block;");
									$(".editableform-loading").attr("style",
											"display: none;");
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

		} catch (e) {
		}

	});

	function toVaild() {
		$("input[name='desc']").val($('#editor1').html());
		$("#status").val(true);
		$("#scoreShopItem").submit();
	}
	function toVaild2() {
		$("input[name='desc']").val($('#editor1').html());
		$("#status").val(false);
		$("#scoreShopItem").submit();
	}
</script>
