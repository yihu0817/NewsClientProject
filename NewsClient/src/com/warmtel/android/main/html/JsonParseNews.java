package com.warmtel.android.main.html;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.warmtel.android.common.configs.Logs;
import com.warmtel.android.main.useful.NewsInfo;
import com.warmtel.android.main.useful.NewsInfo.ReadNewsStyle;
import com.warmtel.android.main.useful.jsonobj.CityAllInfo;
import com.warmtel.android.main.useful.jsonobj.NewsInfoUseObj;
import com.warmtel.android.main.useful.jsonobj.PicModelObj;
import com.warmtel.android.main.useful.jsonobj.PicNewsObj;
import com.warmtel.android.main.useful.jsonobj.ReadNewsImagSrcObj;
import com.warmtel.android.main.useful.jsonobj.ReadNewsObj;
import com.warmtel.android.main.useful.jsonobj.VideoJsonArray;
import com.warmtel.android.main.useful.jsonobj.VideoModelObj;
import com.warmtel.android.main.useful.jsonobj.WeatherJson;
import com.warmtel.android.main.util.HttpConnectionUtil;
import com.warmtel.android.main.util.HttpConnectionUtil.HttpConnectionCallback;
import com.warmtel.android.main.util.HttpConnectionUtil.HttpMethod;

/**
 * 
 * @author Administrator 1.先建立连接 2.获取Json数据 3.解析Json数据 4.获取网络图片 5.将资源定义给Adapter
 * 
 */
public class JsonParseNews {
	public static int sPageNo = NewsInfo.sPageNo; // 表示初始界面显示第一页
	public static int sPageSize = NewsInfo.sPageSize; // 页大小，显示每页多少条数据
	// private String NewsUrlHead = NewsInfo.HEADLINE_URL_CREATE;
	// private String HeadLineUrl = NewsInfo.HEADLINE_URL_CREATE +
	// NewsInfo.sTail;
	HttpConnectionUtil httpConn = new HttpConnectionUtil();
	List<ReadNewsObj> mReadList = new ArrayList<ReadNewsObj>();
	private OnTestURLListener testUrlListener;
	private OnNewsInfoListener onNewsInfoListener;
	private OnPicNewsListener onPicNewsListener;
	private OnPicDetailListener onPicDetailListener;
	private OnVideoNewsListener onVideoNewsListener;
	private OnCityInfoListener onCityInfoListener;
	private OnWeatherJsonListener onWeatherJsonListener;

	public void setOnTestUrlListener(OnTestURLListener testUrlListener) {
		this.testUrlListener = testUrlListener;
		Logs.e("setTestUrlListener   start");
	}

	public void setOnNewsInfoListener(OnNewsInfoListener onNewsInfoListener) {
		Logs.v("setOnNewsInfoListener  开始");
		this.onNewsInfoListener = onNewsInfoListener;
	}

	public void setOnPicNewsListener(OnPicNewsListener onPicNewsListener) {
		this.onPicNewsListener = onPicNewsListener;
	}

	public void setOnPicDetailListener(OnPicDetailListener onPicDetailListener) {
		this.onPicDetailListener = onPicDetailListener;
	}

	public void setOnVideoNewsListener(OnVideoNewsListener onVideoNewsListener) {
		this.onVideoNewsListener = onVideoNewsListener;
	}

	public void setOnCityInfoListener(OnCityInfoListener onCityInfoListener) {
		this.onCityInfoListener = onCityInfoListener;
	}

	public void setOnWeatherJsonListener(
			OnWeatherJsonListener onWeatherJsonListener) {
		this.onWeatherJsonListener = onWeatherJsonListener;
	}

