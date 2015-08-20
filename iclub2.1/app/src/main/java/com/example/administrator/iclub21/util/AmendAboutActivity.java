package com.example.administrator.iclub21.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jeremy.Customer.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AmendAboutActivity extends ActionBarActivity implements View.OnClickListener {
    @ViewInject(R.id.register_reten_tv)
    private TextView aboutReturnTv;
    @ViewInject(R.id.register_title_tv)
    private TextView aboutTitleTv;
    @ViewInject(R.id.register_commit_tv)
    private  TextView aboutCimmitTv;
    @ViewInject(R.id.login_layout)
    private RelativeLayout aboutTaileLayout;

    //功能介绍
    @ViewInject(R.id.about_function_layout)
    private LinearLayout aboutFubctionLayout;

    @ViewInject(R.id.about_idea_layout)
    private LinearLayout aboutIdeaLayout;

    @ViewInject(R.id.about_layout)
    private LinearLayout aboutLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amend_about);
        ViewUtils.inject(this);
        aboutInti();

    }

    private void aboutInti() {
        aboutCimmitTv.setVisibility(View.GONE);
        aboutTitleTv.setText("关于iClub");
        aboutInitView();

    }

    private void aboutInitView() {

        aboutReturnTv.setOnClickListener(this);
        aboutFubctionLayout.setOnClickListener(this);
        aboutIdeaLayout.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.register_reten_tv:
                finish();
                break;
            case R.id.about_function_layout:
                Intent intent=new Intent(AmendAboutActivity.this,AboutFunctionActivity.class);
                startActivity(intent);

                break;
            case R.id.about_idea_layout:

                Intent adeaIntent=new Intent(AmendAboutActivity.this,AdeaAboutActivity.class);
                startActivity(adeaIntent);

                break;




        }

    }


}
