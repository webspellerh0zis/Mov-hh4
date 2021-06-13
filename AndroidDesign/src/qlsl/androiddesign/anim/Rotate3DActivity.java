package qlsl.androiddesign.anim;

import android.view.View;

/**
 * 适合Activity各布局间切换3D翻转动画，暂不清楚兼容Fragment的写法
 * 
 */
public class Rotate3DActivity {

	private int mCenterX = 160;
	private int mCenterY = 0;
	// A
	private View layoutFront;
	// C
	private View layoutBack;
	// B
	private View layoutRight;
	// D
	private View layoutLeft;

	private Rotate3DAnimation leftAnimation;
	private Rotate3DAnimation rightAnimation;

	public static final int toLeft = 0;
	public static final int toRight = 1;

	public void setFrontView(View layoutFront) {
		this.layoutFront = layoutFront;
	}

	public void setRightView(View layoutRight) {
		this.layoutRight = layoutRight;
	}

	public void setBackView(View layoutBack) {
		this.layoutBack = layoutBack;
	}

	public void setLeftView(View layoutLeft) {
		this.layoutLeft = layoutLeft;
	}

	// 左旋转
	public void initFirst() {
		leftAnimation = new Rotate3DAnimation(0, -90, 0.0f, 0.0f, mCenterX,
				mCenterY);
		rightAnimation = new Rotate3DAnimation(90, 0, 0.0f, 0.0f, mCenterX,
				mCenterY);
		leftAnimation.setFillAfter(true);
		leftAnimation.setDuration(1000);
		rightAnimation.setFillAfter(true);
		rightAnimation.setDuration(1000);
	}

	// 右旋转
	public void initSecond() {
		leftAnimation = new Rotate3DAnimation(-90, 0, 0.0f, 0.0f, mCenterX,
				mCenterY);
		rightAnimation = new Rotate3DAnimation(0, 90, 0.0f, 0.0f, mCenterX,
				mCenterY);
		leftAnimation.setFillAfter(true);
		leftAnimation.setDuration(1000);
		rightAnimation.setFillAfter(true);
		rightAnimation.setDuration(1000);
	}

	// B面转到A面所在位置
	public void B2A(Rotate3DAnimation rightAnimation) {
		layoutRight.startAnimation(rightAnimation);
	}

	// D面转到A面所在位置
	public void D2A(Rotate3DAnimation leftAnimation) {
		layoutLeft.startAnimation(leftAnimation);
	}

	// A面转到D面所在位置
	public void A2D(Rotate3DAnimation rightAnimation) {
		layoutFront.startAnimation(rightAnimation);
	}

	// C面转到D面所在位置
	public void C2D(Rotate3DAnimation leftAnimation) {
		layoutBack.startAnimation(leftAnimation);
	}

	// C面转到B面所在位置
	public void C2B(Rotate3DAnimation rightAnimation) {
		layoutBack.startAnimation(rightAnimation);
	}

	// A面转到B面所在位置
	public void A2B(Rotate3DAnimation leftAnimation) {
		layoutFront.startAnimation(leftAnimation);
	}

	// D面转到C面所在位置
	public void D2C(Rotate3DAnimation rightAnimation) {
		layoutLeft.startAnimation(rightAnimation);
	}

	// B面转到C面所在位置
	public void B2C(Rotate3DAnimation leftAnimation) {
		layoutRight.startAnimation(leftAnimation);
	}

	// A在正面时
	public void frontMoveHandle(int to) {
		if (layoutFront == null || layoutBack == null || layoutLeft == null
				|| layoutRight == null) {
			return;
		}
		if (to == toLeft) {
			initFirst();
			layoutFront.startAnimation(leftAnimation);
			B2A(rightAnimation);
		} else if (to == toRight) {
			initSecond();
			layoutFront.startAnimation(rightAnimation);
			D2A(leftAnimation);
		}

	}

	// B位于正面时
	public void rightMoveHandle(int to) {
		if (layoutFront == null || layoutBack == null || layoutLeft == null
				|| layoutRight == null) {
			return;
		}
		if (to == toLeft) {
			initFirst();
			layoutRight.startAnimation(leftAnimation);
			C2B(rightAnimation);
		} else if (to == toRight) {
			initSecond();
			layoutRight.startAnimation(rightAnimation);
			A2B(leftAnimation);
		}

	}

	// D位于正面时
	public void leftMoveHandle(int to) {
		if (layoutFront == null || layoutBack == null || layoutLeft == null
				|| layoutRight == null) {
			return;
		}
		if (to == toLeft) {
			initFirst();
			layoutLeft.startAnimation(leftAnimation);
			A2D(rightAnimation);
		} else if (to == toRight) {
			initSecond();
			layoutLeft.startAnimation(rightAnimation);
			C2D(leftAnimation);
		}

	}

	// C位于正面时
	public void backMoveHandle(int to) {
		if (layoutFront == null || layoutBack == null || layoutLeft == null
				|| layoutRight == null) {
			return;
		}
		if (to == toLeft) {
			initFirst();
			layoutBack.startAnimation(leftAnimation);
			D2C(rightAnimation);
		} else if (to == toRight) {
			initSecond();
			layoutBack.startAnimation(rightAnimation);
			B2C(leftAnimation);
		}

	}

}
