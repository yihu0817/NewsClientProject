package com.warmtel.android.main.useful.jsonobj;

import java.util.ArrayList;
import java.util.List;

public class ProvinceInfo {
	private String province;
	private List<CityInfo> city=new ArrayList<CityInfo>();
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public List<CityInfo> getCity() {
		return city;
	}
	public void setCity(List<CityInfo> city) {
		this.city = city;
	}

}
