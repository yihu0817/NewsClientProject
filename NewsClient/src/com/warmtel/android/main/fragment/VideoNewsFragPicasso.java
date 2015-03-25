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
import com.warmtel.android.common.widget.VideoPullRefreshListView;
import com.warmtel.android.common.widget.VideoPullRefreshListView.onLoadListener;
import com.warmtel.android.main.html.JsonParseNews;
import com.warmtel.android.main.html.JsonParseNews.OnVideoNewsListener;
import com.warmtel.android.main.useful.NewsInfo;
import com.warmtel.android.main.useful.ViewPagerInfo;
import com.warmtel.android.main.useful.jsonobj.VideoModelObj;
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
public class VideoNewsFragPicasso extends ListFragment implements OnVideoNewsListener, onLoadListener, OnItemClickListener,
		OnRefreshListener{

	private int mCurrentPage = NewsInfo.sPageNo;
	private JsonParseNews mJsonParseNews;
	private VideoNewsAdapter mVideoNewsAdapter;
	private VideoPullRefreshListView mListView;
	List<ViewPagerInfo> mViewList = new ArrayList<ViewPagerInfo>();
	private String mPortUrl;
	private String mPort;
	private SwipeRefreshLayout mSwipeRefresh;// 下拉刷新Google官方控件

	// 封装的构造方法
	public static VideoNewsFragPicasso newInstance(String port) {
		VideoNewsFragPicasso videonewsFrag = new VideoNewsFragPicasso();
		Bundle args = new Bundle();
		args.putString("port", port);
		videonewsFrag.setArguments(args);
		return videonewsFrag;
	}
	public static VideoNewsFragPicasso newInstance(String portUrl,String port) {
		VideoNewsFragPicasso videonewsFrag = new VideoNewsFragPicasso();
		Bundle args = new Bundle();
		args.putString("portUrl", portUrl);
		args.putString("port", port);
		videonewsFrag.setArguments(args);
		return videonewsFrag;
	}
	// 第一个执行
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mJsonParseNews = new JsonParseNews();
		mVideoNewsAdapter = new VideoNewsAdapter(getActivity());
	}

	// 第二个执行
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
//		mPortUrl = bundle.getString("portUrl");
		mPort=bundle.getString("port");
		mPortUrl=NewsInfo.getVideoUrl(mPort, mCurrentPage);
		Logs.e("onCreate");
	}

	// 第三个执行
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.video_news_child_layout,
				container, false);
		// 一定要在onExcute外执行啊，不然会重复跳到第一条
		initViewPager(inflater, v);
		mListView.setAdapter(mVideoNewsAdapter);
		/**
		 * 网络取数据
		 */
		// 注册监听
		mJsonParseNews.getVedioListHttpConn(mPortUrl,mPort);
		mJsonParseNews.setOnVideoNewsListener(this);
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
		mListView = (VideoPullRefreshListView) v.findViewById(android.R.id.list);
		// 实例化下拉刷新控件
		mSwipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefresh);
		// 设置下拉时显示的颜色
		mSwipeRefresh.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwipeRefresh.setOnRefreshListener(this);
	}
