package com.example.mycalender.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 * 
 * 头部年/月
 * 
 * @author caik13
 * 
 */
public class HeaderView extends View {

	private Context mContext;
	private Paint mPaint;
	private int monthText;
	private int year;

	public HeaderView(Context context, int monthText, int year) {
		super(context);
		if(monthText > 12){
			this.monthText = monthText - 12;
		}else{
			this.monthText = monthText;
		}
		this.year = year;

		this.mContext = context;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @return
	 */
	private int getWindowWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.widthPixels;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(getWindowWidth(), (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 50, mContext.getResources()
						.getDisplayMetrics()));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setTextAlign(Paint.Align.CENTER);
		mPaint.setTextSize(TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 15, mContext.getResources()
						.getDisplayMetrics()));
		mPaint.setColor(Color.BLACK);
		canvas.drawText(year + "年" + monthText + "月", getWidth() / 2, getHeight() / 2
				+ mPaint.getFontMetrics().bottom, mPaint);
	}

	public int getMonthText() {
		return monthText;
	}

	public void setMonthText(int monthText) {
		this.monthText = monthText;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
