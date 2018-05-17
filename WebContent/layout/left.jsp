<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
	try{ace.settings.check('main-container' , 'fixed')}catch(e){}
</script>

<!-- #section:basics/sidebar -->
<div id="sidebar" class="sidebar                  responsive">
	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
	</script>


	<ul class="nav nav-list" id="menu">
			
	</ul><!-- /.nav-list -->

	<!-- #section:basics/sidebar.layout.minimize -->
	<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
		<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
	</div>

	<!-- /section:basics/sidebar.layout.minimize -->
	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
		$(function(){
			
			$.ajax({
				url : '${pageContext.request.contextPath }/activity/getMenu',
				cache : false,
				dataType : "json",
				success : function(r) {
					var httpMenuList=r.obj;
					var length=r.obj.length;
					var str="";
					for(var i=0;i<length;i++){
						var children=httpMenuList[i].menusList;
						str+="<li class=''>";
						if(children.length>0){
							str+="<a href='${pageContext.request.contextPath }"+httpMenuList[i].url+"' class='dropdown-toggle'>";
							
						}else{
							str+="<a href='${pageContext.request.contextPath }"+httpMenuList[i].url+"'>";
						}
						
						str+="<i class='"+httpMenuList[i].logo+"'></i>"+
						"<span class='menu-text'>"+httpMenuList[i].name+" </span>";
						if(children.length>0){
							str+="<b class='arrow fa fa-angle-down'></b>";
						}
						str+="</a>"+
						"<b class='arrow'></b>";
						if(children.length>0){
							str+="<ul class='submenu'>";
							for(var j=0;j<children.length;j++){
								str+="<li class=''><a href='${pageContext.request.contextPath }"+children[j].url+"'>"+
								"<i class='"+children[j].logo+"'></i>"+children[j].name+"</a><b class='arrow'></b></li>"
								}
							str+="</ul>";
						}
						
						
						"</li>";
					}
					
					$('#menu').html(str);
					
					menu();
					
				}
			});
			
		});
		
		function menu(){
			var titleValue=$.trim($('title').text());
			var objs=$('.menu-text');
			for(var i=0;i<objs.length;i++){
				if($.trim($(objs[i]).text())===titleValue){
					$(objs[i]).parent().parent().addClass('active');
					return;
				}
			}
			var objsUl=$('.submenu > li > a');
			for(var j=0;j<objsUl.length;j++){
				if($.trim($(objsUl[j]).text())===titleValue){
						
					<c:choose>
						<c:when test="${code =='04'}">
							var name=$.trim($(objsUl[j]).parent().parent().parent().children('a').text());
							if(name==='招募'){
								$(objsUl[j]).parent().addClass('active');
								$(objsUl[j]).parent().parent().parent().addClass('open active');
							}
						</c:when>
						<c:otherwise>
							$(objsUl[j]).parent().addClass('active');
							$(objsUl[j]).parent().parent().parent().addClass('open active');
							return;
						</c:otherwise>
					</c:choose>
					
				}
			}
			
		}
	</script>
</div>
