package com.warmtel.android.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.warmtel.android.R;

public class LeftSlidingMenuFragment extends Fragment  implements OnClickListener{
	RelativeLayout mPic,mVideo,mWeather,mMap,mMore;
	onMenuClickListener mCallback;
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback=(onMenuClickListener)activity;
			}catch(ClassCastException e)
			{
				throw new ClassCastException(activity.toString()
						+ "must implements onMenuClickListener ");
			}
	}
	
	
	
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	View v=inflater.inflate(R.layout.left_sliding_menu_layout, container,false);
	mPic=(RelativeLayout)v.findViewById(R.id.news_pic_layout);
	mVideo=(RelativeLayout)v.findViewById(R.id.news_video_layout);
	mWeather=(RelativeLayout)v.findViewById(R.id.news_weather_layout);
	mMap=(RelativeLayout)v.findViewById(R.id.news_map_layout);
	mMore=(RelativeLayout)v.findViewById(R.id.news_more_layout);
	mPic.getBackground().setAlpha(100);//透明度,0-255表示透明级别
	mVideo.getBackground().setAlpha(100);//透明度,0-255表示透明级别
	mWeather.getBackground().setAlpha(100);//透明度,0-255表示透明级别
	mMap.getBackground().setAlpha(100);//透明度,0-255表示透明级别
	mMore.getBackground().setAlpha(100);//透明度,0-255表示透明级别
	
	mPic.setOnClickListener(this);
	mVideo.setOnClickListener(this);
	mWeather.setOnClickListener(this);
	mMap.setOnClickListener(this);
	mMore.setOnClickListener(this);
	return v;
}
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	mCallback.onMenuClick(v);
}

public interface onMenuClickListener
{
	public void onMenuClick(View v);
}
}
