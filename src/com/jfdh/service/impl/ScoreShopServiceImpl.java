package com.jfdh.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.jfdh.httpmodel.BackendUserDtail;
import com.jfdh.httpmodel.DataRequest;
import com.jfdh.httpmodel.DataResponse;
import com.jfdh.httpmodel.HttpData;
import com.jfdh.httpmodel.HttpScoreExchangeRecord;
import com.jfdh.httpmodel.HttpScoreShopItem;
import com.jfdh.httpmodel.HttpSearch;
import com.jfdh.httpmodel.HttpUserOnly;
import com.jfdh.httpmodel.PagingData;
import com.jfdh.httpmodel.ScoreTransfer;
import com.jfdh.httpmodel.WeiChatScoreRecord;
import com.jfdh.httpweichatmodel.SendMessage;
import com.jfdh.model.Activity;
import com.jfdh.model.Application;
import com.jfdh.model.ApplicationStatus;
import com.jfdh.model.GovOrg;
import com.jfdh.model.ScoreBalanceRecord;
import com.jfdh.model.ScoreExchangeRecord;
import com.jfdh.model.ScorePool;
import com.jfdh.model.ScoreShopItem;
import com.jfdh.model.ShopItemType;
import com.jfdh.model.User;
import com.jfdh.repository.ActivityRepository;
import com.jfdh.repository.ApplicationRepository;
import com.jfdh.repository.GovOrgRepository;
import com.jfdh.repository.ScoreBalanceRecordRepository;
import com.jfdh.repository.ScoreExchangeRecordRepository;
import com.jfdh.repository.ScorePoolRepository;
import com.jfdh.repository.ScoreShopItemRepository;
import com.jfdh.repository.ShopItemTypeRepository;
import com.jfdh.repository.UserRepository;
import com.jfdh.service.ISendMessageService;
import com.jfdh.service.ScoreShopService;
import com.jfdh.util.CouponUtil;
import com.jfdh.util.DateUtil;
import com.jfdh.util.DynamicSpecifications;
import com.jfdh.util.FileCopyHelper;
import com.jfdh.util.JpaTransactional;
import com.jfdh.util.SearchFilter;
import com.jfdh.util.SearchFilter.Operator;

@Service("scoreShopService")
public class ScoreShopServiceImpl implements ScoreShopService {
	@Autowired
	private ActivityRepository activityRepository;
	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private ScoreShopItemRepository scoreShopItemRepository;
	@Autowired
	private ShopItemTypeRepository shopItemTypeRepository;
	@Autowired
	private FileCopyHelper fileCopyHelper;
	@Autowired
	private ScorePoolRepository scorePoolRepository;
	@Autowired
	private GovOrgRepository govOrgRepository;
	@Autowired
	private ScoreBalanceRecordRepository scoreBalanceRecordRepository;
	@Autowired
	private ScoreExchangeRecordRepository scoreExchangeRecordRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ISendMessageService sendService;
	
	@Value("#{configProperties['scoreShoplogoPath']}")
	private String scoreShoplogoPath;

	@Override
	public DataResponse search(DataRequest request) {
		DataResponse response = new DataResponse();
		List<ScoreShopItem> list;
		Sort sort;
		if (StringUtils.isNotEmpty(request.getSidx())) {
			sort = new Sort(
					request.getSord().toLowerCase().equals("asc") ? Sort.Direction.ASC
							: Sort.Direction.DESC, request.getSidx());
		} else {
			sort = new Sort(Sort.Direction.DESC, "createDate");
		}
		Page<ScoreShopItem> scoreShopItems = scoreShopItemRepository
				.findAll(new PageRequest(request.getPage() - 1, request
						.getRows(), sort));
		list = scoreShopItems.getContent();
		response.setRecords((int) scoreShopItems.getTotalElements());
		response.setTotal(scoreShopItems.getTotalPages());
		response.setPage(request.getPage());
		response.setRows(list);

		return response;
	}

	@Override
	@Transactional()
	public ScoreShopItem save(ScoreShopItem ScoreShopItem) {
		ScoreShopItem.setCreateDate(new Date());
		ScoreShopItem.setRestNum(ScoreShopItem.getNum());
		
		return scoreShopItemRepository.save(ScoreShopItem);
	}

	@Override
	public ScoreShopItem findById(String id) {
		return scoreShopItemRepository.findOne(id);
	}

