package com.example.mycalender.widget;

import java.util.Calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycalender.utils.DistanceProvider;

public class MyViewPager extends ViewGroup {
	private Context mContext;
	private GestureDetector gestureDetector;
	private DistanceProvider distanceProvider;

	private Calendar calendar;
	private int monthNum;
	private CalendarView[] months = new CalendarView[5];

	private int currentId = 2;// 当前显示的图片的id，从0开始，最大值为getChildCount()-1
	private boolean pageChange = false;
	private boolean left = false;
	private boolean right = false;

	/**
	 * down事件时的x坐标
	 */
	private int firstX;

	public MyViewPager(Context context) {
		this(context, null);
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyViewPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;

		init();
		initMonth();
	}

	private void init() {
		gestureDetector = new GestureDetector(mContext,
				new GestureDetector.OnGestureListener() {

					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						System.out.println("抬起");
						return false;
					}

					@Override
					public void onShowPress(MotionEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public boolean onScroll(MotionEvent e1, MotionEvent e2,
							float distanceX, float distanceY) {
						scrollBy((int) distanceX, 0);
						return false;
					}

					@Override
					public void onLongPress(MotionEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public boolean onDown(MotionEvent e) {
						// TODO Auto-generated method stub
						return false;
					}
				});
		calendar = Calendar.getInstance();
		distanceProvider = new DistanceProvider(mContext);
	}

	private void initMonth() {
		monthNum = calendar.get(Calendar.MONTH);
		months[0] = new CalendarView(mContext, monthNum - 2, 2015);
		months[1] = new CalendarView(mContext, monthNum - 1, 2015);
		months[2] = new CalendarView(mContext, monthNum, 2015);
		months[3] = new CalendarView(mContext, monthNum + 1, 2015);
		months[4] = new CalendarView(mContext, monthNum + 2, 2015);

		for (int j = 0; j < months.length; j++) {
			addView(months[j]);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		// 在判断是否拦截事件的时候 就要同时把事件交给gestureDetector处理，
		gestureDetector.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			firstX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			if (event.getX() - firstX > 5 || firstX - event.getX() > 5) {
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 使用工具来解析触摸事件
		gestureDetector.onTouchEvent(event);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// firstX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:

			int tmpId = 0;
			// System.out.println("currentId=" + currentId);
			// 手指向右滑动超过屏幕的1/2，当前的id应该-1
			if (event.getX() - firstX > getWidth() / 2) {
				tmpId = currentId - 1;
				right = true;
				left = false;
				pageChange = true;
			} else if (firstX - event.getX() > getWidth() / 2) {
				tmpId = currentId + 1;
				right = false;
				left = true;
				pageChange = true;
			} else {
				tmpId = currentId;
				pageChange = false;
			}
			// System.out.println("currentId=" + currentId);

			// 三目运算符的效率比if else效率要高很多
			int childCount = getChildCount();
			currentId = tmpId < 0 ? 0
					: ((tmpId > childCount - 1) ? childCount - 1 : tmpId);

			moveToDest();
			OnPageChange(left, right);
			break;

		default:
			break;
		}
		// 消费掉本次事件
		return true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			view.layout(i * getWidth(), 0, getWidth() * (i + 1), getHeight());
		}

	}

	public CalendarView[] get(){
		return months;
	}
	
	/**
	 * 移动到合理的位置的方法
	 */
	private void moveToDest() {
		/**
		 * 移动起点到终点的距离
		 */
		int distance = currentId * getWidth() - getScrollX();

		/**
		 * 在一段时间里面，平滑移动这段距离
		 */
		// 开启计算偏移量
		distanceProvider.startScroll(getScrollX(), 0, distance, 0);

		invalidate();// invalidate会导致ondraw和computeScroll方法的执行

	}

	private boolean isFrist = false;

	@Override
	public void computeScroll() {
		// 计算滑动偏移量
		if (!distanceProvider.computeScrollOffset()) {
			int newX = (int) distanceProvider.getCurrentX();
			scrollTo(newX, 0);
			// 再次刷新
			invalidate();
		}

		// 初始时显示第三个
		if (!isFrist) {
			isFrist = true;
			scrollTo(getWidth() * 2, 0);
		}
	}

	/**
	 * 
	 */
	public void OnPageChange(boolean left, boolean right) {
		int m = ((CalendarView) getChildAt(currentId)).getMonth() - 1;
		
		if (left) {
			// removeViewAt(currentId - 3);
			// removeViewAt(currentId + 2);
//			months[0].setMonth(m + 2);
			// addView(months[0], currentId + 2);
//			addView(months[0]);
			View view = getChildAt(currentId - 3);
			getChildAt(currentId - 3).layout((currentId + 2) * getWidth(), 0, (currentId + 3) * getWidth(), getHeight());
			requestLayout();
		}
		if (right) {
			// removeViewAt(currentId + 3);
			// removeViewAt(currentId - 2);
//			months[months.length - 1].setMonth(m - 2);
			// addView(months[months.length - 1], currentId - 2);
			getChildAt(currentId + 3).layout((currentId - 2) * getWidth(), 0, getWidth() * (currentId - 1), getHeight());
			requestLayout();
		}
	}
}
