package com.jfdh.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jfdh.httpmodel.HttpActivity;
import com.jfdh.httpmodel.HttpActivityComments;
import com.jfdh.httpmodel.Json;
import com.jfdh.model.FieldsType;
import com.jfdh.model.User;
import com.jfdh.service.IActivityService;
import com.jfdh.service.IFieldTypeService;
import com.jfdh.service.UserService;
import com.jfdh.util.Constants;
import com.jfdh.util.JsoupService;
import com.jfdh.util.StringUtil;

@Controller
@RequestMapping("/tools")
public class ToolsController {
	@Autowired
	private IFieldTypeService fieldTypeService;
	@Autowired
	private UserService userService;
	@Autowired
	private JsoupService jsoupService;
	@Autowired
	private IActivityService activityService;
	
	
	@RequestMapping(value="/createActivity",method=RequestMethod.GET)
	public String createActivity(Model model,HttpActivity httpActivity){
		
		List<User> users=userService.findUserByVirtual(true);
		model.addAttribute("users", users);
		List<FieldsType> list=fieldTypeService.getFieldType();
		httpActivity.setFieldsType(list);
		model.addAttribute("code", Constants.CONSTANTS_ACTIVITY);
		return "tools.create.activity";
	}
	
	@RequestMapping(value="/createRecruitment",method=RequestMethod.GET)
	public String createRecruitment(Model model,HttpActivity httpActivity){
		List<User> users=userService.findUserByVirtual(true);
		model.addAttribute("users", users);
		List<FieldsType> list=fieldTypeService.getFieldType();
		httpActivity.setFieldsType(list);
		model.addAttribute("code", Constants.CONSTANTS_RECRUITMENT);
		return "tools.create.recruitment";
	}
	
	@RequestMapping(value="/createAssistant",method=RequestMethod.GET)
	public String createAssistant(Model model,HttpActivity httpActivity){
		List<User> users=userService.findUserByVirtual(true);
		model.addAttribute("users", users);
		model.addAttribute("code", Constants.CONSTANTS_ASSISTANT);
		return "tools.create.assistant";
	}
	
	@RequestMapping(value="/createCooperation",method=RequestMethod.GET)
	public String createCooperation(Model model,HttpActivity httpActivity){
		List<User> users=userService.findUserByVirtual(true);
		model.addAttribute("users", users);
		model.addAttribute("code", Constants.CONSTANTS_COOPERATION);
		return "tools.create.cooperation";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Json saveOrEdit(HttpActivity httpActivity,BindingResult result, HttpSession session,Model model,
			HttpServletRequest request){
		Json j=new Json();
		String code = httpActivity.getCode();
		String userId=request.getParameter("userId");
		User user=new User();
		user.setId(userId);
		String content=httpActivity.getContent();
		System.out.println(content);
		if(StringUtil.isNotNull(content) && content.indexOf("base64")>0){
			content=jsoupService.parse(content,request.getContextPath());
			System.out.println(content);
			httpActivity.setContent(content);
		}
		activityService.saveActivityForVirtual(httpActivity, user);
		model.addAttribute("code", code);
		j.setMsg("保存成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping(value="/createComment/{code}/{id}",method=RequestMethod.GET)
	public String createComment(@PathVariable String code,@PathVariable String id,HttpSession session,Model model,HttpActivityComments httpActivityComments){
		model.addAttribute("code", code);
		model.addAttribute("activityId", id);
		List<User> users=userService.findUserByVirtual(true);
		model.addAttribute("users", users);
		if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code)){
			return "default.active.create.comment";
		}else if(Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code)){
			return "default.recruitment.create.comment";
		}else if(Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(code)){
			return "default.active.create.assistant";
		}else if(Constants.CONSTANTS_COOPERATION.equalsIgnoreCase(code)){
			return "default.active.create.cooperation";
		}
		return "";
	}
	
	
	@RequestMapping(value="/submitComment/{code}",method=RequestMethod.POST)
	public String submitComment(@PathVariable String code,HttpActivityComments httpActivityComments,HttpServletRequest request){
		User user=new User();
		user.setId(httpActivityComments.getUserId());
		String activityId = httpActivityComments.getActivityId();
		String content = httpActivityComments.getContent();
		activityService.submitComment(activityId,content, user);
		
		return "redirect:/activity/showComment/"+code+"/"+activityId;
	}
	
	@RequestMapping(value="/inputUser",method=RequestMethod.GET)
	public String inputUser(Model model,HttpActivity httpActivity){
		
		return "tools.create.user";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public Json saveUser(String names){
		Json j=new Json();
		if(StringUtil.isNotNull(names)){
			try {
				userService.saveUsers(names);
				j.setMsg("保存成功！");
				j.setSuccess(true);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				j.setMsg("保存失败！");
				j.setSuccess(false);
			}
		}
		return j;
	}
}
