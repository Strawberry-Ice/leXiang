package com.jfdh.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="shopitemtype")
public class ShopItemType {
	@Id
	@Column(length=36)
	private String id;
	@Column(length=50)
	private String name;
	private BigDecimal ratio;
	private boolean lottery ;
	private boolean constant;
	@OneToMany(mappedBy = "type", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<ScoreShopItem> scoreShopItems = new ArrayList<ScoreShopItem>();

	public boolean isLottery() {
		return lottery;
	}

	public void setLottery(boolean lottery) {
		this.lottery = lottery;
	}

	public boolean isConstant() {
		return constant;
	}

	public void setConstant(boolean constant) {
		this.constant = constant;
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

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

	public List<ScoreShopItem> getScoreShopItems() {
		return scoreShopItems;
	}

	public void setScoreShopItems(List<ScoreShopItem> scoreShopItems) {
		this.scoreShopItems = scoreShopItems;
	}

	
}