	public void testURLAdd(int currentPage, ReadNewsStyle read,
			final String port) {
		String readNewsUrl = getUrl(currentPage, read);
		Logs.v(readNewsUrl);
		httpConn.asyncConnect(readNewsUrl, HttpMethod.GET,
				new HttpConnectionCallback() {
					@Override
					public void onResponse(String response) {
						List<ReadNewsObj> readList = new ArrayList<ReadNewsObj>();
						readList = getList2Json(response, port);
						// ReadNewsObj read = readList.get(1);
						// Logs.v(read.getDigest());
						// Logs.v(read.getTitle());
						// Logs.v(read.getShowPic());
						// 累加添加数据
						if (readList != null) {
							mReadList.addAll(readList);
						} else {
							mReadList = readList;
						}
						testUrlListener.onExcute(mReadList);
					}

					@Override
					public void onErrorResponse(String errorMessage) {
						// TODO Auto-generated method stub
						
					}
				});
	}

	public void testURLAdd(final int currentPage, int read, final String port) {
		String readNewsUrl = getUrl(currentPage, read);
		Logs.e(readNewsUrl);
		httpConn.asyncConnect(readNewsUrl, HttpMethod.GET,
				new HttpConnectionCallback() {
					@Override
					public void onResponse(String response) {
						List<ReadNewsObj> readList = new ArrayList<ReadNewsObj>();
						readList = getList2Json(response, port);
						// ReadNewsObj read = readList.get(1);
						// Logs.v(read.getDigest());
						// Logs.v(read.getTitle());
						// Logs.v(read.getShowPic());
						// 累加添加数据
						// final int innerCurrentPage = currentPage;
						// if(readList!=null && innerCurrentPage!=0)
						// {
						// mReadList.addAll(readList);
						// }else if(currentPage==0)
						// {
						// mReadList.clear();
						// mReadList=readList;
						// }
						Logs.e("testURLAdd   start");
						testUrlListener.onExcute(readList);
						Logs.e("testURLAdd   end");
					}

					@Override
					public void onErrorResponse(String errorMessage) {
						// TODO Auto-generated method stub
						
					}
				});
	}

	public static List<ReadNewsObj> getList2Json(String response, String port) {
		List<ReadNewsObj> readList = new ArrayList<ReadNewsObj>();
		// Logs.v(response);
		JSONObject headlineObj = JSONObject.parseObject(response);
		JSONArray headLine = headlineObj.getJSONArray(port);
		for (int i = 1; i < headLine.size() - 1; i++) {
			JSONObject headLineChild = headLine.getJSONObject(i);
			String digest = headLineChild.getString("digest");
			String title = headLineChild.getString("title");
			String showPic = headLineChild.getString("imgsrc");
			String docId = headLineChild.getString("docid");
			JSONArray imgArray = headLineChild.getJSONArray("imgextra");
			List<String> imgArrayList = new ArrayList<String>();
			if (imgArray != null) {
				JSONObject obj0 = (JSONObject) imgArray.getJSONObject(0);
				imgArrayList.add(obj0.getString("imgsrc"));
				JSONObject obj1 = (JSONObject) imgArray.getJSONObject(1);
				imgArrayList.add(obj1.getString("imgsrc"));
			}
			ReadNewsObj readObj = new ReadNewsObj();
			readObj.setDigest(digest);
			readObj.setTitle(title);
			readObj.setShowPic(showPic);
			readObj.setDocId(docId);
			readObj.setImgArray(imgArrayList);
			readList.add(readObj);
		}
		Logs.e("getList2Json >>>>");
		return readList;
	}

	public interface OnTestURLListener {
		public void onExcute(List<ReadNewsObj> readList);
	}

	/**
	 * 网址构造方法
	 */
	public static String getUrl(int currentPage, ReadNewsStyle read) {
		String headLineString = NewsInfo.HEADLINE_URL_CREATE;
		String yuleString = NewsInfo.ENTERTAIN_URL_CREATE;
		String sportString = NewsInfo.SPORTS_URL_CREATE;
		String econmicString = NewsInfo.ECONOMIC_URL_CREATE;
		String scinceString = NewsInfo.SCIENCE_URL_CREATE;
		String tailString = "/" + currentPage * sPageSize + "-" + sPageSize
				+ ".html";
		switch (read) {
		case TODAY_HEADLINE:
			return headLineString + tailString;
		case ENTERTAINMENT:
			return yuleString + tailString;
		case SPORTS:
			return sportString + tailString;
		case ECONOMICS:
			return econmicString + tailString;
		case SCIENCE:
			return scinceString + tailString;
		default:
			return null;
		}

	}

