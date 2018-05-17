package com.jfdh.controller;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.jfdh.excel.ApplicationExcel;
import com.jfdh.httpmodel.BackendUserDtail;
import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.DataResponse;
import com.jfdh.httpmodel.HttpActivity;
import com.jfdh.httpmodel.HttpApplication;
import com.jfdh.httpmodel.HttpMenu;
import com.jfdh.httpmodel.HttpSearch;
import com.jfdh.httpmodel.HttpUser;
import com.jfdh.httpmodel.Json;
import com.jfdh.model.FieldsType;
import com.jfdh.model.User;
import com.jfdh.service.IActivityService;
import com.jfdh.service.IFieldTypeService;
import com.jfdh.service.ISendMessageService;
import com.jfdh.service.ScoreShopService;
import com.jfdh.util.Constants;
import com.jfdh.util.JsoupService;
import com.jfdh.util.StringUtil;
import com.jfdh.validate.ActivityValidate;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;

@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController{
	@Autowired
	private ScoreShopService scoreShopService;
	private IActivityService activityService;
	private IFieldTypeService fieldTypeService;
	private JsoupService jsoupService;

	@Autowired
	private ISendMessageService sendService;
	
	public IActivityService getActivityService() {
		return activityService;
	}

	@Autowired
	public void setActivityService(IActivityService activityService) {
		this.activityService = activityService;
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

	@RequestMapping(value="/getMenu",method=RequestMethod.GET)
	@ResponseBody
	public Json getMenu(String role,HttpServletRequest request){
		Json j=new Json();
		List<HttpMenu> httpMenuList=activityService.getMenu(role,request);
		j.setObj(httpMenuList);
		return j;
	}
	
	@RequestMapping(value="/{menu}/{code}",method=RequestMethod.GET)
	public ModelAndView dispatcher(@PathVariable String menu,@PathVariable String code,HttpUser user, HttpSession session,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		if(StringUtil.isNotNull(code) ){
			mav.addObject("code", code);
			if(Constants.CONSTANTS_NOTICE.equalsIgnoreCase(code)){
				mav.setViewName("default.notice");
			}else if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code)){
				mav.setViewName("default.active.manage");
			}else if(Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code)){
				mav.setViewName("default.recruitment.manage");
			}else if(Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(code)){
				mav.setViewName("default.assistant.manage");
			}else if(Constants.CONSTANTS_COOPERATION.equalsIgnoreCase(code)){
				mav.setViewName("default.cooperation.manage");
			}
		}
		
		return mav;
	}
	
	
	@RequestMapping(value="/pay/{code}",method=RequestMethod.GET)
	public String pay(@PathVariable String code){
		return "default.pay";
	}
	
	
	@RequestMapping(value="/jqgrid/{code}",method=RequestMethod.GET)
	@ResponseBody
	public DataResponse datagrid(
			@PathVariable String code,
			DataRequest dr,
			HttpSession session,
			@RequestParam(required = false, value = "searchField") String searchField,
			@RequestParam(required = false, value = "searchOper") String searchOper,
			@RequestParam(required = false, value = "searchString") String searchString,
			@RequestParam(required = false, value = "filters") String filters) {
		 
		 try {
			 if(StringUtil.isNotNull(filters)){
				 filters = new String(filters.getBytes("iso8859-1"),"UTF-8");
				 JSONParser parser1 = new JSONParser(new StringReader(filters)); 
				 JSONValue jsonValue1 = parser1.nextValue();
				 HttpSearch httpSearch=(com.jfdh.httpmodel.HttpSearch) JSONMapper.toJava(jsonValue1, HttpSearch.class);
				 return activityService.dataGrid(dr,session, code,httpSearch);
			 }
		} catch (TokenStreamException e) {
			e.printStackTrace();
		} catch (RecognitionException e) {
			e.printStackTrace();
		} catch (MapperException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		 return activityService.dataGrid(dr,session, code,null);
	}
	
	@RequestMapping(value="/add/{code}",method=RequestMethod.GET)
	public ModelAndView add(@PathVariable String code,HttpActivity httpActivity){
		ModelAndView mav = new ModelAndView();
		mav.addObject("code", code);
		List<FieldsType> list=fieldTypeService.getFieldType();
		httpActivity.setFieldsType(list);
		
		User user=activityService.currentUser();
		String roleKey=user.getRole().getRoleKey();
		if(Constants.CONSTANTS_COMMUNITY_ADMIN.equalsIgnoreCase(roleKey) || Constants.CONSTANTS_COMMUNITY_USER.equalsIgnoreCase(roleKey)){
			httpActivity.setGovOrgIds(user.getGovOrg().getId());
		}
		
		if(user.getGovOrg()!=null){
			httpActivity.setSponsor(user.getGovOrg().getName().replaceAll("社区", "居委会"));
		}else if(user.getOrg()!=null){
			httpActivity.setSponsor(user.getOrg().getName());
		}else{
			if(Constants.CONSTANTS_SYS_ADMIN.equalsIgnoreCase(user.getRole().getRoleKey())){
				httpActivity.setSponsor(Constants.CONSTANTS_SMILE_TEAM);
			}
		}
		
		if(Constants.CONSTANTS_NOTICE.equals(code)){
			mav.setViewName("default.notice.add");
		}else if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code)){
			mav.setViewName("default.active.add");
		}else if(Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code)){
			mav.setViewName("default.recruitment.add");
		}
		return mav;
		
	}
	
	@RequestMapping(value="/edit/{code}",method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable String code,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		String id=request.getParameter("id");
		HttpActivity httpActivity=activityService.getActivity(id);
		mav.addObject("httpActivity", httpActivity);
		if(Constants.CONSTANTS_NOTICE.equals(code)){
			mav.setViewName("default.notice.add");
		}else if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code)){
			List<FieldsType> list=fieldTypeService.getFieldType();
			httpActivity.setFieldsType(list);
			mav.setViewName("default.active.add");
		}else if(Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code)){
			List<FieldsType> list=fieldTypeService.getFieldType();
			httpActivity.setFieldsType(list);
			mav.setViewName("default.recruitment.add");
		}
		return mav;
		
	}
	
	@RequestMapping(value="/view/{code}",method=RequestMethod.GET)

	public ModelAndView view(@PathVariable String code,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		String id=request.getParameter("id");
		HttpActivity httpActivity=activityService.getActivity(id);
		mav.addObject("httpActivity", httpActivity);
		if(Constants.CONSTANTS_ASSISTANT.equals(code)){
			mav.setViewName("default.assistant.manage.view");
		}else if(Constants.CONSTANTS_COOPERATION.equalsIgnoreCase(code)){
			mav.setViewName("default.cooperation.manage.view");
		}
		return mav;
		
	}
	
	@RequestMapping(value="/checkView/{code}",method=RequestMethod.GET)
	public ModelAndView checkView(@PathVariable String code,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		String id=request.getParameter("id");
		HttpActivity httpActivity=activityService.getActivity(id);
		mav.addObject("httpActivity", httpActivity);
		mav.setViewName("default.check.manage.view");
		return mav;
		
	}
	
	
	@RequestMapping(value="/del",method=RequestMethod.GET)
	@ResponseBody
	public Json del(HttpServletRequest request){
		String ids=request.getParameter("ids");
		Json j=new Json();
		try {
			activityService.del(ids);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		return j;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView saveOrEdit(@Valid HttpActivity httpActivity,BindingResult result, HttpSession session,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		String code = httpActivity.getCode();
		String sendMessage=request.getParameter("sendMessage");
		boolean flag=Boolean.parseBoolean(sendMessage);
		if(result.hasErrors()){
			if(Constants.CONSTANTS_NOTICE.equals(code)){
				mav.setViewName("default.notice.add");
			}else if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code)){
				List<FieldsType> list=fieldTypeService.getFieldType();
				httpActivity.setFieldsType(list);
				mav.setViewName("default.active.add");
			}else if(Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code)){
				List<FieldsType> list=fieldTypeService.getFieldType();
				httpActivity.setFieldsType(list);
				mav.setViewName("default.recruitment.add");
			}
		}else{
			String content=httpActivity.getContent();
			System.out.println(content);
			if(StringUtil.isNotNull(content) && content.indexOf("base64")>0){
				content=jsoupService.parse(content,request.getContextPath());
				System.out.println(content);
				httpActivity.setContent(content);
			}
			
			String result2=activityService.saveorEditActivity(httpActivity,flag);
			if(null!=result2){
				result.addError(new FieldError("httpActivity", "score",result2));
				if(Constants.CONSTANTS_NOTICE.equals(code)){
					mav.setViewName("default.notice.add");
				}else if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code)){
					List<FieldsType> list=fieldTypeService.getFieldType();
					httpActivity.setFieldsType(list);
					mav.setViewName("default.active.add");
				}else if(Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code)){
					List<FieldsType> list=fieldTypeService.getFieldType();
					httpActivity.setFieldsType(list);
					mav.setViewName("default.recruitment.add");
				}
			}else{
				if(Constants.CONSTANTS_NOTICE.equals(code)){
					mav.setViewName("default.notice");
				}else if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code)){
					mav.setViewName("default.active.manage");
				}else if(Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code)){
					mav.setViewName("redirect:/activity/recruitment/04");
				}
