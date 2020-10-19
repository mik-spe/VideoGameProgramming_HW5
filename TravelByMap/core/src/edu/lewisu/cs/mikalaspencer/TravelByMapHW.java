package edu.lewisu.cs.mikalaspencer;

/**
 * Mikala Spencer
 * 2020-10-20
 * This program moves & rotates images of a map and a detailed map.
 * When zooming in, the detailed map appears.
 * When zooming out, the general map appears.
 */

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TravelByMapHW extends ApplicationAdapter {
	// Declare variables
	SpriteBatch batch;

	// Create textures for both map images & TextureRegion to display it
	Texture map;
	Texture mapDetail;
	TextureRegion imgDisplay;

	OrthographicCamera cam;
	int WIDTH;
	int HEIGHT;

	int imgX, imgY;
	int imgWidth, imgHeight;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		map = new Texture("GameMap.jpg");
		mapDetail = new Texture("GameMapDetail.jpg");

		imgDisplay= new TextureRegion(map);

		imgX = 0;
		imgY = 0;

		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		cam = new OrthographicCamera(WIDTH,HEIGHT);
		cam.translate(WIDTH/2,HEIGHT/2);
		cam.update();

		batch.setProjectionMatrix(cam.combined);
	}

	// Keyboard and mouse input
	public void handleInput()
	{
		boolean shiftHeld = false;

		// Update the camera
		boolean cameraNeedsUpdating = false;

		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
		{
			shiftHeld = true;
		}
		if (Gdx.input.isKeyPressed(Keys.UP))
		{
			if (shiftHeld)
			{
				// Zoom out & display general map
				cam.zoom+=0.1;
				imgDisplay = new TextureRegion(map);
			}
			else
			{
				// Move camera up
				cam.translate(0,1);
			}
			cameraNeedsUpdating = true;
		}

		if (Gdx.input.isKeyPressed(Keys.DOWN))
		{
			if (shiftHeld)
			{
				// Zoom in & display detailed map
				cam.zoom+=-0.1;
				imgDisplay = new TextureRegion(mapDetail);
				
			}
			else
			{
				// Move camera down
				cam.translate(0,-1);
			}
			cameraNeedsUpdating = true;
	}

	if (Gdx.input.isKeyPressed(Keys.LEFT))
		{
			if (shiftHeld)
			{
				// Rotate right
				cam.rotate(1);
			}
			else
			{
				// Move camera left
				cam.translate(-1,0);
			}
			cameraNeedsUpdating = true;
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			if (shiftHeld)
			{
				// Rotate left
				cam.rotate(-1);
			}
			else
			{
				// Move camera right
				cam.translate(1,0);
			}
			cameraNeedsUpdating = true;
		}

		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			// Exiting the game
			Gdx.app.exit();
		}

		// Calls the camera update function
		if (cameraNeedsUpdating)
		{
			updateCamera();
		} 
	}

	// Updates the camera and sets projection matrix
	public void updateCamera()
	{
		cam.update();
		batch.setProjectionMatrix(cam.combined);
	}

	@Override
	public void render () 
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		handleInput();

		batch.begin();
		batch.draw(imgDisplay, imgX, imgY);
		batch.end();
	}
	
	@Override
	public void dispose () 
	{
		batch.dispose();
	}
}

