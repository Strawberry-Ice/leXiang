package com.jfdh.service;

import java.util.List;

import com.jfdh.httpmodel.HttpGovOrg;
import com.jfdh.model.GovOrg;




/**
 * 资源Service
 * 
 * @author 
 * 
 */
public interface IGovOrgService extends IBaseService {
	public List<HttpGovOrg> getGovOrg();
}
