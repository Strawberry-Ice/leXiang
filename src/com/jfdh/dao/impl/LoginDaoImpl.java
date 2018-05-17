package com.jfdh.dao.impl;

import org.springframework.stereotype.Repository;

import com.jfdh.dao.ILoginDao;

@Repository("loginDao")
public class LoginDaoImpl<T> extends BaseDaoImpl<T> implements ILoginDao<T> {

}
