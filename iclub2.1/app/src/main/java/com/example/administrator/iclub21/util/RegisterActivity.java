package com.example.administrator.iclub21.util;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.bean.ParmeBean;
import com.example.administrator.iclub21.bean.RegisterValueBean;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

public class RegisterActivity extends ActionBarActivity implements View.OnClickListener {
    @ViewInject(R.id.register_reten_tv)
     private TextView returnTv;
    @ViewInject(R.id.register_commit_tv)
    private TextView commitTv;
    @ViewInject(R.id.register_phone)
    private EditText registerPhoneEdit;
    @ViewInject(R.id.captcha_edit)
    private EditText captchaEdit;
    @ViewInject(R.id.captcha_button)
    private Button captchaBtton;
    @ViewInject(R.id.set_psw_edit)
    private EditText setPswEdit;
    @ViewInject(R.id.verify_psw_edit)
    private EditText verifyPswEdit;
       private HttpUtils httpUtils;
    @ViewInject(R.id.register_title_tv)
    private TextView registerTitleTv;
    private  String  data;
    private TimeCount time;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ViewUtils.inject(this);
        init();
    }
       //程序入口
    private void init() {
       intiView();



    }

    private void intiView() {
        returnTv.setOnClickListener(this);
        commitTv.setOnClickListener(this);
        captchaBtton.setOnClickListener(this);
        httpUtils=new HttpUtils();
        String data=getIntent().getStringExtra("falge");
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        if (data.equals("2")){
            registerTitleTv.setText("注册");
        }else if (data.equals("3")){
            registerTitleTv.setText("忘记密码");
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_reten_tv:
                finish();
                break;
            case R.id.register_commit_tv:
               if (data.equals("2")){
                   intiRegisterData(AppUtilsUrl.getRegisterData());
               }else if (data.equals("3")){
                   intiRegisterData(AppUtilsUrl.getForgetData());
               }

                break;
            case R.id.captcha_button:
                  intiVcodeData();
                break;

        }


    }

    private void intiVcodeData() {
                String pohten=registerPhoneEdit.getText().toString();
                  if (pohten!=null&&pohten.length()==11) {
                      time.start();
                      httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getVcodeData(pohten), new RequestCallBack<String>() {
                          @Override
                          public void onSuccess(ResponseInfo<String> responseInfo) {

                          }

                          @Override
                          public void onFailure(HttpException e, String s) {
                              Log.e("onFailure", s);
                          }
                      });
                  }else {
                      Toast.makeText(RegisterActivity.this, "电话号码错误", Toast.LENGTH_LONG).show();

                  }

    }

    private void intiRegisterData(String url) {

       String psw= MD5Uutils.MD5(setPswEdit.getText().toString());
       String  verifypsw=  MD5Uutils.MD5(verifyPswEdit.getText().toString());
         String uid=registerPhoneEdit.getText().toString();
         String capcha=captchaEdit.getText().toString();
        if (uid.length()==11&&uid!=null){
            if (capcha!=null&&capcha.length()==6){

                     if (setPswEdit.getText().toString()!=null&&verifyPswEdit.getText().toString()!=null&&(verifyPswEdit.getText().toString()).equals(setPswEdit.getText().toString())) {
                         RequestParams requestParams=new RequestParams();
                         requestParams.addBodyParameter("uid",registerPhoneEdit.getText().toString());
                         requestParams.addBodyParameter("pwd",MD5Uutils.MD5(setPswEdit.getText().toString()));
                         requestParams.addBodyParameter("vcode", captchaEdit.getText().toString());
                         httpUtils.send(HttpRequest.HttpMethod.POST, url, requestParams, new RequestCallBack<String>() {
                             @Override
                             public void onSuccess(ResponseInfo<String> responseInfo) {
                                 String rerult = responseInfo.result;
                                 Log.e("intiData", rerult);
                                 if (rerult != null) {

                                     ParmeBean<RegisterValueBean> artistParme = JSONObject.parseObject(rerult, new TypeReference<ParmeBean<RegisterValueBean>>() {
                                     });
                                     RegisterValueBean registerValueBean = artistParme.getValue();
                                     // Log.e("makeText",loginValueBean.getState());
                                     if ("success".equals(registerValueBean.getMessage())) {
                                         Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                                         Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                         intent.putExtra("uid", registerPhoneEdit.getText().toString());
                                         intent.putExtra("psw", MD5Uutils.MD5(setPswEdit.getText().toString()));
                                         //设置返回数据
                                         RegisterActivity.this.setResult(RESULT_OK, intent);
                                         finish();
                                     } else {
                                         Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();


                                     }
                                 }
                             }

                             @Override
                             public void onFailure(HttpException e, String s) {
                                 Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_LONG).show();
                             }
                         });
                     }else {

                         Toast.makeText(RegisterActivity.this, "您输入的密码不一致", Toast.LENGTH_LONG).show();
                     }



            }else {
                Toast.makeText(RegisterActivity.this, "验证码不正确", Toast.LENGTH_LONG).show();

            }

        }else {

            Toast.makeText(RegisterActivity.this, "您输入的电话号码不正确", Toast.LENGTH_LONG).show();

        }


    }
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            captchaBtton.setText("重新验证");
            captchaBtton.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            captchaBtton.setClickable(false);
            captchaBtton.setText(millisUntilFinished / 1000 + "秒");
        }

    }

}
