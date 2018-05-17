package com.jfdh.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="scorebalancerecord")
public class ScoreBalanceRecord {
	@Id
	@Column(length=36)
	private String id;
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "fromScorePoolId", referencedColumnName = "id")
	private ScorePool from;
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "toScorePoolId", referencedColumnName = "id")
	private ScorePool to;
	private long amount;
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "scoreExchangeRecordId", referencedColumnName = "id")
	private ScoreExchangeRecord record;
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "scoreShopItemId", referencedColumnName = "id")
	private ScoreShopItem item;
	@OneToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "applicationId", referencedColumnName = "id")
	private Application application;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ScorePool getFrom() {
		return from;
	}
	public void setFrom(ScorePool from) {
		this.from = from;
	}
	public ScorePool getTo() {
		return to;
	}
	public void setTo(ScorePool to) {
		this.to = to;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public ScoreExchangeRecord getRecord() {
		return record;
	}
	public void setRecord(ScoreExchangeRecord record) {
		this.record = record;
	}
	public ScoreShopItem getItem() {
		return item;
	}
	public void setItem(ScoreShopItem item) {
		this.item = item;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