	@Override
	@JpaTransactional
	public synchronized boolean update(ScoreShopItem scoreShopItem) {
		
		ScoreShopItem ScoreShopItemInDb = scoreShopItemRepository
				.findOne(scoreShopItem.getId());
	
		ScoreShopItemInDb.setName(scoreShopItem.getName());
		ScoreShopItemInDb.setDesc(scoreShopItem.getDesc());
		ScoreShopItemInDb.setOrg(scoreShopItem.getOrg());
//		int restNum=ScoreShopItemInDb.getRestNum()-ScoreShopItemInDb.getNum()+scoreShopItem.getNum();
//		if(restNum<0){
//			return false;
//		}
//		scoreShopItem.setRestNum(restNum);
		if(!ScoreShopItemInDb.isStatus()){
			doAddAdminScorePool(scoreShopItem);
		}else{
			doDeleteAdminScorePool2(scoreShopItem);
		}
		ScoreShopItemInDb.setStatus(scoreShopItem.isStatus());
//		ShopItemType type = scoreShopItem.getType();
		
//		if (type.getId().equals("-1")) {
//			type.setId(UUID.randomUUID().toString());
//			type.setName("其他类");
//			type.setLottery(false);
//			type.setConstant(false);
//			shopItemTypeRepository.save(type);
//		} else {
//			type = shopItemTypeRepository.findOne(type.getId());
//		}

		if (StringUtils.isNotEmpty(scoreShopItem.getLogo())) {
			String result = fileCopyHelper.copyFile(scoreShopItem.getLogo(),
					scoreShoplogoPath);
			if (result.equals("failed")) {
				ScoreShopItemInDb.setLogo(null);
			} else {
				ScoreShopItemInDb.setLogo(result);
			}
		}
		ScoreShopItemInDb.setExpiry(DateUtil.getDateLastSecond(scoreShopItem.getExpiry()));
//		scoreShopItem.setRatio(type.getRatio());
//		ScoreShopItemInDb.setCreateDate(ScoreShopItemInDb.getCreateDate());
		scoreShopItemRepository.save(ScoreShopItemInDb);
		return true;
	}

	@Override
	@JpaTransactional
	public void delete(String ids) {
		for (String id : ids.split(",")) {
			ScoreShopItem scoreShopItem=scoreShopItemRepository.findOne(id);
			doDeleteAdminScorePool(scoreShopItem);
			scoreShopItemRepository.delete(scoreShopItem);
		}
	}

	@Override
	public List<ShopItemType> findAllConstantItemType() {
		return shopItemTypeRepository.findByConstantTrue();
	}

	@Override
	public BigDecimal findRatioById(String id) {
		ShopItemType type = shopItemTypeRepository.findOne(id);
		if (type.isLottery()) {
			return BigDecimal.valueOf(-1);
		} else {
			return type.getRatio();
		}
	}

	@Override
	@JpaTransactional
	public ScoreShopItem addScoreShopItem(ScoreShopItem scoreShopItem) {
		ShopItemType type = scoreShopItem.getType();
		if (type.getId().equals("-1")) {
			type.setId(UUID.randomUUID().toString());
			type.setName("其他类");
			type.setLottery(false);
			type.setConstant(false);
			shopItemTypeRepository.save(type);
		} else {
			type = shopItemTypeRepository.findOne(type.getId());
		}

		if (StringUtils.isNotEmpty(scoreShopItem.getLogo())) {
			String result = fileCopyHelper.copyFile(scoreShopItem.getLogo(),
					scoreShoplogoPath);
			if (result.equals("failed")) {
				scoreShopItem.setLogo(null);
			} else {
				scoreShopItem.setLogo(result);
			}
		}
		scoreShopItem.setRestNum(scoreShopItem.getNum());
		scoreShopItem.setExpiry(DateUtil.getDateLastSecond(scoreShopItem.getExpiry()));
		scoreShopItem.setRatio(type.getRatio());
		scoreShopItem.setId(UUID.randomUUID().toString());
		scoreShopItem.setCreateDate(new Date());
		
		doAddAdminScorePool(scoreShopItem);
		return scoreShopItemRepository.save(scoreShopItem);
	}

