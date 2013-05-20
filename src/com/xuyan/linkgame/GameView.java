package com.xuyan.linkgame;
//4.24发现重大bug，canvas绘制的行与列与数组的行与列相反
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
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
	public static int xCount=10;
	public static int yCount=8;
	public static int touch_x,touch_y;
	public static int flag_line=0;
	public static int flag1=1;
	private Paint paint_rect=new Paint();
	private Paint paint_bit=new Paint();
	private Paint paint=new Paint();
	private Paint paint_line=new Paint();
	private Point a,b;
	public static Point link_e,link_c,link_d;
	//StartActivity activity;
	public int icon_size=BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.a).getWidth();
    protected List<Point> selected = new ArrayList<Point>();
	public GameView(Context context,AttributeSet set){
		super(context,set);		
	}
	public void Play(){
		setMap();
		setPaint();
		GameView.this.invalidate();
	}
	private void setPaint(){		
		paint_rect.setColor(Color.RED);
		paint_rect.setStyle(Paint.Style.STROKE);
		paint_rect.setStrokeWidth(6);
		paint_line.setColor(Color.BLUE);
		paint_line.setStrokeWidth(4);		
		paint.setAntiAlias(true);	
		paint.setAlpha(0);
	}
	//随机交换map的值，即map中的图片筽
	public void change(){
		flag_line=0;
		flag1=0;
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
		//paint.setStyle(Paint.Style.FILL);
		//canvas.drawBitmap(BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.background), 0, 0, null);
		int rect_x=touch_x-touch_x%icon_size;
		int rect_y=touch_y-touch_y%icon_size;
		for(int i=0;i<Array.map.length;i++){
			for(int j=0;j<Array.map[i].length;j++){
				switch(flag_line){
				case 0:
					break;
				case 1:
				case 2:
					canvas.drawLine(a.y*icon_size+icon_size/2,a.x*icon_size+icon_size/2,  b.y*icon_size+icon_size/2,b.x*icon_size+icon_size/2,  paint_line);
					break;
				case 3:
					canvas.drawLine(a.y*icon_size+icon_size/2,a.x*icon_size+icon_size/2,  link_e.y*icon_size+icon_size/2,link_e.x*icon_size+icon_size/2,  paint_line);
					canvas.drawLine(link_e.y*icon_size+icon_size/2,link_e.x*icon_size+icon_size/2,  b.y*icon_size+icon_size/2,b.x*icon_size+icon_size/2,  paint_line);
					break;
				case 4:
					canvas.drawLine(a.y*icon_size+icon_size/2,a.x*icon_size+icon_size/2,  link_c.y*icon_size+icon_size/2,link_c.x*icon_size+icon_size/2,  paint_line);
					canvas.drawLine(link_d.y*icon_size+icon_size/2,link_d.x*icon_size+icon_size/2, link_c.y*icon_size+icon_size/2,link_c.x*icon_size+icon_size/2,  paint_line);
					canvas.drawLine(link_d.y*icon_size+icon_size/2,link_d.x*icon_size+icon_size/2,  b.y*icon_size+icon_size/2,b.x*icon_size+icon_size/2,  paint_line);
					break;
				}
				if(Array.map[i][j]==0) 
					canvas.drawRect(j*icon_size,i*icon_size,j*icon_size+icon_size,i*icon_size+icon_size,paint);
				else
					canvas.drawBitmap(BitmapFactory.decodeResource(this.getContext().getResources(), Array.map[i][j]), j*icon_size, i*icon_size, paint_bit);
			}
		}	
		if(rect_x<yCount*icon_size&&rect_x>=icon_size&&rect_y>=icon_size&&rect_y<xCount*icon_size&&flag1==1)
			canvas.drawRect(rect_x, rect_y, rect_x+icon_size, rect_y+icon_size, paint_rect);
	}
	//初始化map，成对生成图块，并随机打乱图块位置
	private void setMap(){
		int img_no=0;
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
				R.drawable.k,
				};			
			for(int i=0;i<Array.map.length;i++){
				for(int j=0;j<Array.map[i].length;j++){
					if(i==0||i==Array.map.length-1) Array.map[i][j]=0;
					else if(j==0||j==Array.map[i].length-1) Array.map[i][j]=0;
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
		int y=(touch_x-touch_x%icon_size)/icon_size;
		int x=(touch_y-touch_y%icon_size)/icon_size;
		flag1=1;
		Point p=new Point(x,y);
        if(Array.map[p.x][p.y] != 0){
            if(selected.size() == 1){
                if(CheckLink.checklink(selected.get(0),p)){ 
                	flag1=0;
                	a=selected.get(0);
                	b=p;
                    removeMap(a,b);
                    selected.clear();
                    invalidate();
                }
                else{
                	flag_line=0;
                    selected.clear();
                    selected.add(p);
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
		Array.map[a.x][a.y]=0;
		Array.map[b.x][b.y]=0;
	}
	public boolean win(){
		int n=0;
		for(int i=0;i<Array.map.length;i++){
			for(int j=0;j<Array.map[i].length;j++){
				if(Array.map[i][j]==0)
					n++;
			}
		}
		if(n==xCount*yCount) 
			return true;
		else 
			return false;
	}

}
