package com.jfdh.service.impl;


import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.jfdh.dao.IBaseDao;
import com.jfdh.httpmodel.BackendUserDtail;
import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.DataResponse;
import com.jfdh.httpmodel.HttpActivity;
import com.jfdh.httpmodel.HttpActivityComments;
import com.jfdh.httpmodel.HttpApplication;
import com.jfdh.httpmodel.HttpData;
import com.jfdh.httpmodel.HttpFieldValue;
import com.jfdh.httpmodel.HttpMenu;
import com.jfdh.httpmodel.HttpSearch;
import com.jfdh.httpmodel.HttpUser;
import com.jfdh.httpmodel.Json;
import com.jfdh.model.Activity;
import com.jfdh.model.ActivityComments;
import com.jfdh.model.Application;
import com.jfdh.model.Category;
import com.jfdh.model.FieldValue;
import com.jfdh.model.FieldsType;
import com.jfdh.model.GovOrg;
import com.jfdh.model.Org;
import com.jfdh.model.Resource;
import com.jfdh.model.ScoreBalanceRecord;
import com.jfdh.model.ScorePool;
import com.jfdh.model.User;
import com.jfdh.model.VerifyStatus;
import com.jfdh.repository.ApplicationRepository;
import com.jfdh.repository.ScoreBalanceRecordRepository;
import com.jfdh.repository.ScorePoolRepository;
import com.jfdh.repository.UserRepository;
import com.jfdh.service.IActivityService;
import com.jfdh.service.ISendMessageService;
import com.jfdh.util.Constants;
import com.jfdh.util.DateUtil;
import com.jfdh.util.JpaTransactional;
import com.jfdh.util.StringUtil;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.sdicons.json.parser.JSONParser;

/**
 * 资源Service实现�?
 * 
 * @author 
 * 
 */
