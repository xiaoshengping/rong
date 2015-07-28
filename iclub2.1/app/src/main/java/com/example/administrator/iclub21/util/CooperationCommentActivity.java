package com.example.administrator.iclub21.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.iclub21.bean.InviteMessgaeListValueBean;
import com.example.administrator.iclub21.bean.MerchantInviteValueBean;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sina.weibo.sdk.demo.R;

public class CooperationCommentActivity extends ActionBarActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener {
     //头部
    @ViewInject(R.id.return_tv)
    private TextView returnTv;
    @ViewInject(R.id.text_tv)
    private TextView textTv;

     //加减
    @ViewInject(R.id.truth_radiogroup)
    private RadioGroup truthRadiogroup;
    @ViewInject(R.id.append_radio_button)
    private RadioButton appendRButton;
    @ViewInject(R.id.honesty_radiogroup)
    private RadioGroup honestyRadioGroup;
    @ViewInject(R.id.honesty_apappend_rb)
    private RadioButton honestyRButton;
    @ViewInject(R.id.grade_radiogroup)
    private RadioGroup gradeRadiogroup;
    @ViewInject(R.id.honesty_grade_radiogroup)
    private RadioGroup honestyRradeRg;
    @ViewInject(R.id.honesty_three_grade)
    private RadioButton honestyThreeGrade;
    @ViewInject(R.id.truth_three_grade)
    private RadioButton truthThreeGrade;


    //提交按钮
    @ViewInject(R.id.commit_comment_tv)
    private TextView commitCommentTv;

    //数据
    private HttpUtils httpUtils;
    private RequestParams requestParams;
    private String uid=null;
    @ViewInject(R.id.comment_context_et)
    private EditText commentContextEt;
    private InviteMessgaeListValueBean inviteMessgaeListValueBeans;
    private MerchantInviteValueBean merchantInviteValueBeans;
    //真实性
    private String authenticity;
    private String truthGrade;
    //诚实性
    private String integrity;
    private String honestyGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_comment);
        ViewUtils.inject(this);
       inti();



    }

    private void inti() {
        intiView();


    }

    private void intiView() {
        returnTv.setOnClickListener(this);
        textTv.setText("评论");
        SQLhelper sqLhelper=new SQLhelper(CooperationCommentActivity.this);
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);

        }
        inviteMessgaeListValueBeans= (InviteMessgaeListValueBean) getIntent().getSerializableExtra("inviteMessgaeListValueBeans");
        merchantInviteValueBeans= (MerchantInviteValueBean) getIntent().getSerializableExtra("MerchantInviteValueBean");

        honestyThreeGrade.setChecked(true);
        truthThreeGrade.setChecked(true);
        /*appendRButton.setChecked(true);
        honestyRButton.setChecked(true);*/
        truthRadiogroup.setOnCheckedChangeListener(this);
        honestyRadioGroup.setOnCheckedChangeListener(this);
        gradeRadiogroup.setOnCheckedChangeListener(this);
        honestyRradeRg.setOnCheckedChangeListener(this);
        commitCommentTv.setOnClickListener(this);

        httpUtils=new HttpUtils();
        requestParams=new RequestParams();
        //Log.e("2222222222", inviteMessgaeListValueBeans.getInviteResume().getPersonid());
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
             switch (group.getId()){
                 case  R.id.truth_radiogroup:
                     switch (checkedId){
                         case R.id.append_radio_button:

                             truthGrade=authenticity;
                             //Toast.makeText(CooperationCommentActivity.this, truthGrade, Toast.LENGTH_LONG).show();
                             //Log.e("truthGrade",truthGrade);
                             break;
                         case R.id.subtract_radio_button:
                             truthGrade="-"+authenticity;
                             //Log.e("truthGrade","-"+truthGrade);
                             //requestParams.addBodyParameter("authenticity", "-"+authenticity);
                            // Toast.makeText(CooperationCommentActivity.this, "-"+truthGrade, Toast.LENGTH_LONG).show();
                             break;
                     }

                     break;
                 case R.id.honesty_radiogroup:
                    switch (checkedId){
                        case R.id.honesty_apappend_rb:
                            //Toast.makeText(CooperationCommentActivity.this,"11111111111",Toast.LENGTH_LONG).show();
                            honestyGrade=integrity;
                            //requestParams.addBodyParameter("integrity", integrity);

                            break;
                        case R.id.honesty_subtract_rb:
                            //Toast.makeText(CooperationCommentActivity.this,"222222222222222",Toast.LENGTH_LONG).show();
                            honestyGrade="-"+integrity;
                            //requestParams.addBodyParameter("integrity", "-"+integrity);
                            break;

                    }


                     break;
                 case R.id.grade_radiogroup:

                         switch (checkedId){

                             case R.id.truth_one_grade:
                                 authenticity="1";
                                 // Toast.makeText(CooperationCommentActivity.this, authenticity, Toast.LENGTH_LONG).show();
                                 break;
                             case R.id.truth_two_grade:
                                 authenticity="2";
                                 //Toast.makeText(CooperationCommentActivity.this, authenticity, Toast.LENGTH_LONG).show();
                                 break;
                             case R.id.truth_three_grade:
                                 authenticity="3";
                                 break;
                             case R.id.truth_four_grade:
                                 authenticity="4";
                                 break;
                             case R.id.truth_five_grade:
                                 authenticity="5";
                                 break;

                         }



                 break;
                 case R.id.honesty_grade_radiogroup:
                     switch (checkedId){
                         case R.id.honesty_one_grade:
                             integrity="1";
                             break;
                         case R.id.honesty_two_grade:
                             integrity="2";
                             break;
                         case R.id.honesty_three_grade:
                             integrity="3";
                             break;
                         case R.id.honesty_four_grade:
                             integrity="4";
                             break;
                         case R.id.honesty_five_grade:
                             integrity="5";
                             break;


                     }
                     break;

             }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.commit_comment_tv:
                String falge=getIntent().getStringExtra("falgeData");
                if (falge.equals("SuccessfulInviteFragment")){
                    commentContentData(uid,inviteMessgaeListValueBeans.getInvitePerson().getId());
                    commentGradeData(inviteMessgaeListValueBeans.getInviteResume().getPersonid());
                }else if (falge.equals("MerchantSuccessfulInviteFragment")){
                    commentContentData(uid,merchantInviteValueBeans.getInviteResume().getPersonid()+"");
                    commentGradeData(merchantInviteValueBeans.getInviteResume().getPersonid()+"");
                }

                break;
            case R.id.return_tv:
                finish();
                break;
        }

    }

    private void commentGradeData(String personid) {
        requestParams.addBodyParameter("integrity",honestyGrade );
        requestParams.addBodyParameter("authenticity",truthGrade );
        requestParams.addBodyParameter("personid", personid);
        //Log.e("truthGrade", truthGrade);
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getCommentGrade(), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
               finish();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailureonFailure",s);
            }
        });





    }

    private void commentContentData(String uid,String beuid) {
        requestParams.addBodyParameter("uid",uid);
        requestParams.addBodyParameter("beuid",beuid );
        requestParams.addBodyParameter("body",commentContextEt.getText().toString());
        httpUtils.send(HttpRequest.HttpMethod.POST,AppUtilsUrl.getCommentCommit() , requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //Log.e("sjdjjjfjj",responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailure",s);
            }
        });





    }
}
