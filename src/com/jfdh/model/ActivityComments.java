package com.jfdh.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 评论
 * @author Administrator
 *
 */
@Entity
@Table(name="activitycomments")
public class ActivityComments{
	@Id
	@Column(length=36)
	private String id;
	//品论内容
	@Column(columnDefinition = "varchar(500)")
	private String content;
	//
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "activityId", referencedColumnName = "id")
	private Activity details;
	//评论人
	@ManyToOne(cascade = CascadeType.DETACH, optional = true)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private User user;
	//评论时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Activity getDetails() {
		return details;
	}
	public void setDetails(Activity details) {
		this.details = details;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
