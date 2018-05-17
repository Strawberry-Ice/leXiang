package com.jfdh.dao.impl;

import org.springframework.stereotype.Repository;

import com.jfdh.dao.IFieldTypeDao;



@Repository("fieldTypeDao")
public class IFieldTypeDaoImpl<T> extends BaseDaoImpl<T> implements IFieldTypeDao<T> {

}
