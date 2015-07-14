package com.example.administrator.iclub21.bean.talent;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Gallery;

import com.example.administrator.iclub21.R;

import java.util.List;

/**
 * Created by Administrator on 2015/6/12.
 */
public class SpaceImageDetailActivity extends Activity {
    private Gallery spaceimge_g;
    private SpaceImageDetailAdater siAdater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spaceimage_detail);
        inti();

    }
    private void binding(){
        spaceimge_g = (Gallery) findViewById(R.id.spaceimge_g);
    }
    private void inti(){
        binding();
        Bundle bundle=this.getIntent().getExtras();
        List<ResumePicture> list = (List)bundle.getParcelableArrayList("list");
        int num = bundle.getInt("num");
        int maxNum = bundle.getInt("MaxNum");
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        siAdater = new SpaceImageDetailAdater(this,list,width,height,maxNum);
        spaceimge_g.setAdapter(siAdater);
        siAdater.notifyDataSetChanged();
        spaceimge_g.setSelection(num);
    }


}
