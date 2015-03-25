package com.warmtel.android.main.useful;

import android.view.View;

public class ViewPagerInfo {
	private View View;
	private String imgUrl;
	private String title;
	
	public ViewPagerInfo(android.view.View view, String imgUrl,String title) {
		super();
		View = view;
		this.imgUrl = imgUrl;
		this.title=title;
	}
	public View getView() {
		return View;
	}
	public void setView(View view) {
		View = view;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
