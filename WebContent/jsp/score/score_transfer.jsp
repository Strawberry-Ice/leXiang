<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- /section:basics/sidebar -->
<div class="main-content">
	<div class="main-content-inner">
		<div class="page-content">
			<!-- #section:settings.box -->

			<!-- /section:settings.box -->
			<div class="page-header">
				<h1>
					积分管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
						划拨积分
					</small>
				</h1>
			</div>


			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<form:form commandName="scoreTransfer" action="${pageContext.request.contextPath }/score/shop/doTransfer" class="form-horizontal" role="form">
								<div
									class="middle"><font color="red"><form:errors
												path="orgIds" /></font></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 街道或社工委 </label>
							<div class="col-sm-9">
								<div class="space-2"></div>
								<form:select  path="orgIds" items="${govOrgs }" multiple="multiple" class="chosen-select form-control" id="form-field-select-4"
									data-placeholder="请选择街道或社工委..." itemValue="id" itemLabel="name"></form:select>
							</div>
						</div>
						
						<c:forEach items="${govOrgs}" var="govOrg">
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right"
									for="form-field-1"> ${govOrg.name }(请选择社区)</label>
								<div class="col-sm-9">
									<div class="space-2"></div>
	
									<form:select path="orgIds" items="${govOrg.children }" multiple="multiple" class="chosen-select form-control" id="form-field-select-4"
									data-placeholder="请选择社区..." itemValue="id" itemLabel="name"></form:select>
								</div>
							</div>
						</c:forEach>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-5">划拨积分</label>

							<div class="col-sm-9">
								<div class="clearfix">
									<form:input path="score" class="col-xs-1"
										id="form-field-5" placeholder="0" />
										 <span
									class="middle"><font color="red"><form:errors
												path="score" /></font></span>
								</div>
							</div>
						</div>



						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
								<button class="btn btn-info" type="submit">
									<i class="ace-icon fa fa-check bigger-110"></i> 提交
								</button>

								&nbsp; &nbsp; &nbsp;
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

<script type="text/javascript">
	window.jQuery
			|| document.write("<script src='../assets/js/jquery.js'>"
					+ "<"+"/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='../assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
<script type="text/javascript">
	if ('ontouchstart' in document.documentElement)
		document.write("<script src='${pageContext.request.contextPath}/assets/js/jquery.mobile.custom.js'>"
				+ "<"+"/script>");
</script>
<script
	src="${pageContext.request.contextPath }/assets/js/chosen.jquery.js"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">
	jQuery(function($) {

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

		if (!ace.vars['touch']) {
			$('.chosen-select').chosen({
				allow_single_deselect : true
			});
			//resize the chosen on window resize

			$(window).off('resize.chosen').on('resize.chosen', function() {
				$('.chosen-select').each(function() {
					var $this = $(this);
					$this.next().css({
						'width' : $this.parent().width()
					});
				})
			}).trigger('resize.chosen');
			//resize chosen on sidebar collapse/expand
			$(document).on('settings.ace.chosen',
					function(e, event_name, event_val) {
						if (event_name != 'sidebar_collapsed')
							return;
						$('.chosen-select').each(function() {
							var $this = $(this);
							$this.next().css({
								'width' : $this.parent().width()
							});
						})
					});

			$('#chosen-multiple-style .btn').on('click', function(e) {
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				if (which == 2)
					$('#form-field-select-4').addClass('tag-input-style');
				else
					$('#form-field-select-4').removeClass('tag-input-style');
			});
		}
		if('${msg}'){
			alert('${msg}');
		}

	});
</script>