@Service("activityService")
public class ActivityServiceImpl extends BaseServiceImpl implements IActivityService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private ScorePoolRepository scorePoolRepository;
	@Autowired
	private ScoreBalanceRecordRepository scoreBalanceRecordRepository;
	private IBaseDao<Activity> activityDao;
	private IBaseDao<Category> categoryDao;
	private IBaseDao<Application> applicationDao;
	
	private IBaseDao<ActivityComments> activityCommentsDao;
	
	@Autowired
	private ISendMessageService sendService;

	public IBaseDao<ActivityComments> getActivityCommentsDao() {
		return activityCommentsDao;
	}
	@Autowired
	public void setActivityCommentsDao(
			IBaseDao<ActivityComments> activityCommentsDao) {
		this.activityCommentsDao = activityCommentsDao;
	}
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
	@JpaTransactional
	public List<HttpMenu> getMenu(String role,HttpServletRequest request) {
		User user = getUser(userRepository);
		List<Resource> resources= user.getRole().getResources();
		
		List<HttpMenu> returnList = new ArrayList<HttpMenu>();
		for(Resource resource:resources){
			if(resource.getParent()==null&&"1".equals(resource.getType())){
				HttpMenu parent=new HttpMenu();
				parent.setName(resource.getName());
				parent.setLogo(resource.getLogo());
				parent.setUrl(resource.getResUrl());
				parent.setLogo(resource.getLogo());
				
				List<HttpMenu> children = new ArrayList<HttpMenu>();
				for(Resource child:resources){
					if(child.getParent()!=null&&child.getParent().getId().equals(resource.getId())){
						HttpMenu temp=new HttpMenu();
						temp.setName(child.getName());
						temp.setLogo(child.getLogo());
						temp.setUrl(child.getResUrl());
						temp.setLogo(child.getLogo());
						children.add(temp);
					}
				}
				parent.setMenusList(children);
				returnList.add(parent);
			}
		}
		
		return returnList;
	}
	
	public DataResponse dataGrid(DataRequest dr,HttpSession session,String type,HttpSearch httpSearch){
		DataResponse j=new DataResponse();
		Category category=this.getCategory(type);
		User user=this.getUser(userRepository);
		String roleKey=user.getRole().getRoleKey();
		List<Object> param=new ArrayList<Object>();
		
		String hql="from Activity where category.id =? and isDel=false ";
		param.add(category.getId());
		
		
		if(httpSearch!=null){
			List<HttpData> list=httpSearch.getRules();
			HttpData httpData=list.get(0);
			hql+=" and "+httpData.getField()+" like ?";
			param.add("%"+httpData.getData()+"%");
		}
		
		if(Constants.CONSTANTS_COMMUNITY_ADMIN.equalsIgnoreCase(roleKey) || Constants.CONSTANTS_COMMUNITY_USER.equalsIgnoreCase(roleKey)){
			hql+=" and govOrg.id=? and (valid=true or (valid=false and creator.govOrg.id=?)) and ((verifyStatus !=? and verifyStatus !=?)  or verifyStatus is null)";
			param.add(user.getGovOrg().getId());
			param.add(user.getGovOrg().getId());
			VerifyStatus unverified=VerifyStatus.UNVERIFIED;
			param.add(unverified);
			VerifyStatus fail=VerifyStatus.FAIL;
			param.add(fail);
		}else if(Constants.CONSTANTS_ORG_ADMIN.equalsIgnoreCase(roleKey) || Constants.CONSTANTS_ORG_USER.equalsIgnoreCase(roleKey)){
			hql+=" and org.id=? and (valid=true or (valid=false and creator.org.id=?))";
			param.add(user.getOrg().getId());
			param.add(user.getOrg().getId());
		}else{
			hql+=" and ((verifyStatus !=? and verifyStatus !=?)  or verifyStatus is null)";
			VerifyStatus unverified=VerifyStatus.UNVERIFIED;
			param.add(unverified);
			VerifyStatus fail=VerifyStatus.FAIL;
			param.add(fail);
		}
		
		
		if(StringUtil.isNotNull(dr.getSidx())){
			hql+=" order by "+dr.getSidx()+" "+dr.getSord();
		}else{
			hql+=" order by createDate desc";
		}
		
		String totalHql=" select count(*) "+hql;
		//总记录数
		int total=activityDao.count(totalHql,param).intValue();
		
		int totalPages;//总页数  
        int limit = dr.getRows() <= 0 ? 10 : dr.getRows();//每页显示数量  
        int page = dr.getPage() <= 0 ? 1 : dr.getPage();//当前显示页码 
        
        totalPages = total / limit;  
        if (total % limit != 0) {  
            totalPages++;  
        } 
        j.setRecords(total);
        j.setTotal(totalPages);
        j.setPage(page);
		
//		List<Activity> list=activityDao.findByDataRequest(hql, dr.getPage(), dr.getRows(), category.getId());
		List<Activity> list=activityDao.find(hql, dr.getPage(), dr.getRows(), param);
		List<HttpActivity> httpActivityList=new ArrayList<HttpActivity>();
		int i=1;
		for(Activity activity:list){
			HttpActivity httpActivity=new HttpActivity();
			BeanUtils.copyProperties(activity, httpActivity);
			
			httpActivity.setSeq(String.valueOf(i));
			if(Constants.CONSTANTS_ASSISTANT.equalsIgnoreCase(activity.getCategory().getCode())){
				httpActivity.setGoodsStatus(activity.getVerifyStatus().toString());
			}
			this.copyActivityToHttpActivity(httpActivity, activity, type,roleKey);
			httpActivityList.add(httpActivity);
			i++;
		}
		j.setRows(httpActivityList);
		return j;
	}
	
	
	
	public String saveorEditActivity(HttpActivity httpActivity,boolean needSendMessage){
		Activity activity=null;
		User user=this.getUser(userRepository);
		String roleKey=user.getRole().getRoleKey();
		if(StringUtil.isNotNull(httpActivity.getId())){
			activity=this.getActivityByid(httpActivity.getId());
			activity.getFieldTypes().clear();
			this.saveForActivity(httpActivity, activity);
			if(!httpActivity.getNeedTop()){
				activity.setTopStartDate(null);
				activity.setTopEndDate(null);
			}
			activityDao.update(activity);
		}else{
			String code = httpActivity.getCode();
			String ids=httpActivity.getGovOrgIds();
			ScorePool from=null;
			boolean flag=false;
			if(code.equals("04")&&httpActivity.getScore()>0){
				if(StringUtil.isNotNull(ids)){
					String[] id=ids.split(",");
					from=user.getGovOrg()==null?user.getOrg().getScorePool():user.getGovOrg().getScorePool();
					if(from==null){
						return "您的积分不足!!";
					}
					long result=from.getBalance()-httpActivity.getQuota()*httpActivity.getScore()*id.length;
					if(result<0){
						return "您的积分不足!!";
					}
					from.setBalance(result);
					from.setDispensedTotalScore(from.getDispensedTotalScore()+httpActivity.getQuota()*httpActivity.getScore()*id.length);
					from.setUpdateDate(new Date());
					scorePoolRepository.save(from);
					flag=true;
				}
			}
			
			if(StringUtil.isNotNull(ids)){
				String[] id=ids.split(",");
				for(int i=0; i<id.length;i++){
					activity=new Activity();
//					BeanUtils.copyProperties(httpActivity, activity);
					GovOrg gov=new GovOrg();
					gov.setId(id[i]);
					activity.setGovOrg(gov);
					activity.setId(UUID.randomUUID().toString());
					Category category=this.getCategory(code);
				
					activity.setCategory(category);
					activity.setCreateDate(DateUtil.format(DateUtil.format(new Date()), "yyyy-MM-dd"));
					activity.setCreator(user);
					
					if(Constants.CONSTANTS_ORG_ADMIN.equalsIgnoreCase(roleKey) || Constants.CONSTANTS_ORG_USER.equalsIgnoreCase(roleKey)){
						Org org=new Org();
						org.setId(user.getOrg().getId());
						activity.setOrg(org);
						VerifyStatus verifyStatus=VerifyStatus.UNVERIFIED;
						activity.setVerifyStatus(verifyStatus);
					}
					
					this.saveForActivity(httpActivity, activity);
					if(flag){
						activity.setBanlance(activity.getQuota()*activity.getScore());
					}
					activityDao.save(activity);
					activity=getActivityByid(activity.getId());
					
					if(needSendMessage && httpActivity.getValid()){
						sendService.SendNoticeMessage(activity,id[i],httpActivity.getCode());
					}
					
					if(flag){
						ScorePool to =scorePoolRepository.findByGovOrg_Id(activity.getGovOrg().getId());
						if(null==to){
							to=new ScorePool();
							to.setId(UUID.randomUUID().toString());
							to.setCreateDate(new Date());
//							to.setBalance(activity.getBanlance());
							to.setGovOrg(activity.getGovOrg());
//						}else{
//							to.setBalance(to.getBalance()+activity.getBanlance());
						}
						scorePoolRepository.save(to);
						ScoreBalanceRecord record=new ScoreBalanceRecord();
						record.setId(UUID.randomUUID().toString());
						record.setAmount(activity.getBanlance());
						record.setFrom(from);
						record.setTo(to);
						record.setCreateDate(new Date());
					
						scoreBalanceRecordRepository.save(record);
					}
				}
			}else{
				activity=new Activity();
				activity.setId(UUID.randomUUID().toString());
				Category category=this.getCategory(code);
				activity.setCategory(category);
				activity.setCreateDate(DateUtil.format(DateUtil.format(new Date()), "yyyy-MM-dd"));
				this.saveForActivity(httpActivity, activity);
				activity.setCreator(user);
				if(Constants.CONSTANTS_ORG_ADMIN.equalsIgnoreCase(roleKey) || Constants.CONSTANTS_ORG_USER.equalsIgnoreCase(roleKey)){
					Org org=new Org();
					org.setId(user.getOrg().getId());
					activity.setOrg(org);
					VerifyStatus verifyStatus=VerifyStatus.UNVERIFIED;
					activity.setVerifyStatus(verifyStatus);
				}
				activityDao.save(activity);
			}
			
		}
		return null;
	}
	
	
	private Category getCategory(String code){
		Category category=new Category();
		String hql="from Category where code=?";
		List<Category> list=categoryDao.find(hql, code);
		if(list.size()>0){
			category=list.get(0);
		}
		return category;
	}
	
	@Override
	public Json del(String ids) {
		if(StringUtil.isNotNull(ids)){
			String[] id=ids.split(",");
			for(int i=0;i<id.length;i++){
				Activity activity=getActivityByid(id[i]);
				activity.setDel(true);
				if(activity.getApplyNumber()==0){
					if(activity.getCategory().getCode().equals("04")&&activity.getBanlance()!=0){
						ScorePool from = activity.getGovOrg().getScorePool();
						ScorePool to = activity.getCreator().getGovOrg()==null?activity.getCreator().getOrg().getScorePool():activity.getCreator().getGovOrg().getScorePool();
						ScoreBalanceRecord record=new ScoreBalanceRecord();
						record.setId(UUID.randomUUID().toString());
						record.setAmount(activity.getBanlance());
						record.setFrom(from);
						record.setTo(to);
						record.setCreateDate(new Date());
						to.setBalance(to.getBalance()+activity.getBanlance());
						scorePoolRepository.save(to);
						scoreBalanceRecordRepository.save(record);
						activityDao.update(activity);
					}else{
						activityDao.update(activity);
					}
				}
			}
			
		}
		
		return null;
	}
	
	
	@Override
	public HttpActivity getActivity(String id) {
		// TODO Auto-generated method stub
		Activity activity=this.getActivityByid(id);
		HttpActivity hActivity=new HttpActivity();
		BeanUtils.copyProperties(activity, hActivity);
		if(activity.getGovOrg()!=null){
			hActivity.setGovName(activity.getGovOrg().getName());
			hActivity.setGovOrgIds(activity.getGovOrg().getId());
		}
		if(activity.getApplicationStartDate()!=null){
			hActivity.setApplicationStartDates(DateUtil.format(activity.getApplicationStartDate(), "yyyy-MM-dd"));
		}
		if(activity.getApplicationEndDate()!=null){
			hActivity.setApplicationEndDates(DateUtil.format(activity.getApplicationEndDate(), "yyyy-MM-dd"));
		}
		if(activity.getActivtyStartDate()!=null){
			hActivity.setActivtyStartDates(DateUtil.format(activity.getActivtyStartDate(), "yyyy-MM-dd"));
		}
		if(activity.getActivityEndDate()!=null){
			hActivity.setActivityEndDates(DateUtil.format(activity.getActivityEndDate(), "yyyy-MM-dd"));
		}
		
		if(activity.getTopStartDate()!=null){
			hActivity.setTopStartDates(DateUtil.format(activity.getTopStartDate(), "yyyy-MM-dd"));
		}
		
		if(activity.getTopEndDate()!=null){
			hActivity.setTopEndDates(DateUtil.format(activity.getTopEndDate(), "yyyy-MM-dd"));
		}
		
		if(activity.getFieldTypes()!=null && activity.getFieldTypes().size()>0){
			List<FieldsType> list=activity.getFieldTypes();
			String[] fieldType=new String[list.size()]; 
			for(int i=0;i<list.size();i++){
				fieldType[i]=list.get(i).getId();
			}
			hActivity.setFiledType(fieldType);
		}
		return hActivity;
	}
	
	public Activity getActivityByid(String id) {
		// TODO Auto-generated method stub
		String hql="from Activity where id=?";
		Activity activity=activityDao.get(hql, id);
		return activity;
	}
	
	
	
	public DataResponse applyGrid(DataRequest dr,HttpSession session,String type,HttpSearch httpSearch){
		DataResponse j=new DataResponse();
		Category category=this.getCategory(type);
		User user=this.getUser(userRepository);
		String roleKey=user.getRole().getRoleKey();
		List<Object> param=new ArrayList<Object>();
		param.add(category.getId());
		String hql="from Application a where a.activity.category.id =? and a.activity.isDel=false ";
		
		if(Constants.CONSTANTS_COMMUNITY_ADMIN.equalsIgnoreCase(roleKey) || Constants.CONSTANTS_COMMUNITY_USER.equalsIgnoreCase(roleKey)){
			hql+=" and activity.govOrg.id=?";
			param.add(user.getGovOrg().getId());
		}else if(Constants.CONSTANTS_ORG_ADMIN.equalsIgnoreCase(roleKey) || Constants.CONSTANTS_ORG_USER.equalsIgnoreCase(roleKey)){
			hql+=" and activity.org.id=?";
			param.add(user.getOrg().getId());
		}
		
		
		if(httpSearch!=null){
			List<HttpData> list=httpSearch.getRules();
			HttpData httpData=list.get(0);
			if("applyName".equalsIgnoreCase(httpData.getField()) || "applyTel".equalsIgnoreCase(httpData.getField())){
				hql+=" and a.id in (select f.application.id from FieldValue f where f.code like ?)";
				param.add("%"+httpData.getData()+"%");
			}else{
				hql+=" and "+httpData.getField()+" like ?";
				param.add("%"+httpData.getData()+"%");
			}
		}
		
		if(StringUtil.isNotNull(dr.getSidx())){
			hql+=" order by "+dr.getSidx()+" "+dr.getSord();
		}else{
			hql+=" order by applyDate desc";
		}
		
		String totalHql=" select count(*)"+hql;
		//总记录数
		int total=applicationDao.count(totalHql,param).intValue();
		
		int totalPages;//总页数  
        int limit = dr.getRows() <= 0 ? 10 : dr.getRows();//每页显示数量  
        int page = dr.getPage() <= 0 ? 1 : dr.getPage();//当前显示页码 
        
        totalPages = total / limit;  
        if (total % limit != 0) {  
            totalPages++;  
        } 
        j.setRecords(total);
        j.setTotal(totalPages);
        j.setPage(page);
		
		List<Application> list=applicationDao.find(hql, dr.getPage(), dr.getRows(), param);
		List<HttpApplication> httpApplicationList=new ArrayList<HttpApplication>();
		int i=1;
		for(Application application:list){
			HttpApplication httpApplication=new HttpApplication();
			httpApplication.setId(application.getId());
			httpApplication.setSeq(String.valueOf(i));
			httpApplication.setActivityName(application.getActivity().getName());
			httpApplication.setActivityId(application.getActivity().getId());
			httpApplication.setStatus(application.getStatus().toString());
			httpApplication.setApplyDate(DateUtil.format(application.getApplyDate(), "yyyy-MM-dd"));
			List<FieldValue> values=application.getFields();
			for(FieldValue fieldValue:values){
				if(Constants.CONSTANTS_FIELD_TYPE_NAME.equalsIgnoreCase(fieldValue.getType().getName())){
					httpApplication.setApplyName(fieldValue.getCode());
				}else if(Constants.CONSTANTS_FIELD_TYPE_TEL.equalsIgnoreCase(fieldValue.getType().getName())){
					httpApplication.setApplyTel(fieldValue.getCode());
				}else if(Constants.CONSTANTS_FIELD_TYPE_EMAIL.equalsIgnoreCase(fieldValue.getType().getName())){
					
				}
			}
			httpApplicationList.add(httpApplication);
			i++;
		}
		j.setRows(httpApplicationList);
		return j;
	}
	
	
	public DataResponse pay(DataRequest dr,HttpSession session,String type){
		DataResponse j=new DataResponse();
		Category category=this.getCategory(type);
		User user=this.getUser(userRepository);
		String roleKey=user.getRole().getRoleKey();
		List<Object> param=new ArrayList<Object>();
		param.add(category.getId());
		String hql="from Application where activity.category.id =? and status=?";
		param.add(VerifyStatus.PASS);
		
		if(Constants.CONSTANTS_COMMUNITY_ADMIN.equalsIgnoreCase(roleKey) || Constants.CONSTANTS_COMMUNITY_USER.equalsIgnoreCase(roleKey)){
			hql+=" and activity.govOrg.id=?";
			param.add(user.getGovOrg().getId());
		}else if(Constants.CONSTANTS_ORG_ADMIN.equalsIgnoreCase(roleKey) || Constants.CONSTANTS_ORG_USER.equalsIgnoreCase(roleKey)){
			hql+=" and activity.org.id=?";
			param.add(user.getOrg().getId());
		}
		
		
		if(StringUtil.isNotNull(dr.getSidx())){
			hql+=" order by "+dr.getSidx()+" "+dr.getSord();
		}else{
			hql+=" order by applicationStatus asc,applyDate desc";
		}
		
		String totalHql=" select count(*)"+hql;
		//总记录数
		int total=applicationDao.count(totalHql,param).intValue();
		
		int totalPages;//总页数  
        int limit = dr.getRows() <= 0 ? 10 : dr.getRows();//每页显示数量  
        int page = dr.getPage() <= 0 ? 1 : dr.getPage();//当前显示页码 
        
        totalPages = total / limit;  
        if (total % limit != 0) {  
            totalPages++;  
        } 
        j.setRecords(total);
        j.setTotal(totalPages);
        j.setPage(page);
		
		List<Application> list=applicationDao.find(hql, dr.getPage(), dr.getRows(), param);
		List<HttpApplication> httpApplicationList=new ArrayList<HttpApplication>();
		int i=1;
		for(Application application:list){
			HttpApplication httpApplication=new HttpApplication();
			httpApplication.setId(application.getId());
			httpApplication.setSeq(String.valueOf(i));
			httpApplication.setActivityName(application.getActivity().getName());
			httpApplication.setActivityId(application.getActivity().getId());
			httpApplication.setStatus(application.getApplicationStatus()==null?"":application.getApplicationStatus().toString());
			httpApplication.setApplyDate(DateUtil.format(application.getApplyDate(), "yyyy-MM-dd"));
			List<FieldValue> values=application.getFields();
			for(FieldValue fieldValue:values){
				if(Constants.CONSTANTS_FIELD_TYPE_NAME.equalsIgnoreCase(fieldValue.getType().getName())){
					httpApplication.setApplyName(fieldValue.getCode());
				}else if(Constants.CONSTANTS_FIELD_TYPE_TEL.equalsIgnoreCase(fieldValue.getType().getName())){
					httpApplication.setApplyTel(fieldValue.getCode());
				}else if(Constants.CONSTANTS_FIELD_TYPE_EMAIL.equalsIgnoreCase(fieldValue.getType().getName())){
					
				}
			}
			httpApplicationList.add(httpApplication);
			i++;
		}
		j.setRows(httpApplicationList);
		return j;
	}
	