	private void doAddAdminScorePool(ScoreShopItem scoreShopItem) {
		if (scoreShopItem.isStatus()) {
			@SuppressWarnings("unchecked")
			Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder
					.getContext().getAuthentication().getAuthorities();
			;
			for (GrantedAuthority authority : authorities) {
				if (authority.getAuthority().equals("ROLE_Role_sys_admin")) {
					ScorePool adminScorePool = scorePoolRepository
							.findByGovOrg_AdminOrgTrue();
					if (adminScorePool == null) {
						adminScorePool = new ScorePool();
						adminScorePool.setId(UUID.randomUUID().toString());
						adminScorePool.setCreateDate(new Date());
						adminScorePool.setGovOrg(govOrgRepository
								.findByAdminOrgTrue());
					}
					adminScorePool
							.setBalance(adminScorePool.getBalance()
									+ scoreShopItem.getScore()
									* scoreShopItem.getRestNum());
					scorePoolRepository.save(adminScorePool);
					break;
				}
			}
		}
	}
	
	
	private void doDeleteAdminScorePool(ScoreShopItem scoreShopItem) {
		if (scoreShopItem.isStatus()) {
			@SuppressWarnings("unchecked")
			Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder
					.getContext().getAuthentication().getAuthorities();
			;
			for (GrantedAuthority authority : authorities) {
				if (authority.getAuthority().equals("ROLE_Role_sys_admin")) {
					ScorePool adminScorePool = scorePoolRepository
							.findByGovOrg_AdminOrgTrue();
					if (adminScorePool == null) {
						adminScorePool = new ScorePool();
						adminScorePool.setId(UUID.randomUUID().toString());
						adminScorePool.setCreateDate(new Date());
						adminScorePool.setGovOrg(govOrgRepository
								.findByAdminOrgTrue());
					}
					adminScorePool
							.setBalance(adminScorePool.getBalance()
									- scoreShopItem.getScore()
									* scoreShopItem.getRestNum());
					scorePoolRepository.save(adminScorePool);
					break;
				}
			}
		}
	}
	
	private void doDeleteAdminScorePool2(ScoreShopItem scoreShopItem) {
		if (!scoreShopItem.isStatus()) {
			@SuppressWarnings("unchecked")
			Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder
					.getContext().getAuthentication().getAuthorities();
			;
			for (GrantedAuthority authority : authorities) {
				if (authority.getAuthority().equals("ROLE_Role_sys_admin")) {
					ScorePool adminScorePool = scorePoolRepository
							.findByGovOrg_AdminOrgTrue();
					if (adminScorePool == null) {
						adminScorePool = new ScorePool();
						adminScorePool.setId(UUID.randomUUID().toString());
						adminScorePool.setCreateDate(new Date());
						adminScorePool.setGovOrg(govOrgRepository
								.findByAdminOrgTrue());
					}
					adminScorePool
							.setBalance(adminScorePool.getBalance()
									- scoreShopItem.getScore()
									* scoreShopItem.getRestNum());
					scorePoolRepository.save(adminScorePool);
					break;
				}
			}
		}
	}
	
	
	private void doDeleteAdminScorePool3(ScoreShopItem scoreShopItem) {
		if (!scoreShopItem.isStatus()) {
			ScorePool adminScorePool = scorePoolRepository
					.findByGovOrg_AdminOrgTrue();
			if (adminScorePool == null) {
				adminScorePool = new ScorePool();
				adminScorePool.setId(UUID.randomUUID().toString());
				adminScorePool.setCreateDate(new Date());
				adminScorePool.setGovOrg(govOrgRepository
						.findByAdminOrgTrue());
			}
			adminScorePool
					.setBalance(adminScorePool.getBalance()
							- scoreShopItem.getScore()
							* scoreShopItem.getRestNum());
			scorePoolRepository.save(adminScorePool);
		}
	}

	@Override
	public ScorePool findAdminScorePool() {
		return scorePoolRepository.findByGovOrg_AdminOrgTrue();
	}

	@Override
	public List<ScorePool> findTop5ByScorePoolGovOrgParent() {
		return scorePoolRepository
				.findTop5ByGovOrg_AdminOrgFalseAndGovOrg_ValidTrueAndGovOrg_Parent_IdIsNullOrderByBalanceDesc();
	}

	@Override
	public List<ScorePool> findTop5ByScorePoolGovOrg() {
		return scorePoolRepository
				.findTop5ByGovOrg_AdminOrgFalseAndGovOrg_ValidTrueAndGovOrg_Parent_IdIsNotNullOrderByBalanceDesc();
	}

	@Override
	public List<ScorePool> findTop5ByUsers() {
		return scorePoolRepository.findTop5ByUser_StateTrueOrderByBalanceDesc();
	}

