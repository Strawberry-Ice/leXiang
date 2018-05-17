package com.jfdh.service.impl.weichat;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfdh.dao.IBaseDao;
import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.HttpActivity;
import com.jfdh.httpmodel.HttpActivityComments;
import com.jfdh.httpmodel.HttpApplication;
import com.jfdh.httpmodel.HttpCategory;
import com.jfdh.httpmodel.HttpFieldType;
import com.jfdh.httpmodel.HttpUser;
import com.jfdh.httpmodel.Json;
import com.jfdh.model.Activity;
import com.jfdh.model.ActivityComments;
import com.jfdh.model.Application;
import com.jfdh.model.Category;
import com.jfdh.model.FieldValue;
import com.jfdh.model.FieldsType;
import com.jfdh.model.GovOrg;
import com.jfdh.model.User;
import com.jfdh.model.VerifyStatus;
import com.jfdh.repository.ScorePoolRepository;
import com.jfdh.repository.UserRepository;
import com.jfdh.service.ISendMessageService;
import com.jfdh.service.impl.BaseServiceImpl;
import com.jfdh.service.weichat.IActivityService;
import com.jfdh.util.Constants;
import com.jfdh.util.DateUtil;
import com.jfdh.util.JpaTransactional;
import com.jfdh.util.StringUtil;

/**
 * 资源Service实现�?
 * 
 * @author 
 * 
 */
