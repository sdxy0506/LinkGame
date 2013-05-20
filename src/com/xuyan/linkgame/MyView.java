package com.xuyan.linkgame;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.annotation.SuppressLint;

@SuppressLint("DrawAllocation")
public class MyView extends View {
	public float currentX=700,currentY=700;
	public MyView(Context context,AttributeSet set)
	{
		super(context,set);
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
		Paint paint_img=new Paint();
		paint_img.setAntiAlias(true);
		int[] image=new int[]{R.drawable.a,
				              R.drawable.b,
				              R.drawable.c,
				              R.drawable.d,
				              R.drawable.e,
				              R.drawable.f,
				              R.drawable.g,
				              R.drawable.h,
				              //R.drawable.i,
				              //R.drawable.j,
				              //R.drawable.k,
				              };
		Bitmap bitmap=BitmapFactory.decodeResource(this.getContext().getResources(), image[0]);
		int img_width=bitmap.getWidth();
		//调用Array_box类生成两个二维数组
		ArrayBox array_box=new ArrayBox(6,6);
		array_box.setArray();
		//生成当前系统时间的秒
		//Calendar ca=Calendar.getInstance();
		//int seed=ca.get(Calendar.SECOND)%30;
		//生成4个随机数,2个一组确定一个点
		Random random1=new Random(1);
		Random random2=new Random(2);
		Random random3=new Random(3);
		Random random4=new Random(4);
		int img_no=0;
		int stop=0;
		while(stop!=(array_box.x*array_box.y))
		{
			//取随机数列中小于6的点作为坐标值
			int x1=random1.nextInt(6);
			int y1=random2.nextInt(6);
			int x2=random3.nextInt(6);
			int y2=random4.nextInt(6);
			Bitmap bitmap1=BitmapFactory.decodeResource(this.getContext().getResources(), image[img_no]);
			if(array_box.array[x1][y1]!=0&&array_box.array[x2][y2]!=0)
			{
				//成对生成图块
				canvas.drawBitmap(bitmap1, x1*90+10, y1*90+10, paint_img);
				canvas.drawBitmap(bitmap1, x2*90+10, y2*90+10, paint_img);
				array_box.array[x1][y1]=array_box.array[x2][y2]=0;
				array_box.box[x1][y1]=array_box.box[x2][y2]=img_no;
				stop=stop+2;
			}
			img_no=(img_no+1)%7;
		}
		int rect_x=(int)currentX-((int)currentX)%90+10;
		int rect_y=(int)currentY-((int)currentY)%90+10;
		Paint paint_rect=new Paint();
		paint_rect.setAntiAlias(true);
		paint_rect.setColor(Color.RED);
		paint_rect.setStyle(Paint.Style.STROKE);
		paint_rect.setStrokeWidth(8);
		if(rect_x<(array_box.x*100)&&rect_y<(array_box.y*100))
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

