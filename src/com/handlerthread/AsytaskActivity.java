package com.handlerthread;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class AsytaskActivity extends Activity {

	private ImageView iv;
	private long timecurrentTimeMillis, timecurrentTimeMillis2;
	private long time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asynctask);

		initView();

		new MyTask().execute();
	}

	private void initView() {
		iv = (ImageView) findViewById(R.id.iv);
	}

	private class MyTask extends AsyncTask<URL, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(URL... params) {

			try {
				timecurrentTimeMillis = System.currentTimeMillis();
				URL url = new URL(
						"http://g.hiphotos.baidu.com/image/pic/item/060828381f30e9244e3f894a49086e061d95f736.jpg");

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(5 * 1000);
				conn.setReadTimeout(5 * 1000);
				conn.setRequestMethod("GET");
				conn.setUseCaches(true);
				if (conn.getResponseCode() == 200) {
					InputStream is = conn.getInputStream();

					timecurrentTimeMillis2 = System.currentTimeMillis();
					time = timecurrentTimeMillis2 - timecurrentTimeMillis;
					Log.d("yao", time + "");// 请求时间127ms
					return BitmapFactory.decodeStream(is);
				} else {
					Log.d("yao", "server异常...");
				}

//				timecurrentTimeMillis = System.currentTimeMillis();
//				HttpGet get = new HttpGet(
//						"http://g.hiphotos.baidu.com/image/pic/item/060828381f30e9244e3f894a49086e061d95f736.jpg");
//				HttpClient client = new DefaultHttpClient();
//				HttpResponse response = client.execute(get);
//				InputStream is = response.getEntity().getContent();
//				timecurrentTimeMillis2 = System.currentTimeMillis();
//				time = timecurrentTimeMillis2 - timecurrentTimeMillis;
//				Log.d("yao", time + "");// 请求时间183ms
//				return BitmapFactory.decodeStream(is);
				
				//可知使用HttpUrlConnection请求比HttpClient要快
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);
			if (bitmap != null) {
				Log.d("yao", "onPostExecute执行ִ" + bitmap);
				iv.setImageBitmap(bitmap);
			}

		}

	}

}
