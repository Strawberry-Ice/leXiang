package com.jfdh.controller;

import java.io.StringReader;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jfdh.httpmodel.BackendUserDtail;
import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.DataResponse;
import com.jfdh.httpmodel.HttpSearch;
import com.jfdh.httpmodel.Json;
import com.jfdh.httpmodel.UserPasswordValid;
import com.jfdh.httpmodel.UserProfile;
import com.jfdh.model.GovOrg;
import com.jfdh.model.Org;
import com.jfdh.model.Role;
import com.jfdh.model.User;
import com.jfdh.service.RoleService;
import com.jfdh.service.UserService;
import com.jfdh.util.StringUtil;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public String list() {
		return "user.list";
	}
	
	
	@RequestMapping(value="/profile",method = RequestMethod.GET)
	public String profile(Model model) {
		BackendUserDtail userDetails = (BackendUserDtail) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		UserProfile userProfile=new UserProfile();
		BeanUtils.copyProperties(userDetails, userProfile);
		model.addAttribute("userProfile", userProfile);
		return "user.profile";
	}
	
	@RequestMapping(value="/updateProfile",method = RequestMethod.POST)
	public String updateProfile(@Valid UserProfile userProfile,BindingResult result,RedirectAttributes redirectAttributes) {
		if(result.hasErrors()){
			return "user.profile";
		}
		userService.updateProfile(userProfile);
		redirectAttributes.addFlashAttribute("msg","修改成功！！");
		return "redirect:/user/profile";
	}
	
	
	@RequestMapping(value="/password",method = RequestMethod.GET)
	public String password(Model model) {
		UserPasswordValid userPasswordValid=new UserPasswordValid();
		model.addAttribute("userPasswordValid", userPasswordValid);
		return "user.password";
	}
	
	@RequestMapping(value="/changePassword",method = RequestMethod.POST)
	public String changePassword(@Valid UserPasswordValid userPasswordValid,BindingResult result,Model model,RedirectAttributes redirectAttributes) {
		if(result.hasErrors()){
			return "user.password";
		}
		if(!userPasswordValid.getPassword().equals(userPasswordValid.getPassword2())){
			result.addError(new FieldError("userPasswordValid","password2", "两次输入密码不相同！！"));
		}
		if(userService.changePassword(userPasswordValid)){
			redirectAttributes.addFlashAttribute("msg","修改成功！！");
			return "redirect:/user/password";
		}else{
			result.addError(new FieldError("userPasswordValid","oldPassword", "密码不对！！"));
		}
		return "user.password";
	}
	
	@RequestMapping(value="/create",method = RequestMethod.GET)
	public String create(Model model) {
		
		List<Role> roles=roleService.findAll();
		List<GovOrg> govOrgs=userService.findAllGovOrgs();
		List<Org> orgs=userService.findAllOrgs();
		User user=new User();
		model.addAttribute("roles", roles);
		model.addAttribute("orgs", orgs);
		model.addAttribute("user", user);
		model.addAttribute("govOrgs", govOrgs);
		return "default.user.create";
	}
	
	@RequestMapping(value="/edit",method = RequestMethod.GET)
	public String edit(String id,Model model) {
		List<Role> roles=roleService.findAll();
		User user=userService.findById(id);
		if(null!=user.getGovOrg()&&null!=user.getGovOrg().getParent()){
			user.setParentGovOrg(user.getGovOrg().getParent().getId());
		}else if(null!=user.getGovOrg()&&null==user.getGovOrg().getParent()){
			user.setParentGovOrg(user.getGovOrg().getId());
		}
		List<GovOrg> govOrgs=userService.findAllGovOrgs();
		List<Org> orgs=userService.findAllOrgs();
		model.addAttribute("govOrgs", govOrgs);
		model.addAttribute("orgs", orgs);
		model.addAttribute("roles", roles);
		model.addAttribute("user", user);
		return "default.user.edit";
	}
	
	
	@RequestMapping(value="/view",method = RequestMethod.GET)
	public String view(String id,Model model) {
		List<Role> roles=roleService.findAll();
		User user=userService.findById(id);
		if(null!=user.getGovOrg()&&null!=user.getGovOrg().getParent()){
			user.setParentGovOrg(user.getGovOrg().getParent().getId());
		}else if(null!=user.getGovOrg()&&null==user.getGovOrg().getParent()){
			user.setParentGovOrg(user.getGovOrg().getId());
		}
		List<GovOrg> govOrgs=userService.findAllGovOrgs();
		List<Org> orgs=userService.findAllOrgs();
		model.addAttribute("govOrgs", govOrgs);
		model.addAttribute("orgs", orgs);
		model.addAttribute("roles", roles);
		model.addAttribute("user", user);
		return "default.user.view";
	}
	
	
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public String add(@Valid User user,BindingResult result,Model model) {
		if(null!=user.getGovOrg()&&StringUtils.isEmpty(user.getGovOrg().getId())){
			result.addError(new FieldError("user","parentGovOrg", "请选择街道（社工委）！！"));
		}
		if(null!=user.getOrg()&&StringUtils.isEmpty(user.getOrg().getId())){
			result.addError(new FieldError("user","org.id", "请选择机构！！"));
		}
		if(StringUtils.isEmpty(user.getUserName())){
			result.addError(new FieldError("user","userName", "用户名不能为空！！"));
		}else if(user.getUserName().length()>100){
			result.addError(new FieldError("user","userName", "用户名不能超过100个字符！！"));
		}
		if(StringUtils.isEmpty(user.getPassword())){
			result.addError(new FieldError("user","password", "密码不能为空！！"));
		}else if(user.getPassword().length()>50){
			result.addError(new FieldError("user","password", "密码不能超过50个字符！！"));
		}
		if(result.hasErrors()){
			List<Role> roles=roleService.findAll();
			List<GovOrg> govOrgs=userService.findAllGovOrgs();
			List<Org> orgs=userService.findAllOrgs();
			model.addAttribute("orgs", orgs);
			model.addAttribute("govOrgs", govOrgs);
			model.addAttribute("roles", roles);
			return "default.user.create";
		}
		if(null==userService.save(user)){
			result.addError(new FieldError("user","userName", "对不起，该用户名已经存在，请重新输入！！"));
			List<Role> roles=roleService.findAll();
			List<GovOrg> govOrgs=userService.findAllGovOrgs();
			List<Org> orgs=userService.findAllOrgs();
			model.addAttribute("orgs", orgs);
			model.addAttribute("govOrgs", govOrgs);
			model.addAttribute("roles", roles);
			return "default.user.create";
		}
		return "redirect:/user/list";
	}
	
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public String update(@Valid User user,BindingResult result,Model model) {
		if(null!=user.getGovOrg()&&StringUtils.isEmpty(user.getGovOrg().getId())){
			result.addError(new FieldError("user","parentGovOrg", "请选择街道（社工委）！！"));
		}
		if(null!=user.getOrg()&&StringUtils.isEmpty(user.getOrg().getId())){
			result.addError(new FieldError("user","org.id", "请选择机构！！"));
		}
		if(StringUtils.isEmpty(user.getUserName())){
			result.addError(new FieldError("user","userName", "用户名不能为空！！"));
		}else if(user.getUserName().length()>100){
			result.addError(new FieldError("user","userName", "用户名不能超过100个字符！！"));
		}
		if(StringUtils.isEmpty(user.getPassword())){
			result.addError(new FieldError("user","password", "密码不能为空！！"));
		}else if(user.getPassword().length()>50){
			result.addError(new FieldError("user","password", "密码不能超过50个字符！！"));
		}
		if(result.hasErrors()){
			List<Role> roles=roleService.findAll();
			List<GovOrg> govOrgs=userService.findAllGovOrgs();
			List<Org> orgs=userService.findAllOrgs();
			model.addAttribute("orgs", orgs);
			model.addAttribute("govOrgs", govOrgs);
			model.addAttribute("roles", roles);
			return "default.user.edit";
		}
		userService.update(user);
		return "redirect:/user/list";
	}
	
	@RequestMapping(value="/delete",method = RequestMethod.GET)
	@ResponseBody
	public Json delete(@RequestParam(value="ids")String id) {
		Json j=new Json();
		try {
			userService.delete(id);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		return j;
	}
	
	@RequestMapping(value="/getGovOrg",method = RequestMethod.GET)
	@ResponseBody
	public List<GovOrg> getGovOrgs(String parentId) {
		List<GovOrg> govOrgs = userService.getGovOrgsByParentId(parentId);
		return govOrgs;
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
        	HttpSearch httpSearch=null;
        	if(StringUtil.isNotNull(filters)){
				 filters = new String(filters.getBytes("iso8859-1"),"UTF-8");
				 JSONParser parser1 = new JSONParser(new StringReader(filters)); 
				 JSONValue jsonValue1 = parser1.nextValue();
				 httpSearch=(com.jfdh.httpmodel.HttpSearch) JSONMapper.toJava(jsonValue1, HttpSearch.class);
			 }
            DataRequest request = new DataRequest();  
            request.setPage(StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page));  
            request.setRows(StringUtils.isEmpty(rows) ? 20 : Integer.valueOf(rows));  
            request.setSidx(sidx);  
            request.setSord(sord);  
            request.setSearch(search);  
            return userService.search(request,httpSearch);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
	
}
