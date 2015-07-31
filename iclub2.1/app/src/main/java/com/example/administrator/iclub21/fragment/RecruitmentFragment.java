package com.example.administrator.iclub21.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.adapter.RecruitmentListAdapter;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.bean.recruitment.AreaBean;
import com.example.administrator.iclub21.bean.recruitment.JobDetailsDialog;
import com.example.administrator.iclub21.bean.recruitment.RecruitmentImageBean;
import com.example.administrator.iclub21.bean.recruitment.RecruitmentListBean;
import com.example.administrator.iclub21.bean.recruitment.SlideShowView;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.util.JobDetailsActivity;
import com.example.administrator.iclub21.util.SelectedCityOrPositionActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sina.weibo.sdk.demo.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecruitmentFragment extends Fragment {
    @ViewInject(R.id.v1)
    private View v1;
    @ViewInject(R.id.recruitment_listView)
    private ListView recruitmentList;
    @ViewInject(R.id.recruiment_list_title)
    private LinearLayout recruiment_list_title;
    @ViewInject(R.id.reagment_title_search_ib)
    private ImageButton reagment_title_search_ib;
    //    @ViewInject(R.id.selected_city)
    private Button selected_city;
    //    @ViewInject(R.id.selected_position)
    private Button selected_position;
    @ViewInject(R.id.reagment_title_tv)
    private TextView reagment_title_tv;
    @ViewInject(R.id.back_ib)
    private ImageButton back_ib;
    @ViewInject(R.id.progressbar)
    private RelativeLayout progressbar;


    private RecruitmentListAdapter recruitmentAdapter;

    private int citynum = 0;//城市id
    private int jobnum = 0;//城市id
    private AreaBean areaBean = new AreaBean();

    private List<RecruitmentListBean> recruitmentListData;


    public RecruitmentFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recruitment, container, false);
        ViewUtils.inject(this, view);

        inti();
        return view;
    }

    private LayoutInflater mInflater;
    private boolean searchStatusfalse;//搜索状态

    private void inti() {

        initRecruitmentImageData();

        back_ib.setVisibility(View.GONE);

        //搜索
        reagment_title_search_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchStatusfalse=true;
                dialog();
            }
        });
        back_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchStatusfalse = false;
                citynum = 0;
                jobnum = 0;
                sousuo = "";
                selected_city.setText("选择城市");
                selected_position.setText("选择职位");
                reagment_title_tv.setText("娱乐招聘");
                initRecruitmentListData(0,0);
                back_ib.setVisibility(View.GONE);
//                initRecruitmentListData(0,0,"");
            }
        });


        //设置浮框
//        recruitmentList.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
////                if(firstVisibleItem ==1){
////                    recruiment_list_title.setVisibility(View.INVISIBLE);
//////                    recruiment_list_title_ad.setVisibility(View.VISIBLE);
////                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
//
////
//                if (firstVisibleItem >=1 ) {
////
////                    recruiment_list_title_ad.setVisibility(View.GONE);
////                } else if(firstVisibleItem ==1 ) {
//                    recruiment_list_title.setVisibility(View.VISIBLE);
////                    if(recruiment_list_title_ad ==null){
//                    recruiment_list_title_ad = (LinearLayout) view.findViewById(R.id.recruiment_list_title);
//                    recruiment_list_title_ad.setVisibility(View.INVISIBLE);
//                    recruitmentAdapter.floatingCollar=true;
//
////                    recruiment_list_title_ad.setVisibility(View.GONE);
////                }else if(firstVisibleItem ==1){
////                    recruiment_list_title.setVisibility(View.INVISIBLE);
////                    recruiment_list_title_ad.setVisibility(View.VISIBLE);
//               }else {
//                    recruiment_list_title.setVisibility(View.GONE);
//                    if(recruiment_list_title_ad!=null){
//                        recruiment_list_title_ad.setVisibility(View.VISIBLE);
//                        recruitmentAdapter.floatingCollar=false;
//                    }
//
//                }
//
////                recruitmentAdapter.notifyDataSetChanged();
//
//            }
//        });
//
    }

    private JobDetailsDialog srarchBoxDialog;


    //搜索框
    private void dialog() {

        WindowManager wm = getActivity().getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int heigth = wm.getDefaultDisplay().getHeight();

        srarchBoxDialog = new JobDetailsDialog(getActivity(), width ,heigth);
        Window window = srarchBoxDialog.getWindow();
        window.setGravity(Gravity.TOP);
        //清除
        srarchBoxDialog.setEliminate_tv(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                srarchBoxDialog.getEt().setText("");
                srarchBoxDialog.dismiss();
            }
        });

        //搜索
        srarchBoxDialog.getEt().setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_UNSPECIFIED) {

                    back_ib.setVisibility(View.VISIBLE);
                    sousuo = srarchBoxDialog.getEt().getText().toString();
                    reagment_title_tv.setText(sousuo);
//                initRecruitmentListData(citynum, jobnum, sousuo);
                    citynum = 0;
                    jobnum = 0;
                    selected_position.setText("选择职位");
                    selected_city.setText("选择城市");
                    update(getActivity(), citynum, jobnum, sousuo);
                    srarchBoxDialog.dismiss();
//                    progressbar.setVisibility(View.VISIBLE);
//                    update(ArtistSeekActivity.this, artist_seek_et.getText().toString());
//                    Toast.makeText(ArtistSeekActivity.this, "你点了软键盘回车按钮",
//                            Toast.LENGTH_SHORT).show();
                }
                return (arg2.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager) srarchBoxDialog.getEt().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 300);

        srarchBoxDialog.show();
    }

    private String sousuo = "";

    //获取广告图片
    private void initRecruitmentImageData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getRecruitmentImage(), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (result != null) {
                    ArtistParme<RecruitmentImageBean> recruitmentImageBean = JSONObject.parseObject(result, new TypeReference<ArtistParme<RecruitmentImageBean>>() {
                    });
                    if (recruitmentImageBean.getState().equals("success")) {
                        List<RecruitmentImageBean> recruitmentImageData = recruitmentImageBean.getValue();

                        View header = View.inflate(getActivity(), R.layout.recruitment_list_header, null);//头部内容
                        SlideShowView ssv = new SlideShowView(getActivity(), recruitmentImageData);

                        LinearLayout header_ll = (LinearLayout) header.findViewById(R.id.header_ll);
                        selected_city = (Button) header.findViewById(R.id.selected_city);
                        selected_position = (Button) header.findViewById(R.id.selected_position);
                        selected_city.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Bundle bundle = new Bundle();
//                                bundle.putInt("Status", areaBean.PROVINCE);
                                Intent intent = new Intent(getActivity(), SelectedCityOrPositionActivity.class);  //方法1
                                intent.putExtra("Status", areaBean.PROVINCE);
                                startActivityForResult(intent, areaBean.PROVINCE);
                                getActivity().overridePendingTransition(R.anim.in_from_buttom, R.anim.out_to_not);
                            }


                        });
                        selected_position.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                Bundle bundle = new Bundle();
