package com.example.mycalender.widget;

import java.util.Calendar;
import java.util.Date;

import com.example.mycalender.widget.DayView.OnDateChangeListener;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

public class WeekView extends ViewGroup {
	private Context mContext;

	// 一周7天
	private DayView[] days = new DayView[7];
	private CalendarOptions options;
	private int weekNum;
	private int month;
	private int year;
	private Calendar calendar;

	private OnDateChangeListener onDateChangeListener;

	// public WeekView(Context context) {
	// this(context, null);
	// }
	//
	// public WeekView(Context context, AttributeSet attrs) {
	// this(context, attrs, 0, CalendarOptions.getInstance(context), 0);// TODO
	// }

	public WeekView(Context context, CalendarOptions options, int weekNum, int month,
			int year,OnDateChangeListener onDateChangeListener) {
		super(context);
		this.mContext = context;
		this.options = options;
		this.weekNum = weekNum;
		this.onDateChangeListener = onDateChangeListener;
		this.year = year;
		calendar = Calendar.getInstance();
//		if(month >= 12){
//			calendar.set(Calendar.YEAR, year);
//			month -= 12;
//		}else{
			this.month = month;
//		}
		init();
	}

	private void init() {
		calendar.clear();
		calendar.set(Calendar.MONTH, month);
		
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		int maxWeek = calendar.get(Calendar.WEEK_OF_MONTH);
		int weekDiff = weekNum + 1 - maxWeek;
		if (weekDiff > 0) {
			calendar.set(Calendar.WEEK_OF_MONTH, maxWeek);
			calendar.set(Calendar.DAY_OF_WEEK, 7);
			int maxDay = calendar.get(Calendar.DATE);
			for (int i = 0; i < 7; i++) {
				if(maxDay > 20){
					days[i] = new DayView(mContext, null, 0, options, i + 1 + "");
				}else{
					days[i] = new DayView(mContext, null, 0, options, maxDay
							* weekDiff + i + 1 + "");
				}
				addView(days[i]);
			}
		} else {
			// 设置当前为第几周
			calendar.set(Calendar.WEEK_OF_MONTH, weekNum + 1);
			for (int i = 0; i < days.length; i++) {
				calendar.set(Calendar.DAY_OF_WEEK, i + 1);// 设置当前为本周第几天
				days[i] = new DayView(mContext, null, 0, options,
						calendar.get(Calendar.DATE) + "");

				if (weekNum == 0 && calendar.get(Calendar.DATE) > 10
						|| weekNum > 3 && calendar.get(Calendar.DATE) < 10) {
					// 上个月 和 下个月
				} else {
					days[i].setOnDateChangeListener(onDateChangeListener);
					days[i].setCurrentMonth(true);
				}
				addView(days[i]);
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int parentWidthSize = MeasureSpec.getSize(widthMeasureSpec);
		int paretnHeightSize = MeasureSpec.getSize(heightMeasureSpec);

		for (int i = 0; i < getChildCount(); i++) {
			View childView = getChildAt(i);
			// 设定子view宽高
			childView.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			int width = childView.getMeasuredWidth();
			int height = childView.getMeasuredHeight();

			parentWidthSize += width;
			paretnHeightSize = height;
		}
		setMeasuredDimension(parentWidthSize, paretnHeightSize);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (int i = 0; i < getChildCount(); i++) {
			View childView = getChildAt(i);
			int width = childView.getMeasuredWidth();
			childView.layout(l + i * width, t, r, b);
		}
	}

	public DayView[] getDays() {
		return days;
	}
	
	public void setMonth(int month) {
		this.month = month;
//		days.
		init();
//		calendar.clear();
//		calendar.set(Calendar.MONTH, month);
//		
//		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//		
//		int maxWeek = calendar.get(Calendar.WEEK_OF_MONTH);
//		int weekDiff = weekNum + 1 - maxWeek;
//		if (weekDiff > 0) {
//			calendar.set(Calendar.WEEK_OF_MONTH, maxWeek);
//			calendar.set(Calendar.DAY_OF_WEEK, 7);
//			int maxDay = calendar.get(Calendar.DATE);
//			for (int i = 0; i < 7; i++) {
//				days[i].setSolarText(maxDay * weekDiff + i + 1 + ""); 
//			}
//		} else {
//			// 设置当前为第几周
//			calendar.set(Calendar.WEEK_OF_MONTH, weekNum + 1);
//			for (int i = 0; i < days.length; i++) {
//				calendar.set(Calendar.DAY_OF_WEEK, i + 1);// 设置当前为本周第几天
//
//				days[i].setSolarText(calendar.get(Calendar.DATE) + ""); 
//			}
//		}
	}

}
