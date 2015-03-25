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

import com.warmtel.android.R;
import com.warmtel.android.common.anim.ZoomOutPageTransformer;
import com.warmtel.android.main.fragment.PicDetailFrag;
import com.warmtel.android.main.html.JsonParseNews;
import com.warmtel.android.main.html.JsonParseNews.OnPicDetailListener;
import com.warmtel.android.main.useful.NewsInfo;
import com.warmtel.android.main.useful.jsonobj.PicModelObj;

public class PicDetailFragmentAct extends FragmentActivity implements OnClickListener,OnPicDetailListener {
	private ViewPager mViewPager;
	private ImageView mBackView;
	private JsonParseNews mJsonParseNews;
	private List<Fragment> mListFrag=new ArrayList<Fragment>();
	private int mPicCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pic_news_detail_viewpager_act_layout);
		init();
	}
	//初始化mViewPager
	public void init()
	{
		mViewPager=(ViewPager)findViewById(R.id.pic_detail_viewpager);
		mViewPager.setOffscreenPageLimit(5);//设置预先加载页,加载了5页
		mBackView = (ImageView)findViewById(R.id.pic_detail_headpic);
		mBackView.setOnClickListener(this);
		getPicDetailResponse();
	}
	//添加Fragment入List<Fragment>
	public void setData2ListFrag(List<Fragment> listFrag)
	{
		for(int i=0;i<mPicCount;i++)
		{
		listFrag.add(PicDetailFrag.newInstance(i));
		System.out.println("setData2ListFrag"+i+">>>>>"+listFrag.size());
		}
	}
	
	public void getPicDetailResponse() {
		mJsonParseNews = new JsonParseNews();
		String id =getIntent().getStringExtra("ID");
		String picDetailJsonUrl = NewsInfo.getPicDetailUrl(id);
		mJsonParseNews.getPicDetailHttpConn(picDetailJsonUrl);
		mJsonParseNews.setOnPicDetailListener(this);
	}
	
	@Override
	public void onPicDetailList(List<PicModelObj> picDetailList) {
		mPicCount=picDetailList.size();
		//添加Fragment
		setData2ListFrag(mListFrag);
		mViewPager.setAdapter(new FragPagerAdapter(getSupportFragmentManager(), mListFrag));
		mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());//设置为放大型动画
		
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
