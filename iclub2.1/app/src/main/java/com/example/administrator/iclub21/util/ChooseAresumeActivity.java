package com.example.administrator.iclub21.util;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.adapter.ResumeListAdapter;
import com.example.administrator.iclub21.bean.ResumeValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.bean.recruitment.JobDetailsDialog;
import com.example.administrator.iclub21.bean.recruitment.SendParme;
import com.example.administrator.iclub21.bean.recruitment.ViewCountBean;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.url.HttpHelper;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jeremy.Customer.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/19.
 */
public class ChooseAresumeActivity extends Activity implements View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ListView>{

    //添加
    private TextView addResumeTv;
    private TextView retrunTv;
    private PullToRefreshListView resumeListLv;
    private LinearLayout resume_ll;

    private  HttpUtils httpUtils;
    private List<ResumeValueBean> resumeValueBeans;
    private  ResumeListAdapter resumeListAdapter;
    private int offset=0;
    private int jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_resume);
        inti();
    }
    private void inti() {
        Bundle bundle = getIntent().getExtras();
        jobId = bundle.getInt("jobId");

        intiView();

        //Log.e("jsjdjfjfhfhfhsjsjfjfj",getActivity().getCacheDir().getPath());

    }

    @Override
    public void onResume() {
        super.onResume();
        resumeListLv.setRefreshing();
    }

    private void intiView() {
        View addView= LayoutInflater.from(this).inflate(R.layout.add_resume_layout, null);
        addResumeTv= (TextView) addView.findViewById(R.id.add_resume_tv);
        addResumeTv.setVisibility(View.GONE);
        resume_ll = (LinearLayout)findViewById(R.id.resume_ll);
        resume_ll.setBackgroundResource(R.mipmap.iculd_beijin_icon);
        retrunTv= (TextView) findViewById(R.id.role_retrun_tv);
        resumeListLv= (PullToRefreshListView) findViewById(R.id.resume_list_lv);
        ListView listView=resumeListLv.getRefreshableView();
        listView.addFooterView(addView);
        addResumeTv.setOnClickListener(this);
        retrunTv.setOnClickListener(this);
        resumeValueBeans=new ArrayList<ResumeValueBean>();
        resumeListAdapter=new ResumeListAdapter(resumeValueBeans,this);
        listView.setAdapter(resumeListAdapter);
        resumeListLv.setMode(PullToRefreshBase.Mode.BOTH);
        resumeListLv.setOnRefreshListener(this);
        ILoadingLayout endLabels  = resumeListLv
                .getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在刷新...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
        ILoadingLayout startLabels  = resumeListLv
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
        resumeListLv.setRefreshing();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialog(resumeValueBeans.get(position-1).getResumeid(),resumeValueBeans.get(position-1).getResumeJobName());
//                Intent intent = new Intent(ChooseAresumeActivity.this, ResumeListParticularActivity.class);
//                intent.putExtra("resumeValueBeans", resumeValueBeans.get(position-1));
//                intent.putExtra("flage", "ResumeFragment");
//                startActivity(intent);




            }
        });
    }

    private JobDetailsDialog dialog2;
    private boolean sendBl = false;

    private void dialog(final int resumeid,String resume) {
        dialog2 = new JobDetailsDialog(this,resume);
//        EditText editText = (EditText) dialog.getEditText();//方法在CustomDialog中实现
        dialog2.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            HttpUtils httpUtils = new HttpUtils();
            httpUtils.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getSend(jobId, resumeid), new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String result = responseInfo.result;
                    if (result != null) {
                    SendParme<ViewCountBean> viewCountBean = JSONObject.parseObject(result, new TypeReference<SendParme<ViewCountBean>>() {
                    });
                    if (viewCountBean.getState().equals("success")) {
                        ViewCountBean viewCountData = JSONObject.parseObject(viewCountBean.getValue(), ViewCountBean.class);

                        if (viewCountData.getMessage().equals("success")) {
                            sendBl = true;

                        } else if (viewCountData.getMessage().equals("failure")){
                            sendBl = false;
                        }else {
                            sendBl = false;
                        }

                        Intent intent = new Intent();
                        intent.putExtra("SendBl", sendBl);
                        /*给上一个Activity返回结果*/
                        ChooseAresumeActivity.this.setResult(12, intent);
                        /*结束本Activity*/
                        ChooseAresumeActivity.this.finish();

                    }

                    }


                    }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });


//                Intent intent = new Intent(ChooseAresumeActivity.this, LoginActivity.class);
//                startActivityForResult(intent, 1);
                dialog2.dismiss();
            }
        });
        dialog2.setOnNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

//        Window window = dialog2.getWindow();
//        window.setGravity(Gravity.TOP);
//        WindowManager.LayoutParams lp = window.getAttributes();
//        // 设置透明度为0.3
//        lp.alpha = 0.6f;
//        window.setAttributes(lp);

        dialog2.show();
    }

    private void intiResumeListData(int offset) {
        httpUtils=new HttpUtils();
        SQLhelper sqLhelper=new SQLhelper(this);
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);

        }
        String resumeListUrl= AppUtilsUrl.getResumeList(uid, offset);
        httpUtils.send(HttpRequest.HttpMethod.POST, resumeListUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {


                String result=responseInfo.result;
                if (result!=null){
                    HttpHelper.baseToUrl(result, new TypeReference<ArtistParme<ResumeValueBean>>() {
                    }, resumeValueBeans, resumeListAdapter);
                    resumeListLv.onRefreshComplete();

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
            case  R.id.add_resume_tv:
                Intent intent =new Intent(this,AddResumeActivity.class);
                intent.putExtra("resumeNuber","2222");
                intent.putExtra("resumeValueBeans", "");
                startActivity(intent);
                break;
            case R.id.role_retrun_tv:
                ChooseAresumeActivity.this.finish();
                break;


        }

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        resumeValueBeans.clear();
        int offset=0;
        intiResumeListData(offset);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        resumeValueBeans.clear();
        offset=offset+10;
        intiResumeListData(offset);
    }
}

