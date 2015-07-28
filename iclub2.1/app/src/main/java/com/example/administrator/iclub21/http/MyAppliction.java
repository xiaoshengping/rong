package com.example.administrator.iclub21.http;


import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.sina.weibo.sdk.demo.R;

public class MyAppliction extends Application {
	public static  ImageLoader imageLoader = ImageLoader.getInstance();
	public static DisplayImageOptions options;
	public static DisplayImageOptions RoundedOptions;
	public static DisplayImageOptions RoundedOptionsOne;
	private static MyAppliction app = null;
     @Override
    public void onCreate() {
    	// TODO Auto-generated method stub
    	super.onCreate();
		 app=this;
    	initImageLoader(getApplicationContext());
		 options = new DisplayImageOptions.Builder()
				 .showImageOnLoading(R.mipmap.list_touxiang_icon)//加载等待 时显示的图片
				 .showImageForEmptyUri(R.mipmap.list_touxiang_icon)//加载数据为空时显示的图片
				 .showImageOnFail(R.mipmap.list_touxiang_icon)//加载失败时显示的图片
				 .build();
		 RoundedOptions = new DisplayImageOptions.Builder()
				 .showStubImage(R.mipmap.mine_head_default)//加载等待 时显示的图片
				 .showImageForEmptyUri(R.mipmap.mine_head_default)//加载数据为空时显示的图片
				 .showImageOnFail(R.mipmap.mine_head_default)//加载失败时显示的图片
				 .cacheInMemory()
				 .cacheOnDisc()
				 .displayer(new RoundedBitmapDisplayer(200))
				 .build();
		 RoundedOptionsOne = new DisplayImageOptions.Builder()
				 .showStubImage(R.mipmap.dufuke_icon)//加载等待 时显示的图片
				 .showImageForEmptyUri(R.mipmap.dufuke_icon)//加载数据为空时显示的图片
				 .showImageOnFail(R.mipmap.dufuke_icon)//加载失败时显示的图片
				 .cacheInMemory()
				 .cacheOnDisc()
				 .displayer(new RoundedBitmapDisplayer(30))
				 .build();
    }
     public static void initImageLoader(Context context) {
    	// This configuration tuning is custom. You can tune every option, you may tune some of them, 
    			// or you can create default configuration by
    			//  ImageLoaderConfiguration.createDefault(this);
    			// method.
    	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2)
    	.denyCacheImageMultipleSizesInMemory()
    	.discCacheFileNameGenerator(new Md5FileNameGenerator())
    	.tasksProcessingOrder(QueueProcessingType.LIFO)
		.build();
    	//.enableLogging() // Not necessary in common

    	// Initialize ImageLoader with configuration.
    	ImageLoader.getInstance().init(config);
    	//imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    		
    	}
	/**
	 * 拿到上下文对象
	 *
	 *
	 */
	public static MyAppliction getContextInstance(){
		return app;
	}
	/**
	 * 土司
	 */
	public static void showToast(String msg){
		Toast.makeText(app, msg, Toast.LENGTH_SHORT).show();
	}

	private String companyName;
	private String companyPhoen;
	private String companyEmail;
	private String companyWeb;
	private String companyAddress;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyPhoen() {
		return companyPhoen;
	}

	public void setCompanyPhoen(String companyPhoen) {
		this.companyPhoen = companyPhoen;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyWeb() {
		return companyWeb;
	}

	public void setCompanyWeb(String companyWeb) {
		this.companyWeb = companyWeb;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
}
