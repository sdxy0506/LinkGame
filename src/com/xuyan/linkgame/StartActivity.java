package com.xuyan.linkgame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StartActivity extends Activity
{
	public static StartActivity instance = null;
	private GameView gameview;
	private ProgressBar bar2;
	private Button back;
	int status=100;

	@SuppressLint("HandlerLeak")
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_start);
		instance=this;
		back = (Button) findViewById(R.id.back);
		bar2 = (ProgressBar) findViewById(R.id.progressBar1);
		gameview=(GameView) findViewById(R.id.gameview);
		gameview.Play();
		final Handler mHandler=new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				if(msg.what==0x111)
				{
					bar2.setProgress(status);
					//if(status==50)
						//bar2.setVisibility(View.INVISIBLE);
				}
				if(msg.what==0x222)
				{
					dialog();
				}

			}

		};
		class MyThread extends Thread
		{
			public void run()
			{
				while(status>0)
				{
					//��ȡ��ʱ��������ɰٷֱ�
					doWork();
					if(gameview.win()) break;
					//������Ϣ��Handler
					Message m=new Message();
					m.what=0x111;
					//������Ϣ
					mHandler.sendMessage(m);
				}
				Message m=new Message();
				m.what=0x222;
				//������Ϣ
				mHandler.sendMessage(m);
			}
			//ģ��һ����ʱ�Ĳ���
			protected void doWork() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				status--;

			}
		}
		Thread my=new MyThread();
		my.start();
		back.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v)
			{
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(StartActivity.this, Start.class);
				instance.finish();
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */				
			}
		});
		Button button2 = (Button) findViewById(R.id.change);
		button2.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				GameView draw=(GameView) findViewById(R.id.gameview);
				draw.change();
				draw.invalidate();
			}
		});
		

	}	
	public void dialog()
	{
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("��Ϸ����");
		builder.setIcon(R.drawable.ic_launcher);
		if(gameview.win()) builder.setMessage("��ϲ�㣬��ʤ�ˣ�");
		else builder.setMessage("ʱ�䵽��");
		// ΪAlertDialog.Builder��ӡ�ȷ������ť
		setPositiveButton(builder);
		// ΪAlertDialog.Builder��ӡ�ȡ������ť
		setNegativeButton(builder);
		builder.create().show();
	}
	private AlertDialog.Builder setPositiveButton(
			AlertDialog.Builder builder)
	{
		// ����setPositiveButton�������ȷ����ť
		return builder.setPositiveButton("ȷ��", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				StartActivity.this.finish();
			}
		});
	}

	private AlertDialog.Builder setNegativeButton(
			AlertDialog.Builder builder)
	{
		// ����setNegativeButton�������ȡ����ť
		return builder.setNegativeButton("ȡ��", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(StartActivity.this, Start.class);
				StartActivity.this.finish();
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */
			}
		});
	}
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}*/
	/*public void clickChange(View source){
		GameView draw=(GameView) findViewById(R.id.gameview);
		draw.change();
		draw.invalidate();
	}*/

	public void winner(){
		TextView text1=(TextView) findViewById(R.id.text1);
		text1.setText("��ϲ�㣬��ʤ�ˣ�");
	}

}

