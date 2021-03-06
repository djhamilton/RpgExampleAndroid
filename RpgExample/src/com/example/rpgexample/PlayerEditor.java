/**
 * 
 */
package com.example.rpgexample;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;

/**
 * @author impaler
 *
 */
public class PlayerEditor {
	
	private static final String TAG = PlayerEditor.class.getSimpleName();

	private Bitmap bitmap;		// the animation sequence
	private Rect sourceRect;	// the rectangle to be drawn from the animation bitmap
	private int frameNr;		// number of frames in animation
	private int currentFrame;	// the current frame
	private long frameTicker;	// the time of the last frame update
	private int framePeriod;	// milliseconds between each frame (1000/fps)
	
	private int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
	private int spriteHeight;	// the height of the sprite
	
	private int x;				// the X coordinate of the object (top left of the image)
	private int y;				// the Y coordinate of the object (top left of the image)
	
	private boolean up, down, right, left = false;
	
	public PlayerEditor(Bitmap bitmap, int x, int y, int width, int height, int fps, int frameCount) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		currentFrame = 0;
		frameNr = frameCount;
		spriteWidth = bitmap.getWidth() / frameCount;
		spriteHeight = bitmap.getHeight();
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		framePeriod = 1000 / fps;
		frameTicker = 0l;
	}
	
	public void updatePos(TileGroundEditor tg){
		if(this.down){
    		if (y+64 < tg.getHeight()*64){
    			if(!tg.checkIntersecPlayer(x, y+64))
    				y+=64;
            }
    	}
    	if(this.up){
    		if(y > 0){
    			if(!tg.checkIntersecPlayer(x, y-64))
    				y-=64;
    		}   			   			
    	}
    	if(this.left){
    		if(x > 0){
    			if(!tg.checkIntersecPlayer(x-64, y))
    				x-=64;
    		}  			
    	}
    	if(this.right){
    		if (x+64 < tg.getWidth()*64){
    			if(!tg.checkIntersecPlayer(x+64, y))
    				x+=64;
            }
    	}
    }
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public Rect getSourceRect() {
		return sourceRect;
	}
	public void setSourceRect(Rect sourceRect) {
		this.sourceRect = sourceRect;
	}
	public int getFrameNr() {
		return frameNr;
	}
	public void setFrameNr(int frameNr) {
		this.frameNr = frameNr;
	}
	public int getCurrentFrame() {
		return currentFrame;
	}
	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}
	public int getFramePeriod() {
		return framePeriod;
	}
	public void setFramePeriod(int framePeriod) {
		this.framePeriod = framePeriod;
	}
	public int getSpriteWidth() {
		return spriteWidth;
	}
	public void setSpriteWidth(int spriteWidth) {
		this.spriteWidth = spriteWidth;
	}
	public int getSpriteHeight() {
		return spriteHeight;
	}
	public void setSpriteHeight(int spriteHeight) {
		this.spriteHeight = spriteHeight;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	// the update method for Elaine
	public void update(long gameTime) {
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			// increment the frame
			currentFrame++;
			if (currentFrame >= frameNr) {
				currentFrame = 0;
			}
		}
		// define the rectangle to cut out sprite
		this.sourceRect.left = currentFrame * spriteWidth;
		this.sourceRect.right = this.sourceRect.left + spriteWidth;
	}
	
	// the draw method which draws the corresponding frame
	public void draw(Canvas canvas,int posGX, int posGY) {
		// where to draw the sprite
		//Rect destRect = new Rect(getX()-posGX, getY()-posGY, getX()-posGX + spriteWidth, getY()-posGY + spriteHeight);
		//canvas.drawBitmap(bitmap, sourceRect, destRect, null);
		
		Paint colorLine = new Paint();
        colorLine.setColor(Color.RED);
        colorLine.setStyle(Style.FILL);
		canvas.drawRect(x-posGX, y-posGY, x+64-posGX, y+4-posGY, colorLine);
		canvas.drawRect(x-posGX, y-posGY, x+4-posGX, y+64-posGY, colorLine);
		canvas.drawRect(x-posGX, y+64-4-posGY, x+64-posGX, y+64-posGY, colorLine);
		canvas.drawRect(x+64-4-posGX, y-posGY, x+64-posGX, y+64-posGY, colorLine);
		Paint colorRect = new Paint();
        colorRect.setColor(Color.RED);
        colorRect.setStyle(Style.FILL);
        colorRect.setAlpha(50);
        canvas.drawRect(x+4-posGX, y+4-posGY, x+64-4-posGX, y+64-4-posGY, colorRect);
		
		/*canvas.drawBitmap(bitmap, 20, 150, null);
		Paint paint = new Paint();
		paint.setARGB(50, 0, 255, 0);
		canvas.drawRect(20 + (currentFrame * destRect.width()), 150, 20 + (currentFrame * destRect.width()) + destRect.width(), 150 + destRect.height(),  paint);
		*/
	}
	
	public void setUp(){
		this.up=true;
		this.down=false;
		this.left=false;
		this.right=false;
	}
	public void setDown(){
		this.down=true;
		this.up=false;
		this.left=false;
		this.right=false;
	}
	
	public void setRight(){
		this.right=true;
		this.up=false;
		this.down=false;
		this.left=false;
	}
	
	public void setleft(){
		this.left=true;
		this.up=false;
		this.down=false;
		this.right=false;
	}
	
	public void desactivateArrows(){
		this.up=false;
		this.down=false;
		this.left=false;
		this.right=false;
	}
}
