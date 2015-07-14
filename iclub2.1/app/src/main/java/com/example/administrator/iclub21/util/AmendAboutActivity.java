package com.example.administrator.iclub21.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AmendAboutActivity extends ActionBarActivity implements View.OnClickListener {
    @ViewInject(R.id.register_reten_tv)
    private TextView aboutReturnTv;
    @ViewInject(R.id.register_title_tv)
    private TextView aboutTitleTv;
    @ViewInject(R.id.register_commit_tv)
    private  TextView aboutCimmitTv;
    @ViewInject(R.id.login_layout)
    private RelativeLayout aboutTaileLayout;

    //功能介绍
    @ViewInject(R.id.about_function_layout)
    private LinearLayout aboutFubctionLayout;


    //意见反馈
    @ViewInject(R.id.about_layout)
    private LinearLayout aboutLayout;
    @ViewInject(R.id.about_idea_edit)
    private EditText aboutIdeaEdit;
    @ViewInject(R.id.about_idea_layout)
    private LinearLayout aboutIdeaLayout;
    @ViewInject(R.id.feedback_layout)
    private  LinearLayout feedbackLayout;
    @ViewInject(R.id.about_retrun_tv)
    private TextView feedbackRetrunTv;
    @ViewInject(R.id.about_commit_tv)
    private TextView feedbackCommitTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amend_about);
        ViewUtils.inject(this);
        aboutInti();

    }

    private void aboutInti() {
        aboutCimmitTv.setVisibility(View.GONE);
        aboutTitleTv.setText("关于iClub");
        aboutInitView();

    }

    private void aboutInitView() {
        aboutReturnTv.setOnClickListener(this);
        aboutIdeaLayout.setOnClickListener(this);
        feedbackRetrunTv.setOnClickListener(this);
        feedbackCommitTv.setOnClickListener(this);
        aboutFubctionLayout.setOnClickListener(this);




    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.register_reten_tv:
                finish();
                break;
            case R.id.about_function_layout:
                Intent intent=new Intent(AmendAboutActivity.this,AboutFunctionActivity.class);
                startActivity(intent);

                break;
            case R.id.about_idea_layout:
                aboutLayout.setVisibility(View.GONE);
                aboutTaileLayout.setVisibility(View.GONE);
                feedbackLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.about_retrun_tv:
                aboutTaileLayout.setVisibility(View.VISIBLE);
                aboutLayout.setVisibility(View.VISIBLE);
                feedbackLayout.setVisibility(View.GONE);
                break;
            case R.id.about_commit_tv:
                String aboutIdeaString =aboutIdeaEdit.getText().toString();
                if (!TextUtils.isEmpty(aboutIdeaString)){
                    aboutTaileLayout.setVisibility(View.VISIBLE);
                    aboutLayout.setVisibility(View.VISIBLE);
                    feedbackLayout.setVisibility(View.GONE);
                    aboutIntiData();
                }else {
                    Toast.makeText(AmendAboutActivity.this,"您还没有输入内容",Toast.LENGTH_LONG).show();

                }

                break;


        }

    }

    private void aboutIntiData() {
        HttpUtils httpUtils=new HttpUtils();

        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getAboutIdea(aboutIdeaEdit.getText().toString()), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                 String result= responseInfo.result;
                Log.e("result",result);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });



    }
}
