package org.danv.kapiku500.ui;

public class Coordinates {
	 private float mX;
	 private float mY;
	 	 
	 public float getX()
	 {
		 return mX;
	 }
	 
	 public float getY()
	 {
		 return mY;
	 }
	 
	 public Coordinates ()
	 {
		 this(0,0);
	 }
	 
	 public Coordinates (float x, float y)
	 {
		 mX = x;
		 mY = y;
	 }
	 
	 public void setX(float x)
	 {
		 mX = x;
	 }
	 
	 public void setY(float y)
	 {
		 mY = y;
	 }
}
