package com.handlerthread;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	private TextView mTvServiceInfo;

	private HandlerThread mCheckMsgThread;
	private Handler mCheckMsgHandler;
	private boolean isUpdateInfo;
	private static final int MSG_UPDATE_INFO = 0x110;

	// 与UI线程管理的handler
	private Handler mHandler = new Handler();

	private Button button,button1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTvServiceInfo = (TextView) findViewById(R.id.textView);
		button = (Button) findViewById(R.id.button);
		button1 = (Button) findViewById(R.id.button1);
				
		button.setOnClickListener(this);
		button1.setOnClickListener(this);
		// 创建后台线程
		initBackThread();

	}

	@Override
	protected void onResume() {
		super.onResume();
		// 开始查询
		isUpdateInfo = true;
		mCheckMsgHandler.sendEmptyMessage(MSG_UPDATE_INFO);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 停止查询
		isUpdateInfo = false;
		mCheckMsgHandler.removeMessages(MSG_UPDATE_INFO);

	}

	private void initBackThread() {
		mCheckMsgThread = new HandlerThread("check-message-coming");
		mCheckMsgThread.start();
		mCheckMsgHandler = new Handler(mCheckMsgThread.getLooper()) {
			@Override
			public void handleMessage(Message msg) {
				checkForUpdate();
				if (isUpdateInfo) {
					mCheckMsgHandler.sendEmptyMessageDelayed(MSG_UPDATE_INFO,
							3000);
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
		default:
			break;
		}
		
	}

}
