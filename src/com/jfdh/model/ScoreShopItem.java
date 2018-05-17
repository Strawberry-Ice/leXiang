package com.jfdh.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jfdh.util.CustomDateSerializer;

/**
 * 积分池 居委会，管理员many-to-one 社区居民one-to-one
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name="scoreshopitem")
public class ScoreShopItem {
	@Id
	@Column(length = 36)
	private String id;
	@Column(length = 50)
	@NotBlank (message="礼品名称不能为空！！")
	private String name;
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "orgId", referencedColumnName = "id")
	private Org org;
	
	@JsonIgnore
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name="description",columnDefinition = "TEXT", nullable = true)
	private String desc;
	@JsonIgnore
	@NotNull(message="礼品价格不能为空！！")
	@DecimalMin(value="0.01",message="礼品价格不能为空！！")
	private BigDecimal price;
	@NotNull(message="礼品数量不能为空！！")
	@Min(value=1,message="礼品数量不能小于1个！！")
	private Integer num;
	@JsonIgnore
	private Integer restNum;
	private Integer score;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)  
	private Date createDate;
	@JsonIgnore
	private BigDecimal ratio;
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "shopItemTypeId", referencedColumnName = "id")
	private ShopItemType type;
	// 上传商品图片地址
	@Column(columnDefinition = "varchar(500)")
	private String logo;
	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiry;
	@JsonIgnore
	@OneToMany(mappedBy = "item", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<ScoreBalanceRecord> itemScoreBalanceRecords = new ArrayList<ScoreBalanceRecord>();
	@JsonIgnore
	@OneToMany(mappedBy = "scoreShop", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)

	private List<ScoreExchangeRecord> scoreShopScoreBalanceRecords = new ArrayList<ScoreExchangeRecord>();
	
	private boolean status;
	
	
	@NotNull(message="兑换有效天数！！")
	private int day;
	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

	public Integer getRestNum() {
		return restNum;
	}

	public void setRestNum(Integer restNum) {
		this.restNum = restNum;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

	public ShopItemType getType() {
		return type;
	}

	public void setType(ShopItemType type) {
		this.type = type;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<ScoreBalanceRecord> getItemScoreBalanceRecords() {
		return itemScoreBalanceRecords;
	}

	public void setItemScoreBalanceRecords(
			List<ScoreBalanceRecord> itemScoreBalanceRecords) {
		this.itemScoreBalanceRecords = itemScoreBalanceRecords;
	}

	public List<ScoreExchangeRecord> getScoreShopScoreBalanceRecords() {
		return scoreShopScoreBalanceRecords;
	}

	public void setScoreShopScoreBalanceRecords(
			List<ScoreExchangeRecord> scoreShopScoreBalanceRecords) {
		this.scoreShopScoreBalanceRecords = scoreShopScoreBalanceRecords;
	}

}
