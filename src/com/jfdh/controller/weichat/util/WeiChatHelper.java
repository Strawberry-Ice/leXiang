package com.jfdh.controller.weichat.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfdh.controller.weichat.message.Field;
import com.jfdh.controller.weichat.message.Message;

@Service
public class WeiChatHelper {
	@Value("#{configProperties['weichatToken']}")
	private String weichatToken;

	@Value("#{configProperties['appid']}")
	private String appid;
	@Value("#{configProperties['secret']}")
	private String secret;
	
	private String accessToken;
	
	private long startTime;
	
	private long startTickeTime;
	
	private String ticket;
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	private String getToken() throws JsonProcessingException, IOException{
		String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
		String json=HttpUtil.getUrl(url);
		ObjectMapper mapper = new ObjectMapper();  
		JsonNode rootNode = mapper.readTree(json); 
		return rootNode.path("access_token").asText();
	}
	
	public String getAccessToken() throws JsonProcessingException, IOException{
		if(startTime==0){
			startTime=System.currentTimeMillis();
		}
		while(StringUtils.isEmpty(accessToken)||System.currentTimeMillis()-startTime>60*60*1000){
			accessToken=getToken();
			startTime=System.currentTimeMillis();
		}
	
		return accessToken;
	}
	
	public String getAccessTicket() throws JsonProcessingException, IOException{
		if(startTickeTime==0){
			startTickeTime=System.currentTimeMillis();
		}
		while(StringUtils.isEmpty(ticket)||System.currentTimeMillis()-startTickeTime>60*60*1000){
			ticket=getTicket();
			startTickeTime=System.currentTimeMillis();
		}
	
		return ticket;
	}
	
	private String getTicket() throws JsonProcessingException, IOException{
		String token=getAccessToken();
		String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi";
		String json=HttpUtil.getUrl(url);
		ObjectMapper mapper = new ObjectMapper();  
		JsonNode rootNode = mapper.readTree(json); 
		return rootNode.path("ticket").asText();
	}
	
	
	private JsonNode getTokenByCode(String code) throws JsonProcessingException, IOException{
		String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		String jsonString=HttpUtil.getUrl(url);
		ObjectMapper mapper = new ObjectMapper();  
		JsonNode rootNode = mapper.readTree(jsonString); 
		return rootNode;
	}
	
	public JsonNode getUserInfo(String code) throws JsonProcessingException, IOException{
		JsonNode json=getTokenByCode(code);
		String access_token=json.path("access_token").asText();
		String openid=json.path("openid").asText();
		String url="https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
		String jsonString=HttpUtil.getUrl(url);
		ObjectMapper mapper = new ObjectMapper();  
		JsonNode jsonReturn = mapper.readTree(jsonString); 
		return jsonReturn;
	}
	
	public JsonNode getTemplate() throws JsonProcessingException, IOException{
		String token=getAccessToken();
		String url="https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token="+token;
		String jsonString=HttpUtil.httpRequest(url, "post", "industry_id1:39");
		ObjectMapper mapper = new ObjectMapper();  
		JsonNode rootNode = mapper.readTree(jsonString); 
		return rootNode;
	}
	
	private String getTemplateId() throws JsonProcessingException, IOException{
		String token=getAccessToken();
		String url="https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token"+token;
		String jsonString=HttpUtil.httpRequest(url,"post",null);
		ObjectMapper mapper = new ObjectMapper();  
		JsonNode rootNode = mapper.readTree(jsonString); 
		return rootNode.path("template_id_short").asText();
	}
	
	
	public static void sendMessage(String openid){
		String token="lctXI7WB2kjhsunVTrmd0DEXwTW2L4IbH0rodCdedt1SCZpZ9QestLArlYivq6NcGrs95y2pJ6KMOnlAHtsqUp6di4go-4wVzV8-F4PdHT8";
		Message message=new Message();
		message.setTouser("oynFNuD0yadopNg_Lxy_ywoaKnBk");
		message.setTemplate_id("F2bLP1IC2DuhWo9R3pl1AZ1FFukIbxk_jW5rquknDkU");
		message.setUrl("http://www.baidu.com");
		message.setTopcolor("#E47834");
		Map<String,Field> data=new HashMap<String,Field>();
		Field field=new Field();
		field.setValue("你好，世界！！");
		field.setColor("#FDBEEF");
		data.put("hello",field);
		data.put("hello2",field);
		data.put("hello3",field);
		message.setData(data);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonfromList = mapper.writeValueAsString(message);
			System.out.println(jsonfromList);
			String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token;
			String jsonString=HttpUtil.httpRequest(url,"POST",jsonfromList);
			System.out.println(jsonString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}  
	}
	public static void main(String[] args) {
		sendMessage(null);
	}
}
