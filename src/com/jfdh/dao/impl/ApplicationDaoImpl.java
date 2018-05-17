package com.jfdh.dao.impl;

import org.springframework.stereotype.Repository;

import com.jfdh.dao.IApplicationDao;



@Repository("applicationDao")
public class ApplicationDaoImpl<T> extends BaseDaoImpl<T> implements IApplicationDao<T> {

}
