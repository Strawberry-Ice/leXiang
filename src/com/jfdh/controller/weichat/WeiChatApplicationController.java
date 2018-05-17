package com.jfdh.controller.weichat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jfdh.model.User;
import com.jfdh.service.weichat.IActivityService;
import com.jfdh.util.Constants;


@Controller
@RequestMapping("/weichatApplication")
public class WeiChatApplicationController {
	private IActivityService weichatActivityService;
	
	public IActivityService getWeichatActivityService() {
		return weichatActivityService;
	}
	@Autowired
	public void setWeichatActivityService(IActivityService weichatActivityService) {
		this.weichatActivityService = weichatActivityService;
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response,HttpSession session){
		ModelAndView mav = new ModelAndView();
		String id=request.getParameter("id");
		String type=request.getParameter("type");
		User user=(User) session.getAttribute("USER_FOR_WEICHAT");
		weichatActivityService.saveApplication(id, request,user);
		
		if(Constants.CONSTANTS_OCCASION_COMMUNITY.equalsIgnoreCase(type)){
			mav.setViewName("redirect:/weichatActivity/listAllActivity/"+Constants.CONSTANTS_OCCASION_COMMUNITY+"/"+Constants.CONSTANTS_ALL);
		}else{
			mav.setViewName("redirect:/weichatActivity/listAllActivity/"+Constants.CONSTANTS_OCCASION_SQUARE+"/"+Constants.CONSTANTS_ALL);
		}
		return mav;
	}
	
}
