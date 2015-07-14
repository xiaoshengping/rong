package com.example.administrator.iclub21.util;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.adapter.InfomationAdapter;
import com.example.administrator.iclub21.bean.InformationValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

public class InformationActivity extends ActionBarActivity implements View.OnClickListener {
    @ViewInject(R.id.information_lv)
    private ListView informationListv;
    //公告详细
    @ViewInject(R.id.detail_information_layout)
    private LinearLayout detailLayout;
    @ViewInject(R.id.particular_title_tv)
    private TextView tailteDetailTv;
    @ViewInject(R.id.particular_time_tv)
    private TextView timeDetailTv;
    @ViewInject(R.id.particular_content_tv)
    private TextView contentDetailTv;
    @ViewInject(R.id.role_retrun_tv)
    private TextView retrunTv;


    private List<InformationValueBean> informationValueBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ViewUtils.inject(this);
        intiInfomation();


    }

    private void intiInfomation() {
        retrunTv.setOnClickListener(this);
        intiData();



    }

    private void intiData() {
        HttpUtils httpUtils=new HttpUtils();
        String informationUrl= AppUtilsUrl.getInformationList("0","10");
        httpUtils.send(HttpRequest.HttpMethod.POST, informationUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result=responseInfo.result;
                ArtistParme<InformationValueBean> artistParme= JSONObject.parseObject(result, new TypeReference<ArtistParme<InformationValueBean>>() {
                });
                informationValueBeans=artistParme.getValue();
                InfomationAdapter infomationAdapter=new InfomationAdapter(informationValueBeans,InformationActivity.this);
                informationListv.setAdapter(infomationAdapter);
                infomationAdapter.notifyDataSetChanged();
                informationListv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        informationListv.setVisibility(View.GONE);
                        detailLayout.setVisibility(View.VISIBLE);
                        tailteDetailTv.setText(informationValueBeans.get(position).getTitle());
                        contentDetailTv.setText(informationValueBeans.get(position).getContent());
                        timeDetailTv.setText(informationValueBeans.get(position).getPutdate());

                    }
                });

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });




    }


    @Override
    public void onClick(View v) {
        finish();
    }
}
