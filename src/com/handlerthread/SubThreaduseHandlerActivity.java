package com.handlerthread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class SubThreaduseHandlerActivity extends ActionBarActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Log.d("yao", "Runnable开启线程方式");
			}
		}).start();
		
		new Thread(){
			@Override
			public void run() {
				super.run();
				Log.d("yao", "thread开启线程方式");
			}
		}.start();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*new Thread(){
			
			public Handler handler;
			public void run() {
				Looper.prepare();
				
				handler = new Handler(){
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
					}
				};
				
			};
		};
		
		new Thread(){
			public void run() {
//				handler
			};
		};*/
		
	}
}
