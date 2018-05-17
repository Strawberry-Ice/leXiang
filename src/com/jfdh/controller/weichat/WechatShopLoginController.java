package com.jfdh.controller.weichat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jfdh.httpmodel.HttpUser;
import com.jfdh.httpmodel.Json;
import com.jfdh.model.User;
import com.jfdh.service.ScoreShopService;
import com.jfdh.service.weichat.WeichatUserService;
import com.jfdh.util.Constants;
import com.jfdh.util.StringUtil;


@Controller
@RequestMapping("/weichatShop")
public class WechatShopLoginController {
	
	@Autowired
	private WeichatUserService weichatUserService;
	
	@Autowired
	private ScoreShopService scoreShopService;
	
	@RequestMapping("/login")
	public String login(Model model){
		HttpUser httpUser = new HttpUser();
		model.addAttribute("httpUser", httpUser);
		return "/weichat/shop/login";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView login(@Valid HttpUser httpUser,BindingResult result,HttpSession session){
		ModelAndView model = new ModelAndView();
		if(!StringUtil.isNotNull(httpUser.getUserName())){
			result.addError(new FieldError("httpUser","userName", "请输入用户名！"));
		}
		if(!StringUtil.isNotNull(httpUser.getPassword())){
			result.addError(new FieldError("httpUser","password", "请输入密码！"));
		}
		if(StringUtil.isNotNull(httpUser.getUserName()) && StringUtil.isNotNull(httpUser.getPassword())){
			String md5Password = new Md5PasswordEncoder().encodePassword(
					httpUser.getPassword(), httpUser.getUserName());
			User user=weichatUserService.findByUserNameAndPassWord(httpUser.getUserName(), md5Password);
			if(user==null || !StringUtil.isNotNull(user.getUserName())){
				result.addError(new FieldError("httpUser","userName", "用户名或者密码错误！"));
			}else{
				session.setAttribute(Constants.CONSTANTS_USER_SESSION_WEICHAT, user);
			}
		}
		if(result.hasErrors()){
			model.setViewName("/weichat/shop/login");
		}else{
			
			model.setViewName("redirect:/weichatShop/identifyingCode");
		}
		return model;
	}
	
	@RequestMapping("/identifyingCode")
	public String identifyingCode(Model model){
		return "/weichat/shop/submit";
	}
	
	@RequestMapping(value = "/valid", method = RequestMethod.GET)
	@ResponseBody
	public Json exchangeValid(HttpServletRequest request,HttpSession session) {
		Json j=new Json();
		String key=request.getParameter("key");
		if (StringUtils.isEmpty(key)) {
			j.setMsg("验证码不能为空！！");
		} else {
			User user=(User) session.getAttribute(Constants.CONSTANTS_USER_SESSION_WEICHAT);
			Object o=scoreShopService.doExchangeValid(key,user);
			if(o instanceof String){
				j.setMsg((String)o);
			}else{
				j.setMsg("兑换成功！！");
//				j.setObj(o);
			}
		}
		j.setObj(key);
		j.setSuccess(true);
		return j;
	}
}
