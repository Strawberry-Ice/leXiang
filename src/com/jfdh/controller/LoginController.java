package com.jfdh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.jfdh.httpmodel.HttpUser;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("msg", "用户名密码不正确！！");
		}

		if (logout != null) {
			model.addObject("msg", "您以成功登出！！");
		}
		model.setViewName("login");

		return model;

	}



//	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView login(@Valid HttpUser user,BindingResult result, HttpSession session,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		String captchaClient = user.getKaptcha();
		String captchaServer = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (!captchaClient.equals(captchaServer)) {
			
			session.setAttribute("sessionInfo", user);
		}
		if(result.hasErrors()){
			
			mav.setViewName("login");
			mav.addObject("error", "用户名或密码不正确！");
			return mav;
		}else{
			//todo check username
			mav.setViewName("default.main");
		}
		
		return mav;
	}
	
	
	@RequestMapping("/doKaptcha")
	@ResponseBody
	public String doKaptcha(String kaptcha,HttpSession session){
		String captchaServer = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (kaptcha.equals(captchaServer)) {
			return "true";
		}else{
			return "false";
		}
	}
	
	@RequestMapping("/main")
	public String main(HttpUser user, HttpSession session,
			HttpServletRequest request){
		
		return "default.main";
	}
	
	
}
