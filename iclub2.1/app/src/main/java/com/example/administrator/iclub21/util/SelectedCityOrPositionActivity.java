package com.example.administrator.iclub21.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.iclub21.adapter.SelectedCityOrPositionAdapter;
import com.example.administrator.iclub21.bean.recruitment.AreaBean;
import com.jeremy.Customer.R;

/**
 * Created by Administrator on 2015/5/27.
 */
public class SelectedCityOrPositionActivity extends Activity {

    //    private ListView selecte_province_lv;
    private ListView selecte_city_lv;
    private LinearLayout selecte_province_ll;
    private TextView title_name_tv;
    //    private LinearLayout selecte_city_ll;
    private SelectedCityOrPositionAdapter scAdaper;
    //    private int selscte = 1 ;//1、省 ；2、城
    private int status;
    private int company=0;
    private AreaBean areaBean = new AreaBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_city_position);
//
        init();
    }

    private String yv="";

    private void init() {

//        selecte_province_ll = (LinearLayout) findViewById(R.id.selecte_province_ll);
        selecte_city_lv = (ListView) findViewById(R.id.selecte_city_lv);
        title_name_tv = (TextView) findViewById(R.id.title_name_tv);



        View header = View.inflate(this, R.layout.null_header, null);//头部内容

        selecte_city_lv.addHeaderView(header);//添加头部


        Bundle bundle = getIntent().getExtras();
        status = bundle.getInt("Status");
        company = bundle.getInt("Company");
        if(company==-1){
            if(status==areaBean.PROVINCE) {
                initProvince(true);
            }else if(status==areaBean.POSITION){
                initPosition(true);
            }

        }else if(status==areaBean.PROVINCE) {
            initProvince();
        }else if(status==areaBean.POSITION){
            initPosition();
        }
        //item点击事件
        selecte_city_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    if (status == areaBean.PROVINCE) {
                        if (areaBean.getsCityCount(areaBean.getProvince(position - 1, 2)) != 1) {
                            yv = areaBean.getProvince(position-1,1);
                            status = areaBean.CITY;
                            initCity(areaBean.getProvince(position - 1, 2));
                            if(company==0){

                            }
                        } else {
                            Intent intent = new Intent();
                            intent.putExtra("City", areaBean.getCityNum(0));
                            intent.putExtra("Position", -1);
                            intent.putExtra("CityName", areaBean.getCityName(0));
                        /*给上一个Activity返回结果*/
                            SelectedCityOrPositionActivity.this.setResult(12, intent);
                        /*结束本Activity*/
                            SelectedCityOrPositionActivity.this.finish();
                        }
                    } else if (status == areaBean.CITY) {
//                        Toast.makeText(SelectedCityOrPositionActivity.this, areaBean.getNumCityName(SelectedCityOrPositionActivity.this,391), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.putExtra("City", areaBean.getCityNum(position - 1));
                        intent.putExtra("Position", -1);
                        intent.putExtra("CityName", areaBean.getCityName(position - 1));
                        intent.putExtra("PROVINCE",yv);
                        /*给上一个Activity返回结果*/
                        SelectedCityOrPositionActivity.this.setResult(12, intent);
//
                        /*结束本Activity*/
                        SelectedCityOrPositionActivity.this.finish();
                    } else if (status == areaBean.POSITION) {
                        if (areaBean.getPositionNum(position - 1) != -1) {
//                        Toast.makeText(SelectedCityOrPositionActivity.this, areaBean.getNumPositionName(SelectedCityOrPositionActivity.this, areaBean.getPositionNum(position - 1)), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            intent.putExtra("City", -1);
                            intent.putExtra("Position", areaBean.getPositionNum(position - 1));
                            intent.putExtra("PositionName", areaBean.getPosition(position - 1));
                /*给上一个Activity返回结果*/
                            SelectedCityOrPositionActivity.this.setResult(12, intent);
/*结束本Activity*/
                            SelectedCityOrPositionActivity.this.finish();
                        }
                    }

//                selecte_city_ll.setVisibility(View.VISIBLE);

//                selecte_city_lv.setVisibility(View.GONE);
//                Animation animation = AnimationUtils.loadAnimation(SelectedCityOrPositionActivity.this, R.anim.ou);
//                animation.setFillAfter(true);
//                selecte_province_ll.startAnimation(animation);
//                selecte_city_lv.startAnimation(animation);//控件动画
                }
            }
        });

    }

    //初始化职位
    private  void initPosition(){
        title_name_tv.setText("选择职位");
        status=areaBean.POSITION;

        scAdaper = new SelectedCityOrPositionAdapter(this,status);
        selecte_city_lv.setAdapter(scAdaper);
        scAdaper.notifyDataSetChanged();

    }
    //初始化职位
    private  void initPosition(boolean b){
        title_name_tv.setText("选择职位");
        status=areaBean.POSITION;

        scAdaper = new SelectedCityOrPositionAdapter(this,status,b);
        selecte_city_lv.setAdapter(scAdaper);
        scAdaper.notifyDataSetChanged();

    }
    //初始化省份
    private void initProvince() {
        title_name_tv.setText("选择省份");
        status=areaBean.PROVINCE;
//        selscte = 1;
        scAdaper = new SelectedCityOrPositionAdapter(this,status);
        selecte_city_lv.setAdapter(scAdaper);
        scAdaper.notifyDataSetChanged();


    }
    //初始化省份
    private void initProvince(boolean b) {
        title_name_tv.setText("选择省份");
        status=areaBean.PROVINCE;
//        selscte = 1;
        scAdaper = new SelectedCityOrPositionAdapter(this,status,b);
        selecte_city_lv.setAdapter(scAdaper);
        scAdaper.notifyDataSetChanged();


    }

    //初始化城市
    private void initCity(String str) {
        title_name_tv.setText("选择城市");
        status=areaBean.CITY;
//        selscte = 2;
        scAdaper = new SelectedCityOrPositionAdapter(this,status,str);
        selecte_city_lv.setAdapter(scAdaper);
        scAdaper.notifyDataSetChanged();

//        selecte_city_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Toast.makeText(SelectedCityOrPositionActivity.this,areaBean.getCityNum(position)+"" , Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent();
//                intent.putExtra("Name", areaBean.getCityNum(position));
//                /*给上一个Activity返回结果*/
//                SelectedCityOrPositionActivity.this.setResult(RESULT_OK, intent);
///*结束本Activity*/
//                SelectedCityOrPositionActivity.this.finish();
//            }
//        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if(status == areaBean.PROVINCE) {
                Intent intent = new Intent();
                intent.putExtra("City", -1);
                intent.putExtra("Position", -1);
                SelectedCityOrPositionActivity.this.setResult(12, intent);
                SelectedCityOrPositionActivity.this.finish();
                overridePendingTransition(R.anim.in_from_buttom, R.anim.out_to_buttom);
            }else if(status == areaBean.CITY){
//                Animation animation = AnimationUtils.loadAnimation(SelectedCityOrPositionActivity.this, R.anim.out_from_right);
//                animation.setFillAfter(true);
//                selecte_city_ll.startAnimation(animation);//控件动画
//                selecte_province_lv.setVisibility(View.VISIBLE);
                if(company==-1) {
                    initProvince(true);
                }else {
                    initProvince();
                }
            }else if(status == areaBean.POSITION){
                Intent intent = new Intent();
                intent.putExtra("City", -1);
                intent.putExtra("Position", -1);
                SelectedCityOrPositionActivity.this.setResult(12, intent);
                SelectedCityOrPositionActivity.this.finish();
                overridePendingTransition(R.anim.in_from_buttom, R.anim.out_to_right);
            }
//
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

    public void back(View v){

        if(status == areaBean.PROVINCE) {
            Intent intent = new Intent();
            intent.putExtra("City", -1);
            intent.putExtra("Position", -1);
            SelectedCityOrPositionActivity.this.setResult(12, intent);
            SelectedCityOrPositionActivity.this.finish();
            overridePendingTransition(R.anim.in_from_buttom, R.anim.out_to_buttom);
        }else if(status == areaBean.CITY){
//                Animation animation = AnimationUtils.loadAnimation(SelectedCityOrPositionActivity.this, R.anim.out_from_right);
//                animation.setFillAfter(true);
//                selecte_city_ll.startAnimation(animation);//控件动画
//                selecte_province_lv.setVisibility(View.VISIBLE);
            if(company==-1) {
                initProvince(true);
            }else {
                initProvince();
            }
        }else if(status == areaBean.POSITION){
            Intent intent = new Intent();
            intent.putExtra("City", -1);
            intent.putExtra("Position", -1);
            SelectedCityOrPositionActivity.this.setResult(12, intent);
            SelectedCityOrPositionActivity.this.finish();
            overridePendingTransition(R.anim.in_from_buttom, R.anim.out_to_right);
        }

    }

}
