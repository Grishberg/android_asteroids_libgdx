package com.github.grishberg.mygame;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class Spaceship {
	private static final float ROTATE_SPEED = 300;
	private static final float MAX_SPEED = 7;
	private int spaceshipW = 64;
	private int spaceshipH = 64;
	private final int screenW;
	private final int screenH;
	private int x;
	private int y;
	private double momentumAnle;
	private float angle = 0;
	private double speed = 0;
	private float speedX;
	private float speedY;
	private double momentumX;
	private double momentumY;
	Rectangle bucket;
	
	Texture bucketImage;
	private final Sprite sprite;
	
	public Spaceship(int scrW, int scrH){
		screenW = scrW;
		screenH = scrH;
		bucketImage = new Texture( Gdx.files.internal("spaceship.png"));
		sprite = new Sprite(bucketImage);
		// create a Rectangle to logically represent the bucket
		bucket = new Rectangle();
		bucket.x = screenW / 2 - 64 / 2; // center the bucket horizontally
		bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
		// the bottom screen edge
		bucket.width = spaceshipW;
		bucket.height = spaceshipH;
	}
	
	void drawDebug(SpriteBatch batch, BitmapFont font) {
	}
	
	public void onTouched(Vector3 touchPos, float timeDelta ) {
		if (touchPos.x < screenW/3){
			onLeftPressed(timeDelta);
		} else if(touchPos.x >= screenW/3 && touchPos.x < 2.0f*screenW/3.0f){
			onMovePressed(timeDelta);
		} else {
			onRightPressed(timeDelta);
		}
	}
	
	public void onLeftPressed(float timeDelta){
		angle += ROTATE_SPEED * timeDelta;
	}
	
	public void onRightPressed(float timeDelta){
		angle -= ROTATE_SPEED * timeDelta;
	}
	
	public void onMovePressed(float timeDelta) {
		double mX = Math.cos(momentumAnle * MathUtils.degRad) * speed;
		double mY = Math.sin(momentumAnle * MathUtils.degRad) * speed;
		
		float powerDelta = 8f * timeDelta;
		momentumX = powerDelta * Math.cos(angle * MathUtils.degRad);
		momentumY = powerDelta * Math.sin(angle * MathUtils.degRad);
		double dx = mX + momentumX;
		double dy = mY + momentumY;
		speedX = (float) dx;
		speedY = (float) dy;
		double ang = Math.atan2(dy, dx) * MathUtils.radDeg;
		momentumAnle = ang;
		speed = Math.sqrt(dx*dx + dy*dy);
		if(speed > MAX_SPEED){
			speed = MAX_SPEED;
		}
	}
	
	private void checkBounds() {
		// make sure the bucket stays within the screen bounds
		if (bucket.x < 0)
			bucket.x = screenW;
		if (bucket.x > screenW)
			bucket.x = 0;
			
		if(bucket.y < 0) {
			bucket.y = screenH;
		}
		if(bucket.y > screenH) {
			bucket.y = 0;
		}
	}
	
	public boolean isOverlaps(Rectangle rect) {
		return rect.overlaps(bucket);
	}
	
	public void render(SpriteBatch batch) {
		sprite.setCenter(bucket.x,bucket.y);
		sprite.setOrigin(spaceshipW/2, spaceshipH/2);
		sprite.setRotation(angle-90);
		
		bucket.x += speed * Math.cos((momentumAnle) * MathUtils.degRad);
		bucket.y += speed * Math.sin((momentumAnle) * MathUtils.degRad );
		
		
		sprite.setSize(spaceshipW, spaceshipH);
		sprite.setPosition(bucket.x, bucket.y);
		sprite.draw(batch);
		checkBounds();
	}
	
	public void dispose(){
		bucketImage.dispose();
	}
}
