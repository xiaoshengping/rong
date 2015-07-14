package com.example.administrator.iclub21.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.iclub21.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantInformationFragment extends Fragment {

    @ViewInject(R.id.text_tv)
    private TextView textTv;

    public MerchantInformationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_merchant_information, container, false);
        ViewUtils.inject(this,view);
        textTv.setText("消息通知");
        return view;
    }


}