	public static String getUrl(int currentPage, int read) {
		String headLineString = NewsInfo.HEADLINE_URL_CREATE;
		String yuleString = NewsInfo.ENTERTAIN_URL_CREATE;
		String sportString = NewsInfo.SPORTS_URL_CREATE;
		String econmicString = NewsInfo.ECONOMIC_URL_CREATE;
		String scinceString = NewsInfo.SCIENCE_URL_CREATE;
		String tailString = "/" + currentPage * sPageSize + "-" + sPageSize
				+ ".html";
		Logs.e("getUrl");
		switch (read) {
		case NewsInfo.TODAY_HEADLINE:
			return headLineString + tailString;
		case NewsInfo.ENTERTAINMENT:
			return yuleString + tailString;
		case NewsInfo.SPORTS:
			return sportString + tailString;
		case NewsInfo.ECONOMICS:
			return econmicString + tailString;
		case NewsInfo.SCIENCE:
			return scinceString + tailString;
		default:
			return null;
		}
	}

	/**
	 * 獲得新聞正文解析
	 * 
	 * @param response
	 * @param docId
	 * @return 步驟: 1.獲得URL，構造完整網址，這裡是方法getNewsInfoUrl(String docId);
	 *         2.創建異步加載數據的工具類，調用方法得到Json字符串getNewsInfoHttpConn(String
	 *         response,final String docId) 3.獲得Json字符串并分離出我們想要的信息
	 *         存放為構造的對象，并返回這個帶值的對象, 方法getNewsInfoObj2Json(String response,String
	 *         docId); 4.定義回调接口并定义回调方法，原因是不知道何时从网络取回数据，所以我们就要先定义方法，
	 *         等数据取回来了才提醒我们(触发回调方法),通知实现这个接口的类并执行实现这个回调方法里的代码。
	 *         5.在这个类中声明回调接口，并提供set方法。（其实说白了就是为了给实现这个接口的类暴露方法调用这个接口）
	 * 
	 */
	public NewsInfoUseObj getNewsInfoObj2Json(String response, String docId) {
		// List<NewsInfoUseObj> newsInfoList = new ArrayList<NewsInfoUseObj>();
		 Logs.v(response);
		 
		JSONObject allObj = JSONObject.parseObject(response);
		JSONObject localObj = allObj.getJSONObject(docId);// 得到以这个端口命名的Json对象
		// 查找需要的信息
		String body = localObj.getString("body");
		List<ReadNewsImagSrcObj> img = new ArrayList<ReadNewsImagSrcObj>();
		String picUrl = null;
		int picCount = 0;
		Logs.v("localObj.getJSONArray:" + localObj.getJSONArray("img"));
		if (localObj.getJSONArray("img").isEmpty()) {
			picUrl = null;
		} else {
			picCount = localObj.getJSONArray("img").size();
			picUrl = localObj.getJSONArray("img").getJSONObject(0)
					.getString("src");
			localObj.getJSONArray("img");
			for (int i = 0; i < localObj.getJSONArray("img").size(); i++) {
				ReadNewsImagSrcObj imagObj = new ReadNewsImagSrcObj();
				imagObj.setRef(localObj.getJSONArray("img").getJSONObject(i)
						.get("ref").toString());
				imagObj.setPixel(localObj.getJSONArray("img").getJSONObject(i)
						.get("pixel").toString());
				imagObj.setAlt(localObj.getJSONArray("img").getJSONObject(i)
						.get("alt").toString());
				imagObj.setSrc(localObj.getJSONArray("img").getJSONObject(i)
						.get("src").toString());
				img.add(imagObj);
			}
		}
		String title = localObj.getString("title");
		String source = localObj.getString("source");
		String ptime = localObj.getString("ptime");

		NewsInfoUseObj newsInfoObj = new NewsInfoUseObj();
		newsInfoObj.setBody(body);
		newsInfoObj.setPicUrl(picUrl);
		newsInfoObj.setTitle(title);
		newsInfoObj.setSource(source);
		newsInfoObj.setPtime(ptime);
		newsInfoObj.setPicCount(picCount);
		newsInfoObj.setImg(img);
		Logs.v("body:" + body);
		Logs.v("picUrl:" + picUrl);
		Logs.v("title:" + title);
		Logs.v("source:" + source);
		Logs.v("ptime:" + ptime);
		Logs.v("picCount:" + picCount);
		Logs.v("getNewsInfoList2Json>>>>>到可以瀏覽新聞啦");
		return newsInfoObj;
	}

