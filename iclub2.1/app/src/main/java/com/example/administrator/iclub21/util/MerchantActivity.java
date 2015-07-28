package com.example.administrator.iclub21.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.iclub21.fragment.CompanyMessageFragment;
import com.example.administrator.iclub21.fragment.FragmentTabAdapter;
import com.example.administrator.iclub21.fragment.InviteParticularFragment;
import com.example.administrator.iclub21.fragment.MerchantInformationFragment;
import com.example.administrator.iclub21.fragment.RecruitmentHistoryFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sina.weibo.sdk.demo.R;

import java.util.ArrayList;

public class MerchantActivity extends ActionBarActivity {
    @ViewInject(R.id.merchant_radio_rg)
    private RadioGroup merchantRadioGrop;
    @ViewInject(R.id.huste_radio_button)
    private RadioButton husteRadioButton;
    private ArrayList<Fragment> fragment=new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        ViewUtils.inject(this);
        inti();
    }
    private void inti() {
        husteRadioButton.setChecked(true);
        addFragment();
        FragmentTabAdapter fragmentTabAdapter=new FragmentTabAdapter(this,fragment,R.id.merchant_fragment_layout,merchantRadioGrop);



    }

    private void addFragment() {
        fragment.add(new RecruitmentHistoryFragment());
        fragment.add(new CompanyMessageFragment());
        fragment.add(new InviteParticularFragment());
        fragment.add(new MerchantInformationFragment());


    }


}
