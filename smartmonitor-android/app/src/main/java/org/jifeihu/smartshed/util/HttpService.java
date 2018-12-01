package org.jifeihu.smartshed.util;

import android.app.Application;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 通过http协议获取web资源
 */
public class HttpService {

	public static final String POST = "POST";
	public static final String GET = "GET";

	/**
	 * 获取web资源
	 * @param servletName 请求的资源名
	 * @param map 请求参数的键值对
	 * @return 服务器返回的信息
	 */
	public String getSource(Application context, String servletName, @Nullable Map<Object, Object> map, String method) {
		StringBuilder sb = new StringBuilder();
		try {
			SharedPreferenceUtil sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
			String user = sharedPreferenceUtil.getUserInfo();
			URL url = new URL(sharedPreferenceUtil.getServerHome() + servletName);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置超时时间
			conn.setConnectTimeout(5000);
			// 设置请求方式
			conn.setRequestMethod(method);
			// 设置是否向httpUrlConnection输出，因为此处为post请求，参数要放在正文内，因此需要设置为true,默认为false
			conn.setDoOutput("POST".equals(method));
			// 不使用缓存
			conn.setUseCaches(false);
			if(user != null) {
				Log.i("---------cookie--------", user);
				conn.setRequestProperty("Cookie", "user=");
			}
			conn.connect();

			if("POST".equals(method) && map != null) {
				PrintWriter pw = new PrintWriter(conn.getOutputStream());
				boolean flag = true;
				for(Map.Entry<Object, Object> entry : map.entrySet()) {
					if(flag) flag = false;
					else pw.print("&");
					String key = entry.getKey().toString();
					String value = entry.getValue().toString();
					pw.print(key);
					pw.print("=");
					pw.print(URLEncoder.encode(value,"utf-8"));
				}
				pw.flush();
				pw.close();
				Log.i("--------send----------", map.toString());
			}
			Log.i("--状态码--",""+conn.getResponseCode());
			if(conn.getResponseCode() != 200) {
				return null;
			}
			InputStream in = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		Log.i("--接收：--",sb.toString());
		return sb.toString();
	}

}
