package com.example.administrator.iclub21.util;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.adapter.ResumeCommentAdapter;
import com.example.administrator.iclub21.bean.InviteMessgaeListValueBean;
import com.example.administrator.iclub21.bean.ParmeBean;
import com.example.administrator.iclub21.bean.ReputationValueBean;
import com.example.administrator.iclub21.bean.ResumeCommentValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.view.CustomHomeScrollListView;
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

public class CompanyInviteMessageActivity extends ActionBarActivity implements View.OnClickListener {
    //头部
    @ViewInject(R.id.text_tv)
    private TextView titleText;
    @ViewInject(R.id.return_tv)
    private TextView returnText;

    //基本信息
    @ViewInject(R.id.company_invity_name_tv)
    private TextView companyInviteNameTv;
    @ViewInject(R.id.job_invite_name_tv)
    private TextView jobInviteNameTv;
    @ViewInject(R.id.invite_schedule_tv)
    private TextView inviteSchedule;
    @ViewInject(R.id.web_invite_tv)
    private TextView webInviteTv;
    @ViewInject(R.id.phone_invity_tv)
    private TextView phoneInviteTv;
    @ViewInject(R.id.email_invite_tv)
    private TextView emailInviteTv;
    @ViewInject(R.id.address_invite_tv)
    private TextView addressInviteTv;
    @ViewInject(R.id.honesty_degree_tv)
    private TextView honestyDegreeTv;
    @ViewInject(R.id.truth_invite_tv)
    private TextView truthInviteTv;
    @ViewInject(R.id.cooperation_number_tv)
    private TextView cooperationNumberTv;
    @ViewInject(R.id.comment_layout)
    private LinearLayout commentLayout;

   //评论
    @ViewInject(R.id.comment_listview)
    private CustomHomeScrollListView commentListView;



    //接受和拒绝按钮
    @ViewInject(R.id.adopt_invite_bt)
    private Button adoptButton;
    @ViewInject(R.id.refuse_invite_bt)
    private  Button refuseButton;

    //数据
    private InviteMessgaeListValueBean inviteMessgaeListValueBean;
    private HttpUtils httpUtils;
    private RequestParams requestParams;
    private ReputationValueBean reputationValueBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_invite_message);
        ViewUtils.inject(this);
        inti();
    }

    private void inti() {
        intiView();
        intiCommentData();


    }
      //评论
    private void intiCommentData() {
       // Log.e("11111111100", inviteMessgaeListValueBean.getInvitePerson().getId());
        SQLhelper sqLhelper=new SQLhelper(this);
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String personid=null;
        while (cursor.moveToNext()) {
            personid = cursor.getString(1);

        }
        requestParams.addBodyParameter("personid", personid);
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getResumeCommentData(), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
               // Log.e("111111111",responseInfo.result);
                  if (responseInfo.result!=null){
                      ArtistParme<ResumeCommentValueBean> artistParme=JSONObject.parseObject(responseInfo.result,new TypeReference<ArtistParme<ResumeCommentValueBean>>(){});
                      if (artistParme.getState().equals("success")){
                          intiListView(artistParme.getValue());


                      }
                  }


            }


            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailure",s);
            }
        });



    }
    private void intiListView(List<ResumeCommentValueBean> data) {
        ResumeCommentAdapter resumeCommentAdapter=new ResumeCommentAdapter(data,CompanyInviteMessageActivity.this);
        commentListView.setAdapter(resumeCommentAdapter);
        resumeCommentAdapter.notifyDataSetChanged();


    }
    private void intiView() {
        httpUtils=new HttpUtils();
        requestParams=new RequestParams();
        titleText.setText("邀约详情");
        adoptButton.setOnClickListener(this);
        returnText.setOnClickListener(this);
        refuseButton.setOnClickListener(this);
        Intent intent=getIntent();
        inviteMessgaeListValueBean= (InviteMessgaeListValueBean) intent.getSerializableExtra("InviteMessgaeListValueBean");
        companyInviteNameTv.setText(inviteMessgaeListValueBean.getInvitePerson().getBEcompanyName());
        jobInviteNameTv.setText(inviteMessgaeListValueBean.getInviteResume().getResumeJobName());
        webInviteTv.setText(inviteMessgaeListValueBean.getInvitePerson().getBEweb());
        phoneInviteTv.setText(inviteMessgaeListValueBean.getInvitePerson().getBEphone());
        emailInviteTv.setText(inviteMessgaeListValueBean.getInvitePerson().getBEemail());
        addressInviteTv.setText(inviteMessgaeListValueBean.getInvitePerson().getBEaddress());
        inviteSchedule.setText(inviteMessgaeListValueBean.getTripTime());
        String flage= intent.getStringExtra("flage");
        if (flage.equals("AcceptInviteFragment")){
            commentLayout.setVisibility(View.GONE);
        }
        reputationData();




    }
     //信誉值
    private void reputationData() {
        requestParams.addBodyParameter("personid",inviteMessgaeListValueBean.getInvitePerson().getId());
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getReputationData(), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
               String result= responseInfo.result;
                if (!TextUtils.isEmpty(result)){
                    ParmeBean<ReputationValueBean> parmeBean= JSONObject.parseObject(result,new TypeReference<ParmeBean<ReputationValueBean>>(){});
                    if (parmeBean.getState().equals("success")){
                      reputationValueBean=   parmeBean.getValue();
                        if (reputationValueBean!=null) {
                            honestyDegreeTv.setText(reputationValueBean.getIntegrity());
                            truthInviteTv.setText(reputationValueBean.getAuthenticity());
                            cooperationNumberTv.setText(reputationValueBean.getTransactionRecord());

                        }
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
        switch (v.getId()){
            case R.id.adopt_invite_bt:
                adoptData("1");
                break;
            case R.id.refuse_invite_bt:
                adoptData("2");
                break;
            case R.id.return_tv:
                finish();
                break;

        }
    }

    private void adoptData(String status) {
        requestParams.addBodyParameter("inviteid",inviteMessgaeListValueBean.getInviteid());
        requestParams.addBodyParameter("status", status);
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getAdoptAndRefuse(),requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("responseInfo",responseInfo.result);
                if (responseInfo.result!=null){
                    finish();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailure",s);
            }
        });


    }
}
