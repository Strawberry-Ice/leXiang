package com.jfdh.controller.weichat;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.jfdh.controller.weichat.util.WeiChatHelper;
import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.HttpActivity;
import com.jfdh.httpmodel.HttpActivityComments;
import com.jfdh.httpmodel.HttpCategory;
import com.jfdh.httpmodel.HttpFieldType;
import com.jfdh.httpmodel.Json;
import com.jfdh.httpmodel.WeiChartHttpUser;
import com.jfdh.httpmodel.WeichatSinguate;
import com.jfdh.model.FieldsType;
import com.jfdh.model.User;
import com.jfdh.service.IFieldTypeService;
import com.jfdh.service.weichat.IActivityService;
import com.jfdh.service.weichat.WeichatUserService;
import com.jfdh.util.Constants;
import com.jfdh.util.FilePathHelper;
import com.jfdh.util.JsoupService;
import com.jfdh.util.StringUtil;
import com.jfdh.validate.WeichatActivityValidate;


@Controller
@RequestMapping("/weichatActivity")
public class WeiChatActivityController {
	public static final Logger LOG = LoggerFactory
			.getLogger(WeiChatActivityController.class);
	@Value("#{configProperties['fileUploadDisk']}")
	private String fileUploadDisk;

	@Value("#{configProperties['logoPath']}")
	private String logoPath;
	@Value("#{configProperties['fileUploadPath']}")
	private String fileUploadPath;
	
	@Value("#{configProperties['appid']}")
	private String appid;
	
	@Autowired
	private WeiChatHelper weiChatHelper;
	
	@Autowired
	private WeichatUserService weichatUserService;
	
	private IActivityService weichatActivityService;
	private IFieldTypeService fieldTypeService;
	private JsoupService jsoupService;
	
	public IActivityService getWeichatActivityService() {
		return weichatActivityService;
	}
	@Autowired
	public void setWeichatActivityService(IActivityService weichatActivityService) {
		this.weichatActivityService = weichatActivityService;
	}
	
	public IFieldTypeService getFieldTypeService() {
		return fieldTypeService;
	}
	@Autowired
	public void setFieldTypeService(IFieldTypeService fieldTypeService) {
		this.fieldTypeService = fieldTypeService;
	}
	public JsoupService getJsoupService() {
		return jsoupService;
	}
	@Autowired
	public void setJsoupService(JsoupService jsoupService) {
		this.jsoupService = jsoupService;
	}
	@RequestMapping(value="/activity/{occasion}",method=RequestMethod.GET)
	public ModelAndView community(@PathVariable String occasion,HttpSession session){
		User user=(User) session.getAttribute("USER_FOR_WEICHAT");
		ModelAndView mav = new ModelAndView();
		if(Constants.CONSTANTS_OCCASION_COMMUNITY.equalsIgnoreCase(occasion)){
			if("匿名用户".equalsIgnoreCase(user.getNickName())||user.getGovOrg()==null){
				ModelMap mmap = new ModelMap(); 
				mmap.addAttribute("errMsg", "匿名用户不能访问社区！");
				mav = new ModelAndView("redirect:/weichatActivity/listAllActivity/"+Constants.CONSTANTS_OCCASION_SQUARE+"/"+Constants.CONSTANTS_ALL,mmap);
			}else{
				mav.setViewName("redirect:/weichatActivity/listAllActivity/"+Constants.CONSTANTS_OCCASION_COMMUNITY+"/"+Constants.CONSTANTS_ALL);
			}
		}else{
			mav.setViewName("redirect:/weichatActivity/listAllActivity/"+Constants.CONSTANTS_OCCASION_SQUARE+"/"+Constants.CONSTANTS_ALL);
		}
		
		return mav;
	}

