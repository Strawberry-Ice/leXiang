package com.jfdh.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfdh.dao.IBaseDao;
import com.jfdh.httpmodel.HttpGovOrg;
import com.jfdh.model.GovOrg;
import com.jfdh.service.IGovOrgService;

/**
 * 资源Service实现�?
 * 
 * @author 
 * 
 */
@Service("govOrgService")
public class GovOrgServiceImpl extends BaseServiceImpl implements IGovOrgService {

	private IBaseDao<GovOrg> govOrgDao;
	
	public IBaseDao<GovOrg> getGovOrgDao() {
		return govOrgDao;
	}
	@Autowired
	public void setGovOrgDao(IBaseDao<GovOrg> govOrgDao) {
		this.govOrgDao = govOrgDao;
	}
	
	@Override
	public List<HttpGovOrg> getGovOrg() {
		List<HttpGovOrg> returnList = new ArrayList<HttpGovOrg>();
		List<GovOrg> workList = new ArrayList<GovOrg>();
		Map<GovOrg, HttpGovOrg> workMap = new HashMap<GovOrg, HttpGovOrg>();
		
		String hql = "from GovOrg where adminOrg=false";
		List<GovOrg> govOrgList = govOrgDao.find(hql);
		
		for (Iterator<GovOrg> iter = govOrgList.iterator(); iter.hasNext();) {
			GovOrg govOrg = iter.next();
			
			
			if (govOrg.getParent() == null) {
				workList.add(govOrg);
				HttpGovOrg httpgovOrg = new HttpGovOrg();
				httpgovOrg.setId(govOrg.getId());
				httpgovOrg.setName(govOrg.getName());
//				BeanUtils.copyProperties(govOrg, HttpgovOrg);
				returnList.add(httpgovOrg);
				workMap.put(govOrg, httpgovOrg);
			}
		}
		
		while (workList.size() > 0) {
			GovOrg parentResource = workList.remove(0);
			
			for (GovOrg r: govOrgList) {
				
				if (r.getParent() == parentResource) {
					HttpGovOrg httpResource = new HttpGovOrg();
//					BeanUtils.copyProperties(r, httpResource);
					httpResource.setId(r.getId());
					httpResource.setName(r.getName());
					HttpGovOrg parent = workMap.get(parentResource);
					parent.getChildren().add(httpResource);
					
				}
			}
			
		}
		
		return returnList;
	}
	
	
}
