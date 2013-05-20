package com.xuyan.linkgame;

import com.xuyan.linkgame.Start;
import com.xuyan.linkgame.StartActivity;
import com.xuyan.linkgame.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Start extends Activity
{

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		
		ImageButton button1 = (ImageButton) findViewById(R.id.imageButton1);
		/* 监听button的事件信息 */
		button1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v)
			{
				/* 新建一个Intent对象 */
				Intent intent = new Intent();
				/* 指定intent要启动的类 */
				intent.setClass(Start.this, StartActivity.class);
				/* 启动一个新的Activity */
				startActivity(intent);
				/* 关闭当前的Activity */
				Start.this.finish();
			}
		});
	}	
}

