package com.jfdh.service;

import com.jfdh.httpweichatmodel.SendMessage;
import com.jfdh.model.Activity;
import com.jfdh.model.User;

public interface ISendMessageService extends IBaseService{
	
	public void SendNoticeMessage(Activity activity,String govOrgId,String code);
	
	public void SendApplyMessage(Activity activity,User user,String msg);
	
	public void SendScoreMessage(SendMessage sendMessage);
}
