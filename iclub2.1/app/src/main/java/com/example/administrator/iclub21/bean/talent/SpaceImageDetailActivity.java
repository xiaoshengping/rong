package com.example.administrator.iclub21.bean.talent;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Gallery;

import com.jeremy.Customer.R;

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

        spaceimge_g.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finish();
                overridePendingTransition(R.anim.out_to_not, R.anim.spaceimagedetail_out);
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.out_to_not, R.anim.spaceimagedetail_out);
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }


}