//                                bundle.putInt("Status", areaBean.PROVINCE);
                                Intent intent = new Intent(getActivity(), SelectedCityOrPositionActivity.class);  //方法1
                                intent.putExtra("Status", areaBean.POSITION);
                                startActivityForResult(intent, areaBean.POSITION);
                                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_not);
                            }


                        });
                        header_ll.addView(ssv);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (getResources().getDimension(R.dimen.ssv_height)));
                        layoutParams.setMargins(0, 0, 0, 0);
                        ssv.setLayoutParams(layoutParams);
                        recruitmentList.addHeaderView(header);//添加头部
//                        initRecruitmentListData(0, 0, "");
                        initRecruitmentListData(0,0);

                    }

                }


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });


    }

    private void update(Context context,int city, int job, String abc){
        progressbar.setVisibility(View.VISIBLE);
        UpdateTextTask updateTextTask = new UpdateTextTask(context,city,job,abc);
        updateTextTask.execute();
    }


    class UpdateTextTask extends AsyncTask<Void,Integer,Integer> {
        private Context context;
        private int city;
        private int job;
        private String abc;
        UpdateTextTask(Context context,int city, int job, String abc) {
            this.context = context;
            this.city = city;
            this.job = job;
            this.abc = abc;
        }

        /**
         * 运行在UI线程中，在调用doInBackground()之前执行
         */
        @Override
        protected void onPreExecute() {
//            Toast.makeText(context,"开始执行",Toast.LENGTH_SHORT).show();
        }
        /**
         * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
         */
        @Override
        protected Integer doInBackground(Void... params) {
            int i=0;
            while(i<10){
                i++;
                publishProgress(i);
                initRecruitmentListData(city, job, abc);

            }
            return null;
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(Integer integer) {
//            Toast.makeText(context, "执行完毕", Toast.LENGTH_SHORT).show();
//            reagment_title_tv.setText("1");
            progressbar.setVisibility(View.INVISIBLE);
            recruitmentAdapter = new RecruitmentListAdapter(recruitmentListData, getActivity());
            recruitmentList.setAdapter(recruitmentAdapter);
            recruitmentAdapter.notifyDataSetChanged();

            if(recruitmentListData.size()==0){
                Toast.makeText(getActivity(), "暂时还没有相关数据", Toast.LENGTH_LONG).show();
            }

            recruitmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), JobDetailsActivity.class);  //方法1
//                intent.putCharSequenceArrayListExtra("Detail",recruitmentListData);
                Bundle bundle=new Bundle();
                bundle.putSerializable("Detail",recruitmentListData.get(position-1));
                intent.putExtras(bundle);
//                intent.putExtra("Status", areaBean.PROVINCE);
                startActivity(intent);


            }
            });
        }

        /**
         * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
//            tv.setText(""+values[0]);
        }
    }


    //获取招聘列表（非搜索）
    private void initRecruitmentListData(int city, int job) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getRecruitmentList(city,job), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (result != null) {
                    ArtistParme<RecruitmentListBean> recruitmentListBean = JSONObject.parseObject(result, new TypeReference<ArtistParme<RecruitmentListBean>>() {
                    });
                    if (recruitmentListBean.getState().equals("success")) {
                        recruitmentListData = recruitmentListBean.getValue();
                        recruitmentAdapter = new RecruitmentListAdapter(recruitmentListData, getActivity());
                        recruitmentList.setAdapter(recruitmentAdapter);
                        recruitmentAdapter.notifyDataSetChanged();

                        recruitmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getActivity(), JobDetailsActivity.class);  //方法1
//                intent.putCharSequenceArrayListExtra("Detail",recruitmentListData);
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("Detail",recruitmentListData.get(position-1));
                                intent.putExtras(bundle);
//                intent.putExtra("Status", areaBean.PROVINCE);
                                startActivity(intent);
                            }
                        });

                    }

                }


            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });


    }



    //获取招聘列表
    public void initRecruitmentListData(int city, int job, String abc) {
        HttpPost httpPost = new HttpPost(AppUtilsUrl.getRecruitmentList(city, job, 0, abc));
        // 设置HTTP POST请求参数必须用NameValuePair对象
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("cityid", city+""));
        params.add(new BasicNameValuePair("jobCategory", job+""));
        params.add(new BasicNameValuePair("keyWord", abc));
        params.add(new BasicNameValuePair("offset", "0"));
        params.add(new BasicNameValuePair("limit", "10"));

        HttpResponse httpResponse = null;
        try {
            // 设置httpPost请求参数
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            httpResponse = new DefaultHttpClient().execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 第三步，使用getEntity方法活得返回结果
                String result = EntityUtils.toString(httpResponse.getEntity());

                System.out.println(result);
                if (result != null) {
                    ArtistParme<RecruitmentListBean> recruitmentListBean = JSONObject.parseObject(result, new TypeReference<ArtistParme<RecruitmentListBean>>() {
                    });
                    if (recruitmentListBean.getState().equals("success")) {
                        recruitmentListData = recruitmentListBean.getValue();

                    }

                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }










//        HttpPost httpPost=new HttpPost();
//        httpPost.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getRecruitmentList(city,job,0,abc), new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//
//                String result = responseInfo.result;
//                if (result != null) {
//                    ArtistParme<RecruitmentListBean> recruitmentListBean = JSONObject.parseObject(result, new TypeReference<ArtistParme<RecruitmentListBean>>() {
//                    });
//                    if (recruitmentListBean.getState().equals("success")) {
//                        recruitmentListData = recruitmentListBean.getValue();
//                        recruitmentAdapter = new RecruitmentListAdapter(recruitmentListData, getActivity());
//                        recruitmentList.setAdapter(recruitmentAdapter);
//                        recruitmentAdapter.notifyDataSetChanged();
////                        selected_city.setVisibility(View.INVISIBLE);
//
//
//                    }
//
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//
//            }
//        });

//        recruitmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), JobDetailsActivity.class);  //方法1
////                intent.putCharSequenceArrayListExtra("Detail",recruitmentListData);
//                Bundle bundle=new Bundle();
//                bundle.putSerializable("Detail",recruitmentListData.get(position-1));
//                intent.putExtras(bundle);
////                intent.putExtra("Status", areaBean.PROVINCE);
//                startActivity(intent);
//            }
//        });


//    }




    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
//        switch(requestCode){
//            case RESULT_OK:
   /*取得来自SecondActivity页面的数据，并显示到画面*/
        Bundle bundle = data.getExtras();

         /*获取Bundle中的数据，注意类型和key*/
        int city = bundle.getInt("City");
        String cName = bundle.getString("CityName");
        if(city>=0) {
            if (city!=0) {
                selected_city.setText(cName);
            }else {
                selected_city.setText("选择城市");
            }
            citynum = city;
            if(searchStatusfalse) {
                update(getActivity(), citynum, jobnum, sousuo);
            }else {
                initRecruitmentListData(citynum,jobnum);
            }
//            initRecruitmentListData(citynum,jobnum,"");

        }
        int job = bundle.getInt("Position");
        String pName = bundle.getString("PositionName");
        if(job>=0&&job!=10){
            if (job!=0) {
                selected_position.setText(pName);
            }else {
                selected_position.setText("选择职位");
            }
            jobnum = job;
            if(searchStatusfalse) {
                update(getActivity(), citynum, jobnum, sousuo);
            }else {
                initRecruitmentListData(citynum,jobnum);
            }
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
////            if (ss) {
////                ss = false;
////                citynum = 0;
////                jobnum = 0;
////                sousuo = "";
////                selected_city.setText("选择城市");
////                selected_position.setText("选择职位");
////                reagment_title_tv.setText("娱乐招聘");
////                update(getActivity(), citynum, jobnum, sousuo);
////                back_b.setVisibility(View.GONE);
////            } else {
////                getActivity().finish();
////            }
////
//            return true;
//        } else {
//            return super.onKeyDown(keyCode, event);
//        }
//
//    }


}
