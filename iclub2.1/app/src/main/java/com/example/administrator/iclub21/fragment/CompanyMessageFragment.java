package com.example.administrator.iclub21.fragment;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.bean.BMerchantValueBean;
import com.example.administrator.iclub21.bean.ParmeBean;
import com.example.administrator.iclub21.http.MyAppliction;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.util.CompanyEditActivity;
import com.example.administrator.iclub21.util.SQLhelper;
import com.jeremy.Customer.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyMessageFragment extends Fragment implements View.OnClickListener {

    @ViewInject(R.id.company_name)
    private LinearLayout companyNameLayout;
    @ViewInject(R.id.company_phone)
    private LinearLayout companyPhoneLayout;
    @ViewInject(R.id.company_email)
    private LinearLayout companyEmailLayout;
    @ViewInject(R.id.company_http)
    private LinearLayout companyHttpLayout;
    @ViewInject(R.id.comapny_address)
    private LinearLayout companyAddressLayout;
    @ViewInject(R.id.company_messaage_layout)
    private LinearLayout companyMessageLayout;


    @ViewInject(R.id.company_name_tv)
    private TextView companyNmeTv;
    @ViewInject(R.id.company_phone_tv)
    private TextView companyPhoneTv;
    @ViewInject(R.id.company_email_tv)
    private TextView companyEmailTv;
    @ViewInject(R.id.company_http_tv)
    private TextView companyHttpTv;
    @ViewInject(R.id.company_address_tv)
    private TextView companyAdressTv;
    @ViewInject(R.id.text_tv)
    private TextView textTv;
    @ViewInject(R.id.company_message_retrun_tv)
    private TextView companyMessageRetrun;

    private  BMerchantValueBean bMerchantValueBean;


    private RequestParams requestParams;
    @ViewInject(R.id.progressbar)
    private ProgressBar progressbar;

    public CompanyMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company_message, container, false);
        ViewUtils.inject(this, view);
        inti();

        return view;
    }

    private void inti() {
        textTv.setText("公司信息");
        intiView();
        progressbar.setVisibility(View.VISIBLE);
        MyAppliction.showToast("正在加载数据......");
        intitData();

    }


    @Override
    public void onResume() {
        super.onResume();
        intitData();
    }

    private void intitData() {
        SQLhelper sqLhelper=new SQLhelper(getActivity());
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
                        if (!TextUtils.isEmpty(bMerchantValueBean.getBEcompanyName())){
                            companyNmeTv.setText(bMerchantValueBean.getBEcompanyName());
                        }else {
                            companyNmeTv.setText("请填写公司名称(必填)");
                        }
                        if (!TextUtils.isEmpty(bMerchantValueBean.getBEphone())){
                            companyPhoneTv.setText(bMerchantValueBean.getBEphone());

                        }else {
                            companyPhoneTv.setText("请填写电话号码(必填)");
                        }
                        if (!TextUtils.isEmpty(bMerchantValueBean.getBEemail())){
                            companyEmailTv.setText(bMerchantValueBean.getBEemail());
                        }else {
                            companyEmailTv.setText("请填写Email(必填)");
                        }
                        if (!TextUtils.isEmpty(bMerchantValueBean.getBEweb())){
                            companyHttpTv.setText(bMerchantValueBean.getBEweb());
                        }else {
                            companyHttpTv.setText("请填写官网(选填)");
                        }

                        if (!TextUtils.isEmpty(bMerchantValueBean.getBEaddress())){
                            companyAdressTv.setText(bMerchantValueBean.getBEaddress());
                        }else {
                            companyAdressTv.setText("请填写公司地址(必填)");
                        }
                        progressbar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    MyAppliction.showToast("网络请求超时");
                    progressbar.setVisibility(View.GONE);
                }
            });


        }

        cursor.close();
        db.close();


    }

    private void intiView() {
        requestParams=new RequestParams();
        companyNameLayout.setOnClickListener(this);
        companyPhoneLayout.setOnClickListener(this);
        companyEmailLayout.setOnClickListener(this);
        companyHttpLayout.setOnClickListener(this);
        companyAddressLayout.setOnClickListener(this);
        companyMessageRetrun.setOnClickListener(this);

    }

   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       SQLhelper sqLhelper=new SQLhelper(getActivity());
       SQLiteDatabase db= sqLhelper.getWritableDatabase();
       Cursor cursor=db.query("user", null, null, null, null, null, null);
       String uid=null;
       while (cursor.moveToNext()) {
           uid = cursor.getString(0);



       }
        switch (resultCode){

            case 17:
               String name=data.getStringExtra("name").toString();
                companyNmeTv.setText(name);
                update(uid,name);
                break;
            case 18:
                String phone=data.getStringExtra("phone").toString();
                companyPhoneTv.setText(phone);
                break;
            case 19:
                String email=data.getStringExtra("email").toString();
                companyEmailTv.setText(email);
                break;
            case 20:
                String http=data.getStringExtra("http").toString();
                companyHttpTv.setText(http);

                break;
            case 21:
                String address=data.getStringExtra("adress").toString();
                companyAdressTv.setText(address);
                break;
        }



        super.onActivityResult(requestCode, resultCode, data);
    }



    /**
     * 更新公司名字记录的
     */
    public void update(String uid,String name){
        SQLhelper sqLhelper= new SQLhelper(getActivity());
        SQLiteDatabase db = sqLhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLhelper.COMPANYNAME, name);
        db.update(SQLhelper.tableName, contentValues,
                "uid=?",
                new String[]{uid});
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.company_name:
                Intent intentName = new Intent(getActivity(), CompanyEditActivity.class);
                intentName.putExtra("companyData", "name");
                intentName.putExtra("bMerchantValueBean",bMerchantValueBean);
                startActivityForResult(intentName, 17);
                break;
            case R.id.company_phone:
                Intent intentPhone = new Intent(getActivity(), CompanyEditActivity.class);
                intentPhone.putExtra("companyData", "phone");
                intentPhone.putExtra("bMerchantValueBean",bMerchantValueBean);
                startActivityForResult(intentPhone, 18);
                break;
            case R.id.company_email:
                Intent intentEmail = new Intent(getActivity(), CompanyEditActivity.class);
                intentEmail.putExtra("companyData", "email");
                intentEmail.putExtra("bMerchantValueBean",bMerchantValueBean);
                startActivityForResult(intentEmail, 19);
                break;
            case R.id.company_http:
                Intent intentHttp = new Intent(getActivity(), CompanyEditActivity.class);
                intentHttp.putExtra("companyData", "http");
                intentHttp.putExtra("bMerchantValueBean",bMerchantValueBean);
                startActivityForResult(intentHttp, 20);
                break;
            case R.id.comapny_address:
                Intent intentAddress = new Intent(getActivity(), CompanyEditActivity.class);
                intentAddress.putExtra("companyData", "adress");
                intentAddress.putExtra("bMerchantValueBean",bMerchantValueBean);
                startActivityForResult(intentAddress, 21);

                break;
            case R.id.company_message_retrun_tv:
//                Intent intent=new Intent(getActivity(), RoleActivity.class);
//                startActivity(intent);
                     getActivity().finish();

                break;


        }


    }
}
