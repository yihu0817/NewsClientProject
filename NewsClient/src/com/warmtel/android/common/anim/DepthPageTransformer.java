package com.warmtel.android.common.anim;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class DepthPageTransformer implements PageTransformer {
	private static final float MIN_SCALE = 0.75f;

	@Override
	public void transformPage(View arg0, float arg1) {
		int pageWidth = arg0.getWidth();

		if (arg1 < -1) { // [-∞ ,-1)
			// 这一页已经是最左边的屏幕页
			arg0.setAlpha(0);
		} else if (arg1 <= 0) { // [-1,0]
			// 向左面滑屏使用默认的过渡动画
			arg0.setAlpha(1);
			arg0.setTranslationX(0);
			arg0.setScaleX(1);
			arg0.setScaleY(1);
		} else if (arg1 <= 1) { // (0,1]
			// 淡出页面
			arg0.setAlpha(1 - arg1);

			// 抵消默认的整页过渡
			arg0.setTranslationX(pageWidth * -arg1);

			// 根据缩放系数变化 (在 MIN_SCALE 和 1 之间变化)
			float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
					* (1 - Math.abs(arg1));
			arg0.setScaleX(scaleFactor);
			arg0.setScaleY(scaleFactor);

		} else { // (1,+∞]
			// 这一页已经是最右边的屏幕页
			arg0.setAlpha(0);
		}
	}

}
