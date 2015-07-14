package com.example.administrator.iclub21.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.adapter.TalentListAdapter;
import com.example.administrator.iclub21.bean.artist.ArtistParme;
import com.example.administrator.iclub21.bean.recruitment.AreaBean;
import com.example.administrator.iclub21.bean.talent.TalentValueBean;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.util.SelectedCityOrPositionActivity;
import com.example.administrator.iclub21.util.TalendDetailsActivity;
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
public class TalentFragment extends Fragment {
    @ViewInject(R.id.talent_listView)
          private ListView talentList;

    private Button selected_city;
    private Button selected_position;
    private AreaBean areaBean = new AreaBean();
    private int citynum = 0;//城市id
    private int jobnum = 0;//城市id

    List<TalentValueBean> talentData;


    public TalentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_talent, container, false);
        ViewUtils.inject(this, view);
        inti();
        return view;
    }


    private void inti() {

        View header = View.inflate(getActivity(), R.layout.recruitment_list_header, null);//头部内容

        LinearLayout header_ll = (LinearLayout) header.findViewById(R.id.header_ll);
        header_ll.setVisibility(View.GONE);
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

        talentList.addHeaderView(header);//添加头部
        //初始化
        initTalentData(0, 0);


    }



    private void initTalentData(int city , int jobcategory) {
        HttpUtils httpUtils=new HttpUtils();

      httpUtils.send(HttpRequest.HttpMethod.GET, AppUtilsUrl.getTalentList(city, jobcategory), new RequestCallBack<String>() {
          @Override
          public void onSuccess(ResponseInfo<String> responseInfo) {
              String result = responseInfo.result;
              if (result != null) {
                  ArtistParme<TalentValueBean> talentBean = JSONObject.parseObject(result, new TypeReference<ArtistParme<TalentValueBean>>() {
                  });
                  if (talentBean.getState().equals("success")) {
                      talentData = talentBean.getValue();
                      TalentListAdapter talentAdapter = new TalentListAdapter(talentData, getActivity());
                      talentList.setAdapter(talentAdapter);
                      talentAdapter.notifyDataSetChanged();

                      talentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                          @Override
                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                              Intent intent = new Intent(getActivity(), TalendDetailsActivity.class);  //方法1
//                intent.putCharSequenceArrayListExtra("Detail",recruitmentListData);
                              Bundle bundle = new Bundle();
                              bundle.putSerializable("Detail", talentData.get(position - 1));
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
            initTalentData(citynum,jobnum);
//            update(getActivity(),citynum,jobnum,sousuo);
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
            initTalentData(citynum, jobnum);
//            initRecruitmentListData(citynum,jobnum,"");
        }
    }


}
