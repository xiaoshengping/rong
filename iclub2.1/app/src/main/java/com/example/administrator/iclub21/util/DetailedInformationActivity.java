package com.example.administrator.iclub21.util;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.iclub21.bean.InformationValueBean;
import com.jeremy.Customer.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class DetailedInformationActivity extends ActionBarActivity implements View.OnClickListener {
    //公告详细
    @ViewInject(R.id.detail_information_layout)
    private LinearLayout detailLayout;
    @ViewInject(R.id.particular_title_tv)
    private TextView tailteDetailTv;
    @ViewInject(R.id.particular_time_tv)
    private TextView timeDetailTv;
    @ViewInject(R.id.particular_content_tv)
    private TextView contentDetailTv;
    @ViewInject(R.id.role_retrun_tv)
    private TextView retrunTv;
    @ViewInject(R.id.text_tv)
    private TextView textTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_information);
        ViewUtils.inject(this);
        inti();


    }

    private void inti() {
        textTv.setText("公告消息");
        retrunTv.setOnClickListener(this);
        InformationValueBean informationValueBean= (InformationValueBean) getIntent().getSerializableExtra("informationValueBeans");
        tailteDetailTv.setText(informationValueBean.getTitle());
        contentDetailTv.setText(informationValueBean.getContent());
        timeDetailTv.setText(informationValueBean.getPutdate());

    }


    @Override
    public void onClick(View v) {
        finish();
    }
}
