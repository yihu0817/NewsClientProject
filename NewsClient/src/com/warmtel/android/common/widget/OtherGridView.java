package com.warmtel.android.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class OtherGridView extends GridView {

	public OtherGridView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/**
		 * makeMeasureSpec(int size, int mode)
		 *size   the size of the measure specification 测量规格的尺寸
		 *     Integer.MAX_VALUE =0x7FFFFFFF(0111=7)
		 *     >>2 表示右移两位 得到0x1FFFFFFF(0001=1)
		 *mode  he mode of the measure specification 测量规格的模式
		 *      MeasureSpec.AT_MOST      The child can be as large as it wants up to the specified size.
		 *      子控件可以尽可能的大
		 *      MeasureSpec.AT_MOST=2 << MODE_SHIFT(30左移2位   0111 1000=64+32+16+8=120)
		 *      private static final int MODE_SHIFT = 30;(0001 1110)
		 */
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
