package com.example.administrator.iclub21.util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.AccessTokenKeeper;
import com.example.administrator.iclub21.Constantser;
import com.example.administrator.iclub21.bean.LoginValueBean;
import com.example.administrator.iclub21.bean.ParmeBean;
import com.example.administrator.iclub21.bean.recruitment.SendParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.jeremy.Customer.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

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
    @ViewInject(R.id.qq_login)
    private ImageView qq_login;
    @ViewInject(R.id.weibo_login)
    private ImageView weibo_login;
    private HttpUtils httpUtils;
    private SQLhelper sqLhelper;

    public static Tencent mTencent;

    private static final String TAG = "weibosdk";

    /** 显示认证后的信息，如 AccessToken */
//    private TextView mTokenText;

    private AuthInfo mAuthInfo;

    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;



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
        qq_login.setOnClickListener(this);
        weibo_login.setOnClickListener(this);
        httpUtils=new HttpUtils();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_reten_tv:
               /* Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                intent.putExtra("state","");
                intent.putExtra("imageUrl","");
                intent.putExtra("userName","");
                //设置返回数据
                LoginActivity.this.setResult(RESULT_OK, intent);
                LoginActivity.this.finish();*/
                finish();
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
            case R.id.qq_login:
                mTencent = Tencent.createInstance("1102291619", this);
                doLogin();
                break;
            case R.id.weibo_login:
                weibodenglu();
                break;
            case R.id.register_tv:
               Intent registerIntent=new Intent(LoginActivity.this,RegisterActivity.class);
                registerIntent.putExtra("falge","2");
                startActivity(registerIntent);

                break;
            case R.id.forget_psw_tv:
                Intent forgetIntent=new Intent(LoginActivity.this,RegisterActivity.class);
                forgetIntent.putExtra("falge","3");
                startActivity(forgetIntent);

                break;
        }

    }

    private void weibodenglu(){
        // 获取 Token View，并让提示 View 的内容可滚动（小屏幕可能显示不全）

//        TextView hintView = (TextView) findViewById(com.sina.weibo.sdk.demo.R.id.obtain_token_hint);
//        hintView.setMovementMethod(new ScrollingMovementMethod());



        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (mAccessToken.isSessionValid()) {
//            updateTokenView(true);
//            Intent intent = new Intent();
//            LoginActivity.this.setResult(12, intent);
//                        /*结束本Activity*/
//            LoginActivity.this.finish();
        }
            // 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
            mAuthInfo = new AuthInfo(this, Constantser.APP_KEY, Constantser.REDIRECT_URL, Constantser.SCOPE);
            mSsoHandler = new SsoHandler(this, mAuthInfo);

            mSsoHandler.authorize(new AuthListener());


    }
    /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     *
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息
            String  phoneNum =  mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                // 显示 Token
                updateTokenView(false);

                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
                Toast.makeText(LoginActivity.this,R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = getString(R.string.weibosdk_demo_toast_auth_failed);
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(LoginActivity.this, "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 显示当前 Token 信息。
     *
     * @param hasExisted 配置文件中是否已存在 token 信息并且合法
     */
    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new java.util.Date(mAccessToken.getExpiresTime()));
        String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
//        mTokenText.setText(String.format(format, mAccessToken.getToken(), date));

        String message = String.format(format, mAccessToken.getToken(), date);
        if (hasExisted) {
            message = getString(R.string.weibosdk_demo_token_has_existed) + "\n" + message;
        }
//        mTokenText.setText(message);
        denglu(AppUtilsUrl.getLoginWeibo(mAccessToken.getToken()));
    }


    //qq登录
    private void denglu(String token){
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, token, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (result != null) {
                    SendParme<LoginValueBean> loginValueBean = com.alibaba.fastjson.JSONObject.parseObject(result, new TypeReference<SendParme<LoginValueBean>>() {
                    });
                    if (loginValueBean.getState().equals("success")) {
                        LoginValueBean loginValueData = com.alibaba.fastjson.JSONObject.parseObject(loginValueBean.getValue(), LoginValueBean.class);
                        SQLhelper sqLhelper=new SQLhelper(LoginActivity.this);
                        insertData(sqLhelper, loginValueData.getUid(), loginValueData.getUserName(), loginValueData.getUserIcon(), loginValueData.getState(), loginValueData.getMobile(),loginValueData.getPersonId(),loginValueData.getCompanyName());
                        finish();

                    }

                }


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void doLogin() {
        IUiListener listener = new BaseUiListener() {
            @Override
            protected void doComplete(org.json.JSONObject values) {
                try {
                    String token = values.getString(Constants.PARAM_ACCESS_TOKEN);
                    String expires = values.getString(Constants.PARAM_EXPIRES_IN);
                    String openId = values.getString(Constants.PARAM_OPEN_ID);
                    denglu(AppUtilsUrl.getLoginQQ(token));
//                    new_login_btn.setText(token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                updateLoginButton();
            }
        };
        mTencent.login(this, "all", listener);
    }


    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            //V2.0版本，参数类型由JSONObject 改成了Object,具体类型参考api文档
//            mBaseMessageText.setText("onComplete:");
            doComplete((org.json.JSONObject)response);
        }
        protected void doComplete(org.json.JSONObject values) {

        }
        @Override
        public void onError(UiError e) {
//            showResult("onError:", "code:" + e.errorCode + ", msg:"
//                    + e.errorMessage + ", detail:" + e.errorDetail);
        }
        @Override
        public void onCancel() {
//            showResult("onCancel", "");
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
                   // Log.e("intiData",rerult);
                    if (rerult!=null){

                        ParmeBean<LoginValueBean> artistParme= JSONObject.parseObject(rerult, new TypeReference<ParmeBean<LoginValueBean>>() {
                        });
                        LoginValueBean loginValueBean=  artistParme.getValue();
                        if ("success".equals(artistParme.getState())&&uid.equals(loginValueBean.getUid())){
                           //Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                            SQLhelper sqLhelper=new SQLhelper(LoginActivity.this);
                            insertData(sqLhelper, loginValueBean.getUid(), loginValueBean.getUserName(), loginValueBean.getUserIcon(), loginValueBean.getState(),
                                    loginValueBean.getMobile(), loginValueBean.getPersonId(),loginValueBean.getCompanyName());
                            Intent intent = new Intent();
                            LoginActivity.this.setResult(12, intent);
                        /*结束本Activity*/
                            LoginActivity.this.finish();

//                            finish();
                            /*Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);*/

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
    public void insertData(SQLhelper sqLhelper,String uid,String userName,String userIcon,String state,String mobile,String personid ,String companyName){
        SQLiteDatabase db=sqLhelper.getWritableDatabase();
       // db.execSQL("insert into user(uid,userName,userIcon,state) values('战士',3,5,7)");
        ContentValues values=new ContentValues();
        values.put(SQLhelper.UID,uid);
        values.put(SQLhelper.PERSONID,personid);
        values.put(SQLhelper.USERNAME, userName);
        values.put(SQLhelper.USERICON, userIcon);
        values.put(SQLhelper.STSTE, state);
        values.put(SQLhelper.MOBILE, mobile);
        values.put(SQLhelper.COMPANYNAME,companyName);
        db.insert(SQLhelper.tableName, SQLhelper.UID, values);
        db.close();
    }


}
