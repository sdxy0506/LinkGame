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
		/* ����button���¼���Ϣ */
		button1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v)
			{
				/* �½�һ��Intent���� */
				Intent intent = new Intent();
				/* ָ��intentҪ�������� */
				intent.setClass(Start.this, StartActivity.class);
				/* ����һ���µ�Activity */
				startActivity(intent);
				/* �رյ�ǰ��Activity */
				Start.this.finish();
			}
		});
	}	
}