	@Override
	@JpaTransactional
	public synchronized boolean transfer(ScoreTransfer scoreTransfer) {
		ScorePool adminScorePool = scorePoolRepository.findByGovOrg_AdminOrgTrue();
		if(scoreTransfer.getScore()*scoreTransfer.getOrgIds().length>adminScorePool.getBalance()){
			return false;
		}else{
			List<String> ids = new ArrayList<String>();
			for (String str : scoreTransfer.getOrgIds()){
				ids.add(str);
			}
			List<ScorePool> updateScorePools=new ArrayList<ScorePool>();
			List<ScoreBalanceRecord> records=new ArrayList<ScoreBalanceRecord>();
			List<GovOrg> govOrgs = govOrgRepository.findByIdIn(ids);
			for(GovOrg govOrg:govOrgs){
				ScorePool sp=govOrg.getScorePool();
				if(null==sp){
					sp=new ScorePool();
					sp.setId(UUID.randomUUID().toString());
					sp.setCreateDate(new Date());
					sp.setBalance(scoreTransfer.getScore());
					sp.setGovOrg(govOrg);
				}else{
					sp.setBalance(sp.getBalance()+scoreTransfer.getScore());
				}
				sp.setUpdateDate(new Date());
				updateScorePools.add(sp);
				ScoreBalanceRecord record=new ScoreBalanceRecord();
				record.setId(UUID.randomUUID().toString());
				record.setAmount(scoreTransfer.getScore());
				record.setFrom(adminScorePool);
				record.setTo(sp);
				record.setCreateDate(new Date());
				
				records.add(record);
			}
			
			adminScorePool.setBalance(adminScorePool.getBalance()-scoreTransfer.getScore()*records.size());
			adminScorePool.setDispensedTotalScore(adminScorePool.getDispensedTotalScore()+scoreTransfer.getScore()*records.size());
			adminScorePool.setUpdateDate(new Date());
			updateScorePools.add(adminScorePool);
			scorePoolRepository.save(updateScorePools);
			scoreBalanceRecordRepository.save(records);
		
			return true;
		}
		
	}

	@Override
	@JpaTransactional
	public synchronized Object doExchangeValid(String key,User user) {
		ScoreExchangeRecord scoreExchangeRecord=scoreExchangeRecordRepository.findByCoupon(key);
		if(scoreExchangeRecord==null){
			return "兑换码错误！！";
		}else if(scoreExchangeRecord.isStatus()){
			return "不允许重复兑换！！";
		}else{
			if(scoreExchangeRecord.getExpiry().before(new Date())){
				return "过期不能兑换！！";
			}
			if(user.getRole().getRoleKey().equals("sys_admin")){
				scoreExchangeRecord.setStatus(true);
				scoreExchangeRecord.setVerifyDate(new Date());
				scoreExchangeRecord.setOperator(user);
				scoreExchangeRecordRepository.save(scoreExchangeRecord);
				return scoreExchangeRecord;
			}else if(user.getOrg()!=null&&user.getOrg().getId().equals(scoreExchangeRecord.getScoreShop().getOrg().getId())){
				scoreExchangeRecord.setStatus(true);
				scoreExchangeRecord.setVerifyDate(new Date());
				scoreExchangeRecord.setOperator(user);
				scoreExchangeRecordRepository.save(scoreExchangeRecord);
				return scoreExchangeRecord;
			}else{
				return "对不起，您没有权限";
			}
		}
	}

	@Override
	public List<ScoreExchangeRecord> getAllExchangeRecord() {
		return scoreExchangeRecordRepository.findByStatusTrue();
	}

	@Override
	@JpaTransactional
	public DataResponse searchScoreExchangeList(DataRequest request) {
		DataResponse response = new DataResponse();
		List<ScoreExchangeRecord> list;
		Sort sort;
		if (StringUtils.isNotEmpty(request.getSidx())) {
			sort = new Sort(
					request.getSord().toLowerCase().equals("asc") ? Sort.Direction.ASC
							: Sort.Direction.DESC, request.getSidx());
		} else {
			sort = new Sort(Sort.Direction.DESC, "createDate");
		}
		
		@SuppressWarnings("unchecked")
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
			    .getAuthentication().getAuthorities();
		Page<ScoreExchangeRecord> scoreShopItems=null;
		for(GrantedAuthority authority:authorities){
			if(authority.getAuthority().equals("ROLE_Role_sys_admin")){
				scoreShopItems = scoreExchangeRecordRepository
						.findByStatusTrue(new PageRequest(request.getPage() - 1, request
								.getRows(), sort));
				break;
			}
		}
		if(null==scoreShopItems){
			BackendUserDtail userDetails = (BackendUserDtail) SecurityContextHolder.getContext()
				    .getAuthentication()
				    .getPrincipal();
			User user=userRepository.findOne(userDetails.getId());
			System.out.println(user.getOrg().getName()+">>>>>>>>>>>>>>");
			scoreShopItems = scoreExchangeRecordRepository
					.findByStatusTrueAndScoreShop_Org(user.getOrg(),new PageRequest(request.getPage() - 1, request
							.getRows(), sort));
			
		}
		list = scoreShopItems.getContent();
		List<HttpScoreExchangeRecord> returnValue=new ArrayList<HttpScoreExchangeRecord>();
		for(ScoreExchangeRecord sec:list){
			HttpScoreExchangeRecord hser=new HttpScoreExchangeRecord();
			BeanUtils.copyProperties(sec, hser);
			HttpUserOnly operator=new HttpUserOnly();
			HttpUserOnly exchanger=new HttpUserOnly();
			HttpScoreShopItem scoreShop=new HttpScoreShopItem();
			BeanUtils.copyProperties(sec.getOperator(), operator);
			BeanUtils.copyProperties(sec.getExchanger(), exchanger);
			BeanUtils.copyProperties(sec.getScoreShop(), scoreShop);
			
			hser.setOperator(operator);
			hser.setExchanger(exchanger);
			hser.setScoreShop(scoreShop);
			returnValue.add(hser);
		}
		
		response.setRecords((int) scoreShopItems.getTotalElements());
		response.setTotal(scoreShopItems.getTotalPages());
		response.setPage(request.getPage());
		response.setRows(returnValue);

		return response;
	}

