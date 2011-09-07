package org.danv.kapiku500.ui;

public class Coordinates {
	 private CoordinateInfo mX;
	 private CoordinateInfo mY;
	 
	 public CoordinateInfo getXInfo()
	 {
		 return mX;
	 }
	 public CoordinateInfo getYInfo()
	 {
		 return mY;
	 }
	 
	 public float getX()
	 {
		 return mX.Position;
	 }
	 
	 public float getY()
	 {
		 return mY.Position;
	 }
	 
	 public Coordinates(CoordinateInfo x, CoordinateInfo y)
	 {
		 mX = x;
		 mY = y;
	 }
	 
	 public Coordinates ()
	 {
		 this(0,0);
	 }
	 
	 public Coordinates (float x, float y)
	 {
		 mX = new CoordinateInfo(x, 0, 0);
		 mY = new CoordinateInfo(y, 0, 0);
	 }
}
