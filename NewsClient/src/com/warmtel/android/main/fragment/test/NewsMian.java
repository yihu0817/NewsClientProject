package com.warmtel.android.main.fragment.test;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Window;

import com.warmtel.android.R;
import com.warmtel.android.common.widget.PagerSlidingTabStripMine;

public class NewsMian extends FragmentActivity {
	private DisplayMetrics dm;
	private PagerSlidingTabStripMine mPagerSliding;
	private ViewPager mViewPager;
	//用來填充數據
	private List<Fragment> mListFragments=new ArrayList<Fragment>();
	//用來填充標題
	private List<String> mListTitles=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.news_main_layout);
		mPagerSliding=(PagerSlidingTabStripMine) findViewById(R.id.pagerSlidingTabstrip);
		mViewPager=(ViewPager)findViewById(R.id.main_viewpager);
		initFrag();
		mViewPager.setAdapter(new FragPagerAdapter(getSupportFragmentManager(), mListTitles, mListFragments));
		
		//此方法重要
		mPagerSliding.setViewPager(mViewPager);
		setTabValue();
	}
	
	protected void initFrag()
	{
		dm=getResources().getDisplayMetrics();
		mListFragments.add(new FragmentTest("学校开销1", "秋季学期展开,活动丰富。。。。1"));
		mListFragments.add(new FragmentTest("学校开销2", "秋季学期展开,活动丰富。。。。2"));
		mListFragments.add(new FragmentTest("学校开销3", "秋季学期展开,活动丰富。。。。3"));
		mListFragments.add(new FragmentTest("学校开销4", "秋季学期展开,活动丰富。。。。4"));
		mListFragments.add(new FragmentTest("学校开销5", "秋季学期展开,活动丰富。。。。5"));
		mListFragments.add(new FragmentTest("学校开销6", "秋季学期展开,活动丰富。。。。6"));
		mListFragments.add(new FragmentTest("学校开销7", "秋季学期展开,活动丰富。。。。7"));
		mListFragments.add(new FragmentTest("学校开销8", "秋季学期展开,活动丰富。。。。8"));
		
		mListTitles.add("头条");
		mListTitles.add("娱乐");
		mListTitles.add("体育");
		mListTitles.add("财经");
		mListTitles.add("科技");
		mListTitles.add("军事");
		mListTitles.add("电影");
		mListTitles.add("教育");
	}
	
	public void setTabValue()
	{
		//设置为true 均匀分配title位置
		mPagerSliding.setShouldExpand(true);
		//设置分割线为透明
		mPagerSliding.setDividerColor(Color.TRANSPARENT);
		mPagerSliding.setUnderlineHeight((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		mPagerSliding.setIndicatorHeight((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		mPagerSliding.setTextSize((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, dm));
		//设置选中字体颜色和指示器颜色相同
//		mPagerSliding.setIndicatorColor(Color.parseColor("#45c01a"));
//		mPagerSliding.setSelectedTextColor(Color.parseColor("#45c01a"));
//		mPagerSliding.setTabBackground(0);
		mPagerSliding.setIndicatorColor(0);
		mPagerSliding.setSelectedTextColor(Color.parseColor("#FFFFFF"));
//		mPagerSliding.setTabBackground(R.drawable.btn_common_normal);
//		mPagerSliding.setSelected(true);
	}
	
	public class FragPagerAdapter extends FragmentPagerAdapter{
		private List<String> mtitleLists;
		private List<Fragment> mfragLists;

		public FragPagerAdapter(FragmentManager fm,List<String> titleLists,List<Fragment> fragLists) {
			super(fm);
			mtitleLists=titleLists;
			mfragLists=fragLists;
		}

		@Override
		public Fragment getItem(int arg0) {
			return (mfragLists==null||mfragLists.size()==0)?null:mfragLists.get(arg0);
		}

		@Override
		public int getCount() {
			return mfragLists==null?0:mfragLists.size();
		}
		/*
		 * 每个页面Title
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return (mtitleLists.size()>position)?mtitleLists.get(position):"";
		}
		
		
	}

}
