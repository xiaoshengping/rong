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

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.adapter.InfomationAdapter;
import com.example.administrator.iclub21.bean.InformationValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.util.DetailedInformationActivity;
import com.example.administrator.iclub21.util.SQLhelper;
import com.jeremy.Customer.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment implements View.OnClickListener {

    @ViewInject(R.id.role_retrun_tv)
    private TextView retrunTv;

    @ViewInject(R.id.message_listView)
    private ListView MessageListView;

    private List<InformationValueBean> informationValueBeans;
    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_message, container, false);
        ViewUtils.inject(this, view);
        intiData();
        retrunTv.setOnClickListener(this);
        return view;
    }


    private void intiData() {
        SQLhelper sqLhelper=new SQLhelper(getActivity());
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);
        }
        HttpUtils httpUtils=new HttpUtils();
        String informationUrl= AppUtilsUrl.getMessageList(uid,"30");
        httpUtils.send(HttpRequest.HttpMethod.POST, informationUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.e("1111111",result);
                ArtistParme<InformationValueBean> artistParme = JSONObject.parseObject(result, new TypeReference<ArtistParme<InformationValueBean>>() {
                });
                informationValueBeans = artistParme.getValue();
                InfomationAdapter infomationAdapter = new InfomationAdapter(informationValueBeans, getActivity());
                MessageListView.setAdapter(infomationAdapter);
                infomationAdapter.notifyDataSetChanged();
                MessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(),DetailedInformationActivity.class);
                        intent.putExtra("informationValueBeans", informationValueBeans.get(position));
                        startActivity(intent);


                    }
                });

            }

            @Override
            public void onFailure(HttpException e, String s) {

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
}