	public String getNewsInfoUrl(String docId) {
		String newsInfoUrl = NewsInfo.sNewsInfoHead + docId
				+ NewsInfo.sNewsInfoTail;
		Logs.v("newsInfoUrl>>>>>>>>>" + newsInfoUrl);
		return newsInfoUrl;
	}

	public void getNewsInfoHttpConn(final String docId) {
		String newsInfoUrl = getNewsInfoUrl(docId);
		Logs.e("我需要的" + newsInfoUrl);
		httpConn.asyncConnect(newsInfoUrl, HttpMethod.GET,
				new HttpConnectionCallback() {
					@Override
					public void onResponse(String response) {
						Logs.v("getNewsInfoHttpConn   start");
						NewsInfoUseObj newsInfoObj = getNewsInfoObj2Json(
								response, docId);
						onNewsInfoListener.OnNewsInfo(newsInfoObj);
						Logs.e("getNewsInfoHttpConn   end");
					}

					@Override
					public void onErrorResponse(String errorMessage) {
						// TODO Auto-generated method stub
						
					}
				});
	}

	public interface OnNewsInfoListener {
		public void OnNewsInfo(NewsInfoUseObj newsInfoObj);
	}

	/**
	 * 获得图片列表的解析
	 * 
	 */

	// 封装异步加载数据后返回json字符串的解析
	public List<PicNewsObj> getPicList2Json(String response) {
		List<PicNewsObj> picList = new ArrayList<PicNewsObj>();
		// Logs.v(response);
		JSONObject headlineObj = JSONObject.parseObject(response);
		JSONObject dataObj = headlineObj.getJSONObject("data");
		JSONArray listArray = dataObj.getJSONArray("list");
		for (int i = 0; i < listArray.size(); i++) {
			JSONObject listChild = listArray.getJSONObject(i);
			String id = listChild.getString("id");
			String picTitle = listChild.getString("title");
			String picUrl = listChild.getString("pic");
			PicNewsObj picNewsObj = new PicNewsObj();
			picNewsObj.setId(id);
			picNewsObj.setPicTitle(picTitle);
			picNewsObj.setPicUrl(picUrl);
			picList.add(picNewsObj);
			// Logs.e("headline>>>>>"+(headLine.size()-1-1));//计算新闻列表一篇总数
			Logs.v(id);
			Logs.e(picTitle);
			Logs.v(picUrl);
		}
		Logs.e("getList2Json");
		return picList;
	}

	// 封装异步任务方法
	public void getPicListHttpConn(String url) {
		httpConn.asyncConnect(url, HttpMethod.GET,
				new HttpConnectionCallback() {
					@Override
					public void onResponse(String response) {
						Logs.v("getPicListHttpConn   start");
						List<PicNewsObj> picList = getPicList2Json(response);
						onPicNewsListener.onPicNewsList(picList);
						Logs.e("getPicListHttpConn   end");
					}

					@Override
					public void onErrorResponse(String errorMessage) {
						// TODO Auto-generated method stub
						
					}
				});
	}

	// 提供接口给实例化他的类使用
	public interface OnPicNewsListener {
		public void onPicNewsList(List<PicNewsObj> picList);
	}

	/**
	 * pic Detail 解析
	 */
	public int getPicDitalCount2Json(String response) {
		int picCount = 0;
		JSONObject headlineObj = JSONObject.parseObject(response);
		JSONObject dataObj = headlineObj.getJSONObject("data");
		JSONArray listArray = dataObj.getJSONArray("pics");
		picCount = listArray.size();
		return picCount;
	}

