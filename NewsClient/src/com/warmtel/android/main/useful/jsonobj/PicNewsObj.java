package com.warmtel.android.main.useful.jsonobj;

/**
 * 
 * @author Administrator
 *1.list[Object]数组
 *2.postion1位置为一个对象,包含:
 *String id
 *String title
 *String picUrl
 *
 */
public class PicNewsObj {
	private String id;
	private String picTitle;
	private String picUrl;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPicTitle() {
		return picTitle;
	}
	public void setPicTitle(String picTitle) {
		this.picTitle = picTitle;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}
