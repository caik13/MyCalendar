package com.example.mycalender;

import com.example.mycalender.widget.CalendarView;
import com.example.mycalender.widget.MyViewPager;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        MyViewPager calendarView = (MyViewPager) findViewById(R.id.calendar_view);
//
//        CalendarView[] aa = calendarView.get();
//        
//        for (CalendarView calendarView2 : aa) {
//        	calendarView2.setMonth(8);
//		}
    }

}
