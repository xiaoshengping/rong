package com.example.administrator.iclub21.bean.talent;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sina.weibo.sdk.demo.R;


/**
 * Created by Administrator on 2015/6/16.
 */
public class MusicActivity extends Activity{

    private SeekBar skbProgress;
    private Player player;
    private EditText file_name_text;
    private TextView tipsView;
    private LinearLayout music_back_ll;
    private int NOSTATE = 0;
    private int PLAY = 1;
    private int PAUSE = 2;
    private int state = 0;

    private String musicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        tipsView=(TextView) this.findViewById(R.id.tips);
        tipsView.setOnClickListener(new ClickEvent());
        music_back_ll = (LinearLayout)this.findViewById(R.id.music_back_ll);
        music_back_ll.setOnClickListener(new ClickEvent());

        skbProgress = (SeekBar) this.findViewById(R.id.skbProgress);
        skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.music_in);
        animation.setFillEnabled(true);
        music_back_ll.startAnimation(animation);

        Bundle bundle = getIntent().getExtras();
        musicName = bundle.getString("musicName");
        String url=bundle.getString("url");//"http://www.iclubapps.com/upload/15041411283761274609.mp3";//"http://192.168.153.50:8080/Hello/Complicated.mp3";//file_name_text.getText().toString();
        player = new Player(url,skbProgress);
        tipsView.setText(musicName);
        TelephonyManager telephonyManager=(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new MyPhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
    }

    /**
     * 只有电话来了之后才暂停音乐的播放
     */
    private final class MyPhoneListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING://电话来了
                    player.callIsComing();
                    break;
                case TelephonyManager.CALL_STATE_IDLE: //通话结束
                    player.callIsDown();
                    break;
            }
        }
    }

    class ClickEvent implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {

            if (arg0 == tipsView) {
                if(state==NOSTATE) {
                    state=PLAY;
                    player.play();

                }else{
                    player.pause();
                }
            }
            if(arg0 == music_back_ll){
                player.stop();

                finish();
                overridePendingTransition(R.anim.out_to_not,R.anim.music_out);
            }

//            if (arg0 == btnPause) {
//                boolean pause=player.pause();
//                if (pause) {
//                    btnPause.setText("继续");
//                    tipsView.setText("音乐暂停播放...");
//                }else{
//                    btnPause.setText("暂停");
//                    tipsView.setText("音乐继续播放...");
//                }
//            } else if (arg0 == btnPlayUrl) {
//                player.play();
//                tipsView.setText("音乐开始播放...");
//            } else if (arg0 == btnStop) {
//                player.stop();
//                tipsView.setText("音乐停止播放...");
//            } else if (arg0==btnReplay) {
//                player.replay();
//                tipsView.setText("音乐重新播放...");
//            }
        }
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * player.mediaPlayer.getDuration()
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            player.stop();
            finish();
            overridePendingTransition(R.anim.out_to_not,R.anim.music_out);

            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

}
