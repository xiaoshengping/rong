package com.example.administrator.iclub21.bean.talent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.administrator.iclub21.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/15.
 */
public class PicturesshowMoreActivity extends Activity {

//    private ListView prcturesshowmore_lv;
    private GridView gridview;
    private PicturesshowMoreAdater pmAdater;
    private List<ResumePicture> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picturesshowmore);
        init();
    }
    private void binding(){
//        prcturesshowmore_lv = (ListView) findViewById(R.id.prcturesshowmore_lv);
        gridview = (GridView)findViewById(R.id.gridview);
    }
    private void init(){
        binding();

        Bundle bundle=this.getIntent().getExtras();
        list = (List)bundle.getParcelableArrayList("list");

        pmAdater = new PicturesshowMoreAdater(this,list);
        gridview.setAdapter(pmAdater);
        pmAdater.notifyDataSetChanged();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PicturesshowMoreActivity.this, SpaceImageDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("list", (ArrayList)list);
                bundle.putInt("num", position);
                bundle.putInt("MaxNum",list.size());
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.spaceimagedetail_in,R.anim.out_to_not);
            }
        });
    }

    public void back(View v){

        finish();
//        video_display_lv.setVisibility(View.VISIBLE);

    }
}