//				if(flag && !StringUtil.isNotNull(httpActivity.getId()) && httpActivity.getValid()){
//					sendService.SendNoticeMessage(httpActivity);
//				}
			}
		}
		
		mav.addObject("code", code);
		return mav;
	}

	@InitBinder("httpActivity")
	protected void initbinder(WebDataBinder  binder){
		binder.setValidator(new ActivityValidate());
	}
	
	
	
	@RequestMapping(value="/applyData/{code}",method=RequestMethod.GET)
	public ModelAndView applyData(@PathVariable String code,HttpUser user, HttpSession session,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.addObject("code", code);
		mav.setViewName("default.application");
		return mav;
	}
	
	
	@RequestMapping(value="/applyGrid/{code}",method=RequestMethod.GET)
	@ResponseBody
	public DataResponse applyGrid(@PathVariable String code,DataRequest dr,HttpSession session,@RequestParam(required = false, value = "searchField") String searchField,
			@RequestParam(required = false, value = "searchOper") String searchOper,
			@RequestParam(required = false, value = "searchString") String searchString,
			@RequestParam(required = false, value = "filters") String filters){
		 try {
			 
			 BackendUserDtail userDetails = (BackendUserDtail) SecurityContextHolder.getContext()
					    .getAuthentication()
					    .getPrincipal();
			 
			 if(StringUtil.isNotNull(filters)){
				 filters = new String(filters.getBytes("iso8859-1"),"UTF-8");
				 JSONParser parser1 = new JSONParser(new StringReader(filters)); 
				 JSONValue jsonValue1 = parser1.nextValue();
				 HttpSearch httpSearch=(com.jfdh.httpmodel.HttpSearch) JSONMapper.toJava(jsonValue1, HttpSearch.class);
				 session.setAttribute("filters"+userDetails.getId(), filters);
				 return activityService.applyGrid(dr,session, code,httpSearch);
			 }else{
				 session.removeAttribute("filters"+userDetails.getId());
			 }
		 } catch (TokenStreamException e) {
				e.printStackTrace();
			} catch (RecognitionException e) {
				e.printStackTrace();
			} catch (MapperException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		return activityService.applyGrid(dr,session, code,null);
	}
	
	
	@RequestMapping(value="/payData/{code}",method=RequestMethod.GET)
	@ResponseBody
	public DataResponse payData(@PathVariable String code,DataRequest dr,HttpSession session){
		return activityService.pay(dr,session, code);
	}
	
	@RequestMapping(value="/application/{code}/{id}",method=RequestMethod.GET)
	@ResponseBody
	public String application(@PathVariable String code,@PathVariable String id){
		return scoreShopService.application(code,id);
	}
	
	
	@RequestMapping(value="/downloadExcel/{type}",method=RequestMethod.GET)
	public ModelAndView download(@PathVariable String type,HttpSession session){
		
		ModelAndView mav = new ModelAndView(new ApplicationExcel());
		List<FieldsType> typeList=fieldTypeService.getFieldType();
		List<HttpApplication> applicationList=activityService.getHttpApplication(type,session);
		mav.addObject("typeList", typeList);
		mav.addObject("applicationList", applicationList);
		return mav;
	}
	
	
	@RequestMapping(value="/paymant",method=RequestMethod.GET)
	@ResponseBody
	public Json paymant(HttpServletRequest request){
		String ids=request.getParameter("ids");
		Json j=new Json();
		try {
			j=activityService.payment(ids);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		return j;
	}
	
	@RequestMapping(value="/check/{code}",method=RequestMethod.GET)
	public ModelAndView check(@PathVariable String code,HttpUser user, HttpSession session,
			HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.addObject("code", code);
		mav.setViewName("default.check");
		return mav;
	}
	
	@RequestMapping(value="/checkGrid/{code}",method=RequestMethod.GET)
	@ResponseBody
	public DataResponse checkGrid(@PathVariable String code,DataRequest dr,HttpSession session){
		return activityService.checkGrid(dr,session, code);
	}
	
	@RequestMapping(value="/agreeForActivity/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Json agreeForActivity(@PathVariable String id,HttpServletRequest request){
		Json j=new Json();
		
		try {
			activityService.agreeForActivity(id);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		return j;
	}
	
	@RequestMapping(value="/rejectForActivity/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Json rejectForActivity(@PathVariable String id,HttpServletRequest request){
		Json j=new Json();
		
		try {
			activityService.rejectForActivity(id);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		return j;
	}
	
	
	@RequestMapping(value="/enable",method=RequestMethod.GET)
	@ResponseBody
	public Json enable(HttpServletRequest request){
		String ids=request.getParameter("ids");
		Json j=new Json();
		try {
			activityService.enable(ids);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		return j;
	}
	
	@RequestMapping(value="/notenable",method=RequestMethod.GET)
	@ResponseBody
	public Json notenable(HttpServletRequest request){
		String ids=request.getParameter("ids");
		Json j=new Json();
		try {
			activityService.notenable(ids);
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		return j;
	}
	
	@RequestMapping(value="/showComment/{code}/{activityId}",method=RequestMethod.GET)
	public ModelAndView showComment(@PathVariable String code,@PathVariable String activityId){
		ModelAndView mav = new ModelAndView();
		HttpActivity hActivity=activityService.getComments(activityId);
		mav.addObject("hActivity", hActivity);
		mav.addObject("code", code);
		mav.addObject("activityId", activityId);
		
		if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code)){
			mav.setViewName("default.active.comment");
		}else if(Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code)){
			mav.setViewName("default.recruitment.comment");
		}else if(Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(code)){
			mav.setViewName("default.active.assistant");
		}else if(Constants.CONSTANTS_COOPERATION.equalsIgnoreCase(code)){
			mav.setViewName("default.active.cooperation");
		}
		return mav;
	}
	
	@RequestMapping(value="/delComment/{code}/{activityId}/{id}",method=RequestMethod.GET)
	public ModelAndView delComment(@PathVariable String code,@PathVariable String activityId,@PathVariable String id){
		ModelAndView mav = new ModelAndView();
		activityService.delComment(id);
		mav.setViewName("redirect:/activity/showComment/"+code+"/"+activityId);
		return mav;
	}
	
}
