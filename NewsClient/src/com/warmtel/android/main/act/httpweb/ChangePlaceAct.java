package com.warmtel.android.main.act.httpweb;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.warmtel.android.R;
import com.warmtel.android.common.configs.Logs;
import com.warmtel.android.main.html.JsonParseNews;
import com.warmtel.android.main.useful.jsonobj.CityAllInfo;

@SuppressLint("ResourceAsColor")
public class ChangePlaceAct extends FragmentActivity implements OnChildClickListener,OnGroupClickListener{
	private ExpandableListView mExpandableListView;
	private AutoCompleteTextView mAutoTxt;
	private List<String> mGroups;
	private List<List<String>> mChildren;
	private JsonParseNews mJsonParseNews;
	private WeatherCityAdapter mWeatherCityAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
		setContentView(R.layout.weather_change_place_layout);
		initView();
	}

	public void initView() {
		mExpandableListView = (ExpandableListView) findViewById(R.id.weather_city_expand_list);
		mExpandableListView.setAlwaysDrawnWithCacheEnabled(true);
		mAutoTxt = (AutoCompleteTextView) findViewById(R.id.weather_auto_complete_txt);
		mJsonParseNews = new JsonParseNews();
		mWeatherCityAdapter=new WeatherCityAdapter(this);
		getData();
		mExpandableListView.setAdapter(mWeatherCityAdapter);
		//设置为默认展开
		Logs.v(mExpandableListView.getCount()+">>>mExpandableListView");
		 mWeatherCityAdapter.setGroups(mGroups);
		 mWeatherCityAdapter.setChildren(mChildren);
		 mExpandableListView.setOnGroupClickListener(this);
		 mExpandableListView.setOnChildClickListener(this);
		for(int i=0;i<mGroups.size();i++)
		{
			mExpandableListView.expandGroup(i);
		}
	}

	public void getData() {
		CityAllInfo city=mJsonParseNews.getCityInfo2Object(this);
		Logs.v("city>>>>"+city.getCity_info().get(1).getProvince());
		Logs.v("省份个数："+city.getCity_info().size());
		mGroups=new ArrayList<String>();
		mChildren=new ArrayList<List<String>>();
		for (int i = 0; i < city.getCity_info().size(); i++) {
			mGroups.add(city.getCity_info().get(i).getProvince());
			List<String> childItem = new ArrayList<String>();
			for (int j = 0; j < city.getCity_info().get(i).getCity().size(); j++) {
				childItem.add(city.getCity_info().get(i).getCity().get(j)
						.getCity_name());
			}
			mChildren.add(childItem);
		}
	}

	public void onBackToWeatherClicked(View v) {
		finish();
	}

	class WeatherCityAdapter extends BaseExpandableListAdapter {
		List<String> groups=new ArrayList<String>();
		List<List<String>> children=new ArrayList<List<String>>();
//		List<String> childItem=new ArrayList<String>();
		Context  context;
		LayoutInflater inflater;
		public WeatherCityAdapter(Context ctx)
		{
			context=ctx;
			inflater=LayoutInflater.from(ctx);
		}
		public void setGroups(List<String> groups) {
			this.groups = groups;
		}
		public void setChildren(List<List<String>> children) {
			this.children = children;
		}
//		public void setChildItem(List<String> childItem) {
//			this.childItem = childItem;
//		}


		@Override
		public int getGroupCount() {
			return groups.size();
		}
		@Override
		public int getChildrenCount(int groupPosition) {
			return children.get(groupPosition).size();
		}
		@Override
		public Object getGroup(int groupPosition) {
			return groups.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return children.get(groupPosition).get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			TextView mGroup_txt;
			if(convertView==null)
			{
			convertView=inflater.inflate(R.layout.weather_place_group_layout, null);
			mGroup_txt=(TextView)convertView.findViewById(R.id.groupItem);
			convertView.setTag(mGroup_txt);
			}
			/*判断是否group张开，来分别设置背景图*/  
//			if(isExpanded)
//			{
//				convertView.setBackgroundColor(R.color.azure);
//			}else
//			{
//				convertView.setBackgroundColor(R.color.lightgray);
//			}
			mGroup_txt=(TextView)convertView.getTag();
			mGroup_txt.setText(groups.get(groupPosition).toString());
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			TextView mChild_txt;
			if(convertView==null)
			{
			convertView=inflater.inflate(R.layout.weather_place_child_layout, null);
			mChild_txt=(TextView)convertView.findViewById(R.id.childItem);
			convertView.setTag(mChild_txt);
			}
			/*判断是否是最后一项，最后一项设计特殊的背景*/ 
//			if(isLastChild)
//			{
//				convertView.setBackgroundColor(R.color.orange);
//			}else
//			{
//				convertView.setBackgroundColor(R.color.red);
//			}
			mChild_txt=(TextView)convertView.getTag();
			mChild_txt.setText(getChild(groupPosition, childPosition).toString());
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}
		/**
		 *  Whether the child at the specified position is selectable.
		 *  在指定位置的子项目是否可选，true表示可选
		 */
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		
		return false;
	}
	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		TextView childeItem=(TextView) v.findViewById(R.id.childItem);
			Toast.makeText(this, "点击了第"+(groupPosition+1)+"组的第"+(childPosition+1)+"项。"+childeItem.getText(), Toast.LENGTH_SHORT).show();
		Intent intent=new Intent("scxh.intent.action.weatherShow");	
		intent.putExtra("PlaceName", childeItem.getText());
		startActivity(intent);
		finish();	
		return false;
	}
}
