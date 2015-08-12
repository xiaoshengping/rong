package com.example.administrator.iclub21.util;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.bean.ParmeBean;
import com.example.administrator.iclub21.bean.RecruitmentHistoryValueBean;
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

public class MerchantJobParticularActivity extends ActionBarActivity implements View.OnClickListener {

    @ViewInject(R.id.position_text_view)
    private TextView positionTextView;
    @ViewInject(R.id.time_text_view)
    private TextView timeTextView;
    @ViewInject(R.id.viewCount_text_view)
    private TextView viewCountTextView;
    @ViewInject(R.id.workPay_text_view)
    private TextView workPayTextView;
    @ViewInject(R.id.companyName_text_view)
    private TextView companyNameTextView;
    @ViewInject(R.id.workPlace_text_view)
    private TextView workPlaceTextView;
    @ViewInject(R.id.recruitingNumbers_text_view)
    private TextView recruitingNumbersTextView;
    @ViewInject(R.id.jobRequirements_text_view)
    private TextView jobRequirementsTextView;
    @ViewInject(R.id.jobInfo_text_view)
    private TextView jobInfoTextView;
    @ViewInject(R.id.phone_text_view)
    private TextView phoneTextView;
    @ViewInject(R.id.email_text_view)
    private TextView emailTextView;
    @ViewInject(R.id.web_text_view)
    private TextView webTextView;
    @ViewInject(R.id.address_text_view)
    private TextView addressTextView;
    @ViewInject(R.id.add_recruitment_retrun_tv)
    private TextView returnTv;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_job_particular);
        ViewUtils.inject(this);
        returnTv.setOnClickListener(this);
        intiData();

    }

    private void intiData() {
        HttpUtils httpUtils=new HttpUtils();
        String jobid=getIntent().getStringExtra("informationValueBeans");
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter("jobid",jobid);
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getJodParticular(), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                     String result=responseInfo.result;
                //Log.e("kkskdkkf",result);
                if(result!=null){
                    ParmeBean<RecruitmentHistoryValueBean> parmeBean= JSONObject.parseObject(result,new TypeReference<ParmeBean<RecruitmentHistoryValueBean>>(){});
                    RecruitmentHistoryValueBean recruitmentHistoryValueBean=parmeBean.getValue();
                     if (recruitmentHistoryValueBean!=null){
                         positionTextView.setText(recruitmentHistoryValueBean.getPosition());
                         timeTextView.setText("发布时间:"+recruitmentHistoryValueBean.getPuttime());
                         viewCountTextView.setText("浏览量:"+recruitmentHistoryValueBean.getViewCount());
                         workPayTextView.setText(recruitmentHistoryValueBean.getWorkPay());
                         companyNameTextView.setText(recruitmentHistoryValueBean.getCompanyName());
                         workPlaceTextView.setText(recruitmentHistoryValueBean.getWorkPlace());
                         recruitingNumbersTextView.setText(recruitmentHistoryValueBean.getRecruitingNumbers());
                         jobRequirementsTextView.setText(recruitmentHistoryValueBean.getJobRequirements());
                         jobInfoTextView.setText(recruitmentHistoryValueBean.getJobInfo());
                         phoneTextView.setText(recruitmentHistoryValueBean.getPhone());
                         emailTextView.setText(recruitmentHistoryValueBean.getEmail());
                         webTextView.setText(recruitmentHistoryValueBean.getWeb());
                         addressTextView.setText(recruitmentHistoryValueBean.getAddress());


                     }

                }
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
