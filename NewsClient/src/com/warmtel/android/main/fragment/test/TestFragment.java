package com.warmtel.android.main.fragment.test;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class TestFragment extends ListFragment {
	private String news[]={"111列表Fragment，用来显示列表视图 ","222列表Fragment，用来显示列表视图 ","333列表Fragment，用来显示列表视图 ","444列表Fragment，用来显示列表视图 ","列表Fragment，用来显示列表视图 "};
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListAdapter adapter=new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, news);
		setListAdapter(adapter);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	

}
