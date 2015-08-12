package com.example.administrator.iclub21.fragment;


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
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.adapter.MerchantMessageListAdapter;
import com.example.administrator.iclub21.bean.MerchantMessageValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.util.SQLhelper;
import com.jeremy.Customer.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantInformationFragment extends Fragment implements View.OnClickListener {

    @ViewInject(R.id.text_tv)
    private TextView textTv;
    @ViewInject(R.id.role_retrun_tv)
    private TextView retrunTextView;
    @ViewInject(R.id.merchant_message_lv)
    private ListView informationListv;

    private List<MerchantMessageValueBean> informationValueBeans;

    public MerchantInformationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_merchant_information, container, false);
        ViewUtils.inject(this,view);
        textTv.setText("消息通知");

        intiInfomation();
        return view;
    }

    private void intiInfomation() {
        retrunTextView.setOnClickListener(this);
        intiData();



    }

    private void intiData() {
        SQLhelper sqLhelper=new SQLhelper(getActivity());
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);


        }
        HttpUtils httpUtils=new HttpUtils();
        String informationUrl= AppUtilsUrl.getMessageMerchantList(uid);
        httpUtils.send(HttpRequest.HttpMethod.POST, informationUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;

                ArtistParme<MerchantMessageValueBean> artistParme = JSONObject.parseObject(result, new TypeReference<ArtistParme<MerchantMessageValueBean>>() {
                });
                informationValueBeans = artistParme.getValue();
                MerchantMessageListAdapter merchantMessageListAdapter = new MerchantMessageListAdapter(informationValueBeans, getActivity());
                informationListv.setAdapter(merchantMessageListAdapter);
                merchantMessageListAdapter.notifyDataSetChanged();

                informationListv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                    }
                });

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
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getPreviewResume(), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

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
}
