package org.danv.kapiku500.ui;

public class CoordinateInfo
{
	public float Position = 0;
	public float Text = 0;
	public float Delta = 0;
	
	public void increasePosition(float offset)
	{
		if(Delta > 0)
			Position += Delta + offset;
	}
	
	public CoordinateInfo(float position, float delta, float text)
	{
		Position = position;
		Text = text;
		Delta = delta;
	}
	
	public CoordinateInfo()
	{
		
	}
}
