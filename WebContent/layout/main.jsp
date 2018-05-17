<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title><tiles:getAsString name="title" ignore="true"/></title>
<meta name="description" content="User login page" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<jsp:include page="../init/init.jsp"></jsp:include>

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
			if('ontouchstart' in document.documentElement) document.write("<script src='<%=request.getContextPath()%>/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>

		<jsp:include page="../init/initjs.jsp"></jsp:include>

</head>

<body class="no-skin">
		<!-- #section:basics/navbar.layout -->
		<tiles:insertAttribute name="header" />

		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">
			<tiles:insertAttribute name="left" />

			<!-- /section:basics/sidebar -->
			<tiles:insertAttribute name="main" />
			<!-- /.main-content -->

			<tiles:insertAttribute name="footer" />
		</div><!-- /.main-container -->

</body>