package com.example.administrator.iclub21.bean.artist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeremy.Customer.R;


/**
 * Created by Administrator on 2015/6/16.
 */
public class ArtistConditionSelectActivity extends Activity implements View.OnClickListener {

    private LinearLayout select_area_ll,select_sex_ll,select_tupe_ll,artist_condition_select_ll;
    private Button select_hinterland_b,select_macao_b,select_rest_b,select_man_b,select_girl_b,select_singer_b,select_actor_b;
    private Button artist_area_tv,artist_sex_tv,artist_tupe_tv;
    private Button select_area_all_b,select_sex_all_b,select_tupe_all_b;
    private TextView move_height;


    private int select = 0;
    private int moveHeight = 0;
    private int AREA = 1;
    private int SEX = 2;
    private int TUPE = 3;

    private String HINTERLAND = "area=0&";
    private String MACAO = "area=1&";
    private String REST = "area=2&";
    private String MAN = "sex=0&";
    private String GIRL = "sex=1&";
    private String SINGER = "artistCategory=0&";
    private String ACTOR = "artistCategory=1&";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_condition_select);
        Bundle bundle = getIntent().getExtras();
        select = bundle.getInt("Screen");
        moveHeight = bundle.getInt("MoveHeight");
        inti(select);
    }
    private void binding(){

        move_height = (TextView)findViewById(R.id.move_height);

        select_area_all_b = (Button)findViewById(R.id.select_area_all_b);
        select_sex_all_b = (Button)findViewById(R.id.select_sex_all_b);
        select_tupe_all_b = (Button)findViewById(R.id.select_tupe_all_b);

        artist_area_tv = (Button)findViewById(R.id.artist_area_tv);
        artist_sex_tv = (Button)findViewById(R.id.artist_sex_tv);
        artist_tupe_tv = (Button)findViewById(R.id.artist_tupe_tv);

        select_area_ll = (LinearLayout) findViewById(R.id.select_area_ll);
        select_sex_ll = (LinearLayout) findViewById(R.id.select_sex_ll);
        select_tupe_ll = (LinearLayout) findViewById(R.id.select_tupe_ll);
        artist_condition_select_ll = (LinearLayout) findViewById(R.id.artist_condition_select_ll);

        select_hinterland_b = (Button) findViewById(R.id.select_hinterland_b);
        select_macao_b = (Button) findViewById(R.id.select_macao_b);
        select_rest_b = (Button) findViewById(R.id.select_rest_b);
        select_man_b = (Button) findViewById(R.id.select_man_b);
        select_girl_b = (Button) findViewById(R.id.select_girl_b);
        select_singer_b = (Button) findViewById(R.id.select_singer_b);
        select_actor_b = (Button) findViewById(R.id.select_actor_b);

        select_hinterland_b.setOnClickListener(this);
        select_macao_b.setOnClickListener(this);
        select_rest_b.setOnClickListener(this);
        select_man_b.setOnClickListener(this);
        select_girl_b.setOnClickListener(this);
        select_singer_b.setOnClickListener(this);
        select_actor_b.setOnClickListener(this);
        select_area_all_b.setOnClickListener(this);
        select_sex_all_b.setOnClickListener(this);
        select_tupe_all_b.setOnClickListener(this);

        artist_area_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inti(AREA);
            }
        });
        artist_sex_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inti(SEX);
            }
        });
        artist_tupe_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inti(TUPE);
            }
        });

        artist_condition_select_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("Area", "no");
                intent.putExtra("Sex", "");
                intent.putExtra("Tupe", "");
                intent.putExtra("AreaName", "区域");
                intent.putExtra("SexName", "性别");
                intent.putExtra("TupeName", "类型");

                ArtistConditionSelectActivity.this.setResult(RESULT_OK, intent);
                ArtistConditionSelectActivity.this.finish();
                overridePendingTransition(R.anim.out_to_not,R.anim.music_out);
            }
        });


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void inti(int str){
        binding();

//        move_height.setText(dip2px(235) + "/" + moveHeight);

        move_height.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (dip2px(235) - moveHeight)));

        select_area_ll.setVisibility(View.INVISIBLE);
        select_sex_ll.setVisibility(View.INVISIBLE);
        select_tupe_ll.setVisibility(View.INVISIBLE);


        switch(str){
            case 1:
                select_area_ll.setVisibility(View.VISIBLE);
                break;

            case 2:
                select_sex_ll.setVisibility(View.VISIBLE);
                break;

            case 3:
                select_tupe_ll.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            finish();
            Intent intent = new Intent();
            intent.putExtra("Area", "no");
            intent.putExtra("Sex", "");
            intent.putExtra("Tupe", "");
            intent.putExtra("AreaName", "区域");
            intent.putExtra("SexName", "性别");
            intent.putExtra("TupeName", "类型");

            ArtistConditionSelectActivity.this.setResult(RESULT_OK, intent);
            ArtistConditionSelectActivity.this.finish();
            overridePendingTransition(R.anim.out_to_not,R.anim.music_out);

            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        String area = "",sex = "",tupe = "";
        String areaName = "区域",sexName = "性别",tupeName = "类型";

        switch (v.getId()){
            case R.id.select_area_all_b:
                area ="";
                areaName = "区域";
                break;
            case R.id.select_sex_all_b:
                sex ="";
                sexName = "性别";
                break;
            case R.id.select_tupe_all_b:
                tupe ="";
                tupeName = "类型";
                break;
            case R.id.select_hinterland_b:
                area = HINTERLAND;
                areaName = "中国大陆";
                break;
            case R.id.select_macao_b:
                area = MACAO;
                areaName = "港澳台";
                break;
            case R.id.select_rest_b:
                area = REST;
                areaName = "其他";
                break;
            case R.id.select_man_b:
                sex = MAN;
                sexName = "男";
                break;
            case R.id.select_girl_b:
                sex = GIRL;
                sexName = "女";
                break;
            case R.id.select_singer_b:
                tupe = SINGER;
                tupeName = "歌手";
                break;
            case R.id.select_actor_b:
                tupe = ACTOR;
                tupeName = "演员";
                break;

//            case R.id.artist_area_tv:
//                inti(AREA);
//                break;
//            case R.id.artist_sex_tv:
//                inti(SEX);
//                break;
//            case R.id.artist_tupe_tv:
//                inti(TUPE);
//                break;
            default:
                break;

        }
        Intent intent = new Intent();
        intent.putExtra("Area", area);
        intent.putExtra("Sex", sex);
        intent.putExtra("Tupe", tupe);
        intent.putExtra("AreaName", areaName);
        intent.putExtra("SexName", sexName);
        intent.putExtra("TupeName", tupeName);
                        /*给上一个Activity返回结果*/
        ArtistConditionSelectActivity.this.setResult(RESULT_OK, intent);
                        /*结束本Activity*/
        ArtistConditionSelectActivity.this.finish();
        overridePendingTransition(R.anim.out_to_not, R.anim.music_out);

    }

    public int dip2px(float dipValue) {
        final float scale = ArtistConditionSelectActivity.this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
