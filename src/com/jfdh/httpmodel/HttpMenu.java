package com.jfdh.httpmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源模型
 * 
 * @author
 * 
 */
public class HttpMenu {

	private String id;
	private String name;
	private Integer sequence;
	private String logo;
	private String descript;
	private String url;

	private String valid;

	private List<HttpMenu> menusList = new ArrayList<HttpMenu>();

	public HttpMenu() {
		super();
	}

	public HttpMenu(String name, String url,String logo) {
		super();
		this.name = name;
		this.url = url;
		this.logo=logo;
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

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public List<HttpMenu> getMenusList() {
		return menusList;
	}

	public void setMenusList(List<HttpMenu> menusList) {
		this.menusList = menusList;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
