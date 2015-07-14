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
import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.adapter.ResumeListAdapter;
import com.example.administrator.iclub21.bean.ResumeValueBean;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.util.AddResumeActivity;
import com.example.administrator.iclub21.util.ResumeListParticularActivity;
import com.example.administrator.iclub21.util.SQLhelper;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResumeFragment extends Fragment implements View.OnClickListener {
    //添加
    private TextView addResumeTv;
    private TextView retrunTv;
    private ListView resumeListLv;

    private  HttpUtils httpUtils;

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
        intiResumeListData();
        //Log.e("jsjdjfjfhfhfhsjsjfjfj",getActivity().getCacheDir().getPath());

    }

    private void intiDownload(String dowloadPath) {

        httpUtils.download(AppUtilsUrl.ImageBaseUrl+dowloadPath,getActivity().getCacheDir().getPath(), true, true, new RequestCallBack<File>() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);

            }

            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                responseInfo.result.getPath();
                Log.e("download",responseInfo.result.getPath());
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }


    private void intiView(View view) {
        View addView=LayoutInflater.from(getActivity()).inflate(R.layout.add_resume_layout,null);
        addResumeTv= (TextView) addView.findViewById(R.id.add_resume_tv);
        retrunTv= (TextView) view.findViewById(R.id.role_retrun_tv);
        resumeListLv= (ListView) view.findViewById(R.id.resume_list_lv);
        resumeListLv.addFooterView(addView);
        addResumeTv.setOnClickListener(this);
        retrunTv.setOnClickListener(this);



    }

    private void intiResumeListData() {
         httpUtils=new HttpUtils();
        SQLhelper sqLhelper=new SQLhelper(getActivity());
        SQLiteDatabase db= sqLhelper.getWritableDatabase();
        Cursor cursor=db.query("user", null, null, null, null, null, null);
        String uid=null;
        while (cursor.moveToNext()) {
            uid = cursor.getString(0);

        }

            String resumeListUrl= AppUtilsUrl.getResumeList(uid);
            httpUtils.send(HttpRequest.HttpMethod.POST, resumeListUrl, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {


                    String result=responseInfo.result;
                    ArtistParme<ResumeValueBean> artistParme= JSONObject.parseObject(result,new TypeReference<ArtistParme<ResumeValueBean>>(){});
                    final List<ResumeValueBean> resumeValueBeans= artistParme.getValue();
                    ResumeListAdapter resumeListAdapter=new ResumeListAdapter(resumeValueBeans,getActivity());
                    resumeListLv.setAdapter(resumeListAdapter);
                    resumeListAdapter.notifyDataSetChanged();
                    // final Bitmap bitmap= BitmapFactory.decodeFile("");
                    resumeListLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent=new Intent(getActivity(), ResumeListParticularActivity.class);
                            intent.putExtra("resumeValueBeans", resumeValueBeans.get(position));
                            intent.putExtra("flage","ResumeFragment");
                            startActivity(intent);
                            int movieSize=resumeValueBeans.get(position).getResumeMovie().size();
                            if (movieSize!=0&&resumeValueBeans.get(position).getResumeMovie()!=null){
                                for (int i = 0; i <movieSize; i++) {
                                    intiDownload(resumeValueBeans.get(i).getResumeMovie().get(i).getPath());
                                }

                            }


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

}
