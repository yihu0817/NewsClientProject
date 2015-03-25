package com.warmtel.android.main.act.httpweb;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.warmtel.android.R;
import com.warmtel.android.main.fragment.PicNewsFragment;

/**
 * 
 * @author Administrator
 *PicNewsAct---->
 *PicNewsFragment(添加多个PicNewsFragPicasso  包括子标题和TitleBar)--->
 *PicNewsFragPicasso(图片List展示页)--->
 *PicDetailFragmentAct(Viewpager展示页)---->
 *PicDetailFrag(每一张图片的展示页 PicDetail)
 *
 */
public class PicNewsAct extends FragmentActivity{
	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.pic_news_act_layout);
		getSupportFragmentManager().beginTransaction().add(R.id.pic_news_act_layout,new PicNewsFragment()).commit();
	}
}
