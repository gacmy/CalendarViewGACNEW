package com.gac.calendarviewgac;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements CalendarViewGAC.OnDateSelectedListener{

    HashMap<String,Integer> map = TestData.getMap();
    private RelativeLayout view;
    private ViewPager vp;
    CalendarPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarViewGAC calendar = (CalendarViewGAC)findViewById(R.id.calendar);
        calendar.setOnDateSelectedLintener(this);
        calendar.setDecorator(new TextDecorator(map));

    }




    public  void  print(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSelected(DayView view) {
        view.setSelected();
        print(view.getDate().toString());
    }

}
