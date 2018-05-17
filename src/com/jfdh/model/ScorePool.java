package com.jfdh.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="scorepool")
public class ScorePool {
	@Id
	@Column(length=36)
	private String id;
	@OneToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "govOrgId", referencedColumnName = "id")
	private GovOrg govOrg;
	@OneToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "orgId", referencedColumnName = "id")
	private Org org;
	@OneToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private User user;
	private long balance;
	private long allGetPoints;
	private long dispensedTotalScore;
	private long recoveredTotalScore;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	@OneToMany(mappedBy = "from", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<ScoreBalanceRecord> fromScoreBalanceRecords=new ArrayList<ScoreBalanceRecord>();
	@OneToMany(mappedBy = "to", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<ScoreBalanceRecord> toScoreBalanceRecords=new ArrayList<ScoreBalanceRecord>();
	
	public long getAllGetPoints() {
		return allGetPoints;
	}
	public void setAllGetPoints(long allGetPoints) {
		this.allGetPoints = allGetPoints;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public GovOrg getGovOrg() {
		return govOrg;
	}
	public void setGovOrg(GovOrg govOrg) {
		this.govOrg = govOrg;
	}
	public Org getOrg() {
		return org;
	}
	public void setOrg(Org org) {
		this.org = org;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public long getDispensedTotalScore() {
		return dispensedTotalScore;
	}
	public void setDispensedTotalScore(long dispensedTotalScore) {
		this.dispensedTotalScore = dispensedTotalScore;
	}
	public long getRecoveredTotalScore() {
		return recoveredTotalScore;
	}
	public void setRecoveredTotalScore(long recoveredTotalScore) {
		this.recoveredTotalScore = recoveredTotalScore;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public List<ScoreBalanceRecord> getFromScoreBalanceRecords() {
		return fromScoreBalanceRecords;
	}
	public void setFromScoreBalanceRecords(
			List<ScoreBalanceRecord> fromScoreBalanceRecords) {
		this.fromScoreBalanceRecords = fromScoreBalanceRecords;
	}
	public List<ScoreBalanceRecord> getToScoreBalanceRecords() {
		return toScoreBalanceRecords;
	}
	public void setToScoreBalanceRecords(
			List<ScoreBalanceRecord> toScoreBalanceRecords) {
		this.toScoreBalanceRecords = toScoreBalanceRecords;
	}
	
	
}
