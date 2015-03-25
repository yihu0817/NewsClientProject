package com.warmtel.android.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.common.slidingmenu.lib.SlidingMenu;
import com.common.slidingmenu.lib.app.SlidingFragmentActivity;
import com.warmtel.android.R;
import com.warmtel.android.common.configs.Logs;
import com.warmtel.android.main.LeftSlidingMenuFragment.onMenuClickListener;
import com.warmtel.android.main.fragment.MianReadNewsFragmentUpdate;
import com.warmtel.android.main.fragment.MianReadNewsFragmentUpdate.OnTitleBarClickListener;

public class NewsSlidingMain extends SlidingFragmentActivity  implements onMenuClickListener,OnTitleBarClickListener{
	//声明Menu
	private SlidingMenu menu;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_frame_layout);
		//初始化菜单
		initSlidingMenu();
	}

	/**
	 * 初始化滑动菜单
	 */
	private void initSlidingMenu()
	{
		//设置主界面视图
		MianReadNewsFragmentUpdate nmfU=new MianReadNewsFragmentUpdate();
		getSupportFragmentManager().beginTransaction().replace(R.id.content_framelayout, nmfU).commit();
		
		//设置滑动菜单属性
		menu= getSlidingMenu();//实例化
		menu.setMode(SlidingMenu.LEFT);
		setBehindContentView(R.layout.menu_frame);
		menu.setSlidingEnabled(true);
		menu.setShadowWidthRes(R.dimen.shadow_width);//设置阴影宽度
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//设置上方视图的触摸模式的值
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_layout, new LeftSlidingMenuFragment()).commit();
		//设置滑动菜单的的视图界面
		menu.setShadowDrawable(R.drawable.shadow);//设置阴影shape
		menu.setBehindOffsetRes(R.dimen.behind_offset);//根据像素的值来设置下方视图的偏移量
		menu.setBehindScrollScale(0.8f);
		menu.setFadeDegree(0.35f);//设置渐入渐出效果的值
	}

	@Override
	public void onBackPressed() {
		//点击返回键关闭滑动菜单
		if(menu.isMenuShowing())
		{
			menu.showContent();
		}else
		{
			super.onBackPressed();
		}
	}

	@Override
	public void onMenuClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.news_pic_layout:
			Toast.makeText(this, "picture",Toast.LENGTH_SHORT ).show();
			Intent intent=new Intent("scxh.intent.action.picShow");
	        startActivity(intent);
	        //如果显示的是菜单，就显示回正文
	        if(menu.isMenuShowing())
			{
				menu.showContent();
			}else
			{
				super.onBackPressed();
			}
			break;
		case R.id.news_video_layout:
			Toast.makeText(this, "onVideoNews",Toast.LENGTH_SHORT ).show();
			Intent intentVideo=new Intent("scxh.intent.action.videoShow");
	        startActivity(intentVideo);
	        //如果显示的是菜单，就显示回正文
	        if(menu.isMenuShowing())
			{
				menu.showContent();
			}else
			{
				super.onBackPressed();
			}
			break;
		case R.id.news_weather_layout:
			Toast.makeText(this, "onWeather",Toast.LENGTH_SHORT ).show();
			Intent intentWeather=new Intent("scxh.intent.action.weatherShow");
	        startActivity(intentWeather);
	        //如果显示的是菜单，就显示回正文
	        if(menu.isMenuShowing())
			{
				menu.showContent();
			}else
			{
				super.onBackPressed();
			}
			break;
		case R.id.news_map_layout:
			Toast.makeText(this, "onMap",Toast.LENGTH_SHORT ).show();
			Intent intentmap=new Intent("scxh.intent.action.locationMap");
	        startActivity(intentmap);
	        //如果显示的是菜单，就显示回正文
	        if(menu.isMenuShowing())
			{
				menu.showContent();
			}else
			{
				super.onBackPressed();
			}
			break;
		case R.id.news_more_layout:
			Toast.makeText(this, "onMore",Toast.LENGTH_SHORT ).show();
			break;
		default:
			break;
		}
	}
	@Override
	public void onTitlleClickListener(View v) {
		Logs.v("NewsSlidingMain   onTitlleClickListener");
		switch (v.getId()) {
		case R.id.titlebar_left_img:
			toggle();
			break;
		default:
			break;
		}
	}
	public void onChannelClicked(View v)
	{
		Intent intent =new Intent("scxh.intent.action.channelChange");
		startActivity(intent);
	}
}
