package com.gac.calendarviewgac;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

/**
 * Created by Administrator on 2016/3/17.
 */
public class TextSpan implements LineBackgroundSpan {
    private  int color ;
    private  String mText;
    public static int EXCEPTION = 1;
    public static int NORMAL = 0;
    private int mode = -1;
    public TextSpan(int color, String text){
        this.color = color;
        this.mText = text;
    }
    public TextSpan(){
        this.color = Color.RED;
        this.mText = "正常";
    }
    public TextSpan(int mode){
        this.mode = mode;
        if(this.mode == EXCEPTION){
            this.color = Color.RED;
            this.mText = "异常";
        }else if(this.mode == NORMAL){
            this.color = Color.BLACK;
            this.mText = "正常";
        }
    }
    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        int oldColor = p.getColor();
        float textSize = p.getTextSize();//sp

        p.setColor(color);
        float center = (right+left)/2;
        float textLength = this.mText.length()*textSize;
        c.drawText(mText,center-textLength/2,bottom+baseline-15,p);
        p.setColor(oldColor);
    }
}
