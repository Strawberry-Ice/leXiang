package com.jfdh.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.DataResponse;
import com.jfdh.httpmodel.Json;
import com.jfdh.model.Org;
import com.jfdh.service.OrgService;
import com.jfdh.service.RoleService;

@Controller
@RequestMapping("/org")
public class OrgController {
	
	@Autowired
	private OrgService orgService;
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public String list() {
		return "org.list";
	}
	
	
	@RequestMapping(value="/create",method = RequestMethod.GET)
	public String create(Model model) {
		Org org=new Org();
		model.addAttribute("org", org);
		return "default.org.create";
	}
	
	@RequestMapping(value="/edit",method = RequestMethod.GET)
	public String edit(String id,Model model) {
		Org org=orgService.findById(id);
		model.addAttribute("org", org);
		return "default.org.edit";
	}
	
	@RequestMapping(value="/view",method = RequestMethod.GET)
	public String view(String id,Model model) {
		Org org=orgService.findById(id);
		model.addAttribute("org", org);
		return "default.org.view";
	}
	
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public String add(@Valid Org org,BindingResult result,Model model) {
		if(result.hasErrors()){
			return "default.org.create";
		}
		orgService.save(org);
		return "redirect:/org/list";
	}
	
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public String update(@Valid Org org,BindingResult result,Model model) {
		if(result.hasErrors()){
			return "default.org.edit";
		}
		orgService.update(org);
		return "redirect:/org/list";
	}
	
	@RequestMapping(value="/delete",method = RequestMethod.GET)
	@ResponseBody
	public Json delete(@RequestParam(value="ids")String id) {
		Json j=new Json();
		try {
			orgService.delete(id);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		return j;
	}
	
	
	@RequestMapping(value="/listData")  
    @ResponseBody  
    public DataResponse list(@RequestParam(defaultValue="1",value="page") String page,  
            @RequestParam(defaultValue="20",value="rows") String rows,  
            @RequestParam("sidx") String sidx,  
            @RequestParam("sord") String sord,  
            @RequestParam("_search") boolean search,  
            @RequestParam(required=false,value="searchField") String searchField,  
            @RequestParam(required=false,value="searchOper") String searchOper,  
            @RequestParam(required=false,value="searchString") String searchString,  
            @RequestParam(required=false,value="filters") String filters  
            ){  
        try {  
            DataRequest request = new DataRequest();  
            request.setPage(StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page));  
            request.setRows(StringUtils.isEmpty(rows) ? 20 : Integer.valueOf(rows));  
            request.setSidx(sidx);  
            request.setSord(sord);  
            request.setSearch(search);  
            return orgService.search(request);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
	
}
