package com.example.administrator.iclub21.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.iclub21.fragment.RoleTalentsFragment;

import java.util.List;

/**
 * Created by xiaoshengping on 2015/6/1.
 */
public class RoleTalentsAdapter extends FragmentPagerAdapter{

    private List<Integer> data;

    public RoleTalentsAdapter(FragmentManager fm,List<Integer> data) {
        super(fm);
        this.data=data;
    }

    @Override
    public Fragment getItem(int position) {
        RoleTalentsFragment roleTalentsFragment=new RoleTalentsFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("iamgeUrl", data.get(position));
        roleTalentsFragment.setArguments(bundle);
        return roleTalentsFragment;
    }

    @Override
    public int getCount() {
        return data.size();
    }



}
