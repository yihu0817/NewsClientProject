package com.warmtel.android.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.warmtel.android.R;
import com.warmtel.android.common.configs.Logs;

public class ReadNewsPicDetailFrag extends Fragment{
	private ImageView mDetailImg;
	private ProgressBar mProgressBar;
	private TextView mDetialTitle, mDetailCount, mDetailContent;
	public static ReadNewsPicDetailFrag newInstance(String picUrl,String picTitle,int currentPosition,int picCount)
	{
		ReadNewsPicDetailFrag pf=new ReadNewsPicDetailFrag();
		Bundle args=new Bundle();
		args.putString("picUrl", picUrl);
		args.putString("picTitle", picTitle);
		args.putInt("currentPosition", currentPosition);
		args.putInt("picCount", picCount);
		pf.setArguments(args);
		
		Logs.w("picUrl"+picUrl);
		Logs.w("picTitle"+picTitle);
		Logs.w("currentPosition:"+currentPosition);
		Logs.w("picCount:"+picCount);
		return pf;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.pic_read_news_detail_layout_update, container,
				false);
       init(v);
       getPicInfo();
		return v;
	}
	public void init(View v)
	{
		mDetailImg=(ImageView)v.findViewById(R.id.pic_read_showPic);
		mProgressBar=(ProgressBar)v.findViewById(R.id.pic_read_load_progressbar);
		mDetialTitle=(TextView)v.findViewById(R.id.pic_read_title);
		mDetailCount=(TextView)v.findViewById(R.id.pic_read_count);
		mDetailContent=(TextView)v.findViewById(R.id.pic_read_content);
	}
	//========================要从这个地方开始改 ，需要用到Picasso    
	//2014.10.27.19:47已经搞定
	//Picasso.with(getActivity()).load(StringUrl).into(mDetailImg);
	//努力方向是找到这个图片的StringUrl
	public void getPicInfo()
	{
		Bundle args=getArguments();
		String picTitle=args.getString("picTitle");
		String picUrl=args.getString("picUrl");
		int currentPosition=args.getInt("currentPosition");
		int picCount=args.getInt("picCount");
		setUIinfo(picTitle, picUrl, currentPosition, picCount);
	}
	public void setUIinfo(String picTitle,String picUrl,int currentPosition,int picCount)
	{
		Picasso.with(getActivity()).load(picUrl).into(mDetailImg);
//		mDetialTitle.setText(picTitle);
		mDetailCount.setText(currentPosition+1+"/"+picCount);
		mProgressBar.setVisibility(View.GONE);
	}
}
