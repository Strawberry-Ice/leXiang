package com.jfdh.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 报名字段
 * @author Administrator
 *
 */
@Entity
@Table(name="fieldvalue")
public class FieldValue {
	@Id
	@Column(length=36)
	private String id;
	private String code;
	private String description;
	
	@ManyToOne(cascade = CascadeType.PERSIST, optional = true)
	@JoinColumn(name = "fieldTypeId", referencedColumnName = "id")
	private FieldsType type;
	@ManyToOne(cascade = CascadeType.PERSIST, optional = true)
	@JoinColumn(name = "applicationId", referencedColumnName = "id")
	private Application application;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public FieldsType getType() {
		return type;
	}
	public void setType(FieldsType type) {
		this.type = type;
	}
}
