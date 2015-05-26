package com.example.mycalender.widget;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Checkable;

public class DayView extends View implements Checkable {
	private CalendarOptions options;
	private OnDateChangeListener onDateChangeListener = null;

	// 是否选中状态
	private boolean isChecked = false;
	private Context mContext;

	private int bgColor;

	private int width;
	private int height;

	private boolean isCurrentMonth = false;
	private String solarText = "";

	// 记录当前日期
	private Date currentDate;

	private Paint paint;

	public DayView(Context context, AttributeSet attrs, int defStyleAttr,
			CalendarOptions calendarOptions, String solarText) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
		this.options = calendarOptions;
		this.solarText = solarText;
		init();
	}

	/**
	 * 
	 */
	private void init() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		bgColor = options.getBgDefaultColor();
		if (options.getDayWidth() == 0 || options.getDayHeight() == 0) {
			// 屏幕宽度/7
			int defaultSize = getWindowWidth() / 7;
			width = defaultSize;
			height = defaultSize;
		} else {
			width = options.getDayWidth();
			height = options.getDayHeight();
		}
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
		setMeasuredDimension(width, height);
	}

	// 参考 http://blog.csdn.net/hursing
	// https://github.com/AigeStudio/DatePicker
	@Override
	protected void onDraw(Canvas canvas) {
		// 给画笔设置背景颜色
		paint.setColor(bgColor);

		// 画背景
		switch (options.getDayCheckedType()) {
		case CalendarOptions.DAY_CHECKED_TYPE_CIRCLE:
			// 画圆
			canvas.drawCircle(width / 2, width / 2, width / 2, paint);
			break;
		case CalendarOptions.DAY_CHECKED_TYPE_RECTANGLE:
			// TODO 画长方形
			// canvas.drawRect(left, top, right, bottom,
			// paint);(targetRect.width() / 2, targetRect.width() / 2,
			// targetRect.width() / 2, paint);
			break;
		}
		
		// 文字居中
		paint.setTextAlign(Paint.Align.CENTER);
		// 设置 阳历 文字大小 颜色
		paint.setTextSize(options.getSolarSize());
		paint.setColor(options.getSolarCalendarColor());

		// 上面文字baseline以下的高度
		float bigTextSize = paint.getFontMetrics().bottom;
		canvas.drawText(solarText, width / 2, height / 2, paint);

		// 设置 农历 文字大小 颜色
		paint.setTextSize(options.getLunarSize());
		paint.setColor(options.getLunarCalendarColor());
		float y = -paint.getFontMetrics().top + paint.getFontMetrics().ascent
				/ 2;
		canvas.drawText(options.getLunarText(), width / 2, height / 2 + y
				+ bigTextSize, paint);
	}

	// invalidate
	public void reView() {
		requestLayout();
		invalidate();
	}

	@Override
	public void setChecked(boolean checked) {
		if (checked) {
			isChecked = true;
			bgColor = options.getBgSelectedColor();
		} else {
			isChecked = false;
			bgColor = options.getBgDefaultColor();
		}
		reView();
	}

	@Override
	public boolean isChecked() {
		return isChecked;
	}

	// 切换状态
	@Override
	public void toggle() {
		setChecked(!isChecked);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (null != onDateChangeListener) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				onDateChangeListener.onDateChange(getSolarText());
				// toggle();
				break;
			default:
				break;
			}
			return true;
		}
		return false;
	}

	public boolean isCurrentMonth() {
		return isCurrentMonth;
	}

	public void setCurrentMonth(boolean isCurrentMonth) {
		this.isCurrentMonth = isCurrentMonth;
	}

	public int getSolarText() {
		return Integer.parseInt(solarText);
	}
	
	public void setSolarText(String day){
		solarText = day;
//		paint = null;
//		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		reView();
	}

	public void setOnDateChangeListener(
			OnDateChangeListener onDateChangeListener) {
		this.onDateChangeListener = onDateChangeListener;
	}

	public interface OnDateChangeListener {
		void onDateChange(int dayOfMonth);
	}
}
