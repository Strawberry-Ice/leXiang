package com.jfdh.dao.impl;

import org.springframework.stereotype.Repository;

import com.jfdh.dao.IActivityDao;



@Repository("activityDao")
public class ActivityDaoImpl<T> extends BaseDaoImpl<T> implements IActivityDao<T> {

}
