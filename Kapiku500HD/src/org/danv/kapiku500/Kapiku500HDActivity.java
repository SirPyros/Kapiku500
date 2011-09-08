package org.danv.kapiku500;

import java.util.HashMap;
import java.util.Map;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.HorizontalAlign;
import org.danv.kapiku500.engine.Bone;
import org.danv.kapiku500.engine.Game;
import org.danv.kapiku500.engine.HumanPlayer;
import org.danv.kapiku500.engine.IPlayer;
import org.danv.kapiku500.ui.CoordinateInfo;
import org.danv.kapiku500.ui.Coordinates;
import org.danv.kapiku500.ui.DisplayUnit;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Kapiku500HDActivity extends BaseGameActivity implements IOnSceneTouchListener {
	// ===========================================================
		// Constants
		// ===========================================================
		private static final int CAMERA_WIDTH = 1280;
		private static final int CAMERA_HEIGHT = 800;
		private static final Rect BOARD_AREA = new Rect(200, 200, CAMERA_WIDTH - 400, CAMERA_HEIGHT - 400); 
		private static final int BONE_HEIGHT = 128;
		private static final int BONE_WIDTH = 64;

		// ===========================================================
		// Fields
		// ===========================================================
		private Camera mCamera;
		private BitmapTextureAtlas mBitmapTextureAtlas;		
		private Game mGame;
		private IPlayer[] mPlayers;		
		private Map<String, DisplayUnit> dominoDisplay;
		private BitmapTextureAtlas mFontTexture;
		private Font mFont;
		private boolean mGrabbed = false;
		private int mPlayerSetupIdx = 0;
		// ===========================================================
		// Constructors
		// ===========================================================

		// ===========================================================
		// Getter & Setter
		// ===========================================================

		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================
		
		@Override
		public Engine onLoadEngine() {
			this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
			SetupGameEngine();
			return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
		}

		@Override
		public void onLoadResources() {
			this.mBitmapTextureAtlas = new BitmapTextureAtlas(2048, BONE_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
			Bone[] bones = mGame.getDeck().getBones();
			dominoDisplay = new HashMap<String, DisplayUnit>();
			int idx = 0;
			for(Bone bone : bones)
			{
				dominoDisplay.put(bone.toString(), 
						new DisplayUnit(new Coordinates(), 
								BitmapTextureAtlasTextureRegionFactory.createFromAsset(
										this.mBitmapTextureAtlas, this, bone.toString() + ".png", idx*BONE_WIDTH, 0)));				
				idx++;				
			}
			
			this.mFontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

			this.mFont = new Font(this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, true, Color.BLACK);

			this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
			this.getFontManager().loadFont(this.mFont);
			
			this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas);
		}

		@Override
		public Scene onLoadScene() {
			this.mEngine.registerUpdateHandler(new FPSLogger());

			final Scene scene = new Scene();
			scene.setOnAreaTouchTraversalFrontToBack();
			scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
			
			/* Calculate the coordinates for the face, so its centered on the camera. */			
			mPlayerSetupIdx = 0;
			for(IPlayer player : mPlayers)
	        {
				CoordinateInfo X = new CoordinateInfo();
				CoordinateInfo Y = new CoordinateInfo();				
				
				float pRotation = 0;
				switch(mPlayerSetupIdx)
				{
					case 0:
						X.Text = CAMERA_WIDTH / 2;
						Y.Text = BONE_HEIGHT + 16;
						X.Delta = 16;
						X.Position = 200;
						Y.Position = 0;
						break;
					case 1:
						X.Text = CAMERA_WIDTH - BONE_HEIGHT - BONE_WIDTH;
						Y.Text = CAMERA_HEIGHT / 2;
						Y.Delta = 16;
						pRotation = 90;
						X.Position = CAMERA_WIDTH - BONE_HEIGHT;
						Y.Position = 10;
						break;
					case 2:
						X.Text = CAMERA_WIDTH / 2;
						Y.Text = CAMERA_HEIGHT - BONE_HEIGHT - 10;
						X.Delta = 16;
						X.Position = 10;
						Y.Position = CAMERA_HEIGHT - BONE_HEIGHT;
						break;
					case 3:
						X.Text = BONE_HEIGHT + 10;
						Y.Text = CAMERA_HEIGHT / 2;
						Y.Delta = 16;
						pRotation= 90;
						X.Position = BONE_WIDTH;
						Y.Position = 0;
						break;
				}
				
				final Text textLeft = new Text(X.Text, Y.Text, this.mFont, player.getName(), HorizontalAlign.CENTER);
				textLeft.setRotation(pRotation);
				scene.attachChild(textLeft);
	        	Bone[] bones = player.getHand().getBones();
	        	for(Bone bone : bones)
	        	{
	        		DisplayUnit boneDisplay = dominoDisplay.get(bone.toString());
	        		boneDisplay.setCoordinates(X.Position, Y.Position);
	        		final Sprite face = new Sprite(X.Position, Y.Position, boneDisplay.getTexture()) {
	        			int playerIdx = mPlayerSetupIdx;
	        			@Override
	        			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	        				if(mGame.getCurrentPlayerIndex() != playerIdx)
	        					return true;
	        				switch(pSceneTouchEvent.getAction()) {
		    					case TouchEvent.ACTION_DOWN:
		    						if(!mGrabbed)
		    						{
			    						this.setScale(1.25f);
			    						mGrabbed = true;
		    						}
		    						break;
		    					case TouchEvent.ACTION_MOVE:
		    						if(mGrabbed) {
		    							this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
		    						}
		    						break;
		    					case TouchEvent.ACTION_UP:
		    						if(mGrabbed) {
		    							mGrabbed = false;
		    							this.setScale(1.0f);
		    						}
		    						break;
		    				}
		    				return true;
	        			}
	        		};
	        		face.setRotation(pRotation);
	        		scene.attachChild(face);
	        		scene.registerTouchArea(face);	        		
	        		X.increasePosition(BONE_WIDTH);
	        		Y.increasePosition(BONE_WIDTH);
	        	}	        		
				
	        	mPlayerSetupIdx++;
	        }
			scene.setTouchAreaBindingEnabled(true);
			return scene;
		}

		@Override
		public void onLoadComplete() {

		}
		
		@Override
		public boolean onSceneTouchEvent(Scene pScene,
				TouchEvent pSceneTouchEvent) {
			// TODO Auto-generated method stub
			return false;
		}

		// ===========================================================
		// Methods
		// ===========================================================
		private void SetupGameEngine()
		{
			mGame = new Game();
			mPlayers = new IPlayer[4];
	        for(int idx=0;idx<4;idx++)
	        	mPlayers[idx] = new HumanPlayer("Player" + String.valueOf(idx));
	        
	        mGame.newGame(mPlayers);
		}
		
		
		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================

		
		
		
    
}