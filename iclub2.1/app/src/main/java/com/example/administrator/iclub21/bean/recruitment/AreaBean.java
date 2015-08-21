package com.example.administrator.iclub21.bean.recruitment;

import android.content.Context;

import com.jeremy.Customer.R;


/**
 * Created by Administrator on 2015/5/27.
 */
public class AreaBean {

    private static String[] sArea;
    private String[] sProvince;
    private static String[] sCity;
    public int CITY = 1;//cv'城市
    public int PROVINCE = 2;//省份
    public int POSITION = 3;//职位

    public int getAreaCount(Context context){
        sArea = context.getString(R.string.area).split(";");
        return sArea.length;
    }
    public int getPositionCount(Context context){
        sArea = context.getString(R.string.position).split(",");
        return sArea.length;
    }
    public String getPosition(int i){
        String[] s = sArea[i].split(":");
        return s[1];
    }
    public int getPositionNum(int i){
        String[] s = sArea[i].split(":");
        return Integer.parseInt(s[0]);
    }

    public String getNumPositionName(Context context,int num){
        if(num==1) {
            return "编剧";//context.getString(R.string.position).split(num + ":")[1].split(",")[0];
        }else {
            return context.getString(R.string.position).split(num + ":")[1].split(",")[0];
        }

    }

    public int getsCityCount(String str){
        sCity = str.split(",");
        return sCity.length;
    }

    public String getArea(int num){

        return sArea[num];
    }
    public String getProvince(int num,int sel){
        sProvince = sArea[num].split("a");
        String s = "";
        if(sel == 1){
            s = sProvince[0];
        }else if(sel == 2 ){
            s = sProvince[1];
        }
        return s;
    }
    public String getCityName(int i){
        String[] s = sCity[i].split(":");
        return s[1];
    }
    public int getCityNum(int i){
        String[] s = sCity[i].split(":");
        return Integer.parseInt(s[0]);
//        return s[0];
    }

    public String getNumCityName(Context context,int num){
        return context.getString(R.string.area).split(num+":")[1].split(",")[0];
    }


}
