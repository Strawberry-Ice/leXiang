package com.jfdh.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jfdh.httpmodel.HttpGovOrg;
import com.jfdh.httpmodel.Json;
import com.jfdh.service.IGovOrgService;

@Controller
@RequestMapping("/govOrg")
public class GovOrgController extends BaseController {
	
	private IGovOrgService govOrgService;
	
	public IGovOrgService getCategoryService() {
		return govOrgService;
	}

	@Autowired
	public void setCategoryService(IGovOrgService categoryService) {
		this.govOrgService = categoryService;
	}


	@RequestMapping(value="/getGovOrg",method=RequestMethod.GET)
	@ResponseBody
	public Json getMenu(String role,HttpServletRequest request){
		Json j=new Json();
//		List<HttpMenu> httpMenuList=activityService.getMenu(role,request);
//		j.setObj(httpMenuList);
////		Logger log = Logger.getLogger(this.getClass());
		List<HttpGovOrg> resourceList=govOrgService.getGovOrg();
		j.setObj(resourceList);
		j.setSuccess(true);
		return j;
	}
}
