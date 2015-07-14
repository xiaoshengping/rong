package com.example.administrator.iclub21.util;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.adapter.RoleTalentsAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class MerchantJionActivity extends ActionBarActivity {
    @ViewInject(R.id.merchant_jion_pager)
    private ViewPager merchantJionPager;

    List<Integer> data=new ArrayList<Integer>();
    public int[] imageString={R.mipmap.merchantion_icon_a,R.mipmap.merchantion_icon_b,R.mipmap.merchantion_icon_c,R.mipmap.merchantion_icon_d};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_jion);
        ViewUtils.inject(this);
        initMerchantion();
    }

    private void initMerchantion() {
        intiPager();



    }


    private void intiPager() {
        for (int i = 0; i <4 ; i++) {
            data.add(imageString[i]);
        }

        RoleTalentsAdapter roleTalentsAdapter=new RoleTalentsAdapter(getSupportFragmentManager(),data);
        merchantJionPager.setAdapter(roleTalentsAdapter);
        roleTalentsAdapter.notifyDataSetChanged();


    }
}
