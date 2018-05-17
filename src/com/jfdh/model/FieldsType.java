package com.jfdh.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="FieldType")
@Table(name="fieldtype")
public class FieldsType {
	@Id
	@Column(length = 36)
	private String id;
	@Column(length = 50)
	private String name;
	
	@Column(length = 50)
	private String type;
	//排序
	private BigDecimal sequence;

	@OneToMany(mappedBy = "type", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private List<FieldValue> fields = new ArrayList<FieldValue>();

	@ManyToMany(mappedBy = "fieldTypes", cascade = CascadeType.DETACH)
	private List<Activity> activities = new ArrayList<Activity>();

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

	public List<FieldValue> getFields() {
		return fields;
	}

	public void setFields(List<FieldValue> fields) {
		this.fields = fields;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getSequence() {
		return sequence;
	}

	public void setSequence(BigDecimal sequence) {
		this.sequence = sequence;
	}
}
