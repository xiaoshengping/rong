package com.example.administrator.iclub21.util;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.example.administrator.iclub21.adapter.RoleTalentsAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sina.weibo.sdk.demo.R;

import java.util.ArrayList;
import java.util.List;

public class RoleTalentsActivity extends ActionBarActivity {
    @ViewInject(R.id.talents_pager)
    private ViewPager talentsPager;
    List<Integer> data=new ArrayList<Integer>();
    public int[] imageString={R.mipmap.talents_icon_a,R.mipmap.talents_icon_b,R.mipmap.talents_icon_c,R.mipmap.talents_icon_d};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_talents);
        ViewUtils.inject(this);
        inti();
    }

    private void inti() {
        intiPager();


    }

    private void intiPager() {
        for (int i = 0; i <4 ; i++) {
            data.add(imageString[i]);
        }

        RoleTalentsAdapter roleTalentsAdapter=new RoleTalentsAdapter(getSupportFragmentManager(),data);
        talentsPager.setAdapter(roleTalentsAdapter);
        roleTalentsAdapter.notifyDataSetChanged();


    }
}