	@Override
	public List<ScoreExchangeRecord> findAllScoreExchangeList() {
		return (List<ScoreExchangeRecord>) scoreExchangeRecordRepository.findByStatusTrue();
	}

	@Override
	public Page<ScoreShopItem> findAllScoreShop4weichat(DataRequest request) {
		Sort sort = new Sort(Sort.Direction.DESC, "createDate");
		Page<ScoreShopItem> scoreShopItems = scoreShopItemRepository
				.findByRestNumGreaterThanAndExpiryGreaterThanAndStatusTrue(0,new Date(),new PageRequest(request.getPage() - 1, request
						.getRows(), sort));
		return scoreShopItems;
	}

	@Override
	@JpaTransactional
	public PagingData<WeiChatScoreRecord> findAllBalanceRecord4weichat(String userId,
			DataRequest request) {
		
		Sort sort = new Sort(Sort.Direction.DESC, "createDate");
		Page<ScoreBalanceRecord> pages = scoreBalanceRecordRepository.findByFrom_UserIdOrTo_UserId(userId,userId,new PageRequest(request.getPage() - 1, request
				.getRows(),sort));
		List<ScoreBalanceRecord> records=pages.getContent();
		List<WeiChatScoreRecord> myRecords=new ArrayList<WeiChatScoreRecord>();
		
		for(ScoreBalanceRecord record:records){
			WeiChatScoreRecord weiRecord=new WeiChatScoreRecord();
			BeanUtils.copyProperties(record, weiRecord);
			if(record.getFrom().getUser()!=null&&record.getFrom().getUser().getId().equals(userId)){
				weiRecord.setPlus(false);
				weiRecord.setMessage("使用积分");
			}else{
				weiRecord.setPlus(true);
				weiRecord.setMessage("获得积分");
			}
			weiRecord.setScore((int)record.getAmount());
			myRecords.add(weiRecord);
		}
		
		PagingData<WeiChatScoreRecord> pagingData=new PagingData<WeiChatScoreRecord>();
		pagingData.setContent(myRecords);
		pagingData.setHasNext(pages.hasNext());
		return pagingData;
	}

	@Override
	@JpaTransactional
	public synchronized String doExchangeItem(String userId, String id, Integer number) {
		ScoreShopItem item=scoreShopItemRepository.findOne(id);
		
		ScoreExchangeRecord myRecord = scoreExchangeRecordRepository.findByExchanger_IdAndScoreShop_Id(userId,id);
		if(null!=myRecord){
			return "您已经兑换过！！选择其他商品！！";
		}
		if(item.getRestNum().intValue()<number.intValue()){
			return "您下手晚啦！！商城中可兑换的物品不足！！请减少数量或者选择其他商品！！";
		}
		User user=userRepository.findOne(userId);
		ScorePool pool=user.getScorePool();
		int needTotalScore=item.getScore()*number;
		if(null==pool||needTotalScore>pool.getBalance()){
			return "您的积分不足！！请减少数量或者选择其他商品！！";
		}
		pool.setBalance(pool.getBalance()-needTotalScore);
		pool.setUpdateDate(new Date());
		scorePoolRepository.save(pool);
		item.setRestNum(item.getRestNum()-number);
		scoreShopItemRepository.save(item);
		
		String coupon=CouponUtil.generateShortUuid();
		ScoreExchangeRecord tempRecord = scoreExchangeRecordRepository.findByCoupon(coupon);
		while(null!=tempRecord){
			coupon=CouponUtil.generateShortUuid();
			tempRecord = scoreExchangeRecordRepository.findByCoupon(coupon);
		}
		
		ScoreExchangeRecord scoreExchangeRecord=new ScoreExchangeRecord();
		scoreExchangeRecord.setCoupon(coupon);
		scoreExchangeRecord.setCreateDate(new Date());
		scoreExchangeRecord.setExchanger(user);
		if(item.getDay()==0){
			scoreExchangeRecord.setExpiry(item.getExpiry());
		}else{
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, item.getDay());
			
			scoreExchangeRecord.setExpiry(cal.getTime());
		}
		scoreExchangeRecord.setId(UUID.randomUUID().toString());
		scoreExchangeRecord.setNumber(number);
		scoreExchangeRecord.setScoreShop(item);
		scoreExchangeRecord.setStatus(false);
		scoreExchangeRecordRepository.save(scoreExchangeRecord);
		
