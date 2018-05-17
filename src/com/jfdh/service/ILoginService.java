package com.jfdh.service;

import javax.servlet.http.HttpSession;

import com.jfdh.httpmodel.HttpUser;
import com.jfdh.model.User;




/**
 * 
 * 
 * @author 
 * 
 */
public interface ILoginService extends IBaseService {

	User loginin(HttpUser httpUser, HttpSession session);

}