	@RequestMapping(value="/listAllActivity/{type}/{code}",method=RequestMethod.GET)
	public ModelAndView getActivity(@PathVariable String code,
			@PathVariable String type,HttpSession session,
			@RequestParam(defaultValue = "1", value = "page") String page,
			@RequestParam(defaultValue = "6", value = "rows") String rows,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		DataRequest dataRequest = new DataRequest();
		dataRequest.setPage(StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page));
		dataRequest.setRows(StringUtils.isEmpty(rows) ? 6 : Integer.valueOf(rows));
		String errMsg=request.getParameter("errMsg");
		if(StringUtil.isNotNull(errMsg)){
			try {
				
				errMsg = new String(errMsg.getBytes("iso8859-1"),"UTF-8");
				mav.addObject("errMsg", errMsg);
				System.out.println("errMsg"+errMsg);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+path+"/weichatLoginForAnonymity";
			try {
				basePath=URLEncoder.encode(basePath, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			mav.addObject("basePath", basePath);
			mav.addObject("appid", appid);
			
		}
		mav.addObject("code", code);
		mav.addObject("type", type);
		User user=(User) session.getAttribute("USER_FOR_WEICHAT");
		/**
		 * 二手的二级菜单代码
		 */
		String goodsType=null;
//		String goodsType=request.getParameter("goodsType");
//		if(StringUtil.isNotNull(goodsType) ){
//			mav.addObject("goodsType", goodsType);
//		}
		List<HttpActivity> httpActivityList=weichatActivityService.getActivityList(type, code,goodsType, dataRequest,user);
		mav.addObject("httpActivityList", httpActivityList);
		List<HttpCategory> httpCategoryList=weichatActivityService.getAllCategorys(type);
		mav.addObject("httpCategoryList", httpCategoryList);
		if(httpActivityList.size()==6){
			List<HttpActivity> nextHttpActivityList=weichatActivityService.getActivityList(type, code,goodsType, dataRequest,user);
			if(nextHttpActivityList.size()>0){
				mav.addObject("nextParam","page="+(dataRequest.getPage()+1)+"&rows="+dataRequest.getRows());
			}
		}
		if(Constants.CONSTANTS_OCCASION_COMMUNITY.equalsIgnoreCase(type)){
			if(Constants.CONSTANTS_ALL.equalsIgnoreCase(code) || Constants.CONSTANTS_NOTICE.equalsIgnoreCase(code)){
				mav.setViewName("default.weichat.community");
			}else{
				mav.setViewName("default.weichat.community.add");
			}
			
		}else if(Constants.CONSTANTS_OCCASION_SQUARE.equalsIgnoreCase(type)){
			mav.setViewName("default.weichat.square");
		}
		return mav;
	}
	
	
	@RequestMapping(value="/listAllActivity2/{type}/{code}",method=RequestMethod.GET)
	public ModelAndView getActivity2(@PathVariable String code,
			@PathVariable String type,HttpSession session,
			@RequestParam(defaultValue = "1", value = "page") String page,
			@RequestParam(defaultValue = "6", value = "rows") String rows,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		DataRequest dataRequest = new DataRequest();
		dataRequest.setPage(StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page));
		dataRequest.setRows(StringUtils.isEmpty(rows) ? 6 : Integer.valueOf(rows));
		String errMsg=request.getParameter("errMsg");
		if(StringUtil.isNotNull(errMsg)){
			try {
				
				errMsg = new String(errMsg.getBytes("iso8859-1"),"UTF-8");
				mav.addObject("errMsg", errMsg);
				System.out.println("errMsg"+errMsg);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+path+"/weichatLoginForAnonymity";
			try {
				basePath=URLEncoder.encode(basePath, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			mav.addObject("basePath", basePath);
			mav.addObject("appid", appid);
			
		}
		mav.addObject("code", code);
		mav.addObject("type", type);
		User user=(User) session.getAttribute("USER_FOR_WEICHAT");
		/**
		 * 二手的二级菜单代码
		 */
		String goodsType=null;
//		String goodsType=request.getParameter("goodsType");
//		if(StringUtil.isNotNull(goodsType) ){
//			mav.addObject("goodsType", goodsType);
//		}
		List<HttpActivity> httpActivityList=weichatActivityService.getActivityList(type, code,goodsType, dataRequest,user);
		mav.addObject("httpActivityList", httpActivityList);
		List<HttpCategory> httpCategoryList=weichatActivityService.getAllCategorys(type);
		mav.addObject("httpCategoryList", httpCategoryList);
		if(httpActivityList.size()==6){
			List<HttpActivity> nextHttpActivityList=weichatActivityService.getActivityList(type, code,goodsType, dataRequest,user);
			if(nextHttpActivityList.size()>0){
				mav.addObject("nextParam","page="+(dataRequest.getPage()+1)+"&rows="+dataRequest.getRows());
			}
		}
		if(Constants.CONSTANTS_OCCASION_COMMUNITY.equalsIgnoreCase(type)){
			if(Constants.CONSTANTS_ALL.equalsIgnoreCase(code) || Constants.CONSTANTS_NOTICE.equalsIgnoreCase(code)){
				mav.setViewName("/weichat/community_masonry");
			}else{
				mav.setViewName("default.weichat.community.add");
			}
			
		}else if(Constants.CONSTANTS_OCCASION_SQUARE.equalsIgnoreCase(type)){
			mav.setViewName("default.weichat.square");
		}
		return mav;
	}
	
	@RequestMapping(value="/view/{type}/{code}/{id}",method=RequestMethod.GET)
	public ModelAndView view(@PathVariable String code,
			@PathVariable String type,@PathVariable String id,HttpSession session){
		ModelAndView mav = new ModelAndView();
		User user=(User) session.getAttribute("USER_FOR_WEICHAT");
		HttpActivity httpActivity=weichatActivityService.getActivity(id,user);
		mav.addObject("httpActivity", httpActivity);
		List<HttpCategory> httpCategoryList=weichatActivityService.getAllCategorys(type);
		mav.addObject("httpCategoryList", httpCategoryList);
		mav.addObject("code", code);
		mav.addObject("type", type);
		if(Constants.CONSTANTS_NOTICE.equalsIgnoreCase(code)){
			mav.setViewName("default.weichat.notice");
		}else if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code)){
			mav.setViewName("default.weichat.activity");
		}else if(Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code)){
			mav.setViewName("default.weichat.recruitment");
		}else if(Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(code)){
			mav.setViewName("default.weichat.assistant");
		}else if(Constants.CONSTANTS_COOPERATION.equalsIgnoreCase(code)){
			mav.setViewName("default.weichat.cooperation");
		}
		return mav;
	}
	
	@RequestMapping(value="/share/{type}/{code}/{id}",method=RequestMethod.GET)
	public ModelAndView share(@PathVariable String code,
			@PathVariable String type,@PathVariable String id,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		HttpActivity httpActivity=weichatActivityService.getActivity(id);
		mav.addObject("httpActivity", httpActivity);
		List<HttpCategory> httpCategoryList=weichatActivityService.getAllCategorys(type);
		mav.addObject("httpCategoryList", httpCategoryList);
		mav.addObject("code", code);
		mav.addObject("type", type);
		if(Constants.CONSTANTS_NOTICE.equalsIgnoreCase(code)){
			mav.setViewName("/weichat/share/notice_article");
		}else if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code)){
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+path+"/weichatMyCommunity";
			try {
				basePath=URLEncoder.encode(basePath, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			mav.addObject("basePath", basePath);
			mav.addObject("appid", appid);
			mav.setViewName("/weichat/share/activity_article");
		}else if(Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code)){
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+path+"/weichatMyCommunity";
			try {
				basePath=URLEncoder.encode(basePath, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			mav.addObject("basePath", basePath);
			mav.addObject("appid", appid);
			mav.setViewName("/weichat/share/recruitment_article");
		}else if(Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(code)){
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+path+"/weichatMyCommunity";
			try {
				basePath=URLEncoder.encode(basePath, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			mav.addObject("basePath", basePath);
			mav.addObject("appid", appid);
			mav.setViewName("/weichat/share/assistant_article");
		}else if(Constants.CONSTANTS_COOPERATION.equalsIgnoreCase(code)){
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+path+"/weichatMyCommunity";
			try {
				basePath=URLEncoder.encode(basePath, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			mav.addObject("basePath", basePath);
			mav.addObject("appid", appid);
			mav.setViewName("/weichat/share/cooperation_article");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/share1/{type}/{code1}/{id}",method=RequestMethod.GET)
	public ModelAndView share1(String code,@PathVariable String code1,
			@PathVariable String type,@PathVariable String id,HttpServletRequest request,HttpSession session){
		ModelAndView mav = new ModelAndView();
		try {
			JsonNode jsonUser = weiChatHelper.getUserInfo(code);
			String openid = jsonUser.path("openid").asText();
			WeiChartHttpUser httpUser = new WeiChartHttpUser();
			httpUser.setOpenid(openid);
			httpUser.setNickName(jsonUser.path("nickname").asText());
			httpUser.setAddress(jsonUser.path("country").asText()
					+ jsonUser.path("province").asText()
					+ jsonUser.path("city").asText());
			httpUser.setHeadimgurl(jsonUser.path("headimgurl").asText());
			mav.addObject("httpUser", httpUser);
			session.setAttribute(Constants.CONSTANTS_HTTP_USER_SESSION_WEICHAT, httpUser);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		HttpActivity httpActivity=weichatActivityService.getActivity(id);
		mav.addObject("httpActivity", httpActivity);
		List<HttpCategory> httpCategoryList=weichatActivityService.getAllCategorys(type);
		mav.addObject("httpCategoryList", httpCategoryList);
		mav.addObject("code", code1);
		mav.addObject("type", type);
		if(Constants.CONSTANTS_NOTICE.equalsIgnoreCase(code1)){
			mav.setViewName("/weichat/share/notice_article");
		}else if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code1)){
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+path+"/weichatMyCommunity";
			try {
				basePath=URLEncoder.encode(basePath, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			mav.addObject("basePath", basePath);
			mav.addObject("appid", appid);
			mav.setViewName("/weichat/share/activity_article");
		}else if(Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code1)){
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+path+"/weichatMyCommunity";
			try {
				basePath=URLEncoder.encode(basePath, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			mav.addObject("basePath", basePath);
			mav.addObject("appid", appid);
			mav.setViewName("/weichat/share/recruitment_article");
		}else if(Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(code1)){
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+path+"/weichatMyCommunity";
			try {
				basePath=URLEncoder.encode(basePath, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			mav.addObject("basePath", basePath);
			mav.addObject("appid", appid);
			mav.setViewName("/weichat/share/assistant_article");
		}else if(Constants.CONSTANTS_COOPERATION.equalsIgnoreCase(code1)){
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+path+"/weichatMyCommunity";
			try {
				basePath=URLEncoder.encode(basePath, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			mav.addObject("basePath", basePath);
			mav.addObject("appid", appid);
			mav.setViewName("/weichat/share/cooperation_article");
		}
		
		return mav;
	}
	
	@RequestMapping(value="/shared/{id}",method=RequestMethod.GET)
	@ResponseBody
	public String shared(@PathVariable String id){
		String result=weichatActivityService.updateSharedActivity(id);
		return result;
	}
	
	
	@RequestMapping(value="/apply/{type}/{code}/{id}",method=RequestMethod.GET)
	public ModelAndView apply(@PathVariable String code,
			@PathVariable String type,@PathVariable String id,HttpSession session){
		ModelAndView mav = new ModelAndView();
		User user=(User) session.getAttribute("USER_FOR_WEICHAT");
		List<HttpFieldType> list=weichatActivityService.getField(id,user);
		
		mav.addObject("code", code);
		mav.addObject("type", type);
		mav.addObject("id", id);
		mav.addObject("list", list);
		List<HttpCategory> httpCategoryList=weichatActivityService.getAllCategorys(type);
		mav.addObject("httpCategoryList", httpCategoryList);
		mav.setViewName("default.weichat.apply");
		return mav;
	}
	
	@RequestMapping(value="/applyForShare/{type}/{code}/{id}",method=RequestMethod.GET)
	public ModelAndView applyForShare(@PathVariable String code,
			@PathVariable String type,@PathVariable String id,HttpSession session){
		ModelAndView mav = new ModelAndView();
		User user=null;
		
		WeiChartHttpUser httpUser =(WeiChartHttpUser) session.getAttribute(Constants.CONSTANTS_HTTP_USER_SESSION_WEICHAT);
		String openid=httpUser.getOpenid();
		user = weichatUserService.findByOpenid(openid);
		if(user==null){
			user=new User();
			user.setNickName(httpUser.getNickName());
		}
//			WeiChartHttpUser httpUser = new WeiChartHttpUser();
//			httpUser.setOpenid(openid);
//			httpUser.setNickName(jsonUser.path("nickname").asText());
//			httpUser.setAddress(jsonUser.path("country").asText()
//					+ jsonUser.path("province").asText()
//					+ jsonUser.path("city").asText());
//			httpUser.setHeadimgurl(jsonUser.path("headimgurl").asText());
		mav.addObject("httpUser", httpUser);
		
//		User user=(User) session.getAttribute("USER_FOR_WEICHAT");
		List<HttpFieldType> list=weichatActivityService.getField(id,user);
		
		mav.addObject("code", code);
		mav.addObject("type", type);
		mav.addObject("id", id);
		mav.addObject("list", list);
		mav.setViewName("/weichat/share/apply");
		return mav;
	}
	
//	@RequestMapping(value="/save",method=RequestMethod.GET)
//	public ModelAndView save(HttpServletRequest request, HttpServletResponse response,HttpSession session){
//		ModelAndView mav = new ModelAndView();
//		String id=request.getParameter("id");
//		String type=request.getParameter("type");
//		User user=(User) session.getAttribute("USER_FOR_WEICHAT");
//		String msg=weichatActivityService.saveApplication(id, request,user);
//		
//		if(Constants.CONSTANTS_OCCASION_COMMUNITY.equalsIgnoreCase(type)){
//			mav.setViewName("redirect:/weichatActivity/listAllActivity/"+Constants.CONSTANTS_OCCASION_COMMUNITY+"/"+Constants.CONSTANTS_ALL);
//		}else{
//			mav.setViewName("redirect:/weichatActivity/listAllActivity/"+Constants.CONSTANTS_OCCASION_SQUARE+"/"+Constants.CONSTANTS_ALL);
//		}
//		return mav;
//	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public Json save(HttpServletRequest request, HttpServletResponse response,HttpSession session){
		LOG.info("apply>>>>>>>start");
		Json j=new Json();
		String id=request.getParameter("id");
		LOG.info("id>>>>>>>"+id);
		Object attribute = session.getAttribute("USER_FOR_WEICHAT");
		User user=null;
		if(attribute==null){
			String openid=request.getParameter("openid");
			LOG.info("openid>>>>>>>"+openid);
			user = weichatUserService.findByOpenid(openid);
			LOG.info("user>>>>>>>"+user);
			if(user==null){
				WeiChartHttpUser httpUser=new WeiChartHttpUser();
				httpUser.setAddress(request.getParameter("address"));
				httpUser.setHeadimgurl(request.getParameter("headimgurl"));
				httpUser.setOpenid(openid);
				String nickName = request.getParameter("nickName");
				System.out.println("nickName==============="+nickName);
				httpUser.setNickName(nickName);
				System.out.println("nickName==============="+httpUser.getNickName());
				user=weichatUserService.saveUserInfo(httpUser);
			}
		}else{
			user=(User) attribute;
		}
		j=weichatActivityService.saveApplication(id, request,user);
		LOG.info("msg>>>>>>>"+j.getMsg());
		LOG.info("apply>>>>>>>end");
		return j;
	}
	
	@RequestMapping(value="/cancelApplication/{type}/{code}/{id}",method=RequestMethod.GET)
	public ModelAndView cancelApplication(@PathVariable String code,
			@PathVariable String type,@PathVariable String id,HttpSession session){
		ModelAndView mav = new ModelAndView();
		User user=(User) session.getAttribute("USER_FOR_WEICHAT");
		weichatActivityService.cancelApplication(id,user);
		mav.setViewName("redirect:/weichatActivity/view/"+type+"/"+code+"/"+id);
		return mav;
	}
	
	@RequestMapping(value="/agree/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Json agree(@PathVariable String id,HttpServletRequest request){
		Json j=new Json();
		
		try {
			weichatActivityService.agree(id);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		return j;
	}
	
	@RequestMapping(value="/reject/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Json reject(@PathVariable String id,HttpServletRequest request){
		Json j=new Json();
		
		try {
			weichatActivityService.reject(id);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		return j;
	}
	
	@RequestMapping(value="/createActivity/{type}/{code}",method=RequestMethod.GET)
	public ModelAndView createActivity(@PathVariable String code,@PathVariable String type,HttpActivity httpActivity){
		ModelAndView mav = new ModelAndView();
		mav.addObject("code", code);
		mav.addObject("type", type);
		List<HttpCategory> httpCategoryList=weichatActivityService.getAllCategorys(type);
		List<HttpCategory> httpCategoryChildren=weichatActivityService.getCategorysByCode(code);
		if(httpCategoryChildren.size()>0){
			mav.addObject("httpCategoryChildren", httpCategoryChildren);
		}
		mav.addObject("httpCategoryList", httpCategoryList);
		List<FieldsType> list=fieldTypeService.getFieldType();
		httpActivity.setFieldsType(list);
		if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code)){
			mav.setViewName("default.weichat.activity.manager");
		}else if(Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code)){
			mav.setViewName("default.weichat.recruitment.manager");
		}else if(Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(code)){
			mav.setViewName("default.weichat.assistant.manager");
		}else if(Constants.CONSTANTS_COOPERATION.equalsIgnoreCase(code)){
			mav.setViewName("default.weichat.cooperation.manager");
		}
		return mav;
	}
	
	@RequestMapping(value="/editActivity/{id}",method=RequestMethod.GET)
	public ModelAndView editActivity(@PathVariable String id){
		ModelAndView mav = new ModelAndView();
		HttpActivity httpActivity=weichatActivityService.getActivity(id);
//		String content=httpActivity.getContent();
//		if(content.indexOf("<br/>")>0){
//			String imgUrl=content.substring(0,content.indexOf("<br/>"));
//			System.out.println(imgUrl);
//			content=content.substring(content.indexOf("<br/>")+5);
//			httpActivity.setImgUrl(imgUrl);
//			httpActivity.setContent(content);
//		}
		List<FieldsType> list=fieldTypeService.getFieldType();
		httpActivity.setFieldsType(list);
		mav.addObject("httpActivity", httpActivity);
		String code=httpActivity.getCode();
		if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code)){
			mav.setViewName("/weichat/activity_edit");
		}else if(Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code)){
			mav.setViewName("/weichat/recruitment_edit");
		}else if(Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(code)){
			mav.setViewName("/weichat/assistant_edit");
		}else if(Constants.CONSTANTS_COOPERATION.equalsIgnoreCase(code)){
			mav.setViewName("/weichat/cooperation_edit");
		}
		return mav;
	}
	
	@RequestMapping(value="/viewActivity/{id}",method=RequestMethod.GET)
	public ModelAndView viewActivity(@PathVariable String id){
		ModelAndView mav = new ModelAndView();
		HttpActivity httpActivity=weichatActivityService.getActivity(id);
		
		mav.addObject("httpActivity", httpActivity);
		String code=httpActivity.getCode();
		if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code)){
			mav.setViewName("/weichat/activity_view");
		}else if(Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code)){
			mav.setViewName("/weichat/recruitment_view");
		}else if(Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(code)){
			mav.setViewName("/weichat/assistant_view");
		}else if(Constants.CONSTANTS_COOPERATION.equalsIgnoreCase(code)){
			mav.setViewName("/weichat/cooperation_view");
		}
		return mav;
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView saveOrEdit(
			@RequestParam(value = "file", required = false) MultipartFile file,
			@Valid HttpActivity httpActivity, BindingResult result,HttpServletRequest request,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String code = httpActivity.getCode();
		String type=httpActivity.getType();
		mav.addObject("code", code);
		mav.addObject("type", type);
		if(result.hasErrors()){
			List<FieldsType> list=fieldTypeService.getFieldType();
			httpActivity.setFieldsType(list);
			if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code)){
				mav.setViewName("default.weichat.activity.manager");
			}else if(Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code)){
				mav.setViewName("default.weichat.recruitment.manager");
			}else if(Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(code)){
//				List<HttpCategory> httpCategoryChildren=weichatActivityService.getCategorysByCode(code);
//				if(httpCategoryChildren.size()>0){
//					mav.addObject("httpCategoryChildren", httpCategoryChildren);
//				}
				mav.setViewName("default.weichat.assistant.manager");
			}else if(Constants.CONSTANTS_COOPERATION.equalsIgnoreCase(code)){
				mav.setViewName("default.weichat.cooperation.manager");
			}
			
			List<HttpCategory> httpCategoryList=weichatActivityService.getAllCategorys(type);
			mav.addObject("httpCategoryList", httpCategoryList);
		}else{
			
			if (null!=file&&StringUtils.isNotEmpty(file.getOriginalFilename())) {
				String temp = file.getOriginalFilename();
				String fileName = System.currentTimeMillis()
						+ temp.substring(temp.lastIndexOf("."));
				File targetFile = new File(FilePathHelper.getPath(fileUploadDisk + fileUploadPath
						+ logoPath), fileName);
				if (!targetFile.getParentFile().exists()) {
					targetFile.getParentFile().mkdirs();
				}
				
				try {
					file.transferTo(targetFile);
					httpActivity.setImgUrl(FilePathHelper.getPath(fileUploadPath
						+ logoPath)+"/"+fileName);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
//			String imgUrl = httpActivity.getImgUrl();
//			if(StringUtil.isNotNull(imgUrl) && imgUrl.indexOf("base64")>0){
//				path=jsoupService.parseImg(imgUrl);
//			}else{
//				httpActivity.setContent(imgUrl+"<br/>"+httpActivity.getContent());
//			}
			User user=(User) session.getAttribute("USER_FOR_WEICHAT");
			weichatActivityService.saveorEdit(httpActivity,request,user);
			if(StringUtil.isNotNull(httpActivity.getId())){
				mav.setViewName("redirect:/weichatShowMyActivity");
			}else{
				mav.setViewName("redirect:/weichatActivity/listAllActivity/"+type+"/"+code);
			}
			
		}
		
		return mav;
	}
	
	@RequestMapping(value="/submitComment",method=RequestMethod.GET)
	@ResponseBody
	public Json submitComment(HttpActivityComments httpActivityComments,HttpSession session){
		Json j=new Json();
		User user=null;
		if(session.getAttribute("USER_FOR_WEICHAT")!=null){
			user=(User) session.getAttribute("USER_FOR_WEICHAT");
		}else{
			System.out.println("openId==========="+httpActivityComments.getOpenId());
			user = weichatUserService.findByOpenid(httpActivityComments.getOpenId());
			if(user==null){
				WeiChartHttpUser httpUser=new WeiChartHttpUser();
				httpUser.setAddress(httpActivityComments.getAddress());
				httpUser.setHeadimgurl(httpActivityComments.getHeadimgurl());
				httpUser.setOpenid(httpActivityComments.getOpenId());
				try {
					String nickName = httpActivityComments.getNickName();
					System.out.println("nickName==============="+nickName);
					httpUser.setNickName(new String(nickName.getBytes("iso8859-1"),"UTF-8"));
					System.out.println("nickName==============="+httpUser.getNickName());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				user=weichatUserService.saveUserInfo(httpUser);
			}
		}
		
		try{
			String activityId = httpActivityComments.getActivityId();
			String content = httpActivityComments.getContent();
			content = new String(content.getBytes("iso8859-1"),"UTF-8");
			List<HttpActivityComments> httpActivityCommentlist=weichatActivityService.submitComment(activityId,content, user);
			j.setObj(httpActivityCommentlist);
			j.setSuccess(true);
		}catch(Exception e){
			
		}
		
		return j;
	}
	
	
	@InitBinder("httpActivity")
	protected void initbinder(WebDataBinder  binder){
		binder.setValidator(new WeichatActivityValidate());
	}
	
	@RequestMapping(value="/createSignature",method=RequestMethod.GET)
	@ResponseBody
	private Json createSignature(HttpServletRequest request) throws JsonProcessingException, IOException{
		String url=request.getParameter("url");
		System.out.println("url============="+url);
		Json j=new Json();
		String nonceStr = UUID.randomUUID().toString();
		long timestamp = System.currentTimeMillis() / 1000;
		String[] str = { "noncestr=" + nonceStr,
				"jsapi_ticket=" + weiChatHelper.getAccessTicket(),
				"timestamp=" + timestamp, "url=" + url };
		Arrays.sort(str); // 字典序排序
		String bigStr = str[0] + "&" + str[1] + "&" + str[2] + "&" + str[3];
		// SHA1加密
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(bigStr.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
			WeichatSinguate ws=new WeichatSinguate();
			ws.setAppId(weiChatHelper.getAppid());
			ws.setNonceStr(nonceStr);
			ws.setSignature(signature);
			ws.setTimestamp(timestamp);
			j.setObj(ws);
			j.setSuccess(true);
			System.out.println("signature============="+signature);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			j.setSuccess(false);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			j.setSuccess(false);
		}
		return j;
	}
	
	private String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	
	@RequestMapping(value="/showTemplateMessage/{code}/{openid}/{id}",method=RequestMethod.GET)
	public ModelAndView showTemplateMessage(@PathVariable String code,
			@PathVariable String openid,@PathVariable String id,HttpSession session){
		ModelAndView mav = new ModelAndView();
		User user = weichatUserService.findByOpenid(openid);
		session.setAttribute("USER_FOR_WEICHAT", user);
		mav.setViewName("redirect:/weichatActivity/view/1/"+code+"/"+id);
		return mav;
	}
	
	@RequestMapping(value="/weichatShowUserInfo/{openid}",method=RequestMethod.GET)
	public ModelAndView weichatShowUserInfo(@PathVariable String openid,HttpSession session){
		ModelAndView mav = new ModelAndView();
		User user = weichatUserService.findByOpenid(openid);
		session.setAttribute("USER_FOR_WEICHAT", user);
		mav.setViewName("redirect:/weichatShowUserInfo");
		return mav;
	}
}
