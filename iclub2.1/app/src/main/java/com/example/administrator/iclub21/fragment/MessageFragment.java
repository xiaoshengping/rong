package com.example.administrator.iclub21.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.adapter.ResumeMessageListAdapter;
import com.example.administrator.iclub21.bean.ResumeMessageValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.url.HttpHelper;
import com.example.administrator.iclub21.util.MerchantJobParticularActivity;
import com.example.administrator.iclub21.util.SQLhelper;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jeremy.Customer.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment implements View.OnClickListener,  PullToRefreshBase.OnRefreshListener2<ListView> {

    @ViewInject(R.id.role_retrun_tv)
    private TextView retrunTv;

    @ViewInject(R.id.message_tv)
    private TextView messageTv;

    @ViewInject(R.id.message_listView)
    //private ListView MessageListView;
    private PullToRefreshListView MessageListView;

    private List<ResumeMessageValueBean> informationValueBeans;
    private ResumeMessageListAdapter resumeMessageListAdapter;
    private int offset;
    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_message, container, false);
        ViewUtils.inject(this, view);
        intiListView();

        retrunTv.setOnClickListener(this);
        return view;
    }

    private void intiListView() {
        informationValueBeans=new ArrayList<>();
         resumeMessageListAdapter = new ResumeMessageListAdapter(informationValueBeans, getActivity());
        MessageListView.setAdapter(resumeMessageListAdapter);
        MessageListView.setMode(PullToRefreshBase.Mode.BOTH);
        MessageListView.setOnRefreshListener(this);
        ILoadingLayout endLabels  = MessageListView
                .getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在刷新...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
        ILoadingLayout startLabels  = MessageListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
        MessageListView.setRefreshing();

        MessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MerchantJobParticularActivity.class);
                intent.putExtra("informationValueBeans", informationValueBeans.get(position - 1).getJobid());
                startActivity(intent);


            }
        });





    }


    private void intiData(int offset) {
        SQLhelper sqLhelper=new SQLhelper(getActivity());
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);
        }
        HttpUtils httpUtils=new HttpUtils();
        String informationUrl= AppUtilsUrl.getMessageList(uid,offset);
        httpUtils.send(HttpRequest.HttpMethod.POST, informationUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                HttpHelper.baseToUrl(result, new TypeReference<ArtistParme<ResumeMessageValueBean>>() {
                }, informationValueBeans, resumeMessageListAdapter);
                MessageListView.onRefreshComplete();
                if (informationValueBeans.size()==0){
                    MessageListView.setVisibility(View.GONE);
                    messageTv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                MessageListView.onRefreshComplete();
                Log.e("hdhfhhf",s);
            }
        });




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.role_retrun_tv:
                getActivity().finish();
                break;


        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        informationValueBeans.clear();
        offset=0;
        intiData(offset);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

        offset=offset+10;
        intiData(offset);
    }
}
