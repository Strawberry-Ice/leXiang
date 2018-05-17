<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fytec"  uri="/fengyuntec.com" %>
<script src="${pageContext.request.contextPath }/weichat/js/mobiscroll_date.js"></script>
<script src="${pageContext.request.contextPath }/weichat/js/mobiscroll.js"></script>
<link href="${pageContext.request.contextPath }/weichat/css/mobiscroll.css" rel="stylesheet">
<link href="${pageContext.request.contextPath }/weichat/css/mobiscroll_date.css" rel="stylesheet">
<style type="text/css">
dd.error {
	border: 0 none;
	color: red;
}

.add-form dd label {
	padding: 6px 3px;
}

.add-form dd label input {
	width: 10%;
	padding: 6px 3px;
	border: 1px solid #d5d5d5;
	width: auto;
	vertical-align: middle;
	margin-right: 2px;
}

.upload {
	position: absolute;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	opacity: 0;
	display: block;
	z-index: 10;
}

.thumbs img {
	width: 100%;
}

.uploadwrap {
	position: relative;
	line-height: 32px;
}

.uploadwrap a {
	padding: 0;
}
</style>
<section id="main" class="wrap-page">
		<div class="page">
			<div class="mt10 mb10">
			<form:form method="post" modelAttribute="httpActivity"
				action="${pageContext.request.contextPath }/weichatActivity/"
				cssClass="mt10 mb10 add-form" id="activityForm"
				cssStyle="margin-top: 60px;padding-bottom: 40px;"
				enctype="multipart/form-data">
				<dl>
					<dt>互助名称：</dt>
					<dd class="text-left">
						<form:input path="name" placeholder="互助名称" />
					</dd>
					<dd class="error">
						<form:errors path="name" />
					</dd>
				</dl>
				<dl>
					<dt>图片：</dt>
					<dd>
						<p id="thumbs" class="thumbs">
							
						</p>
						<c:if test="${not empty imgUrl}"></c:if>
						<div class="activity-btn">
							<p class="uploadwrap">

								<a href="javascript:void(0);">图片上传</a> <input id="input"
									name="file" type="file" size="10" multiple="true"
									class="upload" onchange="imagesSelected(this.files)" />
								<form:hidden path="imgUrl" id="imgUrl" />
							</p>
						</div>
					</dd>
				</dl>
				<dl>
					<dt>互助内容：</dt>
					<dd>
						<form:textarea path="content" cssStyle="overflow-y: hidden; height:70px;" onpropertychange="changeHeight(this);" oninput="changeHeight(this);"/>
					</dd>
					<dd class="error">
						<form:errors path="content" />
					</dd>
				</dl>
				<dl>
					<dt></dt>
					<dd>
						<label for=""><fytec:checkbox path="needPropose" value="true" />允许推荐</label>
					</dd>
					<dd class="error">
						<form:errors path="needPropose" />
					</dd>
				</dl>
				<div class="activity-btn">
					<a href="#" onclick="submit();">发布</a>
				</div>
				<input type="hidden" name="id" value="${httpActivity.id}" />
				<input type="hidden" name="needVerify" value="false" />
				<input type="hidden" name="valid" value="true" />
				<input type="hidden" name="type" value="${type }" />
				<input type="hidden" name="code" value="${code }" />
			</form:form>
		</div>
		</div>
</section>
<script type="text/javascript">

$(function () {

});

function submit(){
	layer.open({
	    content: '保存中，请稍后！',
	    style: 'background-color:#09C1FF; color:#fff; border:none;',
	    time: 2
	});
	$('#activityForm').submit();
}

function imagesSelected(myFiles) {
	  for (var i = 0, f; f = myFiles[i]; i++) {
	    var imageReader = new FileReader();
	    imageReader.onload = (function(aFile) {
	      return function(e) {
	       $('#thumbs').html('<img class="images" src="'+e.target.result+'" title="'+aFile.name+'"/>');
	       $('#imgUrl').val(e.target.result);
	      };
	    })(f);
	    imageReader.readAsDataURL(f);
	  }
	}

</script>


