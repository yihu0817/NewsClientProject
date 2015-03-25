package com.warmtel.android.main.act.httpweb;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.warmtel.android.R;
import com.warmtel.android.common.configs.Logs;
import com.warmtel.android.main.html.JsonParseNews;
import com.warmtel.android.main.html.JsonParseNews.OnWeatherJsonListener;
import com.warmtel.android.main.useful.NewsInfo;
import com.warmtel.android.main.useful.jsonobj.WeatherDataObj;
import com.warmtel.android.main.useful.jsonobj.WeatherJson;
import com.warmtel.android.main.util.DateTools;

public class WeatherShowAct extends FragmentActivity  implements OnWeatherJsonListener{
	private int place_Request_code=1;
	private TextView mTitleTxt;
	private JsonParseNews mJsonParseNews;
	private TextView mDateTxt,mWeekTxt,mTemptureAreaTxt,mWeatherTxt,mWind;
	private ImageView mWeatherShowPic;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_fragment_layout);
		initView();
	}
	public void initView()
	{
		mJsonParseNews=new JsonParseNews();
		mTitleTxt=(TextView)findViewById(R.id.weather_title_txt);
		mDateTxt=(TextView)findViewById(R.id.weather_date_txt);
		mWeekTxt=(TextView)findViewById(R.id.weather_week_txt);
		mTemptureAreaTxt=(TextView)findViewById(R.id.weather_temperture_txt);
		mWeatherTxt=(TextView)findViewById(R.id.weather_weather_txt);
		mWind=(TextView)findViewById(R.id.weather_wind_txt);
		mWeatherShowPic=(ImageView)findViewById(R.id.weather_info_pic);
		
		String weather_head=NewsInfo.WEATHER_PORT;
		String weather_url=null;
		if(getIntent().getStringExtra("PlaceName")!=null)
		{
			String placeName=getIntent().getStringExtra("PlaceName");
			mTitleTxt.setText(placeName+"天气");
			weather_url=weather_head+placeName;
		}else
		{
			weather_url=weather_head+"成都";
		}
		mJsonParseNews.getWeatherListHttpConn(weather_url);
		mJsonParseNews.setOnWeatherJsonListener(this);
	}
	
	public void onBackClicked(View v) {
		finish();
	}
	public void onChangePlaceClicked(View v)
	{
		Intent intent=new Intent("scxh.intent.action.changePlace");
		startActivityForResult(intent,place_Request_code);
		finish();
	}
	@Override
	public void onWeatherJsonList(WeatherJson weatherJson) {
		// TODO Auto-generated method stub
		WeatherDataObj weatherObj=weatherJson.getData();
		Logs.d(weatherObj.getCity());
		mDateTxt.setText(weatherObj.getDateTime());
		mTemptureAreaTxt.setText(weatherObj.getMinTemp()+"~"+weatherObj.getMaxTemp());
		mWeatherTxt.setText(weatherObj.getWeather());
		mWind.setText(weatherObj.getWindDirection()+weatherObj.getWindForce());
		Date date=new Date(java.lang.System.currentTimeMillis());
		mWeekTxt.setText(DateTools.date2week(date));
		getWeatherPic(weatherObj);
	}
	
	public void getWeatherPic(WeatherDataObj weatherObj)
	{
		int imgIndex=Integer.valueOf(weatherObj.getImg_1());
		switch (imgIndex) {
		case 0:
			mWeatherShowPic.setImageResource
			
			(R.drawable.a00);
			break;
		case 1:
			mWeatherShowPic.setImageResource(R.drawable.a01);
			break;
		case 2:
			mWeatherShowPic.setImageResource(R.drawable.a02);
			break;
		case 3:
			mWeatherShowPic.setImageResource(R.drawable.a03);
			break;
		case 4:
			mWeatherShowPic.setImageResource(R.drawable.a04);
			break;
		case 5:
			mWeatherShowPic.setImageResource(R.drawable.a05);
			break;
		case 6:
			mWeatherShowPic.setImageResource(R.drawable.a06);
			break;
		case 7:
			mWeatherShowPic.setImageResource(R.drawable.a07);
			break;
		case 8:
			mWeatherShowPic.setImageResource(R.drawable.a08);
			break;
		case 9:
			mWeatherShowPic.setImageResource(R.drawable.a09);
			break;
		case 10:
			mWeatherShowPic.setImageResource(R.drawable.a10);
			break;
		case 11:
			mWeatherShowPic.setImageResource(R.drawable.a11);
			break;
		case 12:
			mWeatherShowPic.setImageResource(R.drawable.a12);
			break;
		case 13:
			mWeatherShowPic.setImageResource(R.drawable.a13);
			break;
		case 14:
			mWeatherShowPic.setImageResource(R.drawable.a14);
			break;
		case 15:
			mWeatherShowPic.setImageResource(R.drawable.a15);
			break;
		case 16:
			mWeatherShowPic.setImageResource(R.drawable.a16);
			break;
		case 17:
			mWeatherShowPic.setImageResource(R.drawable.a17);
			break;
		case 18:
			mWeatherShowPic.setImageResource(R.drawable.a18);
			break;
		case 19:
			mWeatherShowPic.setImageResource(R.drawable.a19);
			break;
		case 20:
			mWeatherShowPic.setImageResource(R.drawable.a20);
			break;
		case 21:
			mWeatherShowPic.setImageResource(R.drawable.a21);
			break;
		case 22:
			mWeatherShowPic.setImageResource(R.drawable.a22);
			break;
		case 23:
			mWeatherShowPic.setImageResource(R.drawable.a23);
			break;
		case 24:
			mWeatherShowPic.setImageResource(R.drawable.a24);
			break;
		case 25:
			mWeatherShowPic.setImageResource(R.drawable.a25);
			break;
		case 26:
			mWeatherShowPic.setImageResource(R.drawable.a26);
			break;
		case 27:
			mWeatherShowPic.setImageResource(R.drawable.a27);
			break;
		case 28:
			mWeatherShowPic.setImageResource(R.drawable.a28);
			break;
		case 29:
			mWeatherShowPic.setImageResource(R.drawable.a29);
			break;
		case 30:
			mWeatherShowPic.setImageResource(R.drawable.a30);
			break;
		case 31:
			mWeatherShowPic.setImageResource(R.drawable.a31);
			break;
		default:
			break;
		}
		
	}
	
}
