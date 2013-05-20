package com.xuyan.linkgame;

public class ArrayBox {
	int n=0;
	int x,y;
	int[][] array,box;
	public ArrayBox(int x,int y){
		this.x=x;
		this.y=y;
		this.array=new int[x][y];
		this.box=new int[x][y];
	}
	void setArray(){
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				array[i][j]=++n;
				box[i][j]=0;
			}
		}
	}

}