	public List<PicModelObj> getPicDetail2Json(String response) {
		List<PicModelObj> picDetailList = new ArrayList<PicModelObj>();
		// Logs.v(response);
		JSONObject headlineObj = JSONObject.parseObject(response);
		JSONObject dataObj = headlineObj.getJSONObject("data");
		String title = dataObj.getString("title");
		JSONArray listArray = dataObj.getJSONArray("pics");
		for (int i = 0; i < listArray.size(); i++) {
			JSONObject listChild = listArray.getJSONObject(i);
			String picUrl = listChild.getString("pic");
			String picContent = listChild.getString("alt");
			// 做有没有尺寸的判断
			String picSize = null;
			if (listChild.getString("size") == null) {
				picSize = "";
			} else {
				picSize = listChild.getString("size");
			}
			PicModelObj picModelObj = new PicModelObj();
			picModelObj.setPic(picUrl);
			picModelObj.setAlt(picContent);
			picModelObj.setSize(picSize);
			picModelObj.setTitle(title);
			;
			picDetailList.add(picModelObj);
			// Logs.e("headline>>>>>"+(headLine.size()-1-1));//计算新闻列表一篇总数
			Logs.v(picUrl);
			Logs.e(picContent);
			Logs.v(picSize);
		}
		Logs.e("getList2Json");
		return picDetailList;
	}

	// 封装异步任务方法
	public void getPicDetailHttpConn(String url) {
		httpConn.asyncConnect(url, HttpMethod.GET,
				new HttpConnectionCallback() {
					@Override
					public void onResponse(String response) {
						Logs.v("getPicDetailHttpConn   start");
						List<PicModelObj> picDetailList = getPicDetail2Json(response);
						onPicDetailListener.onPicDetailList(picDetailList);
						Logs.e("getPicDetailHttpConn   end");
					}

					@Override
					public void onErrorResponse(String errorMessage) {
						// TODO Auto-generated method stub
						
					}
				});
	}

	// 提供接口给实例化他的类使用
	public interface OnPicDetailListener {
		public void onPicDetailList(List<PicModelObj> picDetailList);
	}

	/**
	 * 获得视频列表的解析
	 * 
	 */

	// 封装异步加载数据后返回json字符串的解析
	// 此方法有限制,只能加载热点Video,因为 VideoJsonArray定死了List的接口。
	public List<VideoModelObj> getVideoList2Json(String response) {
		// List<VideoModelObj> videoList = new ArrayList<VideoModelObj>();
		VideoJsonArray videoJsonArray = JSON.parseObject(response,
				VideoJsonArray.class);
		List<VideoModelObj> videoList = videoJsonArray.getV9LG4B3A0();
		return videoList;
	}

	public List<VideoModelObj> getVideoList2Json(String response, String port) {
		// Logs.v(response);
		List<VideoModelObj> videoList = new ArrayList<VideoModelObj>();
		JSONObject headlineObj = JSONObject.parseObject(response);
		JSONArray listArray = headlineObj.getJSONArray(port);
		for (int i = 0; i < listArray.size(); i++) {
			JSONObject listChild = listArray.getJSONObject(i);
			String cover = listChild.getString("cover");
			String title = listChild.getString("title");
			int replyCount = listChild.getIntValue("replyCount");
			String mp4_url = listChild.getString("mp4_url");
			int length = listChild.getIntValue("length");
			String mp4Hd_url = listChild.getString("mp4Hd_url");// 高清视频地址
			int playersize = listChild.getIntValue("playersize");
			String m3u8Hd_url = listChild.getString("m3u8Hd_url");// m3u8格式高清地址
			String ptime = listChild.getString("ptime");// 编辑时间
			String m3u8_url = listChild.getString("m3u8_url");
			String vid = listChild.getString("vid");
			VideoModelObj videoModelObj = new VideoModelObj(cover, title,
					replyCount, mp4_url, length, mp4Hd_url, playersize,
					m3u8Hd_url, ptime, m3u8_url, vid);
			videoList.add(videoModelObj);
		}
		return videoList;
	}

