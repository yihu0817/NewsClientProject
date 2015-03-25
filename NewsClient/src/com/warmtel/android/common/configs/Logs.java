package com.warmtel.android.common.configs;

import android.util.Log;

public class Logs {
	private static final String tag="TAG";
	private static boolean DEBUG=true;//true ���԰汾 false ��ʽ���а汾	
	public static void v(String msg)
	{
		if(DEBUG)
		Log.v(tag, msg);
	}
	public static void d(String msg)
	{
		if(DEBUG)
		Log.d(tag, msg);
	}
	public static void i(String msg)
	{
		if(DEBUG)
		Log.i(tag, msg);
	}
	public static  void w(String msg)
	{
		if(DEBUG)
		Log.w(tag, msg);
	}
	public static  void e(String msg)
	{
		if(DEBUG)
		Log.e(tag, msg);		
	}
	
	
	
	

}
