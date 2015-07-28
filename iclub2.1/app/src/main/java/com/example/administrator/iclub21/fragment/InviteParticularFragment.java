package com.example.administrator.iclub21.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sina.weibo.sdk.demo.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InviteParticularFragment extends Fragment {

    @ViewInject(R.id.merchant_invite_radio_rg)
    private RadioGroup inviteRadioGrop;
    @ViewInject(R.id.nvite_radio_bt)
    private RadioButton adoptRadioButton;
    private ArrayList<Fragment> fragments=new ArrayList<Fragment>();
    public InviteParticularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_invite_particular, container, false);
        ViewUtils.inject(this, view);
        inti();

        return view;
    }

    private void inti() {
        addFragment();
        FragmentTabAdapter fragmentTabAdapter=new FragmentTabAdapter(getActivity(),fragments,R.id.merchant_invite_fragment_layout,inviteRadioGrop);




    }

    private void addFragment() {
        fragments.add(new MerchantInviteMessageFragment());
        fragments.add(new MerchantAcceptInviteFragment());
        fragments.add(new MerchantSuccessfulInviteFragment());

    }
}
