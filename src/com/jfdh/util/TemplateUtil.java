package com.jfdh.util;

import com.jfdh.controller.weichat.util.HttpUtil;
import com.jfdh.httpweichatmodel.SendMessage;


public class TemplateUtil {
	public static String CONSTANTS_NOTICE="YvfanS0gANFm15YQGawZvaXfhDVPhDc2BES8RpOmWq8";
	
	public static String CONSTANTS_APPLICATION="Vicqj2FRWux_fjyxhHxNRKawfTt8trdSPnIlzyNrqC0";
	
	public static String CONSTANTS_SCORE="3KaMUInOg5i8Sb4LQd_OQmguY1My47I6KOmhW6Mva3s";
	
	public static String sendNoticeMessage(SendMessage message){
		String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+message.getAccess_token();
		String url2="http://www.headline.cc/smilecommunity/weichatActivity/showTemplateMessage/"+message.getCode()+"/"+message.getOpenId()+"/"+message.getActivityId();
		String strJson="{\"touser\":\""+message.getOpenId()+"\",\"template_id\":\""+CONSTANTS_NOTICE+"\",";
		   strJson+="\"url\":\""+url2+"\",";
		   strJson+="\"data\":{";
		   strJson+="\"first\": {\"value\":\""+message.getFirst()+"\"},";
		   strJson+="\"keyword1\":{\"value\":\""+message.getKeyword1()+"\"},";
		   strJson+="\"keyword2\":{\"value\":\""+message.getKeyword2()+"\"},";
		   strJson+="\"remark\":{\"value\":\""+message.getRemark()+"\"}";
		   strJson+="}";
		   strJson+="}";
		   String result=HttpUtil.httpRequest(url, "POST", strJson);
		   return result;
	}
	
	public static String sendApplyMessage(SendMessage message){
		String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+message.getAccess_token();
		String url2="http://www.headline.cc/smilecommunity/weichatActivity/showTemplateMessage/"+message.getCode()+"/"+message.getOpenId()+"/"+message.getActivityId();
		String strJson="{\"touser\":\""+message.getOpenId()+"\",\"template_id\":\""+CONSTANTS_APPLICATION+"\",";
		   strJson+="\"url\":\""+url2+"\",";
		   strJson+="\"data\":{";
		   strJson+="\"first\": {\"value\":\""+message.getFirst()+"\"},";
		   strJson+="\"keyword1\":{\"value\":\""+message.getKeyword1()+"\"},";
		   strJson+="\"keyword2\":{\"value\":\""+message.getKeyword2()+"\"},";
		   strJson+="\"keyword3\":{\"value\":\""+message.getKeyword3()+"\"},";
		   strJson+="\"remark\":{\"value\":\""+message.getRemark()+"\"}";
		   strJson+="}";
		   strJson+="}";
		   String result=HttpUtil.httpRequest(url, "POST", strJson);
		   return result;
	}
	
	public static String sendScoreMessage(SendMessage message){
		String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+message.getAccess_token();
		String url2="http://www.headline.cc/smilecommunity/weichatActivity/weichatShowUserInfo/"+message.getOpenId();
		String strJson="{\"touser\":\""+message.getOpenId()+"\",\"template_id\":\""+CONSTANTS_SCORE+"\",";
		   strJson+="\"url\":\""+url2+"\",";
		   strJson+="\"data\":{";
		   strJson+="\"first\": {\"value\":\""+message.getFirst()+"\"},";
		   if(StringUtil.isNotNull(message.getFieldName())){
			   strJson+="\"FieldName\":{\"value\":\""+message.getFieldName()+"\"},";
		   }
		   if(StringUtil.isNotNull(message.getAccount())){
			   strJson+="\"Account\":{\"value\":\""+message.getAccount()+"\"},";
		   }
		   strJson+="\"change\":{\"value\":\""+message.getChange()+"\"},";
		   strJson+="\"CreditChange\":{\"value\":\""+message.getCreditChange()+"\"},";
		   strJson+="\"CreditTotal\":{\"value\":\""+message.getCreditTotal()+"\"},";
		   strJson+="\"remark\":{\"value\":\""+message.getRemark()+"\"}";
		   strJson+="}";
		   strJson+="}";
		   String result=HttpUtil.httpRequest(url, "POST", strJson);
		   return result;
	}
}
