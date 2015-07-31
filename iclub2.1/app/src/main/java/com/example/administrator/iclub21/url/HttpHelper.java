package com.example.administrator.iclub21.url;

import android.widget.BaseAdapter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.http.MyAppliction;

import java.util.List;

/**
 * Created by Administrator on 2015/3/21.
 */
public class HttpHelper {
   // private  RequestQueue queue;
    public static HttpHelper helper;
    public static HttpHelper getHelper(){
        if(helper == null){
            helper = new HttpHelper();
        }
        return helper;

    }

    public static <T> void baseToUrl(String json, TypeReference<ArtistParme<T>> type, List<T> datas, BaseAdapter adapter) {
        ArtistParme<T> comm = JSONObject.parseObject(json, type);
        if ("success".equals(comm.getState())) {
            datas.addAll(comm.getValue());
            adapter.notifyDataSetChanged();

        } else {
            MyAppliction.showToast("联网失败");

        }
    }
   /* public static <T> void baseToUrl(String json, TypeReference<ArtistParme<T>> type, List<T> datas, BaseAdapter adapter,TextView hasDataTV) {
        ArtistParme<T> comm = JSONObject.parseObject(json, type);
        if ("1".equals(comm.getResult())) {
            datas.addAll(comm.getData());
            adapter.notifyDataSetChanged();
            if(datas.isEmpty()){
                hasDataTV.setVisibility(View.VISIBLE);
            }else{
                hasDataTV.setVisibility(View.GONE);
            }
        } else {
            BaseApp.showToast("联网失败");
        }
    }
    public static <T> void pagerToUrl(String json, TypeReference<CommsBean<T>> type, List<T> datas, FragmentPagerAdapter adapter) {
        CommsBean<T> comm = JSONObject.parseObject(json, type);
        if ("1".equals(comm.getResult())) {
            datas.addAll(comm.getData());
            adapter.notifyDataSetChanged();
        } else {
            BaseApp.showToast("联网失败");
        }
    }


    public static <T> void bannerToUrl(String json, TypeReference<CommsBean<T>> type, List<T> datas, FragmentPagerAdapter adapter) {
        CommsBean<T> comm = JSONObject.parseObject(json, type);
        if ("1".equals(comm.getResult())) {
            datas.addAll(comm.getData());
            adapter.notifyDataSetChanged();
        } else {
            BaseApp.showToast("联网失败");
        }
    }

    public static <T> void singleToUrl(String json,TypeReference<CommBean<T>> type,List<T> datas,BaseAdapter adapter){
        CommBean<T> comm = JSONObject.parseObject(json,type);
        if("1".equals(comm.getResult())){
            datas.clear();
            datas.add(comm.getData());
            adapter.notifyDataSetChanged();
        }else{
            BaseApp.showToast("联网失败");
        }
    }*/


  /*  public RequestQueue getRequestQueue() {
        if (queue == null) {
            //应用场景.queue跟整个应用程序的生命周期进行绑定 而不是Activity或Fragment
            queue = Volley.newRequestQueue(BaseApp.getContextInstance());
        }
        return queue;
    }

    public <T> void addRequest(Request<T> request) {
        getRequestQueue().add(request);
    }
    public void cancelAll(Object tag) {
        queue.cancelAll(tag);
    }*/
}
