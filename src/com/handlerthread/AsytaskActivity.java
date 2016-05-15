package com.handlerthread;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class AsytaskActivity extends Activity {
//	public String url = "http://api.map.baidu.com/telematics/v3/weather?location=%E5%98%89%E5%85%B4&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		new MyTask().execute();
	}

	private class MyTask extends AsyncTask<URL, Void, String> {

		@Override
		protected String doInBackground(URL... params) {
			
			String result = null;
					try {
						URL url = new URL("http://api.map.baidu.com/telematics/v3/weather?location=%E5%98%89%E5%85%B4&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ");
						
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setConnectTimeout(15*1000);
						if (conn.getResponseCode() == 200) {
							InputStream is = conn.getInputStream();
//							result = 
						}else {
							
						}
						
						
					} catch (IOException e) {
						e.printStackTrace();
					}
//				HttpGet get = new HttpGet(url);
//				HttpClient client = new DefaultHttpClient();
//				
//				HttpResponse response = client.execute(get);
//				
//				if (response.getStatusLine().getStatusCode() == 200) {
//					result = EntityUtils.toString(response.getEntity(),"utf-8");
//					Log.d("yao", result);
//				}
				
//				HttpURLConnection httpURLConnection = new 
				
			
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result!=null) {
				Log.d("yao", "onPostExecute执行"+result);
			}
			
		}

	}

}
