package com.jfdh.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.DataResponse;
import com.jfdh.httpmodel.HttpActivity;
import com.jfdh.httpmodel.HttpActivityComments;
import com.jfdh.httpmodel.HttpApplication;
import com.jfdh.httpmodel.HttpMenu;
import com.jfdh.httpmodel.HttpSearch;
import com.jfdh.httpmodel.Json;
import com.jfdh.model.Application;
import com.jfdh.model.User;



/**
 * 资源Service
 * 
 * @author 
 * 
 */
public interface IActivityService extends IBaseService {
	List<HttpMenu> getMenu(String role,HttpServletRequest request);
	
	public DataResponse dataGrid(DataRequest dr,HttpSession session,String type,HttpSearch httpSearch);
	public String saveorEditActivity(HttpActivity httpActivity,boolean flag);
	
	public Json del(String ids);
	
	public HttpActivity getActivity(String id);
	
	public DataResponse applyGrid(DataRequest dr,HttpSession session,String type,HttpSearch httpSearch);
	
//	public void agree(String id);
	
	public void reject(String id);
	
	public List<Application> getApplication(String type);
	
	public List<HttpApplication> getHttpApplication(String type,HttpSession session);

	public Json payment(String ids);
	
	public DataResponse checkGrid(DataRequest dr,HttpSession session,String type);
	
	public void agreeForActivity(String id);
	
	public void rejectForActivity(String id);
	
	public User currentUser();

	DataResponse pay(DataRequest dr, HttpSession session, String code);
	
	public Json enable(String ids);
	
	public Json notenable(String ids);
	
	public HttpActivity getComments(String id);
	
	public void delComment(String id);
	
	public void saveActivityForVirtual(HttpActivity httpActivity,User user);
	
	public void submitComment(String activityId,String content,User user);
}
