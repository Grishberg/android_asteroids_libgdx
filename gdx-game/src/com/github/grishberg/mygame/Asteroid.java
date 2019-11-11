package com.github.grishberg.mygame;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

public class Asteroid
{
	private final int w = 64;
	private final int h = 64;
	private final Texture dropImage;
	public Asteroid() {
		dropImage = new Texture(Gdx.files.internal("bigrock.png"));
		
	}
	
	public void render(SpriteBatch batch, float x, float y) {
		batch.draw(dropImage, x, y, w, h);
	}
	
	public void dispose() {
		dropImage.dispose();
	}
}
