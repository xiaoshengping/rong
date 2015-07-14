package com.example.administrator.iclub21.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.iclub21.R;
import com.example.administrator.iclub21.bean.recruitment.JobDetailsDialog;
import com.example.administrator.iclub21.bean.talent.MusicActivity;
import com.example.administrator.iclub21.bean.talent.Player;
import com.example.administrator.iclub21.bean.talent.ResumeMovie;
import com.example.administrator.iclub21.bean.talent.ResumeMusic;
import com.example.administrator.iclub21.url.AppUtilsUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/27.
 */
public class VideoDisplyAdapter extends BaseAdapter{

    private Context mContext;
    private ViewHodle viewHodle = new ViewHodle();
    private ViewMusic viewMusic = new ViewMusic();
    private LayoutInflater mInflater;
    private List<ResumeMovie> data;
    private List<ResumeMusic> musicsData;
    private List<ResumeMovie> myData;
    private List<ResumeMusic> myMusicsData;
    private AppUtilsUrl auu = new AppUtilsUrl();
    private String movieDirection = "∧";
    private String musicDirection = "∧";


    public VideoDisplyAdapter(List<ResumeMovie> data, List<ResumeMusic> musicsData, Context context) {
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
        this.data = data;
        this.musicsData = musicsData;
        this.myData = data;
        this.myMusicsData = musicsData;
    }

    public void hideShowTool(String part , boolean movie, boolean music){
        if(part.equals("Movie")){
            if(movie){
                this.data = myData;
                movieDirection = mContext.getString(R.string.up_arrow);
            }else {
                this.data = new ArrayList<ResumeMovie>();
                movieDirection = mContext.getString(R.string.down_arrow);
            }
        }else {
            if(music){
                this.musicsData = myMusicsData;
                musicDirection = mContext.getString(R.string.up_arrow);;
            }else {
                this.musicsData = new ArrayList<ResumeMusic>();
                musicDirection = mContext.getString(R.string.down_arrow);
            }
        }
    }


    @Override
    public int getCount() {
        return data.size()+musicsData.size()+2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //视频
        if (position <= data.size()) {
//            if (convertView == null) {
            convertView = mInflater.inflate(R.layout.video_display_list_layout, null);
//                viewHodle = new ViewHodle();

            viewHodle.btn_play_ib = (ImageButton) convertView.findViewById(R.id.btn_play_ib);
            viewHodle.video_rl = (RelativeLayout) convertView.findViewById(R.id.video_rl);
            viewHodle.video_title_ll = (LinearLayout) convertView.findViewById(R.id.video_title_ll);
            viewHodle.movie_direction_tv = (TextView)convertView.findViewById(R.id.movie_direction_tv);


                convertView.setTag(viewHodle);
//            } else {
//                viewHodle = (ViewHodle) convertView.getTag();
//            }
            if (position == 0) {
                viewHodle.video_rl.setVisibility(View.GONE);
                viewHodle.video_title_ll.setVisibility(View.VISIBLE);
                viewHodle.movie_direction_tv.setText(movieDirection);
            }else if (position <= data.size()) {
                viewHodle.video_rl.setVisibility(View.VISIBLE);
                viewHodle.video_title_ll.setVisibility(View.GONE);

                viewHodle.btn_play_ib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(auu.ImageBaseUrl+data.get(position-1).getPath()), "video/mp4");
                        mContext.startActivity(intent);
//                        String url = auu.ImageBaseUrl+data.get(position-1).getPath();
//                        Uri uri = Uri.parse(url);
//                        Intent m_intent = new Intent(Intent.ACTION_VIEW, uri);
//                        mContext.startActivity(m_intent);

                    }
                });

            }
        } else {
            convertView = mInflater.inflate(R.layout.music_list_layout, null);
            viewMusic.music_title_ll = (LinearLayout) convertView.findViewById(R.id.music_title_ll);
            viewMusic.music_ll = (LinearLayout) convertView.findViewById(R.id.music_ll);
            viewMusic.tipsView = (TextView) convertView.findViewById(R.id.tips);
            viewMusic.music_direction_tv = (TextView)convertView.findViewById(R.id.music_direction_tv);
            if (position == data.size() + 1) {
                viewMusic.music_title_ll.setVisibility(View.VISIBLE);
                viewMusic.music_ll.setVisibility(View.GONE);
                viewMusic.music_direction_tv.setText(musicDirection);
            } else {
                viewMusic.music_title_ll.setVisibility(View.GONE);
                viewMusic.music_ll.setVisibility(View.VISIBLE);

                viewMusic.tipsView.setText("歌曲"+musicsData.get(position - data.size() - 2).getTitle());

                viewMusic.tipsView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, MusicActivity.class);  //方法1
                        intent.putExtra("url",auu.ImageBaseUrl + musicsData.get(position - data.size() - 2).getPath());
                        intent.putExtra("musicName",musicsData.get(position - data.size() - 2).getTitle());
                        intent.putExtras(intent);
                        mContext.startActivity(intent);
//                        TalendDetailsActivity.overridePendingTransition(R.anim.music_in, R.anim.out_to_right);
//                        dialog(auu.ImageBaseUrl + musicsData.get(position - data.size() - 2).getPath());
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setDataAndType(Uri.parse(auu.ImageBaseUrl + musicsData.get(position - data.size() - 2).getPath()), "audio/MP3");
//                        mContext.startActivity(intent);
                    }
                });

