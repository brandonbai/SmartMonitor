package org.jifeihu.smartshed;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.File;

public class SmartShedApp extends Application {

	private RefWatcher refWatcher;
	private static final String TAG = SmartShedApp.class.getSimpleName();
	private static String rootPath = "/smartshed";

	public static String cachePath = "/cache";

	public static RefWatcher getRefWatcher(Context context) {
		SmartShedApp application = (SmartShedApp) context.getApplicationContext();
		return application.refWatcher;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		initPath();
		refWatcher = LeakCanary.install(this);
		Log.i(TAG, "create");
	}
	
	private void initPath() {
		String ROOT = "";
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			ROOT = Environment.getExternalStorageDirectory().getPath();
		}
		rootPath = ROOT + rootPath;
		cachePath = rootPath + cachePath;
		File file = new File(cachePath);
		if(!file.exists()) {
			file.mkdirs();
			Log.i(TAG, "mkdirs");
		}
	}
}
