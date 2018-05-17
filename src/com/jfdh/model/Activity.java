package com.jfdh.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.OrderBy;

/**
 * 
 * @author Administrator 详细内容表
 */
@Entity
@Table(name="activity")
public class Activity {
	@Id
	@Column(length=36)
	private String id;

	// 如活动名称，招募名称,活动名称
	@Column(length = 50)
	private String name;

	// 属于哪个活动
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "categoryId", referencedColumnName = "id")
	private Category category;

	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition = "TEXT", nullable = true)
	private String content;

	// 阅读数
	private Long readCount;

	// 分享数
	private Long shardCount;
	
	// 发布场合
	@Column(length = 10)
	private String occasion ;

	// 报名开始时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date applicationStartDate;
	
	@Column(length = 10)
	private String activtyStartTimes;

	// 报名截止时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date applicationEndDate;

	// 活动开始时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date activtyStartDate;

	// 活动截止时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date activityEndDate;
	
	// 
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(columnDefinition = "varchar(300)")
	private String logo;

	// 报名名额
	private int quota;

	// 已报名人数
	private int applyNumber;

	// 招募积分(每个人得到的单位积分)
	private int score;

	//该活动剩余的积分（从社区或者机构那边得到的积分）
	private int banlance;
	
	private boolean needTop;
	
	// 活动开始时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date topStartDate;

		// 活动截止时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date topEndDate;
	
	public int getBanlance() {
		return banlance;
	}

	public void setBanlance(int banlance) {
		this.banlance = banlance;
	}

	// 是否需要报名审核
	private boolean needVerify=true;

	// 备注
	@Column(length = 200)
	private String remark;

	// 是否启用
	private boolean valid = true;

	// 对机构发布信息的审核状态
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private VerifyStatus verifyStatus;
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "govOrgId", referencedColumnName = "id")
	private GovOrg govOrg;
	
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "orgId", referencedColumnName = "id")
	private Org org;

	// 社区居民通过user id来浏览信息分类
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private User creator;
	
	

	@OneToMany(mappedBy = "details", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@OrderBy(clause="createDate desc")
	private List<ActivityComments> comments = new ArrayList<ActivityComments>();

	// 报名数据
	@OneToMany(mappedBy = "activity", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<Application> applications = new ArrayList<Application>();

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "activityFieldType", joinColumns = @JoinColumn(name = "activityId"), inverseJoinColumns = @JoinColumn(name = "fieldTypeId"))
	private List<FieldsType> fieldTypes = new ArrayList<FieldsType>();

	@OneToMany(mappedBy = "activity", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<ScoreExchangeRecord> scoreExchangeRecords = new ArrayList<ScoreExchangeRecord>();
	@Transient
	private String frontStautus;
	@Column(length = 1000)
	private String url;
	
	private boolean needUrl;
	
	private boolean isDel=false;
	//是否需要推荐
	private boolean needPropose=false;
	//主办方
	private String sponsor;
	@Column(length = 100)
	private String image;//保存手机端上传图片的地址
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getFrontStautus() {
		Date now =new Date();
		if(VerifyStatus.SELLING.equals(verifyStatus)){
			return verifyStatus.getName();
		}else if(VerifyStatus.SOLD.equals(verifyStatus)){
			return verifyStatus.getName();
		}else if(now.after(applicationStartDate) && now.before(applicationEndDate)){
			return "报名进行中";
		}else if(now.before(applicationStartDate)){
			return "报名未开始";
		}else if(now.after(applicationEndDate) || quota-applyNumber<=0){
			return "已结束或报满";
		}
		return " ";
	}

	public void setFrontStautus(String frontStautus) {
		this.frontStautus = frontStautus;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getReadCount() {
		return readCount;
	}

	public void setReadCount(Long readCount) {
		this.readCount = readCount;
	}

	public Long getShardCount() {
		return shardCount;
	}

	public void setShardCount(Long shardCount) {
		this.shardCount = shardCount;
	}

	public Date getApplicationStartDate() {
		return applicationStartDate;
	}

	public void setApplicationStartDate(Date applicationStartDate) {
		this.applicationStartDate = applicationStartDate;
	}

	public Date getApplicationEndDate() {
		return applicationEndDate;
	}

	public void setApplicationEndDate(Date applicationEndDate) {
		this.applicationEndDate = applicationEndDate;
	}

	public Date getActivtyStartDate() {
		return activtyStartDate;
	}

	public void setActivtyStartDate(Date activtyStartDate) {
		this.activtyStartDate = activtyStartDate;
	}

	public Date getActivityEndDate() {
		return activityEndDate;
	}

	public void setActivityEndDate(Date activityEndDate) {
		this.activityEndDate = activityEndDate;
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

	public boolean isNeedVerify() {
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

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public VerifyStatus getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(VerifyStatus verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public GovOrg getGovOrg() {
		return govOrg;
	}

	public void setGovOrg(GovOrg govOrg) {
		this.govOrg = govOrg;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<ActivityComments> getComments() {
		return comments;
	}

	public void setComments(List<ActivityComments> comments) {
		this.comments = comments;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public List<FieldsType> getFieldTypes() {
		return fieldTypes;
	}

	public void setFieldTypes(List<FieldsType> fieldTypes) {
		this.fieldTypes = fieldTypes;
	}

	public List<ScoreExchangeRecord> getScoreExchangeRecords() {
		return scoreExchangeRecords;
	}

	public void setScoreExchangeRecords(
			List<ScoreExchangeRecord> scoreExchangeRecords) {
		this.scoreExchangeRecords = scoreExchangeRecords;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOccasion() {
		return occasion;
	}

	public void setOccasion(String occasion) {
		this.occasion = occasion;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isNeedTop() {
		return needTop;
	}

	public void setNeedTop(boolean needTop) {
		this.needTop = needTop;
	}

	public Date getTopStartDate() {
		return topStartDate;
	}

	public void setTopStartDate(Date topStartDate) {
		this.topStartDate = topStartDate;
	}

	public Date getTopEndDate() {
		return topEndDate;
	}

	public void setTopEndDate(Date topEndDate) {
		this.topEndDate = topEndDate;
	}

	public boolean isNeedUrl() {
		return needUrl;
	}

	public void setNeedUrl(boolean needUrl) {
		this.needUrl = needUrl;
	}

	public boolean isDel() {
		return isDel;
	}

	public void setDel(boolean isDel) {
		this.isDel = isDel;
	}

	public boolean isNeedPropose() {
		return needPropose;
	}

	public void setNeedPropose(boolean needPropose) {
		this.needPropose = needPropose;
	}

	public String getActivtyStartTimes() {
		return activtyStartTimes;
	}

	public void setActivtyStartTimes(String activtyStartTimes) {
		this.activtyStartTimes = activtyStartTimes;
	}
	
}
