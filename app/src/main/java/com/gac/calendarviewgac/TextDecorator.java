package com.gac.calendarviewgac;


import android.util.Log;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2016/3/22.
 */
public class TextDecorator {
    private int color;
    private HashSet<CalendarDay> dates;

    private HashMap<String,Integer> hashMap;
    public TextDecorator(int color, Collection<CalendarDay> dates) {
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    public TextDecorator(HashMap<String, Integer> map){
        hashMap = map;
        this.dates = new HashSet<>();
        Set<String> set = map.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            Date date = new Date();
            try {
                date = DateUtils.parse(iterator.next());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            CalendarDay day = CalendarDay.from(date);
            this.dates.add(day);
        }

    }

    public boolean shouldDecorate(CalendarDay day) {

        return dates.contains(day);
    }

    //返回当前的dayView 改变样式

    public int shouldDecorateGAC(CalendarDay day) {
        int currentMode = -1;
        //CalendarDay 转换为Date类型自动月份+`1
        String str = DateUtils.getDateStr(day.getDate());
        Log.e("gac", "********************shouldDecorateGAC date:" + str);
        if(hashMap.containsKey(str)){
            currentMode = hashMap.get(str);
            //Log.e("gac","shouldDecorateGAC:"+str);
            // Log.e("gac","currentMode:"+currentMode);
        }
        return currentMode;

    }
}
