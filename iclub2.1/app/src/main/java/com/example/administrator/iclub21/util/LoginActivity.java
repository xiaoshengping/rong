package com.example.administrator.iclub21.util;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.bean.LoginValueBean;
import com.example.administrator.iclub21.bean.ParmeBean;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.security.NoSuchAlgorithmException;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {
    @ViewInject(R.id.edit_phone)
    private EditText phoneEdit;
    @ViewInject(R.id.edit_psw)
    private EditText pswEdit;
    @ViewInject(R.id.login_button)
    private Button loginButton;
    @ViewInject(R.id.login_reten_tv)
    private TextView returnTV;
    @ViewInject(R.id.register_tv)
    private TextView RegisterTv;
   @ViewInject(R.id.forget_psw_tv)
    private TextView forgetTv;
    private HttpUtils httpUtils;
    private SQLhelper sqLhelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);
        init();
    }
     //程序入口
    private void init() {
       intiView();
        sqLhelper=new SQLhelper(this);

    }
    private void intiView() {
        // loginButton.setOnClickListener(this);
        returnTV.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        RegisterTv.setOnClickListener(this);
        forgetTv.setOnClickListener(this);
        httpUtils=new HttpUtils();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_reten_tv:
                Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                intent.putExtra("state","");
                intent.putExtra("imageUrl","");
                intent.putExtra("userName","");
                //设置返回数据
                LoginActivity.this.setResult(RESULT_OK, intent);
                LoginActivity.this.finish();
                break;
            case R.id.login_button:
                 String uid=  phoneEdit.getText().toString();
                 String  psw=MD5Uutils.MD5(pswEdit.getText().toString());
                if(uid!="0"&&psw!="0"){
                try {
                    intiLoginData(uid,psw);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                }
                break;
            case R.id.register_tv:
               Intent registerIntent=new Intent(LoginActivity.this,RegisterActivity.class);
                registerIntent.putExtra("falge","2");
                startActivityForResult(registerIntent,1);

                break;
            case R.id.forget_psw_tv:
                Intent forgetIntent=new Intent(LoginActivity.this,RegisterActivity.class);
                forgetIntent.putExtra("falge","3");
                startActivityForResult(forgetIntent,1);

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String uid = data.getExtras().getString("uid");
        String psw = data.getExtras().getString("psw");
        try {
            intiLoginData(uid,psw);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    //登录数据
    private void intiLoginData(final String uid,String psw) throws NoSuchAlgorithmException {

        if (uid.length()!=11&&uid!="0"&&psw!="0"){
            Toast.makeText(LoginActivity.this,"您输入的电话号码不正确",Toast.LENGTH_LONG).show();

        }else {
            httpUtils.send(HttpRequest.HttpMethod.POST,AppUtilsUrl.getLoginData(uid,psw) , new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String rerult=responseInfo.result;
                    Log.e("intiData",rerult);
                    if (rerult!=null){

                        ParmeBean<LoginValueBean> artistParme= JSONObject.parseObject(rerult, new TypeReference<ParmeBean<LoginValueBean>>() {
                        });
                        LoginValueBean loginValueBean=  artistParme.getValue();
                       // Log.e("makeText",loginValueBean.getState());
                        if ("success".equals(artistParme.getState())&&uid.equals(loginValueBean.getUid())){
                           Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                            SQLhelper sqLhelper=new SQLhelper(LoginActivity.this);
                            insertData(sqLhelper, loginValueBean.getUid(), loginValueBean.getUserName(), loginValueBean.getUserIcon(), loginValueBean.getState(), loginValueBean.getMobile());

                            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                            intent.putExtra("state",loginValueBean.getState());
                            intent.putExtra("imageUrl",loginValueBean.getUserIcon());
                            intent.putExtra("userName",loginValueBean.getUserName());
                            //设置返回数据
                            LoginActivity.this.setResult(RESULT_OK, intent);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this,"密码或用户名错误",Toast.LENGTH_LONG).show();


                        }
                    }


                }

                @Override
                public void onFailure(HttpException e, String s) {
                    // Log.e("onFailure",s);
                }
            });

        }
        //Log.e("psw",MD5Uutils.encryptMd5(psw));





    }
    public void insertData(SQLhelper sqLhelper,String uid,String userName,String userIcon,String state,String mobile ){
        SQLiteDatabase db=sqLhelper.getWritableDatabase();
       // db.execSQL("insert into user(uid,userName,userIcon,state) values('战士',3,5,7)");
        ContentValues values=new ContentValues();
        values.put(SQLhelper.UID,uid);
        values.put(SQLhelper.USERNAME, userName);
        values.put(SQLhelper.USERICON, userIcon);
        values.put(SQLhelper.STSTE, state);
        values.put(SQLhelper.MOBILE, mobile);
        db.insert(SQLhelper.tableName, SQLhelper.UID, values);
        db.close();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("state", "");
            intent.putExtra("imageUrl", "");
            intent.putExtra("userName", "");
            //设置返回数据
            LoginActivity.this.setResult(RESULT_OK, intent);
            LoginActivity.this.finish();
        }
        return false;
    }
}
