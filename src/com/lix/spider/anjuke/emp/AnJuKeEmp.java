package com.lix.spider.anjuke.emp;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;

public class AnJuKeEmp implements Serializable {

	private static final long serialVersionUID = 1577303225938149923L;

	private String name;
	private String phone;
	private String company;
	private String proLevel;
	private String fetchUrl;
	private String shopUrl;
	private Date fetchDate;
	private int htmlLength ;

	public AnJuKeEmp() {

	}

	public AnJuKeEmp(String name, String phone, String company,
			String proLevel, String fetchUrl, String shopUrl,int htmlLenth) {
		this.name = name;
		this.phone = phone;
		this.company = company;
		this.proLevel = proLevel;
		this.fetchUrl = fetchUrl;
		this.shopUrl = shopUrl;
		this.htmlLength = htmlLenth ;
		this.fetchDate = new Date();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFetchUrl() {
		return fetchUrl;
	}

	public void setFetchUrl(String fetchUrl) {
		this.fetchUrl = fetchUrl;
	}

	public Date getFetchDate() {
		return fetchDate;
	}

	public void setFetchDate(Date fetchDate) {
		this.fetchDate = fetchDate;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getProLevel() {
		return proLevel;
	}

	public void setProLevel(String proLevel) {
		this.proLevel = proLevel;
	}

	public String toString() {
		return JSONArray.toJSONString(this);
	}

	public int getHtmlLength() {
		return htmlLength;
	}

	public void setHtmlLength(int htmlLength) {
		this.htmlLength = htmlLength;
	}

}
