package com.example.administrator.iclub21.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.administrator.iclub21.bean.ArtistHeadBean;
import com.example.administrator.iclub21.fragment.ArtistHeadFragment;

import java.util.List;

/**
 * Created by Administrator on 2015/5/18.
 */
public class ArtistPagerAdapter  extends FragmentPagerAdapter {
    private List<ArtistHeadBean> data;
    public ArtistPagerAdapter(FragmentManager fm ,List<ArtistHeadBean> data) {
        super(fm);
        this.data=data;
    }

    @Override
    public Fragment getItem(int position) {
        ArtistHeadFragment headFragment=new ArtistHeadFragment();
        Bundle bundle=new Bundle();
        bundle.putString("headData",data.get(position).getPath());
        headFragment.setArguments(bundle);
        return headFragment;
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
