<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
<!--    <meta name="format-detection"content="telephone=yes, email=yes" />-->
    <title>微笑生活</title>
    <meta name="description" content="" />
	<meta name="keywords" content="" />
	<jsp:include page="weichart_js_and_css.jsp"></jsp:include>
</head>
<body>
	<header id="head" class="header fixed-top">
		<img src="${pageContext.request.contextPath }/weichat/images/head_bg.jpg" alt="head_img">
	</header>
	<section id="main" class="wrap-page index-page">
		<div class="page pd10" style="margin-top: 70px;">
            <form:form commandName="httpUser" class="form border br5" action="${pageContext.request.contextPath }/weichatCreateUser" id="form" method="post">
            	<form:hidden path="openid"/>
            	<form:hidden path="needReceive"/>
            	<form:hidden path="id"/>
            	<form:hidden path="address"/>
            	<form:hidden path="headimgurl"/>
                <div class="form-group">
                    <dl>
                        <dt>用户昵称</dt>
                        <dd><form:input path="nickName"/></dd>
                    </dl>
                </div>
                <div class="form-group">
                    <dl>
                        <dt>手机号码</dt>
                        <dd><form:input path="tel"/></dd>
                    </dl>
                </div>
                <div class="form-group">
                    <dl>
                        <dt>我的社区</dt>
                            <dd>
                                <div class="mt10 mb10 border br5">
                                    <div class="pd10 choose-local">
                                        <i class="icon-arrow-down fr gray"></i>
                                        <input type="text" value="" placeholder="区域" id="parent" onclick="showParent();" readonly="readonly">
                                    </div>
                                    <input type="hidden" name="govOrgId" id="govOrgId">
                                    <div class="list-community line-list br5" style="display: none;" id="parentul">
                                        <ul>
                                        	<c:forEach items="${govOrgs }" var="govOrg">
                                            <li value="${govOrg.id }" onclick="loadChildren(this);">${govOrg.name }</li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </dd>
                    </dl>
                </div>
                <div class="form-group">
                    <dl>
                    <dt></dt>
                        <dd>
                            <div class="mt10 mb10 border br5">
                                <div class="pd10 choose-local">
                                    <i class="icon-arrow-down fr gray"></i>
                                    <input type="text" value="" placeholder="社区" id="child" onclick="showChild();" readonly="readonly">
                                </div>
                                <div class="list-community line-list br5" style="display: none;" id="childul">
                                    <ul id="children">
                                        
                                    </ul>
                                </div>
                            </div>
                        </dd>
                    </dl>
                </div>
                <div class="form-btn">
                    <input type="submit" name="mode" value="绑定帐号" class="btn btn-orange">
                </div>
                <div class="form-tips">
                    <p>注：亲，暂时不支持修改社区，慎重哦！</p>
                </div>
            </form:form>
            <form:form commandName="httpUser" class="form border br5" action="${pageContext.request.contextPath }/weichatCreateUser" id="form">
           		<form:hidden path="openid"/>
            	<form:hidden path="id"/>
            	<form:hidden path="address"/>
            	<form:hidden path="headimgurl"/>
            	<form:hidden path="nickName"/>
            <div class="form border br5">
                <div class="form-group">
                    <dl>
                        <dt>暂不绑定</dt>
                    </dl>
                </div>
                <div class="form-btn">
                	
                    <a href="#" class="btn btn-orange" onclick="submit();">随便逛逛</a>
                </div>
            </div>
            </form:form>
            
		</div>
	</section>
</body>
<script type="text/javascript">
	function submit(){
		$('#form').submit();
	}

	function showParent(){
		$('#parentul').show();
	}
	
	function showChild(){
		$('#childul').show();
	}

	function loadChildren(obj){
		var id=$(obj).attr('value');
		$.ajax({
			url : '${pageContext.request.contextPath }/weiChatGovOrgChildren?id='+id,
			cache : false,
			dataType : "json",
			success : function(r) {
				var govOrgs=r.obj;
				$('#children').html('');
				for(var i=0;i<govOrgs.length;i++){
					var govOrg=govOrgs[i];
					$('#children').append("<li value='"+govOrg.id+"' onclick='setValue(this);'>"+govOrg.name+"</li>");
				}
				$('#parent').val($(obj).text());
				$('#parentul').hide();
			}
		});
	}
	
	function setValue(obj){
		$('#child').val($(obj).text());
		$('#childul').hide();
		$('#govOrgId').val($(obj).attr('value'));
	}
</script>
</html>