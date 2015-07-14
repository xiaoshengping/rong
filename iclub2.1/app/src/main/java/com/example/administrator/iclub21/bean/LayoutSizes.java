package com.example.administrator.iclub21.bean;

/**
 * Created by Administrator on 2015/6/23.
 */
public class LayoutSizes {
    public int layoutHeigh(int screenWidth , int width ,int heigh){
        double a = (double)width/(double)heigh;
        double b = (double)screenWidth/a;
        /*DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;*/
        return (int)b;
    }

}
