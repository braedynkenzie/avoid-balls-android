package dev.mobile.helpers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import dev.mobile.game.GameWorld;
import dev.mobile.gameobjects.Ball;
import dev.mobile.gameobjects.Projectile;

/**
 * Created by Braedyn Kenzie on 2017-02-28.
 *
 */

public class InputHandler implements GestureDetector.GestureListener {
    GameWorld world;
    Ball mainBall;

    public InputHandler(GameWorld gameworld){
        this.world = gameworld;
        mainBall = this.world.mainBall;
    }


    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
//        if((velocityX > 1000 | velocityX < -1000) && (velocityY > 1000 | velocityY < -1000)){
//            mainBall.setXVel((int) velocityX * 2000);
//            if(velocityX > 5000){mainBall.setXVel(5000 * 2000); }
//            mainBall.setYVel((int) velocityY * 2000);
//            if(velocityY > 5000){mainBall.setYVel(5000 * 2000); }
//        }

        // might implement a projectile from mainBall on fling input
        Projectile fling_projectile = new Projectile(2, (int) mainBall.getX(), (int) mainBall.getY(), (int) velocityX, (int) velocityY);
        world.addProjectile(fling_projectile);
        mainBall.setXVel((int) (mainBall.getXVel() - velocityX*2000));
        mainBall.setYVel((int) (mainBall.getYVel() - velocityY*2000));


        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
