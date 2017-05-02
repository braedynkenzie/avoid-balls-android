package dev.mobile.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Braedyn Kenzie on 2017-03-01.
 *
 */

public class Projectile {
    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();

    private int RADIUS = 10;
    private int MAX_SPEED = 1500000;

    float death_timer;

    private Circle boundingCircle = new Circle();

    public Projectile(float deathTime, int x_pos, int y_pos, int x_vel, int y_vel){
        death_timer = deathTime;

        position.x = x_pos;
        position.y = y_pos;
        velocity.x = x_vel;
        velocity.y = y_vel;

        boundingCircle.setRadius(RADIUS);

        if(x_vel > MAX_SPEED){
            velocity.x = MAX_SPEED;
        }
        if(x_vel < -MAX_SPEED){
            velocity.x = -MAX_SPEED;
        }
        if(y_vel > MAX_SPEED){
            velocity.y = MAX_SPEED;
        }
        if(y_vel < -MAX_SPEED){
            velocity.y = -MAX_SPEED;
        }
    }

    public void update(float delta){
        death_timer -= delta;

        position.x += velocity.x/10 * delta;
        position.y += velocity.y/10 * delta;
        boundingCircle.setPosition(position);

        velocity.x *= 21/20f;
        velocity.y *= 21/20f;

        // todo: delete the projectile when it goes off the screen (do in gameworld class)
    }

    public void updateDangerProjectile(float delta, Ball mainBall){
        velocity.x += (mainBall.getX() - position.x);
        velocity.y += (mainBall.getY() - position.y);

        position.x += velocity.x/10 * delta;
        position.y += velocity.y/10 * delta;
        boundingCircle.setPosition(position);

        velocity.x *= 41/40f;
        velocity.y *= 41/40f;

        // todo: delete the projectile when it goes off the screen (do in gameworld class)
    }

    public float getDeathTimer() { return death_timer; }

    public int getX(){
        return (int) position.x;
    }

    public int getY(){
        return (int) position.y;
    }

    public int getXVel(){
        return (int) velocity.x;
    }

    public int getYVel(){
        return (int) velocity.y;
    }

    public int getRadius(){
        return RADIUS;
    }

    public Circle getBoundingCircle(){ return boundingCircle; }


}
