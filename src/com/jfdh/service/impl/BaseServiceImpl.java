package com.jfdh.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jfdh.httpmodel.BackendUserDtail;
import com.jfdh.httpmodel.HttpActivity;
import com.jfdh.model.Activity;
import com.jfdh.model.FieldsType;
import com.jfdh.model.User;
import com.jfdh.model.VerifyStatus;
import com.jfdh.repository.UserRepository;
import com.jfdh.service.IBaseService;
import com.jfdh.util.Constants;
import com.jfdh.util.DateUtil;
import com.jfdh.util.StringUtil;




/**
 * 基础Service,所有ServiceImpl需要extends此Service来获得默认事务的控制
 * 
 * @author 
 * 
 */
@Service("baseService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class BaseServiceImpl implements IBaseService {

	protected void copyActivityToHttpActivity(HttpActivity httpActivity,Activity activity,String type,String roleKey){
		if(StringUtil.isNotNull(activity.getImage())){
			httpActivity.setImgUrl(activity.getImage());
		}
		Long readCount = activity.getReadCount()==null ? 0 : activity.getReadCount();
		Long shardCount = activity.getShardCount()==null ? 0:activity.getShardCount();
		httpActivity.setReadCounts(String.valueOf(readCount));
		
		httpActivity.setShardCounts(String.valueOf(shardCount));
		
		if(activity.getGovOrg() != null){
			httpActivity.setGovName(activity.getGovOrg().getName());
			if(activity.getGovOrg().getParent()!=null){
				httpActivity.setGovParentName(activity.getGovOrg().getParent().getName());
			}
		}
		httpActivity.setCreateDates(DateUtil.format(activity.getCreateDate(), "yyyy-MM-dd"));
		httpActivity.setCode(activity.getCategory().getCode());
		if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(type) || Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(type)){
			Date applicationStartDate=activity.getApplicationStartDate();
			Date applicationEndDate=activity.getApplicationEndDate();
			httpActivity.setApplicationStartDates(DateUtil.format(applicationStartDate, "yyyy-MM-dd"));
			httpActivity.setApplicationEndDates(DateUtil.format(applicationEndDate, "yyyy-MM-dd"));
			int quote=activity.getQuota();
			int num=activity.getApplyNumber();
			Date now =new Date();
			if(now.after(applicationStartDate) && now.before(applicationEndDate)){
				httpActivity.setApplicationStatus(Constants.CONSTANTS_APPLICATION_STATUS_BEGIN);
				httpActivity.setApplicationColor(Constants.CONSTANTS_APPLICATION_STATUS_RED);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				   long l=applicationEndDate.getTime()-now.getTime();
				   long day=l/(24*60*60*1000);
				   long hour=(l/(60*60*1000)-day*24);
//				   long min=((l/(60*1000))-day*24*60-hour*60);
				   
				   httpActivity.setUneatenTime(day+"天"+hour+"小时");
				   if(httpActivity.getQuota()>0){
					   httpActivity.setUneatenNumber(httpActivity.getQuota()-httpActivity.getApplyNumber()+"名");
				   }else{
					   httpActivity.setUneatenNumber("不限定人数");
				   }
			}else if(now.before(applicationStartDate)){
				httpActivity.setApplicationStatus(Constants.CONSTANTS_APPLICATION_STATUS_NOT_BEGIN);
				httpActivity.setApplicationColor(Constants.CONSTANTS_APPLICATION_STATUS_GREEN);
			}else if(now.after(applicationEndDate)){
				httpActivity.setApplicationStatus(Constants.CONSTANTS_APPLICATION_STATUS_CLOSED);
				httpActivity.setApplicationColor(Constants.CONSTANTS_APPLICATION_STATUS_GRAY);
			}else if(quote-num<=0){
				httpActivity.setApplicationStatus(Constants.CONSTANTS_APPLICATION_STATUS_CLOSED);
				httpActivity.setApplicationColor(Constants.CONSTANTS_APPLICATION_STATUS_FILLED);
			}
		}
		
		if(!activity.isValid()){
			httpActivity.setApplicationStatus(Constants.CONSTANTS_APPLICATION_STATUS_UNSAVED);
		}else{
			if(Constants.CONSTANTS_NOTICE.equalsIgnoreCase(type)){
				httpActivity.setApplicationStatus(Constants.CONSTANTS_APPLICATION_STATUS_SAVED);
			}else if(Constants.CONSTANTS_ORG_ADMIN.equalsIgnoreCase(roleKey) || Constants.CONSTANTS_ORG_USER.equalsIgnoreCase(roleKey)){
				if(activity.getVerifyStatus().toString().equals(VerifyStatus.UNVERIFIED.toString())){
					httpActivity.setApplicationStatus("待审核");
				}
			}
		}
		
		if(activity.getFieldTypes()!=null && activity.getFieldTypes().size()>0){
			List<FieldsType> list=activity.getFieldTypes();
			String[] fieldType=new String[list.size()]; 
			for(int i=0;i<list.size();i++){
				fieldType[i]=list.get(i).getId();
			}
			httpActivity.setFiledType(fieldType);
		}
	}
	
	
	protected void saveForActivity(HttpActivity httpActivity,Activity activity){
		activity.setContent(httpActivity.getContent());
		activity.setName(httpActivity.getName());
		activity.setRemark(httpActivity.getRemark());
		activity.setNeedUrl(httpActivity.isNeedUrl());
		activity.setSponsor(httpActivity.getSponsor());
		activity.setImage(httpActivity.getImgUrl());
		String url = httpActivity.getUrl();
		if(StringUtil.isNotNull(url)){
			if(url.startsWith("http")){
				activity.setUrl(url);
			}else{
				activity.setUrl("http://"+url);
			}
		}
		
		
		String code=httpActivity.getCode();
		if(Constants.CONSTANTS_NOTICE.equalsIgnoreCase(code)){
			activity.setLogo(Constants.CONSTANTS_PICTURE_NOTICE);
		}else if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code)){
			activity.setLogo(Constants.CONSTANTS_PICTURE_ACTIVITY);
		}else if(Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code)){
			activity.setLogo(Constants.CONSTANTS_PICTURE_RECRUITMENT);
		}else if(Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(code)){
			activity.setLogo(Constants.CONSTANTS_PICTURE_ASSISTANT);
		}else if(Constants.CONSTANTS_COOPERATION.equalsIgnoreCase(code)){
			activity.setLogo(Constants.CONSTANTS_PICTURE_COOPERATION);
		}
		activity.setOccasion(httpActivity.getOccasion());
		activity.setValid(httpActivity.getValid());
		if(Constants.CONSTANTS_ACTIVITY.equalsIgnoreCase(code) || Constants.CONSTANTS_RECRUITMENT.equalsIgnoreCase(code)){
			if(httpActivity.getScore() != 0){
				activity.setScore(httpActivity.getScore());
			}
			activity.setNeedVerify(httpActivity.getNeedVerify());
			activity.setQuota(httpActivity.getQuota());
			activity.setApplicationStartDate(DateUtil.format(httpActivity.getApplicationStartDates(), "yyyy-MM-dd"));
			activity.setApplicationEndDate(DateUtil.format(httpActivity.getApplicationEndDates()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			if(StringUtil.isNotNull(httpActivity.getActivtyStartDates())){
				activity.setActivtyStartDate(DateUtil.format(httpActivity.getActivtyStartDates(), "yyyy-MM-dd"));
				activity.setActivtyStartTimes(httpActivity.getActivtyStartTimes());
			}
			if(StringUtil.isNotNull(httpActivity.getActivityEndDates())){
				activity.setActivityEndDate(DateUtil.format(httpActivity.getActivityEndDates(), "yyyy-MM-dd"));
			}
			
			String[] filedType= httpActivity.getFiledType();
			if(filedType!=null && filedType.length>0){
				
				for(int i=0;i<filedType.length;i++){
					FieldsType fieldsType=new FieldsType();
					fieldsType.setId(filedType[i]);
					activity.getFieldTypes().add(fieldsType);
				}
				
			}
		}
		
		activity.setNeedTop(httpActivity.getNeedTop());
		if(StringUtil.isNotNull(httpActivity.getTopStartDates())){
			activity.setTopStartDate(DateUtil.format(httpActivity.getTopStartDates(), "yyyy-MM-dd"));
		}
		
		if(StringUtil.isNotNull(httpActivity.getTopEndDates())){
			activity.setTopEndDate(DateUtil.format(httpActivity.getTopEndDates(), "yyyy-MM-dd"));
		}
	}
	
	protected User getUser(UserRepository userRepository) {
		BackendUserDtail userDetails = (BackendUserDtail) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		User user=userRepository.findOne(userDetails.getId());
		return user;
	}
	
}