package com.example.administrator.iclub21.util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.bean.RecruitmentHistoryValueBean;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AddRecruitmentActivity extends ActionBarActivity implements View.OnClickListener {
     //添加招聘
    @ViewInject(R.id.position_edit)
    private EditText positionEdit;
    @ViewInject(R.id.workPay_edit)
    private EditText workPayEdit;
    @ViewInject(R.id.recruitingNumbers_edit)
    private EditText recruitingNumbersEdit;
    @ViewInject(R.id.profession_classification_tv)
    private TextView professionClassfitionTv;
    @ViewInject(R.id.work_address_tv)
    private TextView workAddressTv;
    @ViewInject(R.id.experience_require_tv)
    private TextView experienceRequireTv;
    @ViewInject(R.id.work_describe_tv)
    private TextView  workDescribeTv;

    @ViewInject(R.id.jobRequirements_layout)
    private LinearLayout jobRequirementsLayout;
    @ViewInject(R.id.jobInfo_layout)
    private LinearLayout jobInfoLayout;

    @ViewInject(R.id.add_recruitment_retrun_tv)
    private TextView addRetrunTv;
    @ViewInject(R.id.add_recruitment_save_text)
    private TextView addSaveTv;

    private  String merchantWork;
    private String merchantInfo;
    @ViewInject(R.id.delete_recuitment)
    private TextView deleteRecruitmentTv;
    private RecruitmentHistoryValueBean recruitmentHistoryValueBean;
    private  String uid=null;
    private Intent intent;
    private HttpUtils httpUtils;
    private  RequestParams requestParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recruitment);
        ViewUtils.inject(this);
        inti();



    }

    private void inti() {
        intiView();
        SQLhelper sqLhelper = new SQLhelper(this);
        SQLiteDatabase db = sqLhelper.getWritableDatabase();
        Cursor cursor = db.query("user", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            uid = cursor.getString(0);

        }
        cursor.close();
        db.close();


    }

    private void intiEditData() {
        String position=positionEdit.getText().toString();
        String workPay=workPayEdit.getText().toString();
        String recruitingNumbers=recruitingNumbersEdit.getText().toString();
        if (!TextUtils.isEmpty(position)&&!TextUtils.isEmpty(workPay)&&!TextUtils.isEmpty(recruitingNumbers)){
            requestParams.addBodyParameter("jobid",recruitmentHistoryValueBean.getJobId()+"");
            requestParams.addBodyParameter("jobCategory","1");
            requestParams.addBodyParameter("cityid","1");
            requestParams.addBodyParameter("position",position);
            requestParams.addBodyParameter("workPay",workPay);
            requestParams.addBodyParameter("recruitingNumbers",recruitingNumbers);
            requestParams.addBodyParameter("jobRequirements",merchantWork);
            requestParams.addBodyParameter("jobInfo",merchantInfo);

            httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getEditJod(),requestParams, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    Log.e("result",responseInfo.result);
                    finish();
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Log.e("onFailure",s);
                }
            });


        }else {

            alert("您输入的内容不全");

        }






    }



    private void intiData() {
        String position=positionEdit.getText().toString();
        String workPay=workPayEdit.getText().toString();
        String recruitingNumbers=recruitingNumbersEdit.getText().toString();
        if (!TextUtils.isEmpty(position)&&!TextUtils.isEmpty(workPay)&&!TextUtils.isEmpty(recruitingNumbers)){
            requestParams.addBodyParameter("uid",uid);
            requestParams.addBodyParameter("jobCategory","1");
            requestParams.addBodyParameter("cityid","1");
            requestParams.addBodyParameter("position",position);
            requestParams.addBodyParameter("workPay",workPay);
            requestParams.addBodyParameter("recruitingNumbers",recruitingNumbers);
            requestParams.addBodyParameter("jobRequirements",merchantWork);
            requestParams.addBodyParameter("jobInfo",merchantInfo);

            httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getAddJod(),requestParams, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    //Log.e("result", responseInfo.result);
                    finish();
                }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });


        }else {

            alert("您输入的内容不全");

        }






    }


    private void intiView() {
        httpUtils=new HttpUtils();
        requestParams=new RequestParams();
        intent=getIntent();
        jobRequirementsLayout.setOnClickListener(this);
        jobInfoLayout.setOnClickListener(this);
        addRetrunTv.setOnClickListener(this);
        addSaveTv.setOnClickListener(this);
        deleteRecruitmentTv.setOnClickListener(this);
        recruitmentHistoryValueBean= (RecruitmentHistoryValueBean) intent.getSerializableExtra("recruitmentHistoryValueBean");
        if (recruitmentHistoryValueBean!=null){
            deleteRecruitmentTv.setVisibility(View.VISIBLE);
            professionClassfitionTv.setText(recruitmentHistoryValueBean.getJobcategory()+"");
            workAddressTv.setText(recruitmentHistoryValueBean.getAddress());
            positionEdit.setText(recruitmentHistoryValueBean.getPosition());
            if (!TextUtils.isEmpty(recruitmentHistoryValueBean.getJobRequirements())){
                experienceRequireTv.setText(recruitmentHistoryValueBean.getJobRequirements());
                experienceRequireTv.setTextColor(getResources().getColor(R.color.white));

            }else {
                experienceRequireTv.setText("亲，请填写经验要求哦(必填)");
                experienceRequireTv.setTextColor(getResources().getColor(R.color.textColor9a4274));
            }
            if (!TextUtils.isEmpty(recruitmentHistoryValueBean.getJobInfo())){
                workDescribeTv.setText(recruitmentHistoryValueBean.getJobInfo());
                workDescribeTv.setTextColor(getResources().getColor(R.color.white));
            }else {
                workDescribeTv.setText("亲，请填写职位描述哦(必填)");
                workDescribeTv.setTextColor(getResources().getColor(R.color.textColor9a4274));
            }
            workPayEdit.setText(recruitmentHistoryValueBean.getWorkPay());
            recruitingNumbersEdit.setText(recruitmentHistoryValueBean.getRecruitingNumbers());
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 15:
                merchantWork = data.getStringExtra("merchantWork").toString();
                if (!TextUtils.isEmpty(merchantWork)){

                    experienceRequireTv.setText(merchantWork);
                    experienceRequireTv.setTextColor(getResources().getColor(R.color.white));
                }else {
                    experienceRequireTv.setText("亲，请填写经验要求哦(必填)");
                    experienceRequireTv.setTextColor(getResources().getColor(R.color.textColor9a4274));
                }

                break;
                case 16:
                    merchantInfo = data.getStringExtra("merchantInfo").toString();
                    if (!TextUtils.isEmpty(merchantInfo)){
                        workDescribeTv.setText(merchantInfo);
                        workDescribeTv.setTextColor(getResources().getColor(R.color.white));
                    }else {
                        workDescribeTv.setText("亲，请填写职位描述哦(必填)");
                        workDescribeTv.setTextColor(getResources().getColor(R.color.textColor9a4274));
                    }
                    break;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jobRequirements_layout:
                Intent jobRequirementsInent=new Intent(this,ExperienceWorkActivity.class);
                jobRequirementsInent.putExtra("addRecruitment","jobRequirements");
                startActivityForResult(jobRequirementsInent,15);
                break;
            case R.id.jobInfo_layout:
                Intent jobInfoInent=new Intent(this,ExperienceWorkActivity.class);
                jobInfoInent.putExtra("addRecruitment","jobInfo");
                startActivityForResult(jobInfoInent,16);
                break;
            case R.id.add_recruitment_save_text:
               String data= intent.getStringExtra("falgeData");
                if (data.equals("RecruitmentHistoryFragment")){
                    intiEditData();
                }else{
                    intiData();
                }

                break;
            case R.id.add_recruitment_retrun_tv:
                     finish();
                break;
            case R.id.delete_recuitment:
                deleteJob();

                break;
        }




    }

    private void deleteJob() {
        HttpUtils httpUtils=new HttpUtils();
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter("jobid",recruitmentHistoryValueBean.getJobId()+"");
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getDeleteJob(),requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result=responseInfo.result;
                        if (!TextUtils.isEmpty(result)){
                            finish();
                        }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });





    }


    private void alert(String text) {
        Dialog dialog = new AlertDialog.Builder(this).setTitle("提示")
                .setMessage(text)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
    }
}
