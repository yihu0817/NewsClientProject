package com.warmtel.android.main.fragment;

import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.warmtel.android.R;
import com.warmtel.android.main.html.JsonParseNews;
import com.warmtel.android.main.html.JsonParseNews.OnPicDetailListener;
import com.warmtel.android.main.useful.NewsInfo;
import com.warmtel.android.main.useful.jsonobj.PicModelObj;

public class PicDetailFrag extends Fragment implements OnPicDetailListener,OnClickListener{
	private JsonParseNews mJsonParseNews;
	private ImageView mDetailImg;
	private ProgressBar mProgressBar;
	private TextView mDetialTitle, mDetailCount, mDetailContent;
	private int mTotalNum;
	private int mShortAnimationDuration;
	public static PicDetailFrag newInstance(int position)
	{
		PicDetailFrag pf=new PicDetailFrag();
		Bundle args=new Bundle();
		args.putInt("position", position);
		pf.setArguments(args);
		return pf;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.pic_news_detail_layout_update, container,
				false);
       init(v);
       showPicDetail();
		return v;
	}
	
	public void init(View v)
	{
		mDetailImg=(ImageView)v.findViewById(R.id.pic_detail_showPic);
		mProgressBar=(ProgressBar)v.findViewById(R.id.pic_detail_load_progressbar);
		mDetialTitle=(TextView)v.findViewById(R.id.pic_detail_title);
		mDetailCount=(TextView)v.findViewById(R.id.pic_detail_count);
		mDetailContent=(TextView)v.findViewById(R.id.pic_detail_content);
		mShortAnimationDuration=1000;
		mDetailImg.setOnClickListener(this);
	}
	public void showPicDetail() {
		mJsonParseNews = new JsonParseNews();
		String id =getActivity(). getIntent().getStringExtra("ID");
		String picDetailJsonUrl = NewsInfo.getPicDetailUrl(id);
		mJsonParseNews.getPicDetailHttpConn(picDetailJsonUrl);
		mJsonParseNews.setOnPicDetailListener(this);
	}
	@Override
	public void onPicDetailList(List<PicModelObj> picDetailList) {
		Bundle args=getArguments();
		int position=args.getInt("position");
	    System.out.println("onPicDetailList+position>>>>>>"+position);
		showDetail(position, picDetailList);
	}
	public void showDetail(int position,List<PicModelObj> picDetailList)
	{
		mTotalNum=picDetailList.size();
		String picUrl=picDetailList.get(position).getPic().replace("auto", "400x800");
		Picasso.with(getActivity()).load(picUrl).into(mDetailImg);
		mProgressBar.setVisibility(View.GONE);
		mDetialTitle.setText(picDetailList.get(position).getTitle());
		mDetailCount.setText(position+1+"/"+mTotalNum);
		mDetailContent.setText(picDetailList.get(position).getAlt());
	}

	
	/**
	 * View渐变动画
	 */
	private void crossfade() {

	    // 设置内容View为0%的不透明度，但是状态为“可见”，
	    // 因此在动画过程中是一直可见的（但是为全透明）。
		mDetialTitle.setAlpha(0f);
		mDetialTitle.setVisibility(View.VISIBLE);

	    // 开始动画内容View到100%的不透明度，然后清除所有设置在View上的动画监听器。
		mDetialTitle.animate()
	            .alpha(1f)
	            .setDuration(mShortAnimationDuration)
	            .setListener(null);

	    // 加载View开始动画逐渐变为0%的不透明度，
	    // 动画结束后，设置可见性为GONE（消失）作为一个优化步骤
	    //（它将不再参与布局的传递等过程）
		mProgressBar.animate()
	            .alpha(0f)
	            .setDuration(mShortAnimationDuration)
	            .setListener(new AnimatorListenerAdapter() {
	                @Override
	                public void onAnimationEnd(Animator animation) {
	                	mProgressBar.setVisibility(View.GONE);
	                }
	            });
	}
	
	
	public void onShowBigClicked(View v)
	{
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pic_detail_showPic:
			Toast.makeText(getActivity(), mDetialTitle.getText(), Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		
	}
}
