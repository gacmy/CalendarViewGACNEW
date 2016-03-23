package com.gac.calendarviewgac;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/3/21.
 */
public class CalendarPagerView extends ViewGroup implements View.OnClickListener{
    //当前月的页面中的 当前月
    private int currentMonth;
    //存储四十二天的日期
    private List<DayView> dayViews = new ArrayList<>();

    //用来传递日期点击事件
    private CalendarViewGAC calendarView;

    public CalendarPagerView(Context context,CalendarDay firstDayCurrentMonth,CalendarViewGAC calendarView) {
        super(context);
        currentMonth = firstDayCurrentMonth.getMonth()+1;
        this.calendarView = calendarView;
        buildWeekViews();
        buildayViews(firstDayCurrentMonth);
    }


    public int getCurrentMonth(){
        return currentMonth;
    }
    public void clearSelction(){
        for(int i = 0; i < dayViews.size();i++){
            dayViews.get(i).setUnselected();
        }
    }

    //建立周日到周一的标题栏
    private void buildWeekViews(){
        TextView tv1 = new TextView(getContext());
        tv1.setText("星期日");
        TextView tv2 = new TextView(getContext());
        tv2.setText("星期一");
        TextView tv3 = new TextView(getContext());
        tv3.setText("星期二");
        TextView tv4 = new TextView(getContext());
        tv4.setText("星期三");
        TextView tv5 = new TextView(getContext());
        tv5.setText("星期四");
        TextView tv6 = new TextView(getContext());
        tv6.setText("星期五");
        TextView tv7 = new TextView(getContext());
        tv7.setText("星期六");
        addView(tv1);
        addView(tv2);
        addView(tv3);
        addView(tv4);
        addView(tv5);
        addView(tv6);
        addView(tv7);
    }
    //根据当前月份生成是四十二天的日期
    private void buildayViews(CalendarDay firstDayCurrentMonth){
        dayViews.clear();
        Log.e("gac","firstDayCurrentMonth:"+firstDayCurrentMonth.toString());
        Calendar calendar = firstDayCurrentMonth.getCalendar();
        Log.e("gac","month:"+calendar.get(Calendar.MONTH));
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        int delta = CalendarUtils.getDayOfWeek(calendar);
        Log.e("gac","delta:"+delta);
        if(delta > 0){

            delta = Calendar.SUNDAY - delta;
            calendar.add(Calendar.DATE,delta);
        }else{

        }
        for(int i = 0; i <  42;i++){
            DayView day = null;
            if(currentMonth != (calendar.get(Calendar.MONTH)+1)){
                day = new DayView(getContext(),calendar,View.INVISIBLE);
            }else{
                day = new DayView(getContext(),calendar,View.VISIBLE);
            }
            if(currentMonth != day.getDate().getMonth()){
                day.setVisibility(INVISIBLE);
            }
            calendar.add(Calendar.DATE, 1);
            day.setOnClickListener(this);
            dayViews.add(day);
            addView(day);
        }
    }

    //给CalendarPagerView 应用设置好的TextSpan
    //TextDecorator 得到传递的日期集合 并且判断该日期需要设置的控件内容类型
    public void applayDecorator(TextDecorator decorator){
        for(int i = 0; i < dayViews.size();i++){
            DayView dayView = dayViews.get(i);

            int mode = decorator.shouldDecorateGAC(dayView.getDay());
            Log.e("gac","date:"+dayView.getDate()+" mode:"+mode);

            dayView.applyTextSpan(mode,currentMonth==dayView.getDate().getMonth());
        }
    }

    //给四十九个控件 进行排列
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        final int parentLeft = 0;

        int childTop = 0;
        int childLeft = parentLeft;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            child.layout(childLeft, childTop, childLeft + width, childTop + height);

            childLeft += width;

            //We should warp every so many children
            if (i % 7 == 6) {
                childLeft = parentLeft;
                childTop += height;
            }

        }
    }

    //测量布局文件 对布局进行测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        final int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (specHeightMode == MeasureSpec.UNSPECIFIED || specWidthMode == MeasureSpec.UNSPECIFIED) {
            throw new IllegalStateException("CalendarPagerView should never be left to decide it's size");
        }

        //The spec width should be a correct multiple
        final int measureTileSize = specWidthSize / 7;

        //Just use the spec sizes
        setMeasuredDimension(specWidthSize, specHeightSize);

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    measureTileSize,
                    MeasureSpec.EXACTLY
            );

            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    measureTileSize,
                    MeasureSpec.EXACTLY
            );

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }

    }

    //将日期的点击事件传给CalendarViewGAC
    @Override
    public void onClick(View v) {
        DayView d = (DayView)v;

        calendarView.onDateClicked(d);
    }
}

