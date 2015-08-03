package com.example.administrator.iclub21.bean.artist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.administrator.iclub21.adapter.ArtistListAdapter;
import com.example.administrator.iclub21.url.AppUtilsUrl;
import com.example.administrator.iclub21.util.ArtistDetailActivity;
import com.jeremy.Customer.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/17.
 */
public class ArtistSeekActivity extends Activity{

    private GridView artistGridView;
    private TextView artist_seek_tv;
    private EditText artist_seek_et;
    private ArtistListAdapter adapter;
    private ProgressBar progressbar;

    private ArtistParme<ArtistListBean> artistParme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_seek);
        inti();
    }

    private void binding(){
        artistGridView = (GridView) findViewById(R.id.artist_list_gridView);
        artist_seek_tv = (TextView) findViewById(R.id.artist_seek_tv);
        artist_seek_et = (EditText) findViewById(R.id.artist_seek_et);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
    }

    private void inti(){
        binding();

        artist_seek_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_UNSPECIFIED) {
//                    artist_seek_et.getText()
                    if(artist_seek_et.getText().toString().equals("")){
                        Toast.makeText(ArtistSeekActivity.this, "请输入搜索内容",Toast.LENGTH_SHORT).show();
                    }else {
                        progressbar.setVisibility(View.VISIBLE);
                        update(ArtistSeekActivity.this, artist_seek_et.getText().toString());
//                    Toast.makeText(ArtistSeekActivity.this, "你点了软键盘回车按钮",
//                            Toast.LENGTH_SHORT).show();
                    }
                }
                return (arg2.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });

        artist_seek_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                artist_seek_tv.setText(artist_seek_et.getText().toString());
            }
        });
    }


    private void update(Context context,String abc){
        UpdateTextTask updateTextTask = new UpdateTextTask(context,abc);
        updateTextTask.execute();
    }


    class UpdateTextTask extends AsyncTask<Void,Integer,Integer> {
        private Context context;
        private int city;
        private int job;
        private String abc;
        UpdateTextTask(Context context,String abc) {
            this.context = context;
            this.abc = abc;
        }

        /**
         * 运行在UI线程中，在调用doInBackground()之前执行
         */
        @Override
        protected void onPreExecute() {
//            Toast.makeText(context,"开始执行",Toast.LENGTH_SHORT).show();
        }
        /**
         * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
         */
        @Override
        protected Integer doInBackground(Void... params) {
            int i=0;
            while(i<10){
                i++;
                publishProgress(i);
                initListData(abc);

            }
            return null;
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(Integer integer) {
            if ("success".equals(artistParme.getState())) {
                progressbar.setVisibility(View.GONE);
                adapter=new ArtistListAdapter(artistParme.getValue(),ArtistSeekActivity.this);
                artistGridView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if(artistParme.getValue().size()==0){
                    Toast.makeText(ArtistSeekActivity.this, "暂时还没有相关数据", Toast.LENGTH_LONG).show();
                }


                artistGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Intent intent = new Intent(getActivity(), JobDetailsActivity.class);  //方法1
//                                Bundle bundle=new Bundle();
//                                bundle.putSerializable("Detail",recruitmentListData.get(position-1));
//                                intent.putExtras(bundle);
//                                startActivity(intent);
                        Intent intent = new Intent(ArtistSeekActivity.this, ArtistDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("Detail", artistParme.getValue().get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });

                // List<ArtistListBean> artistListData = artistParme.getValue();
                //intiGridView(artistListData);
//                      Log.e("name", artistParme.getValue().get(0).getArtistPicture().get(0).getName())  ;
//                ArtistListAdapter adapter=new ArtistListAdapter(artistParme.getValue(),ArtistSeekActivity.this);
//                artist_seek_tv.setText(artistParme.getValue().get(0).toString());
////                artistGridView.setAdapter(adapter);
////                adapter.notifyDataSetChanged();
            }

        }

        /**
         * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
//            tv.setText(""+values[0]);
        }
    }

    //获取艺人列表
    public void initListData(String abc) {
        HttpPost httpPost = new HttpPost(AppUtilsUrl.getArtistSeekList());
        // 设置HTTP POST请求参数必须用NameValuePair对象
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", abc));
        params.add(new BasicNameValuePair("offset", "0"));
        params.add(new BasicNameValuePair("limit", "4"));

        HttpResponse httpResponse = null;
        try {
            // 设置httpPost请求参数
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            httpResponse = new DefaultHttpClient().execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 第三步，使用getEntity方法活得返回结果
                String result = EntityUtils.toString(httpResponse.getEntity());

                System.out.println(result);
                if (result != null) {
                    artistParme = JSONObject.parseObject(result, new TypeReference<ArtistParme<ArtistListBean>>() {
                    });

//                    artist_seek_tv.setText(artistParme.getValue().get(0).toString());



                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
