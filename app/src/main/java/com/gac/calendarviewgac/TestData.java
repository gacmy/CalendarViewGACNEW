package com.gac.calendarviewgac;



import java.util.HashMap;

/**
 * Created by Administrator on 2016/3/17.
 */
public class TestData {

    private static HashMap<String,Integer> map = new HashMap<>();
    public static HashMap<String,Integer> getMap(){
        map.put("2016-03-07", 1);
        map.put("2016-03-06",1);
        map.put("2016-03-05",1);
        map.put("2016-03-01",0);
        map.put("2016-02-08",1);
        map.put("2016-02-07",1);

        return map;
    }
}
