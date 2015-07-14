package com.example.administrator.iclub21.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistHeadFragment extends Fragment {
         @ViewInject(R.id.artist_head_pager)
       private ImageView artistHeadIv;
    public ArtistHeadFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_artist_head, container, false);
        ViewUtils.inject(this,view);
        inti();
        return view;
    }

    private void inti() {
        Bundle bundle=getArguments();
       String headData= bundle.getString("headData");
        BitmapUtils bitmapUtils=new BitmapUtils(getActivity());
        bitmapUtils.display(artistHeadIv, AppUtilsUrl.ImageBaseUrl+headData);

    }


}
