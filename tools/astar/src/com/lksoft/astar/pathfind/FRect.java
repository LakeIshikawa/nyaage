package com.lksoft.astar.pathfind;

import java.io.Serializable;

/**
 * ﾌﾛｰﾄ長方形
 * 
 * @author lago francesco
 *
 */
public class FRect implements Serializable {
	private float x;
	private float y;
	private float width;
	private float height;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public FRect(float x, float y, float width, float height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public float getLeft(){
		return x;
	}
	
	public float getBottom(){
		return y;
	}
	
	public float getRight(){
		return x+width;
	}
	
	public float getTop(){
		return y+height;
	}
	
	public float getCenterX(){
		return x+width/2;
	}
	
	public float getCenterY(){
		return y+height/2;
	}
	
	@Override
	public String toString(){
		return "["+x+","+y+","+width+","+height+"]";
	}
	
	public void setLeft(float xp){
		x = xp;
	}
	
	public void setBottom(float yp){
		y = yp;
	}
	
	public void setWidth(float width){
		this.width = width;
	}
	
	public void setHeight(float height){
		this.height = height;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
}
