package com.example.administrator.iclub21.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sina.weibo.sdk.demo.R;

public class ExperienceWorkActivity extends ActionBarActivity implements View.OnClickListener {
    //头部
    @ViewInject(R.id.message_return_tv)
    private TextView messsageReturnTv;
    @ViewInject(R.id.message_comment_tv)
    private TextView messageCommentTv;
    @ViewInject(R.id.message_save_tv)
    private TextView messageSaveTv;
      //经验要求
    @ViewInject(R.id.experience_info_layout)
    private LinearLayout experienceInfoLayout;
    @ViewInject(R.id.experience_info_et)
    private EditText experienceInfoEdit;
    //职位描述
    @ViewInject(R.id.work_experience_layout)
    private LinearLayout experienceWorkLayout;
    @ViewInject(R.id.add_WorkExperience_et)
    private EditText experienceWorkEdit;

    private Intent intent;
    private  String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_work);
        ViewUtils.inject(this);
        inti();
    }

    private void inti() {
        intiView();
        intiData();



    }



    private void intiData() {
        intent=getIntent();
        data=intent.getStringExtra("addRecruitment");
         if (data.equals("jobRequirements")){
             experienceWorkLayout.setVisibility(View.VISIBLE);
             messageCommentTv.setText("经验要求");
         }else if (data.equals("jobInfo")){
             experienceInfoLayout.setVisibility(View.VISIBLE);
             messageCommentTv.setText("职位描述");
         }

    }

    private void intiView() {
        messsageReturnTv.setOnClickListener(this);
        messageSaveTv.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.message_return_tv:
                if (data.equals("jobRequirements")){
                    intent.putExtra("merchantWork", "");
                    setResult(15, intent);

                }else if(data.equals("jobInfo")){
                    intent.putExtra("merchantInfo", "");
                    setResult(16, intent);

                }
                finish();
                break;
            case R.id.message_save_tv:
                if (data.equals("jobRequirements")){
                    intent.putExtra("merchantWork",experienceWorkEdit.getText().toString());
                    setResult(15, intent);
                    finish();
                }else if(data.equals("jobInfo")){
                    intent.putExtra("merchantInfo", experienceInfoEdit.getText().toString());
                    setResult(16, intent);
                    finish();
                }
                break;

        }


    }
}
