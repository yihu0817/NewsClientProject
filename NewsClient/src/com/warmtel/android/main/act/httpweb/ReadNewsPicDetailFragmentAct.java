package com.warmtel.android.main.act.httpweb;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.warmtel.android.R;
import com.warmtel.android.main.fragment.ReadNewsPicDetailFrag;
import com.warmtel.android.main.useful.jsonobj.ReadNewsImagSrcObj;

public class ReadNewsPicDetailFragmentAct extends FragmentActivity implements OnClickListener {
	private ViewPager mViewPager;
	private ImageView mBackView;
	private TextView mTitleTxt;
	private List<Fragment> mListFrag=new ArrayList<Fragment>();
	private int mPicCount;
	private String mTitle;
	private ArrayList<ReadNewsImagSrcObj> mImg;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pic_read_news_detail_viewpager_act_layout);
		init();
	}
	//初始化mViewPager
	public void init()
	{
		mViewPager=(ViewPager)findViewById(R.id.pic_detail_viewpager);
		mBackView = (ImageView)findViewById(R.id.pic_detail_headpic);
		mTitleTxt=(TextView)findViewById(R.id.pic_detail_headtitle);
		mBackView.setOnClickListener(this);
		getIntentInfo();
		setPicData2ListFrag(mListFrag);
		mViewPager.setAdapter(new FragPagerAdapter(getSupportFragmentManager(), mListFrag));
	}
	//添加Fragment入List<Fragment>
	public void setPicData2ListFrag(List<Fragment> listFrag)
	{
		for(int i=0;i<mPicCount;i++)
		{
			String picUrl=mImg.get(i).getSrc();
			String picTitle=mImg.get(i).getAlt();
		listFrag.add(ReadNewsPicDetailFrag.newInstance(picUrl,picTitle,i,mPicCount));
		System.out.println("setPicData2ListFrag"+i+">>>>>"+listFrag.size());
		}
	}
	
	public void getIntentInfo() {
		mTitle=getIntent().getStringExtra("title");
		mTitleTxt.setText(mTitle);
		mImg=getIntent().getParcelableArrayListExtra("imginfo");
		mPicCount=mImg.size();
	}

	public class FragPagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> mfragLists;

		public FragPagerAdapter(FragmentManager fm,List<Fragment> fragLists) {
			super(fm);
			mfragLists = fragLists;
		}

		@Override
		public Fragment getItem(int arg0) {
			return (mfragLists == null || mfragLists.size() == 0) ? null
					: mfragLists.get(arg0);
		}
		@Override
		public int getCount() {
			return mfragLists == null ? 0 : mfragLists.size();
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pic_detail_headpic:
			finish();
			break;
		default:
			break;
		}
	}
}
