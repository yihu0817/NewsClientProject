package com.warmtel.android.main.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
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
import com.warmtel.android.common.widget.PagerSlidingTabStripMine;
import com.warmtel.android.main.useful.NewsInfo;
/**
 * update信息:
 * 1.2014.10.24 封装了添加Fragment和Title的方法
 * 2.2014.10.24 自定义控件替换MyOneCustomTitleBar之前的Title布局
 * @author Administrator
 *
 */
public class PicNewsFragment extends Fragment  implements OnClickListener{
	private DisplayMetrics dm;
	private PagerSlidingTabStripMine mPagerSliding;
	private ViewPager mViewPager;
	private ImageView mBackImg;
	// 用來填充數據
	private List<Fragment> mListFragments = new ArrayList<Fragment>();
	// 用來填充標題
	private List<String> mListTitles = new ArrayList<String>();
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.pic_news_fragment_layout,
				container, false);
		mPagerSliding = (PagerSlidingTabStripMine) v
				.findViewById(R.id.pagerSlidingTabstrip);
		mViewPager = (ViewPager) v.findViewById(R.id.pic_news_viewpager);
		mBackImg=(ImageView)v.findViewById(R.id.pic_news_title_headpic);
		mBackImg.setOnClickListener(this);
		initFrag();
		mViewPager.setAdapter(new FragPagerAdapter(getActivity()
				.getSupportFragmentManager(), mListTitles, mListFragments));
		// 此方法重要
		mPagerSliding.setViewPager(mViewPager);
		
		//会导致ID获取错误，所以单击图片会出现不对应的图片集,不建议使用
//		mViewPager.setPageTransformer(true, new DepthPageTransformer());//设置为潜藏型动画  
//		mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());//设置为放大型动画
		setTabValue();
		return v;
	}

	protected void initFrag() {
		dm = getResources().getDisplayMetrics();
		PicFragmentAdd(mListFragments, mListTitles, NewsInfo.CHANNEL_S,NewsInfo.PIC_TITLE_S);
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
	public void PicFragmentAdd(List<Fragment> listFrag,List<String> listTitle,String[] channel,String[] titles)
	{
		for(int i=0;i<channel.length;i++)
		{
			listFrag.add(PicNewsFragPicasso.newInstance(NewsInfo.getRequestUrl(channel[i])));
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
			return (mfragLists == null || mfragLists.size() == 0) ? null
					: mfragLists.get(arg0);
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
			return (mtitleLists.size() > position) ? mtitleLists.get(position)
					: "";
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pic_news_title_headpic:
			getActivity().finish();
			break;
		default:
			break;
		}
	}
	
}
