package com.gac.calendarviewgac;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/3/22.
 */
public class CalendarViewGAC extends LinearLayout {
    private CalendarPager pager;//继承ViewPager 为了装载月份页面的视图
    private CalendarPagerAdapter adapter;
    private OnDateSelectedListener listener;//单独日期点击事件的监听器
    private TextView title;//显示年月的标题
    public  interface OnDateSelectedListener{
        void onDateSelected(DayView view);
    }
    public CalendarViewGAC(Context context) {
        this(context, null);
    }

    public CalendarViewGAC(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

public CalendarViewGAC(Context context,AttributeSet attrs,int def){
    super(context, attrs, def);
    setOrientation(VERTICAL);
    initTopBar(context);
    init(context);
    //addView(new CalendarPagerView(context));
}
    public void setOnDateSelectedLintener(OnDateSelectedListener lintener){
        this.listener = lintener;
    }
    //初始化标题栏
    private void initTopBar(Context context){
        title = new TextView(context);
        title.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        title.setText("title");
        title.setTextSize(20);
        title.setGravity(Gravity.CENTER);
        addView(title,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    //初始化CalendarPager 并且填充Adapter
    private void init(Context context){
        pager = new CalendarPager(context);
        adapter = new CalendarPagerAdapter(context,this);
        pager.setAdapter(adapter);
        SimpleDateFormat format = new SimpleDateFormat("MM 月 yyyy 年");
        String date =  format.format((CalendarDay.today().getDate()));
        title.setText(date);
        //设置当前日期为显示时间
        pager.setCurrentItem(CalendarPagerAdapter.DateAndPostion.getPosition(CalendarDay.today()));
        //给pager添加滑动监听器
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CalendarDay day = CalendarPagerAdapter.DateAndPostion.getCalendarDay(position);
              // date = DateUtils.getDateStr(day.getDate());
                SimpleDateFormat format = new SimpleDateFormat("MM 月 yyyy 年");
                String date =  format.format(day.getDate());
                title.setText(date);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        addView(pager,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(CalendarViewGAC.class.getName());
    }


    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(CalendarViewGAC.class.getName());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return pager.dispatchTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int t, int right, int b) {
        final int count = getChildCount();
        Log.e("gac","count:count:"+count);
        final int parentLeft = getPaddingLeft();
        final int parentWidth = right - left - parentLeft - getPaddingRight();

        int childTop = getPaddingTop();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);


            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            int delta = (parentWidth - width) / 2;
            int childLeft = parentLeft + delta;

            child.layout(childLeft, childTop, childLeft + width, childTop + height);

            childTop += height;
        }
    }

    private static int clampSize(int size, int spec) {
        int specMode = MeasureSpec.getMode(spec);
        int specSize = MeasureSpec.getSize(spec);
        switch (specMode) {
            case MeasureSpec.EXACTLY: {
                return specSize;
            }
            case MeasureSpec.AT_MOST: {
                return Math.min(size, specSize);
            }
            case MeasureSpec.UNSPECIFIED:
            default: {
                return size;
            }
        }
    }




    protected void onDateClicked(DayView dayView){
        adapter.clearSelections();

        listener.onDateSelected(dayView);
    }

   public void setDecorator(TextDecorator decorator){
        adapter.setDecorator(decorator);
    }


    class CalendarPager extends ViewPager {
        private boolean pagingEnabled = true;

        public CalendarPager(Context context) {
            super(context);
        }
        CalendarPager(Context context,AttributeSet attrs){
            super(context,attrs);
        }


        public void setChildrenDrawingOrderEnabledCompat(boolean enable) {
            setChildrenDrawingOrderEnabled(enable);
        }
        /**
         * enable disable viewpager scroll
         *
         * @param pagingEnabled false to disable paging, true for paging (default)
         */
        public void setPagingEnabled(boolean pagingEnabled) {
            this.pagingEnabled = pagingEnabled;
        }

        /**
         * @return is this viewpager allowed to page
         */
        public boolean isPagingEnabled() {
            return pagingEnabled;
        }


        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return pagingEnabled && super.onInterceptTouchEvent(ev);
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            return pagingEnabled && super.onTouchEvent(ev);
        }

        @Override
        public boolean canScrollVertically(int direction) {
            /**
             * disables scrolling vertically when paging disabled, fixes scrolling
             * for nested {@link android.support.v4.view.ViewPager}
             */
            return pagingEnabled && super.canScrollVertically(direction);
        }

        @Override
        public boolean canScrollHorizontally(int direction) {
            /**
             * disables scrolling horizontally when paging disabled, fixes scrolling
             * for nested {@link android.support.v4.view.ViewPager}
             */
            return pagingEnabled && super.canScrollHorizontally(direction);
        }



    }



}
