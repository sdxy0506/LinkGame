package com.xuyan.linkgame;

import java.util.LinkedList;

import android.graphics.Point;

public class CheckLink {
	private static boolean horzion(Point a,Point b){
		if(a.x==b.x&&a.y==b.y) return false;
		int x_start=a.y<=b.y?a.y:b.y;
		int x_end=a.y<=b.y?b.y:a.y;
		for(int x=x_start+1;x<x_end;x++){
			if(Array.map[a.x][x]!=0){
				return false;
			}
		}
		return true;
		
	}
	private static boolean vertical(Point a,Point b){
		if(a.x==b.x&&a.y==b.y) return false;
		int y_start=a.x<=b.x?a.x:b.x;
		int y_end=a.x<=b.x?b.x:a.x;
		for(int y=y_start+1;y<y_end;y++){
			if(Array.map[y][a.y]!=0){
				return false;
			}
		}
		return true;
	}
	private static boolean oneCorner(Point a,Point b){
		Point c=new Point(b.x,a.y);
		Point d=new Point(a.x,b.y);
		if(Array.map[c.x][c.y]==0){
			boolean method1=vertical(a,c)&&horzion(b,c);
			GameView.link_e=c;
			return method1;
		}
		if(Array.map[d.x][d.y]==0){
			boolean method2=horzion(a,d)&&vertical(b,d);
			GameView.link_e=d;
			return method2;
		}
		return false;
	}
	private static LinkedList<Line> scan(Point a,Point b){
		LinkedList<Line> line=new LinkedList<Line>();
		for(int y=a.y-1;y>=0;y--){
			Point c=new Point(a.x,y);
			Point d=new Point(b.x,y);
			if(Array.map[a.x][y]==0&&Array.map[b.x][y]==0&&vertical(c,d)){
				line.add(new Line(0,c,d));
			}
		}
		for(int y=a.y+1;y<Array.map[a.x].length;y++){
			Point c=new Point(a.x,y);
			Point d=new Point(b.x,y);
			if(Array.map[a.x][y]==0&&Array.map[b.x][y]==0&&vertical(c,d)){
				line.add(new Line(0,c,d));
			}
		}
		for(int x=a.x-1;x>=0;x--){
			Point c=new Point(x,a.y);
			Point d=new Point(x,b.y);
			if(Array.map[x][a.y]==0&&Array.map[x][b.y]==0&&horzion(c,d)){
				line.add(new Line(1,c,d));
			}
		}
		for(int x=a.x+1;x<Array.map.length;x++){
			Point c=new Point(x,a.y);
			Point d=new Point(x,b.y);
			if(Array.map[x][a.y]==0&&Array.map[x][b.y]==0&&horzion(c,d)){
				line.add(new Line(1,c,d));
			}
		}
		return line;
	}
	public static boolean twoCorner(Point a,Point b){
		LinkedList<Line> linelist=scan(a,b);
		if(linelist.isEmpty())
			return false;
		for(int index=0;index<linelist.size();index++){
			Line l=(Line)(linelist.get(index));
			if(l.direct==1){
				if(vertical(a,l.a)&&vertical(b,l.b)){
					GameView.link_c=l.a;
					GameView.link_d=l.b;
					return true;
				}
			}
			else {
				if(horzion(a,l.a)&&horzion(b,l.b)){
					GameView.link_c=l.a;
					GameView.link_d=l.b;
					return true;
				}
			}
		}
		return false;
	}
	public static boolean checklink(Point a,Point b){
		if(Array.map[a.x][a.y]!=Array.map[b.x][b.y]){
			return false;
		}
		else if(a.x==b.x&&horzion(a,b)){
			GameView.flag_line=1;
			return true;
		}
		else if(a.y==b.y&&vertical(a,b)){
			GameView.flag_line=2;
			return true;
		}
		else if(oneCorner(a,b)){
			GameView.flag_line=3;
			return true;
		}
		else if(twoCorner(a,b)){
			GameView.flag_line=4;
			return true;
		}
		else return false;
	}
}
class Line{           
	public Point a,b;
	public int direct;
	public Line(){}
	public Line(int direct,Point a,Point b){
		this.direct=direct;
		this.a=a;
		this.b=b;
	}
}