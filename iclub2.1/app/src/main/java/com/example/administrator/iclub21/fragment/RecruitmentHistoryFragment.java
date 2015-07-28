package com.example.administrator.iclub21.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.adapter.RecruitmentHistoryAdapter;
import com.example.administrator.iclub21.bean.RecruitmentHistoryValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.url.HttpHelper;
import com.example.administrator.iclub21.util.AddRecruitmentActivity;
import com.example.administrator.iclub21.util.SQLhelper;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sina.weibo.sdk.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecruitmentHistoryFragment extends Fragment implements View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ListView> {
    @ViewInject(R.id.text_tv)
    private TextView textTv;
    @ViewInject(R.id.save_text)
    private TextView saveTv;
    @ViewInject(R.id.recruiment_history_list_lv)
    private PullToRefreshListView recruitmentHistoryLv;
    @ViewInject(R.id.company_message_retrun_tv)
    private TextView  recruitmentRetrunTv;
    private List<RecruitmentHistoryValueBean> recruitmentHistoryValueBean;
    private RecruitmentHistoryAdapter recruitmentHistoryAdapter;
    private int offset=0;

    public RecruitmentHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_recruitment_history, container, false);
        ViewUtils.inject(this,view);
       inti();
        return view;
    }

    private void inti() {
        initView();
        intiListView();


    }

    private void intiListView() {
        recruitmentHistoryValueBean=new ArrayList<RecruitmentHistoryValueBean>();
        recruitmentHistoryAdapter=new RecruitmentHistoryAdapter(recruitmentHistoryValueBean,getActivity());
        recruitmentHistoryLv.setAdapter(recruitmentHistoryAdapter);
        recruitmentHistoryLv.setMode(PullToRefreshBase.Mode.BOTH);
        recruitmentHistoryLv.setOnRefreshListener(this);
        ILoadingLayout loadingLayout = recruitmentHistoryLv
                .getLoadingLayoutProxy();
        loadingLayout.setPullLabel("你可劲拉，拉...");// 刚下拉时，显示的提示
        loadingLayout.setRefreshingLabel("好嘞，正在刷新...");// 刷新时
        loadingLayout.setReleaseLabel("你敢放，我就敢刷新...");// 下来达到一定距离时，显示的提示
        recruitmentHistoryLv.setRefreshing();
        recruitmentHistoryLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), AddRecruitmentActivity.class);
                intent.putExtra("recruitmentHistoryValueBean", recruitmentHistoryValueBean.get(position-1));
                intent.putExtra("falgeData", "RecruitmentHistoryFragment");
                startActivity(intent);
            }
        });




    }

    private void initView() {
        textTv.setText("招聘历史");
        saveTv.setVisibility(View.VISIBLE);
        saveTv.setText("添加");
        saveTv.setOnClickListener(this);
        recruitmentRetrunTv.setOnClickListener(this);

    }

    private void initRecruitmentHistoryData(int offset) {
        SQLhelper sqLhelper=new SQLhelper(getActivity());
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        while (cursor.moveToNext()) {
          uid = cursor.getString(0);

        }
        if (!TextUtils.isEmpty(uid)){
            HttpUtils httpUtils=new HttpUtils();
            httpUtils.send(HttpRequest.HttpMethod.POST, AppUtilsUrl.getRecruitmentHistoryList(uid,offset), new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String result=responseInfo.result;

                    if (result!=null){
                       /* ArtistParme<RecruitmentHistoryValueBean> artistParme= JSONObject.parseObject(result, new TypeReference<ArtistParme<RecruitmentHistoryValueBean>>() {
                        });
                        recruitmentHistoryValueBean=  artistParme.getValue();*/

                        HttpHelper.baseToUrl(result, new TypeReference<ArtistParme<RecruitmentHistoryValueBean>>() {
                        }, recruitmentHistoryValueBean, recruitmentHistoryAdapter);
                        recruitmentHistoryLv.onRefreshComplete();

                        //Log.e("result",recruitmentHistoryValueBean.get(0).getAddress());
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_text:
                if (recruitmentHistoryValueBean!=null)
                {
                    Intent intent=new Intent(getActivity(), AddRecruitmentActivity.class);
                    intent.putExtra("falgeData","");
                    startActivity(intent);

                }else {
                    FragmentManager fragmentManager=getFragmentManager();
                    FragmentTransaction ft= fragmentManager.beginTransaction();
                    CompanyMessageFragment companyMessageFragment=new CompanyMessageFragment();
                    //ft.add(R.id.merchant_fragment_layout,companyMessageFragment);
                    ft.hide(new RecruitmentHistoryFragment());
                    ft.show(companyMessageFragment);
                    ft.commit();


                }
                break;
            case R.id.company_message_retrun_tv:
//                Intent intent=new Intent(getActivity(), RoleActivity.class);
//                startActivity(intent);

              getActivity().finish();
               break;

        }
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        recruitmentHistoryValueBean.clear();
        int offset=0;
        initRecruitmentHistoryData(offset);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        offset=offset+10;
        initRecruitmentHistoryData(offset);
    }
}
