package com.example.mycalender.widget;

import java.util.Calendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class CalendarViewPager extends ViewPager {

	public static final int FIRST_ITEM_NUM = 1;
	public static final int LAST_ITEM_NUM = 12;
	private CalendarAdapter calendarAdapter;
	private Context mContext;
	private int currentItem = 1;
	private boolean mIsChanged = false;

	private Calendar calendar;
	private int monthNum;
	private int monthNext;
	private int monthLast;
	private int year;
	private CalendarView[] months = new CalendarView[14];

	public CalendarViewPager(Context context) {
		this(context, null);
	}

	public CalendarViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	private void init() {
		calendar = Calendar.getInstance();
		calendarAdapter = new CalendarAdapter(mContext);
		setAdapter(calendarAdapter);
		setOnPageChangeListener(new CalendarOnPageChangeListener());

		monthNum = calendar.get(Calendar.MONTH);
		year = calendar.get(Calendar.YEAR);
		months[monthNum + 1] = new CalendarView(mContext, monthNum, year);
		setCurrentItem(monthNum + 1);
	}

	class CalendarOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {

			System.out.println(position);
			System.out.println("year:" + year);
			System.out.println();
			
			monthNum = months[position].getMonth();
			monthNext = monthNum + 1;
			monthLast = monthNum - 1;
			if (monthNext > 12) {
				year = year + 1;
				monthNext = 0;
			}
			if (monthLast < -1) {
				year = year - 1;
				monthLast = 11;
			}

			mIsChanged = true;
			if (position > LAST_ITEM_NUM) {
				currentItem = FIRST_ITEM_NUM;
				System.out.println("减");
				months[0] = null;
				months[2] = null;
				months[1] = null;
				months[0] = new CalendarView(mContext, 11, year - 1);
				months[2] = new CalendarView(mContext, 1, year);
				months[1] = new CalendarView(mContext, 0, year);
			} else if (position < FIRST_ITEM_NUM) {
				currentItem = LAST_ITEM_NUM;
				System.out.println("加");
				months[11] = null;
				months[12] = null;
				months[13] = null;
				months[11] = new CalendarView(mContext, 10, year);
				months[12] = new CalendarView(mContext, 11, year);
				months[13] = new CalendarView(mContext, 0, year + 1);
			} else {
				currentItem = position;
				months[position - 1] = null;
				months[position + 1] = null;
				months[position - 1] = new CalendarView(mContext, monthLast, year);
				months[position + 1] = new CalendarView(mContext, monthNext, year);
			}

		}

		@Override
		public void onPageScrollStateChanged(int state) {
			if (ViewPager.SCROLL_STATE_IDLE == state) {
				if (mIsChanged) {
					mIsChanged = false;
					setCurrentItem(currentItem, false);
				}
			}
		}
	}

	/**
	 * 
	 * @author caik
	 * 
	 */
	public class CalendarAdapter extends PagerAdapter {

		private CalendarAdapter(Context context) {
		}

		@Override
		public int getCount() {
			return months.length;
		}

		@Override
		public Object instantiateItem(View container, final int position) {
			((ViewPager) container).addView(months[position]);
			return months[position];
		}

		@Override
		public void destroyItem(View container, int position, Object view) {
			((ViewPager) container).removeView((View) view);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((View) object);
		}
	}
}
