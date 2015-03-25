package com.warmtel.android.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.warmtel.android.R;
import com.warmtel.android.common.configs.ListPageConfig;

public class VideoPullRefreshListView extends ListView implements OnScrollListener {
	private int pageItemcount=ListPageConfig.videoPageItemCount;
	private boolean isLastRow;
	private Context mContext;
	private View footerContainer;
	private onLoadListener loadListener;

	public void setOnLoadListener(onLoadListener loadListener) {
		this.loadListener = loadListener;
	}
	public VideoPullRefreshListView(Context context) {
		super(context);
		mContext=context;
		init();
	}

	public VideoPullRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		init();
	}
	/**
	 * 添加Footer
	 */
	public void init()
	{
		setOnScrollListener(this);
		footerContainer=LayoutInflater.from(mContext).inflate(R.layout.listpage_footer_scroll_layout, null);
	    addFooterView(footerContainer);
	}
	/**
	 * 移除Footer
	 */
	public void removeFooter()
	{
		removeFooterView(footerContainer);
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(isLastRow && scrollState==AbsListView.OnScrollListener.SCROLL_STATE_IDLE)
		{
			isLastRow=false;
			loadListener.onLoadMore();
//			view.setStackFromBottom(true);
		}
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
//判断是否是一页中的最后一项且 第一页不用加载
		if(firstVisibleItem+visibleItemCount==totalItemCount && totalItemCount>=(pageItemcount+1))
		{
			isLastRow=true;
		}
	}
	//回调接口 需要在此类中声明，在使用此类的Activity中实现接口
	public interface onLoadListener
	{
		public void onLoadMore();
	}

}
