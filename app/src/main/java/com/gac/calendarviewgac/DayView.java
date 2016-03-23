package com.gac.calendarviewgac;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckedTextView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;


/**
 * Created by Administrator on 2016/3/21.
 */
public class DayView extends CheckedTextView {
    //这一天的日期类
    private CalendarDay day;

    //构造函数给个Visible参数 决定日期是否可见
    public DayView(Context context,Calendar day,int visible) {
        super(context);
        setGravity(Gravity.CENTER);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            setTextAlignment(TEXT_ALIGNMENT_CENTER);
        }
        this.day = CalendarDay.from(day);
        setVisibility(visible);
        setText(CalendarUtils.getDay(day)+"");

    }

    //返回这个日期是几号
    public String getLabel(){
        return CalendarUtils.getDay(day.getCalendar())+"";
    }

    //返回真实日期 Calendar类的月份是从0开始的
    public CalendarDay getDate(){
        CalendarDay tempDay = CalendarDay.from(day.getYear(),day.getMonth()+1,day.getDay());
        return tempDay;
    }

    //返回CalendarDay对象 CalendarDay类是对Calendar的封装类
    public  CalendarDay getDay(){
        CalendarDay tempDay = CalendarDay.from(day.getYear(),day.getMonth(),day.getDay());
        return tempDay;
    }
    //设置选择背景
    private Drawable customBackground;

    //设置背景图片
    public void setCustomBackground(Drawable drawable){
        if(drawable == null){
            this.customBackground = null;
        }else{
            this.customBackground = drawable.getConstantState().newDrawable(getResources());
        }
        invalidate();
    }

    //自定义了一个TextSpan 给需要的日期集合设置这个样式 扩展日期控件的内容
    // int mode mode 的不同设置不同的样式
    //visible 决定日期显示不显示 ,如果是当前页面的日期就为true 不是当前页面的月份就不显示
    public  void applyTextSpan(int mode,boolean visible){


        setVisibility(visible ? View.VISIBLE : View.INVISIBLE);

        if(mode == 0 || mode == 1){
            String label = getLabel();
            SpannableString formattedLabel = new SpannableString(getLabel());
            formattedLabel.setSpan(new TextSpan(mode), 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(formattedLabel);
        }else{
            setText(getLabel());
        }

       // invalidate();


    }



    //清空DayView的背景,
    public void setUnselected(){
        customBackground = null;
        invalidate();
    }
    //设置DayVIew 点击日期后的背景
    public void setSelected(){

        customBackground = generateCircleDrawable(Color.GRAY);
        invalidate();
    }

    private final Rect tempRect = new Rect();
    @Override
    protected void onDraw(Canvas canvas) {
        if(customBackground != null){
            canvas.getClipBounds(tempRect);
            customBackground.setBounds(tempRect);
            customBackground.setState(getDrawableState());
            customBackground.draw(canvas);
        }
        super.onDraw(canvas);
    }
    //生成圆的的drawable
    private static Drawable generateCircleDrawable(final int color){
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.setShaderFactory(new ShapeDrawable.ShaderFactory() {

            @Override
            public Shader resize(int width, int height) {
                return new LinearGradient(0, 0, 0, 0, color, color, Shader.TileMode.REPEAT);
            }
        });
        return drawable;
    }
}
