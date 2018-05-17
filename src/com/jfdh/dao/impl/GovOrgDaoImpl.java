package com.jfdh.dao.impl;

import org.springframework.stereotype.Repository;

import com.jfdh.dao.IGovOrgDao;



@Repository("govOrgDao")
public class GovOrgDaoImpl<T> extends BaseDaoImpl<T> implements IGovOrgDao<T> {

}
