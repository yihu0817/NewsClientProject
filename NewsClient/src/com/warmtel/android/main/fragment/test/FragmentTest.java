package com.warmtel.android.main.fragment.test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warmtel.android.R;

@SuppressLint("ValidFragment")
public class FragmentTest extends Fragment {
	private String title;
	private String info;
	private TextView mTitleTxt,mInfoTxt;
	
	
	public FragmentTest instance()
	{
		FragmentTest ft=new FragmentTest();
		return ft;
	}
	
	public FragmentTest() {
		super();
	}

	public FragmentTest(String title,String info)
	{
		super();
		this.title=title;
		this.info=info;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v=inflater.inflate(R.layout.fragment_test_layout, null);
		mTitleTxt=(TextView)v.findViewById(R.id.fragment_test_title);
		mInfoTxt=(TextView)v.findViewById(R.id.fragment_test_info);
		mTitleTxt.setText(title);
		mInfoTxt.setText(info);
		return v;
	}

}
