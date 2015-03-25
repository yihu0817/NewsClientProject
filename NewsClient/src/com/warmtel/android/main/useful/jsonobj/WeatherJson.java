package com.warmtel.android.main.useful.jsonobj;

public class WeatherJson {
	private String status;
	private String message;
//	private WeatherDataObj data=new WeatherDataObj();
	private WeatherDataObj data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public WeatherDataObj getData() {
		return data;
	}
	public void setData(WeatherDataObj data) {
		this.data = data;
	}

}
