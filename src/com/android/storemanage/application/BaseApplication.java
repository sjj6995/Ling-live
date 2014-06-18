package com.android.storemanage.application;

import com.android.storemanage.utils.ExceptionHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
	private static BaseApplication application;

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		ExceptionHandler handler = ExceptionHandler.getInstance();
		handler.init(getApplicationContext());
		// if (Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >=
		// Build.VERSION_CODES.GINGERBREAD) {
		// StrictMode.setThreadPolicy(new
		// StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
		// StrictMode.setVmPolicy(new
		// StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
		// }
		initImageLoader(getApplicationContext());

	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	public static BaseApplication getApplication() {
		return application;
	}
}
