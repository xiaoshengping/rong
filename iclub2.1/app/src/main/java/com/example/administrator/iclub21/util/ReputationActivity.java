package com.example.administrator.iclub21.util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.adapter.ReputationAdapter;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.bean.talent.CommentBean;
import com.example.administrator.iclub21.bean.talent.ReputationValueBean;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

/**
 * Created by Administrator on 2015/7/9.
 */
public class ReputationActivity extends Activity {

    private ListView reputation_list;
    private ReputationAdapter adapter;
    private int id;
    private TextView authenticity_tv,integrity_tv,record_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reputation);
        init();
    }

    private void binding(){
        reputation_list = (ListView)findViewById(R.id.reputation_list);
    }

    private void init(){
        binding();
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("Personid");
        View header = View.inflate(this, R.layout.reputation_head, null);//头部内容
        authenticity_tv = (TextView)header.findViewById(R.id.authenticity_tv);
        integrity_tv = (TextView)header.findViewById(R.id.integrity_tv);
        record_tv = (TextView)header.findViewById(R.id.record_tv);
        reputation_list.addHeaderView(header);//添加头部

        initReputationValue();
    }

    //初始化信誉值
    private void initReputationValue(){
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getReputationValue(id), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (result != null) {
                    ArtistParme<ReputationValueBean> reputationValueBean = JSONObject.parseObject(result, new TypeReference<ArtistParme<ReputationValueBean>>() {
                    });
                    if (reputationValueBean.getState().equals("success")) {

                        if (reputationValueBean.getValue() != null) {
                            ReputationValueBean reputationValueDate = reputationValueBean.getValue().get(0);
                            authenticity_tv.setText(reputationValueDate.getAuthenticiy());
                            integrity_tv.setText(reputationValueDate.getIntegrity());
                            record_tv.setText(reputationValueDate.getTransactionRecord());
                        }

                    }

                }


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
        initcCollaborateComment();
    }

    //初始化合作评论
    private void initcCollaborateComment(){
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getComment(id), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (result != null) {
                    ArtistParme<CommentBean> commentBean = JSONObject.parseObject(result, new TypeReference<ArtistParme<CommentBean>>() {
                    });
                    if (commentBean.getState().equals("success")) {

                        if(commentBean.getValue()!=null) {
                            List<CommentBean> commentDate = commentBean.getValue();
                            adapter = new ReputationAdapter(ReputationActivity.this,commentDate);
                            reputation_list.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                    }

                }


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

}
