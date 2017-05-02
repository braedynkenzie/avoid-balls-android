package dev.mobile.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;

import dev.mobile.game.GameRenderer;
import dev.mobile.game.GameWorld;
import dev.mobile.helpers.InputHandler;

/**
 * Created by Braedyn Kenzie on 2017-02-28.
 *
 */

public class GameScreen implements Screen {
    GameWorld world;
    GameRenderer renderer;

    private float runTime = 0;

    public GameScreen(){
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        this.world = new GameWorld(screenWidth, screenHeight);
        this.renderer = new GameRenderer(world, screenWidth, screenHeight);

        GestureDetector gd = new GestureDetector(new InputHandler(world));
        Gdx.input.setInputProcessor(gd);

    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render();

    }



    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
