package com.jfdh.validate;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jfdh.httpmodel.HttpActivity;
import com.jfdh.util.Constants;
import com.jfdh.util.StringUtil;

public class WeichatActivityValidate implements Validator {


	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz.equals(HttpActivity.class);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		HttpActivity httpActivity=(HttpActivity) arg0;
		
		if(Constants.CONSTANTS_ACTIVITY.equals(httpActivity.getCode())){
			ValidationUtils.rejectIfEmpty(errors, "name", "user.username.required", "活动名不能为空!");
			ValidationUtils.rejectIfEmpty(errors, "content", "user.content.required", "活动内容不能为空!");
			ValidationUtils.rejectIfEmpty(errors, "applicationStartDates", "user.applicationStartDates.required", "活动报名时间不能为空!");
			
			ValidationUtils.rejectIfEmpty(errors, "applicationEndDates", "user.applicationEndDates.required", "报名截止时间不能为空!");
			
			ValidationUtils.rejectIfEmpty(errors, "filedType", "user.filedType.required", "报名数据不能为空!");
			
			
		}
		
		if(Constants.CONSTANTS_RECRUITMENT.equals(httpActivity.getCode())){
			ValidationUtils.rejectIfEmpty(errors, "name", "user.username.required", "招募名不能为空!");
			ValidationUtils.rejectIfEmpty(errors, "content", "user.content.required", "招募内容不能为空!");
			
			ValidationUtils.rejectIfEmpty(errors, "applicationStartDates", "user.applicationStartDates.required", "招募报名时间不能为空!");
			
			ValidationUtils.rejectIfEmpty(errors, "applicationEndDates", "user.applicationEndDates.required", "报名截止时间不能为空!");
			
			ValidationUtils.rejectIfEmpty(errors, "filedType", "user.filedType.required", "报名数据不能为空!");
		}
		
		if(Constants.CONSTANTS_ASSISTANT.equals(httpActivity.getCode())){
			ValidationUtils.rejectIfEmpty(errors, "name", "user.username.required", "商品名称不能为空!");
			ValidationUtils.rejectIfEmpty(errors, "content", "user.content.required", "商品介绍不能为空!");
			
			ValidationUtils.rejectIfEmpty(errors, "goodsStatus", "user.goodsStatus.required", "商品状态不能为空!");
			
//			ValidationUtils.rejectIfEmpty(errors, "goodsType", "user.goodsType.required", "商品类型不能为空!");
		}
		
		if(Constants.CONSTANTS_COOPERATION.equals(httpActivity.getCode())){
			ValidationUtils.rejectIfEmpty(errors, "name", "user.username.required", "互助名不能为空!");
			ValidationUtils.rejectIfEmpty(errors, "content", "user.content.required", "互助内容不能为空!");
		}
		
	}

}
