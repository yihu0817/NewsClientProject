package com.warmtel.android.main.useful.jsonobj;

import java.util.ArrayList;
import java.util.List;

public class NewsInfoUseObj {
	private String body;
	private String picUrl;
	private String title;
	private String source;
	private String ptime;
	private int picCount;
	private List<ReadNewsImagSrcObj> img=new ArrayList<ReadNewsImagSrcObj>(); 
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getPtime() {
		return ptime;
	}
	public void setPtime(String ptime) {
		this.ptime = ptime;
	}
	public int getPicCount() {
		return picCount;
	}
	public void setPicCount(int picCount) {
		this.picCount = picCount;
	}
	public List<ReadNewsImagSrcObj> getImg() {
		return img;
	}
	public void setImg(List<ReadNewsImagSrcObj> img) {
		this.img = img;
	}
}
