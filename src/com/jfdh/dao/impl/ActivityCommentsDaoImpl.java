package com.jfdh.dao.impl;

import org.springframework.stereotype.Repository;

import com.jfdh.dao.IActivityDao;



@Repository("activityCommentsDao")
public class ActivityCommentsDaoImpl<T> extends BaseDaoImpl<T> implements IActivityDao<T> {

}
