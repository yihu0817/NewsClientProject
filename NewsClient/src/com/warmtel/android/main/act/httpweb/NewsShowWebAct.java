package com.warmtel.android.main.act.httpweb;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.warmtel.android.R;
import com.warmtel.android.common.configs.Logs;
import com.warmtel.android.main.html.JsonParseNews;
import com.warmtel.android.main.html.JsonParseNews.OnNewsInfoListener;
import com.warmtel.android.main.useful.NewsInfo;
import com.warmtel.android.main.useful.jsonobj.NewsInfoUseObj;
import com.warmtel.android.main.useful.jsonobj.ReadNewsImagSrcObj;

@SuppressLint("SetJavaScriptEnabled")
public class NewsShowWebAct extends Activity  implements OnNewsInfoListener{
	private WebView mWebView;
	private static String mineType ="txt/html";
	private static String encoding="UTF-8";
	
	
	private String url1="http://money.163.com/14/1024/10/A9AKMP3G00254TI5.html";//竖屏只能显示一半,横屏可以显示完整
	private String url2="http://3g.163.com/news/14/1024/14/A9B0AIR00001124J.html";//可以完整显示网页
	private String url3="http://c.m.163.com/nc/article/A9B0AIR00001124J/full.html";//可显示完整，但是带Html标签，需要处理
	private String docIDSupportUrl="http://www.thepaper.cn/newsDetail_forward_1273158";//这个网址在docID中提供,前三个是新闻列表接口提供,可以完整显示网页
	
	private JsonParseNews mJsonParseNews;
	private TextView mNewsTitle,mNewsSource,mNewsCount,mNewsInfo;
	private ImageView mNewsImg,mLoadImg;
	private RelativeLayout mLoadLayout;
	private ScrollView mScrollView;
	private int mShortAnimationDuration;
	
	
	private Picasso mPicasso;
	private RequestCreator mRequestCreator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_news_layout_update);
//		mLoadImg=(ImageView)findViewById(R.id.web_news_load_img);
//		Animation animation=AnimationUtils.loadAnimation(this, R.anim.news_info_load_rorate);
//		mLoadImg.startAnimation(animation);
		init();
	}
	
	public void init()
	{
		mJsonParseNews=new JsonParseNews();
		mNewsTitle=(TextView)findViewById(R.id.web_news_title);
		mNewsSource=(TextView)findViewById(R.id.web_news_source);
		mNewsCount=(TextView)findViewById(R.id.web_news_img_count);
		mNewsInfo=(TextView)findViewById(R.id.web_news_infoTxt);
		mNewsImg=(ImageView)findViewById(R.id.web_news_img);
		mLoadImg=(ImageView)findViewById(R.id.web_news_load_img);
		mScrollView=(ScrollView)findViewById(R.id.web_scrollview_layout);
		/**
		 * View间渐变
		 */
		//初始化隐藏这个View
		mNewsInfo.setVisibility(View.GONE);
		// 获取并缓存系统默认的“短”时长,也可以自定义
//        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mShortAnimationDuration = 1000;
        
		mLoadLayout=(RelativeLayout)findViewById(R.id.web_news_load_layout);
		setLoadImgAnim();
		getNewsInfoData();
		mJsonParseNews.setOnNewsInfoListener(this);
	}
	public void setLoadImgAnim()
	{
		Animation animation=AnimationUtils.loadAnimation(this, R.anim.news_info_load_rorate);
		mLoadImg.startAnimation(animation);//启动动画
	}
	//方法一
	public void getNewsInfoData()
	{
		Logs.v("getNewsInfoData 开始");
		String docid=getIntent().getStringExtra(NewsInfo.sDocID);
//		Logs.e(">>>>>>>>>>>>>>>>>>>>>>>docid:"+docid);
		mJsonParseNews.getNewsInfoHttpConn(docid);
//		String newsInfoUrl=NewsInfo.sNewsInfoHead+docid+NewsInfo.sNewsInfoTail;
//		Logs.v(newsInfoUrl);
		Logs.v("getNewsInfoData 结束");
	}
	public void setViewInfo(NewsInfoUseObj newsInfoObj)
	{
		mLoadLayout.setVisibility(View.GONE);
		mNewsTitle.setText(newsInfoObj.getTitle());
		mNewsSource.setText("来源:"+newsInfoObj.getSource()+" "+newsInfoObj.getPtime());
		if((newsInfoObj.getPicUrl())!=null)
		{
			mPicasso=Picasso.with(this);
			mRequestCreator=mPicasso.load(newsInfoObj.getPicUrl());
			mRequestCreator.into(mNewsImg);
					
		mNewsCount.setVisibility(View.VISIBLE);
		mNewsCount.setText("共"+newsInfoObj.getPicCount()+"张");
		mNewsCount.getBackground().setAlpha(100);
		}else
		{
			mNewsImg.setVisibility(View.GONE);
			mNewsCount.setVisibility(View.GONE);
		}
		mNewsInfo.setText(Html.fromHtml(newsInfoObj.getBody()));
		crossfade();
		Logs.v("setViewInfo 结束");
	}
	
	private String mTitle;
	private ArrayList<ReadNewsImagSrcObj> mImg;
	@Override
	public void OnNewsInfo(NewsInfoUseObj newsInfoObj) {
		Logs.v("OnNewsInfo  开始");
		setViewInfo(newsInfoObj);
		mTitle=newsInfoObj.getTitle();
		mImg=(ArrayList<ReadNewsImagSrcObj>) newsInfoObj.getImg();
	}
	

	
	public void onBackClicked(View v)
	{
		finish();
	}
	public void onShowPicNewsClicked(View v)
	{
		Toast.makeText(this, mNewsTitle.getText(), Toast.LENGTH_SHORT).show();
		Intent intent=new Intent("scxh.intent.action.readNewsPicAct");
		intent.putExtra("title", mTitle);
		intent.putParcelableArrayListExtra("imginfo", mImg);
		startActivity(intent);
	}
	
	/**
	 * View渐变动画
	 */
	private void crossfade() {

	    // 设置内容View为0%的不透明度，但是状态为“可见”，
	    // 因此在动画过程中是一直可见的（但是为全透明）。
		mNewsInfo.setAlpha(0f);
		mNewsInfo.setVisibility(View.VISIBLE);

	    // 开始动画内容View到100%的不透明度，然后清除所有设置在View上的动画监听器。
		mNewsInfo.animate()
	            .alpha(1f)
	            .setDuration(mShortAnimationDuration)
	            .setListener(null);

	    // 加载View开始动画逐渐变为0%的不透明度，
	    // 动画结束后，设置可见性为GONE（消失）作为一个优化步骤
	    //（它将不再参与布局的传递等过程）
		mLoadLayout.animate()
	            .alpha(0f)
	            .setDuration(mShortAnimationDuration)
	            .setListener(new AnimatorListenerAdapter() {
	                @Override
	                public void onAnimationEnd(Animator animation) {
	                	mLoadLayout.setVisibility(View.GONE);
	                }
	            });
	}
	

	
	
	//webView相关的
	//mWebView=(WebView)findViewById(R.id.webView_news);
