package com.example.mycalender.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;

public class CalendarOptions {
	/**
	 * 圆形
	 */
	public static final int DAY_CHECKED_TYPE_CIRCLE = 1;
	/**
	 * 矩形
	 */
	public static final int DAY_CHECKED_TYPE_RECTANGLE = 2;

	private static CalendarOptions calendarOptions = null;
	private static Context mContext;

	private int dayWidth;// dayView宽
	private int dayHeight;// dayView高
	private float solarSize;// sp
	private float lunarSize;// sp
	private String solarText = "";
	private String lunarText = "";
	private int solarCalendarColor = Color.BLACK;// 阳历 文字颜色
	private int lunarCalendarColor = Color.GRAY;// 农历 文字颜色
	private int bgDefaultColor = Color.WHITE;// 默认的背景颜色
	private int bgSelectedColor = Color.CYAN;// 选中后的背景颜色
	private int dayCheckedType = DAY_CHECKED_TYPE_CIRCLE;// 背景形状类型；1：圆形，2：矩形

	private CalendarOptions() {
		setSolarSize(15);
		setLunarSize(10);
	}

	public static CalendarOptions getInstance(Context context) {
		mContext = context;
		if (null == calendarOptions) {
			calendarOptions = new CalendarOptions();
			// TODO 这里面要给默认值
		}
		
		return calendarOptions;
	}

	public int getDayWidth() {
		return dayWidth;
	}

	public CalendarOptions setDayWidth(int dayWidth) {
		this.dayWidth = dayWidth;
		return this;
	}

	public int getDayHeight() {
		return dayHeight;
	}

	public CalendarOptions setDayHeight(int dayHeight) {
		this.dayHeight = dayHeight;
		return this;
	}

	public int getSolarCalendarColor() {
		return solarCalendarColor;
	}

	public CalendarOptions setSolarCalendarColor(int solarCalendarColor) {
		this.solarCalendarColor = solarCalendarColor;
		return this;
	}

	public int getLunarCalendarColor() {
		return lunarCalendarColor;
	}

	public CalendarOptions setLunarCalendarColor(int lunarCalendarColor) {
		this.lunarCalendarColor = lunarCalendarColor;
		return this;
	}

	public int getBgDefaultColor() {
		return bgDefaultColor;
	}

	public CalendarOptions setBgDefaultColor(int bgDefaultColor) {
		this.bgDefaultColor = bgDefaultColor;
		return this;
	}

	public int getBgSelectedColor() {
		return bgSelectedColor;
	}

	public CalendarOptions setBgSelectedColor(int bgSelectedColor) {
		this.bgSelectedColor = bgSelectedColor;
		return this;
	}

	public int getDayCheckedType() {
		return dayCheckedType;
	}

	public CalendarOptions setDayCheckedType(int type) {
		this.dayCheckedType = type;
		return this;
	}

	public float getSolarSize() {
		return solarSize;
	}

	public CalendarOptions setSolarSize(float solarSize) {
		this.solarSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				solarSize, mContext.getResources().getDisplayMetrics());
		return this;
	}

	public float getLunarSize() {
		return lunarSize;
	}

	public CalendarOptions setLunarSize(float lunarSize) {
		this.lunarSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				lunarSize, mContext.getResources().getDisplayMetrics());
		return this;
	}

	public String getSolarText() {
		return solarText;
	}

	public void setSolarText(String solarText) {
		this.solarText = solarText;
	}

	public String getLunarText() {
		return lunarText;
	}

	public void setLunarText(String lunarText) {
		this.lunarText = lunarText;
	}

}
