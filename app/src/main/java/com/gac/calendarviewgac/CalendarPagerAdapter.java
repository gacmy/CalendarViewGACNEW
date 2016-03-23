package com.gac.calendarviewgac;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/3/21.
 */
public class CalendarPagerAdapter extends PagerAdapter{

    private  CalendarViewGAC view;
    private int currentPosition = -1;
    private Context context;
    CalendarPagerView pager;//当前这个月的日历页面
    private CalendarViewGAC calendarViewGAC;//将CalendarViewGAC传递给CalendarPagerView 为了传递日期点击事件
    private List<CalendarPagerView> pagers = new ArrayList<>();//存储缓存的日历页面的集合
    private TextDecorator decorator;//设置DayView控件的扩展界面的日期集合
    public CalendarPagerAdapter(Context c,CalendarViewGAC calendarViewGAC){
        context = c;
        this.calendarViewGAC = calendarViewGAC;
    }


    //通过此方法得到一个CalendarVIewPager页面 根据position位置去获得当前日期
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //Log.e("gac","instaniateItem........");
        pager =  createViewByPosition(position);//new CalendarPagerView(view.getContext());

        pagers.add(pager);
        container.addView(pager);
        //invalidateDecorators();
        invalidateDecorators();
        return pager;
    }

    public void setDecorator(TextDecorator decorator){
        this.decorator = decorator;
        invalidateDecorators();
    }


    private void invalidateDecorators(){
        Log.e("gac","size:"+pagers.size());
        for(int i = 0;i < pagers.size(); i++){
            Log.e("gac","pager current month:"+pagers.get(i).getCurrentMonth());
            pagers.get(i).applayDecorator(decorator);
        }
    }
    private CalendarPagerView createViewByPosition(int position){
        if(currentPosition == -1 || position == 0){
            currentPosition = DateAndPostion.getPosition(CalendarDay.today());

        }
       // Log.e("gac","create ************************View By position!!!!!!!");
       // Log.e("gac","position:"+position);
        CalendarDay day =  DateAndPostion.getCalendarDay(position);
       // Log.e("gac","day:"+day.toString());
        return  new CalendarPagerView(context,day,calendarViewGAC);
    }
    @Override
    public int getCount() {
        return 50000;
    }


//    @Override
//    public int getItemPosition(Object object) {
//        Log.e("gac","getItemPosition");
//        int index = 0;
//        if(object instanceof CalendarPagerView){
//            index = currentPosition;
//        }
//        Log.e("gac","index;"+index);
//        return index;
//    }




    //清除所有选中的日期背景 然后重新设置点击背景
    public void clearSelections(){
        for(int i = 0; i < pagers.size();i++){
            pagers.get(i).clearSelction();
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((CalendarPagerView) object);
        pagers.remove((CalendarPagerView)object);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }



    //这个类用于日期和ViewPager当前位置的转换类 可以将当前位置转换为日期,也可以将日期转换为当前位置
    public static class DateAndPostion{
        public static int getPosition(CalendarDay c){
            int year = c.getYear();
            int month = c.getMonth();
            return (year-1)*12+month;
        }

        public static  CalendarDay getCalendarDay(int position){
            int year = position/12;
            int month = position-(year*12);
            return CalendarDay.from(year+1, month, 1);
        }
    }

}
