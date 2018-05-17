package com.jfdh.service.weichat;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.HttpActivity;
import com.jfdh.httpmodel.HttpActivityComments;
import com.jfdh.httpmodel.HttpCategory;
import com.jfdh.httpmodel.HttpFieldType;
import com.jfdh.httpmodel.Json;
import com.jfdh.model.User;
import com.jfdh.service.IBaseService;



/**
 * 资源Service
 * 
 * @author 
 * 
 */
public interface IActivityService extends IBaseService {
	public List<HttpActivity> getActivityList(String type,String code,String goodsType,DataRequest request,User user);
	public List<HttpCategory> getAllCategorys(String type);
	
	public HttpActivity getActivity(String id,User user);
	
	public HttpActivity getActivity(String id);
	
	public Json saveApplication(String id,HttpServletRequest request,User user);
	
	public List<HttpFieldType> getField(String id,User user);
	
	public void cancelApplication(String id,User user);
	public void agree(String id);
	
	public void reject(String id);
	
	public void saveorEdit(HttpActivity httpActivity,HttpServletRequest request,User user);
	public String updateSharedActivity(String id);
	
	public List<HttpActivityComments> submitComment(String activityId,String content,User user);
	
	public List<HttpCategory> getCategorysByCode(String code);
}
