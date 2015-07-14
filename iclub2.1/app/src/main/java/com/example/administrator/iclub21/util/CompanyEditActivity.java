package com.example.administrator.iclub21.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.bean.BMerchantValueBean;
import com.example.administrator.iclub21.http.MyAppliction;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

public class CompanyEditActivity extends ActionBarActivity implements View.OnClickListener {
    @ViewInject(R.id.edit_company_name_et)
    private EditText editCompanyNameEv;
    @ViewInject(R.id.edit_company_phone_et)
    private EditText editCompanyPhoneEt;
    @ViewInject(R.id.edit_company_email_et)
    private EditText editCompanyEmailEt;
    @ViewInject(R.id.edit_company_http_et)
    private EditText editCompanyHttpEt;
    @ViewInject(R.id.edit_company_address_et)
    private EditText editCompanyAddressEt;

    @ViewInject(R.id.edit_company_retrun_tv)
    private TextView editCompanyRetrunTv;
    @ViewInject(R.id.edit_company_text_tv)
    private TextView editCompanyTextTv;
    @ViewInject(R.id.edit_company_save_text)
    private TextView editCompanySaveTv;

    private  Intent intent;
    private  String data;

    private  MyAppliction myAppliction;
    private  String address;
    private  String name;
    private String web;
    private  String phone;
    private  String email;
    private RequestParams requestParams;
    private  BMerchantValueBean bMerchantValueBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_edit);
        ViewUtils.inject(this);

        init();
    }

    private void init() {
       requestParams=new RequestParams();
        intiView();
        intiData();



    }



    private void intiData() {
      intent=getIntent();
      data= intent.getStringExtra("companyData");
     bMerchantValueBean= (BMerchantValueBean) intent.getSerializableExtra("bMerchantValueBean");
        if (data.equals("name")){
            editCompanyNameEv.setVisibility(View.VISIBLE);
            editCompanyTextTv.setText("公司名称");
            if (!TextUtils.isEmpty(bMerchantValueBean.getBEcompanyName())){
                editCompanyNameEv.setText(bMerchantValueBean.getBEcompanyName());
            }else {
                editCompanyNameEv.setHint("请输入公司名称");
//                phone=bMerchantValueBean.getBEphone();
//                email=bMerchantValueBean.getBEemail();
//                web=bMerchantValueBean.getBEweb();
//                address=bMerchantValueBean.getBEaddress();
//                name=editCompanyNameEv.getText().toString();
            }


        }else if (data.equals("phone")){
            editCompanyPhoneEt.setVisibility(View.VISIBLE);

            editCompanyTextTv.setText("电话");
            if (!TextUtils.isEmpty(bMerchantValueBean.getBEphone())){
                editCompanyPhoneEt.setText(bMerchantValueBean.getBEphone());

            }else {
                editCompanyPhoneEt.setHint("请输入电话");
            }

//            name=bMerchantValueBean.getBEcompanyName();
//            email=bMerchantValueBean.getBEemail();
//            web=bMerchantValueBean.getBEweb();
//            address=bMerchantValueBean.getBEaddress();

        }else if (data.equals("email")){
             editCompanyEmailEt.setVisibility(View.VISIBLE);
            editCompanyEmailEt.setHint("请输入Email");
            editCompanyTextTv.setText("Email");
        }else if (data.equals("http")){
            editCompanyHttpEt.setVisibility(View.VISIBLE);
            editCompanyHttpEt.setHint("请输入官网");
            editCompanyTextTv.setText("官网");
        }else if (data.equals("adress")){
            editCompanyAddressEt.setVisibility(View.VISIBLE);
            editCompanyTextTv.setText("公司地址");

        }






    }

    private void intiView() {
        editCompanyRetrunTv.setOnClickListener(this);
        editCompanyTextTv.setOnClickListener(this);
        editCompanySaveTv.setOnClickListener(this);
        myAppliction= (MyAppliction) getApplication();


    }
    private void intiHttpData() {
        HttpUtils httpUtils=new HttpUtils();
        requestParams.addBodyParameter("uid", "15088138598");
        if (data.equals("name")) {
                phone=bMerchantValueBean.getBEphone();
                email=bMerchantValueBean.getBEemail();
                web=bMerchantValueBean.getBEweb();
                address=bMerchantValueBean.getBEaddress();
                name=editCompanyNameEv.getText().toString();
            requestParams.addBodyParameter("BEaddress",address );
            requestParams.addBodyParameter("BEcompanyName",name);
            requestParams.addBodyParameter("BEweb", web);
            requestParams.addBodyParameter("BEphone", phone);
            requestParams.addBodyParameter("BEemail", email);

        }else if (data.equals("phone")){
            name=bMerchantValueBean.getBEcompanyName();
            email=bMerchantValueBean.getBEemail();
            web=bMerchantValueBean.getBEweb();
            address=bMerchantValueBean.getBEaddress();
            phone=editCompanyPhoneEt.getText().toString();
            requestParams.addBodyParameter("BEaddress",address );
            requestParams.addBodyParameter("BEcompanyName",name);
            requestParams.addBodyParameter("BEweb", web);
            requestParams.addBodyParameter("BEphone", phone);
            requestParams.addBodyParameter("BEemail", email);

        }else if (data.equals("email")){
            name=bMerchantValueBean.getBEcompanyName();
            phone=bMerchantValueBean.getBEphone();
            web=bMerchantValueBean.getBEweb();
            address=bMerchantValueBean.getBEaddress();
            email=editCompanyEmailEt.getText().toString();
            requestParams.addBodyParameter("BEaddress",address );
            requestParams.addBodyParameter("BEcompanyName",name);
            requestParams.addBodyParameter("BEweb", web);
            requestParams.addBodyParameter("BEphone", phone);
            requestParams.addBodyParameter("BEemail", email);
        }else if (data.equals("http")){
            name=bMerchantValueBean.getBEcompanyName();
            phone=bMerchantValueBean.getBEphone();
            email=bMerchantValueBean.getBEemail();
            address=bMerchantValueBean.getBEaddress();
            web=editCompanyHttpEt.getText().toString();
            requestParams.addBodyParameter("BEaddress",address );
            requestParams.addBodyParameter("BEcompanyName",name);
            requestParams.addBodyParameter("BEweb", web);
            requestParams.addBodyParameter("BEphone", phone);
            requestParams.addBodyParameter("BEemail", email);
        }else if (data.equals("adress")){
            name=bMerchantValueBean.getBEcompanyName();
            phone=bMerchantValueBean.getBEphone();
            email=bMerchantValueBean.getBEemail();
            web=bMerchantValueBean.getBEweb();
            address=editCompanyAddressEt.getText().toString();
            requestParams.addBodyParameter("BEaddress",address );
            requestParams.addBodyParameter("BEcompanyName",name);
            requestParams.addBodyParameter("BEweb", web);
            requestParams.addBodyParameter("BEphone", phone);
            requestParams.addBodyParameter("BEemail", email);

        }

        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getUpdateMerchant(),requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("responseInfo111111111", responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailure",s);
            }
        });




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_company_retrun_tv:
                 finish();
                break;
            case R.id.edit_company_save_text:

                if (data.equals("name")){
                  intent.putExtra("name",editCompanyNameEv.getText().toString());
                    setResult(17, intent);
                    finish();
                } else if (data.equals("phone")) {
                    intent.putExtra("phone",editCompanyPhoneEt.getText().toString());
                    setResult(18, intent);


                    finish();
                } else if (data.equals("email")) {
                    intent.putExtra("email",editCompanyEmailEt.getText().toString());
                    setResult(19, intent);


                    finish();
                } else if (data.equals("http")) {
                    intent.putExtra("http", editCompanyHttpEt.getText().toString());
                    setResult(20, intent);


                    finish();
                } else if (data.equals("adress")) {
                    intent.putExtra("adress", editCompanyAddressEt.getText().toString());
                    setResult(21, intent);
                    finish();
                }
                intiHttpData();
            break;
        }



    }

}
