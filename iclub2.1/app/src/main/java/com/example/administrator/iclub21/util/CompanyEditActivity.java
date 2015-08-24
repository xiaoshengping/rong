package com.example.administrator.iclub21.util;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.bean.BMerchantValueBean;
import com.example.administrator.iclub21.bean.ParmeBean;
import com.example.administrator.iclub21.bean.recruitment.AreaBean;
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
    @ViewInject(R.id.address_layout)
    private LinearLayout addressLayout;

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
    private  int city;
    private String province;
    private String cName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_edit);
        ViewUtils.inject(this);

        init();
    }

    private void init() {
        intiView();
        // intitbMerchantData();
        intiData();



    }



    private void intiData() {
      intent=getIntent();
      data= intent.getStringExtra("companyData");
     bMerchantValueBean= (BMerchantValueBean) intent.getSerializableExtra("bMerchantValueBean");
        if (data.equals("name")){
            editCompanyNameEv.setVisibility(View.VISIBLE);
            editCompanyTextTv.setText("公司名称");
            if (bMerchantValueBean!=null){
                if (!TextUtils.isEmpty(bMerchantValueBean.getBEcompanyName())){
                    editCompanyNameEv.setText(bMerchantValueBean.getBEcompanyName());
                }else {
                    editCompanyNameEv.setHint("请输入公司名称");
                }
            }else {
                editCompanyNameEv.setHint("请输入公司名称");
            }



        }else if (data.equals("phone")){
            editCompanyPhoneEt.setVisibility(View.VISIBLE);

            editCompanyTextTv.setText("电话");
            if (bMerchantValueBean!=null){
                if (!TextUtils.isEmpty(bMerchantValueBean.getBEphone())){
                    editCompanyPhoneEt.setText(bMerchantValueBean.getBEphone());

                }else {
                    editCompanyPhoneEt.setHint("请输入电话");
                }

            }else {
                editCompanyPhoneEt.setHint("请输入电话");

            }


        }else if (data.equals("email")){

            editCompanyEmailEt.setVisibility(View.VISIBLE);
            editCompanyTextTv.setText("Email");
            if (bMerchantValueBean!=null){
                if (!TextUtils.isEmpty(bMerchantValueBean.getBEemail())){
                    editCompanyEmailEt.setText(bMerchantValueBean.getBEemail());

                }else {
                    editCompanyEmailEt.setHint("请输入Email");
                }
            }else {


                editCompanyEmailEt.setHint("请输入Email");

            }



        }else if (data.equals("http")){

            editCompanyHttpEt.setVisibility(View.VISIBLE);

            editCompanyTextTv.setText("官网");
            if (bMerchantValueBean!=null){
                if (!TextUtils.isEmpty(bMerchantValueBean.getBEweb())){
                    editCompanyHttpEt.setText(bMerchantValueBean.getBEweb());

                }else {
                    editCompanyHttpEt.setHint("请输入官网");
                }

            }else {

                editCompanyHttpEt.setHint("请输入官网");
            }

        }else if (data.equals("adress")){
            addressLayout.setVisibility(View.VISIBLE);
            editCompanyTextTv.setText("公司地址");
            if (bMerchantValueBean!=null){
                if (!TextUtils.isEmpty(bMerchantValueBean.getBEaddress())){
                    editCompanyAddressEt.setText(bMerchantValueBean.getBEaddress());
                }
            }


        }






    }

    private void intiView() {
        requestParams=new RequestParams();
        editCompanyRetrunTv.setOnClickListener(this);
        editCompanyTextTv.setOnClickListener(this);
        editCompanySaveTv.setOnClickListener(this);

        myAppliction= (MyAppliction) getApplication();

    }

    private void intitbMerchantData() {
        SQLhelper sqLhelper=new SQLhelper(CompanyEditActivity.this);
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);

        }
        if (!TextUtils.isEmpty(uid)){
            HttpUtils httpUtils=new HttpUtils();
            requestParams.addBodyParameter("uid", uid);
            httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getAcquireMerchant(), requestParams, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String result = responseInfo.result;
                    if (!TextUtils.isEmpty(result)) {
                        ParmeBean<BMerchantValueBean> parmeBean = JSONObject.parseObject(result, new TypeReference<ParmeBean<BMerchantValueBean>>() {
                        });
                        bMerchantValueBean = parmeBean.getValue();

                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });


        }

        cursor.close();
        db.close();


    }




    private void intiHttpData() {
        HttpUtils httpUtils=new HttpUtils();
        SQLhelper sqLhelper=new SQLhelper(CompanyEditActivity.this);
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);

        }
        requestParams.addBodyParameter("uid", uid);
        if (data.equals("name")) {
            name=editCompanyNameEv.getText().toString();
            requestParams.addBodyParameter("BEcompanyName",name);

        }else if (data.equals("phone")){
            phone=editCompanyPhoneEt.getText().toString();
            requestParams.addBodyParameter("BEphone", phone);


        }else if (data.equals("email")){
            email=editCompanyEmailEt.getText().toString();
            requestParams.addBodyParameter("BEemail", email);
        }else if (data.equals("http")){
            web=editCompanyHttpEt.getText().toString();
            requestParams.addBodyParameter("BEweb", web);

        }else if (data.equals("adress")){

            address=editCompanyAddressEt.getText().toString();

                requestParams.addBodyParameter("BEaddress",address );



        }

        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getUpdateMerchant(),requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //Log.e("responseInfo111111111", responseInfo.result);
                intitbMerchantData();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailureonFailure",s);
            }
        });




    }

    private AreaBean areaBean = new AreaBean();

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_company_retrun_tv:
                finish();
                break;
            case R.id.company_city_tv:
                Intent intent1 = new Intent(this, SelectedCityOrPositionActivity.class);  //方法1
                intent1.putExtra("Status", areaBean.PROVINCE);
                intent1.putExtra("Company",-1);
                startActivityForResult(intent1, areaBean.PROVINCE);
                overridePendingTransition(R.anim.in_from_buttom, R.anim.out_to_not);

                break;
            case R.id.edit_company_save_text:

                if (data.equals("name")){
                    if (!TextUtils.isEmpty(editCompanyNameEv.getText().toString())){
                        intent.putExtra("name",editCompanyNameEv.getText().toString());
                        setResult(17, intent);
                        finish();
                    }else {
                       MyAppliction.showExitGameAlert("你还没有输入公司名字",CompanyEditActivity.this);
                    }

                } else if (data.equals("phone")) {

                    if (!TextUtils.isEmpty(editCompanyPhoneEt.getText().toString())){
                        intent.putExtra("phone",editCompanyPhoneEt.getText().toString());
                        setResult(18, intent);
                        finish();
                    }else {
                        MyAppliction.showExitGameAlert("你还没有输入电话号码",CompanyEditActivity.this);
                    }
                } else if (data.equals("email")) {
                    if (!TextUtils.isEmpty(editCompanyEmailEt.getText().toString())){
                        intent.putExtra("email",editCompanyEmailEt.getText().toString());
                        setResult(19, intent);
                        finish();
                    }else {
                        MyAppliction.showExitGameAlert("你还没有输入Email",CompanyEditActivity.this);
                    }

                } else if (data.equals("http")) {

                    if (!TextUtils.isEmpty(editCompanyHttpEt.getText().toString())){
                        intent.putExtra("http", editCompanyHttpEt.getText().toString());
                        setResult(20, intent);
                        finish();
                    }else {
                        MyAppliction.showExitGameAlert("你还没有输入公司官网",CompanyEditActivity.this);
                    }
                } else if (data.equals("adress")) {
                        intent.putExtra("adress",editCompanyAddressEt.getText().toString());
                    setResult(21, intent);
                    finish();
                }
                intiHttpData();
                break;

        }



    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
//        switch(requestCode){
//            case RESULT_OK:
   /*取得来自SecondActivity页面的数据，并显示到画面*/
        Bundle bundle = data.getExtras();

         /*获取Bundle中的数据，注意类型和key*/
        city = bundle.getInt("City");
         cName = bundle.getString("CityName");
         province = bundle.getString("PROVINCE");


    }

}
