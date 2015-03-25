package com.warmtel.android.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.warmtel.android.R;
import com.warmtel.android.common.configs.Logs;
/**
 * 
 * @author Administrator
 * //注意init()方法里引入布局要用this，不能为null，因为这个是(可选View作为布局的父控件)Optional view to be the parent of the generated hierarchy.
 *	//如果需要此布局在指定的父控件上显示就必须要指定成this。
 *自定义控件步骤:
 *1.在res/layout里  书写Xml布局文件
 *2.java代码自定义类继承某个View。
 *3.在res/values/attrs.xml 文件里设置自定义属性
 *格式:<declare-styleable name="类名">
 *         <attr name="自定义属性名" format="color" />
 *         。。。。。。
 *       </declare-styleable>
 * 4.定义全局私有的属性：（包含（1）属性值的类型和（2）控件类型还有（3）Listener） 
 * private String mTitleTxt;
 * private Drawable mLeftIcon;
 * private Drawable mRightIcon;
 * private OnClickListener onClickListener;
 * private TextView mTitleView;
 * private ImageView mLeftView, mRightView;
 *      
 * 5.自定义类重写构造方法,一定要选用带ArrtibuteSet(属性集合)的,即
 * public XXXX(Context context, AttributeSet attrs)
 * {
 *     (1)引入xml布局,记得第二个参数要用this
 *     LayoutInflater.from(context).inflate(R.layout.mycustom_titlebar_layout, this);
 *     (2)使用TypedArray获得控件属性集合
 *     TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
 *			R.styleable.MyOneCustomTitleBar, 0, 0);
 *			参数解释（1）构造方法传入的属性集合
 *			               （2）res/values/attrs.xml中的《declare-styleable》定义的属性文件
 *			               （3）后两个通常为空，还没仔细研究
 *     (3)定义全局变量获得属性值
 *     mTitleTxt = a.getString(R.styleable.MyOneCustomTitleBar_middle_txt_cust);
 *     	mLeftIcon = a.getDrawable(R.styleable.MyOneCustomTitleBar_left_icon_cust);
 *      (4)判断得到的值是否为空，不为空就设置给布局中相应的控件
 *      if (mLeftIcon != null) {	
 *      setLeftIcon(mLeftIcon);
 *      			}
 * }
 * 6.实现控件的监听事件（一般都实现系统提供的）并提供方法给实例化的控件使用，
 * 目的是传入使用该控件的Activity(或Fragment)实现的相同监听的Listener。
 *   本例程序执行步骤为:
 *   (1)ReadNewsMianFragmentUpdate中实现View.OnClickListener
 *   (2)实例化MyOneCustomTitleBar的控件调用setOnCustomClickListener()传入监听器
 *   mCustomTitleBar.setOnCustomClickListener(this);
 *   (3)MyOneCustomTitleBar中实现OnClickListener后重写onClick方法，
 *   关键是注册系统监听传入MyOneCustomTitleBar中全局的OnClickListener
 *   @Override
 *   public void onClick(View v) {
 *   if(onClickListener != null){
 *   v.setOnClickListener(onClickListener);
 *   }
 *   }
 *   (4)所有要用到该监听的控件都注册监听事件，ex.mLeftView,mRightView
 *   mLeftView.setOnClickListener(this);
 *   mRightView.setOnClickListener(this);
 *   (5)ReadNewsMianFragmentUpdate中重写onClick方法：里面写上具体实现
 *   ================
 *   比如mLeftView点击后   
 *   调用mLeftView.setOnClickListener(this);
 *   ---->查找到MyOneCustomTitleBar中onClick方法的:v.setOnClickListener(onClickListener);
 *   ---->查找到实例化的onClickListener并识别是ReadNewsMianFragmentUpdate中的Listener
 *   ---->调用ReadNewsMianFragmentUpdate中的onClick方法（该方法中写具体实现）
 */
public class MyOneCustomTitleBar extends RelativeLayout implements OnClickListener{
	private String mTitleTxt;
	private Drawable mLeftIcon;
	private Drawable mRightIcon;
	private OnClickListener onClickListener;
	private TextView mTitleView;
	private ImageView mLeftView, mRightView;
	public MyOneCustomTitleBar(Context context) {
		super(context, null);
		init(context);
	}
	public MyOneCustomTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.MyOneCustomTitleBar, 0, 0);
		try {
			mTitleTxt = a.getString(R.styleable.MyOneCustomTitleBar_middle_txt_cust);
			mLeftIcon = a.getDrawable(R.styleable.MyOneCustomTitleBar_left_icon_cust);
			mRightIcon = a.getDrawable(R.styleable.MyOneCustomTitleBar_right_icon_cust);
			if (mTitleTxt != null) {
				mTitleView.setText(mTitleTxt);
			}
			if (mLeftIcon != null) {
				setLeftIcon(mLeftIcon);
			}
			if (mRightIcon != null) {
				setRightIcon(mRightIcon);
			}
		} finally {
			a.recycle();
		}
	}
	
	public MyOneCustomTitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public void init(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		//注意这个构造方法要用this，不能为null，因为这个是(可选View作为布局的父控件)Optional view to be the parent of the generated hierarchy.
		//如果需要此布局在指定的父控件上显示就必须要指定成this。
		inflater.inflate(R.layout.mycustom_titlebar_layout, this);
		mTitleView = (TextView) findViewById(R.id.titlebar_middle_Title);
		Log.v("tag", "mTitleView  :"+mTitleView);
	}

	public String getTitleTxt() {
		return mTitleTxt;
	}

	public void setTitleTxt(String titleTxt) {
		mTitleTxt = titleTxt;
	}

	public Drawable getLeftIcon() {
		return mLeftIcon;
	}

	public void setLeftIcon(Drawable leftIcon) {
		mLeftIcon = leftIcon;
		Log.v("tag", "mLeftView  :"+mLeftView);
		mLeftView = (ImageView) findViewById(R.id.titlebar_left_img);
		mLeftView.setVisibility(VISIBLE);
		mLeftView.setImageDrawable(leftIcon);
		//实现系统的OnClickListener传this.
		mLeftView.setOnClickListener(this);
	}

	public Drawable getRightIcon() {
		return mRightIcon;
	}

	public void setRightIcon(Drawable rightIcon) {
		mRightIcon = rightIcon;
		mRightView = (ImageView) findViewById(R.id.titlebar_left_img);
		mRightView.setVisibility(VISIBLE);
		mRightView.setImageDrawable(rightIcon);
		mRightView.setOnClickListener(this);
	}

	public void setOnCustomClickListener(OnClickListener l) {
		Logs.v("MyOneCustomTitleBar   setOnCustomClickListener");
		this.onClickListener = l;
	}
	@Override
	public void onClick(View v) {
		if(onClickListener != null){
			Logs.v("MyOneCustomTitleBar   onClick");
			v.setOnClickListener(onClickListener);
		}
	}
}