@Service("weichatActivityService")
public class ActivityServiceImpl extends BaseServiceImpl implements IActivityService {
	public static final Logger LOG=LoggerFactory.getLogger(ActivityServiceImpl.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ScorePoolRepository scorePoolRepository;
	private IBaseDao<Activity> activityDao;
	private IBaseDao<Category> categoryDao;
	private IBaseDao<Application> applicationDao;
	private IBaseDao<ActivityComments> activityCommentsDao;
	
	
	@Autowired
	private ISendMessageService sendService;
	
	public IBaseDao<Activity> getActivityDao() {
		return activityDao;
	}
	@Autowired
	public void setActivityDao(IBaseDao<Activity> activityDao) {
		this.activityDao = activityDao;
	}
	public IBaseDao<Category> getCategoryDao() {
		return categoryDao;
	}
	@Autowired
	public void setCategoryDao(IBaseDao<Category> categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	
	@JpaTransactional
	public IBaseDao<Application> getApplicationDao() {
		return applicationDao;
	}
	@Autowired
	public void setApplicationDao(IBaseDao<Application> applicationDao) {
		this.applicationDao = applicationDao;
	}
	
	public IBaseDao<ActivityComments> getActivityCommentsDao() {
		return activityCommentsDao;
	}
	@Autowired
	public void setActivityCommentsDao(
			IBaseDao<ActivityComments> activityCommentsDao) {
		this.activityCommentsDao = activityCommentsDao;
	}
	/**
	 * type 是发布场合类型
	 * 
	 * 启用二手的二级菜单时启用这段代码
	 */
//	public List<HttpActivity> getActivityList(String type,String code,String goodsType,DataRequest request,User user) {
//		user=userRepository.findOne("3");
//		Category category=new Category();
//		Category child=new Category();
//		if(StringUtil.isNotNull(goodsType)){
//			child=this.getCategory(goodsType);
//			category=child.getParent();
//		}else{
//			category=this.getCategory(code);
//		}
//		
//		List<Activity> list=new ArrayList<Activity>();
//		List<HttpActivity> httpActivityList=new ArrayList<HttpActivity>();
//		String govOrgId="";
//		if(user.getGovOrg()!=null){
//			govOrgId=user.getGovOrg().getId();
//			System.out.println("govOrgId"+govOrgId);
//		}
//		
//		String hql="from Activity";
//		List<Object> param=new ArrayList<Object>();
//		
//		if(!Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(code)){
//			hql+=" where (verifyStatus !=? or verifyStatus !=?  or verifyStatus is null)";
//			VerifyStatus unverified=VerifyStatus.UNVERIFIED;
//			param.add(unverified);
//			VerifyStatus fail=VerifyStatus.FAIL;
//			param.add(fail);
//		}else{
//			hql+=" where 1=1";
//		}
//		
//		if(Constants.CONSTANTS_OCCASION_COMMUNITY.equalsIgnoreCase(type)){
//			hql+=" and govOrg.id=? and (occasion=? or occasion is null)";
//			param.add(govOrgId);
//			param.add(type);
//		}else if(Constants.CONSTANTS_OCCASION_SQUARE.equalsIgnoreCase(type)){
//			hql+=" and occasion=?";
//			param.add(type);
//		}
//		if(!Constants.CONSTANTS_ALL.equalsIgnoreCase(code) && !Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(code)){
//				hql+=" and category.id=?";
//				param.add(category.getId());
//		}
//		
//		if(StringUtil.isNotNull(goodsType) ){
//			hql+=" and category.id=?";
//			param.add(child.getId());
//		}
//		
//		hql+=" order by createDate desc";
//		list=activityDao.find(hql, request.getPage(), request.getRows(), param);
//		
//		for(Activity activity:list){
//			HttpActivity httpActivity=new HttpActivity();
//			BeanUtils.copyProperties(activity, httpActivity);
//			Category category2 = activity.getCategory();
//			String categoryType=category2.getCode();
//			System.out.println("1"+categoryType);
//			Category parent = category2.getParent();
//			if(parent!=null){
//				categoryType=parent.getCode();
//			}
//			System.out.println("2"+categoryType);
//			this.copyActivityToHttpActivity(httpActivity, activity, categoryType);
//			
//			//二手时需要考虑二级菜单
//			if(Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(code)){
//				if(parent!=null){
//					if(parent.getCode().equalsIgnoreCase(code)){
//						httpActivity.setGoodsType(category2.getCode());
//						httpActivity.setGoodsTypeName(category2.getName());
//						httpActivity.setGoodsStatus(activity.getVerifyStatus().toString());
//						httpActivityList.add(httpActivity);
//					}
//				}
//			}else{
//				System.out.println("3"+categoryType);
//				if(Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(categoryType)){
//					httpActivity.setGoodsType(category2.getCode());
//					httpActivity.setGoodsTypeName(category2.getName());
//					httpActivity.setGoodsStatus(activity.getVerifyStatus().toString());
//				}
//				httpActivityList.add(httpActivity);
//			}
//			
//			httpActivity.setType(categoryType);
//			
//			if(activity.getCreator()!=null){
//				httpActivity.setNickName(activity.getCreator().getNickName());
//			}
//			List<ActivityComments> comments=activity.getComments();
//			if(comments!=null && comments.size()>0){
//				httpActivity.setCommentCounts(String.valueOf(comments.size()));
//			}else{
//				httpActivity.setCommentCounts(String.valueOf(0));
//			}
//			
//			
//		}
//		return httpActivityList;
//	}
	
	public List<HttpActivity> getActivityList(String type,String code,String goodsType,DataRequest request,User user) {
		List<Activity> list=new ArrayList<Activity>();
		List<HttpActivity> httpActivityList=new ArrayList<HttpActivity>();
		String govOrgId="";
		if(user.getGovOrg()!=null){
			govOrgId=user.getGovOrg().getId();
			System.out.println("govOrgId"+govOrgId);
		}
		
		String hql="from Activity where valid=true and isDel=false and ((verifyStatus !=? and verifyStatus !=?) or verifyStatus is null)";
		List<Object> param=new ArrayList<Object>();
		VerifyStatus unverified=VerifyStatus.UNVERIFIED;
		param.add(unverified);
		VerifyStatus fail=VerifyStatus.FAIL;
		param.add(fail);
		
		if(Constants.CONSTANTS_OCCASION_COMMUNITY.equalsIgnoreCase(type)){
			hql+=" and govOrg.id=? and (occasion=? or occasion is null)";
			param.add(govOrgId);
			param.add(type);
		}else if(Constants.CONSTANTS_OCCASION_SQUARE.equalsIgnoreCase(type)){
			hql+=" and occasion=?";
			param.add(type);
		}
		
		if(!Constants.CONSTANTS_ALL.equalsIgnoreCase(code)){
			Category category=this.getCategory(code);
			hql+=" and category.id=?";
			param.add(category.getId());
		}
		
		hql+=" order by (case when topStartDate < ? and topEndDate > ? then 1 else 0 end) desc, createDate desc";
		param.add(new Date());
		param.add(new Date());
		list=activityDao.find(hql, request.getPage(), request.getRows(), param);
		
		for(Activity activity:list){
			HttpActivity httpActivity=new HttpActivity();
			BeanUtils.copyProperties(activity, httpActivity);
			String categoryType=activity.getCategory().getCode();
			this.copyActivityToHttpActivity(httpActivity, activity, categoryType,null);
			Date now =new Date();
			if(activity.isNeedTop()&& now.after(activity.getTopStartDate()) && now.before(activity.getTopEndDate())){
				httpActivity.setName("<span style='color:red;'>【置顶】</span>"+httpActivity.getName());
			}
			
			if(Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(code)){
					httpActivity.setGoodsStatus(activity.getVerifyStatus().toString());
					VerifyStatus ve=VerifyStatus.SELLING;
					if(activity.getVerifyStatus().name().toString().equalsIgnoreCase(ve.name().toString())){
						httpActivity.setGoodColor(Constants.CONSTANTS_APPLICATION_STATUS_RED);
					}else{
						httpActivity.setGoodColor(Constants.CONSTANTS_APPLICATION_STATUS_GRAY);
					}
					httpActivityList.add(httpActivity);
			}else{
				if(Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(categoryType)){
					httpActivity.setGoodsStatus(activity.getVerifyStatus().toString());
					VerifyStatus ve=VerifyStatus.SELLING;
					if(activity.getVerifyStatus().name().toString().equalsIgnoreCase(ve.name().toString())){
						httpActivity.setGoodColor(Constants.CONSTANTS_APPLICATION_STATUS_RED);
					}else{
						httpActivity.setGoodColor(Constants.CONSTANTS_APPLICATION_STATUS_GRAY);
					}
				}
				httpActivityList.add(httpActivity);
			}
//			
			httpActivity.setType(categoryType);
			User creator = activity.getCreator();
			if(creator!=null){
				httpActivity.setNickName(creator.getNickName());
			}
			if(creator!=null){
				//小区居民发布的活动显示昵称
				if(StringUtil.isNotNull(creator.getOpenid())){
					httpActivity.setFrontName(creator.getNickName());
				}else if(creator.getGovOrg()!=null){
					httpActivity.setFrontName(creator.getGovOrg().getName().replaceAll("社区", "居委会"));
				}else if(creator.getOrg()!=null){
					httpActivity.setFrontName(creator.getOrg().getName());
				}else{
					if(creator.isVirtual()){
						httpActivity.setFrontName(creator.getNickName());
					}else{
						if(Constants.CONSTANTS_SYS_ADMIN.equalsIgnoreCase(creator.getRole().getRoleKey())){
							httpActivity.setFrontName(Constants.CONSTANTS_SMILE_TEAM);
						}
					}
					
				}
			}
			
			
			List<ActivityComments> comments=activity.getComments();
			if(comments!=null && comments.size()>0){
				httpActivity.setCommentCounts(String.valueOf(comments.size()));
			}else{
				httpActivity.setCommentCounts(String.valueOf(0));
			}
		}
		return httpActivityList;
	}
	
	private Category getCategory(String code){
		//categoryDao
		Category category=new Category();
		String hql="from Category where code=?";
		List<Category> list=categoryDao.find(hql, code);
		if(list.size()>0){
			category=list.get(0);
		}
		return category;
	}
	private Activity getActivityByid(String id) {
		// TODO Auto-generated method stub
		String hql="from Activity where id=?";
		Activity activity=activityDao.get(hql, id);
		return activity;
	}
	@Override
	public HttpActivity getActivity(String id,User user) {
		HttpActivity httpActivity=new HttpActivity();
		Activity activity=this.getActivityByid(id);
		activity.setReadCount(activity.getReadCount()==null?1:activity.getReadCount()+1);
		activityDao.save(activity);
		BeanUtils.copyProperties(activity, httpActivity);
		
		User creator = activity.getCreator();
		if(creator!=null){
			//小区居民发布的活动显示昵称
			if(StringUtil.isNotNull(creator.getOpenid())){
				httpActivity.setFrontName(creator.getNickName());
			}else if(creator.getGovOrg()!=null){
				httpActivity.setFrontName(creator.getGovOrg().getName().replaceAll("社区", "居委会"));
			}else if(creator.getOrg()!=null){
				httpActivity.setFrontName(creator.getOrg().getName());
			}else{
				if(creator.isVirtual()){
					httpActivity.setFrontName(creator.getNickName());
				}else{
					if(Constants.CONSTANTS_SYS_ADMIN.equalsIgnoreCase(creator.getRole().getRoleKey())){
						httpActivity.setFrontName(Constants.CONSTANTS_SMILE_TEAM);
					}
				}
			}
		}
		
		this.copyActivityToHttpActivity(httpActivity, activity, activity.getCategory().getCode(),null);
		
		List<Application> list=activity.getApplications();
		List<HttpApplication> httpUserList=new ArrayList<HttpApplication>();
		VerifyStatus verifyStatus=VerifyStatus.PASS;
		if(list!=null){
			for(Application application:list){
				if(verifyStatus.name().equalsIgnoreCase(application.getStatus().name())){
					HttpApplication httpUser=new HttpApplication();
					List<FieldValue> values=application.getFields();
					for(FieldValue fieldValue:values){
						if(fieldValue.getType().getName().equalsIgnoreCase("姓名")){
							String code = fieldValue.getCode();
							httpUser.setApplyName(code);
						}
					}
					httpUser.setUserLogo(application.getUser().getLogo());
					Date applyDate=application.getApplyDate();
					httpUser.setApplyDate(DateUtil.format(applyDate, "yyyy-MM-dd"));
					httpUserList.add(httpUser);
					if(user.getId().equalsIgnoreCase(application.getUser().getId())){
						httpActivity.setFlag(true);
					}
				}
			}
		}
		httpActivity.setHttpApplications(httpUserList);
		
		List<ActivityComments> comments=activity.getComments();
		List<HttpActivityComments> httpActivityComments=new ArrayList<HttpActivityComments>();
		if(comments!=null){
			for(ActivityComments activityComments:comments){
				HttpActivityComments httpActivityComment=new HttpActivityComments();
				httpActivityComment.setContent(activityComments.getContent());
				httpActivityComment.setCreateDate(DateUtil.format(activityComments.getCreateDate(), "yyyy-MM-dd"));
				HttpUser httpUser=new HttpUser();
				User creater=activityComments.getUser();
				httpUser.setNickName(creater.getNickName());
				httpUser.setImageUrl(creater.getLogo());
				httpActivityComment.setHttpUser(httpUser);
				httpActivityComments.add(httpActivityComment);
			}
			httpActivity.setHttpActivityComments(httpActivityComments);
			
			httpActivity.setCommentCounts(String.valueOf(comments.size()));
		}else{
			httpActivity.setCommentCounts(String.valueOf(0));
		}
		
		Category parent = activity.getCategory().getParent();
		if(parent!=null){
			httpActivity.setCode(parent.getCode());
			httpActivity.setGoodsStatus(activity.getVerifyStatus().name());
			httpActivity.setGoodsType(activity.getCategory().getId());
			httpActivity.setGoodsTypeName(activity.getCategory().getName());
		}
		
		Date now =new Date();
		if(activity.getApplicationStartDate()!=null&& now.before(activity.getApplicationStartDate())){
			httpActivity.setIfBegin(false);
			return httpActivity;
		}else if(activity.getApplicationEndDate()!=null&& now.after(activity.getApplicationEndDate())){
			httpActivity.setIfBegin(false);
			httpActivity.setIfEnd(false);
			return httpActivity;
		}
		
		return httpActivity;
	}
	
	@Override
	public HttpActivity getActivity(String id) {
		HttpActivity httpActivity=new HttpActivity();
		Activity activity=this.getActivityByid(id);
		activity.setReadCount(activity.getReadCount()==null?1:activity.getReadCount()+1);
		activityDao.save(activity);
		BeanUtils.copyProperties(activity, httpActivity);
		
		User creator = activity.getCreator();
		if(creator!=null){
			//小区居民发布的活动显示昵称
			
			if(creator.isVirtual()){
				httpActivity.setFrontName("发布人: "+creator.getNickName());
			}else{
				if(StringUtil.isNotNull(creator.getOpenid())){
					StringBuffer sb=new  StringBuffer();
					sb.append("发布人: "+creator.getNickName());
					if(creator.getGovOrg()!=null){
						sb.append(" 社区： "+creator.getGovOrg().getName());
					}
					httpActivity.setFrontName(sb.toString());
				}else if(creator.getGovOrg()!=null){
					httpActivity.setFrontName("发布人: "+creator.getGovOrg().getName().replaceAll("社区", "居委会"));
				}else if(creator.getOrg()!=null){
					httpActivity.setFrontName("发布人: "+creator.getOrg().getName());
				}else{
					if(Constants.CONSTANTS_SYS_ADMIN.equalsIgnoreCase(creator.getRole().getRoleKey())){
						httpActivity.setFrontName(Constants.CONSTANTS_SMILE_TEAM);
					}
				}
			}
		}
		
		this.copyActivityToHttpActivity(httpActivity, activity, activity.getCategory().getCode(),null);
		
		List<Application> list=activity.getApplications();
		List<HttpApplication> httpUserList=new ArrayList<HttpApplication>();
		VerifyStatus verifyStatus=VerifyStatus.PASS;
		if(list!=null){
			for(Application application:list){
				
				if(verifyStatus.name().equalsIgnoreCase(application.getStatus().name())){
					HttpApplication httpUser=new HttpApplication();
					List<FieldValue> values=application.getFields();
					for(FieldValue fieldValue:values){
						if(fieldValue.getType().getName().equalsIgnoreCase("姓名")){
							String code = fieldValue.getCode();
							httpUser.setApplyName(code);
						}
					}
					httpUser.setUserLogo(application.getUser().getLogo());
					Date applyDate=application.getApplyDate();
					httpUser.setApplyDate(DateUtil.format(applyDate, "yyyy-MM-dd"));
					httpUserList.add(httpUser);
				}
			}
		}
		httpActivity.setHttpApplications(httpUserList);
		
		
		List<ActivityComments> comments=activity.getComments();
		List<HttpActivityComments> httpActivityComments=new ArrayList<HttpActivityComments>();
		if(comments!=null){
			for(ActivityComments activityComments:comments){
				HttpActivityComments httpActivityComment=new HttpActivityComments();
				httpActivityComment.setContent(activityComments.getContent());
				httpActivityComment.setCreateDate(DateUtil.format(activityComments.getCreateDate(), "yyyy-MM-dd"));
				HttpUser httpUser=new HttpUser();
				User creater=activityComments.getUser();
				httpUser.setNickName(creater.getNickName());
				httpUser.setImageUrl(creater.getLogo());
				httpActivityComment.setHttpUser(httpUser);
				httpActivityComments.add(httpActivityComment);
			}
			httpActivity.setHttpActivityComments(httpActivityComments);
			
			httpActivity.setCommentCounts(String.valueOf(comments.size()));
		}else{
			httpActivity.setCommentCounts(String.valueOf(0));
		}
		//有二级菜单的
		
		Category parent = activity.getCategory().getParent();
		if(parent!=null){
			httpActivity.setCode(parent.getCode());
			httpActivity.setGoodsStatus(activity.getVerifyStatus().name());
			httpActivity.setGoodsType(activity.getCategory().getId());
		}
		
		Date now =new Date();
		if(activity.getApplicationStartDate()!=null&& now.before(activity.getApplicationStartDate())){
			httpActivity.setIfBegin(false);
			return httpActivity;
		}else if(activity.getApplicationEndDate()!=null&& now.after(activity.getApplicationEndDate())){
			httpActivity.setIfBegin(false);
			httpActivity.setIfEnd(false);
			return httpActivity;
		}
		
		return httpActivity;
	}
	@Override
	public Json saveApplication(String id,HttpServletRequest request,User user) {
		LOG.info("saveApplication>>>>>>>start");
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		Json j=new Json();
		j.setSuccess(true);
		j.setMsg("报名成功！");
		Activity activity=this.getActivityByid(id);
		List<Application> list=activity.getApplications();
		if(list!=null){
			for(Application application:list){
				LOG.info("application status>>>>>>>"+application.getStatus().name().toString());
				if(application.getUser().getId().equalsIgnoreCase(user.getId())){
					if(application.getStatus().name().equalsIgnoreCase(VerifyStatus.UNVERIFIED.name())){
						j.setMsg("您已报名,等待审核中！");
						LOG.info("application msg>>>>>>>您已报名,等待审核中！");
						j.setSuccess(false);
						return j;
					}else if(application.getStatus().name().equalsIgnoreCase(VerifyStatus.PASS.name())){
						j.setMsg("您已报名！");
						LOG.info("application msg>>>>>>>您已报名！");
						j.setSuccess(false);
						return j;
					}
					
				}
			}
		}
		
		Application application=new Application();
		Enumeration enu = request.getParameterNames();
		while(enu.hasMoreElements())     {   
		    String parName=(String)enu.nextElement();
			if (!"id".equalsIgnoreCase(parName)
					&& !"type".equalsIgnoreCase(parName)
					&& !"openid".equalsIgnoreCase(parName)
					&& !"address".equalsIgnoreCase(parName)
					&& !"headimgurl".equalsIgnoreCase(parName)
					&& !"nickName".equalsIgnoreCase(parName)) {
		    	
		    	
		    	 String value = request.getParameter(parName);
		    	 System.out.println(value);
            	FieldValue fieldValue=new FieldValue();
	            fieldValue.setId(UUID.randomUUID().toString());
	            fieldValue.setCode(value);
	            FieldsType type=new FieldsType();
	            type.setId(parName);
	            fieldValue.setType(type);
	            fieldValue.setApplication(application);
	            application.getFields().add(fieldValue);
            }
		}   
		
   		application.setId(UUID.randomUUID().toString());
   		String msg1="";
   		LOG.info("activity  verify>>>>>>>"+activity.isNeedVerify());
   		if(activity.isNeedVerify()){
   			VerifyStatus verifyStatus=VerifyStatus.UNVERIFIED;
   			application.setStatus(verifyStatus);
   			j.setMsg("已提交，请等待审核");
   			j.setSuccess(true);
   		}else{
   			msg1="您好，您已成功报名了活动";
   			VerifyStatus verifyStatus=VerifyStatus.PASS;
   			application.setStatus(verifyStatus);
   			String msg=this.updateActivity(id,Constants.CONSTANTS_PLUS);
   			LOG.info("msg>>>>>>>"+msg);
   			if(StringUtil.isNotNull(msg)){
   				j.setMsg(msg);
   				j.setSuccess(false);
   				return j;
   			}
   		}
   		application.setUser(user);
   		application.setActivity(activity);
   		application.setApplyDate(DateUtil.format(DateUtil.format(new Date()), "yyyy-MM-dd hh:mm:ss"));
   		applicationDao.save(application);
   		if(!activity.isNeedVerify()){
   			System.out.println("start send message >>>>");
   	   		sendService.SendApplyMessage(activity, user, msg1);
   	   		System.out.println("send message end >>>>");
   		}
   		LOG.info("saveApplication>>>>>>>end");
		return j;
	}
	
	@Override
	/**
	 * 获取报名页面的字段
	 */
	public List<HttpFieldType> getField(String id,User user) {
		Activity activity=this.getActivityByid(id);
		List<FieldsType> fieldTypes=activity.getFieldTypes();
		Collections.sort(fieldTypes, new MyCompartor());
		List<HttpFieldType> httpFieldTypes=new ArrayList<HttpFieldType>();
		for(FieldsType fieldsType:fieldTypes){
			HttpFieldType httpFieldType=new HttpFieldType();
			BeanUtils.copyProperties(fieldsType, httpFieldType);
			if(Constants.CONSTANTS_FIELD_TYPE_NAME.equalsIgnoreCase(fieldsType.getName())){
				httpFieldType.setValue(user.getNickName());
			}else if(Constants.CONSTANTS_FIELD_TYPE_TEL.equalsIgnoreCase(fieldsType.getName())){
				httpFieldType.setValue(user.getTel());
			}else if(Constants.CONSTANTS_FIELD_TYPE_EMAIL.equalsIgnoreCase(fieldsType.getName())){
				
			}
			httpFieldTypes.add(httpFieldType);
		}
		return httpFieldTypes;
	}
	
	class MyCompartor implements Comparator<Object>{
		public int compare(Object obj1, Object obj2) {
			FieldsType fieldsType1=(FieldsType)obj1;
			FieldsType fieldsType2=(FieldsType)obj2;
			return fieldsType1.getSequence().compareTo(fieldsType2.getSequence());
		}
		
	}
	
//	private User getUser(){
//		User user=userRepository.findOne("b1cac085-892d-46b4-bcce-4b89dfbc0d72");
//		return user;
//	}
	@Override
	public void cancelApplication(String id,User user) {
		Activity activity=this.getActivityByid(id);
		List<Application> list=activity.getApplications();
		
		if(list!=null){
			for(Application application:list){
				if(user.getId().equalsIgnoreCase(application.getUser().getId())){
					VerifyStatus verifyStatus=VerifyStatus.PASS;
					if(verifyStatus.name().equalsIgnoreCase(application.getStatus().name())){
						verifyStatus=VerifyStatus.CANCLE;
						application.setStatus(verifyStatus);
						applicationDao.update(application);
						this.updateActivity(id,Constants.CONSTANTS_MINUS);
					}
				}
			}
		}
	}
	
	
	@Override
	public void agree(String id) {
		VerifyStatus verifyStatus=VerifyStatus.PASS;
		Application application=this.geyApplicationByid(id);
		VerifyStatus status=application.getStatus();
		if(status.getName().toString().equalsIgnoreCase(verifyStatus.getName().toString())){
			return;
		}
		String msg=this.updateActivity(application.getActivity().getId(), Constants.CONSTANTS_PLUS);
		if(!StringUtil.isNotNull(msg)){
			String hql="update Application set status='"+verifyStatus.name()+"' where id='"+id+"'";
			applicationDao.executeHql(hql);
			msg="审核通过！";
			String msg1="您好，您已成功报名了活动";
			System.out.println("start send message >>>>");
			Activity activity=application.getActivity();
			User user=application.getUser();
	   		sendService.SendApplyMessage(activity, user, msg1);
	   		System.out.println("send message end >>>>");
		}
		
	}
	
	@Override
	public void reject(String id) {
		VerifyStatus verifyStatus=VerifyStatus.FAIL;
		Application application=this.geyApplicationByid(id);
		VerifyStatus status=application.getStatus();
		if(status.getName().toString().equalsIgnoreCase(verifyStatus.getName().toString())){
			return;
		}
		String msg=this.updateActivity(application.getActivity().getId(), Constants.CONSTANTS_MINUS);
		if(!StringUtil.isNotNull(msg)){
			String hql="update Application set status='"+verifyStatus.name()+"' where id='"+id+"'";
			applicationDao.executeHql(hql);
			msg="审核未通过！";
			
			String msg1="您好，您的报名未成功";
			System.out.println("start send message >>>>");
			Activity activity=application.getActivity();
			User user=application.getUser();
	   		sendService.SendApplyMessage(activity, user, msg1);
	   		System.out.println("send message end >>>>");
		}
		
	}
	
	private synchronized String updateActivity(String id,String mark){
		String hql="from Activity where id=?";
		Activity activity=activityDao.get(hql, id);
		int quote=activity.getQuota();
		int applyNumber=activity.getApplyNumber();
		String msg="";
		if(Constants.CONSTANTS_PLUS.equalsIgnoreCase(mark) && (quote>applyNumber || quote==0)){
			activity.setApplyNumber(applyNumber+1);
		}else if(Constants.CONSTANTS_MINUS.equalsIgnoreCase(mark) && applyNumber > 0){
			activity.setApplyNumber(applyNumber-1);
			
		}
		
		if(quote == applyNumber && quote!=0){
			msg="已报满！";
		}else{
			activityDao.saveOrUpdate(activity);
		}
		
		return msg;
	}
	
	private Application geyApplicationByid(String id){
		return applicationDao.get(Application.class, id);
	}
	@Override
		public List<HttpCategory> getAllCategorys(String type) {
			
			List<HttpCategory> returnList = new ArrayList<HttpCategory>();
			List<Category> workList = new ArrayList<Category>();
			Map<Category, HttpCategory> workMap = new HashMap<Category, HttpCategory>();
			
			String hql="from Category order by seq asc";
			List<Category> categoryList = categoryDao.find(hql);
			
			for (Iterator<Category> iter = categoryList.iterator(); iter.hasNext();) {
				Category category = iter.next();
				
				
				if (category.getParent() == null) {
					workList.add(category);
					HttpCategory httpCategory = new HttpCategory();
					httpCategory.setId(category.getId());
					String name = category.getName();
					
					httpCategory.setLogo(category.getLogo());
					httpCategory.setCode(category.getCode());
	//				BeanUtils.copyProperties(govOrg, HttpgovOrg);
					if(Constants.CONSTANTS_OCCASION_SQUARE.equalsIgnoreCase(type)){
						if(Constants.CONSTANTS_ALL.equalsIgnoreCase(category.getCode()) || Constants.CONSTANTS_NOTICE.equalsIgnoreCase(category.getCode()) || Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(category.getCode())){
							returnList.add(httpCategory);
							if(name.equalsIgnoreCase("通知")){
								name="优惠";
							}
						}
					}else{
						returnList.add(httpCategory);
					}
					httpCategory.setName(name);
					workMap.put(category, httpCategory);
				}
			}
			
			while (workList.size() > 0) {
				Category parentResource = workList.remove(0);
				for (Category r: categoryList) {
					if (r.getParent() == parentResource) {
						HttpCategory httpResource = new HttpCategory();
						httpResource.setId(r.getId());
						httpResource.setName(r.getName());
						httpResource.setCode(r.getCode());
						HttpCategory parent = workMap.get(parentResource);
						parent.getChildren().add(httpResource);
						
					}
				}
				
			}
			
			return returnList;
		}
	@Override
	public void saveorEdit(HttpActivity httpActivity,HttpServletRequest request,User user) {
		Activity activity=new Activity();
		if(StringUtil.isNotNull(httpActivity.getId())){
			activity=this.getActivityByid(httpActivity.getId());
		}else{
			BeanUtils.copyProperties(httpActivity, activity);
			activity.setId(UUID.randomUUID().toString());
			
			
			activity.setCreator(user);
			//非二手商品类型
//			if(!StringUtil.isNotNull(httpActivity.getGoodsType())){
				Category category=this.getCategory(httpActivity.getCode());
				activity.setCategory(category);
//			}
			activity.setCreateDate(DateUtil.format(DateUtil.format(new Date()), "yyyy-MM-dd HH:mm:ss"));
			
			GovOrg gov=new GovOrg();
			gov.setId(user.getGovOrg().getId());
			activity.setGovOrg(gov);
		}
		this.saveForActivity(httpActivity, activity);
		//二手商品状态
		if(StringUtil.isNotNull(httpActivity.getGoodsStatus())){
			for(VerifyStatus verifyStatus:VerifyStatus.values()){
				 System.out.println(verifyStatus+" "+verifyStatus.name());
				 if(verifyStatus.name().equalsIgnoreCase(httpActivity.getGoodsStatus())){
					 activity.setVerifyStatus(verifyStatus); 
					 break;
				 }
			}
		}
		//二手商品类型
//		if(StringUtil.isNotNull(httpActivity.getGoodsType())){
//			Category category=new Category();
//			category.setId(httpActivity.getGoodsType());
//			activity.setCategory(category);
//		}
//		if(StringUtil.isNotNull(path)){
//			activity.setContent("<img src='"+request.getContextPath()+path+"'/><br/>"+activity.getContent());
//		}
		
		activityDao.saveOrUpdate(activity);
	}
	@Override
	public String updateSharedActivity(String id) {
		String hql="from Activity where id=?";
		Activity activity=activityDao.get(hql, id);
		activity.setShardCount(activity.getShardCount()==null?1:activity.getShardCount()+1);
		activityDao.save(activity);
		return "success";
	}
	@Override
	public List<HttpActivityComments> submitComment(
			String activityId,String content,User user) {
		ActivityComments activityComments=new ActivityComments();
		activityComments.setId(UUID.randomUUID().toString());
		activityComments.setContent(content);
		activityComments.setCreateDate(new Date());
		Activity activity=new Activity();
		activity.setId(activityId);
		activityComments.setDetails(activity);
		activityComments.setUser(user);
		activityCommentsDao.save(activityComments);
		
		List<HttpActivityComments> list=new ArrayList<HttpActivityComments>();
		String hql="from ActivityComments where details.id=? order by createDate desc";
		List<ActivityComments> activityCommentList=activityCommentsDao.find(hql, activityId);
		for(ActivityComments activityComment:activityCommentList){
			HttpActivityComments httpActivityComments=new HttpActivityComments();
			HttpUser httpUsers=new HttpUser();
			httpUsers.setNickName(activityComment.getUser().getNickName());
			httpUsers.setImageUrl(activityComment.getUser().getLogo());
			httpActivityComments.setHttpUser(httpUsers);
			httpActivityComments.setContent(activityComment.getContent());
			httpActivityComments.setCreateDate(DateUtil.format(activityComment.getCreateDate(), "yyyy-MM-dd"));
			list.add(httpActivityComments);
		}
		
		return list;
	}
	@Override
	public List<HttpCategory> getCategorysByCode(String code) {
		String hql="from Category where code=? order by code";
		List<Category> categoryList = categoryDao.find(hql, code);
		List<HttpCategory> httpChildren=new ArrayList<HttpCategory>();
		if(categoryList!=null){
			Category category=categoryList.get(0);
			List<Category> children=category.getChildren();
			for(Category child:children){
				HttpCategory httpCategory=new HttpCategory();
				httpCategory.setId(child.getId());
				httpCategory.setName(child.getName());
				httpChildren.add(httpCategory);
			}
		}
		return httpChildren;
	}
	//user=userRepository.findOne("b1cac085-892d-46b4-bcce-4b89dfbc0d72");
}
