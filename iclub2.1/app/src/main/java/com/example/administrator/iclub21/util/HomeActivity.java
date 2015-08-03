package com.example.administrator.iclub21.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.widget.RadioGroup;

import com.example.administrator.iclub21.fragment.ArtistFragment;
import com.example.administrator.iclub21.fragment.FragmentTabAdapter;
import com.example.administrator.iclub21.fragment.MineFragment;
import com.example.administrator.iclub21.fragment.RecruitmentFragment;
import com.example.administrator.iclub21.fragment.TalentFragment;
import com.jeremy.Customer.R;

import java.util.ArrayList;

public class HomeActivity extends ActionBarActivity {
    private ArrayList<Fragment> fragments=new ArrayList<Fragment>();
    private RadioGroup homeRG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }
    private void init() {
        intiRadioGroup();
        addFragment();
        FragmentTabAdapter fragmentTabAdapter=new FragmentTabAdapter(this,fragments,R.id.home_layout,homeRG);

    }

    private void intiRadioGroup() {
        homeRG= (RadioGroup) findViewById(R.id.iclub_home_rg);

    }

    private void addFragment() {
        fragments.add(new ArtistFragment());
        fragments.add(new TalentFragment());
        fragments.add(new RecruitmentFragment());
        fragments.add(new MineFragment());
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


//    public void selected_position(View v){
//
//        Toast.makeText(this, "Helloddddddddd", Toast.LENGTH_SHORT).show();
//    }


}
