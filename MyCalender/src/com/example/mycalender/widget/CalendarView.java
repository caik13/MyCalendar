package com.example.mycalender.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycalender.widget.DayView.OnDateChangeListener;

public class CalendarView extends ViewGroup implements OnDateChangeListener {
	private WeekView[] weeks = new WeekView[6];
	private HeaderView headerView;
	private Context mContext;
	private int month;
	private int year;

	public CalendarView(Context context, int month, int year) {
		super(context);
		this.month = month;
		this.mContext = context;
		this.year = year;
		init(CalendarOptions.getInstance(context));
	}

	/**
	 * 初始化
	 * 
	 * @param options
	 */
	public void init(CalendarOptions options) {
		headerView = new HeaderView(mContext, month + 1, year);
		addView(headerView);
		for (int i = 0; i < weeks.length; i++) {
			weeks[i] = new WeekView(mContext, options, i, month, year, this);
			addView(weeks[i]);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (int i = 0; i < getChildCount(); i++) {
			View childView = getChildAt(i);
			int height = childView.getMeasuredHeight();
			if (i == 0) {
				childView.layout(0, 0, getWidth(), height);
			} else {
				// 因为layout会执行两次 所以要除以2
				childView.layout(0, i * height / 2, getWidth(), height + i
						* height);
			}
		}
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
		for (WeekView weekView : weeks) {
			weekView.setMonth(month);
		}
	}

	@Override
	public void onDateChange(int dayOfMonth) {
		for (WeekView weekView : weeks) {
			for (DayView dayView : weekView.getDays()) {
				if (dayView.getSolarText() != dayOfMonth) {
					if (dayView.isChecked()) {
						dayView.setChecked(false);
					}
				} else {
					if (!dayView.isChecked()  && dayView.isCurrentMonth()) {
						dayView.setChecked(true);
					}
				}
			}
		}
		// onDateChange();
	}

	public interface onDateChangeListener {
		void onDateChange(int year, int month, int dayOfMonth);
	}
}
