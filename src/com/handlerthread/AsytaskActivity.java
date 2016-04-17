package com.handlerthread;

import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class AsytaskActivity extends Activity {
	private String url = "http://api.map.baidu.com/telematics/v3/weather?location=%E5%98%89%E5%85%B4&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ";

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
				HttpGet get = new HttpGet(url);
				HttpClient client = new DefaultHttpClient();
				
				HttpResponse response = client.execute(get);
				
				if (response.getStatusLine().getStatusCode() == 200) {
					result = EntityUtils.toString(response.getEntity(),"utf-8");
					Log.d("yao", result);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result!=null) {
				Log.d("yao", "onPostExecute接收到的"+result);
			}
			
		}

	}

}
