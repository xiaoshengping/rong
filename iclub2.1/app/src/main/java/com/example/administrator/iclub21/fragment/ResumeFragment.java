package com.example.administrator.iclub21.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.adapter.ResumeListAdapter;
import com.example.administrator.iclub21.bean.ResumeValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.url.HttpHelper;
import com.example.administrator.iclub21.util.AddResumeActivity;
import com.example.administrator.iclub21.util.ResumeListParticularActivity;
import com.example.administrator.iclub21.util.SQLhelper;
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
 * A simple {@link Fragment} subclass.
 */
public class ResumeFragment extends Fragment implements View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ListView> {
    //添加
    private TextView addResumeTv;
    private TextView retrunTv;
    private PullToRefreshListView resumeListLv;

    private  HttpUtils httpUtils;
    private List<ResumeValueBean> resumeValueBeans;
    private  ResumeListAdapter resumeListAdapter;
    private int offset=0;

    public ResumeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_resume, container, false);
        inti(view);
        return view;
    }
    private void inti(View view) {
        intiView(view);

        //Log.e("jsjdjfjfhfhfhsjsjfjfj",getActivity().getCacheDir().getPath());

    }

    @Override
    public void onResume() {
        super.onResume();
        resumeListLv.setRefreshing();
    }

    private void intiView(View view) {
        View addView=LayoutInflater.from(getActivity()).inflate(R.layout.add_resume_layout,null);
        addResumeTv= (TextView) addView.findViewById(R.id.add_resume_tv);
        retrunTv= (TextView) view.findViewById(R.id.role_retrun_tv);
        resumeListLv= (PullToRefreshListView) view.findViewById(R.id.resume_list_lv);
        ListView listView=resumeListLv.getRefreshableView();
        listView.addFooterView(addView);
        addResumeTv.setOnClickListener(this);
        retrunTv.setOnClickListener(this);
        resumeValueBeans=new ArrayList<ResumeValueBean>();
         resumeListAdapter=new ResumeListAdapter(resumeValueBeans,getActivity());
        listView.setAdapter(resumeListAdapter);
        resumeListLv.setMode(PullToRefreshBase.Mode.BOTH);
        resumeListLv.setOnRefreshListener(this);
        ILoadingLayout loadingLayout = resumeListLv
                .getLoadingLayoutProxy();
        loadingLayout.setPullLabel("加载更多数据...");// 刚下拉时，显示的提示
        loadingLayout.setRefreshingLabel("正在刷新...");// 刷新时
        loadingLayout.setReleaseLabel("加载更多数据...");// 下来达到一定距离时，显示的提示
        resumeListLv.setRefreshing();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ResumeListParticularActivity.class);
                intent.putExtra("resumeValueBeans", resumeValueBeans.get(position-1));
                intent.putExtra("flage", "ResumeFragment");
                startActivity(intent);




            }
        });
    }

    private void intiResumeListData(int offset) {
         httpUtils=new HttpUtils();
        SQLhelper sqLhelper=new SQLhelper(getActivity());
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);

        }
            String resumeListUrl= AppUtilsUrl.getResumeList(uid,offset);
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
                Intent intent =new Intent(getActivity(),AddResumeActivity.class);
                intent.putExtra("resumeNuber","2222");
                intent.putExtra("resumeValueBeans", "");
                startActivity(intent);
                break;
            case R.id.role_retrun_tv:
                getActivity().finish();
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
