package com.xuyan.linkgame;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.app.Activity;
import java.util.*;

public class MyView extends View {
	public float currentX,currentY;
	public MyView(Context context,AttributeSet set)
	{
		super(context,set);
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
		Paint paint=new Paint();
		int[] image=new int[]{R.drawable.a,
				              R.drawable.b,
				              R.drawable.c,
				              R.drawable.d,
				              R.drawable.e,
				              R.drawable.f,
				              R.drawable.g,
				              R.drawable.h,
				              R.drawable.i,
				              R.drawable.j,
				              R.drawable.k,};
		//声明一个6*6的二维数组，用来存放36张图片的相对位置
		int[][] array=new int[6][6];
		int n=0;
		for(int i=0;i<6;i++)
		{
			for(int j=0;j<6;j++)
			{
				array[i][j]=++n;
			}
		}
		//生成当前系统时间的秒
		//Calendar ca=Calendar.getInstance();
		//int seed=ca.get(Calendar.SECOND)%30;
		Random random1=new Random(1);
		Random random2=new Random(2);
		Random random3=new Random(3);
		Random random4=new Random(4);
		int k=0;
		int stop=0;
		while(stop!=36)
		{
			int point1=random1.nextInt(6);
			int point2=random2.nextInt(6);
			int point3=random3.nextInt(6);
			int point4=random4.nextInt(6);
			Bitmap bitmap=BitmapFactory.decodeResource(this.getContext().getResources(), image[k]);
			if(array[point1][point2]!=0&&array[point3][point4]!=0)
			{
				canvas.drawBitmap(bitmap, point1*100, point2*100, paint);
				canvas.drawBitmap(bitmap, point3*100, point4*100, paint);
				array[point1][point2]=0;
				array[point3][point4]=0;
				stop=stop+2;
			}
			k=(k+1)%7;
			//canvas.drawBitmap(bitmap, point2*100, point2*100, paint);
		}
		/*for(int i=1;i<700;i=i+100)
		{
			for(int j=1;j<800;j=j+100)
			{
				canvas.drawBitmap(bitmap, i, j, paint);

			}
		}*/
		int rect_x=(int)currentX-((int)currentX)%100;
		int rect_y=(int)currentY-((int)currentY)%100;
		Paint paint_rect=new Paint();
		paint_rect.setAntiAlias(true);
		paint_rect.setColor(Color.RED);
		paint_rect.setStyle(Paint.Style.STROKE);
		paint_rect.setStrokeWidth(8);
		Bitmap bitmap=BitmapFactory.decodeResource(this.getContext().getResources(), image[0]);
		int img_width=bitmap.getWidth();
		canvas.drawRect(rect_x, rect_y, rect_x+img_width, rect_y+img_width, paint_rect);		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		currentX=event.getX();
		currentY=event.getY();
		invalidate();
		return true;
	}
     }

