package com.warmtel.android.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.warmtel.android.R;
import com.warmtel.android.common.configs.ListPageConfig;

public class PullRefreshListView extends ListView implements OnScrollListener {
	private int pageItemcount = ListPageConfig.pageItemCount;
	private boolean isLastRow;
	private Context mContext;
	private View footerContainer;
	private View mPressMoreView;
	private View mScrollMoreView;
	private ProgressBar mPressProgressBar;
	private onLoadListener loadListener;
	/**
	 * SCROLL 滚动加载
	 * PRESS 按下加载 
	 */
	public enum PullType {
		PRESS, SCROLL
	}

	private PullType pullType = PullType.SCROLL;
	
	public void setOnLoadListener(onLoadListener loadListener) {
		this.loadListener = loadListener;
	}

	public PullRefreshListView(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public PullRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	/**
	 * 添加Footer
	 */
	public void init() {
		setOnScrollListener(this);
		footerContainer = LayoutInflater.from(mContext).inflate(
				R.layout.listpage_footer_scroll_layout, null);
		mPressMoreView = footerContainer.findViewById(R.id.press_more_layout);
		mScrollMoreView = footerContainer.findViewById(R.id.scroll_more_layout);
		mPressProgressBar = (ProgressBar) footerContainer.findViewById(R.id.listpage_foot_pree_progressbar);
		
		mScrollMoreView.setVisibility(View.VISIBLE);
		mPressMoreView.setVisibility(View.GONE);
		
		addFooterView(footerContainer);
		
		mPressMoreView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(pullType == PullType.PRESS){
					mPressProgressBar.setVisibility(View.VISIBLE);
					loadListener.onLoadMore();
				}
			}
		});
	}
	/**
	 * 设置加载方式
	 * 滚动加载
	 * 按下加载
	 * @param pt
	 */
	public void setPullType(PullType pt){
		pullType = pt;
		if(pullType == PullType.PRESS){
			mScrollMoreView.setVisibility(View.GONE);
			mPressMoreView.setVisibility(View.VISIBLE);
		}else{
			mPressMoreView.setVisibility(View.GONE);
			mScrollMoreView.setVisibility(View.VISIBLE);
		}
	}
	public void onLoadMoreFinish(){
		mPressProgressBar.setVisibility(View.GONE);
		
	}
	/**
	 * 移除Footer
	 */
	public void removeFooter() {
		removeFooterView(footerContainer);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (pullType == PullType.SCROLL && isLastRow
				&& scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
			isLastRow = false;
			loadListener.onLoadMore();
			// view.setStackFromBottom(true);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// 判断是否是一页中的最后一项且 第一页不用加载
		if (firstVisibleItem + visibleItemCount == totalItemCount
				&& totalItemCount >= (pageItemcount + 1)) {
			isLastRow = true;
		}
	}

	// 回调接口 需要在此类中声明，在使用此类的Activity中实现接口
	public interface onLoadListener {
		public void onLoadMore();
	}

}
