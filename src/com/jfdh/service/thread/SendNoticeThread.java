package com.jfdh.service.thread;

import java.util.List;

import com.jfdh.httpweichatmodel.SendMessage;
import com.jfdh.model.Activity;
import com.jfdh.model.GovOrg;
import com.jfdh.model.User;
import com.jfdh.util.TemplateUtil;

public class SendNoticeThread extends Thread {

	public SendNoticeThread(Activity activity, String govOrgId, String code,
			String access_token, List<User> list) {
		super();
		this.activity = activity;
		this.govOrgId = govOrgId;
		this.code = code;
		this.access_token = access_token;
		this.list = list;
	}

	public Activity activity;
	public String govOrgId;
	public String code;
	public String access_token;
	public List<User> list;
	
	public void run() {
		for(User user:list){
			if(user.getNeedReceive()){
				GovOrg govOrg=user.getGovOrg();
				String openId=user.getOpenid();
				String name = govOrg.getName();
				String first="您收到了来自"+name+"的通知";
				String keyword1=name;
				String keyword2=activity.getName();
				String remark=activity.getRemark();
				if(remark.contains("*")){
					remark=remark.replace("*", name);
				}
				remark+="——『如不想收到本社区通知，可进入微笑社区，在“个人中心”里关闭』";
				SendMessage message=new SendMessage();
				message.setActivityId(activity.getId());
				message.setOpenId(openId);
				message.setAccess_token(access_token);
				message.setFirst(first);
				message.setKeyword1(keyword1);
				message.setKeyword2(keyword2);
				message.setRemark(remark);
				message.setCode(code);
				String result=TemplateUtil.sendNoticeMessage(message);
				System.out.println("发送消息>>>>>>>>>>>>>>>："+result);
			}
		}
	}
	
	
}