/**
 * 新闻适配器
 * @author Administrator
 */
	class VideoNewsAdapter extends BaseAdapter {
		List<VideoModelObj> videoList= new ArrayList<VideoModelObj>();
		LayoutInflater inflater;
		Context mContext;

		public VideoNewsAdapter(Context context) {
			mContext = context;
			inflater = LayoutInflater.from(context);
		}
		// 封装方法
		public void addDatas(int CurrentPage, List<VideoModelObj> list) {
			// 用mCurrentPage判断是set数据 还是添加数据
			if (CurrentPage == 0) {
				setData(list);
			} else {
				addData(list);
			}
		}

		public void setData(List<VideoModelObj> list) {
			videoList = list;
			notifyDataSetChanged();
		}

		public void addData(List<VideoModelObj> list) {
			if (list != null) {
				videoList.addAll(list);
			}
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return videoList.size();
		}
		@Override
		public Object getItem(int position) {
			return videoList.get(position);
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
				convertView = inflater.inflate(R.layout.video_news_child_item_layout, null);
				viewHolder.videoPicShow=(ImageView)convertView.findViewById(R.id.vedio_news_child_show_pic);
				viewHolder.videotitle=(TextView)convertView.findViewById(R.id.video_news_child_show_title);
				viewHolder.videoTime=(TextView)convertView.findViewById(R.id.video_news_child_show_time);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			VideoModelObj videoModelObj = (VideoModelObj) getItem(position);
			viewHolder.videotitle.setText(videoModelObj.getTitle());
			// 第三方方法
			Picasso.with(mContext).load(videoModelObj.getCover()).into(viewHolder.videoPicShow);
			
			//计算时长
			int totalSecond=videoModelObj.getLength();
			int min=totalSecond/60;
			int second=totalSecond%60;
			if(min<10)
			{
				if(second<10)
				{
					viewHolder.videoTime.setText("0"+min+":"+"0"+second);
				}else
				{
					viewHolder.videoTime.setText("0"+min+":"+second);
				}
			}else
			{
				if(second<10)
				{
					viewHolder.videoTime.setText(min+":"+"0"+second);
				}else
				{
					viewHolder.videoTime.setText(min+":"+second);
				}
			}
			
			Logs.e("VideoNewsAdapter>>>>>>>>>>>>getView");
			return convertView;
		}
		class ViewHolder {
			ImageView videoPicShow;
			TextView videotitle;
			TextView videoTime;
		}
	}
	@Override
	public void onLoadMore() {
		Logs.e("onLoadMore");
		mCurrentPage++;
		//要在前面调用方法重新给新的地址
		mPortUrl=NewsInfo.getVideoUrl(mPort, mCurrentPage);
		mJsonParseNews.getVedioListHttpConn(mPortUrl,mPort);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		VideoModelObj videoModelObj =(VideoModelObj)parent.getAdapter().getItem(position);
	        Toast.makeText(getActivity(),videoModelObj.getTitle()+">>", Toast.LENGTH_SHORT).show();
	        enterVideoAct(videoModelObj);
	}
	public void enterVideoAct(VideoModelObj videoModelObj)
	{
		Intent  intent =new Intent("scxh.intent.action.videoPlayer");
		if(videoModelObj.getMp4Hd_url()!=null)
		{
			intent.putExtra("playUrl",videoModelObj.getMp4Hd_url());
		}else
		{
			intent.putExtra("playUrl",videoModelObj.getMp4_url());
		}
		intent.putExtra("videoName",videoModelObj.getTitle());
		startActivity(intent);
	}

	// 记得要设置监听
	// mSwipeRefresh.setOnRefreshListener(this);
	@Override
	public void onRefresh() {
		Logs.v("onRefresh>>> 下拉刷新开始");
		Toast.makeText(getActivity(), "下拉刷新", Toast.LENGTH_SHORT).show();
		Logs.v("testURLAdd>>> 下拉刷新的数据加载开始");
		//要在前面调用方法重新给新的地址
		mCurrentPage = 0;
		mPortUrl=NewsInfo.getVideoUrl(mPort, mCurrentPage);
		mJsonParseNews.getVedioListHttpConn(mPortUrl,mPort);
		mSwipeRefresh.setRefreshing(false);
		Logs.v("onRefresh>>> 下拉刷新完成");
	}
	@Override
	public void onVideoNewsList(List<VideoModelObj> videoList) {
		// TODO Auto-generated method stub
		// 设置监听
				Logs.e("onPicNewsList");
						mListView.setOnLoadListener(this);
						mListView.setOnItemClickListener(this);
						mVideoNewsAdapter.addDatas(mCurrentPage,videoList);
	}
}
