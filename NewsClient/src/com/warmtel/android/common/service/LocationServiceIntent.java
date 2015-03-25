package com.warmtel.android.common.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.support.v4.app.NotificationCompat.Builder;

import com.warmtel.android.R;

public class LocationServiceIntent extends IntentService {
	private Notification mNotif;
	private NotificationManager mNotifyManager;
	public void initNotif()
	{
		 int id=100;
		mNotifyManager=(NotificationManager)getSystemService(Service.NOTIFICATION_SERVICE);
		mNotif=new Builder(this).setSmallIcon(R.drawable.weather_local_icon_noraml)
				.setContentTitle("定位成功").setContentText("0000text").build();
		mNotifyManager.notify(id, mNotif);
	}

	public LocationServiceIntent(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

}
