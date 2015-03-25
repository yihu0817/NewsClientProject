package com.warmtel.android.main.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.warmtel.android.R;
import com.warmtel.android.common.configs.Logs;
import com.warmtel.android.common.widget.PicPullRefreshListView;
import com.warmtel.android.common.widget.PicPullRefreshListView.onLoadListener;
import com.warmtel.android.main.html.JsonParseNews;
import com.warmtel.android.main.html.JsonParseNews.OnPicNewsListener;
import com.warmtel.android.main.useful.ViewPagerInfo;
import com.warmtel.android.main.useful.jsonobj.PicNewsObj;
/**
 * update信息:
 * 1.2014.10.24 在Adapter中使用了Picasso来缓存图片
 * 2.2014.10.24 实现了SliderLayout里面的TextSliderView的点击监听
 * 3.2014.10.25 取消了OnArticleOpenListener接口，
 *    because ListView可以直接在Fragment里相应点击事件
 * 
 * @author Administrator
 *
 */
public class PicNewsFragPicasso extends ListFragment implements OnPicNewsListener, onLoadListener, OnItemClickListener,
		OnRefreshListener{

	private int mCurrentPage =1;
	private JsonParseNews mJsonParseNews;
	private PicNewsAdapter mpicNewsAdapter;
	private PicPullRefreshListView mListView;
	List<ViewPagerInfo> mViewList = new ArrayList<ViewPagerInfo>();
	private String mChannelUrl;
	private String mUrlHead;
	private SwipeRefreshLayout mSwipeRefresh;// 下拉刷新Google官方控件

	// 封装的构造方法
	public static PicNewsFragPicasso newInstance(String channelUrl) {
		PicNewsFragPicasso picnewsFrag = new PicNewsFragPicasso();
		Bundle args = new Bundle();
		args.putString("channel", channelUrl);
		picnewsFrag.setArguments(args);
		return picnewsFrag;
	}

	// 第一个执行
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mJsonParseNews = new JsonParseNews();
		mpicNewsAdapter = new PicNewsAdapter(getActivity());
	}

	// 第二个执行
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		mUrlHead=bundle.getString("channel");
		mChannelUrl = mUrlHead+mCurrentPage;
		Logs.e("onCreate");
	}

	// 第三个执行
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.pic_news_child_layout,
				container, false);
		// 一定要在onExcute外执行啊，不然会重复跳到第一条
		initViewPager(inflater, v);
		mListView.setAdapter(mpicNewsAdapter);
		/**
		 * 网络取数据
		 */
		// 注册监听
		mJsonParseNews.getPicListHttpConn(mChannelUrl);
		mJsonParseNews.setOnPicNewsListener(this);

		Logs.e("onCreateView");
		return v;
	}

	// 第四个执行
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Logs.e("onActivityCreated");

	}

	public void initViewPager(LayoutInflater inflater, View v) {
		mListView = (PicPullRefreshListView) v.findViewById(android.R.id.list);
		// 实例化下拉刷新控件
		mSwipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefresh);
		// 设置下拉时显示的颜色
		mSwipeRefresh.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwipeRefresh.setOnRefreshListener(this);
		mListView.setOnItemClickListener(this);
	}
/**
 * 新闻适配器
 * @author Administrator
 */
	class PicNewsAdapter extends BaseAdapter {
		List<PicNewsObj> picList= new ArrayList<PicNewsObj>();
		LayoutInflater inflater;
		Context mContext;

		public PicNewsAdapter(Context context) {
			mContext = context;
			inflater = LayoutInflater.from(context);
		}

		// 封装方法
		public void addDatas(int CurrentPage, List<PicNewsObj> list) {
			// 用mCurrentPage判断是set数据 还是添加数据
			if (CurrentPage == 1) {
				setData(list);
			} else {
				addData(list);
			}
		}

		public void setData(List<PicNewsObj> list) {
			picList = list;
			notifyDataSetChanged();
		}

		public void addData(List<PicNewsObj> list) {
			if (list != null) {
				picList.addAll(list);
			}
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return picList.size();
		}

		@Override
		public Object getItem(int position) {
			return picList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = inflater.inflate(
						R.layout.pic_news_child_item_layout, null);
				viewHolder.imageShow=(ImageView)convertView.findViewById(R.id.pic_news_child_show_pic);
				viewHolder.titlePic=(TextView)convertView.findViewById(R.id.pic_news_child_show_title);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			PicNewsObj picObj = (PicNewsObj) getItem(position);
			viewHolder.titlePic.setText(picObj.getPicTitle());
			
			String picUrl=picObj.getPicUrl().replace("auto", "854x480x75x0x0x3");
			// 第三方方法
			Picasso.with(mContext).load(picUrl)
					.into(viewHolder.imageShow);
			Logs.e("ReadNewsAdapter>>>>>>>>>>>>getView");
			return convertView;
		}
		class ViewHolder {
			ImageView imageShow;
			TextView titlePic;
		}
	}
	@Override
	public void onLoadMore() {
		Logs.e("onLoadMore");
		int num=mCurrentPage++;
		if(num==2)
		{
		mChannelUrl = mUrlHead+num;
		mJsonParseNews.getPicListHttpConn(mChannelUrl);
	     }else
	     {
	    	 Toast.makeText(getActivity(), "亲,已经没有图片新闻啦!", Toast.LENGTH_SHORT).show();
	    	 mListView.removeFooter();
	     }
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		PicNewsObj picObj =(PicNewsObj)parent.getAdapter().getItem(position);
	        Toast.makeText(getActivity(),picObj.getId()+">>", Toast.LENGTH_SHORT).show();
//	        Intent intent=new Intent("scxh.intent.action.picDetail");
	        
	        Intent intent=new Intent("scxh.intent.action.picDetailFragAct");
	        intent.putExtra("ID", picObj.getId());
	        startActivity(intent);
	}

	// 记得要设置监听
	// mSwipeRefresh.setOnRefreshListener(this);
	@Override
	public void onRefresh() {
		Logs.v("onRefresh>>> 下拉刷新开始");
		Toast.makeText(getActivity(), "下拉刷新", Toast.LENGTH_SHORT).show();
		mCurrentPage = 1;
		mChannelUrl=mUrlHead+mCurrentPage;
		Logs.v("testURLAdd>>> 下拉刷新的数据加载开始");
		mJsonParseNews.getPicListHttpConn(mChannelUrl);
		mSwipeRefresh.setRefreshing(false);
		Logs.v("onRefresh>>> 下拉刷新完成");
	}
	@Override
	public void onPicNewsList(List<PicNewsObj> picList) {
		// TODO Auto-generated method stub
		// 设置监听
		Logs.e("onPicNewsList");
				mListView.setOnLoadListener(this);
				mListView.setOnItemClickListener(this);
				mpicNewsAdapter.addDatas(mCurrentPage,picList);
	}
}
