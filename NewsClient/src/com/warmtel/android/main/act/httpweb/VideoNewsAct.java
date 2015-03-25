package com.warmtel.android.main.act.httpweb;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.warmtel.android.R;
import com.warmtel.android.main.fragment.VideoNewsFragment;

public class VideoNewsAct extends FragmentActivity{
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.video_news_act_layout);
		getSupportFragmentManager().beginTransaction().add(R.id.video_news_act_layout,new VideoNewsFragment()).commit();
	}
}
