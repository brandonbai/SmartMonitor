package org.jifeihu.smartshed.network;

import android.util.Log;

import com.google.gson.Gson;

import org.jifeihu.smartshed.SmartShedApp;
import org.jifeihu.smartshed.bean.MyResponse;
import org.jifeihu.smartshed.interfaces.IConstants;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jifeihu on 2017/3/14.
 */

public class OkHttpManager implements IConstants {

    private static final String TAG = OkHttpManager.class.getSimpleName();
    private String token;

    private OkHttpClient client;
    private String path;

    private OkHttpManager() {
        final long cacheSize = 1024*1024*10;//缓存文件最大限制大小10M
        String cachePath = SmartShedApp.cachePath;  //设置缓存文件路径
        Cache cache = new Cache(new File(cachePath), cacheSize);  //

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(8, TimeUnit.SECONDS);  //设置连接超时时间
        builder.writeTimeout(8, TimeUnit.SECONDS);//设置写入超时时间
        builder.readTimeout(8, TimeUnit.SECONDS);//设置读取数据超时时间
        builder.retryOnConnectionFailure(false);//设置不进行连接失败重试
        builder.cache(cache);//这种缓存
        client = builder.build();
    }
    public OkHttpClient getClient() {
        return client;
    }

    private static OkHttpManager INSTANCE = new OkHttpManager();

    public static OkHttpManager getOkHttpManager() {
        return INSTANCE;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public MyResponse getMyResponseByPost(String url, Map<String, String> form, Type resultType) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : form.entrySet()){
            builder.add(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = builder.build();

        Request request = new Request.Builder().url(path+url).addHeader("token", token==null?"":token)
                .post(requestBody).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String result = response.body().string();
            Log.i(TAG, "post---"+result);
            return new Gson().fromJson(result, resultType);
        } catch (Exception e) {
            return null;
        }
    }

    public MyResponse getMyResponseByGet(String url, Type type) {
        Request request = new Request.Builder().url(path+url)
                .cacheControl(new CacheControl.Builder().maxAge(10, TimeUnit.SECONDS).build())
                .addHeader(KEY_TOKEN, token).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String result = response.body().string();
            Log.i(TAG, "get---"+result);
            return new Gson().fromJson(result, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
