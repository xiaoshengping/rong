package com.example.administrator.iclub21.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.adapter.MerchantMessageListAdapter;
import com.example.administrator.iclub21.bean.MerchantMessageValueBean;
import com.example.administrator.iclub21.bean.ParmeBean;
import com.example.administrator.iclub21.bean.ResumeValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.url.HttpHelper;
import com.example.administrator.iclub21.util.ResumeListParticularActivity;
import com.example.administrator.iclub21.util.SQLhelper;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jeremy.Customer.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantInformationFragment extends Fragment implements View.OnClickListener,  PullToRefreshBase.OnRefreshListener2<ListView> {

    @ViewInject(R.id.text_tv)
    private TextView textTv;
    @ViewInject(R.id.role_retrun_tv)
    private TextView retrunTextView;
    @ViewInject(R.id.merchant_message_lv)
    //private ListView informationListv;
    private PullToRefreshListView informationListv;
    private List<MerchantMessageValueBean> informationValueBeans;
    private MerchantMessageListAdapter merchantMessageListAdapter;
    private int offset=0;
    @ViewInject(R.id.progressbar)
    private ProgressBar ProgressBar;


    public MerchantInformationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_merchant_information, container, false);
        ViewUtils.inject(this,view);
        textTv.setText("投递消息");

        intiInfomation();
        return view;
    }

    private void intiInfomation() {
        retrunTextView.setOnClickListener(this);
        initView();




    }

    private void initView() {
        informationValueBeans=new ArrayList<>();
        merchantMessageListAdapter = new MerchantMessageListAdapter(informationValueBeans, getActivity());
        informationListv.setAdapter(merchantMessageListAdapter);
        informationListv.setMode(PullToRefreshBase.Mode.BOTH);
        informationListv.setOnRefreshListener(this);
        ILoadingLayout endLabels  = informationListv
                .getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在刷新...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
        ILoadingLayout startLabels  = informationListv
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
        informationListv.setRefreshing();

        informationListv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intiResumeData(informationValueBeans.get(position-1).getApplyerResumeid());


            }
        });





    }

    private void intiData(int offset) {
        SQLhelper sqLhelper=new SQLhelper(getActivity());
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);


        }
        HttpUtils httpUtils=new HttpUtils();
        String informationUrl= AppUtilsUrl.getMessageMerchantList(uid,offset);
        httpUtils.send(HttpRequest.HttpMethod.POST, informationUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                //Log.e("hsdhdfh",responseInfo.result);
               /* ArtistParme<MerchantMessageValueBean> artistParme = JSONObject.parseObject(result, new TypeReference<ArtistParme<MerchantMessageValueBean>>() {
                });
                informationValueBeans = artistParme.getValue();*/
                HttpHelper.baseToUrl(result, new TypeReference<ArtistParme<MerchantMessageValueBean>>() {
                }, informationValueBeans, merchantMessageListAdapter);
                informationListv.onRefreshComplete();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailure", s);
            }
        });




    }

    private void intiResumeData(String resumeid) {
        HttpUtils httpUtils=new HttpUtils();
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter("resumeid",resumeid);
        ProgressBar.setVisibility(View.VISIBLE);
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getPreviewResume(), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result=responseInfo.result;
                if (result!=null){
                    ParmeBean<ResumeValueBean> artistParme=JSONObject.parseObject(result,new TypeReference<ParmeBean<ResumeValueBean>>(){});
                    ResumeValueBean resumeValueBeans=    artistParme.getValue();
                    ProgressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(getActivity(), ResumeListParticularActivity.class);
                    intent.putExtra("resumeValueBeans", resumeValueBeans);
                    intent.putExtra("flage", "MerchantAcceptInviteFragment");
                    startActivity(intent);
                }



            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });




    }

    @Override
    public void onClick(View v) {
        getActivity().finish();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        informationValueBeans.clear();
        offset=0;
        intiData(offset);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        offset=offset+10;
        intiData(offset);
    }
}