//                String url = "http://www.iclubapps.com/upload/15041411283761274609.mp3";//"http://192.168.153.50:8080/Hello/Complicated.mp3";//file_name_text.getText().toString();

//                TelephonyManager telephonyManager=(TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
//                telephonyManager.listen(new MyPhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);

            }
        }

        //音乐
//            if (position==data.size()+1) {
//                convertView = mInflater.inflate(R.layout.music_list_layout, null);
//                viewMusic = new ViewMusic();

//                convertView.setTag(viewMusic);
//            } else if(position>data.size()+1){
//                viewMusic = (ViewMusic) convertView.getTag();
//            }

//        }

        return convertView;
    }

    private Player player;

    private JobDetailsDialog musicDialog;// = new JobDetailsDialog(mContext);

//    private void dialog(String url) {
//
////        WindowManager wm = mContext.getWindowManager();
////        int width = wm.getDefaultDisplay().getWidth();
//
//        musicDialog = new JobDetailsDialog(mContext);
//        musicDialog.getSkbProgress().setOnSeekBarChangeListener(new SeekBarChangeEvent());
//        player = new Player(url,musicDialog.getSkbProgress());
////        TelephonyManager telephonyManager=(TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
////        telephonyManager.listen(new MyPhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
////        Window window = musicDialog.getWindow();
////        window.setGravity(Gravity.TOP);
//        musicDialog.setTipsView(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                player.play();
////                tipsView.setText("音乐开始播放...");
//            }
//        });
//
//        musicDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                return true;
//            }
//        });
//
//        musicDialog.show();
//    }
//    @Override
//    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
////        if(keyCode==KeyEvent.KEYCODE_BACK){
//            player.play();
////            viewMusic.music_title_ll.setVisibility(View.VISIBLE);
////            notifyDataSetChanged();
////        }
//        return true;
//    }

//    /**
//     * 只有电话来了之后才暂停音乐的播放
//     */
//    private final class MyPhoneListener extends android.telephony.PhoneStateListener{
//        @Override
//        public void onCallStateChanged(int state, String incomingNumber) {
//            switch (state) {
//                case TelephonyManager.CALL_STATE_RINGING://电话来了
//                    player.callIsComing();
//                    break;
//                case TelephonyManager.CALL_STATE_IDLE: //通话结束
//                    player.callIsDown();
//                    break;
//            }
//        }
//    }

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





        public class ViewHodle {
            private ImageButton btn_play_ib;
            private RelativeLayout video_rl;
            private LinearLayout video_title_ll;
            private TextView movie_direction_tv;

        }
    public class ViewMusic {
        private LinearLayout music_ll;
        private LinearLayout music_title_ll;
        private TextView tipsView;
        private TextView music_direction_tv;
    }
    }


