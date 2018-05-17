package com.jfdh.httpmodel;

import java.util.List;
import java.util.Map;

/**
 * 后台向前台返回JSON，用于easyui的datagrid
 * 
 * @author
 * 
 */
public class DataResponse implements java.io.Serializable {

	private int total;// 总页数
	private int page;//当前页
	private List rows;// 每行记录
	
	//查询出的记录数
    private int records;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}  
}