	// 封装异步任务方法
	public void getVedioListHttpConn(String url) {
		httpConn.asyncConnect(url, HttpMethod.GET,
				new HttpConnectionCallback() {
					@Override
					public void onResponse(String response) {
						Logs.v("getVedioListHttpConn   start");
						List<VideoModelObj> videoList = getVideoList2Json(response);
						onVideoNewsListener.onVideoNewsList(videoList);
						Logs.e("getVedioListHttpConn   end");
					}

					@Override
					public void onErrorResponse(String errorMessage) {
						// TODO Auto-generated method stub
						
					}
				});
	}

	// 封装异步任务方法
	public void getVedioListHttpConn(String url, final String port) {
		httpConn.asyncConnect(url, HttpMethod.GET,
				new HttpConnectionCallback() {
					@Override
					public void onResponse(String response) {
						Logs.v("getVedioListHttpConn   start");
						List<VideoModelObj> videoList = getVideoList2Json(
								response, port);
						onVideoNewsListener.onVideoNewsList(videoList);
						Logs.e("getVedioListHttpConn   end");
					}

					@Override
					public void onErrorResponse(String errorMessage) {
						// TODO Auto-generated method stub
						
					}
				});
	}

	// 提供接口给实例化他的类使用
	public interface OnVideoNewsListener {
		public void onVideoNewsList(List<VideoModelObj> videoList);
	}

	/**
	 * 获取城市数据
	 */
	// 读取asset里面的File并返回一个字符串
	public String readAssetFile(String FileName, Context context)
			throws IOException {
		InputStream is = context.getAssets().open(FileName);
		int size = is.available();
		byte[] bt = new byte[size];
		// 读取流并将数据存入byte[]数组
		is.read(bt);
		String info = new String(bt, "UTF-8");
		return info;
	}

	public CityAllInfo getCityInfo2Object(Context context) {
		try {
			String json = readAssetFile("city_info_txt", context);
			CityAllInfo city = JSON.parseObject(json, CityAllInfo.class);
			Logs.v("userName:"
					+ city.getCity_info().get(0).getCity().get(0)
							.getCity_name()
					+ ",email:"
					+ city.getCity_info().get(0).getCity().get(0)
							.getCity_code());
			return city;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private CityInfoAsyncTask mCityInfoAsyncTask;

	public void getCityInfoAsync(Context context) {
		mCityInfoAsyncTask = new CityInfoAsyncTask();
		mCityInfoAsyncTask.execute(context);
	}

	class CityInfoAsyncTask extends AsyncTask<Context, Void, CityAllInfo> {
		@Override
		protected CityAllInfo doInBackground(Context... params) {
			return getCityInfo2Object(params[0]);
		}

		@Override
		protected void onPostExecute(CityAllInfo result) {
			onCityInfoListener.onCityInfo(result);
		}

	}

	// 提供接口给实例化他的类使用
	public interface OnCityInfoListener {
		public void onCityInfo(CityAllInfo city);
	}

	/**
	 * 天气解析
	 * 
	 */
	// 封装异步加载数据后返回json字符串的解析
	public WeatherJson getWeatherList2Json(String response) {
		// List<VideoModelObj> videoList = new ArrayList<VideoModelObj>();
		WeatherJson weatherJson = JSON.parseObject(response, WeatherJson.class);
		return weatherJson;
	}

	// 封装异步任务方法
	public void getWeatherListHttpConn(String url) {
		httpConn.asyncConnect(url, HttpMethod.GET,
				new HttpConnectionCallback() {
					@Override
					public void onResponse(String response) {
						Logs.v("getVedioListHttpConn   start");
						WeatherJson weatherJson = getWeatherList2Json(response);
						onWeatherJsonListener.onWeatherJsonList(weatherJson);
						Logs.e("getVedioListHttpConn   end");
					}

					@Override
					public void onErrorResponse(String errorMessage) {
						// TODO Auto-generated method stub
						
					}
				});
	}

	// 提供接口给实例化他的类使用
	public interface OnWeatherJsonListener {
		public void onWeatherJsonList(WeatherJson weatherJson);
	}
}
