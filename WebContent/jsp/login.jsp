<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>登陆 -乐享后台管理系统</title>
<meta name="description" content="User login page" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<jsp:include page="../init/init.jsp"></jsp:include>
</head>
<body class="login-layout blur-login">
	<div class="main-container">
		<div class="main-content">
			<div class="row">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="login-container">
						<div class="center">
							<h1>

								<span class="white" id="id-text2">社区-积分兑换系统</span>
							</h1>
						</div>

						<div class="space-6"></div>

						<div class="position-relative">
							<div id="login-box"
								class="login-box visible widget-box no-border">
								<div class="widget-body">
									<div class="widget-main" id="eventForm">
										<h4 class="header blue lighter bigger">
											<i class="ace-icon fa fa-coffee green"></i> 请登陆
										</h4>
										${error }
										<div class="space-6"></div>
										<form method="post"
											action="${pageContext.request.contextPath }/j_spring_security_check"
											id="loginForm">
											<!--<form action="loginController.do" id="loginForm" method="post">-->
											<fieldset>
												<label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="text" class="form-control" id="userName"
														placeholder="用户名" name="username" /> <i
														class="ace-icon fa fa-user"></i>
												</span> 
												</label> <label class="block clearfix"> <span
													class="block input-icon input-icon-right"> <input
														type="password" class="form-control" id="password"
														placeholder="密码" name="password" /> <i
														class="ace-icon fa fa-lock"></i>
												</span> 
												</label>

												<div class="clearfix">
													<label class="inline"> <input
														class="form-control" placeholder="验证码" id="kaptcha"
														maxlength="4" />
													</label> <label class="inline"> <img
														src="${pageContext.request.contextPath }/captchaController/image"
														width="55" height="34" id="kaptchaImage" />
													</label>
												</div>
												<div class="help-block col-xs-12 col-sm-reset inline"
													id="errorMsg"><font color='red'>${msg}</font></div>
												<div class="space"></div>

												<div class="clearfix">
													<!--<label class="inline">
															<input type="checkbox" class="ace" />
															<span class="lbl"> Remember Me</span>
														</label>  -->

													<button type="button" id="eventClick"
														class="width-35 pull-right btn btn-sm btn-primary"
														onclick="checkLogin();">
														<i class="ace-icon fa fa-key"></i> <span
															class="bigger-110">登陆</span>
													</button>
												</div>

												<div class="space-4"></div>
											</fieldset>
										</form>
									</div>
									<!-- /.widget-main -->

									<div class="toolbar clearfix">
										<div>
											<!--<a href="#" data-target="#forgot-box" class="forgot-password-link">
													<i class="ace-icon fa fa-arrow-left"></i>
													I forgot my password
												</a>  -->
										</div>

										<div>
											<!--<a href="#" data-target="#signup-box" class="user-signup-link">
													注册
													<i class="ace-icon fa fa-arrow-right"></i>
												</a>-->
										</div>
									</div>
								</div>
								<!-- /.widget-body -->
							</div>
							<!-- /.login-box -->

						</div>
						<!-- /.position-relative -->
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->

	<!--[if !IE]> -->
	<script type="text/javascript">
		
		
			window.jQuery || document.write("<script src='<%=request.getContextPath()%>/assets/js/jquery.js'>"+"<"+"/script>");
		</script>

	<!-- <![endif]-->

	<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='<%=request.getContextPath()%>/assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
	<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='${pageContext.request.contextPath}/assets/js/jquery.mobile.custom.js'>"
							+ "<"+"/script>");
	</script>

	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		$(function(){
			$('#eventForm').find('input').on('keyup', function(event) {/* 增加回车提交功能 */
				if (event.keyCode == '13') {
					$("#eventClick").click();
				}
			});
		})
	
		function checkLogin() {
			$('#errorMsg').html("");
			var errorCode = "";
			if ($('#userName').val() == "") {
				errorCode += errorCode == "" ? "请输入用户名！！" : "<br/>请输入用户名！！";
			}
			if ($('#password').val() == "") {
				errorCode += errorCode == "" ? "请输入密码！！" : "<br/>请输入密码！！";
			}
			if ($('#kaptcha').val() == "") {
				errorCode += errorCode == "" ? "请输入验证码！！" : "<br/>请输入验证码！！";
			}
			if ("" != errorCode) {
				$('#errorMsg').html(
						"<font color='red'>" + errorCode + "</font>")
			} else {
				$.get("${pageContext.request.contextPath}/doKaptcha", {
					kaptcha : $('#kaptcha').val(),
					time : new Date().getTime()
				},
						function(data) {
							if (data == "true") {
								$('#loginForm').submit();
							} else {
								$('#errorMsg').html(
										"<font color='red'>验证码错误！！</font>");
								$('#kaptchaImage').click();
								$('#kaptcha').val("");
							}
						});

			}

		}

		$('#kaptchaImage')
				.click(
						function() {//生成验证码
							$(this).hide().attr(
									'src',
									'${pageContext.request.contextPath}/captchaController/image'
											+ Math.floor(Math.random() * 100))
									.fadeIn();
							event.cancelBubble = true;
						});

		jQuery(function($) {
			$(document).on('click', '.toolbar a[data-target]', function(e) {
				e.preventDefault();
				var target = $(this).data('target');
				$('.widget-box.visible').removeClass('visible');//hide others
				$(target).addClass('visible');//show target
			});
		});
	</script>
</body>
</html>