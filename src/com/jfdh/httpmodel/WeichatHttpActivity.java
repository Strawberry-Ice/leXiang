package com.jfdh.httpmodel;

import java.util.List;

import com.jfdh.model.FieldsType;


/**
 * 
 * @author Administrator 详细内容表
 */
public class WeichatHttpActivity {
	private String id;

	// 如活动名称，招募名称,活动名称
	private String name;
	
	//
	private String seq;

	// 属于哪个活动
	private String type;
	private String category_id;
	
	private String govOrgIds;
	private String govName;
	private String govParentName;
	
	private String content;
	
	// 阅读数
	private String readCounts;

	// 分享数
	private String shardCounts;

	// 报名开始时间
	private String applicationStartDates;

	// 报名截止时间
	private String applicationEndDates;

	// 活动开始时间
	private String activtyStartDates;

	// 活动截止时间
	private String activityEndDates;
	
	// 活动截止时间
	private String createDates;
	
	private String logo;

	// 报名名额
	private int quota;

	// 已报名人数
	private int applyNumber;
	
	// 剩余名额
	private String uneatenNumber;
	
	// 剩余时间
	private String uneatenTime;

	// 招募积分
	private int score;

	// 是否需要报名审核
	private boolean needVerify;

	// 备注
	private String remark;
	//是否启用
	private boolean valid;
	
	// 发布场合
	private String occasion ;
	
	//报名数据
	private String[] filedType;
	private List<FieldsType> fieldsType;
	//活动开始状态
	private String applicationStatus;
	
	private String checkStatus;
	//判断用户是否报名
	private boolean flag=false;
	
	//
	private List<HttpApplication> httpApplications;

	public List<FieldsType> getFieldsType() {
		return fieldsType;
	}

	public void setFieldsType(List<FieldsType> fieldsType) {
		this.fieldsType = fieldsType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReadCounts() {
		return readCounts;
	}

	public void setReadCounts(String readCounts) {
		this.readCounts = readCounts;
	}

	public String getShardCounts() {
		return shardCounts;
	}

	public void setShardCounts(String shardCounts) {
		this.shardCounts = shardCounts;
	}

	public String getApplicationStartDates() {
		return applicationStartDates;
	}

	public void setApplicationStartDates(String applicationStartDates) {
		this.applicationStartDates = applicationStartDates;
	}

	public String getApplicationEndDates() {
		return applicationEndDates;
	}

	public void setApplicationEndDates(String applicationEndDates) {
		this.applicationEndDates = applicationEndDates;
	}

	public String getActivtyStartDates() {
		return activtyStartDates;
	}

	public void setActivtyStartDates(String activtyStartDates) {
		this.activtyStartDates = activtyStartDates;
	}

	public String getActivityEndDates() {
		return activityEndDates;
	}

	public void setActivityEndDates(String activityEndDates) {
		this.activityEndDates = activityEndDates;
	}

	public String getCreateDates() {
		return createDates;
	}

	public void setCreateDates(String createDates) {
		this.createDates = createDates;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getQuota() {
		return quota;
	}

	public void setQuota(int quota) {
		this.quota = quota;
	}

	public int getApplyNumber() {
		return applyNumber;
	}

	public void setApplyNumber(int applyNumber) {
		this.applyNumber = applyNumber;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean getNeedVerify() {
		return needVerify;
	}

	public void setNeedVerify(boolean needVerify) {
		this.needVerify = needVerify;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getGovOrgIds() {
		return govOrgIds;
	}

	public void setGovOrgIds(String govOrgIds) {
		this.govOrgIds = govOrgIds;
	}

	public String getGovName() {
		return govName;
	}

	public void setGovName(String govName) {
		this.govName = govName;
	}

	public String getOccasion() {
		return occasion;
	}

	public void setOccasion(String occasion) {
		this.occasion = occasion;
	}

	public String[] getFiledType() {
		return filedType;
	}

	public void setFiledType(String[] filedType) {
		this.filedType = filedType;
	}

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getGovParentName() {
		return govParentName;
	}

	public void setGovParentName(String govParentName) {
		this.govParentName = govParentName;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public String getUneatenNumber() {
		return uneatenNumber;
	}

	public void setUneatenNumber(String uneatenNumber) {
		this.uneatenNumber = uneatenNumber;
	}

	public String getUneatenTime() {
		return uneatenTime;
	}

	public void setUneatenTime(String uneatenTime) {
		this.uneatenTime = uneatenTime;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public List<HttpApplication> getHttpApplications() {
		return httpApplications;
	}

	public void setHttpApplications(List<HttpApplication> httpApplications) {
		this.httpApplications = httpApplications;
	}
	
}
