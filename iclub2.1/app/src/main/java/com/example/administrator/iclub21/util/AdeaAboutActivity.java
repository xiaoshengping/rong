package com.example.administrator.iclub21.util;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.iclub21.http.MyAppliction;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.jeremy.Customer.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AdeaAboutActivity extends ActionBarActivity implements View.OnClickListener{

    //意见反馈

    @ViewInject(R.id.about_idea_edit)
    private EditText aboutIdeaEdit;

    @ViewInject(R.id.about_retrun_tv)
    private TextView feedbackRetrunTv;
    @ViewInject(R.id.about_commit_tv)
    private TextView feedbackCommitTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adea_about);
        ViewUtils.inject(this);
        intiView();
    }

    private void intiView() {


        feedbackRetrunTv.setOnClickListener(this);
        feedbackCommitTv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.about_retrun_tv:
                finish();

                break;
            case R.id.about_commit_tv:
                String aboutIdeaString =aboutIdeaEdit.getText().toString();
                if (!TextUtils.isEmpty(aboutIdeaString)){
                    aboutIntiData();
                }else {
                   // Toast.makeText(AmendAboutActivity.this, "您还没有输入内容", Toast.LENGTH_LONG).show();
                    MyAppliction.showExitGameAlert("您还没有输入内容",AdeaAboutActivity.this);
                }

                break;


        }

    }
    private void aboutIntiData() {
        HttpUtils httpUtils=new HttpUtils();
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter("content",aboutIdeaEdit.getText().toString());
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getAboutIdea(),requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result= responseInfo.result;
                //  Log.e("result",result);
                MyAppliction.showToast("提交成功");
                finish();
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });



    }
}
