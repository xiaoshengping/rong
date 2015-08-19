package com.example.administrator.iclub21.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.bean.ParmeBean;
import com.example.administrator.iclub21.bean.PswValueBean;
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

public class AmendPswActivity extends ActionBarActivity implements View.OnClickListener {
   @ViewInject(R.id.former_psw_edit)
    private EditText formerPswEdit;
    @ViewInject(R.id.new_psw_edit)
    private EditText newPswEdit;
    @ViewInject(R.id.reset_psw_edit)
    private EditText resetPswEdit;
    @ViewInject(R.id.register_reten_tv)
    private TextView amendRetrunTv;
    @ViewInject(R.id.register_title_tv)
    private TextView amendTitleTv;
    @ViewInject(R.id.register_commit_tv)
    private TextView amendCommitTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amend_psw);
        ViewUtils.inject(this);
        intiAmendPsw();



    }

    private void intiAmendPsw() {
        intiAmendPswView();




    }

    private void intiAmendPswView() {
        amendTitleTv.setText("修改密码");
        amendRetrunTv.setOnClickListener(this);
        amendCommitTv.setOnClickListener(this);


    }

    private void intiAmndPswData() {
        SQLhelper sqLhelper=new SQLhelper(AmendPswActivity.this);
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;

        while (cursor.moveToNext()) {
            uid = cursor.getString(0);
        }
        HttpUtils httpUtils=new HttpUtils();
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter("uid",uid);
        requestParams.addBodyParameter("oldpwd",MD5Uutils.MD5(formerPswEdit.getText().toString()));
        requestParams.addBodyParameter("newpwd", MD5Uutils.MD5(newPswEdit.getText().toString()));
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getAmendPsw(),requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                  String result=   responseInfo.result;
                ParmeBean<PswValueBean> parmeBean = JSONObject.parseObject(result,new TypeReference<ParmeBean<PswValueBean>>(){});
                if (parmeBean.getState().equals("success")){
                    if (parmeBean.getValue().getMessage().equals("success")){
                        finish();

                    }else {
                        MyAppliction.showToast("旧密码错误!");

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
            case R.id.register_reten_tv:
                finish();
                break;
            case R.id.register_commit_tv:
                if (!TextUtils.isEmpty(formerPswEdit.getText().toString())&&!TextUtils.isEmpty(newPswEdit.getText().toString())&&
                        (resetPswEdit.getText().toString()).equals(newPswEdit.getText().toString())    ){
                    intiAmndPswData();

                }else {
                    //Toast.makeText(AmendPswActivity.this,"您填写的信息不全",Toast.LENGTH_LONG).show();
                MyAppliction.showExitGameAlert("您填写的信息不全或错误",AmendPswActivity.this);
                }


                break;

        }


    }
}