//	String  data="<html>"+"<body>"+"我是测试程序<br>"+"<body>"+"</html>";
//	mWebView.loadDataWithBaseURL(null, data, "txt/html", "utf-8", null);
//	mWebView.getSettings().setJavaScriptEnabled(true);  
//	mWebView.setWebChromeClient(new WebChromeClient());  
/*	//方法一
	public String getNewsInfoData(Bundle readNews,WebView webView)
	{
		String docid=readNews.getString(NewsInfo.sDocID);
		String newsInfoUrl=NewsInfo.sNewsInfoHead+docid+NewsInfo.sNewsInfoTail;
		Logs.v(newsInfoUrl);
//		webView.setWebViewClient(new WebViewClient(){
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
////				view.loadUrl(url);
//				String  data="<html>我是测试程序</html>";
//				view.loadDataWithBaseURL(null, data, "txt/html", "UTF-8", null);
//				return true;
//			}
//		});
//			webView.loadData(URLEncoder.encode(data, encoding), mineType, encoding);
		return newsInfoUrl;
	}
/*	@Override
	public void OnNewsInfo(NewsInfoUseObj newsInfoObj) {
		// TODO Auto-generated method stub
//		mWebView.setWebViewClient(new WebViewClient(){
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				view.loadUrl(url);
//				return true;
//			}
//		});
		WebSettings settings = mWebView.getSettings(); 
		settings.setJavaScriptEnabled(true);
//		mWebView.loadUrl(newsInfoObj.getBody());
//		mWebView.loadData(newsInfoObj.getBody(), "txt/html", "UTF-8");
		mWebView.loadDataWithBaseURL(null, newsInfoObj.getBody(), "txt/html", "utf-8", null);
//		mWebView.setInitialScale(39);//设置适应全屏  39 为竖屏   57适应横屏
		settings.setUseWideViewPort(true); //设置为双击放大字体
//        settings.setLoadWithOverviewMode(true); 
		mWebView.setWebChromeClient(new WebChromeClient());  
	}
*/	
}
