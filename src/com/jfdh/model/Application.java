package com.jfdh.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 报名数据
 * @author wangfei
 *
 */
@Entity
@Table(name="application")
public class Application {
	@Id
	@Column(length=36)
	private String id;
	
	//活动或者招募名称
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "activityId", referencedColumnName = "id")
	private Activity activity;
	
	//报名人员
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private User user;
	
	//报名时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date applyDate;

	//状态
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private VerifyStatus status;
	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private ApplicationStatus applicationStatus;
	
	
	@OneToOne(mappedBy = "application", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private ScoreBalanceRecord scoreBalanceRecord;
	
	public ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}


	@OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<FieldValue> fields=new ArrayList<FieldValue>();
	
	public ScoreBalanceRecord getScoreBalanceRecord() {
		return scoreBalanceRecord;
	}

	public void setScoreBalanceRecord(ScoreBalanceRecord scoreBalanceRecord) {
		this.scoreBalanceRecord = scoreBalanceRecord;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public VerifyStatus getStatus() {
		return status;
	}

	public void setStatus(VerifyStatus status) {
		this.status = status;
	}

	public List<FieldValue> getFields() {
		return fields;
	}

	public void setFields(List<FieldValue> fields) {
		this.fields = fields;
	}
	
}
