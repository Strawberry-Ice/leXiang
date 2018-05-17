package com.jfdh.dao.impl;

import org.springframework.stereotype.Repository;

import com.jfdh.dao.ICategoryDao;



@Repository("categoryDao")
public class CategoryDaoImpl<T> extends BaseDaoImpl<T> implements ICategoryDao<T> {

}