//	@Override
//	public void agree(String id) {
//		VerifyStatus verifyStatus=VerifyStatus.PASS;
//		String msg=this.updateActivity(id, activityDao, Constants.CONSTANTS_PLUS);
//		if(!StringUtil.isNotNull(msg)){
//			String hql="update Application set status='"+verifyStatus.name()+"' where id='"+id+"'";
//			applicationDao.executeHql(hql);
//			msg="审核通过！";
//		}
//		
//	}
	@Override
	public void reject(String id) {
		VerifyStatus verifyStatus=VerifyStatus.FAIL;
		String hql="update Application set status='"+verifyStatus.name()+"' where id='"+id+"'";
		applicationDao.executeHql(hql);
		
	}
	@Override
	public List<Application> getApplication(String type) {
		Category category=this.getCategory(type);
		String hql="from Application where activity.category.id =? ";
		List<Application> list=applicationDao.find(hql, category.getId());
		return list;
	}
	
	public List<HttpApplication> getHttpApplication(String type,HttpSession session) {
		User user=this.getUser(userRepository);
		String roleKey=user.getRole().getRoleKey();
		Category category=this.getCategory(type);
		HttpSearch httpSearch=null;
		if(session.getAttribute("filters"+user.getId())!=null){
			Object obj= session.getAttribute("filters"+user.getId());
			try {
				String filters=(String) obj;
				JSONParser parser1 = new JSONParser(new StringReader(filters)); 
				 JSONValue jsonValue1 = parser1.nextValue();
				 httpSearch=(com.jfdh.httpmodel.HttpSearch) JSONMapper.toJava(jsonValue1, HttpSearch.class);
			} catch (TokenStreamException | RecognitionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MapperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
		
		String hql="from Application where activity.category.id =? ";
		
		List<Object> param=new ArrayList<Object>();
		param.add(category.getId());
		
		if(Constants.CONSTANTS_COMMUNITY_ADMIN.equalsIgnoreCase(roleKey) || Constants.CONSTANTS_COMMUNITY_USER.equalsIgnoreCase(roleKey)){
			hql+=" and activity.govOrg.id=?";
			param.add(user.getGovOrg().getId());
		}else if(Constants.CONSTANTS_ORG_ADMIN.equalsIgnoreCase(roleKey) || Constants.CONSTANTS_ORG_USER.equalsIgnoreCase(roleKey)){
			hql+=" and activity.org.id=?";
			param.add(user.getOrg().getId());
		}
		
		
		if(httpSearch!=null){
			List<HttpData> list=httpSearch.getRules();
			HttpData httpData=list.get(0);
			if("applyName".equalsIgnoreCase(httpData.getField()) || "applyTel".equalsIgnoreCase(httpData.getField())){
				hql+=" and id in (select f.application.id from FieldValue f where f.code like ?)";
				param.add("%"+httpData.getData()+"%");
			}else{
				hql+=" and "+httpData.getField()+" like ?";
				param.add("%"+httpData.getData()+"%");
			}
		}
		
		
		List<Application> list=applicationDao.find(hql, param);
		List<HttpApplication> httpApplicationList=new ArrayList<HttpApplication>();
		for(Application application:list){
			HttpApplication httpApplication=new HttpApplication();
			httpApplication.setActivityName(application.getActivity().getName());
			httpApplication.setStatus(application.getStatus().toString());
			httpApplication.setApplyDate(DateUtil.format(application.getApplyDate(), "yyyy-MM-dd"));
			List<FieldValue> values=application.getFields();
			for(FieldValue fieldValue:values){
				HttpFieldValue httpFieldvalue=new HttpFieldValue();
				httpFieldvalue.setCode(fieldValue.getCode());
				httpFieldvalue.setTypeName(fieldValue.getType().getName());
				httpApplication.getValues().add(httpFieldvalue);
			}
			httpApplicationList.add(httpApplication);
		}
		
		return httpApplicationList;
	}
	@Override
	public Json payment(String ids) {
		Json j=new Json();
		long totoalScore=0;
		if(StringUtil.isNotNull(ids)){
			
			User user = getUser(userRepository);

			String[] id=ids.split(",");
			for(int i=0;i<id.length;i++ ){
				Application application=this.getApplicationById(id[i]);
				long score=application.getActivity().getScore();
				totoalScore+=score;
				
			}
			ScorePool parent=null;
			if(user.getOrg()!=null){
				parent=user.getOrg().getScorePool();
			}
			if(user.getGovOrg()!=null){
				parent=user.getGovOrg().getScorePool();
			}
			totoalScore=parent.getBalance()-totoalScore;
			if(totoalScore>=0){
				for(int i=0;i<id.length;i++ ){
					Application application=this.getApplicationById(id[i]);
					long score=application.getActivity().getScore();
					//参与者获得积分
					if(application.getUser()!=null){
						ScorePool scorePool=application.getUser().getScorePool();
						scorePool.setAllGetPoints(scorePool.getAllGetPoints()+score);
						score=score+scorePool.getBalance();
						scorePool.setBalance(score);
						scorePoolRepository.save(scorePool);
					}
					
				}
				parent.setBalance(totoalScore);
				scorePoolRepository.save(parent);
				j.setMsg("支付成功！");
			}else{
				j.setMsg("您的积分不足！");
			}
			
		}
		return j;
		
	}
	
	public Application getApplicationById(String id) {
		String hql="from Application where id =? ";
		List<Application> list=applicationDao.find(hql, id);
		return list.get(0);
	}
	
	
	public DataResponse checkGrid(DataRequest dr,HttpSession session,String type){
		DataResponse j=new DataResponse();
		Category category=this.getCategory(type);
		
		User user=this.getUser(userRepository);
		String roleKey=user.getRole().getRoleKey();
		List<Object> param=new ArrayList<Object>();
		String hql="from Activity where valid=true and category.id =? and isDel =false ";
		param.add(category.getId());
		
		if(Constants.CONSTANTS_COMMUNITY_ADMIN.equalsIgnoreCase(roleKey) || Constants.CONSTANTS_COMMUNITY_USER.equalsIgnoreCase(roleKey)){
			hql+=" and govOrg.id=? and org.id is not null ";
			param.add(user.getGovOrg().getId());
		}else if(Constants.CONSTANTS_SYS_ADMIN.equalsIgnoreCase(roleKey)){
			hql+=" and org.id is not null ";
		}
		
		
		if(StringUtil.isNotNull(dr.getSidx())){
			hql+="order by "+dr.getSidx()+" "+dr.getSord();
		}else{
			hql+="order by createDate desc";
		}
		
		String totalHql=" select count(*) "+hql;
		//总记录数
		int total=applicationDao.count(totalHql,param).intValue();
		
		int totalPages;//总页数  
        int limit = dr.getRows() <= 0 ? 10 : dr.getRows();//每页显示数量  
        int page = dr.getPage() <= 0 ? 1 : dr.getPage();//当前显示页码 
        
        totalPages = total / limit;  
        if (total % limit != 0) {  
            totalPages++;  
        } 
        j.setRecords(total);
        j.setTotal(totalPages);
        j.setPage(page);
		
        List<Activity> list=activityDao.find(hql, dr.getPage(), dr.getRows(), param);
		List<HttpActivity> httpActivityList=new ArrayList<HttpActivity>();
		int i=1;
		for(Activity activity:list){
			
			HttpActivity httpActivity=new HttpActivity();
			BeanUtils.copyProperties(activity, httpActivity);
			
			httpActivity.setSeq(String.valueOf(i));
			
			this.copyActivityToHttpActivity(httpActivity, activity, type,roleKey);
			httpActivity.setCheckStatus(activity.getVerifyStatus().toString());
			httpActivityList.add(httpActivity);
			i++;
		}
		j.setRows(httpActivityList);
		return j;
	}
	@Override
	public void agreeForActivity(String id) {
		VerifyStatus verifyStatus=VerifyStatus.PASS;
		String hql="update Activity set verifyStatus='"+verifyStatus.name()+"' where id='"+id+"'";
		activityDao.executeHql(hql);
		
	}
	@Override
	public void rejectForActivity(String id) {
		VerifyStatus verifyStatus=VerifyStatus.FAIL;
		String hql="update Activity set verifyStatus='"+verifyStatus.name()+"' where id='"+id+"'";
		activityDao.executeHql(hql);
		
	}
	@Override
	public User currentUser() {
		return this.getUser(userRepository);
		
	}
	@Override
	public Json enable(String ids) {
		if(StringUtil.isNotNull(ids)){
			String[] id=ids.split(",");
			for(int i=0;i<id.length;i++){
				Activity activity=getActivityByid(id[i]);
				activity.setValid(true);
				activityDao.update(activity);
			}
		}
		return null;
	}
	@Override
	public Json notenable(String ids) {
		if(StringUtil.isNotNull(ids)){
			String[] id=ids.split(",");
			for(int i=0;i<id.length;i++){
				Activity activity=getActivityByid(id[i]);
				activity.setValid(false);
				activityDao.update(activity);
			}
		}
		return null;
	}
	@Override
	public HttpActivity getComments(String id) {
		Activity activity=this.getActivityByid(id);
		HttpActivity hActivity=new HttpActivity();
		BeanUtils.copyProperties(activity, hActivity);
		
		List<ActivityComments> comments=activity.getComments();
		List<HttpActivityComments> httpActivityComments=new ArrayList<HttpActivityComments>();
		if(comments!=null){
			for(ActivityComments activityComments:comments){
				HttpActivityComments httpActivityComment=new HttpActivityComments();
				httpActivityComment.setId(activityComments.getId());
				httpActivityComment.setContent(activityComments.getContent());
				httpActivityComment.setCreateDate(DateUtil.format(activityComments.getCreateDate(), "yyyy-MM-dd"));
				HttpUser httpUser=new HttpUser();
				User creater=activityComments.getUser();
				httpUser.setNickName(creater.getNickName());
				httpUser.setImageUrl(creater.getLogo());
				httpActivityComment.setHttpUser(httpUser);
				httpActivityComments.add(httpActivityComment);
			}
			hActivity.setHttpActivityComments(httpActivityComments);
			
			hActivity.setCommentCounts(String.valueOf(comments.size()));
		}else{
			hActivity.setCommentCounts(String.valueOf(0));
		}
		
		return hActivity;
	}
	@Override
	public void delComment(String id) {
		ActivityComments a=new ActivityComments();
		a.setId(id);
		activityCommentsDao.delete(a);
	}
	@Override
	public void saveActivityForVirtual(HttpActivity httpActivity,User user) {
		String code = httpActivity.getCode();
		String ids=httpActivity.getGovOrgIds();
		Activity activity=null;
		if(StringUtil.isNotNull(ids)){
			String[] id=ids.split(",");
			for(int i=0; i<id.length;i++){
				activity=new Activity();
//				BeanUtils.copyProperties(httpActivity, activity);
				GovOrg gov=new GovOrg();
				gov.setId(id[i]);
				activity.setGovOrg(gov);
				activity.setId(UUID.randomUUID().toString());
				Category category=this.getCategory(code);
			
				activity.setCategory(category);
				activity.setCreateDate(DateUtil.format(DateUtil.format(new Date()), "yyyy-MM-dd"));
				activity.setCreator(user);
				
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
				activityDao.save(activity);
			}
		}else{
			activity=new Activity();
			activity.setId(UUID.randomUUID().toString());
			Category category=this.getCategory(code);
			activity.setCategory(category);
			activity.setCreateDate(DateUtil.format(DateUtil.format(new Date()), "yyyy-MM-dd"));
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
			activity.setCreator(user);
			activityDao.save(activity);
		}
	}
	
	
	public void submitComment(
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
	}
}
