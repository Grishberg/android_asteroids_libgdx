package com.github.grishberg.mygame;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class Spaceship {
	private static final float ROTATE_SPEED = 50;
	private int spaceshipW = 64;
	private int spaceshipH = 64;
	private final int screenW;
	private final int screenH;
	private int x;
	private int y;
	private float angle = 90f;
	private float speed;
	
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
	
	public void onTouched(Vector3 touchPos, float timeDelta ) {
		if (touchPos.x < screenW/3){
			onLeftPressed(timeDelta);
		} else if(touchPos.x >= screenW/3 && touchPos.x < 2.0f*screenW/3.0f){
			onMovePressed(timeDelta);
		} else {
			onRightPressed(timeDelta);
		}

		checkBounds();
	}
	
	public void onLeftPressed(float timeDelta){
		angle -= ROTATE_SPEED * timeDelta;
		checkBounds();
	}
	
	public void onRightPressed(float timeDelta){
		angle += ROTATE_SPEED * timeDelta;
		checkBounds();
	}
	
	public void onMovePressed(float timeDelta){
		speed += 8 * timeDelta;
		checkBounds();
	}
	
	private void checkBounds() {
		// make sure the bucket stays within the screen bounds
		if (bucket.x < 0)
			bucket.x = 0;
		if (bucket.x > screenW - 64)
			bucket.x = screenW - 64;
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
		bucket.x += speed * Math.cos(angle * MathUtils.degRad);
		bucket.y += speed * Math.sin(angle * MathUtils.degRad );
		sprite.rotate(angle);
		
		sprite.setRotation(angle -90f);
		sprite.setSize(spaceshipW, spaceshipH);
		sprite.setPosition(bucket.x, bucket.y);
		//sprite.setPosition(bucket.x + radius * (float)Math.cos(angle * MathUtils.degrad), bucket.y + radius * (float)Math.sin(angle * MathUtils.degrad));
		sprite.draw(batch);
		//batch.draw(bucketImage, bucket.x, bucket.y, spaceshipW, spaceshipH);
	}
	
	public void dispose(){
		bucketImage.dispose();
	}
}
