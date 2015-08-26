package com.warmtel.android.main.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyErrorHelper;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderLayout.PresetIndicators;
import com.daimajia.slider.library.SliderLayout.Transformer;
import com.daimajia.slider.library.Indicators.PagerIndicator.IndicatorVisibility;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView.OnSliderClickListener;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.warmtel.android.R;
import com.warmtel.android.common.configs.Logs;
import com.warmtel.android.common.widget.PullRefreshListView;
import com.warmtel.android.common.widget.PullRefreshListView.PullType;
import com.warmtel.android.common.widget.PullRefreshListView.onLoadListener;
import com.warmtel.android.main.RequestManager;
import com.warmtel.android.main.act.httpweb.NewsShowWebAct;
import com.warmtel.android.main.html.JsonParseNews;
import com.warmtel.android.main.useful.jsonobj.ReadNewsObj;
import com.warmtel.android.main.util.ApiPreference;
import com.warmtel.android.main.util.HttpConnectionUtil;
import com.warmtel.android.main.util.HttpConnectionUtil.HttpConnectionCallback;

/**
 * update信息: 1.2014.10.24 在Adapter中使用了Picasso来缓存图片 2.2014.10.24
 * 实现了SliderLayout里面的TextSliderView的点击监听 3.2014.10.25
 * 取消了OnArticleOpenListener接口， because ListView可以直接在Fragment里相应点击事件
 * 
 * @author tmb
 * modify viktor 2014.11.7
 * modify viktor 2015.8.25  优化处理：1.网络异常处理 2.下拉刷新，上拉加载更多
 */
public class ReadNewsFragUpdatePicasso extends Fragment implements onLoadListener, OnItemClickListener,
		OnRefreshListener, OnSliderClickListener {
	private int mCurrentPage = 0;
	private ReadNewsAdapter mReadNewsAdapter;
	private PullRefreshListView mListView;
	// private ListView mListView;
	private SliderLayout mSliderLayout;
	private String mPort;
	private int mReadStyle;
	private View mListEmpleyLayout;
	private SwipeRefreshLayout mSwipeRefresh;// 下拉刷新Google官方控件
	private HttpConnectionUtil mHttpConnectionUtil;
	private final static int HANDLER_ERROR = 1;
	private final static int HANDLER_SLIDER = 2;
	private final static int HANDLER_LOADMORE_FINISH = 3;
	
	private Handler mHandler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case HANDLER_ERROR:
				String message = (String)msg.obj;
				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
				break;
			case HANDLER_SLIDER:
				List<ReadNewsObj> readList = (List<ReadNewsObj>)msg.obj;
				
				mSwipeRefresh.setRefreshing(false);
				mListView.onLoadMoreFinish();
				
				showReadPic(readList);
				mReadNewsAdapter.addDatas(mCurrentPage, readList);
				
				break;
			case HANDLER_LOADMORE_FINISH:
				mListView.onLoadMoreFinish();
				break;
			}
		}
	};
	// 封装的构造方法
	public static ReadNewsFragUpdatePicasso newInstance(String port, int read) {
		ReadNewsFragUpdatePicasso readnewsFrag = new ReadNewsFragUpdatePicasso();
		Bundle args = new Bundle();
		args.putInt("readStyle", read);
		args.putString("portStyle", port);
		readnewsFrag.setArguments(args);
		return readnewsFrag;
	}

	// 第三个执行
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mHttpConnectionUtil = new HttpConnectionUtil(getActivity());
		
		View v = inflater.inflate(R.layout.read_news_child_layout_head,container, false);
		mListEmpleyLayout = v.findViewById(R.id.listview_frame_layout);
		// 一定要在onExcute外执行啊，不然会重复跳到第一条
		mListView = (PullRefreshListView) v.findViewById(R.id.listview);
		// 实例化下拉刷新控件
		mSwipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefresh);
		// 设置下拉时显示的颜色
		mSwipeRefresh.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwipeRefresh.setOnRefreshListener(this);
		// 必须要两个参数的 不然会报错cast to的错误
		View mHeadV = inflater.inflate(R.layout.read_news_slider_head_layout, null);
		mSliderLayout = (SliderLayout) mHeadV.findViewById(R.id.read_news_slider);
		
		mListView.setEmptyView(mListEmpleyLayout);
		mListView.addHeaderView(mHeadV);
		mListView.setOnLoadListener(this);
		mListView.setOnItemClickListener(this);
		mListView.setPullType(PullType.PRESS);
		
		mReadNewsAdapter = new ReadNewsAdapter(getActivity());
		mListView.setAdapter(mReadNewsAdapter);
		
		return v;
	}

	// 第四个执行
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		mReadStyle = bundle.getInt("readStyle");
		mPort = bundle.getString("portStyle");
		
