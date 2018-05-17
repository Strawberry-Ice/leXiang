package com.jfdh.service.impl;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfdh.dao.IBaseDao;
import com.jfdh.httpmodel.HttpUser;
import com.jfdh.model.User;
import com.jfdh.service.ILoginService;
import com.jfdh.util.Encrypt;

/**
 * 
 * @author 
 * 
 */
@Service("loginService")
public class LoginServiceImpl extends BaseServiceImpl implements ILoginService {
	
	private IBaseDao<User> loginDao;

	public IBaseDao<User> getBaseDao() {
		return loginDao;
	}
	@Autowired
	public void setBaseDao(IBaseDao<User> baseDao) {
		this.loginDao = baseDao;
	}



	@Override
	public User loginin(HttpUser httpUser,HttpSession session) {
		User user=null;
		String userName=httpUser.getUserName();
		String password=Encrypt.e(httpUser.getPassword());
		String hql="from User where userName=? and password=? and 1=1";
		List<User> userList=loginDao.find(hql,userName,password);
		if(userList!=null && userList.size()>0){
			user=userList.get(0);
		}
		return user;
	}

}
