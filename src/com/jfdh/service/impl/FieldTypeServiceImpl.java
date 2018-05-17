package com.jfdh.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfdh.dao.IBaseDao;
import com.jfdh.model.FieldsType;
import com.jfdh.service.IFieldTypeService;

/**
 * 资源Service实现�?
 * 
 * @author 
 * 
 */
@Service("fieldTypeService")
public class FieldTypeServiceImpl extends BaseServiceImpl implements IFieldTypeService {

	private IBaseDao<FieldsType> fieldTypeDao;
	
	public IBaseDao<FieldsType> getFieldTypeDao() {
		return fieldTypeDao;
	}
	@Autowired
	public void setFieldTypeDao(IBaseDao<FieldsType> fieldTypeDao) {
		this.fieldTypeDao = fieldTypeDao;
	}

	@Override
	public List<FieldsType> getFieldType() {
		String hql="from FieldType order by sequence";
		List<FieldsType> list=fieldTypeDao.find(hql);
		return list;
	}
	
	
}
