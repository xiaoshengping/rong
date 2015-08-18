package com.example.administrator.iclub21.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.example.administrator.iclub21.fragment.FragmentTabAdapter;
import com.example.administrator.iclub21.fragment.InviteFragment;
import com.example.administrator.iclub21.fragment.MessageFragment;
import com.example.administrator.iclub21.fragment.ReputationValueFragment;
import com.example.administrator.iclub21.fragment.ResumeFragment;
import com.jeremy.Customer.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

public class ResumeActivity extends ActionBarActivity  {
         @ViewInject(R.id.resume_radio_rg)
       private RadioGroup resumeRadioGrop;
    private ArrayList<Fragment> fragments=new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        ViewUtils.inject(this);
        inti();

    }

    private void inti() {
        addFragment();
        FragmentTabAdapter fragmentTabAdapter=new FragmentTabAdapter(this,fragments,R.id.resume_fragment_layout,resumeRadioGrop);



    }

    private void addFragment() {
        fragments.add(new ResumeFragment());
        fragments.add(new InviteFragment());
        fragments.add(new ReputationValueFragment());
        fragments.add(new MessageFragment());


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
