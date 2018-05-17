package com.jfdh.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jfdh.controller.weichat.util.WeiChatHelper;
import com.jfdh.httpweichatmodel.SendMessage;
import com.jfdh.model.Activity;
import com.jfdh.model.User;
import com.jfdh.repository.UserRepository;
import com.jfdh.service.ISendMessageService;
import com.jfdh.service.thread.SendNoticeThread;
import com.jfdh.util.DateUtil;
import com.jfdh.util.TemplateUtil;
@Service("sendService")
public class SendMessageImpl extends BaseServiceImpl implements
		ISendMessageService {
	
	public static final Logger LOG = LoggerFactory
			.getLogger(SendMessageImpl.class);
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WeiChatHelper weiChatHelper;

	@Override
	public void SendNoticeMessage(Activity activity,String govOrgId,String code) {
		
		try {
			String access_token=weiChatHelper.getAccessToken();
			List<User> list=userRepository.findByGovOrg_Id(govOrgId);
			
			SendNoticeThread t=new SendNoticeThread(activity,govOrgId,code,access_token,list);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void SendApplyMessage(Activity activity, User user,String msg) {
		
		try {
			String access_token=weiChatHelper.getAccessToken();
			System.out.println("msg >>>>"+msg);
			SendMessage message=new SendMessage();
			message.setOpenId(user.getOpenid());
			message.setCode(activity.getCategory().getCode());
			message.setActivityId(activity.getId());
			message.setAccess_token(access_token);
			String first=msg;
			String keyword1=activity.getName();
			String keyword2=activity.getSponsor();
			String remark=activity.getRemark();
			remark+="——『如不想收到本社区通知，可进入微笑社区，在“个人中心”里关闭』";
			Date activtyStartDate = activity.getActivtyStartDate();
			if(activtyStartDate!=null){
				String keyword3=DateUtil.format(activtyStartDate, "yyyy-MM-dd")+" "+activity.getActivtyStartTimes();
				message.setKeyword3(keyword3);
				System.out.println("keyword3 >>>>"+keyword3);
			}
			message.setFirst(first);
			message.setKeyword1(keyword1);
			message.setKeyword2(keyword2);
			message.setRemark(remark);
			System.out.println("access_token >>>>"+access_token);
			System.out.println("first >>>>"+first);
			System.out.println("openid >>>>"+user.getOpenid());
			System.out.println("code >>>>"+activity.getCategory().getCode());
			
			System.out.println("keyword1 >>>>"+keyword1);
			System.out.println("keyword2 >>>>"+keyword2);
			
			String result=TemplateUtil.sendApplyMessage(message);
			System.out.println("发送消息>>>>>>>>>>>>>>>："+result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void SendScoreMessage(SendMessage message) {
		try {
			String access_token=weiChatHelper.getAccessToken();
			message.setAccess_token(access_token);
			message.setFirst("您的积分账户变更如下");
			message.setRemark("");
			String result=TemplateUtil.sendScoreMessage(message);
			LOG.info("result>>>>>>>>>>>>>"+result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
