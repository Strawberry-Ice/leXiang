<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div id="navbar" class="navbar navbar-default">
	<script type="text/javascript">
		try {
			ace.settings.check('navbar', 'fixed')
		} catch (e) {
		}
	</script>

	<div class="navbar-container" id="navbar-container">
		<!-- #section:basics/sidebar.mobile.toggle -->
		<button type="button" class="navbar-toggle menu-toggler pull-left"
			id="menu-toggler" data-target="#sidebar">
			<span class="sr-only">Toggle sidebar</span> <span class="icon-bar"></span>

			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>

		<!-- /section:basics/sidebar.mobile.toggle -->
		<div class="navbar-header pull-left">
			<!-- #section:basics/navbar.layout.brand -->
			<a href="#" class="navbar-brand"> <small> <i
					class="fa fa-leaf"></i> 积分兑换系统
			</small>
			</a>
		</div>

		<!-- #section:basics/navbar.dropdown -->
		<div class="navbar-buttons navbar-header pull-right" role="navigation">
			<ul class="nav ace-nav">
				<!-- #section:basics/navbar.user_menu -->
				<li class="light-blue"><a data-toggle="dropdown" href="#"
					class="dropdown-toggle"> <img class="nav-user-photo"
						src="${pageContext.request.contextPath }<sec:authentication property="principal.logo"/>" alt="<sec:authentication property="principal.nickName"/>" onerror="javascript:this.src='${pageContext.request.contextPath }/assets/images/no-pic.jpg';"/> <span
						class="user-info"> <small>Welcome,</small> <sec:authentication property="principal.nickName"/>
					</span>
				</a></li>

				<!-- /section:basics/navbar.user_menu -->
			</ul>
		</div>

		<!-- /section:basics/navbar.dropdown -->
	</div>
	<!-- /.navbar-container -->
</div>