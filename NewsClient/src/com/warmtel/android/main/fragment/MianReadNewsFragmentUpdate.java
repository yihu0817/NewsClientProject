package com.warmtel.android.main.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.warmtel.android.R;
import com.warmtel.android.common.configs.Logs;
import com.warmtel.android.common.widget.MyOneCustomTitleBar;
import com.warmtel.android.common.widget.PagerSlidingTabStripMine;
import com.warmtel.android.main.useful.NewsInfo;
/**
 * update信息:
 * 1.2014.10.24 封装了添加Fragment和Title的方法
 * 2.2014.10.24 自定义控件替换MyOneCustomTitleBar之前的Title布局
 * @author Administrator
 *
 */
public class MianReadNewsFragmentUpdate extends Fragment {
	private DisplayMetrics dm;
	private PagerSlidingTabStripMine mPagerSliding;
	private MyOneCustomTitleBar mCustomTitleBar;
	private ViewPager mViewPager;
	private ImageView mStartImg;
	private Handler mHandler=new Handler();
	// 用來填充數據
	private List<Fragment> mListFragments = new ArrayList<Fragment>();
	// 用來填充標題
	private List<String> mListTitles = new ArrayList<String>();
	OnTitleBarClickListener mCallback;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnTitleBarClickListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ "must implements OnTitleBarClickListener ");
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Logs.v("MianReadNewsFragmentUpdate   onCreateView");
		View v = inflater.inflate(R.layout.read_news_fragment_mycustom_layout,
				container, false);
		mPagerSliding = (PagerSlidingTabStripMine) v
				.findViewById(R.id.pagerSlidingTabstrip);
		mCustomTitleBar = (MyOneCustomTitleBar) v.findViewById(R.id.readnews_custom_titlebar);
		mViewPager = (ViewPager) v.findViewById(R.id.main_viewpager);
		initFrag();
		mViewPager.setAdapter(new FragPagerAdapter(getActivity()
				.getSupportFragmentManager(), mListTitles, mListFragments));
		// 此方法重要
		mPagerSliding.setViewPager(mViewPager);
		mCustomTitleBar.setOnCustomClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Logs.v("MianReadNewsFragmentUpdate   onClick");
				mCallback.onTitlleClickListener(v);
			}
		});
		mStartImg=(ImageView)v.findViewById(R.id.start_pic_img);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mStartImg.setVisibility(View.GONE);
			}
		}, 2000);
		setTabValue();
		return v;
	}

	protected void initFrag() {
		dm = getResources().getDisplayMetrics();
		FragmentAdd(mListFragments, mListTitles, NewsInfo.READ_STYLE, NewsInfo.HTTP_PORT, NewsInfo.TITLE_S);
	}
	
	/**
	 * update:2014.10.24封装了添加Fragment和Title的方法
	 * @param listFrag
	 * @param listTitle
	 * @param readStyle
	 * @param httpPort
	 * @param titles
	 */
	//数组长度必须要一样
	public void FragmentAdd(List<Fragment> listFrag,List<String> listTitle,int[] readStyle,String[] httpPort,String[] titles)
	{
		for(int i=0;i<readStyle.length;i++)
		{
			listFrag.add(ReadNewsFragUpdatePicasso.newInstance(httpPort[i], readStyle[i]));
			listTitle.add(titles[i]);
		}
	}

	public void setTabValue() {
		// 设置为true 均匀分配title位置
		mPagerSliding.setShouldExpand(true);
		// 设置分割线为透明
		mPagerSliding.setDividerColor(Color.TRANSPARENT);
		mPagerSliding.setUnderlineHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		mPagerSliding.setIndicatorHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		mPagerSliding.setTextSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 16, dm));
		// 设置选中字体颜色和指示器颜色相同
		mPagerSliding.setIndicatorColor(0);
		mPagerSliding.setSelectedTextColor(Color.parseColor("#FFFFFF"));
	}

	public class FragPagerAdapter extends FragmentPagerAdapter {
		private List<String> mtitleLists;
		private List<Fragment> mfragLists;

		public FragPagerAdapter(FragmentManager fm, List<String> titleLists,
				List<Fragment> fragLists) {
			super(fm);
			mtitleLists = titleLists;
			mfragLists = fragLists;
		}

		@Override
		public Fragment getItem(int arg0) {
			return (mfragLists == null || mfragLists.size() == 0) ? null: mfragLists.get(arg0);
		}

		@Override
		public int getCount() {
			return mfragLists == null ? 0 : mfragLists.size();
		}
		/*
		 * 每个页面Title
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return (mtitleLists.size() > position) ? mtitleLists.get(position): "";
		}
	}
//	@Override
//	public void onClick(View v) {
//		Logs.v("MianReadNewsFragmentUpdate   onClick");
//			mCallback.onTitlleClickListener(v);
//	}
	
	public interface OnTitleBarClickListener 
	{
		public void onTitlleClickListener(View v);
	}
	
	
}
