package com.jfdh.controller.weichat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WeiChatController {
	
	@RequestMapping("/weichatLogin")
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
	
}
