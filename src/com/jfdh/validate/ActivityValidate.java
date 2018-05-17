package com.jfdh.validate;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jfdh.httpmodel.HttpActivity;
import com.jfdh.util.Constants;
import com.jfdh.util.StringUtil;

public class ActivityValidate implements Validator {


	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz.equals(HttpActivity.class);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		HttpActivity httpActivity=(HttpActivity) arg0;
		
		if(Constants.CONSTANTS_NOTICE.equals(httpActivity.getCode())){
			ValidationUtils.rejectIfEmpty(errors, "name", "user.username.required", "公告名不能为空!");
			if(!httpActivity.isNeedUrl()){
				ValidationUtils.rejectIfEmpty(errors, "content", "user.content.required", "公告内容不能为空!");
			}
			
			if(!StringUtil.isNotNull(httpActivity.getId())){
				if("1".equalsIgnoreCase(httpActivity.getOccasion())){
					ValidationUtils.rejectIfEmpty(errors, "govOrgIds", "user.govOrgIds.required", "请选择居委会!");
				}
			}
		}
		
		if(Constants.CONSTANTS_ACTIVITY.equals(httpActivity.getCode())){
			ValidationUtils.rejectIfEmpty(errors, "name", "user.username.required", "活动名不能为空!");
			
			if(Constants.CONSTANTS_OCCASION_COMMUNITY.equalsIgnoreCase(httpActivity.getOccasion())){
				if(!StringUtil.isNotNull(httpActivity.getId())){
					ValidationUtils.rejectIfEmpty(errors, "govOrgIds", "user.govOrgIds.required", "请选择居委会!");
				}
			}
			if(!httpActivity.isNeedUrl()){
				ValidationUtils.rejectIfEmpty(errors, "content", "user.content.required", "活动内容不能为空!");
				
				ValidationUtils.rejectIfEmpty(errors, "filedType", "user.filedType.required", "报名数据不能为空!");
			}
			ValidationUtils.rejectIfEmpty(errors, "occasion", "user.occasion.required", "发布场合不能为空!");
			
			ValidationUtils.rejectIfEmpty(errors, "applicationStartDates", "user.applicationStartDates.required", "活动报名时间不能为空!");
			
			ValidationUtils.rejectIfEmpty(errors, "applicationEndDates", "user.applicationEndDates.required", "报名截止时间不能为空!");
			
			//ValidationUtils.rejectIfEmpty(errors, "activtyStartDates", "user.activtyStartDates.required", "活动开始时间不能为空!");
			
			//ValidationUtils.rejectIfEmpty(errors, "activityEndDates", "user.activityEndDates.required", "活动截止时间不能为空!");
			
			
			
			
		}
		
		if(Constants.CONSTANTS_RECRUITMENT.equals(httpActivity.getCode())){
			ValidationUtils.rejectIfEmpty(errors, "name", "user.username.required", "招募名不能为空!");
			
			if(Constants.CONSTANTS_OCCASION_COMMUNITY.equalsIgnoreCase(httpActivity.getOccasion())){
				if(!StringUtil.isNotNull(httpActivity.getId())){
					ValidationUtils.rejectIfEmpty(errors, "govOrgIds", "user.govOrgIds.required", "请选择居委会!");
				}
			}
			
			if(!httpActivity.isNeedUrl()){
				ValidationUtils.rejectIfEmpty(errors, "content", "user.content.required", "招募内容不能为空!");
				
				ValidationUtils.rejectIfEmpty(errors, "filedType", "user.filedType.required", "报名数据不能为空!");
			}
			
			ValidationUtils.rejectIfEmpty(errors, "occasion", "user.occasion.required", "发布场合不能为空!");
			
			ValidationUtils.rejectIfEmpty(errors, "applicationStartDates", "user.applicationStartDates.required", "招募报名时间不能为空!");
			
			ValidationUtils.rejectIfEmpty(errors, "applicationEndDates", "user.applicationEndDates.required", "报名截止时间不能为空!");
			
			//ValidationUtils.rejectIfEmpty(errors, "activtyStartDates", "user.activtyStartDates.required", "招募开始时间不能为空!");
			
			//ValidationUtils.rejectIfEmpty(errors, "activityEndDates", "user.activityEndDates.required", "招募截止时间不能为空!");
			
		}
		
	}

}
