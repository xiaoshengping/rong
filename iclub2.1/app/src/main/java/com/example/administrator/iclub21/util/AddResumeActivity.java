package com.example.administrator.iclub21.util;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.bean.ParmeBean;
import com.example.administrator.iclub21.bean.ResumeValueBean;
import com.example.administrator.iclub21.bean.SaveResumeValueBean;
import com.example.administrator.iclub21.bean.recruitment.AreaBean;
import com.example.administrator.iclub21.calendar.CalendarActivity;
import com.example.administrator.iclub21.http.ImageUtil;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddResumeActivity extends ActionBarActivity implements View.OnClickListener ,RadioGroup.OnCheckedChangeListener {

    //头部
    @ViewInject(R.id.addresume_return_tv)
    private TextView addResumeReturnTv;
    @ViewInject(R.id.addresume_save_tv)
    private TextView addResumeSaveTv;

    //上传头像
    @ViewInject(R.id.updata_image)
    private LinearLayout updataImage;
    @ViewInject(R.id.touxiang_image)
    private ImageView usericonIV;
    //自我介绍
    @ViewInject(R.id.oneself_known_layout)
    private LinearLayout oneselfLayout;
    private String userInfo;
    @ViewInject(R.id.onself_text_tv)
    private TextView userOnselfText;

    //工作经历
    @ViewInject(R.id.work_experience_layout)
    private LinearLayout workLayout;
    private String userWork;
    @ViewInject(R.id.work_experience_text_tv)
    private TextView workExpexteText;


    //图片介绍
    @ViewInject(R.id.add_iamge_tv)
    private TextView addImage;
    private String userPicturePath;


    //视频介绍
    @ViewInject(R.id.add_video_tv)
    private TextView addVideoTv;
    private String userVideoPath;


    //音乐展示
    @ViewInject(R.id.add_music_tv)
    private TextView addMusicTv;
    private String userMusicPath;




    //个人信息
    @ViewInject(R.id.resumeZhName_et)
    private EditText resumeZhName;
    @ViewInject(R.id.resumeJobName_et)
    private EditText resumeJobName;
    @ViewInject(R.id.resumeQq_et)
    private EditText resumeQq;
    @ViewInject(R.id.resumeEmail_et)
    private EditText resumeEmail;
    @ViewInject(R.id.phone_editText_et)
    private TextView phoneTV;
    @ViewInject(R.id.phone_textView_tv)
    private TextView phoneTextView;
    @ViewInject(R.id.touxiang_image)
    private ImageView touXiangIv;
    @ViewInject(R.id.resume_age_tv)
    private TextView resumeAgeTv;

    private String userName;
    private  String sex = null;
    private String userJobName;
    private  String userQq;
    private  String userEmail;
    private  String touXiangPath;
    @ViewInject(R.id.sex_radio_group)
    private RadioGroup sexRadioGroup;
    @ViewInject(R.id.boy_radio_button)
    private RadioButton boyRadioButton;
    @ViewInject(R.id.girl_radio_button)
    private RadioButton girlRadioButton;
    @ViewInject(R.id.delete_resume_tv)
    private TextView deleteResumeTv;
    private String mobile;
    private   String uid=null;
    @ViewInject(R.id.progressbar)
    private ProgressBar progressbar;
   //下一步
    @ViewInject(R.id.next_layout)
    private LinearLayout nextLayout;
    @ViewInject(R.id.next_resume_tv)
    private TextView nextTextView;




    @ViewInject(R.id.job_classfite_layout)
    private LinearLayout jodClassfiteLayout;
    @ViewInject(R.id.job_city_layout)
    private LinearLayout job_city_layout;



    //日程安排
    @ViewInject(R.id.schedule_ll)
    private LinearLayout schedule_ll;
    @ViewInject(R.id.job_classfite_tv)
    private TextView job_classfite_tv;
    @ViewInject(R.id.job_city_tv)
    private TextView job_city_tv;


    //年龄
    @ViewInject(R.id.resume_age_tv)
    private TextView resumeAge;
    private File screenshotFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());

    private File tempFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());

    /*//加载滚动条
    @ViewInject(R.id.progressbar)
    private ProgressBar ProgressBar;*/



    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private HttpUtils httpUtils;

    private ResumeValueBean resumeValueBean;
    private String resumeNuber;
    private RequestParams compileRequestParams;
    private RequestParams requestParams;
    private DatePickerDialog datePickerDialog;
    private  int year1;
    private  int monthOfYear;
    private  int dayOfMonth;
    private String  age;
    private int selectYear;
    private int selectMonthOfYear;
    private int selectDayOfMonth;

    private AreaBean areaBean = new AreaBean();

    private int job_classfite_num = -1;//职业类别
    private int job_city_num = -1;//工作地点

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resume);
        ViewUtils.inject(this);

        inti();
    }




    private void inti() {
        intiView();
    }

    private void intiView() {

        requestParams = new RequestParams();
        httpUtils = new HttpUtils();
        updataImage.setOnClickListener(this);
        oneselfLayout.setOnClickListener(this);
        workLayout.setOnClickListener(this);
        addImage.setOnClickListener(this);
        addVideoTv.setOnClickListener(this);
        jodClassfiteLayout.setOnClickListener(this);
        job_city_layout.setOnClickListener(this);
        schedule_ll.setOnClickListener(this);
        addMusicTv.setOnClickListener(this);
        resumeAge.setOnClickListener(this);
        addResumeReturnTv.setOnClickListener(this);
        addResumeSaveTv.setOnClickListener(this);
        sexRadioGroup.setOnCheckedChangeListener(this);
        deleteResumeTv.setOnClickListener(this);
        nextTextView.setOnClickListener(this);
        phoneTV.setOnClickListener(this);
        //获取系统时间
        Calendar calendar = Calendar.getInstance();
        year1 = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DATE);
        resumeValueBeanData();   //列表数据
        selectDatabase();       //查询数据库

    }
        //列表数据
    private void resumeValueBeanData() {
        Intent intent=getIntent();
        resumeValueBean= (ResumeValueBean) intent.getSerializableExtra("compiledata");
        resumeNuber=intent.getStringExtra("resumeNuber");
        if (resumeValueBean!=null){
            resumeZhName.setText(resumeValueBean.getResumeZhName());
            if (resumeValueBean.getResumeSex()==0){
                boyRadioButton.setChecked(true);
            }else if(resumeValueBean.getResumeSex()==1){
                girlRadioButton.setChecked(true);
            }
            MyAppliction.imageLoader.displayImage(AppUtilsUrl.ImageBaseUrl + resumeValueBean.getUsericon(), touXiangIv, MyAppliction.RoundedOptions);
            resumeJobName.setText(resumeValueBean.getResumeJobName());
            resumeQq.setText(resumeValueBean.getResumeQq());
            resumeEmail.setText(resumeValueBean.getResumeEmail());
            userInfo=resumeValueBean.getResumeInfo();
            userWork=resumeValueBean.getResumeWorkExperience();
            userOnselfText.setText(resumeValueBean.getResumeInfo());
            workExpexteText.setText(resumeValueBean.getResumeWorkExperience());
            AreaBean areaBean=new AreaBean();
            resumeAgeTv.setText(resumeValueBean.getResumeAge()+"");
            job_classfite_tv.setText(areaBean.getNumPositionName(AddResumeActivity.this, resumeValueBean.getResumeJobCategory()));
            job_city_tv.setText(areaBean.getNumCityName(AddResumeActivity.this, resumeValueBean.getResumeCityId()));
            job_classfite_num=resumeValueBean.getResumeJobCategory();
            job_city_num=resumeValueBean.getResumeCityId();

        }else {
            resumeZhName.setHint("必填");
            resumeJobName.setHint("必填");
            resumeQq.setHint("必填");
            resumeEmail.setHint("必填");
            userOnselfText.setText("亲，请填写自我介绍哦(必填)");
            workExpexteText.setText("亲，请填写工作经历哦(必填)");
            resumeAgeTv.setText("必填");
        }
        if (resumeNuber.equals("1111")){
            nextLayout.setVisibility(View.VISIBLE);
            deleteResumeTv.setVisibility(View.VISIBLE);
            addResumeSaveTv.setVisibility(View.VISIBLE);
        }else {
            nextTextView.setVisibility(View.VISIBLE);

        }



    }

    //查询数据库
    private void selectDatabase() {
        SQLhelper sqLhelper = new SQLhelper(this);
        SQLiteDatabase db = sqLhelper.getWritableDatabase();
        Cursor cursor = db.query("user", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            uid = cursor.getString(0);
            mobile=cursor.getString(5);
        }
        //Log.e("mobile",mobile);
        if (!TextUtils.isEmpty(mobile)){
            phoneTextView.setText(mobile);

        }else {
            phoneTextView.setVisibility(View.GONE);
            phoneTV.setVisibility(View.VISIBLE);

        }
        cursor.close();
        db.close();



    }
        //日期
    public  DatePickerDialog datePickerDialogData() {


        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                age = (year1 - year)+"";
                //Log.e("age00000",age+"");
                if (Integer.valueOf(age)>0){
                    resumeAgeTv.setText(age);
                }else {
                    Toast.makeText(AddResumeActivity.this,"亲,您设置的年龄要大于0哦!",Toast.LENGTH_LONG).show();
                }
                selectYear=year;
                selectMonthOfYear=monthOfYear;
                selectDayOfMonth =dayOfMonth;
            }
        }, year1, monthOfYear, dayOfMonth);
         return datePickerDialog;

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.boy_radio_button:
                sex="0";
                break;
            case R.id.girl_radio_button:
                sex="1";
                break;

        }




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resume_age_tv:
                datePickerDialogData().show();
                break;
            case R.id.updata_image:
                showExitHeadPhotoAlert();
                break;
            case R.id.oneself_known_layout:
                Intent infoIntent = new Intent(AddResumeActivity.this, ResumeMessageActivity.class);
                if (resumeValueBean!=null){
                    infoIntent.putExtra("caseData", "oneself");
                    infoIntent.putExtra("resumeInfoData",resumeValueBean);
                }else {
                    infoIntent.putExtra("caseData", "oneselfNo");
                }

                startActivityForResult(infoIntent, 7);
                break;
            case R.id.work_experience_layout:
                Intent workIntent = new Intent(AddResumeActivity.this, ResumeMessageActivity.class);

                if (resumeValueBean!=null){
                    workIntent.putExtra("caseData", "work");
                    workIntent.putExtra("resumeInfoData",resumeValueBean);
                } else{
                    workIntent.putExtra("caseData", "workNo");

                }
                startActivityForResult(workIntent, 8);

                break;
            case R.id.add_iamge_tv:
                Intent pictureIntent = new Intent(AddResumeActivity.this, ResumeMessageActivity.class);
                if (resumeValueBean!=null){
                    pictureIntent.putExtra("caseData", "picture");
                    pictureIntent.putExtra("resumeInfoData",resumeValueBean);
                }
                startActivityForResult(pictureIntent, 9);

                break;
            case R.id.add_video_tv:
                Intent videoIntent = new Intent(AddResumeActivity.this, ResumeMessageActivity.class);
                if (resumeValueBean!=null){
                    videoIntent.putExtra("caseData", "video");
                    videoIntent.putExtra("resumeInfoData",resumeValueBean);

                }
                startActivityForResult(videoIntent, 10);

                break;
            case R.id.add_music_tv:
                Intent musicIntent = new Intent(AddResumeActivity.this, ResumeMessageActivity.class);
                if (resumeValueBean!=null){
                    musicIntent.putExtra("caseData", "music");
                    musicIntent.putExtra("resumeInfoData",resumeValueBean);
                }
                startActivityForResult(musicIntent, 11);
                break;
            case R.id.job_classfite_layout:
                Intent intentClassfite = new Intent(AddResumeActivity.this, SelectedCityOrPositionActivity.class);  //方法1
                intentClassfite.putExtra("Status", areaBean.POSITION);
                intentClassfite.putExtra("Company",-1);
                startActivityForResult(intentClassfite, 12);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_not);
                break;
            case R.id.job_city_layout:
                Intent intentCity = new Intent(AddResumeActivity.this, SelectedCityOrPositionActivity.class);  //方法1
                intentCity.putExtra("Status", areaBean.PROVINCE);
                intentCity.putExtra("Company",-1);
                startActivityForResult(intentCity, 12);
                overridePendingTransition(R.anim.in_from_buttom, R.anim.out_to_not);
                break;
            case R.id.addresume_save_tv:
               intiSaveData();
                break;
            case R.id.addresume_return_tv:
               /* Intent intent=new Intent(AddResumeActivity.this,ResumeActivity.class);
                startActivity(intent);*/
                if (resumeNuber.equals("2222")){
                    showExitGameAlert();
                }else {
                    setResult(18,getIntent().putExtra("closeActivity","Noclose"));
                    finish();
                }

                break;
            case R.id.schedule_ll://日程安排
                SQLhelper sqLhelper=new SQLhelper(this);
                SQLiteDatabase db= sqLhelper.getWritableDatabase();
                Cursor cursor=db.query("user", null, null, null, null, null, null);
                String id=null;
                while (cursor.moveToNext()) {
                    id = cursor.getString(0);

                }
                Intent intentSchedule = new Intent(AddResumeActivity.this, CalendarActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("userType",2);
                bundle.putString("Personid",id);
                intentSchedule.putExtras(bundle);
                startActivity(intentSchedule);
                break;
            case R.id.delete_resume_tv:
                showDeleteResumeAlert();

                break;
            case R.id.next_resume_tv:

                intiSaveData();
                break;
            case R.id.phone_editText_et:
                Intent intent=new Intent(AddResumeActivity.this,BoundPhoneActivity.class);
                startActivity(intent);

                break;

        }
    }

    private void deleteResumeData() {
        HttpUtils httpUtils=new HttpUtils();
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter("resumeid",resumeValueBean.getResumeid()+"");
        progressbar.setVisibility(View.VISIBLE);
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getDeleteResume(),requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                progressbar.setVisibility(View.GONE);

                showExitGameAlert("删除简历成功!");
            }

            @Override
            public void onFailure(HttpException e, String s) {
                       Log.e("00000",s);
            }
        });






    }

    public void showExitGameAlert(String text) {
        final AlertDialog dlg = new AlertDialog.Builder(AddResumeActivity.this).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.tishi_exit_dialog);
        TextView tailte = (TextView) window.findViewById(R.id.tailte_tv);
        tailte.setText(text);
        // 关闭alert对话框架
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        cancel.setText("确定");
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(18,getIntent().putExtra("closeActivity","close"));
                finish();

                dlg.cancel();
            }
        });
    }



    //返回对话框
    private void showExitGameAlert() {
        final AlertDialog dlg = new AlertDialog.Builder(AddResumeActivity.this).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.shrew_exit_dialog);
        TextView tailte = (TextView) window.findViewById(R.id.tailte_tv);
        tailte.setText("要放弃此次操作?");
        // 为确认按钮添加事件,执行退出应用操作
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        ok.setText("确定");
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                dlg.cancel();
            }
        });

        // 关闭alert对话框架
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        cancel.setText("取消");
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });
    }


    //删除简历对话框
    private void showDeleteResumeAlert() {
        final AlertDialog dlg = new AlertDialog.Builder(AddResumeActivity.this).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.shrew_exit_dialog);
        TextView tailte = (TextView) window.findViewById(R.id.tailte_tv);
        tailte.setText("要放弃此次操作?");
        // 为确认按钮添加事件,执行退出应用操作
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        ok.setText("确定");
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteResumeData();
                dlg.cancel();
            }
        });

        // 关闭alert对话框架
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        cancel.setText("取消");
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });
    }
    private void intiCompileData() {
        if (TextUtils.isEmpty(touXiangPath) || TextUtils.isEmpty(userName)
                || TextUtils.isEmpty(userJobName) || TextUtils.isEmpty(userQq) || TextUtils.isEmpty(userEmail)
                || TextUtils.isEmpty(userInfo) || TextUtils.isEmpty(userWork)||job_classfite_num==-1
                ||job_city_num==-1) {
            MyAppliction.showExitGameAlert("您填写的信息不全或错误", AddResumeActivity.this);

        } else {
            compileRequestParams = new RequestParams();
            compileRequestParams.addBodyParameter("resumeSex", sex);
            compileRequestParams.addBodyParameter("resumeid", resumeValueBean.getResumeid() + "");
            compileRequestParams.addBodyParameter("resumeWorkExperience", userWork);
            compileRequestParams.addBodyParameter("resumeInfo", userInfo);
            compileRequestParams.addBodyParameter("resumeEmail", userEmail);
            compileRequestParams.addBodyParameter("resumeQq", userQq);
            compileRequestParams.addBodyParameter("resumeJobName", userJobName);
            compileRequestParams.addBodyParameter("resumeZhName", userName);
            if (resumeValueBean.getUsericon()==null){
                compileRequestParams.addBodyParameter("usericon", new File(touXiangPath));
            }

            compileRequestParams.addBodyParameter("resumeJobCategory", job_classfite_num+"");
            compileRequestParams.addBodyParameter("resumeCityId", job_city_num + "");
            compileRequestParams.addBodyParameter("resumeMobile", mobile);
            if (selectYear!=0&&selectMonthOfYear!=0&&selectDayOfMonth!=0){
              compileRequestParams.addBodyParameter("birthday", selectYear + "-" + selectMonthOfYear + "-" + selectDayOfMonth);
           }



             progressbar.setVisibility(View.VISIBLE);
            httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getCompileResume(), compileRequestParams, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    // Log.e("onSuccess111111", responseInfo.result);
                    String result = responseInfo.result;
                    if (result != null) {
                        ParmeBean<SaveResumeValueBean> parmeBean = JSONObject.parseObject(result, new TypeReference<ParmeBean<SaveResumeValueBean>>() {
                        });
                        if (parmeBean.getState().equals("success")) {

                            String ResumeId = resumeValueBean.getResumeid() + "";
                            progressbar.setVisibility(View.GONE);
                            showExitGameAlert("保存简历成功!");


                        }
                    }

                }

                @Override
                public void onFailure(HttpException e, String s) {
                    progressbar.setVisibility(View.GONE);
                    MyAppliction.showToast("网络出错了");
                }
            });
        }

    }

    private void intiSaveData() {
       userName = resumeZhName.getText().toString();
        userJobName = resumeJobName.getText().toString();
        userQq = resumeQq.getText().toString();
        userEmail = resumeEmail.getText().toString();
        touXiangPath = screenshotFile.getAbsolutePath();
            if (resumeNuber.equals("2222")) {
                if (!tempFile.exists() || TextUtils.isEmpty(userName)
                        || TextUtils.isEmpty(userJobName) || TextUtils.isEmpty(userQq) || TextUtils.isEmpty(userEmail)
                        || TextUtils.isEmpty(userInfo) || TextUtils.isEmpty(userWork)||job_classfite_num==-1
                        ||job_city_num==-1||selectYear==0||selectMonthOfYear==0||selectDayOfMonth==0) {
                    MyAppliction.showExitGameAlert("您填写的信息不全或错误", AddResumeActivity.this);
                } else {
                    requestParams = new RequestParams();
                    requestParams.addBodyParameter("resumeSex", sex);
                    requestParams.addBodyParameter("uid", uid);
                    requestParams.addBodyParameter("resumeWorkExperience", userWork);
                    requestParams.addBodyParameter("resumeInfo", userInfo);
                    requestParams.addBodyParameter("resumeEmail", userEmail);
                    requestParams.addBodyParameter("resumeQq", userQq);
                    requestParams.addBodyParameter("resumeJobName", userJobName);
                    requestParams.addBodyParameter("resumeZhName", userName);
                    requestParams.addBodyParameter("usericon", new File(touXiangPath));
                    requestParams.addBodyParameter("resumeJobCategory", job_classfite_num + "");
                    requestParams.addBodyParameter("resumeCityId", job_city_num + "");
                    requestParams.addBodyParameter("resumeMobile", mobile);
                    requestParams.addBodyParameter("birthday", selectYear + "-" + selectMonthOfYear + "-" + selectDayOfMonth);
                    progressbar.setVisibility(View.VISIBLE);
                    httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getAddResume(), requestParams, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            // Log.e("onSuccess", responseInfo.result);
                            String result = responseInfo.result;
                            if (result != null) {
                                ParmeBean<SaveResumeValueBean> parmeBean = JSONObject.parseObject(result, new TypeReference<ParmeBean<SaveResumeValueBean>>() {
                                });
                                if (parmeBean.getState().equals("success")) {
                                    SaveResumeValueBean saveValueBean = parmeBean.getValue();
                                    if (saveValueBean.getMessage().equals("success")) {
                                        MyAppliction.showToast("提交数据成功");
                                    } else {
                                        MyAppliction.showToast(saveValueBean.getMessage());
                                    }
                                    Intent intent = new Intent(AddResumeActivity.this, NextResumeActivity.class);
                                    intent.putExtra("resumeid", saveValueBean.getResumeid());
                                    startActivity(intent);
                                    progressbar.setVisibility(View.GONE);
                                    setResult(18, getIntent().putExtra("closeActivity", "close"));
                                    finish();


                                }
                            }

                            //

                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            progressbar.setVisibility(View.GONE);
                            MyAppliction.showToast("网络出错了");

                        }
                    });
                }
                //deleteResumeTv.setVisibility(View.GONE);
            }else if (resumeNuber.equals("1111")){

                intiCompileData();

            }




    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:// 当选择拍照时调用
                startPhotoZoom(Uri.fromFile(tempFile));
                break;
            case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
                // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
                if (data != null)
                    startPhotoZoom(data.getData());
                break;
            case PHOTO_REQUEST_CUT:// 返回的结果
                if (data != null)
                    // setPicToView(data);
                    sentPicToNext(data);

                break;
            case 7:
                userInfo = data.getStringExtra("userInfo").toString();
                //Log.e("userInfo",userInfo);
                if (TextUtils.isEmpty(userInfo)){
                    if (resumeValueBean!=null){
                        if (TextUtils.isEmpty(resumeValueBean.getResumeInfo())){
                            userOnselfText.setText("亲，要填写自我介绍哦!(必填)");
                        }else {
                            userOnselfText.setText(resumeValueBean.getResumeInfo());
                        }

                    }else {
                        userOnselfText.setText("亲，要填写自我介绍哦!(必填)");
                    }

                }else {
                    userOnselfText.setText(userInfo);
                }

                break;

            case 8:
                userWork = data.getStringExtra("userWork").toString();
                //Log.e("userWork",userWork);
                if (TextUtils.isEmpty(userWork)){
                  if (resumeValueBean!=null){
                      if (TextUtils.isEmpty(resumeValueBean.getResumeWorkExperience())){
                          workExpexteText.setText("亲，要填写工作经历哦!(必填)");
                      }else {
                          workExpexteText.setText(resumeValueBean.getResumeWorkExperience());
                      }
                  }else {
                      workExpexteText.setText("亲，要填写工作经历哦!(必填)");
                  }


                }else {
                    workExpexteText.setText(userWork);
                }

                break;
            /*case 9:
                userPicturePath = data.getStringExtra("userPicturePath").toString();
                 //Log.e("userPicturePath",userPicturePath);
                break;
            case 10:
                userVideoPath = data.getStringExtra("userVideoPath").toString();
                // Log.e("userVideoPath",userVideoPath);
                break;
            case 11:
                userMusicPath = data.getStringExtra("usermusicPath").toString();
                break;*/
            case 12:
                /*取得来自SecondActivity页面的数据，并显示到画面*/
                Bundle bundle = data.getExtras();

                /*获取Bundle中的数据，注意类型和key*/
                int city = bundle.getInt("City");//地区号
                String cName = bundle.getString("CityName");//地区名
                if(city>=0) {
                    //job_city_tv.setText(cName+"（城市编号："+city+"）");
                    job_city_tv.setText(cName);
                    job_city_num = city;
                }
                int job = bundle.getInt("Position");//职业号
                String pName = bundle.getString("PositionName");//职业名
                if(job>=0&&job!=10){
             //       job_classfite_tv.setText(pName+"（职业编号："+job+"）");
                  job_classfite_tv.setText(pName);
                    job_classfite_num = job;
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    // 将进行剪裁后的图片传递到下一个界面上
    private void sentPicToNext(Intent picdata) {
        Bundle bundle = picdata.getExtras();

        if (bundle != null) {
            Bitmap photo = bundle.getParcelable("data");
            if (photo == null) {
                usericonIV.setImageResource(R.mipmap.ic_launcher);
            } else {
                Bitmap zoomBitmap = ImageUtil.zoomBitmap(photo, 100, 100);
                //获取圆角图片
                Bitmap roundBitmap = ImageUtil.getRoundedCornerBitmap(zoomBitmap, 200.0f);
                //获取倒影图片
                //Bitmap reflectBitmap = ImageUtil.createReflectionImageWithOrigin(roundBitmap);
                usericonIV.setImageBitmap(roundBitmap);
//                设置文本内容为    图片绝对路径和名字
                //text.setText(tempFile.getAbsolutePath());
                //Log.e("tempFile",tempFile.getAbsolutePath());


                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(screenshotFile);
                    if (null != fos) {
                        photo.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.flush();
                        fos.close();

                        //  Toast.makeText(ResumeActivity.this, "截屏文件已保存至SDCard/AndyDemo/ScreenImage/下", Toast.LENGTH_LONG).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] photodata = baos.toByteArray();
                //System.out.println(photodata.toString());

            } catch (Exception e) {
                e.getStackTrace();
            } finally {
                if (baos != null) {
                    try {
                        baos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }






    //拍照对话框
    private void showExitHeadPhotoAlert() {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.upload_image_layout);
        TextView tailte = (TextView) window.findViewById(R.id.tailte_tv);
        //tailte.setText(photograph);
        tailte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ResumeActivity.this, CramaActivity.class);
//                startActivity(intent);
                Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定调用相机拍照后照片的储存路径
                cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(tempFile));
                startActivityForResult(cameraintent, PHOTO_REQUEST_TAKEPHOTO);
                dlg.cancel();

            }
        });

        // 为确认按钮添加事件,执行退出应用操作
        TextView ok = (TextView) window.findViewById(R.id.btn_ok);
        //ok.setText(selectFile);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 退出应用...
                Intent intent = new Intent();
                intent.setType("image/*");
                //intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);
                dlg.cancel();
            }
        });

        // 关闭alert对话框架
        TextView cancel = (TextView) window.findViewById(R.id.btn_cancel);
        //cancel.setText("取消");
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });
    }



    /*private void initAddVideoData(String resumeid, String videoPath) {

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("resumeid", resumeid);
        requestParams.addBodyParameter("movie", new File(videoPath));
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getAddMovie(), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
               // Log.e("initAddVideoData", responseInfo.result);
               // ProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailure", s);
            }
        });


    }

    private void intiPhotoData(String resumeid, String userPicturePath) {
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("resumeid", resumeid);
        requestParams.addBodyParameter("picture", new File(userPicturePath));

        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getAddPicture(), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
               // Log.e("intiPhotoData", responseInfo.result);

            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailure", s);
            }
        });


    }


    private void intiMusicData(String resumeid, String musicPath) {

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("resumeid", resumeid);
        requestParams.addBodyParameter("music", new File(musicPath));
        httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getAddMusic(), requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
               // Log.e("onSuccess", responseInfo.result);
                //ProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("onFailure", s);
            }
        });

    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (resumeNuber.equals("2222")){
                showExitGameAlert();
            }else {
                setResult(18,getIntent().putExtra("closeActivity","Noclose"));
                finish();
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
