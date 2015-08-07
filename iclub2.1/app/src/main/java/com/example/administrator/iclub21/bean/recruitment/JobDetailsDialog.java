package com.example.administrator.iclub21.bean.recruitment;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.iclub21.calendar.CalendarActivity;
import com.jeremy.Customer.R;


/**
 * Created by Administrator on 2015/6/4.
 */
public class JobDetailsDialog extends Dialog {
    private TextView tips_tv;
    private LinearLayout register_or_cancel;
    private TextView register;
    private TextView cancel;
    private EditText search_import_et;
    private TextView searchbox_seek_tv;

    public int PASTDUE = 1;//已过期
    public int OFFER = 2;//邀约
    public int BEDEFEATED = 3;//加载失败
    public int ING = 4;//加载中
    public int SUCCEED = 5;//邀约成功
    public int FAILURE = 6;//邀约失败
    public int NULL = 7;//无公司名
//    private TextView eliminate_tv;
//    private ImageButton seek_ib;

//    private SeekBar skbProgress;
//    private TextView tipsView;


    public JobDetailsDialog(Context context,boolean register,boolean send) {
        super(context, R.style.JobDetailsDialog);
        setCustomDialog(register,send);
    }

    public JobDetailsDialog(Context context,int tipsType) {
        super(context, R.style.JobDetailsDialog);
        setCalendarDialog(tipsType);
    }

    public JobDetailsDialog(Context context,int w , int h){
        super(context, R.style.SearchBoxDialog);
        setSearchBox(context, w, h);
    }

    private CalendarActivity cc;

    //招聘投递
    private void setCalendarDialog(int tipsType) {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.calendar_dialog, null);
        tips_tv = (TextView) mView.findViewById(R.id.tips_tv);
//        register_or_cancel = (LinearLayout) mView.findViewById(R.id.register_or_cancel);
        register = (TextView) mView.findViewById(R.id.register);
        cancel = (TextView) mView.findViewById(R.id.cancel);
        if(tipsType == PASTDUE){
            tips_tv.setText("该日期已过时");
        }else if(tipsType == BEDEFEATED){
            tips_tv.setText("网络异常请稍后重试！");
        }else if(tipsType == ING){
            tips_tv.setText("正在加载");
        }else if(tipsType == OFFER){
            tips_tv.setText("邀约日期为："+cc.i);
            cancel.setVisibility(View.VISIBLE);
        }else if(tipsType == SUCCEED){
            tips_tv.setText("邀约详细信息已用短信方式发送到你手机号码中！");
            cancel.setVisibility(View.INVISIBLE);
        }else if(tipsType == FAILURE){
            tips_tv.setText("网络异常请稍后重试！");
            cancel.setVisibility(View.INVISIBLE);
        }else if(tipsType == NULL){
            tips_tv.setText("您的公司信息尚未填写或未填写完整，将无法邀约人才。");
            cancel.setVisibility(View.INVISIBLE);
        }
        super.setContentView(mView);
    }


//    public JobDetailsDialog(Context context){
//        super(context, R.style.MusicDialog);
//        setOwnerActivity((Activity) context);
//        setMusic(context);
////        setSearchBox(context);
//    }
//
//    //音乐播放器
//    private void setMusic(Context context){
//        View mView = LayoutInflater.from(getContext()).inflate(R.layout.music_dialog, null);
//        tipsView=(TextView) mView.findViewById(R.id.tips);
////        tipsView.setOnClickListener(new ClickEvent());
//
//        skbProgress = (SeekBar) mView.findViewById(R.id.skbProgress);
////        skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        super.setContentView(mView);
//        getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
//    }
//    public SeekBar getSkbProgress(){
//        return skbProgress;
//    }

    //搜索框
    private void setSearchBox(Context context,int w,int h){
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.searchbox_dialog, null);
        LinearLayout search_ll = (LinearLayout) mView.findViewById(R.id.searchbox_back_ll);
        search_import_et = (EditText) mView.findViewById(R.id.search_import_et);
        searchbox_seek_tv = (TextView) mView.findViewById(R.id.searchbox_seek_tv);
//        eliminate_tv = (TextView) mView.findViewById(R.id.eliminate_tv);
//        seek_ib = (ImageButton) mView.findViewById(R.id.seek_ib);

        search_ll.setLayoutParams(new LinearLayout.LayoutParams(w,h));
//        eliminate_tv.requestFocus();

//        eliminate_tv.setFocusable(true);

        super.setContentView(mView);
    }

    public EditText getEt(){
        return search_import_et;
    }
    //招聘投递
    private void setCustomDialog(boolean registers,boolean send) {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.jobdetails_dialog, null);
        tips_tv = (TextView) mView.findViewById(R.id.tips_tv);
        register_or_cancel = (LinearLayout) mView.findViewById(R.id.register_or_cancel);
        register = (TextView) mView.findViewById(R.id.register);
        cancel = (TextView) mView.findViewById(R.id.cancel);
        if(registers){
            register_or_cancel.setVisibility(View.INVISIBLE);
            tips_tv.setVisibility(View.VISIBLE);
            if(send){
                tips_tv.setText("恭喜你，投递成功");
            }else {
                tips_tv.setText("投递失败，请稍后重试");
            }
        }else {
            register_or_cancel.setVisibility(View.VISIBLE);
            tips_tv.setVisibility(View.INVISIBLE);
        }
        super.setContentView(mView);
    }

//    public View getEditText(){
//        return editText;
//    }

    @Override
    public void setContentView(int layoutResID) {
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {
    }

    /**
     * 确定键监听器
     * @param listener
     */
    public void setOnPositiveListener(View.OnClickListener listener){
        register.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setOnNegativeListener(View.OnClickListener listener){
        cancel.setOnClickListener(listener);
    }

    /**
     * 确定键监听器
     * @param listener
     */
    public void setSeek_ib(View.OnClickListener listener){

//        seek_ib.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setEliminate_tv(View.OnClickListener listener){
        searchbox_seek_tv.setOnClickListener(listener);
    }

//    public void setTipsView(View.OnClickListener listener){
//        tipsView.setOnClickListener(listener);
//    }

//    @Override
//    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//        boolean click=false;
//        if(keyCode==KeyEvent.KEYCODE_BACK){
//            click=true;
//            Log.e("keymsg", "back key");
//
//        }
//        return click;
//    }


//    public class DialogOnKeyListener implements OnKeyListener {
//
//        @Override
//        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//            if (keyCode == KeyEvent.KEYCODE_BACK&& event.getRepeatCount() == 0) {
////                if (!dangTopWeb.canGoBack() && INIT_FLAG ==0){
////
////                    DangTopActivity.this.finish();
////
////                }else {
////                    DangTopActivity.this.dialog.hide();
////                    dangTopWeb.stopLoading();
////                }
//
//
//            }
//            return false;
//        }
//
//    }

}

