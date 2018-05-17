package com.jfdh.service.impl.weichat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jfdh.controller.weichat.util.DownLoadImageUtil;
import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.WeiChartHttpUser;
import com.jfdh.model.Activity;
import com.jfdh.model.Application;
import com.jfdh.model.GovOrg;
import com.jfdh.model.ScoreExchangeRecord;
import com.jfdh.model.User;
import com.jfdh.repository.ActivityRepository;
import com.jfdh.repository.ApplicationRepository;
import com.jfdh.repository.ScoreExchangeRecordRepository;
import com.jfdh.repository.UserRepository;
import com.jfdh.service.weichat.WeichatUserService;
import com.jfdh.util.FilePathHelper;
import com.jfdh.util.JpaTransactional;
import com.jfdh.util.StringUtil;
@Service
public class WeichatUserServiceImpl implements WeichatUserService{
	@Value("#{configProperties['fileUploadDisk']}")
	private String fileUploadDisk;

	@Value("#{configProperties['logoPath']}")
	private String logoPath;
	@Value("#{configProperties['fileUploadPath']}")
	private String fileUploadPath;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ScoreExchangeRecordRepository scoreExchangeRecordRepository;
	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private ActivityRepository activityRepository;
	@Override
	public User findById(String id) {
		return userRepository.findOne(id);
	}
	@Override
	@JpaTransactional
	public void updateUserInfo(WeiChartHttpUser httpUser,String userId) {
		User user=userRepository.findOne(userId);
		BeanUtils.copyProperties(httpUser, user,"logo","id","address","openid");
		if(StringUtils.isNotEmpty(httpUser.getLogo())){
			user.setLogo(httpUser.getLogo());
		}
		userRepository.save(user);
	}
	@Override
	public ScoreExchangeRecord findTop1RecordByUserId(String userId) {
		return scoreExchangeRecordRepository.findTop1ByExchanger_idAndStatusFalseAndExpiryGreaterThan(userId,new Date());
	}
	@Override
	public Application findTop1ApplicationByUserId(String userId) {
		return applicationRepository.findTop1ByUser_IdOrderByApplyDateDesc(userId);
	}
	@Override
	public Page<Application> findAllMyApplication(DataRequest request,
			String userId) {
		Sort sort = new Sort(Sort.Direction.DESC, "applyDate");
		Page<Application> pagingData = applicationRepository.findByUser_Id(userId,new PageRequest(request.getPage() - 1, request
				.getRows(),sort));
		return pagingData;
	}
	@Override
	public User findByOpenid(String openid) {
		return userRepository.findByOpenid(openid);
	}
	@JpaTransactional
	public Page<Activity> findAllMyActivity(DataRequest request, String userId) {
		Sort sort = new Sort(Sort.Direction.DESC, "createDate");
		Page<Activity> pagingData = activityRepository.findByCreator_IdAndCategory_IdNotIn(userId,"01",new PageRequest(request.getPage() - 1, request
				.getRows(),sort));
		List<Activity> list=pagingData.getContent();
		for(Activity activity:list){
			activity.getComments().size();
		}
		return pagingData;
	}
	@Override
	@JpaTransactional
	public User saveUserInfo(WeiChartHttpUser httpUser) {
		User user=new User();
		BeanUtils.copyProperties(httpUser, user,"logo");
		Date date=new Date();
		user.setCreateDate(date);
		System.out.println("httpUser.getType()"+httpUser.getMode());
		if(!"绑定帐号".equalsIgnoreCase(httpUser.getMode())){
			
			user.setNickName(httpUser.getNickName());
			user.setTel("");
			user.setOpenid(httpUser.getOpenid());
			user.setId(UUID.randomUUID().toString());
		}else{
			GovOrg govOrg=new GovOrg();
			govOrg.setId(httpUser.getGovOrgId());
			user.setGovOrg(govOrg);
			if(StringUtils.isNotEmpty(httpUser.getHeadimgurl())){
				String fileName = System.currentTimeMillis()+".jpg";
				String path=FilePathHelper.getPath(fileUploadDisk + fileUploadPath
						+ logoPath);
				DownLoadImageUtil.downLoad(httpUser.getHeadimgurl(), path,fileName);
				user.setLogo(FilePathHelper.getPath(fileUploadPath
						+ logoPath)+"/"+fileName);
			}
			if(!StringUtil.isNotNull(httpUser.getId())){
				user.setId(UUID.randomUUID().toString());
			}
			user.setBundlingDate(date);
		}
		
		return userRepository.save(user);
	}
	@Override
	public User findByUserNameAndPassWord(String userName, String password) {
		return userRepository.findByUserNameAndPassword(userName, password);
	}
	
}