		ScoreBalanceRecord scoreBalanceRecord=new ScoreBalanceRecord();
		
		ScorePool orgPool=item.getOrg().getScorePool();
		
		if(null==orgPool){
			orgPool=new ScorePool();
			orgPool.setBalance(0);
			orgPool.setCreateDate(new Date());
			orgPool.setDispensedTotalScore(0);
			orgPool.setOrg(item.getOrg());
			orgPool.setRecoveredTotalScore(needTotalScore);
			orgPool.setUpdateDate(new Date());
			orgPool.setId(UUID.randomUUID().toString());
		}else{
			orgPool.setRecoveredTotalScore(orgPool.getRecoveredTotalScore()+needTotalScore);
			orgPool.setUpdateDate(new Date());
		}
		scorePoolRepository.save(orgPool);
		
		scoreBalanceRecord.setAmount(needTotalScore);
		scoreBalanceRecord.setCreateDate(new Date());
		scoreBalanceRecord.setFrom(pool);
		scoreBalanceRecord.setTo(orgPool);
		scoreBalanceRecord.setId(UUID.randomUUID().toString());
		scoreBalanceRecord.setItem(item);
		scoreBalanceRecord.setRecord(scoreExchangeRecord);
		scoreBalanceRecordRepository.save(scoreBalanceRecord);
		
