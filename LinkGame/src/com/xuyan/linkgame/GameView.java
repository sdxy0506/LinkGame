package com.xuyan.linkgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("DrawAllocation")
public class GameView extends View{
	public static int xCount=8;
	public static int yCount=8;
	public static float currentX;
	public static float currentY;
	public static int touch_x,touch_y;
	public static int img_no=0;	
	public static int touch_no=0;
	protected List<Point> selected = new ArrayList<Point>();
	public GameView(Context context,AttributeSet set){
		super(context,set);
		setMap();
	}
	public void change(){
		Random random=new Random();
		int xc,yc,tc;
		for(int x=1;x<xCount-1;x++){
			for(int y=1;y<yCount-1;y++){
				xc=random.nextInt(xCount-3)+1;
				yc=random.nextInt(yCount-3)+1;
				tc=Array.map[x][y];
				Array.map[x][y]=Array.map[xc][yc];
				Array.map[xc][yc]=tc;
			}
		}
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		//canvas.drawColor(Color.WHITE);
		Paint paint_bit=new Paint();
		Paint paint=new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		//paint.setStyle(Paint.Style.FILL);
		for(int i=1;i<Array.map.length-1;i++){
			for(int j=1;j<Array.map[i].length-1;j++){
				if(Array.map[i][j]==-1) canvas.drawRect(i*90,j*90,i*90+90,j*90+90,paint);
				else {
					Bitmap bitmap=BitmapFactory.decodeResource(this.getContext().getResources(), Array.map[i][j]);
					canvas.drawBitmap(bitmap, i*90, j*90, paint_bit);
					};
			}
		}
		Bitmap bitmap1=BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.a);
		int img_width=bitmap1.getWidth();
		int rect_x=touch_x-touch_x%90;
		int rect_y=touch_y-touch_y%90;
		Paint paint_rect=new Paint();
		paint_rect.setAntiAlias(true);
		paint_rect.setColor(Color.RED);
		paint_rect.setStyle(Paint.Style.STROKE);
		paint_rect.setStrokeWidth(8);
		if(rect_x<xCount*90&&rect_x>=90&&rect_y>=90&&rect_y<yCount*90)
			canvas.drawRect(rect_x, rect_y, rect_x+img_width, rect_y+img_width, paint_rect);
	}
	public void setMap(){
		int[] image=new int[]{
				R.drawable.a,
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
			for(int i=0;i<Array.map.length;i++){
				for(int j=0;j<Array.map[i].length;j++){
					if(i==0||i==Array.map.length-1) Array.map[i][j]=-1;
					else if(j==0||j==Array.map[i].length-1) Array.map[i][j]=-1;
					else {
						Array.map[i][j]=image[img_no];
					}
				}
				img_no++;
			}
			change();
	}
	public boolean onTouchEvent(MotionEvent event) {
		touch_x=(int)event.getX();
		touch_y=(int)event.getY();
		int x=(touch_x-touch_x%90)/90;
		int y=(touch_y-touch_y%90)/90;
        Point p = new Point(x,y);
        if(Array.map[p.x][p.y] != -1){
            if(selected.size() == 1){
                if(CheckLink.checklink(selected.get(0),p)){   
                    removeMap(selected.get(0),p);
                	//selected.add(p);
                    selected.clear();
                    invalidate();
                }
                else{
                    selected.clear();
                    //selected.add(p);
                    invalidate();
                }
            }
            else{
                selected.add(p);
                invalidate();
            }
        }
        return super.onTouchEvent(event);
    }
	public void removeMap(Point a,Point b){
		Array.map[a.x][a.y]=-1;
		Array.map[b.x][b.y]=-1;
	}
}
