package dev.mobile.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.LinkedList;
import java.util.List;

import dev.mobile.gameobjects.Ball;
import dev.mobile.gameobjects.DangerBall;
import dev.mobile.gameobjects.Projectile;

/**
 * Created by Braedyn Kenzie on 2017-02-28.
 *
 */

public class GameRenderer {

    // World to render
    GameWorld gameworld;

    // Orthographic camera -- 2D game
    private OrthographicCamera cam;

    // Required to render shapes
    private ShapeRenderer shapeRenderer;

    // Required to render text
    SpriteBatch spriteBatch = new SpriteBatch();
    BitmapFont font = new BitmapFont();

    // Game Objects
    private Ball mainBall;
    private List<Projectile> projectiles = new LinkedList<Projectile>();
    private List<Projectile> dangerProjectiles = new LinkedList<Projectile>();
    private List<DangerBall> dangerBalls = new LinkedList<DangerBall>();

    public GameRenderer(GameWorld gameWorld, int gameWidth, int gameHeight){
        this.gameworld = gameWorld;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, gameWidth, gameHeight);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        initGameObjects();
    }

    private void initGameObjects() {
        this.mainBall = this.gameworld.mainBall;
        this.projectiles = gameworld.projectiles;
        this.dangerProjectiles = gameworld.dangerProjectiles;
        this.dangerBalls = gameworld.dangerBalls;

    }

    public void render(){
        // Colour the background of the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.LIGHT_GRAY);

        shapeRenderer.circle(mainBall.getX(),mainBall.getY(),mainBall.getRadius());

        shapeRenderer.end();

        switch(gameworld.getCurrentGameState()){
            case MENU:

                renderMenuItems();

                break;
            case RUNNINGEASY:
            case RUNNINGMED:
            case RUNNINGHARD:

                // Render projectiles
                if(!projectiles.isEmpty()){
                    renderProjectiles();
                }

                // Render dangerBalls
                if(!dangerBalls.isEmpty()){
                    renderDangerBalls();
                }

                // Render the gameTime
                spriteBatch.begin();
                font.draw(spriteBatch, Float.toString(gameworld.gameTime), 10, 10);
                spriteBatch.end();


                break;
            case GAMEOVER:

                // Render dangerBalls
                if(!dangerBalls.isEmpty()){
                    renderDangerBalls();
                }
                mainBall.setXVel(0);
                mainBall.setYVel(0);

                break;

        }
    }

    private void renderMenuItems(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.circle(gameworld.easy.getX(), gameworld.easy.getY(), gameworld.easy.getRadius() + 10);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.circle(gameworld.med.getX(),  gameworld.med.getY(),  gameworld.med.getRadius()  + 10);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(gameworld.hard.getX(), gameworld.hard.getY(), gameworld.hard.getRadius() + 10);
        shapeRenderer.end();

    }

    private void renderDangerBalls(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);

        for (DangerBall dangerBall: dangerBalls){
            shapeRenderer.circle(dangerBall.getX(), dangerBall.getY(), dangerBall.getRadius());
        }

        shapeRenderer.end();
    }

    private void renderProjectiles(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.LIGHT_GRAY );

        for (Projectile projectile: projectiles){
            shapeRenderer.circle(projectile.getX(),projectile.getY(), projectile.getRadius());
        }

        shapeRenderer.setColor(Color.FIREBRICK );
        for (Projectile dangerProjectile: dangerProjectiles) {
            shapeRenderer.circle(dangerProjectile.getX(),dangerProjectile.getY(), dangerProjectile.getRadius());
        }

        shapeRenderer.end();
    }

}