		SendMessage message=new SendMessage();
		message.setOpenId(user.getOpenid());
		message.setFieldName("类型");
		message.setAccount("兑换"+item.getName());
		message.setChange("减少");
		message.setCreditChange(needTotalScore+"");
		message.setCreditTotal(pool.getBalance()+"");
		sendService.SendScoreMessage(message);
		return "success";
	}

	@Override
	public Page<ScoreBalanceRecord> findAllMyScoreRecord(String userId,
			DataRequest request) {
		Page<ScoreBalanceRecord> pages = scoreBalanceRecordRepository.findByFrom_UserIdOrTo_UserId(userId,userId,new PageRequest(0, 10));
		return pages;
	}

	@Override
	@JpaTransactional
	public WeiChatScoreRecord findWeiChatScoreRecordById(String id,
			String userId) {
		ScoreBalanceRecord record=scoreBalanceRecordRepository.findOne(id);
		WeiChatScoreRecord weiRecord=new WeiChatScoreRecord();
		BeanUtils.copyProperties(record, weiRecord);
		if(record.getFrom().getUser()!=null&&record.getFrom().getUser().getId().equals(userId)){
			weiRecord.setPlus(false);
			weiRecord.setMessage("兑换");
			weiRecord.setNumber(record.getRecord().getNumber());
			weiRecord.setLogo(record.getItem().getLogo());
			weiRecord.setName(record.getItem().getName());
		}else{
			weiRecord.setPlus(true);
			weiRecord.setMessage("参加");
			weiRecord.setName(record.getApplication().getActivity().getName());
			weiRecord.setContent(record.getApplication().getActivity().getContent());
		}
		weiRecord.setScore((int)record.getAmount());
		return weiRecord;
	}

	@Override
	public Page<ScoreExchangeRecord> findAllCoupons4weichat(
			String userId, DataRequest request) {
		Sort sort;
		if (StringUtils.isNotEmpty(request.getSidx())) {
			sort = new Sort(
					request.getSord().toLowerCase().equals("asc") ? Sort.Direction.ASC
							: Sort.Direction.DESC, request.getSidx());
		} else {
			sort = new Sort(Sort.Direction.DESC, "createDate");
		}
		Page<ScoreExchangeRecord> records = scoreExchangeRecordRepository
				.findByExchanger_Id(userId,new PageRequest(request.getPage() - 1, request
						.getRows(), sort));
		return records;
	}

	@Override
	public ScoreExchangeRecord findWeiChatScoreRecordById(String id) {
		return scoreExchangeRecordRepository.findOne(id);
	}
	
	
	@Override
	@JpaTransactional
	public synchronized String application(String code, String id) {
		if(code.toUpperCase().equals(ApplicationStatus.FINISHED.name())){
			Application app=applicationRepository.findOne(id);
			if(app.getApplicationStatus()!=null){
				return "failed";
			}
			app.setApplicationStatus(ApplicationStatus.FINISHED);
			applicationRepository.save(app);
			Activity activity=app.getActivity();
			if(activity.getScore()>0){
				activity.setBanlance(activity.getBanlance()-activity.getScore());

				ScorePool sp=app.getUser().getScorePool();
				if(null==sp){
					sp=new ScorePool();
					sp.setId(UUID.randomUUID().toString());
					sp.setCreateDate(new Date());
					sp.setBalance(activity.getScore());
					sp.setUser(app.getUser());
				}else{
					sp.setBalance(sp.getBalance()+activity.getScore());
				}
				
				sp.setAllGetPoints(sp.getAllGetPoints()+activity.getScore());
				sp.setUpdateDate(new Date());
				ScoreBalanceRecord record=new ScoreBalanceRecord();
				record.setId(UUID.randomUUID().toString());
				record.setAmount(activity.getScore());
				record.setFrom(activity.getGovOrg()==null?activity.getOrg().getScorePool():activity.getGovOrg().getScorePool());
				record.setTo(sp);
				record.setCreateDate(new Date());
				record.setApplication(app);
				
				scorePoolRepository.save(sp);
				scoreBalanceRecordRepository.save(record);
				activityRepository.save(activity);
				SendMessage message=new SendMessage();
				message.setOpenId(app.getUser().getOpenid());
				message.setFieldName("类型");
				message.setAccount("完成"+activity.getName());
				message.setChange("增加");
				message.setCreditChange(activity.getScore()+"");
				message.setCreditTotal(sp.getBalance()+"");
				sendService.SendScoreMessage(message);
			}
			
			return "success";
		}else if(code.toUpperCase().equals(ApplicationStatus.UNFINISHED.name())){
			Application app=applicationRepository.findOne(id);
			if(app.getApplicationStatus()!=null){
				return "failed";
			}
			app.setApplicationStatus(ApplicationStatus.UNFINISHED);
			applicationRepository.save(app);
			return "success";
		}
		return null;
	}

	@Override
	public ScorePool findAdminScorePoolById(String id) {
		User user=userRepository.findOne(id);
		ScorePool sp;
		if(null!=user.getGovOrg()){
			sp=user.getGovOrg().getScorePool();
		}else{
			sp=user.getOrg().getScorePool();
		}
		return sp;
	}

	@Override
	public List<ScorePool> findTop5ByUsersById(String id) {
		User user=userRepository.findOne(id);
		return scorePoolRepository.findTop5ByUser_GovOrg_IdAndUser_StateTrueOrderByBalanceDesc(user.getGovOrg().getId());
	}

	@Override
	public DataResponse search(DataRequest request, HttpSearch httpSearch) {
		if(null==httpSearch||httpSearch.getRules().size()==0){
			return this.search(request);
		}
		

		List<HttpData> list2=httpSearch.getRules();
		HttpData httpData=list2.get(0);
		
		String fieldName=httpData.getField();
		String fieldValue=httpData.getData();
		
		DataResponse response = new DataResponse();
		List<ScoreShopItem> list;
		Sort sort;
		if (StringUtils.isNotEmpty(request.getSidx())) {
			sort = new Sort(
					request.getSord().toLowerCase().equals("asc") ? Sort.Direction.ASC
							: Sort.Direction.DESC, request.getSidx());
		} else {
			sort = new Sort(Sort.Direction.DESC, "createDate");
		}
		
		Pageable pageable= new PageRequest(request.getPage() - 1, request.getRows(), sort);
		
		Specification<ScoreShopItem> spe=buildSpecification(fieldName, fieldValue);
		
		Page<ScoreShopItem> scoreShopItems = scoreShopItemRepository
				.findAll(spe,pageable);
		list = scoreShopItems.getContent();
		response.setRecords((int) scoreShopItems.getTotalElements());
		response.setTotal(scoreShopItems.getTotalPages());
		response.setPage(request.getPage());
		response.setRows(list);

		return response;
	}
	
	
	private Specification<ScoreShopItem> buildSpecification(String key,String value) {
		Map<String, SearchFilter> filters = Maps.newHashMap();
		if(StringUtils.isNotEmpty(value)){
			filters.put(key, new SearchFilter(key, Operator.LIKE, value));
		}
		Specification<ScoreShopItem> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), ScoreShopItem.class);
		return spec;
	}
	
	private Specification<ScoreExchangeRecord> buildSpecification2(String key,String value) {
		Map<String, SearchFilter> filters = Maps.newHashMap();
		if(StringUtils.isNotEmpty(value)){
			filters.put(key, new SearchFilter(key, Operator.LIKE, value));
		}
		
		filters.put("status", new SearchFilter("status", Operator.EQ, true));
		Specification<ScoreExchangeRecord> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), ScoreExchangeRecord.class);
		return spec;
	}
	
	
	private Specification<ScoreExchangeRecord> buildSpecification2(String key,String value,String key2,Object value2) {
		Map<String, SearchFilter> filters = Maps.newHashMap();
		if(StringUtils.isNotEmpty(value)){
			filters.put(key, new SearchFilter(key, Operator.LIKE, value));
		}
		if(null!=value2){
			filters.put(key2, new SearchFilter(key2, Operator.LIKE, value2));
		}
		filters.put("status", new SearchFilter("status", Operator.EQ, true));
		Specification<ScoreExchangeRecord> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), ScoreExchangeRecord.class);
		return spec;
	}

	@Override
	public DataResponse searchScoreExchangeList(DataRequest request,
			HttpSearch httpSearch) {
		if(null==httpSearch||httpSearch.getRules().size()==0){
			return this.searchScoreExchangeList(request);
		}
		List<HttpData> list2=httpSearch.getRules();
		HttpData httpData=list2.get(0);
		
		String fieldName=httpData.getField();
		String fieldValue=httpData.getData();
		
		DataResponse response = new DataResponse();
		List<ScoreExchangeRecord> list;
		Sort sort;
		if (StringUtils.isNotEmpty(request.getSidx())) {
			sort = new Sort(
					request.getSord().toLowerCase().equals("asc") ? Sort.Direction.ASC
							: Sort.Direction.DESC, request.getSidx());
		} else {
			sort = new Sort(Sort.Direction.DESC, "createDate");
		}
		
		Pageable pageable= new PageRequest(request.getPage() - 1, request.getRows(), sort);
		
		
		@SuppressWarnings("unchecked")
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
			    .getAuthentication().getAuthorities();
		Page<ScoreExchangeRecord> scoreShopItems=null;
		for(GrantedAuthority authority:authorities){
			if(authority.getAuthority().equals("ROLE_Role_sys_admin")){
				Specification<ScoreExchangeRecord> spe=buildSpecification2(fieldName, fieldValue);
				scoreShopItems = scoreExchangeRecordRepository
						.findAll(spe,pageable);
				break;
			}
		}
		if(null==scoreShopItems){
			BackendUserDtail userDetails = (BackendUserDtail) SecurityContextHolder.getContext()
				    .getAuthentication()
				    .getPrincipal();
			User user=userRepository.findOne(userDetails.getId());
			Specification<ScoreExchangeRecord> spe=buildSpecification2(fieldName, fieldValue,"scoreShop.org",user.getOrg());
			scoreShopItems = scoreExchangeRecordRepository
					.findAll(spe,pageable);
			/*scoreShopItems = scoreExchangeRecordRepository
					.findByStatusTrueAndScoreShop_Org(user.getOrg(),new PageRequest(request.getPage() - 1, request
							.getRows(), sort));*/
			
		}
		list = scoreShopItems.getContent();
		List<HttpScoreExchangeRecord> returnValue=new ArrayList<HttpScoreExchangeRecord>();
		for(ScoreExchangeRecord sec:list){
			HttpScoreExchangeRecord hser=new HttpScoreExchangeRecord();
			BeanUtils.copyProperties(sec, hser);
			HttpUserOnly operator=new HttpUserOnly();
			HttpUserOnly exchanger=new HttpUserOnly();
			HttpScoreShopItem scoreShop=new HttpScoreShopItem();
			BeanUtils.copyProperties(sec.getOperator(), operator);
			BeanUtils.copyProperties(sec.getExchanger(), exchanger);
			BeanUtils.copyProperties(sec.getScoreShop(), scoreShop);
			
			hser.setOperator(operator);
			hser.setExchanger(exchanger);
			hser.setScoreShop(scoreShop);
			returnValue.add(hser);
		}
		
		response.setRecords((int) scoreShopItems.getTotalElements());
		response.setTotal(scoreShopItems.getTotalPages());
		response.setPage(request.getPage());
		response.setRows(returnValue);

		return response;
	}

	@Override
	@JpaTransactional
	public void doBusiness() {
		List<ScoreShopItem> items=scoreShopItemRepository.findByExpiryLessThanAndStatusTrue(new Date());
		for(ScoreShopItem scoreShopItem:items){
			scoreShopItem.setStatus(false);
			
			doDeleteAdminScorePool3(scoreShopItem);
		
			scoreShopItemRepository.save(items);
			
		}
	
	}

	@Override
	public List<ScorePool> findAllTOP10Score() {
		return scorePoolRepository.findTop10ByAllGetPointsGreaterThanAndUserIsNotNullOrderByAllGetPointsDesc(0);
	}

}
