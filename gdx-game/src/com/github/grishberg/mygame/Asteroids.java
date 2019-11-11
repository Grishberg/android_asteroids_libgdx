package com.github.grishberg.mygame;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import java.util.*;
import com.badlogic.gdx.*;

public class Asteroids
{
	private final int screenW;
	private final int screenH;
	private final Array<Rectangle> raindrops;
	private long lastDropTime;
	private int dropsGathered;
	private final Asteroid asteroid;

	public Asteroids(int screenW, int screenH) {
		this.screenW = screenW;
		this.screenH = screenH;
		
		asteroid = new Asteroid();
		// create the raindrops array and spawn the first raindrop
		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	}
	
	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, screenW - 64);
		raindrop.y = screenH;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}
	
	public void render(SpriteBatch batch, float timeDelta) {
		// check if we need to create a new raindrop
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
			spawnRaindrop();
		}
		
		for (Rectangle raindrop : raindrops) {
			asteroid.render(batch, raindrop.x, raindrop.y);
		}
	}
	
	public int checkOverlaps(Spaceship spaceship, float timeDelta){
		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * timeDelta;
			if (raindrop.y + 64 < 0)
				iter.remove();
			if (spaceship.isOverlaps(raindrop)) {
				dropsGathered++;
				//dropSound.play();
				iter.remove();
			}
		}
		return dropsGathered;
	}
}

