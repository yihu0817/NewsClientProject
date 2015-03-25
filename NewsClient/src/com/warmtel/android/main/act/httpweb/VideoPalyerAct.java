package com.warmtel.android.main.act.httpweb;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.utils.ContextUtils;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.warmtel.android.R;
import com.warmtel.android.common.configs.Logs;

public class VideoPalyerAct extends FragmentActivity implements OnInfoListener,
OnBufferingUpdateListener, OnPreparedListener{
	private TextView mLoadRateTxt, mVideoTitle;
//	private ImageView mVideoDoneImg;
	private ProgressBar mProgressbar;
	private VideoView mVideoView;
	private Uri mUri;
	private String mPlayerUrl;
	private String mTitle;

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		init();//初始化之前调用
		setContentView(R.layout.video_player_layout);
		Logs.v("我需要的"+ContextUtils.getDataDir(this) + "libs/");
		initView();
	}
/**
 * 检测libs是否引入
 */
	public void init() {
		try {
			if (!LibsChecker.checkVitamioLibs(this))
				return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Vitamio的videoView设置
	 */
	public void initView()
	{
		mPlayerUrl=getIntent().getStringExtra("playUrl");
		mTitle=getIntent().getStringExtra("videoName");
		 if ("".equals(mPlayerUrl) || mPlayerUrl == null) {
	            Toast.makeText(this, "请求地址错误",Toast.LENGTH_SHORT).show();;
	            finish();
	        }
		mLoadRateTxt=(TextView)findViewById(R.id.video_load_rate);
		mVideoTitle=(TextView)findViewById(R.id.video_title);
//		mVideoDoneImg=(ImageView)findViewById(R.id.video_end_pic);
		mProgressbar=(ProgressBar)findViewById(R.id.video_probar);
		mVideoView=(VideoView)findViewById(R.id.video_buffer);
		mUri=Uri.parse(mPlayerUrl);
		mVideoView.setVideoURI(mUri);//设置播放地址
		mVideoView.setMediaController(new MediaController(this));
		mVideoView.requestFocus();//请求焦点
		mVideoView.setOnInfoListener(this);
		mVideoView.setOnBufferingUpdateListener(this);
		mVideoView.setOnPreparedListener(this);
		
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mp.setPlaybackSpeed(1.0f);
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		mLoadRateTxt.setText(percent + "%");
		//这不一样 mVideoView.setFileName(title);
		mVideoTitle.setText(mTitle);
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		Logs.v("what:"+what);
		switch (what) {
		case MediaPlayer.MEDIA_INFO_BUFFERING_START:
			if(mVideoView.isPlaying())
			{
				mVideoView.pause();
				mProgressbar.setVisibility(View.VISIBLE);
				mLoadRateTxt.setText("");
				mLoadRateTxt.setVisibility(View.VISIBLE);
			}
			break;
		case MediaPlayer.MEDIA_INFO_BUFFERING_END:
//			mVideoDoneImg.setVisibility(View.VISIBLE);
				mVideoView.start();
				mProgressbar.setVisibility(View.GONE);
				mLoadRateTxt.setVisibility(View.GONE);
			break;
		case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
			break;
		default:
			break;
		}
		return false;
	}

}
