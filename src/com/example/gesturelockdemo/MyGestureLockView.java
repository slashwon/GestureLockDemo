package com.example.gesturelockdemo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyGestureLockView extends View {

	private static final int COUNT_PER_LINE = 5;
	private Paint mPaint;
	private int mHeight;
	private int mWidth;
	private float unit;
	private float touchX = -10000;
	private float touchY = -10000;
	private static final int COLOR_ORIGINAL = Color.GRAY;// 原始颜色
	private static final int COLOR_TOUCHED = Color.GREEN;// 点击后的颜色
	private static final int COLOR_WRONG = Color.RED ;//密码错误的颜色
	
	private List<MyCircle> circleList = new ArrayList<MyCircle>();
	private List<MyCircle> touchedList = new ArrayList<MyCircle>();
	boolean codeRight = true ;
	
	public MyGestureLockView(Context context) {
		this(context, null);
	}

	public MyGestureLockView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyGestureLockView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		// 初始化圆
		for (int i = 1; i <= COUNT_PER_LINE; i += 2) {
			for (int j = 1; j <= COUNT_PER_LINE; j += 2) {
				float centerX = j * unit - unit / 2;
				float centerY = i * unit - unit / 2; // 计算圆心的坐标
				MyCircle circle = new MyCircle(centerX, centerY, unit / 2,
						false);
				circleList.add(circle);
			}
		}
		Log.e("test", "circleList.size = " + circleList.size());
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.e("test", "w = " + w + ";h = " + h);
		this.mWidth = w;
		this.mHeight = h;
		int unitLength = mWidth > mHeight ? mHeight : mWidth;
		unit = (float) unitLength / COUNT_PER_LINE;
		init();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 绘制基础图形
		for (int i = 0; i < circleList.size(); i++) {
			MyCircle c = circleList.get(i);
			int color = COLOR_ORIGINAL;
			mPaint.setColor(color);
			canvas.drawCircle(c.getCenterX(), c.getCenterY(), c.getRadius(),
					mPaint);
			if(c.isTouched()){
				color = codeRight ? COLOR_TOUCHED : COLOR_WRONG ;
			}
			mPaint.setColor(color);
			canvas.drawCircle(c.getCenterX(), c.getCenterY(),
					c.getRadius() / 2, mPaint);
		}
		if (touchedList.size() > 1) {
			// 根据触摸点的顺序绘制直线
			for (int i = 0; i < touchedList.size() - 1; i++) {
				int color = codeRight ? COLOR_TOUCHED: COLOR_WRONG;
				mPaint.setColor(color);
				MyCircle c1 = touchedList.get(i);
				MyCircle c2 = touchedList.get(i + 1);
				mPaint.setStrokeWidth(unit/16);
				canvas.drawLine(c1.getCenterX(), c1.getCenterY(),
						c2.getCenterX(), c2.getCenterY(), mPaint);
			}
		}
	}

	@SuppressLint("ClickableViewAccessibility") @Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			for (MyCircle c : circleList) {
				c.setTouched(false);
			}
			touchedList.clear();
			codeRight = true ;
		case MotionEvent.ACTION_MOVE:
			touchX = event.getX();
			touchY = event.getY();
			// 判断触摸点是否在单元格内
			for (MyCircle c : circleList) {
				if (Math.abs(touchX - c.getCenterX()) <= unit / 2
						&& Math.abs(touchY - c.getCenterY()) <= unit / 2) {
					c.setTouched(true);
					if (!touchedList.contains(c)) {
						touchedList.add(c);
					}
				}
			}
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			// TODO 检查是否正确
			codeRight = false;
			invalidate();
			break;
		}
		return true; 
	}
	
}
