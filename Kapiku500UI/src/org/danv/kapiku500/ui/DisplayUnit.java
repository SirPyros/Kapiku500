package org.danv.kapiku500.ui;

import org.anddev.andengine.opengl.texture.region.TextureRegion;

public class DisplayUnit {
	TextureRegion mTexture;
	Coordinates mCoord;
	public DisplayUnit(Coordinates coordinates, TextureRegion texture)
	{
		mTexture = texture;
		mCoord = coordinates;
	}	
	
	public Coordinates getCoordinates()
	{
		return mCoord;
	}
	
	public TextureRegion getTexture()
	{
		return mTexture;
	}
	
	public void setCoordinates(float x, float y)
	{
		mCoord.setX(x);
		mCoord.setY(y);
	}
}
