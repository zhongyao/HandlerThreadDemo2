package com.handlerthread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * 参考blog：
 * http://blog.csdn.net/feiduclear_up/article/details/46840523#comments
 * http://blog.csdn.net/lmj623565791/article/details/47079737
 * @author zhongyao
 *
 */
public class MainActivity extends Activity implements OnClickListener{

	public static String YAO = "yao";
	private TextView mTvServiceInfo;

	private HandlerThread mCheckMsgThread;
	private Handler mCheckMsgHandler = new Handler(){
		
	};
	private boolean isUpdateInfo;
	private static final int MSG_UPDATE_INFO = 0x110;

	// 与UI线程管理的handler
	private Handler mHandler = new Handler();

	private Button button,button1,button2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTvServiceInfo = (TextView) findViewById(R.id.textView);
		button = (Button) findViewById(R.id.button);
		button1 = (Button) findViewById(R.id.button1);
		button2  = (Button) findViewById(R.id.button2);
		
		button.setOnClickListener(this);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		// 创建后台线程
		initBackThread();
		
//		new Thread(){
//			public void run() {
//				mCheckMsgHandler.sendEmptyMessage(1);
//				
//				mHandler.post(new Runnable() {
//					public void run() {
//						mTvServiceInfo.setText("收到");
//					}
//				});
//			};
//		}.start();
		
		

	}

	@Override
	protected void onResume() {
		super.onResume();
		// 开始查询
		isUpdateInfo = true;
		Log.d("yao", "onResume");
		mCheckMsgHandler.sendEmptyMessage(MSG_UPDATE_INFO);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 停止查询
		isUpdateInfo = false;
		mCheckMsgHandler.removeMessages(MSG_UPDATE_INFO);

	}
	/*
	 * 将HandlerThread中创建的looper传递给Handler。
	 * 
	 * 也就意味着该Handler收到Message后，程序在HandlerThread创建的线程中运行
	 * 
	 */
	private void initBackThread() {
		mCheckMsgThread = new HandlerThread("check-message-coming");
		mCheckMsgThread.start();
		mCheckMsgHandler = new Handler(mCheckMsgThread.getLooper()) {
			@Override
			public void handleMessage(Message msg) {
				boolean b = Looper.getMainLooper()==Looper.myLooper();
				Log.v("yao", String.valueOf(b));//为false，可知在子线程中
				Log.d("yao", "mCheckMsgHandler--handleMessage");
				checkForUpdate();
				if (isUpdateInfo) {
					Log.d("yao", "mCheckMsgHandler--sendEmptyMessageDelayed");
//					mCheckMsgHandler.sendEmptyMessageDelayed(MSG_UPDATE_INFO,
//							1000);
					mCheckMsgHandler.sendEmptyMessage(MSG_UPDATE_INFO);
				}
			}
		};

	}

	/**
	 * 模拟从服务器解析数据
	 */
	private void checkForUpdate() {
		try {
			// 模拟耗时
			Thread.sleep(3000);
			
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					Log.d("yao", "mHandler--post");
					Log.d("yao", (Looper.getMainLooper() == Looper.myLooper())+"");//为true：可知在UI线程中
					String result = "实时更新中，当前大盘指数：<font color='red'>%d</font>";
					result = String.format(result,
							(int) (Math.random() * 3000 + 1000));
					mTvServiceInfo.setText(Html.fromHtml(result));
				}
			});

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 释放资源
		mCheckMsgThread.quit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button:
			Intent intent = new Intent(this,AsytaskActivity.class);
			startActivity(intent);
			break;
		case R.id.button1:
			Intent intent1 = new Intent(this,SubThreaduseHandlerActivity.class);
			startActivity(intent1);
			break;
		case R.id.button2:
			Intent intent2 = new Intent(this,MainThreadToSubThreadActivity.class);
			startActivity(intent2);
			break;
		default:
			break;
		}
		
	}

}
