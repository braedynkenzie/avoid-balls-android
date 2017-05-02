package dev.mobile;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.mobile.screens.GameScreen;

public class ABGame extends Game {
	
	@Override
	public void create () {
		GameScreen screen = new GameScreen();
		dev.mobile.helpers.AssetLoader.load();
		setScreen(screen);

	}


	
	@Override
	public void dispose () {

		super.dispose();

	}
}
