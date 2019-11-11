package com.github.grishberg.mygame;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import android.util.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.*;

public class GameScreen implements Screen {
	private static final String TAG = GameScreen.class.getSimpleName();
  	final Drop game;

	Texture dropImage;
	
	Pixmap spaceshipPix;
	//Sound dropSound;
	Music rainMusic;
	OrthographicCamera camera;
	
	
	int dropsGathered;
	
	private int screenW = 480;
	private int screenH = 800;
	private final Spaceship spaceship;
	private final Asteroids asteroids;
	

	public GameScreen(final Drop gam) {
		this.game = gam;

		// load the images for the droplet and the bucket, 64x64 pixels each
		dropImage = new Texture(Gdx.files.internal("bigrock.png"));
		asteroids = new Asteroids(screenW, screenH);
		spaceship = new Spaceship(screenW, screenH);

		// load the drop sound effect and the rain background "music"
		//dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		//rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		//rainMusic.setLooping(true);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenW, screenH);
		
		Gdx.input.setInputProcessor(new InputAdapter(){

				@Override
				public boolean touchDown(int screenX, int screenY, int pointer, int button) {
					return true;
				}
				@Override
				public boolean touchUp(int screenX, int screenY, int pointer, int button) {
					return true;
				}
			});
	}

	@Override
	public void render(float delta) {
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.f, 0.f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the bucket and
		// all drops
		game.batch.begin();
		game.font.draw(game.batch, "Asteroids Collected: " + dropsGathered, 0, screenH);
		spaceship.render(game.batch);
		
		asteroids.render(game.batch, Gdx.graphics.getDeltaTime());
		game.batch.end();

		// process user input
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			spaceship.onTouched(touchPos, Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			spaceship.onLeftPressed(Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			spaceship.onRightPressed(Gdx.graphics.getDeltaTime());

		dropsGathered = asteroids.checkOverlaps(spaceship, Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		//rainMusic.play();
		Log.d(TAG,"show");
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		dropImage.dispose();
		spaceship.dispose();
		//dropSound.dispose();
		rainMusic.dispose();
	}

}
