package com.jfdh.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="scoreexchangerecord")
public class ScoreExchangeRecord {
	@Id
	@Column(length=36)
	private String id;
	//登记人员
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "operatorUserId", referencedColumnName = "id")
	private User operator;
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "scoreShopItemId", referencedColumnName = "id")
	private ScoreShopItem scoreShop;
	
	private int number;
	//兑换码
	@Column(length=100,unique=true)
	private String coupon;
//	private int score;
	//登记日期
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	//使用状态
	private boolean status;
	//使用时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date verifyDate;
	//兑换有效期
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiry;
	//使用者
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "exchangerUserId", referencedColumnName = "id")
	private User exchanger;
	//获取积分
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "activityId", referencedColumnName = "id")
	private Activity activity;
	
	@OneToMany(mappedBy = "record", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<ScoreBalanceRecord> scoreBalanceRecords=new ArrayList<ScoreBalanceRecord>();
	@Transient
	public String getStatusName(){
		if(status){
			return "已使用";
		}else{
			Date now=new Date();
			if(expiry.after(now)){
				return "未使用";
			}else{
				return "已过期";
			}
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public ScoreShopItem getScoreShop() {
		return scoreShop;
	}

	public void setScoreShop(ScoreShopItem scoreShop) {
		this.scoreShop = scoreShop;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

	public User getExchanger() {
		return exchanger;
	}

	public void setExchanger(User exchanger) {
		this.exchanger = exchanger;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public List<ScoreBalanceRecord> getScoreBalanceRecords() {
		return scoreBalanceRecords;
	}

	public void setScoreBalanceRecords(List<ScoreBalanceRecord> scoreBalanceRecords) {
		this.scoreBalanceRecords = scoreBalanceRecords;
	}
	
	
}