//		RequestManager.addRequest(
//				new StringRequest(JsonParseNews
//						.getUrl(mCurrentPage, mReadStyle), responseListener(),
//						errorListener()), this);

		
		final String url = JsonParseNews.getUrl(mCurrentPage, mReadStyle);
		mHttpConnectionUtil.asyncConnect(url, HttpConnectionUtil.HttpMethod.GET,url,new HttpConnectionCallback() {
					
					@Override
					public void onResponse(String response) {
						List<ReadNewsObj> readList = JsonParseNews.getList2Json(response, mPort);
						onExcute(readList);
					}

					@Override
					public void onErrorResponse(String errorMessage) {
						Message msg = Message.obtain();
						msg.what = HANDLER_ERROR;
						msg.obj = errorMessage;
						mHandler.sendMessage(msg);
						
						String cacheJson = ApiPreference.getInstance(getActivity()).getCache(url);
						if(cacheJson != null){
							List<ReadNewsObj> readList = JsonParseNews.getList2Json(cacheJson, mPort);
							onExcute(readList);
						}
					}
				});
	}
	@Override
	public void onRefresh() {
		mCurrentPage = 0;
		
		/**用下面方法 responseListener()被回调三次 为什么?*/
//		RequestManager.addRequest(
//				new StringRequest(JsonParseNews
//						.getUrl(mCurrentPage, mReadStyle), responseListener(),
//						errorListener()), this);
		
		
		mHttpConnectionUtil.asyncConnect(JsonParseNews
						.getUrl(mCurrentPage, mReadStyle), HttpConnectionUtil.HttpMethod.GET,new HttpConnectionCallback() {
							
							@Override
							public void onResponse(String response) {
								List<ReadNewsObj> readList = JsonParseNews.getList2Json(response, mPort);
								onExcute(readList);
							}

							@Override
							public void onErrorResponse(String errorMessage) {
								Message msg = Message.obtain();
								msg.what = HANDLER_ERROR;
								msg.obj = errorMessage;
								mHandler.sendMessage(msg);
								
								String cacheJson = ApiPreference.getInstance(getActivity()).getCache(JsonParseNews.getUrl(mCurrentPage, mReadStyle));
								if(cacheJson != null){
									List<ReadNewsObj> readList = JsonParseNews.getList2Json(cacheJson, mPort);
									onExcute(readList);
								}
							}
						});
		
		
	}
	
	@Override
	public void onLoadMore() {
		mCurrentPage++;
//		RequestManager.addRequest(
//				new StringRequest(JsonParseNews
//						.getUrl(mCurrentPage, mReadStyle), responseListener(),
//						errorListener()), this);
		
		mHttpConnectionUtil.asyncConnect(JsonParseNews
				.getUrl(mCurrentPage, mReadStyle), HttpConnectionUtil.HttpMethod.GET,new HttpConnectionCallback() {
					
					@Override
					public void onResponse(String response) {
						List<ReadNewsObj> readList = JsonParseNews.getList2Json(response, mPort);
						onExcute(readList);
					}

					@Override
					public void onErrorResponse(String errorMessage) {
						Message msg = Message.obtain();
						msg.what = HANDLER_ERROR;
						msg.obj = errorMessage;
						mHandler.sendMessage(msg);

						msg = Message.obtain();
						msg.what = HANDLER_LOADMORE_FINISH;
						mHandler.sendMessage(msg);
						
						
					}
				});
	}

	/**
	 * 新闻适配器
	 * 
	 * @author Administrator
	 */
	class ReadNewsAdapter extends BaseAdapter {
		List<ReadNewsObj> readListAll = new ArrayList<ReadNewsObj>();
		LayoutInflater inflater;
		Context mContext;
		private static final int TYPE_TXT = 0;
		private static final int TYPE_IMG = 1;

		public ReadNewsAdapter(Context context) {
			mContext = context;
			inflater = LayoutInflater.from(context);
		}

		// 封装方法
		public void addDatas(int CurrentPage, List<ReadNewsObj> list) {
			// 用mCurrentPage判断是set数据 还是添加数据
			if (CurrentPage == 0) {
				setData(list);
			} else {
				addData(list);
			}
		}

		public void setData(List<ReadNewsObj> list) {
			readListAll = list;
			notifyDataSetChanged();
		}

		public void addData(List<ReadNewsObj> list) {
			if (list != null) {
				readListAll.addAll(list);
			}
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return readListAll.size();
		}

		@Override
		public Object getItem(int position) {
			return readListAll.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			if (readListAll.get(position).getImgArray().isEmpty()) {
				return TYPE_TXT;
			} else {
				return TYPE_IMG;
			}
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			ViewHolder_IMG viewHolder_IMG = null;
			int type = getItemViewType(position);
			if (convertView == null) {
				switch (type) {
				case TYPE_TXT:
					viewHolder = new ViewHolder();
					convertView = inflater.inflate(R.layout.read_news_child_item_layout, null);
					viewHolder.digestRead = (TextView) convertView.findViewById(R.id.read_news_show_digest);
					viewHolder.titleRead = (TextView) convertView.findViewById(R.id.read_news_show_title);
					viewHolder.imageRead = (ImageView) convertView.findViewById(R.id.read_news_show_pic);
					convertView.setTag(viewHolder);
					break;
				case TYPE_IMG:
					viewHolder_IMG = new ViewHolder_IMG();
					convertView = inflater.inflate(R.layout.read_news_child_item_layout_img, null);
					viewHolder_IMG.titleRead_img = (TextView) convertView.findViewById(R.id.read_news_img_title);
					viewHolder_IMG.imageRead_1 = (ImageView) convertView.findViewById(R.id.read_news_img_pic1);
					viewHolder_IMG.imageRead_2 = (ImageView) convertView.findViewById(R.id.read_news_img_pic2);
					viewHolder_IMG.imageRead_3 = (ImageView) convertView.findViewById(R.id.read_news_img_pic3);
					convertView.setTag(viewHolder_IMG);
					break;
				default:
					break;
				}
			} else {
				switch (type) {
				case TYPE_TXT:
					viewHolder = (ViewHolder) convertView.getTag();
					break;
				case TYPE_IMG:
					viewHolder_IMG = (ViewHolder_IMG) convertView.getTag();
					break;
				default:
					break;
				}
			}
			ReadNewsObj readObj = (ReadNewsObj) getItem(position);
			switch (type) {
			case TYPE_TXT:
				viewHolder.digestRead.setText(readObj.getDigest());
				viewHolder.titleRead.setText(readObj.getTitle());

				ImageListener listener = ImageLoader.getImageListener(viewHolder.imageRead, 
						R.drawable.loading_pin2, android.R.drawable.ic_delete);
				RequestManager.getImageLoader().get(readObj.getShowPic(), listener);
				
				break;
			case TYPE_IMG:
				viewHolder_IMG.titleRead_img.setText(readObj.getTitle());

				ImageListener imageListener = ImageLoader.getImageListener(viewHolder_IMG.imageRead_1, 
						R.drawable.loading_pin2, android.R.drawable.ic_delete);
				RequestManager.getImageLoader().get(readObj.getShowPic(), imageListener);
				
				if (readObj.getImgArray().size() >= 2) {
					ImageListener imageListener1 = ImageLoader.getImageListener(viewHolder_IMG.imageRead_2, 
							R.drawable.loading_pin2, android.R.drawable.ic_delete);
					RequestManager.getImageLoader().get(readObj.getImgArray().get(0), imageListener1);
					
					ImageListener imageListener2 = ImageLoader.getImageListener(viewHolder_IMG.imageRead_3, 
							R.drawable.loading_pin2, android.R.drawable.ic_delete);
					RequestManager.getImageLoader().get(readObj.getImgArray().get(1), imageListener2);
					
				}
				break;
			default:
				break;
			}
			return convertView;
		}

		class ViewHolder {
			ImageView imageRead;
			TextView titleRead;
			TextView digestRead;
		}

		class ViewHolder_IMG {
			ImageView imageRead_1;
			ImageView imageRead_2;
			ImageView imageRead_3;
			TextView titleRead_img;
		}
	}

	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ReadNewsObj readobj = (ReadNewsObj) parent.getAdapter().getItem(position);
		Intent intent = new Intent(getActivity(),NewsShowWebAct.class);
		intent.putExtra("docID", readobj.getDocId());
		startActivity(intent);
	}


	@Override
	public void onSliderClick(BaseSliderView slider) {
		Toast.makeText(getActivity(), slider.getDescription(),
				Toast.LENGTH_SHORT).show();
	}
	
	private Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getActivity(),
						VolleyErrorHelper.getMessage(error, getActivity()),
						Toast.LENGTH_LONG).show();
			}
		};
	}

	private Response.Listener<String> responseListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				List<ReadNewsObj> readList = JsonParseNews.getList2Json(response, mPort);
				onExcute(readList);
			}
		};
	}
	
	public void onExcute(List<ReadNewsObj> readList) {
		Message msg = Message.obtain();
		msg.what = HANDLER_SLIDER;
		msg.obj = readList;
		mHandler.sendMessage(msg);
	}

	public void showReadPic(List<ReadNewsObj> readList) {
		if (mCurrentPage == 0) {
			mSliderLayout.removeAllSliders();// 移除所有的Slider
			/**
			 * 展示橱窗
			 */
			for (int i = 0; i <= 3; i++) {
				TextSliderView textSlider = new TextSliderView(getActivity());
				textSlider.description(readList.get(i).getTitle())
						.image(readList.get(i).getShowPic())
						.setScaleType(BaseSliderView.ScaleType.CenterCrop)
						;
				// 添加进SliderLayout
				mSliderLayout.addSlider(textSlider);
				Logs.v(textSlider.getDescription());
				/**
				 * 实现TextSliderView的点击监听
				 */
				textSlider.setOnSliderClickListener(this);
			}
			// 设置指示器位置
			mSliderLayout.setPresetIndicator(PresetIndicators.Right_Bottom);
			// 设置转场动画(幻灯片切换方式)
			mSliderLayout.setPresetTransformer(Transformer.Default);
			// 幻灯片标题显示方式
			// mSliderLayout.setCustomAnimation(new DescriptionAnimation());
			// 开始自动放映
			// mSliderLayout.startAutoCycle();
			// //停止自动放映
			// mSliderLayout.stopAutoCycle();
			// 指示符是否显示
			mSliderLayout.setIndicatorVisibility(IndicatorVisibility.Visible);
			// 幻灯片切换时间
//			mSliderLayout.setDuration(4000);
			// 幻灯片转化时间 ，停顿时间(显示时间)=切换时间-转化时间
//			mSliderLayout.setSliderTransformDuration(1500, null);
		}

	}

}
