<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
	dd.error {border:0 none;color:red;}
</style>

<section id="main" class="wrap-page">
	<div class="page">
			<div class="mt10 mb10" <c:if test="${code=='04' or code=='03'}">style="margin-top: 110px;"</c:if>>
				<div class="add-bbs">
					<form action="${pageContext.request.contextPath }/weichatActivity/save" class="mt10 add-form" id="form" method="post">
					    <input type="hidden" name="id" value="${id }">
					    <input type="hidden" name="type" value="${type }" id="type">
						<div class="">
						<c:forEach var="httpFieldType" items="${list }">
							<c:choose>
								<c:when test="${httpFieldType.type=='input' }">
									<dl>
										<dt>${httpFieldType.name }：<sub class="red">*</sub></dt>
										<dd><input type="text" name="${httpFieldType.id }" value="${httpFieldType.value }"></dd>
										<dd class="error"></dd>
									</dl>
								</c:when>
								<c:when test="${httpFieldType.type=='radio' }">
									<dl>
										<dt>${httpFieldType.name }：<sub class="red">*</sub></dt>
										<dd>
											<input style="width: 30px;" type="radio" name="${httpFieldType.id }" value="男" checked="checked">男<input style="width: 30px;" value="女" type="radio" name="${httpFieldType.id }">女
										</dd>
										<dd class="error"></dd>
									</dl>
								</c:when>
								<c:when test="${httpFieldType.type=='textarea' }">
									<dl>
										<dt>${httpFieldType.name }：<sub class="red">*</sub></dt>
										<dd>
											<textarea rows="2" cols="10"  name="${httpFieldType.id }">${httpFieldType.value }</textarea>
										</dd>
										<dd class="error"></dd>
									</dl>
								</c:when>
							</c:choose>
							
						</c:forEach>
							<div class="activity-btn">
								<a href="#" onclick="entry();">报名</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
</section>

<script type="text/javascript">
	function entry(){
		var form = document.getElementById("form");
		var tagElements = form.getElementsByTagName('input');
		var flag=true;
		for (var j = 0; j < tagElements.length; j++){  
			var value=tagElements[j].value;
			
			var name=((tagElements[j].parentNode.parentNode).childNodes[1]).innerText;
	      	var ch = new Array;
	      	ch = name.split("：");
			
			 if(value==null || value==''){
	        	  $((tagElements[j].parentNode.parentNode).childNodes[5]).html(ch[0]+"不能为空!");
	        	  
	        	  flag=false;
	          }else{
	        	  if(ch[0]=='邮箱'){
	        		    var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;  
	        		    if (!pattern.test(value)) {  
	        		    	$((tagElements[j].parentNode.parentNode).childNodes[5]).html(ch[0]+'格式不正确！');
	        		    	flag=false;
	        		    }   
	        	  }
	          }
	          
	    } 
		
		tagElements = form.getElementsByTagName('textarea');
		for (var i = 0; i < tagElements.length; i++){  
			var value=$(tagElements[i]).val();
	          if(value==null || value==''){
	        	  var name=((tagElements[i].parentNode.parentNode).childNodes[1]).innerText;
	        	  var ch = new Array;
	        	  ch = name.split("：");
	        	  $((tagElements[i].parentNode.parentNode).childNodes[5]).html(ch[0]+'不能为空！');
	        	  flag=false;
	          }
	    } 
		if(flag){
			$.ajax({
				url : '${pageContext.request.contextPath }/weichatActivity/save',
				cache : false,
				type : "post",
				dataType : "json",
				data : $("#form").serialize(),
				success : function(r) {
					layer.open({
					    content: r.msg,
					    style: 'background-color:#09C1FF; color:#fff; border:none;',
					    time: 2
					});
					
					if(r.success){
						setTimeout(setUrl,2000)
					}
				}
			});
			
		}
	}
	
	function setUrl(){
		location.href='${pageContext.request.contextPath }/weichatActivity/listAllActivity/'+$('#type').val()+'/01'
	}
</script>


